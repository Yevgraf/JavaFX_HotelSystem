package Controller;

import BLL.CheckInBLL;
import BLL.CheckoutBLL;
import Model.MessageBoxes;
import Model.Pagamento;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckInController implements Initializable {

    @FXML
    private AnchorPane PainelGestorStock;

    @FXML
    private Button Voltar;

    @FXML
    private Button btnCheckOut;

    @FXML
    private Button btnCheckin;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private ImageView imgGestorStock;

    @FXML
    private ImageView imgGestorStock1;

    @FXML
    private ImageView imgGestorStock11;

    @FXML
    private Label lblData1;

    @FXML
    private Label lblData11;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblSamos;

    @FXML
    private ListView<Reserva> listViewReservaComcheckin;

    @FXML
    private ListView<Reserva> listViewReservaSemCheckin;

    @FXML
    private ComboBox<Pagamento> metodoPagamento;

    private void initCombos() throws SQLException {
        CheckoutBLL checkoutBLL = new CheckoutBLL();
        ObservableList<Pagamento> pagamentos = checkoutBLL.getPagamentos();
        metodoPagamento.setItems(pagamentos);

    }


    @FXML
    void VoltarClick(ActionEvent event) {

    }

    @FXML
    void checkoutBtnAction(ActionEvent event) {

    }

    @FXML
    void handleCheckInButtonAction(ActionEvent event) {

    }

    private void performCheckIn(Reserva reservation) {
        CheckInBLL checkInBll = new CheckInBLL();
        try {
            checkInBll.checkIn(reservation.getId());
        } catch (SQLException e) {
            // show error message to the user
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","There was a problem performing the check-in. Please try again later.");
        }
    }


    private void initListViews() {
        CheckoutBLL bll = new CheckoutBLL();
        try {
            listViewReservaSemCheckin.setItems(FXCollections.observableArrayList(bll.getPendingReservations()));
        } catch (SQLException e) {
            // show error message to the user
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","There was a problem retrieving the pending reservations. Please try again later.");
        }

        try {
            listViewReservaComcheckin.setItems(FXCollections.observableArrayList(bll.getCheckedInReservations()));
        } catch (SQLException e) {
            // show error message to the user
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","There was a problem retrieving the pending reservations. Please try again later.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initCombos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        initListViews();
    }
}
