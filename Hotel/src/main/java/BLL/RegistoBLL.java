package BLL;

import DAL.RegistoDAL;
import Model.Registo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class RegistoBLL {

    public ObservableList<Registo> getAllRegistos() throws SQLException {
        RegistoDAL dal = new RegistoDAL();
        List<Registo> registos = dal.getAllRegistos();
        ObservableList<Registo> observableList = FXCollections.observableArrayList();
        observableList.addAll(registos);
        return observableList;
    }

    public static void addNewRegisto(int idCartao, int idCliente, String local, Timestamp data) throws SQLException {
        RegistoDAL dal = new RegistoDAL();
        Registo registo = new Registo(idCartao, idCliente, local, data);
        dal.addRegisto(registo);
    }
public void deleteRegisto(int id) throws SQLException {
        RegistoDAL dal = new RegistoDAL();
        dal.deleteRegisto(id);
}

    public static int getCardIdByClientId(int clientId) throws SQLException {
        return RegistoDAL.getCardIdByClientId(clientId);
    }

}
