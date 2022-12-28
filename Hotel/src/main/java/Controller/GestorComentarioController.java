package Controller;

import Model.Comentario;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GestorComentarioController {

    @FXML
    private Button Submeter;

    @FXML
    private Button Voltar;

    @FXML
    private TableColumn<Comentario, String> comentario;

    @FXML
    private TextArea comentarioArea;

    @FXML
    private TableView<Comentario> comentarioTable;

    @FXML
    private TableColumn<Comentario, Integer> idCliente;

    @FXML
    private TableColumn<Comentario, Integer> idTable;

    @FXML
    void VoltarClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) Voltar.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void comentarioTableClick(MouseEvent event) {

    }

}
