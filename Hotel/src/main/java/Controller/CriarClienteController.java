package Controller;

import Model.Cliente;
import Model.MessageBoxes;
import Model.Quarto;
import Model.Utilizador;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAL.DBconn;
import javafx.stage.Stage;

public class CriarClienteController implements Initializable {

    private Label lblSamos;

    @FXML
    private TableColumn<Utilizador, Integer> tbl_contacto;

    @FXML
    private TableColumn<Utilizador, Date> tbl_dataNasc;

    @FXML
    private TableColumn<Utilizador, String> tbl_email;

    @FXML
    private TableColumn<Utilizador, Integer> tbl_id;

    @FXML
    private TableColumn<Utilizador, String> tbl_morada;

    @FXML
    private TableColumn<Utilizador, String > tbl_name;

    @FXML
    private TableColumn<Utilizador, String> tbl_nif;


    @FXML
    private TableColumn<Utilizador, String> tbl_utilizador;

    @FXML
    private TableView<Utilizador> tv_Clientes;
    @FXML
    private DatePicker Dp_dataNascimento;

    @FXML
    private Label EmptyMessage;

    @FXML
    private AnchorPane PainelCriarCliente;

    @FXML
    private Label VerificarContacto;

    @FXML
    private Label VerificarNIF;

    @FXML
    private Label VerificarNome;

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
    private Button btn_update_Voltar;

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
    private Label lblHoras12111;

    @FXML
    private Label lblHoras13;

    @FXML
    private Label lblHoras131;

    @FXML
    private Label lblHoras1311;

    @FXML
    private Label lblHoras13111;


    @FXML
    private TextField txt_contacto;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_morada;

    @FXML
    private TextField txt_nif;

    @FXML
    private TextField txt_nome;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_utilizador;


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
            ps2 = connection.prepareStatement("INSERT INTO Utilizador(nome, contacto, email, utilizador, palavrapasse, nif,idTipoUtilizador,morada,dataNascimento) VALUES (?,?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_nome.getText());
            ps2.setString(2, txt_contacto.getText());
            ps2.setString(3, txt_email.getText());
            ps2.setString(4, txt_utilizador.getText());
            ps2.setInt(7, Integer.parseInt("3"));
            ps2.setString(8, txt_morada.getText());
            ps2.setString(9, Dp_dataNascimento.getEditor().getText());

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

            Utilizador selectedID = (Utilizador) tv_Clientes.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Utilizador WHERE id = ?");
                ps2.setInt(1, selectedID.getId());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Cliente Removido", "Information");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


//    @FXML
//    void onActionUpdateCliente(ActionEvent event) throws IOException, SQLException {
//        try {
//            clientlist.clear();
//
//            String tablequery = "Select * FROM Cliente";
//            preparedStatement = DBconn.getConn().prepareStatement(tablequery);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                clientlist.add(new Cliente(
//                        resultSet.getString("nome"),
//                        resultSet.getInt("contacto"),
//                        resultSet.getString("email"),
//                        resultSet.getString("utilizador"),
//                        resultSet.getString("password"),
//                        resultSet.getInt("nif")));
//                tv_clientes.setItems(clientlist);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(CriarClienteController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

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


    private void initTable() {

        tbl_id.setResizable(false);
        tbl_name.setResizable(false);
        tbl_nif.setResizable(false);
        tbl_morada.setResizable(false);
        tbl_dataNasc.setResizable(false);
        tbl_email.setResizable(false);
        tbl_contacto.setResizable(false);
        tbl_utilizador.setResizable(false);

        tbl_id.setCellValueFactory(new PropertyValueFactory<Utilizador, Integer>("id"));
        tbl_name.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("nome"));
        tbl_nif.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("nif"));
        tbl_morada.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("morada"));
        tbl_dataNasc.setCellValueFactory(new PropertyValueFactory<Utilizador, Date>("dataNascimento"));
        tbl_email.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("email"));
        tbl_contacto.setCellValueFactory(new PropertyValueFactory<Utilizador, Integer>("contacto"));
        tbl_utilizador.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("utilizador"));
        tv_Clientes.setItems(Utilizador.getClientes());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}

