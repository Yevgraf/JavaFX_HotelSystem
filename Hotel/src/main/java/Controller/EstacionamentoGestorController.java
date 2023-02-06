package Controller;

import BLL.EstacionamentoBLL;
import BLL.ReservaBLL;
import BLL.UtilizadorPreferences;
import Model.EstacionamentoAPI.Parking;
import Model.EstacionamentoAPI.TicketInfo;
import Model.MessageBoxes;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class EstacionamentoGestorController {

    @FXML
    private ImageView LogOut;

    @FXML
    private Button getLugares;

    @FXML
    private ImageView imgGestorStock2;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblSamos;

    @FXML
    private AnchorPane roomservices;

    @FXML
    private TextArea txtView;


    @FXML
    private Button Voltar;

    @FXML
    private TextField idDeleteTxT;

    @FXML
    private Button deleteTicketBtn;

    @FXML
    private Button getTodosTicket;

    @FXML
    void getLugaresClick(ActionEvent event) {
        EstacionamentoBLL eBLL = new EstacionamentoBLL();
        var lugares = eBLL.GetLugares();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lugares.Parking.size(); i++) {
            Parking currentParking = lugares.Parking.get(i);

            sb.append("Lugar: " + currentParking.ParkingSpot + "\n");
            sb.append("Preço: " + currentParking.Price + "\n");
            sb.append("Local: " + (currentParking.Indoor ? "Interior" : "Exterior") + "\n\n");
            //sb.append("Vaga: " + (currentParking.Occupied ? "Ocupada" : "Desocupada") + "\n\n");
        }
        txtView.setText(sb.toString());
    }

    @FXML
    void getTodosTicketClick(ActionEvent event) {
        var ticketBLL = new EstacionamentoBLL();
        var ticket = ticketBLL.GetTicketsCriados();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ticket.Tickets.size(); i++) {
            TicketInfo currentTicket = ticket.Tickets.get(i);

            sb.append("ID: " + currentTicket.Id + "\n");
            sb.append("Cliente: " + currentTicket.ClientId + "\n");
            sb.append("Data inicial: " + currentTicket.StartDate + "\n");
            sb.append("Data final: " + currentTicket.EndDate + "\n");
            sb.append("Lugar: " + currentTicket.ParkingSpot + "\n\n");
        }
        txtView.setText(sb.toString());
    }

    @FXML
    void VoltarClick(ActionEvent event) throws IOException {
        if (UtilizadorPreferences.comparaTipoLogin()) {
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
    void deleteTicketBtnClick(ActionEvent event) {
        var ticketBLL = new EstacionamentoBLL();
        if (idDeleteTxT.getText().isEmpty() || idDeleteTxT.getText().isBlank())  {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza um ID!", "ERRO:");
            return;
        }

        if (ticketBLL.DeleteTicket(idDeleteTxT.getText().trim())){
            ReservaBLL rBLL = new ReservaBLL();
            rBLL.updateTicketIDNaReservaToNullQuandoApagaTicket(idDeleteTxT.getText().trim());
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Ticket apagado com sucesso!","Sucesso!");
            idDeleteTxT.setText("");
        } else {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Ticket não encontrado!","ERRO:");
        }
    }
}
