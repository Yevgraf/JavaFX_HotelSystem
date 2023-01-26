package Controller;

import BLL.EstacionamentoBLL;
import BLL.UtilizadorPreferences;
import Model.EstacionamentoAPI.Parking;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    void ClickLogout(MouseEvent event) {

    }

    @FXML
    void getLugaresClick(ActionEvent event) {
        EstacionamentoBLL eBLL = new EstacionamentoBLL();
        var lugares = eBLL.GetLugaresDisponiveis();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lugares.Parking.size(); i++) {
            Parking currentParking = lugares.Parking.get(i);

            sb.append("Lugar: " + currentParking.ParkingSpot + "\n");
            sb.append("Preço: " + currentParking.Price + "\n");
            sb.append("Local: " + (currentParking.Indoor ? "Interior" : "Exterior") + "\n");
            sb.append("Vaga: " + (currentParking.Occupied ? "Ocupada" : "Desocupada") + "\n\n");
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
}
