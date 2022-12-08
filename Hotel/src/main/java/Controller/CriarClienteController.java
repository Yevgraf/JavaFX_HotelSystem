package Controller;

import Model.Cliente;
import Model.MessageBoxes;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import BLL.DBconn;
import javafx.stage.Stage;

public class CriarClienteController {

    @FXML
    private AnchorPane PainelCriarCliente;

    @FXML
    private Label VerificarContacto;

    @FXML
    private Label VerificarNIF;

    @FXML
    private Label VerificarNome;

    @FXML
    private Label EmptyMessage;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private ImageView btnMinimizateApp;

    @FXML
    private Button btn_add_cliente;

    @FXML
    private Button btn_remove_cliente;

    @FXML
    private Button btn_update_Voltar;

    @FXML
    private Button btn_update_cliente;

    @FXML
    private ImageView imgGestorAdionarProd;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private Label lblGestaoProdutos;

    @FXML
    private Label lblHoras11;

    @FXML
    private Label lblHoras111;

    @FXML
    private Label lblHoras1211;

    @FXML
    private Label lblHoras13;

    @FXML
    private Label lblHoras131;

    @FXML
    private Label lblHoras1311;

    @FXML
    private Label lblSamos;

    @FXML
    private TableColumn<Cliente, String> tbl_contacto;

    @FXML
    private TableColumn<Cliente, String> tbl_email;

    @FXML
    private TableColumn<Cliente, String> tbl_name;

    @FXML
    private TableColumn<Cliente, Integer> tbl_nif;

    @FXML
    private TableColumn<Cliente, String> tbl_password;

    @FXML
    private TableColumn<Cliente, String> tbl_utilizador;

    @FXML
    private TableView<Cliente> tv_clientes;

    @FXML
    private TextField txt_contacto;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_nif;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_utilizador;

    ObservableList<Cliente> clientlist = FXCollections.observableArrayList();

    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;


    @FXML
    void clickAddClienteBtn(ActionEvent event) {

    }

    @FXML
    void onActionAddCliente(ActionEvent event) {

        VerifyNIFCliente();
        VerifyNIFClienteMin();
        VerifyContacto();
        VerifyNome();

        if (txt_nome.getText().isEmpty() == false && txt_nif.getText().isEmpty() == false && txt_email.getText().isEmpty() == false
                && txt_contacto.getText().isEmpty() == false && txt_utilizador.getText().isEmpty() == false && txt_password.getText().isEmpty() == false) {
            if (VerifyNIFCliente() == true && VerifyNIFClienteMin() == true && VerifyContacto() == true && VerifyNome() == true) {
                RegistarCliente();
            }
        } else {
            EmptyMessage.setText("Preencha todos os campos");
        }
    }

    void RegistarCliente() {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps2;
        int contador, tamanho, codigoASCII;
        String password;
        String passwordCriptografada = "";
        try {
            ps2 = connection.prepareStatement("INSERT INTO Cliente(nome, contacto, email, utilizador, palavrapasse, nif) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_nome.getText());
            ps2.setString(2, txt_contacto.getText());
            ps2.setString(3, txt_email.getText());
            ps2.setString(4, txt_utilizador.getText());

            ps2.setInt(6, Integer.parseInt(txt_nif.getText()));
            password = txt_password.getText();
            tamanho = password.length();
            password = password.toUpperCase();
            contador = 0;

            while (contador < tamanho) {
                codigoASCII = password.charAt(contador) + 130;
                passwordCriptografada = passwordCriptografada + (char) codigoASCII;
                contador++;
            }
            ps2.setString(5, passwordCriptografada);

            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Cliente inserido", "Informação Cliente");


        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(ex);

        }
    }


    @FXML
    void onActionRemoveCliente(ActionEvent event) {

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Cliente selectedID = (Cliente) tv_clientes.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Cliente WHERE id = ?");
                ps2.setInt(1, selectedID.getNif());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Cliente Removido", "Information");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @FXML
    void onActionUpdateCliente(ActionEvent event) throws IOException, SQLException {
        try {
            clientlist.clear();

            String tablequery = "Select * FROM Cliente";
            preparedStatement = DBconn.getConn().prepareStatement(tablequery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clientlist.add(new Cliente(
                        resultSet.getString("nome"),
                        resultSet.getInt("contacto"),
                        resultSet.getString("email"),
                        resultSet.getString("utilizador"),
                        resultSet.getString("password"),
                        resultSet.getInt("nif")));
                tv_clientes.setItems(clientlist);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CriarClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void onActionVoltar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoClienteController.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btn_update_Voltar.getScene().getWindow();
        stage.setTitle("Pagina GestorController");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    public boolean VerifyNIFCliente() {
        boolean flag;
        String verificar = "SELECT count(1) FROM Cliente WHERE nif ='" + txt_nif.getText() + "'";
        try {
            PreparedStatement stmt = DBconn.getConn().prepareStatement(verificar);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    VerificarNIF.setText("O nif já existe!");
                } else {
                    VerificarNIF.setText("");
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        if (VerificarNIF.getText().equals("O nif já existe!")) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean VerifyNIFClienteMin() {
        boolean flag;
        if (txt_nif.getText().length() < 9) {
            VerificarNIF.setText("Minimo 9 caracteres!");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean VerifyContacto() {
        boolean flag;
        if (txt_contacto.getText().length() < 9) {
            VerificarContacto.setText("Minimo 9 caracteres!");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean VerifyNome() {
        boolean flag;
        char[] chars = txt_nome.getText().toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                VerificarNome.setText("Nome nao pode conter numeros!");
            }
        }
        if (VerificarNome.getText().equals("Nome nao pode conter numeros!")) {
            flag = false;
        } else flag = true;

        return flag;
    }
}

