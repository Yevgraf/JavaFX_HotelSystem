package BLL;

import DAL.ServicoDAL;
import Model.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class ServicoBLL {
    public static ObservableList<Servico> getServicos() {
        return ServicoDAL.getAllServicos();
    }

    public void addServico(Servico servico) {
        ServicoDAL sdal = new ServicoDAL();
        sdal.addServico(servico);
    }

    public void removeServico(int id) throws SQLException {
        ServicoDAL dal = new ServicoDAL();
        Servico servico = dal.deleteServico(id);
        if (servico != null) {
            dal.deleteServico(id);
        }
    }
    public static ObservableList<Servico> getAllServicos() {
        ObservableList<Servico> servicos = FXCollections.observableArrayList();
        try {
            servicos = ServicoDAL.getAllServicos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return servicos;
    }

}
