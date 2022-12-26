package Controller;

import BLL.ReservaBLL;
import BLL.UtilizadorPreferences;
import DAL.DBconn;
import Model.MessageBoxes;
import Model.Reserva;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestaoReservasController implements Initializable {

    @FXML
    private Button adicionarReservaBtn;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button adicionarServico;

    @FXML
    private Button eliminarReservaBtn;

    @FXML
    private TableView<Reserva> tblReservas;

    @FXML
    private TableColumn<Reserva, Integer> tblCoIDColab;

    @FXML
    private TableColumn<Reserva, Integer> tblCoIDQuarto;

    @FXML
    private TableColumn<Reserva, Double> tblCoPreco;

    @FXML
    private TableColumn<Reserva, Integer> tblColDReserva;

    @FXML
    private TableColumn<Reserva, String> tblColDataFim;

    @FXML
    private TableColumn<Reserva, String> tblColDataIni;

    @FXML
    private TableColumn<Reserva, Integer> tblColIDCliente;

    @FXML
    private TableColumn<Reserva, String> tblColServEx;

    @FXML
    void clickAdicionarReservaBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdicionarReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) adicionarReservaBtn.getScene().getWindow();
        stage.setTitle("Adicionar Reserva");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {
        if (UtilizadorPreferences.comparaTipoLogin()){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) btnVoltar.getScene().getWindow();
            stage.setTitle("Pagina Gestor");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) btnVoltar.getScene().getWindow();
            stage.setTitle("Pagina Funcionario");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }
    }

    @FXML
    void clickEliminarReservaBtn(ActionEvent event) {
        Reserva selectedReservation = tblReservas.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            ReservaBLL.deleteReservation(selectedReservation);
        }
        initTable();
    }

    @FXML
    void ServicoReservaClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ServicoReserva.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Servicos Reserva");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }

    private void initTable() {

        tblColDReserva.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("id"));
        tblColIDCliente.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("idCliente"));
        tblCoIDQuarto.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("idQuarto"));
        tblColDataIni.setCellValueFactory(new PropertyValueFactory<Reserva, String>("dataInicio"));
        tblColDataFim.setCellValueFactory(new PropertyValueFactory<Reserva, String>("dataFim"));
        tblColServEx.setCellValueFactory(new PropertyValueFactory<Reserva, String>("servExtra"));
        tblCoPreco.setCellValueFactory(new PropertyValueFactory<Reserva, Double>("preco"));

        tblReservas.setItems(ReservaBLL.getReservas());
    }


}
