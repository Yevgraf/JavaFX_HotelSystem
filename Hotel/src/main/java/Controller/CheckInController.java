package Controller;

import BLL.ReservaBLL;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class CheckInController {

    @FXML
    private ComboBox<Reserva> reservationComboBox;

    public void initialize() {
        ComboBox<Reserva> reservationComboBox = new ComboBox<>();
        reservationComboBox.setItems(ReservaBLL.getReservas());

    }

    @FXML
    private void handleCheckInButtonAction(ActionEvent event) {
        // Get the selected reservation from the combo box
        Reserva selectedReservation = reservationComboBox.getSelectionModel().getSelectedItem();
        // Perform the check-in using the selected reservation
        performCheckIn(selectedReservation);
    }

    private void performCheckIn(Reserva reservation) {
        // TODO: implement check-in logic here
    }

}
