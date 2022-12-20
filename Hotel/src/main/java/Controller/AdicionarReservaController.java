package Controller;

import BLL.ReservaBLL;
import BLL.ServicoBLL;
import Model.*;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static java.lang.Integer.parseInt;

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
    private ComboBox<Utilizador> cmbUtilizadores;

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
    void clickAddReservaBrn(ActionEvent event) {
        Quarto selectedRoom = cmbIDQuarto.getValue();
        LocalDate startDate = DatePickerInicio.getValue();
        if (selectedRoom == null || startDate == null) {
            // show error message
            return;
        }

        boolean isAvailable = ReservaBLL.checkAvailability(selectedRoom.getId(), startDate);
        if (!isAvailable) {
            DataInicio.setText("A data escolhida está ocupada!");
            ValidarQuarto.setText("Quarto ocupado na data escolhida!");
            return;
        }

        if (cmbUtilizadores.getItems().isEmpty() == false && cmbIDQuarto.getItems().isEmpty() == false) {
            AdicionarReserva();
        } else {
            EmptyMessage.setText("Preencha todos os campos");
        }
    }



    @FXML
    void clickAddServicoBtn(ActionEvent event) {

    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Pagina GestorController");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }
    void AdicionarReserva() {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();

        String servExtras = "SemExtras";  //falta criar lista de serviços
        Double preco = 0.0;

        // reservas.add(new Reserva(nif, idColaborador, cmbIDQuarto, dataInicioText, dataFimText, servExtras, preco));

        MessageBoxes.ShowMessage(Alert.AlertType.CONFIRMATION,"Comfirmar reserva","Deseja criar esta reserva?");
        Reserva reserva = new Reserva(null,Integer.parseInt(cmbUtilizadores.getValue().getNif()), cmbIDQuarto.getValue().getId(),
                DatePickerInicio.getValue().toString(), DatePickerFim.getValue().toString(), servExtras, preco);
        ReservaBLL reservaBLL = new ReservaBLL();
        try {
            reservaBLL.addReserva(reserva);
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initCombos();
        initTable();
    }

    public void cmbIdQuartoAction(ActionEvent actionEvent) {
        //cmbIDQuarto.getSelectionModel().getSelectedItem().getId().toString();
    }

    public void btnRedictCriarCliente(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoUtilizadores.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnRedictCriarCliente.getScene().getWindow();
        stage.setTitle("Pagina Criar Cliente");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }
    private void initTable() {

        idServicoTable.setResizable(false);
        descricaoServicoTable.setResizable(false);
        precoServicoTable.setResizable(false);

        idServicoTable.setCellValueFactory(new PropertyValueFactory<Servico, Integer>("idServico"));
        descricaoServicoTable.setCellValueFactory(new PropertyValueFactory<Servico, String>("servico"));
        precoServicoTable.setCellValueFactory(new PropertyValueFactory<Servico, Double>("preco"));
        servicosTable.setItems(ServicoBLL.getServicos());
    }

    public void VerificarDisponibilidade() {
        Quarto selectedRoom = cmbIDQuarto.getValue();
        LocalDate startDate = DatePickerInicio.getValue();
        if (selectedRoom == null || startDate == null) {
            // show error message
            return;
        }

        boolean isAvailable = ReservaBLL.checkAvailability(selectedRoom.getId(), startDate);
        if (!isAvailable) {
            DataInicio.setText("A data escolhida está ocupada!");
            ValidarQuarto.setText("Quarto ocupado na data escolhida!");
        } else {
            DataInicio.setText("");
        }
    }
}


