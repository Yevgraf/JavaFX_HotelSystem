package Controller;

import BLL.DBconn;
import Model.Cartao;
import Model.Colaborador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PanelCriarCartao implements Initializable {

    @FXML
    private AnchorPane PainelCartao;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private ImageView btnMinimizateApp;

    @FXML
    private Button btn_add_Cartao;

    @FXML
    private Button btn_remove_Cartao;

    @FXML
    private Button btn_update_Cartao;

    @FXML
    private Button btn_update_Voltar;

    @FXML
    private ComboBox<?> cmb_CartaoMestre;

    @FXML
    private ImageView imgGestorAdionarProd;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private Label lblGestaoProdutos;

    @FXML
    private Label lblSamos;

    @FXML
    private TableColumn<Cartao, Boolean> tbl_cartaoMestre;

    @FXML
    private TableColumn<Cartao, String> tbl_numCartao;

    @FXML
    private TableView<Cartao> tv_cartao;

    @FXML
    private TextField txt_numCartao;

    @FXML
    void onActionAddCartao(ActionEvent event) {

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Cartao(numCartao, cartaoMestre) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_numCartao.getText());
            ps2.setBoolean(2, Boolean.parseBoolean(cmb_CartaoMestre.getPromptText()));
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    void onActionCmbCartaoMestre(ActionEvent event) {

    }

    @FXML
    void onActionRemoveCartao(ActionEvent event) {

    }

    @FXML
    void onActionUpdateCartao(ActionEvent event) {

    }

    @FXML
    void onActionVoltar(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initCombos();



    }

    private void initTable() {
        tbl_numCartao.prefWidthProperty().bind(tv_cartao.widthProperty().multiply(0.11));
        tbl_cartaoMestre.prefWidthProperty().bind(tv_cartao.widthProperty().multiply(0.11));

        tbl_numCartao.setResizable(false);
        tbl_cartaoMestre.setResizable(false);


        tbl_numCartao.setCellValueFactory(new PropertyValueFactory<Cartao, String>("numCartao"));
        tbl_cartaoMestre.setCellValueFactory(new PropertyValueFactory<Cartao, Boolean>("cartaoMestre"));
        tv_cartao.setItems(Cartao.getCartao());
    }
    private void initCombos() {

        ObservableList<Cartao> oblCartao = FXCollections.observableArrayList(Cartao.getCartao());
        //cmb_CartaoMestre.getItems().add((Cartao) oblCartao);
    }

}
