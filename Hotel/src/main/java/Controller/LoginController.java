package Controller;

import BLL.DBconn;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.owner;

public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField userTxt;

    @FXML
    void clickLoginBtn(ActionEvent event) {
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
            } else {
                loginIncorreto();
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: ..................." + ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loginIncorreto() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText("User e/ou Password incorreto(s)!");
        alert.setContentText("Por favor tente novamente.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}