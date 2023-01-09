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

    private void desativarDiasOcupadosDasReservas(int idQuarto) {
        ReservaBLL rBLL = new ReservaBLL();
        List<LocalDate> startDate = rBLL.getDataInicial(idQuarto);
        List<LocalDate> endDate = rBLL.getDataFinal(idQuarto);
        DatePickerInicio.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || (date.isAfter(startDate) && date.isBefore(endDate)) || date.equals(startDate) || date.equals(endDate));
            }
        });
        DatePickerFim.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || (date.isAfter(startDate) && date.isBefore(endDate)) || date.equals(startDate) || date.equals(endDate));
            }
        });
    }

    private void listenComboboxReserva() {
        ReservaBLL rBLL = new ReservaBLL();
        cmbIDQuarto.setOnAction(event -> {
            int idQuarto = cmbIDQuarto.getValue().getId();
            if (rBLL.verificaSeReservaExiste(idQuarto)) {
                desativarDiasOcupadosDasReservas(idQuarto);
            }
        });
    }

    private void resetDatePickers(){
        DatePickerInicio.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(false);
            }
        });
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


