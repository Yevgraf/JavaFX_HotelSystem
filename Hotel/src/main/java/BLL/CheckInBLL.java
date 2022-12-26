package BLL;

import DAL.CheckInDAL;
import DAL.QuartoDAL;
import DAL.ReservaDAL;
import Model.Reserva;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CheckInBLL {
    private CheckInDAL checkInDAL = new CheckInDAL();
    private ReservaDAL reservaDAL = new ReservaDAL();


    public boolean isQuartoAtivo(int quartoId) throws ParseException {
        Reserva reserva = reservaDAL.getReservaByQuartoId(quartoId);
        if (reserva == null) {
            // if there is no reservation for the quarto, it is not active
            return false;
        }
        String dataFimString = reserva.getDataFim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date dataFimUtil = dateFormat.parse(dataFimString);
        java.sql.Date dataFim = new java.sql.Date(dataFimUtil.getTime());

        // if the current date is after the end date of the reservation, the quarto is not active
        return !dataFim.before(new Date(System.currentTimeMillis()));
    }

    public void checkIn(int reservationId) throws SQLException {
        ReservaDAL dal = new ReservaDAL();
        dal.updateReservationState(reservationId, "checkin");

        QuartoDAL quartoDal = new QuartoDAL();
        quartoDal.updateAtivo(reservationId, true);
    }

}
