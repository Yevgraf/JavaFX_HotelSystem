package Controller;

import BLL.ReservaBLL;
import BLL.ServicoBLL;
import BLL.UtilizadorBLL;
import DAL.DBconn;
import DAL.QuartoDAL;
import DAL.UtilizadorDAL;
import Model.*;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    static List dates = new ArrayList<>();

    static boolean vD;


    @FXML
    void clickAddReservaBrn(ActionEvent event) throws SQLException {
        Quarto selectedRoom = cmbIDQuarto.getValue();
        LocalDate startDate = DatePickerInicio.getValue();
        if (selectedRoom == null || startDate == null) {
            // show error message
            return;
        }

        if (cmbClientes.getItems().isEmpty() == false && cmbIDQuarto.getItems().isEmpty() == false) {
            //if (VerificarDisponibilidade() == true){
                AdicionarReserva();
            //}
        } else {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Preencha todos os campos!","Erro");
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
    void AdicionarReserva() throws SQLException {
        if(DatePickerFim.getValue().isBefore(DatePickerInicio.getValue())){
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
    }

    private void initCombos() {

        cmbIDQuarto.getItems().addAll(QuartoDAL.getAllQuartos().stream()
                .filter(quarto -> !quarto.getAtivo())
                .collect(Collectors.toList()));

        cmbClientes.getItems().addAll(UtilizadorBLL.getAllClientes().stream().collect(Collectors.toList()));
    }

    public void cmbIdQuartoAction(ActionEvent actionEvent) {
        //cmbIDQuarto.getSelectionModel().getSelectedItem().getId().toString();
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

    public boolean VerificarDisponibilidade(){
        String verificar = "SELECT dataInicio FROM Reserva WHERE idQuarto ='" + cmbIDQuarto.getValue().getId() +
                "' And dataInicio like '" + DatePickerInicio.getValue().toString() + "'";
        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(verificar)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                dates = getDatesBetweenUsingJava7(rs.getDate("dataInicio"),rs.getDate("dataFim"));
                if (rs.getString("dataInicio").equals(dates)) {
                    EmptyMessage.setText("Limite de Quartos Alcancado!");
                    vD = false;
                }else{
                    vD = true;
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        return vD;
    }

    public static List getDatesBetweenUsingJava7(Date startDate, Date endDate) {
        List datesInRange = new ArrayList<>();
        Calendar calendar = getCalendarWithoutTime(startDate);
        Calendar endCalendar = getCalendarWithoutTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return datesInRange;
    }

    private static Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

        /*boolean isAvailable = ReservaBLL.checkAvailability(selectedRoom.getId(), startDate);
        if (!isAvailable) {
            DataInicio.setText("A data escolhida está ocupada!");
            ValidarQuarto.setText("Quarto ocupado na data escolhida!");
        } else {
            DataInicio.setText("");
        }
    }*/
}


