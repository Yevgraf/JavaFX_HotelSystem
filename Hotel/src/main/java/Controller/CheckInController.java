package Controller;

import BLL.CheckInBLL;
import BLL.ReservaBLL;
import Model.MessageBoxes;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

public class CheckInController {

    @FXML
    private ComboBox<Reserva> reservationComboBox;

    public void initialize() {

        initCombos();
    }

    private void initCombos() {
        ObservableList<Reserva> reservations = ReservaBLL.getReservas();
        reservationComboBox.setItems(reservations);

    }

    @FXML
    private void handleCheckInButtonAction(ActionEvent event) {
        Reserva reservation = reservationComboBox.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro", "Por favor selecione uma reserva");
        } else {
            CheckInBLL checkInBLL = new CheckInBLL();
            performCheckIn(reservation);
            // show a success message and close the window
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Sucesso", "Check-in realizado com sucesso");
        }
    }

    private void performCheckIn(Reserva reservation) {
        CheckInBLL checkInBll = new CheckInBLL();
        try {
            checkInBll.checkIn(reservation.getId());
        } catch (SQLException e) {
            // show error message to the user
           MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","There was a problem performing the check-in. Please try again later.");
        }
    }



}
