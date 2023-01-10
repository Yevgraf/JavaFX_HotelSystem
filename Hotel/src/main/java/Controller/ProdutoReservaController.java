package Controller;

import BLL.ProdutoReservaBLL;
import BLL.StockBLL;
import DAL.ProdutoDAL;
import DAL.ProdutoReservaDAL;
import DAL.ReservaDAL;
import Model.*;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProdutoReservaController implements Initializable {

    @FXML
    private Button btnAddQuarto;

    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnRmvQuarto;

    @FXML
    private ComboBox<Produto> cmbProduto;

    @FXML
    private ComboBox<Reserva> cmbQuarto;

    @FXML
    private TableColumn<ProdutoReserva, Integer> tbl_id;
    @FXML
    private TableColumn<ProdutoReserva, String> tbl_idProduto;

    @FXML
    private TableColumn<ProdutoReserva, Integer> tbl_idReserva;

    @FXML
    private TableColumn<ProdutoReserva, Integer> tbl_quantidade;

    @FXML
    private TableView<ProdutoReserva> tv_ProdutoQuarto;

    @FXML
    private TextField txt_quantidade;

    @FXML
    private Button voltarBtn;

    private void initTable() {

        tbl_id.setResizable(false);
        tbl_idProduto.setResizable(false);
        tbl_idReserva.setResizable(false);
        tbl_quantidade.setResizable(false);

        tbl_id.setCellValueFactory(new PropertyValueFactory<ProdutoReserva, Integer>("id"));
        tbl_idReserva.setCellValueFactory(new PropertyValueFactory<ProdutoReserva, Integer>("idReserva"));
        tbl_idProduto.setCellValueFactory(new PropertyValueFactory<ProdutoReserva, String>("idProduto"));
        tbl_quantidade.setCellValueFactory(new PropertyValueFactory<ProdutoReserva, Integer>("quantidade"));


        tv_ProdutoQuarto.setItems(ProdutoReservaDAL.getProdutoReserva());
    }

    private void initCombos() {
        cmbQuarto.getItems().addAll(ReservaDAL.getReservasPendentes());
        cmbProduto.getItems().addAll(ProdutoDAL.getAllProdutos());
    }

    @FXML
    void clickAddProdutoQuarto(ActionEvent event) throws SQLException {
        StockBLL sBLL = new StockBLL();
        Reserva selectedReservation = cmbQuarto.getValue();
        Produto selectedProduct = cmbProduto.getValue();
        int quantity = Integer.parseInt(txt_quantidade.getText());
        if (selectedReservation != null && selectedProduct != null){
            if (sBLL.verificaSeProdutoTemQuantidadeSuficiente(selectedProduct.getIdProduto(), quantity)) {
                ProdutoReservaBLL.addProductInRoom(selectedReservation.getId(), selectedProduct.getIdProduto(), quantity);
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Produto adicionado.", "Sucesso");
                initTable();
            } else {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "A quantidade desejada é superior à existente em stock!", "Erro:");
            }
        } else {
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Preencha todos os campos!", "Aviso:");
        }
    }


    @FXML
    void clickEditar(ActionEvent event) {

    }

    @FXML
    void clickRmvProdutoQuarto(ActionEvent event) {
        ProdutoReserva selectedProduct = tv_ProdutoQuarto.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            ProdutoReservaBLL.deleteProductFromRoom(selectedProduct);
            initTable();
        }
    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Gestao Reservas");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initCombos();

    }
}
