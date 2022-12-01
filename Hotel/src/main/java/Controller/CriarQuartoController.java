package Controller;

import BLL.DBconn;
import Model.Colaborador;
import Model.MessageBoxes;
import Model.Quarto;
import Model.TipoQuarto;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.bouncycastle.crypto.agreement.srp.SRP6Client;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

public class CriarQuartoController implements Initializable {

    @FXML
    private Button BtnAddProduto;

    @FXML
    private AnchorPane CriarQuarto;

    @FXML
    private Button btnAddQuarto;

    @FXML
    private Button btnAddTipoQuarto;

    @FXML
    private ImageView btnBack;

    @FXML
    private ComboBox<String > cmbTipoQuarto;


    @FXML
    private CheckBox checkboxWifi;

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
    private TableColumn<Quarto, String > tbl_tipQuarto;

    @FXML
    private TableColumn<Quarto, Boolean> tbl_wifi;

    @FXML
    private TableView<Quarto> tv_Quarto;

    @FXML
    private TextField txt_numcartao;

    @FXML
    private TextField txt_piso;

    @FXML
    private TextField txt_preco;

    @FXML
    private Button btnRmvQuarto;

    @FXML
    private Button btnEditar;
    @FXML
    private Button voltarBtn;

    @FXML
    void AddTipoQuartoOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelAddTipoQuarto.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnAddTipoQuarto.getScene().getWindow();
        stage.setTitle("Adicionar Tipo de quarto");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void addProduto(ActionEvent event) {

    }

    @FXML
    void clickAddQuarto(ActionEvent event) {
        PreparedStatement ps2;
        PreparedStatement ps3;

        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Quarto (tipoQuarto,piso,wifi,preco,numeroCartao, ativo) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps3 = connection.prepareStatement("INSERT INTO Cartao (numeroCartao) VALUES (?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps3.setString(1,(txt_numcartao.getText()));
            ps2.setString(1, cmbTipoQuarto.getValue());
            ps2.setInt(2, Integer.parseInt(txt_piso.getText()));
            if (checkboxWifi.isSelected()) {
                checkboxWifi.setSelected(true);
            } else {
                checkboxWifi.setSelected(false);
            }
            ps2.setBoolean(3, checkboxWifi.isSelected());
            ps2.setDouble(4, Double.parseDouble(txt_preco.getText()));
            ps2.setDouble(5, Double.parseDouble(txt_numcartao.getText()));
            ps3.setString(1,(txt_numcartao.getText()));
            ps2.setBoolean(6,false);

            ps3.executeUpdate();
            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Tipo de Quarto inserido", "Informação Tipo de quarto");

        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(ex);

        }

    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Adicionar Tipo de quarto");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();

    }


    private void initTable() {

        tbl_id.setResizable(false);
        tbl_tipQuarto.setResizable(false);
        tbl_piso.setResizable(false);
        tbl_wifi.setResizable(false);
        tbl_preco.setResizable(false);

        tbl_id.setCellValueFactory(new PropertyValueFactory<Quarto, Integer>("id"));
        tbl_tipQuarto.setCellValueFactory(new PropertyValueFactory<Quarto, String>("tipoQuarto"));
        tbl_piso.setCellValueFactory(new PropertyValueFactory<Quarto, Integer>("piso"));
        tbl_wifi.setCellValueFactory(new PropertyValueFactory<Quarto, Boolean>("wifi"));
        tbl_preco.setCellValueFactory(new PropertyValueFactory<Quarto, Double>("preco"));

        tv_Quarto.setItems(Quarto.getQuarto());
    }

    private void initCombos() {
        cmbTipoQuarto.getItems().add("Singular");
        cmbTipoQuarto.getItems().add("Duplo");
        cmbTipoQuarto.getItems().add("Familiar");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initCombos();


    }


    public void clickEditar(ActionEvent actionEvent) {


        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Quarto selectedID = tv_Quarto.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("UPDATE FROM Quarto WHERE id = ?");
                ps2.setString(1, cmbTipoQuarto.getValue());
                ps2.setInt(2, Integer.parseInt(txt_piso.getText()));
                if (checkboxWifi.isSelected()) {
                    checkboxWifi.setSelected(true);
                } else {
                    checkboxWifi.setSelected(false);
                }
                ps2.setBoolean(3, checkboxWifi.isSelected());
                ps2.setDouble(4, Double.parseDouble(txt_preco.getText()));

                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Quarto alterado", "Information");

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clickRmvQuarto(ActionEvent actionEvent) {

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Quarto selectedID = tv_Quarto.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Quarto WHERE id = ?");
                ps2.setInt(1, selectedID.getId());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Quarto Removido", "Information");

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}