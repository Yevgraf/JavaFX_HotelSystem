package Controller;

import Model.MessageBoxes;
import Model.TipoUtilizador;
import Model.Utilizador;
import com.example.hotel.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import DAL.DBconn;
import javafx.stage.Stage;

public class GestaoUtilizadoresController implements Initializable {

    @FXML
    private Label VerificarContacto;

    @FXML
    private Label VerificarNIF;

    @FXML
    private Label VerificarNome;

    @FXML
    private Label EmptyMessage;

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


    @FXML
    private TableColumn<Utilizador, String> tblContato;

    @FXML
    private TableColumn<Utilizador, String> tblEmail;

    @FXML
    private TableColumn<Utilizador, Integer> tblId;

    @FXML
    private TableColumn<Utilizador, String> tblMorada;

    @FXML
    private TableColumn<Utilizador, String> tblNif;

    @FXML
    private TableColumn<Utilizador, String> tblNome;

    @FXML
    private TableColumn<Utilizador, String> tblTipo;

    @FXML
    private TableColumn<Utilizador, String> tblUtilizador;

    @FXML
    private TableView<Utilizador> tblUtilizadores;

    @FXML
    private TableColumn<Utilizador, Date> tbtDataNascimento;

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox<String> cmbUtilizador;


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
            ps2 = connection.prepareStatement("INSERT INTO Utilizador (nome, contacto, email, utilizador, palavrapasse, nif) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

    private void initCombos() {
        List<TipoUtilizador> tipos = TipoUtilizador.getTipoUtilizador();

        for (TipoUtilizador tipo : tipos) {
            cmbUtilizador.getItems().add(tipo.getTipo());
        }
    }

    @FXML
    void clickCmbUtilizador(ActionEvent event) {
        if (cmbUtilizador.getValue().equals("Gestor")) {
            initTableGestores();
        } else if (cmbUtilizador.getValue().equals("Funcionario")) {
            Utilizador.getFuncionario();
            initTableFuncionarios();
        } else if (cmbUtilizador.getValue().equals("Cliente")) {
            Utilizador.getClientes();
            initTableClientes();
        }
    }


    @FXML
    void clickBtnGestorEliminar(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Utilizador selectedID = tblUtilizadores.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Utilizador WHERE id = ?");
                ps2.setInt(1, selectedID.getId());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Utilizador Removido", "Information");
                initTable();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean VerifyNIFCliente() {
        boolean flag;
        String verificar = "SELECT count(1) FROM Utilizador WHERE nif ='" + txt_nif.getText() + "'";
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

        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbtDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tblMorada.setCellValueFactory(new PropertyValueFactory<>("morada"));
        tblContato.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tblNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        tblUtilizador.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
        tblTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoUser().getTipo()));
        tblUtilizadores.setItems(Utilizador.getTodosUtilizadores());
    }

    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
        initTable();
    }

    private void initTableGestores() {

        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbtDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tblMorada.setCellValueFactory(new PropertyValueFactory<>("morada"));
        tblContato.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tblNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        tblUtilizador.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
        tblTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoUser().getTipo()));
        tblUtilizadores.setItems(Utilizador.getGestores());
    }

    private void initTableFuncionarios() {

        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbtDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tblMorada.setCellValueFactory(new PropertyValueFactory<>("morada"));
        tblContato.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tblNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        tblUtilizador.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
        tblTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoUser().getTipo()));
        tblUtilizadores.setItems(Utilizador.getFuncionario());
    }

    private void initTableClientes() {

        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbtDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tblMorada.setCellValueFactory(new PropertyValueFactory<>("morada"));
        tblContato.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tblNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        tblUtilizador.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
        tblTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoUser().getTipo()));
        tblUtilizadores.setItems(Utilizador.getClientes());
    }

}


