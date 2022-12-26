package Controller;

import BLL.ReservaBLL;
import BLL.ServicoBLL;
import BLL.ServicoReservaBLL;
import Model.MessageBoxes;
import Model.Reserva;
import Model.Servico;
import Model.ServicoReserva;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

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
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION,"Inserido", "Servico inserido à reserva");
            } catch (SQLException e) {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Erro", "Erro ao inserir Serviço");
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
        ObservableList<Reserva> reservations = ReservaBLL.getReservas();
        cmbReserva.setItems(reservations);
    }
}
