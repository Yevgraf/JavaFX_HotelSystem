package DAL;

import Model.Cartao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CartaoDAL {

    public void addCartao(Cartao cartao) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO Cartao (numeroCartao, ativo) VALUES (?, ?)");
        ps.setString(1, cartao.getNumCartao());
        ps.setBoolean(2, !cartao.getAtivo());
        ps.executeUpdate();
    }

    public static ObservableList<Cartao> getAllCartoes() {
        ObservableList<Cartao> list = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Cartao";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Cartao objCartao = new Cartao(rs.getInt("id"), rs.getString("numeroCartao"), rs.getBoolean("ativo"));
                list.add(objCartao);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return list;
    }

    public void deleteCartao(String numeroCartao) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("DELETE FROM Cartao WHERE numeroCartao = ?");
        ps.setString(1, numeroCartao);
        ps.executeUpdate();
    }
}
