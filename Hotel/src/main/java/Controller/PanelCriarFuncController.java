package Controller;

import BLL.DBconn;
import Model.Colaborador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.DatePicker;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import static Model.Colaborador.getColaborador;

public class PanelCriarFuncController implements Initializable {

    @FXML
    private AnchorPane PainelGestorProdutoAdicionar;

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
    private Button btn_add_funcionario;

    @FXML
    private Button btn_remove_funcionario;

    @FXML
    private Button btn_update_funcionario;

    @FXML
    private ComboBox<?> cmb_tipocolaborador;

    @FXML
    private ImageView imgGestorAdionarProd;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private Label lblGestaoProdutos;

    @FXML
    private Label lblHoras1;

    @FXML
    private Label lblHoras11;

    @FXML
    private Label lblHoras111;

    @FXML
    private Label lblHoras12;

    @FXML
    private Label lblHoras121;

    @FXML
    private Label lblHoras1211;

    @FXML
    private Label lblHoras13;

    @FXML
    private Label lblHoras131;

    @FXML
    private Label lblHoras1311;

    @FXML
    private Label lblHoras13111;

    @FXML
    private Label lblHoras131111;

    @FXML
    private Label lblSamos;

    @FXML
    private TableColumn<Colaborador, String> tbl_contacto;

    @FXML
    private TableColumn<Colaborador, Date> tbl_dataNasc;

    @FXML
    private TableColumn<Colaborador, String> tbl_email;

    @FXML
    private TableColumn<Colaborador, Integer> tbl_id;

    @FXML
    private TableColumn<Colaborador, Integer> tbl_idCartao;

    @FXML
    private TableColumn<Colaborador, String> tbl_morada;

    @FXML
    private TableColumn<Colaborador, String> tbl_name;

    @FXML
    private TableColumn<Colaborador, String> tbl_nif;

    @FXML
    private TableColumn<Colaborador, String> tbl_password;

    @FXML
    private TableColumn<Colaborador, String> tbl_tipoColaborador;

    @FXML
    private TableColumn<Colaborador, String> tbl_utilizador;

    @FXML
    private TableView<Colaborador> tv_funcionarios;

    @FXML
    private TextField txt_contacto;


    @FXML
    private DatePicker datePickerNasc;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_idCartao;

    @FXML
    private TextField txt_morada;

    @FXML
    private TextField txt_nif;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_utilizador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();


    }

    @FXML
    void actionAddFuncionario(ActionEvent event) {
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(Integer.parseInt(txt_id.getText()));
        colaborador.setNome(txt_nome.getText());
        colaborador.setNif(txt_nif.getText());
        colaborador.setMorada(txt_morada.getText());
        colaborador.setEmail(txt_email.getText());
        colaborador.setContacto(txt_contacto.getText());
        colaborador.setUtilizador(txt_utilizador.getText());
        colaborador.setPassword(tbl_password.getText());
        colaborador.setTipoColaborador(cmb_tipocolaborador.getPromptText());
        colaborador.setIdCartao(Integer.parseInt(txt_idCartao.getText()));
        txt_id.clear();
        txt_nome.clear();
        txt_nif.clear();
        txt_morada.clear();
        txt_email.clear();
        txt_contacto.clear();
        txt_utilizador.clear();
        txt_password.clear();
        txt_idCartao.clear();


    }

    @FXML
    void actionRemoveFuncionario(ActionEvent event) {

    }

    @FXML
    void actionUpdateFuncionario(ActionEvent event) {

    }

    private void initTable() {
        tbl_id.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_name.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_email.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_dataNasc.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_morada.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_contacto.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_nif.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_password.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_tipoColaborador.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_utilizador.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_idCartao.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));

        tbl_id.setResizable(false);
        tbl_name.setResizable(false);
        tbl_email.setResizable(false);
        tbl_dataNasc.setResizable(false);
        tbl_morada.setResizable(false);
        tbl_contacto.setResizable(false);
        tbl_nif.setResizable(false);
        tbl_password.setResizable(false);
        tbl_tipoColaborador.setResizable(false);
        tbl_utilizador.setResizable(false);
        tbl_idCartao.setResizable(false);

    }

    public void showColaboradores() {
        ObservableList<Colaborador> listaColaborador = getColaborador();
        tbl_id.setCellValueFactory(new PropertyValueFactory<Colaborador, Integer>("idColaborador"));
        tbl_name.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("nome"));
        tbl_email.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("email"));
        tbl_dataNasc.setCellValueFactory(new PropertyValueFactory<Colaborador, Date>("dataNascimento"));
        tbl_morada.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("morada"));
        tbl_contacto.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("contacto"));
        tbl_nif.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("nif"));
        tbl_password.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("password"));
        tbl_tipoColaborador.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("tipoColaborador"));
        tbl_utilizador.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("utilizador"));
        tbl_idCartao.setCellValueFactory(new PropertyValueFactory<Colaborador, Integer>("idCartao"));
        tv_funcionarios.setItems(listaColaborador);
    }


}