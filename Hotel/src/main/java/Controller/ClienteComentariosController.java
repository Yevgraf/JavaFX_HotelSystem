package Controller;

import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ClienteComentariosController {

    @FXML
    private Button Submeter;

    @FXML
    private Button Voltar;

    @FXML
    void SubmeterClick(ActionEvent event) {

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

}
