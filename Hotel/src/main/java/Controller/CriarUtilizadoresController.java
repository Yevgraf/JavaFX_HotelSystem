package Controller;

import BLL.UtilizadorBLL;
import BLL.UtilizadorPreferences;
import DAL.DBconn;
import DAL.TipoUtilizadorDAL;
import DAL.UtilizadorDAL;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

    public static boolean flag;
    static boolean flagnome;

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


    public void onActionAddFuncionario(ActionEvent actionEvent) {
        int nif = Integer.parseInt(txt_nif.getText());
        if (cmb_tipoUtilizador.getValue() != null) {
            String email = txt_email.getText();
          if (txt_nome.getText().isEmpty() == false && txt_nif.getText().isEmpty() == false && txt_morada.getText().isEmpty() == false &&
                  txt_contacto.getText().isEmpty() == false && txt_email.getText().isEmpty() == false && txt_utilizador.getText().isEmpty() == false
                  && txt_password.getText().isEmpty() == false   && cmb_tipoUtilizador.getItems().isEmpty() == false) {
              if (UtilizadorDAL.VerifyNIFColaborador(nif) == true && VerifyNIFColaboradorMin() == true && VerifyContacto() == true && VerifyNome() == true && isValidEmail(email) == true) {
                  RegistarUtilizador();
              } else {
                  MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Dados incorretos!","Erro");}
          } else {
              MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Preencha todos os campos!","Erro");
          }
    } else {
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Escolha um tipo de utilizador.", "Aviso:");
        }
    }

    void RegistarUtilizador() {
        UtilizadorBLL utilizadorBLL = new UtilizadorBLL();
        String nome = txt_nome.getText();
        String nif = txt_nif.getText();
        String morada = txt_morada.getText();
        java.sql.Date sqlDate = java.sql.Date.valueOf(datePickerNasc.getValue());
        String email = txt_email.getText();
        String contacto = txt_contacto.getText();
        String utilizador = txt_utilizador.getText();
        String password = txt_password.getText();
        String tipoUser = cmb_tipoUtilizador.getValue();

        utilizadorBLL.createUtilizador(nome, nif, morada, sqlDate, email, contacto, utilizador, password, tipoUser);
        MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Utilizador criado!", "Informação:");
    }

    public boolean VerifyNIFColaboradorMin() {
        boolean flagnif;
        if (txt_nif.getText().length() < 9) {
            VerificarNIF.setText("Minimo 9 caracteres!");
            flagnif = false;
        } else {
            flagnif = true;
        }
        return flagnif;
    }

    public boolean VerifyContacto() {
        boolean flagcontacto;
        if (txt_contacto.getText().length() < 9) {
            VerificarContacto.setText("Minimo 9 caracteres!");
            flagcontacto = false;
        } else {
            flagcontacto = true;
        }
        return flagcontacto;
    }

    public boolean VerifyNome() {
        char[] chars = txt_nome.getText().toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                VerificarNome.setText("Nome nao pode conter numeros!");
                flagnome = false;
                break;
            }else{
                flagnome = true;
            }
        }
        return flagnome;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}