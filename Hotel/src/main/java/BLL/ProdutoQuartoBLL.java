package BLL;

import DAL.ProdutoQuartoDAL;

import java.sql.SQLException;

public class ProdutoQuartoBLL {

    public static void addProductInRoom(int roomId, String productId, int quantity) {
        try {
            ProdutoQuartoDAL.addProductInRoom(roomId, productId, quantity);
        } catch (SQLException e) {
            // handle exception
        }
    }
}
