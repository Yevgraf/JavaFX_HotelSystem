package Controller;

import BLL.ReservaBLL;
import BLL.UtilizadorPreferences;
import Model.Reserva;
import com.example.hotel.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class QRCodeClienteController implements Initializable {

    @FXML
    private Button Voltar;

    @FXML
    private ComboBox<Reserva> reservaCmb;

    @FXML
    private ImageView qrImage;

    @FXML
    void VoltarClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCliente.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) Voltar.getScene().getWindow();
        stage.setTitle("Pagina Cliente");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void reservaCmbClick(ActionEvent event) {
        //String qrData = "data:image/bmp;base64,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";
        //qrImage.setImage(new Image(qrData));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();


    }

    private void initCombos() {
        ReservaBLL rBLL = new ReservaBLL();
        try {
            List<Reserva> reservasComTicket = rBLL.getReservasComTicket(UtilizadorPreferences.utilizadorId());
            reservaCmb.getItems().addAll(reservasComTicket);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
