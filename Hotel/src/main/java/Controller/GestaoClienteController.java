package Controller;

import BLL.DBconn;
import Model.Cliente;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestaoClienteController implements Initializable {

    @FXML
    private AnchorPane PainelGestorCliente;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private Button btnGestorAdicionarCliente;

    @FXML
    private Button btnGestorEditarCliente;

    @FXML
    private Button btnGestorEliminarCliente;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private ImageView btnMinimizateApp;

    @FXML
    private Button btnVoltar;

    @FXML
    private ImageView imgGestorEditarProduto;

    @FXML
    private ImageView imgGestorEliminarProduto;

    @FXML
    private ImageView imgGestorEntradaStock2;

    @FXML
    private ImageView imgGestorGestaoCliente;

    @FXML
    private Label lblData;

    @FXML
    private Label lblGestaoClientes;

    @FXML
    private Label lblHoras;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblSamos;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, String > tblColContacto;

    @FXML
    private TableColumn<Cliente, String > tblColEmail;

    @FXML
    private TableColumn<Cliente, Integer> tblColNif;

    @FXML
    private TableColumn<Cliente, String> tblColNomeCliente;

    @FXML
    private TableColumn<Cliente, String > tblColPassword;

    @FXML
    private TableColumn<Cliente, String> tblColUtilizador;

    @FXML
    void clickAddClienteBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarClienteController.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestorAdicionarCliente.getScene().getWindow();
        stage.setTitle("Adicionar Cliente");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Pagina GestorController");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void RemoveCliente(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Cliente selectedID = (Cliente) tblClientes.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Cliente WHERE nif = ?");
                ps2.setInt(1, selectedID.getNif());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Cliente Removido", "Information");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void initTable()  {

        tblColNomeCliente.setResizable(false);
        tblColEmail.setResizable(false);
        tblColContacto.setResizable(false);
        tblColNif.setResizable(false);
        tblColPassword.setResizable(false);
        tblColUtilizador.setResizable(false);

        tblColNomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nome"));
        tblColNif.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("nif"));
        tblColEmail.setCellValueFactory(new PropertyValueFactory<Cliente,String>("email"));
        tblColContacto.setCellValueFactory(new PropertyValueFactory<Cliente,String>("contacto"));
        tblColUtilizador.setCellValueFactory(new PropertyValueFactory<Cliente,String>("utilizador"));
        tblColPassword.setCellValueFactory(new PropertyValueFactory<Cliente,String>("password"));
        //tv_clientes.setItems(Cliente.getCliente());
        tblClientes.setItems(Cliente.getCliente());


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Desencriptacao();
        initTable();


    }
}