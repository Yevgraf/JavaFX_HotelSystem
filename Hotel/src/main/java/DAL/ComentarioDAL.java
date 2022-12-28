package DAL;

import Model.Comentario;
import Model.Produto;
import Model.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ComentarioDAL {
    public void addComentario(Comentario comentario) {
        PreparedStatement ps2;

        try {
            // create a database connection
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            // prepare the insert statement
            ps2 = connection.prepareStatement("INSERT INTO Comentario (idCliente,comentario) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // set the values for the prepared statement
            ps2.setInt(1, comentario.getIdCliente());
            ps2.setString(2, comentario.getComentario());

            // execute the insert statement
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ObservableList<Comentario> getAllComentarios() {
        ObservableList<Comentario> list = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Comentario";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Comentario objComentario = new Comentario(rs.getInt("id"), rs.getInt("idCliente"),
                        rs.getString("comentario"));
                list.add(objComentario);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return list;
    }

    public String getComentario (Comentario selectedID) throws SQLException {
        PreparedStatement ps2;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        ps2 = connection.prepareStatement("SELECT comentario FROM Comentario WHERE id = ?");
        ps2.setInt(1, selectedID.getId());
        for (int i = 0; i < getAllComentarios().size(); i++) {
            if (selectedID != null && selectedID.getId() == getAllComentarios().get(i).getId()){
                String comentario = getAllComentarios().get(i).getComentario();
                return comentario;
            }
        }
        return null;
    }

}
