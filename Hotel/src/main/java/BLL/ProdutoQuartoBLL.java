package BLL;

import DAL.ProdutoQuartoDAL;

import java.sql.SQLException;

public class ProdutoQuartoBLL {

    public static void addProductToQuarto(int quartoId, String productId, int quantity) throws SQLException, SQLException {
        ProdutoQuartoDAL.addProductInQuarto(quartoId, productId, quantity);
    }
    public static void deleteProductFromQuarto(int productId) throws SQLException {
        ProdutoQuartoDAL.deleteProductFromQuarto(productId);
    }
}
