package BLL;

import DAL.ReservaDAL;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReservaBLL {

    public void addReserva(Reserva reserva) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        reservaDAL.addReserva(reserva);
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
}
