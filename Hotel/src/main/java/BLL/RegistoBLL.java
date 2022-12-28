package BLL;

import DAL.RegistoDAL;
import Model.Registo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class RegistoBLL {

    public ObservableList<Registo> getAllRegistos() throws SQLException {
        RegistoDAL dal = new RegistoDAL();
        List<Registo> registos = dal.getAllRegistos();
        ObservableList<Registo> observableList = FXCollections.observableArrayList();
        observableList.addAll(registos);
        return observableList;
    }

    public void addNewRegisto(int id, int idCartao, int idCliente, String local, Date data) throws SQLException {
        RegistoDAL dal = new RegistoDAL();
        Registo registo = new Registo(id,idCartao, idCliente, local, data);
        dal.addRegisto(registo);
    }

}
