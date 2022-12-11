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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoUtilizadores.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}