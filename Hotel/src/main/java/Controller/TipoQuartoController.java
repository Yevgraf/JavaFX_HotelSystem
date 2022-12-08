package Controller;

import BLL.DBconn;
import Model.MessageBoxes;
import com.example.hotel.Main;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TipoQuartoController implements Initializable {

    @FXML
    private AnchorPane PainelAddTipoQuarto;

    @FXML
    private ImageView btnBack;


    @FXML
    private Button btn_voltar;


    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private ImageView btnMinimizateApp;

    @FXML
    private Button btn_adicionar;

    @FXML
    private Button btn_remover;

    @FXML
    private CheckBox checkBox_Vista;

    @FXML
    private ImageView imgGestorAdionarProd;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private Label lblData;

    @FXML
    private Label lblGestaoClientes;

    @FXML
    private Label lblGestaoProdutoAdicionar;

    @FXML
    private Label lblHoras;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblSamos;

    @FXML
    private TableColumn<Model.TipoQuarto, Integer> tbl_Id;

    @FXML
    private TableColumn<Model.TipoQuarto, String > tbl_tipo;

    @FXML
    private TableColumn<Model.TipoQuarto, Boolean> tbl_vista;

    @FXML
    private TableView<Model.TipoQuarto> tv_TipoQuarto;

    @FXML
    private TextField txt_tipo;

    @FXML
    void btnAdicionarAction(ActionEvent event) {
        PreparedStatement ps2;

        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO TipoQuarto(tipo,vista ) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_tipo.getText());
            if (checkBox_Vista.isSelected()){
                checkBox_Vista.setSelected(true);
            }else{
                checkBox_Vista.setSelected(false);
            }
            ps2.setBoolean(2,checkBox_Vista.isSelected());

            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Tipo de Quarto inserido", "Informação Tipo de quarto");


        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(ex);

        }
    }

    @FXML
    void btnRemoverAction(ActionEvent event) {

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Model.TipoQuarto selectedID = tv_TipoQuarto.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM TipoQuarto WHERE id = ?");
                ps2.setInt(1, selectedID.getIdTipoQuarto());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Tipo de Quarto Removido", "Information");

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void initTable() {

        tbl_Id.setResizable(false);
        tbl_tipo.setResizable(false);
        tbl_vista.setResizable(false);


        tbl_Id.setCellValueFactory(new PropertyValueFactory<Model.TipoQuarto, Integer>("idTipoQuarto"));
        tbl_tipo.setCellValueFactory(new PropertyValueFactory<Model.TipoQuarto, String>("tipo"));
        tbl_vista.setCellValueFactory(new PropertyValueFactory<Model.TipoQuarto, Boolean>("vista"));

        tv_TipoQuarto.setItems(Model.TipoQuarto.getTipoQuarto());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }


    public void btnVoltarAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btn_voltar.getScene().getWindow();
        stage.setTitle("Pagina GestorController");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }
}
