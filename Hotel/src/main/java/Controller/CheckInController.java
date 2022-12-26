package Controller;

import BLL.CheckInBLL;
import BLL.ReservaBLL;
import BLL.UtilizadorPreferences;
import Model.MessageBoxes;
import Model.Reserva;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CheckInController {

    @FXML
    private Button Voltar;

    @FXML
    private ComboBox<Reserva> reservationComboBox;

    public void initialize() {

        initCombos();
    }

    private void initCombos() {
        ObservableList<Reserva> reservations = ReservaBLL.getReservas();
        reservationComboBox.setItems(reservations);

    }

    @FXML
    private void handleCheckInButtonAction(ActionEvent event) {
        Reserva reservation = reservationComboBox.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro", "Por favor selecione uma reserva");
        } else {
            CheckInBLL checkInBLL = new CheckInBLL();
            performCheckIn(reservation);
            // show a success message and close the window
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Sucesso", "Check-in realizado com sucesso");
        }
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



}
