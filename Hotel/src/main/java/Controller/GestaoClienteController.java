package Controller;

import Model.Utilizador;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestaoClienteController implements Initializable {

    @FXML
    private Button btnGestorAdicionarCliente;

    @FXML
    private Button btnGestorEditarCliente;

    @FXML
    private Button btnGestorEliminarCliente;

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<Utilizador> tblClientes;

    @FXML
    private TableColumn<Utilizador, String> tblColContacto;

    @FXML
    private TableColumn<Utilizador, String> tblColEmail;

    @FXML
    private TableColumn<Utilizador, String> tblColNif;

    @FXML
    private TableColumn<Utilizador, String> tblColNomeCliente;

    @FXML
    private TableColumn<Utilizador, String> tblColPassword;

    @FXML
    private TableColumn<Utilizador, String> tblColUtilizador;

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
        //    PreparedStatement ps2;
        //    try {
        //        DBconn dbConn = new DBconn();
        //        Connection connection = dbConn.getConn();

        //        Utilizador selectedID = (Utilizador) tblClientes.getSelectionModel().getSelectedItem();
        //        if (selectedID != null) {
        //            ps2 = connection.prepareStatement("DELETE FROM Cliente WHERE nif = ?");
        //            ps2.setInt(1, selectedID.getNif());
        //            ps2.executeUpdate();
        //            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Cliente Removido", "Information");
        //        }

        //    } catch (SQLException ex) {
        //        throw new RuntimeException(ex);
        //    }
    }

    private void initTable() {

        tblColNomeCliente.setResizable(false);
        tblColEmail.setResizable(false);
        tblColContacto.setResizable(false);
        tblColNif.setResizable(false);
        tblColPassword.setResizable(false);
        tblColUtilizador.setResizable(false);

        tblColNomeCliente.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("nome"));
        tblColNif.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("nif"));
        tblColEmail.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("email"));
        tblColContacto.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("contacto"));
        tblColUtilizador.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("utilizador"));
        tblColUtilizador.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("password"));
        //tv_clientes.setItems(Cliente.getCliente());
        tblClientes.setItems(Utilizador.getClientes());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}