package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoQuartoDAL {
    public static void addProductInRoom(int roomId, String productId, int quantity) throws SQLException {
        String query1 = "INSERT INTO ProdutoQuarto (idQuarto,idProduto,quantidade) VALUES (?,?,?)";
        String query2 = "UPDATE Stock set quantidade=? WHERE idProduto=?";
        try (Connection connection = DBconn.getConn();
             PreparedStatement ps1 = connection.prepareStatement(query1);
             PreparedStatement ps2 = connection.prepareStatement(query2)) {
            ps1.setInt(1, roomId);
            ps1.setString(2, productId);
            ps1.setInt(3, quantity);
            ps1.executeUpdate();
            int newQuantity = selectStock(productId, connection) - quantity;
            ps2.setInt(1, newQuantity);
            ps2.setString(2, productId);
            ps2.executeUpdate();
        }
    }

    private static int selectStock(String productId, Connection connection) throws SQLException {
        String query = "SELECT quantidade FROM Stock WHERE idProduto=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public static void deleteProductFromRoom(int productId) throws SQLException {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            ps2 = connection.prepareStatement("DELETE FROM ProdutoQuarto WHERE id =?");
            ps2.setInt(1, productId);
            ps2.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
