package Controller;

import BLL.ReservaBLL;
import BLL.ServicoBLL;
import BLL.ServicoReservaBLL;
import Model.MessageBoxes;
import Model.Reserva;
import Model.Servico;
import Model.ServicoReserva;
import com.example.hotel.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ServicoReservaController implements Initializable {

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnRemover;

    @FXML
    private Button Voltar;

    @FXML
    private ComboBox<Reserva> cmbReserva;

    @FXML
    private ListView<Servico> listViewServicos;

    @FXML
    void btnAdicionar(ActionEvent event) {
        Reserva selectedReservation = cmbReserva.getValue();
        Servico selectedServico = listViewServicos.getSelectionModel().getSelectedItem();
        ServicoReserva servicoReserva = new ServicoReserva();
        servicoReserva.setIdReserva(selectedReservation.getId());
        servicoReserva.setIdServico(selectedServico.getIdServico());

        ServicoReservaBLL servicoReservaBLL = new ServicoReservaBLL();
        try {
            servicoReservaBLL.addServicosToReserva(List.of(servicoReserva), selectedReservation.getId());
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION,"Serviço inserido à reserva", "Inserido");
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Erro ao inserir Serviço", "Erro");
        }
    }



    @FXML
    void btnRemover(ActionEvent event) throws SQLException {
        Reserva selectedReservation = cmbReserva.getValue();
        Servico selectedServico = listViewServicos.getSelectionModel().getSelectedItem();
        ServicoReservaBLL servicoReservaBLL = new ServicoReservaBLL();
        servicoReservaBLL.removeServicoFromReservation(selectedReservation, selectedServico);
        MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION,"Removido","Servico removido da reserva");
    }

    @FXML
    void VoltarClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) Voltar.getScene().getWindow();
        stage.setTitle("Gestao CheckIn");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
        initListView();
    }

    private void initListView() {
        ObservableList<Servico> servicos = ServicoBLL.getAllServicos();
        listViewServicos.setItems(servicos);
    }

    private void initCombos() {
        ReservaBLL reservaBLL = new ReservaBLL();

        ObservableList<Reserva> reservations = reservaBLL.getReservas();
        cmbReserva.setItems(reservations);
    }
}
