package BLL;

import DAL.ProdutoDAL;
import DAL.ProdutoQuartoDAL;
import Model.MessageBoxes;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class ProdutoQuartoBLL {

    public static void addProductInRoom(int roomId, String productId, int quantity) {
        try {
            ProdutoQuartoDAL.addProductInRoom(roomId, productId, quantity);
        } catch (SQLException e) {
            // handle exception
        }
    }

    public static void deleteProductFromRoom(Model.ProdutoQuarto selectedProduct) {
        try {
            ProdutoQuartoDAL.deleteProductFromRoom(selectedProduct.getId());
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Produto removido", "Information");
        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Reserva existente com estes dados","Reserva existente");
            throw new RuntimeException(ex);
        }
    }
}
