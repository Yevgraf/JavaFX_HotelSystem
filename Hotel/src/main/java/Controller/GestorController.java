package Controller;

import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GestorController {

    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnCloseApp1;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private Button btnGestorCheck;

    @FXML
    private Button btnGestorCliente;

    @FXML
    private Button btnGestorColab;

    @FXML
    private Button btnGestorStock;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private Button btngestorQuarto;

    @FXML
    private Button btngestorReservas;

    @FXML
    private Button btngestorServico;

    @FXML
    private Button btngestorSugs;

    @FXML
    private ImageView imgGestorCheck;

    @FXML
    private ImageView imgGestorClientes;

    @FXML
    private ImageView imgGestorColab;

    @FXML
    private ImageView imgGestorReservas;

    @FXML
    private ImageView imgGestorReservas1;

    @FXML
    private ImageView imgGestorServico;

    @FXML
    private ImageView imgGestorStock;

    @FXML
    private ImageView imgGestorSugs;

    @FXML
    private Label lblData;

    @FXML
    private Label lblHoras;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblSamos;

    @FXML
    private AnchorPane roomservices;

    @FXML
    void clickGestaoColabBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCriarFuncionario.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorColab.getScene().getWindow();
        stage.setTitle("Criar Funcionario");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();

    }

    @FXML
    void clickGestaoStockBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CarregarXML.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorColab.getScene().getWindow();
        stage.setTitle("Entrada Stock");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();

    }

}
