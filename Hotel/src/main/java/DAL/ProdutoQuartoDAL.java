package DAL;

import Model.ProdutoQuarto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ProdutoQuartoDAL {

    public static void addProductInQuarto(int quartoId, String productId, int quantity) throws SQLException {
        String query1 = "INSERT INTO ProdutoQuarto (idQuarto, idProduto, quantidade) VALUES (?,?,?)";

        try (Connection connection = DBconn.getConn();
             PreparedStatement ps1 = connection.prepareStatement(query1)) {
            ps1.setInt(1, quartoId);
            ps1.setString(2, productId);
            ps1.setInt(3, quantity);
            ps1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void deleteProductFromQuarto(int productId) throws SQLException {
        String query1 = "UPDATE Stock set quantidade = quantidade + (SELECT quantidade FROM ProdutoQuarto WHERE id = ?) WHERE idProduto = (SELECT idProduto FROM ProdutoQuarto WHERE id = ?)";
        String query2 = "DELETE FROM ProdutoQuarto WHERE id = ?";
        try (Connection connection = DBconn.getConn();
             PreparedStatement ps1 = connection.prepareStatement(query1);
             PreparedStatement ps2 = connection.prepareStatement(query2)) {
            ps1.setInt(1, productId);
            ps1.setInt(2, productId);
            ps1.executeUpdate();
            ps2.setInt(1, productId);
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

    public static ObservableList<ProdutoQuarto> getProdutoQuarto() throws SQLException {
        ObservableList<ProdutoQuarto> produtoQuartoList = FXCollections.observableArrayList();
        String query = "SELECT * FROM ProdutoQuarto";
        try (Connection conn = DBconn.getConn();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idQuarto = rs.getInt("idQuarto");
                String idProduto = rs.getString("idProduto");
                int quantidade = rs.getInt("quantidade");
                ProdutoQuarto produtoQuarto = new ProdutoQuarto(id, idQuarto, idProduto, quantidade);
                produtoQuartoList.add(produtoQuarto);
            }
        }
        return produtoQuartoList;
    }

}