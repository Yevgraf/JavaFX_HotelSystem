package BLL;

import DAL.ProdutoReservaDAL;
import Model.MessageBoxes;
import Model.ProdutoReserva;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class ProdutoReservaBLL {

    public static void addProductInRoom(int roomId, String productId, int quantity) {
        try {
            ProdutoReservaDAL.addProductInReservation(roomId, productId, quantity);
        } catch (SQLException e) {

            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro ao adicionar produto no quarto", "Erro");
        }
    }

    public static void deleteProductFromRoom(ProdutoReserva selectedProduct) {
        try {
            ProdutoReservaDAL.deleteProductFromReservation(selectedProduct.getId());
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Produto removido", "Information");
        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Reserva existente com estes dados","Reserva existente");
            throw new RuntimeException(ex);
        }
    }
}
