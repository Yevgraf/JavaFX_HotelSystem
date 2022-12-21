package BLL;

import DAL.ReservaDAL;
import Model.MessageBoxes;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReservaBLL {

    public int addReserva(Reserva reserva) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        return reservaDAL.addReserva(reserva);
    }

    public static ObservableList<Reserva> getReservas() {
        List<Reserva> reservas = ReservaDAL.getReservas();
        ObservableList<Reserva> lista = FXCollections.observableArrayList(reservas);
        return lista;
    }

    public static boolean checkAvailability(int roomId, LocalDate startDate) {
        try {
            return ReservaDAL.isRoomAvailable(roomId, startDate);
        } catch (SQLException e) {
            // handle exception
        }
        return false;
    }

    public static void deleteReservation(Reserva selectedReservation) {
        try {
            ReservaDAL.deleteReservation(selectedReservation.getId());
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "A reserva foi apagada", "Apagar reserva");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateReserva(Reserva reserva) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        reservaDAL.updateReserva(reserva);
    }


    public double calculateTotalAmount(Integer reservationId) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        return reservaDAL.calculateTotalAmount(reservationId);
    }
}
