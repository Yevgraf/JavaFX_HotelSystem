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
    private TableView<Stock> tblStock;

    @FXML
    private TableColumn<Stock, String> idProdutoTable;

    @FXML
    private TableColumn<Stock, Integer> quantidadeTable;

    @FXML
    private Button btnVoltar;

    @FXML
    private Text descricaoTxt;

    @FXML
    void clickTable(MouseEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            Stock selectedID = tblStock.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("SELECT descricao FROM Produto WHERE id = ?");
                ps2.setString(1, selectedID.getIdProduto());
                for (int i = 0; i < Produto.getProduto().size(); i++) {
                    if (selectedID != null && selectedID.getIdProduto().equals(Produto.getProduto().get(i).getIdProduto())){
                        String descricao = Produto.getProduto().get(i).getDescricao();
                        descricaoTxt.setText(descricao);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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

    @FXML
    void clickgestaoProdBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoProdutos.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Gestão de Produtos");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    private void initTable() {
        idProdutoTable.setResizable(false);
        quantidadeTable.setResizable(false);

        idProdutoTable.setCellValueFactory(new PropertyValueFactory<Stock, String>("idProduto"));
        quantidadeTable.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantidade"));
        tblStock.setItems(Stock.getStock());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }

}
