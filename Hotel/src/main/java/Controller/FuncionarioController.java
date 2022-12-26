package Controller;

import BLL.UtilizadorPreferences;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class FuncionarioController {

    @FXML
    private Button btnEntradaStock;

    @FXML
    private Button btnGestaoQuartosClick;

    @FXML
    private Button btnGestorCheck;

    @FXML
    private Button btngestorReservas;

    @FXML
    private Button clickBtnServico;

    @FXML
    private Button clickGestaoStock;

    @FXML
    private Button clickGestaoUtiliBtn;

    @FXML
    private Button clickRegUtiliBtn;

    @FXML
    void clickBtnReservas(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btngestorReservas.getScene().getWindow();
        stage.setTitle("Gerir Reservas");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickBtnServico(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoServicos.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) clickBtnServico.getScene().getWindow();
        stage.setTitle("Gestao Servicos");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();

    }

    @FXML
    void btnGestaoQuartosClick (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarQuarto.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnGestaoQuartosClick.getScene().getWindow();
        stage.setTitle("Criar Quarto");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickEntradaStockBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CarregarXML.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnEntradaStock.getScene().getWindow();
        stage.setTitle("Entrada de Stock");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickGestaoStock(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoStock.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) clickGestaoStock.getScene().getWindow();
        stage.setTitle("Gestao Stock");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickGestaoUtiliBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoUtilizadores.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) clickGestaoUtiliBtn.getScene().getWindow();
        stage.setTitle("Gestao Clientes");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void clickRegUtiliBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CriarUtilizador.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) clickRegUtiliBtn.getScene().getWindow();
        stage.setTitle("Registo Funcionario");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void logoutBtn(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) clickRegUtiliBtn.getScene().getWindow();
        stage.setTitle("Login");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
        UtilizadorPreferences.apagarTipoLogin();
    }


}
