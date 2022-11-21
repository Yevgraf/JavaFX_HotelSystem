package Controller;

import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GestaoReservas {

    @FXML
    private AnchorPane PainelGestorCliente;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private Button btnGestorAdicionarCliente;

    @FXML
    private Button btnGestorEliminarCliente;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private ImageView btnMinimizateApp;

    @FXML
    private Button btnVoltar;

    @FXML
    private ImageView imgGestorEliminarProduto;

    @FXML
    private ImageView imgGestorEntradaStock2;

    @FXML
    private ImageView imgGestorGestaoReserva;

    @FXML
    private Label lblData;

    @FXML
    private Label lblGestaoReserva;

    @FXML
    private Label lblHoras;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblSamos;

    @FXML
    private TableColumn<?, ?> tblCoIDCartao;

    @FXML
    private TableColumn<?, ?> tblCoIDColab;

    @FXML
    private TableColumn<?, ?> tblCoIDQuarto;

    @FXML
    private TableColumn<?, ?> tblCoPreco;

    @FXML
    private TableColumn<?, ?> tblColDReserva;

    @FXML
    private TableColumn<?, ?> tblColDataFim;

    @FXML
    private TableColumn<?, ?> tblColDataIni;

    @FXML
    private TableColumn<?, ?> tblColIDCliente;

    @FXML
    private TableColumn<?, ?> tblColServEx;

    @FXML
    private TableView<?> tblReservas;

    @FXML
    void clickBtnGestorAdicionarCliente(ActionEvent event) {

    }

    @FXML
    void clickBtnGestorEliminarCliente(ActionEvent event) {

    }

    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

}