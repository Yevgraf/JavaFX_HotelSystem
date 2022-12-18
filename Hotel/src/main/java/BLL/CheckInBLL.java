package BLL;

import DAL.CheckInDAL;
import DAL.ReservaDAL;
import Model.Reserva;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CheckInBLL {
    private CheckInDAL checkInDAL = new CheckInDAL();
    private ReservaDAL reservaDAL = new ReservaDAL();

    // method to check in a quarto
    public void checkInQuarto(int quartoId) {
        checkInDAL.checkInQuarto(quartoId);
    }

    // method to check out a quarto
    public void checkOutQuarto(int quartoId) {
        checkInDAL.checkOutQuarto(quartoId);
    }

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


}
