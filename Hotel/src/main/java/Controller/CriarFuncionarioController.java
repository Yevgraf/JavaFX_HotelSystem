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
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import DAL.DBconn;
import javafx.stage.Stage;


public class CriarFuncionarioController implements Initializable {

    @FXML
    private Label VerificarNome;

    @FXML
    private Label VerificarContacto;

    @FXML
    private Label VerificarNIF;

    private Button btn_refresh;

    @FXML
    private Button btn_update_Voltar;

    @FXML
    private Button btn_add_funcionario;

    @FXML
    private Button btn_remove_funcionario;

    @FXML
    private Button btn_update_funcionario;

    @FXML
    private ComboBox<String> cmb_tipoUtilizador;

    @FXML
    private TableColumn<Utilizador, String> tbl_contacto;

    @FXML
    private TableColumn<Utilizador, Date> tbl_dataNasc;

    @FXML
    private TableColumn<Utilizador, String> tbl_email;

    @FXML
    private TableColumn<Utilizador, Integer> tbl_id;

    @FXML
    private TableColumn<Utilizador, String> tbl_morada;

    @FXML
    private TableColumn<Utilizador, String> tbl_name;

    @FXML
    private TableColumn<Utilizador, String> tbl_nif;

    @FXML
    private TableColumn<Utilizador, String> tbl_tipoColaborador;

    @FXML
    private TableColumn<Utilizador, String> tbl_utilizador;

    @FXML
    private TableView<Utilizador> tv_funcionarios;

    @FXML
    private TextField txt_contacto;

    @FXML
    private DatePicker datePickerNasc;

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
    private Button VoltarBtn;

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) VoltarBtn.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    private void initCombos() {
        List<TipoUtilizador> tipos = TipoUtilizador.getTipoUtilizador();

        for (TipoUtilizador tipo : tipos) {
            cmb_tipoUtilizador.getItems().add(tipo.getTipo());
        }
    }

    private void initTable() {

        tbl_name.setResizable(false);
        tbl_email.setResizable(false);
        tbl_dataNasc.setResizable(false);
        tbl_morada.setResizable(false);
        tbl_contacto.setResizable(false);
        tbl_nif.setResizable(false);
        tbl_tipoColaborador.setResizable(false);
        tbl_utilizador.setResizable(false);

        tbl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_name.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbl_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbl_dataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tbl_morada.setCellValueFactory(new PropertyValueFactory<>("morada"));
        tbl_contacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tbl_nif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        tbl_utilizador.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
        tbl_tipoColaborador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoUser().getTipo()));
        tv_funcionarios.setItems(Utilizador.getColaboradores());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initCombos();
    }


    public void onActionAddFuncionario(javafx.event.ActionEvent actionEvent) {
        RegistarUtilizador();
        // VerifyNIFColaborador();
        // VerifyNIFColaboradorMin();
        // VerifyContacto();
        // VerifyNome();
        //  if (txt_nome.getText().isEmpty() == false && txt_nif.getText().isEmpty() == false && txt_morada.getText().isEmpty() == false &&
        //          txt_contacto.getText().isEmpty() == false && txt_email.getText().isEmpty() == false && txt_utilizador.getText().isEmpty() == false
        //          && txt_password.getText().isEmpty() == false   && cmb_tipocolaborador.getItems().isEmpty() == false) {
        //      if (VerifyNIFColaborador() == true && VerifyNIFColaboradorMin() == true && VerifyContacto() == true && VerifyNome() == true) {
        //          RegistarFuncionario();
        //      }
        //  } else {
        //      EmptyMessage.setText("Preencha todos os campos");
        //  }
    }

    void RegistarUtilizador() {
        PreparedStatement ps2;
        int contador, tamanho, codigoASCII;
        String password;
        String passwordCriptografada = "";
        try {
            Utilizador utilizadorACriar = new Utilizador(
                    txt_nome.getText(),
                    txt_nif.getText(),
                    txt_morada.getText(),
                    new Date(datePickerNasc.getValue().toEpochDay()),
                    txt_email.getText(),
                    txt_contacto.getText(),
                    txt_utilizador.getText(),
                    txt_password.getText(),
                    "");

            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Utilizador(nome, nif, morada, dataNascimento, email, contacto, " +
                    "utilizador, palavrapasse, idTipoUtilizador) VALUES (?,?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_nome.getText());
            ps2.setString(2, txt_nif.getText());
            ps2.setString(3, txt_morada.getText());
            ps2.setString(4, datePickerNasc.getEditor().getText());
            ps2.setString(5, txt_email.getText());
            ps2.setString(6, txt_contacto.getText());
            ps2.setString(7, txt_utilizador.getText());
            password = txt_password.getText();
            tamanho = password.length();
            password = password.toUpperCase();
            contador = 0;
//
            while (contador < tamanho) {
                codigoASCII = password.charAt(contador) + 130;
                passwordCriptografada = passwordCriptografada + (char) codigoASCII;
                contador++;
            }
            ps2.setString(8, passwordCriptografada);
            Integer opcao = verificaTipoUtilizador(cmb_tipoUtilizador.getValue());
            ps2.setInt(9, opcao);
            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Utilizador inserido", "Informação Colaborador");

        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(ex);
        }
    }

    public void OnActionRefresh(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarFuncionario.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btn_refresh.getScene().getWindow();
        stage.setTitle("Criar Funcionario");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    public void OnActionUpdate(ActionEvent actionEvent) {

    }

    public void OnActionRemoveFuncionario(ActionEvent actionEvent) {

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Utilizador selectedID = tv_funcionarios.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Utilizador WHERE id = ?");
                ps2.setInt(1, selectedID.getId());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Colaborador Removido", "Information");

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean VerifyNIFColaborador() {
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

    public boolean VerifyNIFColaboradorMin() {
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

    public Integer verificaTipoUtilizador(String comboTxt) {
        Integer opcao;
        switch (comboTxt) {
            case "Gestor":
                opcao = 1;
                break;
            case "Funcionario":
                opcao = 2;
                break;
            default:
                opcao = 3;
        }
        return opcao;
    }
}