package Controller;

import BLL.DBconn;
import Model.Colaborador;
import Model.MessageBoxes;
import Model.Produto;
import Model.Stock;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    @FXML
    private AnchorPane PainelGestorStock;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private Button btnGestorEntradaStock;

    @FXML
    private Button btnGestorProdutosStock;

    @FXML
    private Button btnGestorSaidaStock;

    @FXML
    private TableView<Stock> tblStock;

    @FXML
    private TableColumn<Stock, String> idProdutoTable;

    @FXML
    private TableColumn<Stock, Integer> quantidadeTable;

    @FXML
    private TableColumn<Stock, Boolean> consumivel;

    @FXML
    private Button btnVoltar;

    @FXML
    private Text descricaoTxt;

    @FXML
    private Button consumivelBtn;

    @FXML
    private Button naoConsumivelBtn;

    @FXML
    void clickConsumivelBtn(ActionEvent event) {

      //  PreparedStatement ps2;
      //  try {
      //      DBconn dbConn = new DBconn();
      //      Connection connection = dbConn.getConn();
//
      //      Stock selectedID = tblStock.getSelectionModel().getSelectedItem();
      //      if (selectedID != null && selectedID.getConsumivel() == false) {
      //          ps2 = connection.prepareStatement("UPDATE Stock SET consumivel WHERE consumivel = ?");
      //          ps2.setBoolean(1, selectedID.getConsumivel() = true);
      //          ps2.executeUpdate();
      //          MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Produto passou a consumível", "Informação");
      //      } else {
      //          MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Produto já é consumível", "Erro!");
      //      }
//
      //  } catch (SQLException ex) {
      //      throw new RuntimeException(ex);
      //  }
    }

    @FXML
    void clickNaoConsumivelBtn(ActionEvent event) {

    }

    @FXML
    void clickMostrarDescricao(MouseEvent event) {
        //  PreparedStatement ps2;
        //  try {
        //      DBconn dbConn = new DBconn();
        //      Connection connection = dbConn.getConn();

        //      Stock selectedID = tblStock.getSelectionModel().getSelectedItem();
        //      if (selectedID != null){
        //          ps2 = connection.prepareStatement("SELECT FROM Produto WHERE id = ?");
        //          ps2.setString(1, selectedID.getIdProduto());
        //          ps2.executeUpdate();
        //
        //      }

        //  } catch (SQLException ex) {
        //      throw new RuntimeException(ex);
        //  }
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

    private void initTable() {
        idProdutoTable.setResizable(false);
        quantidadeTable.setResizable(false);

        idProdutoTable.setCellValueFactory(new PropertyValueFactory<Stock, String>("idProduto"));
        quantidadeTable.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantidade"));
        consumivel.setCellValueFactory(new PropertyValueFactory<Stock, Boolean>("consumivel"));
        tblStock.setItems(Stock.getStock());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }

}
