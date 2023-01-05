package BLL;

import DAL.CheckInDAL;
import DAL.CheckoutDAL;
import DAL.QuartoDAL;
import DAL.ReservaDAL;
import Model.CheckIn;
import Model.Checkout;
import Model.Pagamento;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class CheckoutBLL {


    public ObservableList<Pagamento> getPagamentos() throws SQLException {
        CheckoutDAL dal = new CheckoutDAL();
        return dal.getPagamentos();
    }

    public List<Reserva> getPendingReservations() throws SQLException {
        CheckInDAL dal = new CheckInDAL();
        return dal.getPendingReservations();
    }
    public List<Reserva> getCheckedInReservations() throws SQLException {
        CheckoutDAL dal = new CheckoutDAL();
        return dal.getCheckedInReservations();
    }

    public void updateReservationStateCheckout(int reservationId) throws SQLException {
        ReservaDAL dal = new ReservaDAL();
        dal.updateReservationState(reservationId, "checkout");

        QuartoDAL quartoDal = new QuartoDAL();
        quartoDal.updateAtivo(reservationId, false);
    }

    public void addCheckout(Checkout checkout) throws SQLException {
        CheckoutDAL checkoutDAL = new CheckoutDAL();
        checkoutDAL.addCheckout(checkout);
    }

}
