package DAL;

import Model.Quarto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class QuartoDAL {

        // method to add a quarto to the database
        public void addQuarto(Quarto quarto) {
            PreparedStatement ps2;

            try {
                // create a database connection
                DBconn dbConn = new DBconn();
                Connection connection = dbConn.getConn();

                // prepare the insert statement
                ps2 = connection.prepareStatement("INSERT INTO Quarto (tipoQuarto,piso,preco,numeroCartao, ativo) VALUES (?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                // set the values for the prepared statement
                ps2.setString(1, quarto.getTipoQuarto());
                ps2.setString(2, quarto.getPiso());
                ps2.setDouble(3, quarto.getPreco());
                ps2.setString(4, String.valueOf(quarto.getNumCartao()));
                ps2.setBoolean(5, quarto.getAtivo());

                // execute the insert statement
                ps2.executeUpdate();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }



    public void updateQuarto(Quarto quarto) {
        PreparedStatement ps2;

        try {
            // create a database connection
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            // prepare the update statement
            ps2 = connection.prepareStatement("UPDATE Quarto SET tipoQuarto = ?, piso = ?, preco = ? WHERE id = ?");

            // set the values for the prepared statement
            ps2.setString(1, quarto.getTipoQuarto());
            ps2.setString(2, quarto.getPiso());
            ps2.setDouble(3, quarto.getPreco());
            ps2.setInt(4, quarto.getId());

            // execute the update statement
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public Quarto deleteQuarto(int id) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("DELETE FROM Quarto WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        return null;
    }



    public static ObservableList<Quarto> getAllQuartos() {
        ObservableList<Quarto> list = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Quarto";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Quarto objQuarto = new Quarto(rs.getInt("id"), rs.getString("tipoQuarto"), rs.getString("piso"),
                        rs.getDouble("preco"), rs.getString("numeroCartao"), rs.getBoolean("ativo"));
                list.add(objQuarto);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return list;
    }

        public void updateQuartoAtivo(Integer id, boolean b) {
        }
}
