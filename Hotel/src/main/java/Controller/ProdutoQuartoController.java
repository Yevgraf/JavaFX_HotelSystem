package Controller;

import DAL.DBconn;
import DAL.ProdutoDAL;
import DAL.ProdutoQuartoDAL;
import DAL.QuartoDAL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProdutoQuartoController implements Initializable {

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
    private ComboBox<Quarto> cmbQuarto;

    @FXML
    private TableColumn<Model.ProdutoQuarto, Integer> tbl_id;
    @FXML
    private TableColumn<Model.ProdutoQuarto, String> tbl_idProduto;

    @FXML
    private TableColumn<Model.ProdutoQuarto, Integer> tbl_idQuarto;

    @FXML
    private TableColumn<Model.ProdutoQuarto, Integer> tbl_quantidade;

    @FXML
    private TableView<Model.ProdutoQuarto> tv_ProdutoQuarto;

    @FXML
    private TextField txt_quantidade;

    @FXML
    private Button voltarBtn;

    private void initTable() {

        tbl_id.setResizable(false);
        tbl_idProduto.setResizable(false);
        tbl_idQuarto.setResizable(false);
        tbl_quantidade.setResizable(false);

        tbl_id.setCellValueFactory(new PropertyValueFactory<Model.ProdutoQuarto, Integer>("id"));
        tbl_idQuarto.setCellValueFactory(new PropertyValueFactory<Model.ProdutoQuarto, Integer>("idQuarto"));
        tbl_idProduto.setCellValueFactory(new PropertyValueFactory<Model.ProdutoQuarto, String>("idProduto"));
        tbl_quantidade.setCellValueFactory(new PropertyValueFactory<Model.ProdutoQuarto, Integer>("quantidade"));


        tv_ProdutoQuarto.setItems(Model.ProdutoQuarto.getProdutoQuarto());
    }
    private void initCombos() {
        cmbQuarto.getItems().addAll(QuartoDAL.getAllQuartos());
        cmbProduto.getItems().addAll(ProdutoDAL.getAllProdutos());
    }

    @FXML
    void clickAddProdutoQuarto(ActionEvent event) throws SQLException {
        Quarto selectedRoom = cmbQuarto.getValue();
        Produto selectedProduct = cmbProduto.getValue();
        int quantity = Integer.parseInt(txt_quantidade.getText());
        if (selectedRoom == null || selectedProduct == null) {
            // show error message
            return;
        }
        ProdutoQuartoDAL.addProductInRoom(selectedRoom.getId(), selectedProduct.getIdProduto(), quantity);
    }



    @FXML
    void clickEditar(ActionEvent event) {

    }

    @FXML
    void clickRmvProdutoQuarto(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Model.ProdutoQuarto selectedID = tv_ProdutoQuarto.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM ProdutoQuarto WHERE id =?");

                ps2.setInt(1, selectedID.getId());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Produto removido", "Information");

            }
        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Reserva existente com estes dados","Reserva existente");
            throw new RuntimeException(ex);

        }

    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarQuartoController.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Adicionar Tipo de quarto");
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
