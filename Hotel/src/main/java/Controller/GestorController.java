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
    private Button btnEntradaStock;

    @FXML
    private Button btnGestorCheck;

    @FXML
    private Button btnGestorCliente;

    @FXML
    private Button btnGestorColab;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private Button btngestorQuarto;

    @FXML
    private Button btngestorReservas;

    @FXML
    private Button btngestorServico;

    @FXML
    private Button gestaoStockBtn;

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
    private ImageView imgGestorStock2;

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
    private Button getBtngestorServicon;

    @FXML
    void clickBtngestorServico(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoServicos.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorColab.getScene().getWindow();
        stage.setTitle("Gerir Servicos");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickGestaoColabBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarUtilizador.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorColab.getScene().getWindow();
        stage.setTitle("Criar FuncionarioController");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickGestaoClienteBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoUtilizadores.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorCliente.getScene().getWindow();
        stage.setTitle("Gerir Clientes");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickEntradaStockBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CarregarXML.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorColab.getScene().getWindow();
        stage.setTitle("Entrada StockController");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickGestaoStock(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoStock.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorColab.getScene().getWindow();
        stage.setTitle("Entrada StockController");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();

    }

    @FXML
    void clickBtngestorReservas(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorColab.getScene().getWindow();
        stage.setTitle("Gerir Reservas");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void btnGestaoQuartosOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarQuarto.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btngestorQuarto.getScene().getWindow();
        stage.setTitle("Criar Quarto");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }


}
