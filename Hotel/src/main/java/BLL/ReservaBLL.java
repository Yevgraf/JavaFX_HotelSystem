package BLL;

import DAL.QuartoDAL;
import DAL.ReservaDAL;
import Model.MessageBoxes;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservaBLL {

    public Reserva addReserva(Reserva reserva) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        reserva = reservaDAL.addReserva(reserva);

        getTotalReserva(reserva);

        return reserva;
    }

    public ObservableList<Reserva> getReservas() {
        try {
            List<Reserva> reservas = ReservaDAL.getReservas();

            for (Reserva reserva : reservas) {
                getTotalReserva(reserva);
            }

            ObservableList<Reserva> lista = FXCollections.observableArrayList(reservas);
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkAvailability(int roomId, LocalDate startDate) {
        try {
            return ReservaDAL.isRoomAvailable(roomId, startDate);
        } catch (SQLException e) {

        }
        return false;
    }
    public List<Reserva> searchReservationsByClientName(String clientName) {
        ReservaDAL reservationDAL = new ReservaDAL();
        List<Reserva> reservations = null;
        try {
            reservations = reservationDAL.searchReservationsByClientName(clientName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    public static void deleteReservation(Reserva selectedReservation) {
        try {
            ReservaDAL.deleteReservation(selectedReservation.getId());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void updateReserva(Reserva reserva) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        reservaDAL.updateReserva(reserva);
    }

    private static double getTotalServicosReserva(Integer reservationId) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        return reservaDAL.getTotalServicosReserva(reservationId);
    }

    private static double getTotalProdutosReserva(Integer reservationId) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        return reservaDAL.getTotalProdutosReserva(reservationId);
    }


    public static double getTotalReserva(Reserva reserva) throws SQLException {
        QuartoDAL quartoDAL = new QuartoDAL();
        double precoQuarto = quartoDAL.getPreco(reserva.getIdQuarto());

        double precoFinal = 0;

        // Calculo do valor diário
        LocalDate fim = LocalDate.parse(reserva.getDataFim());
        LocalDate inicio = LocalDate.parse(reserva.getDataInicio());
        long diasReserva = ChronoUnit.DAYS.between(inicio, fim);

        precoFinal += diasReserva * precoQuarto;

        // Calculo do valor dos produtos RESERVA
        precoFinal += getTotalProdutosReserva(reserva.getId());


        // Calculo do valor dos servicos
        precoFinal += getTotalServicosReserva(reserva.getId());

        reserva.setPreco(precoFinal);

        return precoFinal;
    }

    public void cancelReservation(int reservationId) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        reservaDAL.cancelReservation(reservationId);
    }

    public void updateReservationPrice(int reservationId, double newPrice) throws SQLException {
        ReservaDAL reservaDAL = new ReservaDAL();
        reservaDAL.updateReservationPrice(reservationId,newPrice);
    }

    public List<LocalDate> getDataInicial(int idQuarto) {
        ReservaDAL reservaDAL = new ReservaDAL();
        List<LocalDate> dataInicial = reservaDAL.getDataInicial(idQuarto);
        return dataInicial;
    }

    public List<LocalDate> getDataFinal(int idQuarto) {
        ReservaDAL reservaDAL = new ReservaDAL();
        List<LocalDate> dataFinal = reservaDAL.getDataFinal(idQuarto);
        return dataFinal;
    }

    public Boolean verificaSeReservaExiste(int idQuarto) {
        ReservaDAL reservaDAL = new ReservaDAL();
        if (reservaDAL.verificaSeExisteReserva(idQuarto)) {
            return true;
        } else {
            return false;
        }
    }

    public LocalDate getProxData(int idQuarto, LocalDate ultData) {
        ReservaDAL reservaDAL = new ReservaDAL();
        LocalDate proxData = reservaDAL.getProximaData(idQuarto, ultData);
        return proxData;
    }

}
