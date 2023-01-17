package DAL;

import BLL.ReservaBLL;
import Controller.CheckInController;
import Controller.CriarQuartoController;
import Controller.CriarUtilizadoresController;
import Model.MessageBoxes;
import Model.Reserva;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class CheckInDAL {

    public List<Reserva> getPendingReservations() throws SQLException {
        List<Reserva> pendingReservations = new ArrayList<>();

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("SELECT R.* FROM Reserva R INNER JOIN EstadoReserva ER ON R.id = ER.reserva WHERE ER.estado = 'pendente'");
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int idCliente = resultSet.getInt("idCliente");
            int idQuarto = resultSet.getInt("idQuarto");
            String dataInicio = String.valueOf(resultSet.getDate("dataInicio"));
            String dataFim = String.valueOf(resultSet.getDate("dataFim"));
            Double preco = resultSet.getDouble("preco");
            Reserva reservation = new Reserva(id, idCliente, idQuarto, dataInicio, dataFim, preco);
            pendingReservations.add(reservation);
        }

        return pendingReservations;
    }

    public static boolean VerificarDataCheckIn(Reserva reservation){
        LocalDate currentDate = LocalDate.now();
        String verificardata = "Select r.dataInicio From Reserva r Where r.id = " + reservation.getId();
        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(verificardata)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getDate(1).equals(currentDate)) {
                    CheckInController.vdci = true;
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Checkin feito com sucesso!", "Aviso");
                }else{
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Ainda não é o dia do CheckIN!", "ERRO");
                    CheckInController.vdci = false;
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        return CheckInController.vdci;
    }


    /*public static boolean VerificarDataCheckOut(){
        Reserva reservation = new Reserva();
        LocalDate currentDate = LocalDate.now();
        String verificardata = "Select r.datafim From Reserva r Where r.id = " + reservation.getId();
        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(verificardata)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getDate(1).equals(currentDate)) {
                    CheckInController.vdci = true;
                }else{
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Ainda não é o dia do CheckOut!", "ERRO");
                    CheckInController.vdci = false;
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        return CheckInController.vdci;
    }*/

}
