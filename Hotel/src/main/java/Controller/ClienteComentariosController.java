package Controller;

import BLL.UtilizadorPreferences;
import com.example.hotel.Main;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClienteComentariosController implements Initializable {


    @FXML
    private Text numLetras;

    @FXML
    private Button Submeter;

    @FXML
    private Button Voltar;

    @FXML
    private TextArea campoComentario;

    @FXML
    void SubmeterClick(ActionEvent event) {

    }

    private void contaLetras() {
        int tamanhoTexto = 200;
        campoComentario.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > tamanhoTexto) campoComentario.setText(oldValue);
                }
        );
        numLetras.textProperty().bind(Bindings.length(campoComentario.textProperty())
                .asString("%d"));
    }


    @FXML
    void VoltarClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCliente.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) Voltar.getScene().getWindow();
        stage.setTitle("Pagina Cliente");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contaLetras();
    }
}
