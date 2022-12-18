package Controller;

import BLL.CheckInBLL;
import DAL.CheckInDAL;

import java.text.ParseException;

public class CheckInController {

    private CheckInDAL checkInDAL = new CheckInDAL();
    private CheckInBLL checkInBLL = new CheckInBLL();

    public void startCheckIn(int quartoId) throws ParseException {
        checkInBLL.checkInQuarto(quartoId);
    }
    public void endCheckIn(int quartoId) {
        checkInBLL.checkInQuarto(quartoId);
    }
    public boolean isQuartoAtivo(int quartoId) throws ParseException {
        return checkInBLL.isQuartoAtivo(quartoId);
    }

}
