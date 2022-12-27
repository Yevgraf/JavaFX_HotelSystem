package DAL;

import Model.TipoUtilizador;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;

public class TipoUtilizadorDAL {
    public static TipoUtilizador getByNome(String nome) throws SQLException {
        if (nome == null) {
            throw new IllegalArgumentException("Nome cannot be null");
        }

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM TipoUtilizador WHERE nome=?");
            ps.setString(1, nome);
            result = ps.executeQuery();
            if (result.next()) {
                return new TipoUtilizador(
                        result.getInt("Id"),
                        result.getString("Nome"));
            }
        } catch (SQLException e) {
            // Handle exception
        } finally {
            if (result != null) {
                result.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public static List<TipoUtilizador> getTipoUtilizador() {
        List<TipoUtilizador> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM TipoUtilizador";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                TipoUtilizador obj = new TipoUtilizador(rs.getInt("id"),rs.getString("nome"));
                lista.add(obj);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
}
