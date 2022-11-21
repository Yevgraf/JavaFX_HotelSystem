package Controller;

import Model.Reserva;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GestaoReservas implements Initializable {

    @FXML
    private Button adicionarReservaBtn;

    @FXML
    private Button btnVoltar;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario_GestaoReservas_Adicionar.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) adicionarReservaBtn.getScene().getWindow();
        stage.setTitle("Adicionar reserva");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickEliminarReservaBtn(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }

    private void initTable() {

        tblColDReserva.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("id"));
        tblColIDCliente.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("nifCliente"));
        tblCoIDColab.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("idColaborador"));
        tblCoIDQuarto.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("idQuarto"));
        tblColDataIni.setCellValueFactory(new PropertyValueFactory<Reserva, String>("dataInicio"));
        tblColDataFim.setCellValueFactory(new PropertyValueFactory<Reserva, String>("dataFim"));
        tblColServEx.setCellValueFactory(new PropertyValueFactory<Reserva, String>("servExtra"));
        tblCoPreco.setCellValueFactory(new PropertyValueFactory<Reserva, Double>("preco"));

        tblReservas.setItems(Reserva.getReservas());
    }

}
