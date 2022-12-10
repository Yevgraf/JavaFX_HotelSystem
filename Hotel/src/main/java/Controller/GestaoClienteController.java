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
import java.util.Date;
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
    private TableColumn<Utilizador, Integer> tbl_contacto;

    @FXML
    private TableColumn<Utilizador, Date> tbl_dataNasc;

    @FXML
    private TableColumn<Utilizador, String> tbl_email;

    @FXML
    private TableColumn<Utilizador, Integer> tbl_id;

    @FXML
    private TableColumn<Utilizador, String> tbl_morada;

    @FXML
    private TableColumn<Utilizador, String> tbl_name;

    @FXML
    private TableColumn<Utilizador, String> tbl_nif;

    @FXML
    private TableColumn<Utilizador, String> tbl_utilizador;

    @FXML
    private TableView<Utilizador> tv_Clientes;
    @FXML
    void clickAddClienteBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarCliente.fxml"));
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

        tbl_id.setResizable(false);
        tbl_name.setResizable(false);
        tbl_nif.setResizable(false);
        tbl_morada.setResizable(false);
        tbl_dataNasc.setResizable(false);
        tbl_email.setResizable(false);
        tbl_contacto.setResizable(false);
        tbl_utilizador.setResizable(false);


        tbl_id.setCellValueFactory(new PropertyValueFactory<Utilizador, Integer>("id"));
        tbl_name.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("nome"));
        tbl_nif.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("nif"));
        tbl_morada.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("morada"));
        tbl_dataNasc.setCellValueFactory(new PropertyValueFactory<Utilizador, Date>("dataNascimento"));
        tbl_email.setCellValueFactory(new PropertyValueFactory<Utilizador, String >("email"));
        tbl_contacto.setCellValueFactory(new PropertyValueFactory<Utilizador, Integer>("contacto"));
        tbl_utilizador.setCellValueFactory(new PropertyValueFactory<Utilizador, String>("utilizador"));
        tv_Clientes.setItems(Utilizador.getClientes());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}