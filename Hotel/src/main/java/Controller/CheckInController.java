package Controller;

import BLL.CheckInBLL;
import BLL.CheckoutBLL;
import BLL.ReservaBLL;
import BLL.UtilizadorPreferences;
import Model.MessageBoxes;
import Model.Pagamento;
import Model.Reserva;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    void VoltarClick(ActionEvent event) throws IOException {
        if (UtilizadorPreferences.comparaTipoLogin()){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) Voltar.getScene().getWindow();
            stage.setTitle("Pagina Gestor");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) Voltar.getScene().getWindow();
            stage.setTitle("Pagina Funcionario");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }
    }
    @FXML
    void checkoutBtnAction(ActionEvent event) {
        Reserva selectedReservation = listViewReservaComcheckin.getSelectionModel().getSelectedItem();
        if (selectedReservation == null) {

            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro", "Por favor, selecione uma reserva.");
            return;
        }


        Pagamento selectedPaymentMethod = metodoPagamento.getSelectionModel().getSelectedItem();
        if (selectedPaymentMethod == null) {
            // show error message if no payment method is selected
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro", "Por favor, selecione um método de pagamento.");
            return;
        }


        CheckoutBLL checkoutBll = new CheckoutBLL();
        try {
            checkoutBll.updateReservationStateCheckout(selectedReservation.getId());


            ReservaBLL reservaBLL = new ReservaBLL();
            double totalCost = reservaBLL.getTotalReserva(selectedReservation);


            String receiptText = "Reserva: " + selectedReservation.getId() + "\n";
            receiptText += "Preço Final: " + totalCost + "\n";
            receiptText += "Método de Pagamento: " + selectedPaymentMethod.getMetodoPagamento() + "\n";


            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Recibo", receiptText);
            // Add Chekcout À BD
            initListViews();
        } catch (SQLException e) {

            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro", "Ocorreu um problema ao realizar o checkout. Por favor, tente novamente mais tarde.");
        }
    }


            @FXML
    void handleCheckInButtonAction(ActionEvent event) {

        Reserva selectedReservation = listViewReservaSemCheckin.getSelectionModel().getSelectedItem();


        if (selectedReservation != null) {
            // perform the check-in
            performCheckIn(selectedReservation);


            initListViews();
        } else {

            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","Por favor, selecione uma reserva da lista.");
        }
    }

    private void performCheckIn(Reserva reservation) {
        CheckInBLL checkInBll = new CheckInBLL();
        try {
            checkInBll.checkIn(reservation.getId());
        } catch (SQLException e) {

            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","Ocorreu um problema ao realizar o check-in. Por favor, tente novamente mais tarde.");
        }
    }


    private void initListViews() {
        CheckoutBLL bll = new CheckoutBLL();
        try {
            listViewReservaSemCheckin.setItems(FXCollections.observableArrayList(bll.getPendingReservations()));
        } catch (SQLException e) {

            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","Ocorreu um problema ao recuperar as reservas pendentes. Por favor, tente novamente mais tarde.");
        }

        try {
            listViewReservaComcheckin.setItems(FXCollections.observableArrayList(bll.getCheckedInReservations()));
        } catch (SQLException e) {

            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro","Ocorreu um problema ao recuperar as reservas pendentes. Por favor, tente novamente mais tarde.");
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
