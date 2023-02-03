package DAL;

import BLL.UtilizadorPreferences;
import Model.MessageBoxes;
import Model.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicoDAL {


    /**
     * A função getAllServicosAndQuartos serve para guardar todos os serviços de quartos
     * @return devolve uma lista de todos os serviços
     */
    public static ObservableList<Servico> getAllServicosAndQuartos() {
        ObservableList<Servico> lista = FXCollections.observableArrayList();
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            // Retrieve all services
            String cmd = "SELECT * FROM Servico";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Servico obj = new Servico(rs.getInt("id"), rs.getString("servico"), rs.getDouble("preco"));
                lista.add(obj);
            }

            // Retrieve all rooms
            cmd = "SELECT * FROM Quarto";
            rs = st.executeQuery(cmd);
            while (rs.next()) {
                Servico obj = new Servico(rs.getInt("id"), "Quarto" + rs.getInt("idCartao"), rs.getDouble("preco"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }


    /**
     * A função addServico serve para verificar se é possível adicionar o serviço
     * @param servico recebe o serviço
     */
    public void addServico(Servico servico) {
        try {
            if (isServicoExists(servico.getServico())) {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Serviço já existe!", "ERRO");
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

    /**
     * A função isServicoExists serve para verificar se o serviço existo
     * @param servico recebe o serviço
     * @return devolve se o serviço existe ou não
     * @throws SQLException mostra a informacao do erro
     */
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


    /**
     * A função deleteServico serve para eliminar um serviço
     * @param id recebe o id
     * @return devolve o serviço eliminado
     * @throws SQLException mostra a informacao do erro
     */
    public Servico deleteServico(int id) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("DELETE FROM Servico WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        return null;
    }

    /**
     * A função getAllServicos serve para guardar todos os serviços
     * @return devolve uma lista com todos os serviços
     */
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

    /**
     * A função getServicosByClientId serve para guardar os serviços pelo id do cliente
     * @return devolve uma lista de todos os serviços
     */
    public static ObservableList<Servico> getServicosByClientId() {
        Integer id = UtilizadorPreferences.utilizadorId();
        ObservableList<Servico> lista = FXCollections.observableArrayList();
        Set<Servico> servicos = new HashSet<>();
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT s.id as sId, s.servico as servico, q.idCartao as idCartao " +
                    "FROM Servico s JOIN ServicoReserva SR ON s.id = SR.idServico " + "JOIN Reserva r ON r.id = SR.idReserva " +
                    "JOIN EstadoReserva er ON er.reserva = r.id " + "JOIN Quarto q ON q.id = r.idQuarto " + "WHERE r.idCliente = ? " +
                    "AND er.estado = 'checkin'";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Servico obj = new Servico(rs.getInt("sId"), rs.getString("servico"));
                servicos.add(obj);
                int idCartao = rs.getInt("idCartao");
                Servico quarto = new Servico("Quarto", idCartao);
                servicos.add(quarto);
            }
            lista.addAll(servicos);
            ps.close();
        } catch (Exception ex) {
        }
        return lista;
    }



}
