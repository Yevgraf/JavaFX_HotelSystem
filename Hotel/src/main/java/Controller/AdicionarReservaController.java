package Controller;

import BLL.EstacionamentoBLL;
import BLL.ReservaBLL;
import BLL.UtilizadorBLL;
import DAL.QuartoDAL;
import Model.*;
import Model.EstacionamentoAPI.Parking;
import Model.EstacionamentoAPI.TicketInfo;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import static BLL.ReservaBLL.getTotalReserva;

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

    @FXML
    private CheckBox check;

    @FXML
    private ComboBox<String> cmbEstacionamento;

    @FXML
    private RadioButton exterior;

    @FXML
    private RadioButton interior;

    @FXML
    private Label lugarLbl;

    @FXML
    private Label matriculaLbl1;

    @FXML
    private TextField matriculaTxt;

    public static Boolean verifica = false;

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
            if (check.isSelected()) {
                if (matriculaTxt.getText().isEmpty() || cmbEstacionamento.getValue() == null) {
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Preencha todos os campos!", "Erro");
                } else {
                    AdicionarReservaEEstacionamento();
                }
            } else {
                AdicionarReservaSemEstacionamento();
            }
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

    void AdicionarReservaSemEstacionamento() throws SQLException, IOException {
        ReservaBLL reservaBLL = new ReservaBLL();
        Reserva reserva = new Reserva(null, cmbClientes.getValue().getId(), cmbIDQuarto.getValue().getId(),
                DatePickerInicio.getValue().toString(), DatePickerFim.getValue().toString(), 0.0);

        reserva = reservaBLL.addReserva(reserva);

        double precoFinal = getTotalReserva(reserva);
        reserva.setPreco(precoFinal);

        txtPreco.setText(reserva.getPreco().toString());
        MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Reserva criada com sucesso.\nPreco total: " + precoFinal, "Reserva");
        addServicoReserva();
    }


    @FXML
    void AdicionarReservaEEstacionamento() throws SQLException, IOException {
        ReservaBLL reservaBLL = new ReservaBLL();
        Reserva reserva = new Reserva(null, cmbClientes.getValue().getId(), cmbIDQuarto.getValue().getId(),
                DatePickerInicio.getValue().toString(), DatePickerFim.getValue().toString(), 0.0);

        reserva = reservaBLL.addReserva(reserva);

        double precoFinal = getTotalReserva(reserva);
        reserva.setPreco(precoFinal);

        TicketInfo ticketInfo = new TicketInfo(
                Integer.toString(cmbClientes.getValue().getId()),
                matriculaTxt.getText().trim(),
                DatePickerInicio.getValue().toString(),
                DatePickerFim.getValue().toString(),
                cmbEstacionamento.getValue().trim(),
                true);
        reservaBLL.reservaEstacionamento(reserva, ticketInfo);

        txtPreco.setText(reserva.getPreco().toString());
        MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Reserva criada com sucesso.\nPreco total: " + precoFinal, "Reserva");
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
        verifica = true;
    }

    private void initCombos() {
        cmbIDQuarto.getItems().addAll(QuartoDAL.getAllQuartos());
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

    private void desativarDiasOcupadosDatePickerFimCasoNull(LocalDate dataInicio) {
        DatePickerFim.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                if (date.isBefore(dataInicio.plusDays(1))) {
                    setDisable(empty || date.isBefore(dataInicio.plusDays(1)));
                    setStyle("-fx-background-color: #FFB6C1;");
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
            } else {
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
                desativarDiasOcupadosDatePickerFimCasoNull(dataInicio);
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
    void clickDateFim(MouseEvent event) {
        if (DatePickerInicio.getValue() == null) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Selecione primeiro a data inicial!", "Erro:");
        }
    }

    @FXML
    void cmbQuartoClick(MouseEvent event) {
        resetDatePickers();
    }

    private Boolean verificaSeDatasNaoSaoNull() {
        if (DatePickerInicio.getValue() == null || DatePickerFim.getValue() == null) {
            return false;
        } else {
            return true;
        }
    }

    private void listnerCheckBox() {
        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //conta nº de lugares ocupados
                EstacionamentoBLL eBLL = new EstacionamentoBLL();
                var lugares = eBLL.GetTicketsCriados();
                var estacionamentos = eBLL.GetLugares();
                int ticketsCriados = 0;
                for (int i = 0; i < lugares.Tickets.size(); i++) {
                    ticketsCriados++;
                }
                // verifica se estão todos ocupados e dá erro se estiverem
                if (ticketsCriados == estacionamentos.Parking.size()) {
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não há estacionamentos disponíveis!", "Erro:");
                    check.setSelected(false);
                    return;
                }

                if (verificaSeDatasNaoSaoNull()) {
                    if (check.isSelected()) {
                        interior.setDisable(false);
                        exterior.setDisable(false);
                        cmbEstacionamento.setDisable(false);
                        lugarLbl.setDisable(false);
                        interior.setSelected(true);
                        matriculaTxt.setDisable(false);
                        matriculaLbl1.setDisable(false);
                        cmbEstacionamento.getItems().clear();
                        cmbEstacionamento.getItems().addAll(getEstacionamentoDisponivel(true));
                    } else {
                        interior.setDisable(true);
                        exterior.setDisable(true);
                        cmbEstacionamento.setDisable(true);
                        lugarLbl.setDisable(true);
                        matriculaTxt.setDisable(true);
                        matriculaLbl1.setDisable(true);
                    }
                } else {
                    MessageBoxes.ShowMessage(Alert.AlertType.WARNING, "Preencher data inicial e final primeiro!", "Aviso:");
                    check.setSelected(false);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
        listenComboboxReserva();
        listnerCheckBox();
        popularCmbEstacionamentoConsoanteOpcaoRadioButton();
    }

    /*
     Mostra todos os desocupados entre as datas escolhidas
     dos DatePaickers (não incluindo os proprios dias)
     */
    private List<String> getEstacionamentoDisponivel(Boolean indoor) {
        try {
            LocalDate dataInicial = DatePickerInicio.getValue();
            LocalDate dataFinal = DatePickerFim.getValue();
            List<String> list = new ArrayList<>();
            EstacionamentoBLL eBLL = new EstacionamentoBLL();
            var ticket = eBLL.GetTicketsCriados();
            var lugares = eBLL.GetLugares();

            for (int i = 0; i < ticket.Tickets.size(); i++) {for (int j = 0; j < lugares.Parking.size(); j++) {
                Parking estacionamento = lugares.Parking.get(j);
                TicketInfo currentParking = ticket.Tickets.get(i);
                LocalDateTime offSetStartDate = LocalDateTime.parse(currentParking.StartDate);
                LocalDate startDate = offSetStartDate.toLocalDate();
                LocalDateTime offSetEncDate = LocalDateTime.parse(currentParking.StartDate);
                LocalDate endDate = offSetEncDate.toLocalDate();
                if (estacionamento.ParkingSpot == currentParking.ParkingSpot &&
                        estacionamento.Occupied == false
                        && estacionamento.Indoor == indoor
                        && (endDate.plusDays(1).isBefore(dataInicial)
                        || startDate.minusDays(1).isAfter(dataFinal))) {
                    // list.add(currentParking.ParkingSpot);
                    list.add(currentParking.ParkingSpot);
                }
            }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void popularCmbEstacionamentoConsoanteOpcaoRadioButton() {
        interior.setOnAction(event -> {
            if (interior.isSelected()) {
                cmbEstacionamento.getItems().clear();
                cmbEstacionamento.getItems().addAll(getEstacionamentoDisponivel(true));
            }
        });
        exterior.setOnAction(event -> {
            if (exterior.isSelected()) {
                cmbEstacionamento.getItems().clear();
                cmbEstacionamento.getItems().addAll(getEstacionamentoDisponivel(false));
            }
        });
    }

}


