package Controller;

import Model.RegistoCartao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class CartaoController {

    @FXML
    private Text cidadeTxt;

    @FXML
    private Text codigoPostalTxt;

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
    private TableColumn<RegistoCartao, LocalDate> tbl_data;

    @FXML
    private TableColumn<RegistoCartao, String> tbl_local;

    @FXML
    private TableColumn<RegistoCartao, String> tbl_numCartao;

    @FXML
    private TableView<?> tv_registosCartao;

    @FXML
    private Text urlText;

    @FXML
    private Button voltarBtn;

    @FXML
    void clickVoltarBtn(ActionEvent event) {

    }

}
