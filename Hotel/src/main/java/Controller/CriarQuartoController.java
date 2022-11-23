package Controller;

import Model.Quarto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CriarQuartoController {

    @FXML
    private Button BtnAddProduto;

    @FXML
    private AnchorPane PainelGestorProdutoAdicionar;

    @FXML
    private Button adicionarReservaBtn;

    @FXML
    private ImageView btnBack;

    @FXML
    private ComboBox<?> cmbTipoQuarto;

    @FXML
    private ComboBox<?> cmbWifi;

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
    private Label lblIdClientes;

    @FXML
    private Label lblIdQuarto;

    @FXML
    private Label lblSamos;

    @FXML
    private Label lblServExtra;

    @FXML
    private TableColumn<Quarto, Integer> tbl_id;

    @FXML
    private TableColumn<Quarto, Integer> tbl_piso;

    @FXML
    private TableColumn<Quarto, Double> tbl_preco;

    @FXML
    private TableColumn<Quarto, String> tbl_tipQuarto;

    @FXML
    private TableColumn<Quarto, Boolean> tbl_wifi;

    @FXML
    private TableView<Quarto> tv_Quarto;

    @FXML
    private TextField txt_piso;

    @FXML
    private TextField txt_preco;

    @FXML
    private Button voltarBtn;

    @FXML
    void addProduto(ActionEvent event) {

    }

    @FXML
    void clickAddQuarto(ActionEvent event) {

    }

    @FXML
    void clickVoltarBtn(ActionEvent event) {

    }

}
