package DAL;

import Model.MessageBoxes;
import Model.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class ServicoDAL {

    public void addServico(Servico servico) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Servico(servico, preco) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, servico.getServico());
            ps2.setDouble(2, servico.getPreco());
            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Serviço inserido", "Informação Serviço");
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(e);
        }
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
            String cmd = "SELECT * FROM Servico";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Servico obj = new Servico(rs.getInt("id"), rs.getString("servico"), rs.getDouble("preco"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

}