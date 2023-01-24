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




    public void checkIn(int reservationId) throws SQLException {
        CheckInDAL checkin = new CheckInDAL();
        checkin.updateStockOnCheckIn(reservationId);
        ReservaDAL dal = new ReservaDAL();
        dal.updateReservationState(reservationId, "checkin");

        QuartoDAL quartoDal = new QuartoDAL();
        quartoDal.updateAtivo(reservationId, true);
    }

}
