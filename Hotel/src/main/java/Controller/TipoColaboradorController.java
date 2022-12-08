package Controller;

import BLL.DBconn;
import Model.MessageBoxes;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoColaboradorController {

    @FXML
    private AnchorPane PainelAddTipoColaborador;

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
    private Button btn_adicionar;

    @FXML
    private Button btn_remover;

    @FXML
    private Button btn_voltar;

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
    private TableColumn<Model.TipoColaborador, Integer> tbl_Id;

    @FXML
    private TableColumn<Model.TipoColaborador, String> tbl_tipo;

    @FXML
    private TableView<Model.TipoColaborador> tv_TipoColaborador;

    @FXML
    private TextField txt_tipo;

    @FXML
    void btnAdicionarAction(ActionEvent event) {
        PreparedStatement ps2;

        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO TipoColaborador(tipo ) VALUES (?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_tipo.getText());

            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Tipo de Colaborador inserido", "Informação Tipo de colaborador");


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

            Model.TipoColaborador selectedID = tv_TipoColaborador.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM TipoColaborador WHERE id = ?");
                ps2.setInt(1, selectedID.getId());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Tipo de Colaborador Removido", "Information");

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @FXML
    void btnVoltarAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarFuncionarioController.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btn_voltar.getScene().getWindow();
        stage.setTitle("criar funcionario");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();


    }

    private void initTable() {

        tbl_Id.setResizable(false);
        tbl_tipo.setResizable(false);


        tbl_Id.setCellValueFactory(new PropertyValueFactory<Model.TipoColaborador, Integer>("id"));
        tbl_tipo.setCellValueFactory(new PropertyValueFactory<Model.TipoColaborador, String>("tipo"));

        tv_TipoColaborador.setItems(Model.TipoColaborador.getTipoColaborador());
    }

}
