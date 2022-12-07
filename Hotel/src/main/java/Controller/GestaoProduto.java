package Controller;

import BLL.DBconn;
import Model.MessageBoxes;
import Model.Produto;
import Model.Servico;
import Model.Stock;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoStock.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Gestão Stock");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickConsumivelBtn(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Produto selectedID = produtosTable.getSelectionModel().getSelectedItem();
            if (selectedID != null && selectedID.getConsumivel() == false) {
                ps2 = connection.prepareStatement("UPDATE Produto SET consumivel = ? WHERE id = ?");
                ps2.setBoolean(1, true);
                ps2.setString(2, selectedID.getIdProduto());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Produto passou a consumível", "Informação");
            } else {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Produto já é consumível", "Erro");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void clickNConsumivelBtn(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Produto selectedID = produtosTable.getSelectionModel().getSelectedItem();
            if (selectedID != null && selectedID.getConsumivel() == true) {
                ps2 = connection.prepareStatement("UPDATE Produto SET consumivel = ? WHERE id = ?");
                ps2.setBoolean(1, false);
                ps2.setString(2, selectedID.getIdProduto());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Produto passou a não consumível", "Informação");
            } else {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Produto já é não consumível", "Erro");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void clickTable(MouseEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            Produto selectedID = produtosTable.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("SELECT quantidade FROM Stock WHERE idProduto = ?");
                ps2.setString(1, selectedID.getIdProduto());
                for (int i = 0; i < Stock.getStock().size(); i++) {
                    if (selectedID != null && selectedID.getIdProduto().equals(Stock.getStock().get(i).getIdProduto())){
                        Integer quant = Stock.getStock().get(i).getQuantidade();
                        quantidade.setText(String.valueOf(quant));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void initTable() {
        idTable.setCellValueFactory(new PropertyValueFactory<Produto, String>("idProduto"));
        descricaoTable.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));
        precoTable.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoUnidade"));
        pesoTable.setCellValueFactory(new PropertyValueFactory<Produto, Double>("peso"));
        consumivelTable.setCellValueFactory(new PropertyValueFactory<Produto, Boolean>("consumivel"));
        produtosTable.setItems(Produto.getProduto());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}