package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PanelCriarCartao {

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
    private TableColumn<?, ?> tbl_id;

    @FXML
    private TableColumn<?, ?> tbl_idCartao;

    @FXML
    private TableColumn<?, ?> tbl_tipoColaborador;

    @FXML
    private TableView<?> tv_funcionarios;

    @FXML
    private TextField txt_idCartao;

    @FXML
    private TextField txt_numCartao;

    @FXML
    void onActionAddCartao(ActionEvent event) {

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

}