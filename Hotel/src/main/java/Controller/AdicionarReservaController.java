package Controller;

import BLL.ReservaBLL;
import BLL.UtilizadorBLL;
import DAL.QuartoDAL;
import Model.*;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

public class AdicionarReservaController implements Initializable {

    @FXML
    private Label EmptyMessage;

    @FXML
    private Label DataFim;

    @FXML
    private Label DataInicio;

    @FXML
    private Label ValidarQuarto;
    @FXML
    private DatePicker DatePickerFim;

    @FXML
    private DatePicker DatePickerInicio;

    @FXML
    private Button addServicoBtn;

    @FXML
    private Button adicionarReservaBtn;

    @FXML
    private Button btnRedictCriarCliente;

    @FXML
    private ComboBox<Utilizador> cmbClientes;


    @FXML
    private ComboBox<Quarto> cmbIDQuarto;

    @FXML
    private TextField dataFim;

    @FXML
    private TextField dataInicio;

    @FXML
    private TableColumn<Servico, String> descricaoServicoTable;

    @FXML
    private TableColumn<Servico, Integer> idServicoTable;

    @FXML
    private TextField nifCliente;

    @FXML
    private TableColumn<Servico, Double> precoServicoTable;

    @FXML
    private TableView<Servico> servicosTable;

    @FXML
    private TextField txtPreco;

    @FXML
    private Button voltarBtn;

    List<LocalDate> dates = new ArrayList<>();

    @FXML
    void clickAddReservaBrn(ActionEvent event) throws SQLException, IOException {
        Quarto selectedRoom = cmbIDQuarto.getValue();
        LocalDate startDate = DatePickerInicio.getValue();
        if (selectedRoom == null || startDate == null) {
            // show error message
            return;
        }

        if (cmbClientes.getItems().isEmpty() == false && cmbIDQuarto.getItems().isEmpty() == false) {
            // if (VerificarDisponibilidade() == true){
            AdicionarReserva();
            //   }
        } else {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Preencha todos os campos!", "Erro");
        }
    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Gerir Reservas");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void AdicionarReserva() throws SQLException, IOException {
        if (DatePickerFim.getValue().isBefore(DatePickerInicio.getValue())) {
            MessageBoxes.ShowMessage(Alert.AlertType.WARNING, "A data final não pode ser inferior à data inicial.", "Aviso");
            return;
        }
        MessageBoxes.ShowMessage(Alert.AlertType.CONFIRMATION, "Confirmar reserva", "Deseja criar esta reserva?");
        ReservaBLL reservaBLL = new ReservaBLL();
        Reserva reserva = new Reserva(null, cmbClientes.getValue().getId(), cmbIDQuarto.getValue().getId(),
                DatePickerInicio.getValue().toString(), DatePickerFim.getValue().toString(), 0.0);

        reserva = reservaBLL.addReserva(reserva);

        txtPreco.setText(reserva.getPreco().toString());

        MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Reserva criada", "Reserva");
        addServicoReserva();
    }

    private void addServicoReserva() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ServicoReserva.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) adicionarReservaBtn.getScene().getWindow();
        stage.setTitle("Servicos Reserva");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
        listenComboboxReserva();
    }

    private void initCombos() {

        cmbIDQuarto.getItems().addAll(QuartoDAL.getAllQuartos().stream()
                .filter(quarto -> !quarto.getAtivo())
                .collect(Collectors.toList()));

        cmbClientes.getItems().addAll(UtilizadorBLL.getAllClientes().stream().collect(Collectors.toList()));
    }

    public void btnRedictCriarCliente(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoUtilizadores.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnRedictCriarCliente.getScene().getWindow();
        stage.setTitle("Gestao Utilizadores");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }


    @FXML
    void quartoAction(ActionEvent event) {
        resetDatePickers();
    }

    private void desativarDiasOcupadosDatePickerInicio(int idQuarto) {
        ReservaBLL rBLL = new ReservaBLL();
        List<LocalDate> listaDatasIniciais = rBLL.getDataInicial(idQuarto);
        List<LocalDate> listaDatasFinais = rBLL.getDataFinal(idQuarto);
        DatePickerInicio.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                ArrayList<LocalDate> diasDesativados = new ArrayList<>();
                for (int i = 0; i < listaDatasIniciais.size(); i++) {
                    LocalDate inicio = listaDatasIniciais.get(i);
                    LocalDate fim = listaDatasFinais.get(i);
                    while (!inicio.isAfter(fim)) {
                        diasDesativados.add(inicio);
                        inicio = inicio.plusDays(1);
                    }
                    if (diasDesativados.contains(date)) {
                        setDisable(true);
                        setStyle("-fx-background-color: #FFB6C1;");
                    }
                }
            }
        });
    }

    private void desativarDiasOcupadosDatePickerFim(LocalDate dataInicio) {
        int idQuarto = cmbIDQuarto.getValue().getId();
        ReservaBLL rBLL = new ReservaBLL();
        LocalDate dataFim = rBLL.getProxData(idQuarto, dataInicio);
        DatePickerFim.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                if (dataFim != null) {
                    if (date.isBefore(dataInicio.plusDays(1)) || date.isAfter(dataFim.minusDays(1))) {
                        setDisable(true);
                        setStyle("-fx-background-color: #FFB6C1;");
                    }
                }
            }
        });
    }

    private void listenComboboxReserva() {
        ReservaBLL rBLL = new ReservaBLL();
        cmbIDQuarto.setOnAction(event -> {
            int idQuarto = cmbIDQuarto.getValue().getId();
            if (rBLL.verificaSeReservaExiste(idQuarto)) {
                desativarDiasOcupadosDatePickerInicio(idQuarto);
                listenDatePickerInicio(idQuarto);
            }
        });
    }

    private void listenDatePickerInicio(int idQuarto) {
        ReservaBLL rBLL = new ReservaBLL();
        DatePickerInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate dataInicio = DatePickerInicio.getValue();
            if (rBLL.getProxData(idQuarto, dataInicio) != null) {
                desativarDiasOcupadosDatePickerFim(dataInicio);
            } else {
                desativarDiasOcupadosDatePickerInicio(idQuarto);
            }
        });
    }

    private void resetDatePickers() {
        DatePickerInicio.setValue(null);
        DatePickerInicio.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(false);
            }
        });
        DatePickerFim.setValue(null);
        DatePickerFim.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(false);
            }
        });
    }

    @FXML
    void cmbQuartoClick(MouseEvent event) {
        resetDatePickers();
    }
}


