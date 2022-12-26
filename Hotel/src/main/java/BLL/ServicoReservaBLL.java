package BLL;

import DAL.ServicoReservaDAL;
import Model.Reserva;
import Model.Servico;
import Model.ServicoReserva;

import java.sql.SQLException;
import java.util.List;

public class ServicoReservaBLL {
    private ServicoReservaDAL servicoReservaDAL = new ServicoReservaDAL();

    public void addServicosToReserva(List<ServicoReserva> servicos, int reservationId) throws SQLException {
        for (ServicoReserva servico : servicos) {
            servico.setIdReserva(reservationId);
            servicoReservaDAL.addServicoToReserva(servico);
        }
    }

    public void removeServicoFromReservation(Reserva selectedReservation, Servico selectedServico) throws SQLException {
        ServicoReservaDAL servicoReservaDAL = new ServicoReservaDAL();
        servicoReservaDAL.removeServicoFromReservation(selectedReservation, selectedServico);
    }


}
