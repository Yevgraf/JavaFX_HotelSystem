package Controller;

import BLL.UserBLL;
import Model.MessageBoxes;
import Model.Utilizador;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField userTxt;

    @FXML
    void clickLoginBtn(ActionEvent event) {

        if (userTxt.getText().isEmpty() == false && passwordTxt.getText().isEmpty() == false) {
            Login();
        } else {
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Preencha todos os campos","Informação");
        }
    }

    void Login() {
        try {
            UserBLL bll = new UserBLL();
            Utilizador utilizador = bll.Login(userTxt.getText(), passwordTxt.getText());

            if (utilizador != null) {
                // FEZ LOGIN
                if (utilizador.getTipoUser().getTipo().equals("Gestor")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
                    Stage stage = new Stage();
                    Stage newStage = (Stage) loginBtn.getScene().getWindow();
                    stage.setTitle("Página do GestorController");
                    newStage.hide();
                    stage.setScene(new Scene(fxmlLoader.load()));
                    stage.show();
                } else if (utilizador.getTipoUser().getTipo().equals("Funcionario")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario.fxml"));
                    Stage stage = new Stage();
                    Stage newStage = (Stage) loginBtn.getScene().getWindow();
                    stage.setTitle("Página do Funcionário");
                    newStage.hide();
                    stage.setScene(new Scene(fxmlLoader.load()));
                    stage.show();
     //Inserir Login pág CLIENTE
               // } else if (utilizador.getTipoUser().getTipo().equals("Cliente")) {
                 //  FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCliente.fxml"));
                 //  Stage stage = new Stage();
                 //  Stage newStage = (Stage) loginBtn.getScene().getWindow();
                 //  stage.setTitle("Página do Cliente");
                 //  newStage.hide();
                 //  stage.setScene(new Scene(fxmlLoader.load()));
                 //  stage.show();
                }
            } else {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Login incorreto!","Erro");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //public boolean VerifyUser() {
    //        boolean flag;
    //        String verificar = "SELECT count(1) FROM Colaborador WHERE utilizador ='" + userTxt.getText() + "'";
    //        try {
    //            PreparedStatement stmt = DBconn.getConn().prepareStatement(verificar);
    //            ResultSet rs = stmt.executeQuery();
    //            while (rs.next()) {
    //                if (rs.getInt(1) == 0) {
    //                    ValidarUser.setText("Utilizador não encontrado!");
    //                } else {
    //                    ValidarUser.setText("");
    //                }
    //            }
    //        } catch (SQLException e) {
    //            e.getCause();
    //        }
    //        if (ValidarUser.getText().equals("Utilizador não encontrado!")) {
    //            flag = false;
    //        } else {
    //            flag = true;
    //        }
    //        return flag;
    //    }

    /*public boolean VerifyPass() {
        boolean flag;
        String verificar = "SELECT count(1) FROM Colaborador WHERE utilizador ='" + userTxt.getText() + "'";
        try {
            PreparedStatement stmt = DBconn.getConn().prepareStatement(verificar);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 0) {
                    ValidarUser.setText("Utilizador não encontrado!");
                } else {
                    ValidarUser.setText("");
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        if (ValidarUser.getText().equals("Utilizador não encontrado!")) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}