package BLL;

import DAL.ReservaDAL;
import Model.Reserva;

import java.sql.SQLException;

public class ReservaBLL {

    public void addReserva(Reserva reserva) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        reservaDAL.addReserva(reserva);
    }
}
