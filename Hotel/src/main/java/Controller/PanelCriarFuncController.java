package Controller;

import Model.Colaborador;
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
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

import BLL.DBconn;
import javafx.stage.Stage;


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
    private Button btn_update_Voltar;

    @FXML
    private Button btn_add_funcionario;

    @FXML
    private Button btn_remove_funcionario;

    @FXML
    private Button btn_update_funcionario;

    @FXML
    private ComboBox<Colaborador> cmb_tipocolaborador;

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

    @FXML
    private Button VoltarBtn;

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) VoltarBtn.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();


    }

    private void initTable() {

        tbl_name.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_email.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_dataNasc.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_morada.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_contacto.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_nif.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_password.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_tipoColaborador.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        tbl_utilizador.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));
        //tbl_idCartao.prefWidthProperty().bind(tv_funcionarios.widthProperty().multiply(0.11));

        tbl_name.setResizable(false);
        tbl_email.setResizable(false);
        tbl_dataNasc.setResizable(false);
        tbl_morada.setResizable(false);
        tbl_contacto.setResizable(false);
        tbl_nif.setResizable(false);
        tbl_password.setResizable(false);
        tbl_tipoColaborador.setResizable(false);
        tbl_utilizador.setResizable(false);
        //tbl_idCartao.setResizable(false);

        tbl_name.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("nome"));
        tbl_email.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("email"));
        tbl_dataNasc.setCellValueFactory(new PropertyValueFactory<Colaborador, Date>("dataNascimento"));
        tbl_morada.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("morada"));
        tbl_contacto.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("contacto"));
        tbl_nif.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("nif"));
        tbl_password.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("password"));
        tbl_tipoColaborador.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("tipoColaborador"));
        tbl_utilizador.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("utilizador"));
        //tbl_idCartao.setCellValueFactory(new PropertyValueFactory<Colaborador, Integer>("idCartao"));
        tv_funcionarios.setItems(Colaborador.getColaborador());
    }


    public void onActionAddFuncionario(javafx.event.ActionEvent actionEvent) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Colaborador( nome, nif, morada, email, contacto, utilizador, palavrapasse, tipoColaborador) VALUES (?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_nome.getText());
            ps2.setString(2, txt_nif.getText());
            ps2.setString(3, txt_morada.getText());
            //ps2.setDate(5,  datePickerNasc.getPromptText());
            ps2.setString(4, txt_email.getText());
            ps2.setString(5, txt_contacto.getText());
            ps2.setString(6, txt_utilizador.getText());
            ps2.setString(7, txt_password.getText());
            ps2.setString(8, "funcionario");
            //  ps2.setString(9, txt_idCartao.getText());
            ps2.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void onActionRemoveFuncionario(ActionEvent actionEvent) {
    }

    public void onActionUpdateFuncionario(ActionEvent actionEvent) {
    }

    public void onActionVoltar(ActionEvent actionEvent) {
    }

    private void initCombos() {

        ObservableList<Colaborador> oblColab = FXCollections.observableArrayList(Colaborador.getColaborador());
        cmb_tipocolaborador.getItems().add((Colaborador) oblColab);
    }
}