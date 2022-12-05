package Controller;

import Model.Produto;
import Model.Stock;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GestaoProduto implements Initializable {

    @FXML
    private Button btnVoltar;

    @FXML
    private Button consumivelBtn;

    @FXML
    private TableColumn<Produto, Boolean> consumivelTable;

    @FXML
    private TableColumn<Produto, String> descricaoTable;

    @FXML
    private TableColumn<Produto, String> idTable;

    @FXML
    private Button nConsumivelBtn;

    @FXML
    private TableColumn<Produto, Double> pesoTable;

    @FXML
    private TableColumn<Produto, Double> precoTable;

    @FXML
    private TableView<Produto> produtosTable;

    @FXML
    private Text quantidade;

    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor_GestaoStock.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Gestão Stock");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickConsumivelBtn(ActionEvent event) {

    }

    @FXML
    void clickNConsumivelBtn(ActionEvent event) {

    }

    private void initTable() {
        idTable.setCellValueFactory(new PropertyValueFactory<Produto, String>("id"));
        descricaoTable.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));
        precoTable.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoPorUnidade"));
        pesoTable.setCellValueFactory(new PropertyValueFactory<Produto, Double>("peso"));
        consumivelTable.setCellValueFactory(new PropertyValueFactory<Produto, Boolean>("consumivel"));
        produtosTable.setItems(Produto.getProduto());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}