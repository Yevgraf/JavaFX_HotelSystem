package Controller;

import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdicionarReserva {

    @FXML
    private AnchorPane PainelGestorProdutoAdicionar;

    @FXML
    private Button addReservaBrn;

    @FXML
    private Button addServicoBtn;

    @FXML
    private ImageView btnBack;

    @FXML
    private ComboBox<?> cmbIDCartao;

    @FXML
    private ComboBox<?> cmbIDCliente;

    @FXML
    private ComboBox<?> cmbIDQuarto;

    @FXML
    private DatePicker dataInicio;

    @FXML
    private TableColumn<?, ?> descricaoServicoTable;

    @FXML
    private TableColumn<?, ?> idServicoTable;

    @FXML
    private ImageView imgGestorAdionarProd;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private Label lblData;

    @FXML
    private Label lblDataFim;

    @FXML
    private Label lblDataIniicio;

    @FXML
    private Label lblGestaoProdutoAdicionar;

    @FXML
    private Label lblGestaoReservas;

    @FXML
    private Label lblHoras;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblIdCartao;

    @FXML
    private Label lblIdClientes;

    @FXML
    private Label lblIdQuarto;

    @FXML
    private Label lblPreço;

    @FXML
    private Label lblSamos;

    @FXML
    private Label lblServExtra;

    @FXML
    private TableColumn<?, ?> precoServicoTable;

    @FXML
    private TableView<?> servicosTable;

    @FXML
    private TextField txtPreco;

    @FXML
    private Button voltarBtn;

    @FXML
    void clickAddReservaBrn(ActionEvent event) {

    }

    @FXML
    void clickAddServicoBtn(ActionEvent event) {

    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario_GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void dataFim(ActionEvent event) {

    }

}
