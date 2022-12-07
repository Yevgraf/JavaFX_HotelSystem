package Controller;

import BLL.DBconn;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;


    @FXML
    private Label ValidarPass;

    @FXML
    private Label ValidarUser;

    @FXML
    private Label EmptyMessage;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField userTxt;

    @FXML
    void clickLoginBtn(ActionEvent event) {

        if (userTxt.getText().isEmpty() == false && passwordTxt.getText().isEmpty() == false) {
            if (/*VerifyPass() == true &&*/ VerifyUser() == true) {
                Login();
            }
        } else {
            EmptyMessage.setText("Preencha todos os campos");
        }

    }

    void Login(){
        PreparedStatement ps;
        try {
            DBconn dbConn = new DBconn();
            int contador, tamanho, codigoASCII;
            String password;
            String passwordCriptografada = "";
            Connection connection = dbConn.getConn();
            ps = connection.prepareStatement("SELECT * FROM Colaborador WHERE utilizador = ? AND palavrapasse = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, userTxt.getText());
            password = passwordTxt.getText();
            tamanho = password.length();
            password = password.toUpperCase();
            contador = 0;

            while (contador < tamanho) {
                codigoASCII = password.charAt(contador) + 130;
                passwordCriptografada = passwordCriptografada + (char) codigoASCII;
                contador++;
            }
            ps.setString(2, passwordCriptografada);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                if (result.first()) {
                    String tipo = result.getString("tipoColaborador");
                    if (tipo.equals("Gestor")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
                        Stage stage = new Stage();
                        Stage newStage = (Stage) loginBtn.getScene().getWindow();
                        stage.setTitle("Página do Gestor");
                        newStage.hide();
                        stage.setScene(new Scene(fxmlLoader.load()));
                        stage.show();
                    } else {
                        if (tipo.equals("Funcionario")) {
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario.fxml"));
                            Stage stage = new Stage();
                            Stage newStage = (Stage) loginBtn.getScene().getWindow();
                            stage.setTitle("Página do Funcionário");
                            newStage.hide();
                            stage.setScene(new Scene(fxmlLoader.load()));
                            stage.show();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: ..................." + ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean VerifyUser() {
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
        }

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