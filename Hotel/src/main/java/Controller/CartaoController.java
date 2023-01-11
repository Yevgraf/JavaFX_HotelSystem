package Controller;

import BLL.RegistoBLL;
import Model.Cartao;
import Model.Registo;
import Model.Servico;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CartaoController implements Initializable {

    @FXML
    private Text cidadeTxt;

    @FXML
    private Text dataTxt;

    @FXML
    private Text idFornecedorTxt;

    @FXML
    private Text moradaTxt;

    @FXML
    private Text noneFornecedorTxt;

    @FXML
    private Text ordemTxt;


    @FXML
    private ComboBox<Cartao> cmbIdCartao;

    @FXML
    private ComboBox<Servico> cmbLocal;

    @FXML
    private TableColumn<Registo, java.sql.Date> tbl_data;

    @FXML
    private TableColumn<Registo, Integer> tbl_id;

    @FXML
    private TableColumn<Registo, Integer> tbl_idCartao;

    @FXML
    private TableColumn<Registo, Integer> tbl_idCliente;

    @FXML
    private TableColumn<Registo, String> tbl_local;

    @FXML
    private TableView<Registo> tv_registosCartao;

    @FXML
    private Text urlText;

    @FXML
    private Button voltarBtn;

    @FXML
    void clickVoltarBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initCombos();
    }

    private void initCombos() {

    }

    private void initTable() {

        RegistoBLL registoBLL = new RegistoBLL();
        try {
            ObservableList<Registo> registos = registoBLL.getAllRegistos();

            tbl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tbl_idCartao.setCellValueFactory(new PropertyValueFactory<>("idCartao"));
            tbl_idCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
            tbl_local.setCellValueFactory(new PropertyValueFactory<>("Local"));
            tbl_data.setCellValueFactory(new PropertyValueFactory<>("Data"));
            tv_registosCartao.setItems(registos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void filterIdCartao(ActionEvent event) {

    }

    @FXML
    void filterLocal(ActionEvent event) {

    }
}