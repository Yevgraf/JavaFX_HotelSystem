package BLL;

import DAL.ServicoReservaDAL;
import Model.MessageBoxes;
import Model.Reserva;
import Model.Servico;
import Model.ServicoReserva;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.List;

public class ServicoReservaBLL {
    private ServicoReservaDAL servicoReservaDAL = new ServicoReservaDAL();

    public void addServicosToReserva(List<ServicoReserva> servicos, int reservationId) throws SQLException {
        for (ServicoReserva servico : servicos) {
            if (!isServicoAssociatedWithReserva(reservationId, servico.getIdServico())) {
                servico.setIdReserva(reservationId);
                servicoReservaDAL.addServicoToReserva(servico);
            } else {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Serviço já existe nesta reserva!","ERRO");
            }
        }
    }


    public void removeServicoFromReservation(Reserva selectedReservation, Servico selectedServico) throws SQLException {
        ServicoReservaDAL servicoReservaDAL = new ServicoReservaDAL();
        servicoReservaDAL.removeServicoFromReservation(selectedReservation, selectedServico);
    }

    public boolean isServicoAssociatedWithReserva(int idReserva, int idServico) throws SQLException {
        ServicoReservaDAL dal = new ServicoReservaDAL();
        return dal.isServicoExistsForReserva(idReserva, idServico);
    }
}
