package DAL;

import BLL.UtilizadorPreferences;
import Model.MessageBoxes;
import Model.Quarto;
import Model.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class ServicoDAL {

    public void addServico(Servico servico) {
        try {
            if (isServicoExists(servico.getServico())) {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Serviço já existe!","ERRO");
            } else {
                DBconn dbConn = new DBconn();
                Connection connection = dbConn.getConn();
                PreparedStatement ps2 = connection.prepareStatement("INSERT INTO Servico(servico, preco) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps2.setString(1, servico.getServico());
                ps2.setDouble(2, servico.getPreco());
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isServicoExists(String servico) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Servico WHERE servico = ?";

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, servico);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }


    public Servico deleteServico(int id) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("DELETE FROM Servico WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        return null;
    }

    public static ObservableList<Servico> getAllServicos() {
        ObservableList<Servico> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM Servico WHERE servico != 'Quarto'";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Servico obj = new Servico(rs.getInt("id"), rs.getString("servico"), rs.getDouble("preco"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
        }
        return lista;
    }

    public static ObservableList<Servico> getServicosByClientId() {
        Integer id = UtilizadorPreferences.utilizadorId();
        ObservableList<Servico> lista = FXCollections.observableArrayList();
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT s.id as idServico, s.servico as servico FROM Servico s JOIN ServicoReserva SR " +
                    "ON s.id = SR.idServico JOIN Reserva r ON r.id = SR.idReserva JOIN EstadoReserva er " +
                    "ON er.reserva = r.id WHERE er.estado like 'checkin' and r.idCliente = " + id;
            PreparedStatement ps = connection.prepareStatement(cmd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Servico obj = new Servico(rs.getInt("idServico"), rs.getString("servico"));
                lista.add(obj);
            }
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}
