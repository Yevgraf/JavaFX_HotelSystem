package Controller;

import BLL.UtilizadorBLL;
import BLL.UtilizadorPreferences;
import DAL.TipoUtilizadorDAL;
import Model.MessageBoxes;
import Model.TipoUtilizador;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Stage;


public class CriarUtilizadoresController implements Initializable {

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

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoUtilizadores.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) VoltarBtn.getScene().getWindow();
        stage.setTitle("Gestao Utilizadores");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    private void initCombos() {
        if (UtilizadorPreferences.comparaTipoLogin()){
            combosGestor();
        } else {
            combosFuncionario();
        }
    }

    private void combosGestor() {
        if (UtilizadorPreferences.comparaTipoLogin()) {
            List<TipoUtilizador> tipos = TipoUtilizadorDAL.getTipoUtilizador();

            for (TipoUtilizador tipo : tipos) {
                cmb_tipoUtilizador.getItems().add(tipo.getTipo());
            }
        }
    }

    private void combosFuncionario() {
        cmb_tipoUtilizador.getItems().add("Cliente");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        UtilizadorBLL utilizadorBLL = new UtilizadorBLL();
        String nome = txt_nome.getText();
        String nif = txt_nif.getText();
        String morada = txt_morada.getText();
        Date dataNascimento = new Date(datePickerNasc.getValue().toEpochDay());
        String email = txt_email.getText();
        String contacto = txt_contacto.getText();
        String utilizador = txt_utilizador.getText();
        String password = txt_password.getText();
        String tipoUser = cmb_tipoUtilizador.getValue();

        dataNascimento = new Date(datePickerNasc.getValue().toEpochDay());
        java.sql.Date sqlDate = java.sql.Date.valueOf(datePickerNasc.getValue());

        utilizadorBLL.createUtilizador(nome, nif, morada, sqlDate, email, contacto, utilizador, password, tipoUser);
        MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Utilizador inserido", "Informação Utilizador");
    }
}