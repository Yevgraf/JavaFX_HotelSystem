package Controller;

import DAL.DBconn;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ComboBox<Cliente> cmbClientes;

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
        VerificarDisponibilidade();
        if (cmbClientes.getItems().isEmpty() == false && cmbIDQuarto.getItems().isEmpty() == false) {
            if (VerificarDisponibilidade() == true) {
                AdicionarReserva();
            }
        }else {
            EmptyMessage.setText("Preencha todos os campos");
        }
    }

    void AdicionarReserva() {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();

        Integer idColadorador = 3;    //vai ser substituído pelo GET depois do login feito

        String servExtras = "SemExtras";  //falta criar lista de serviços
        Double preco = 0.0;

        // reservas.add(new Reserva(nif, idColadorador, cmbIDQuarto, dataInicioText, dataFimText, servExtras, preco));

        MessageBoxes.ShowMessage(Alert.AlertType.CONFIRMATION,"Comfirmar reserva","Deseja criar esta reserva?");
        PreparedStatement ps2;
        PreparedStatement ps3;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Reserva(nifCliente, idColaborador, idQuarto," +
                    "dataInicio, dataFim, servExtra, preco)" +
                    "VALUES (?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ps2.setInt(1, cmbClientes.getValue().getNif());
            ps2.setInt(2, idColadorador);
            ps2.setInt(3, cmbIDQuarto.getValue().getId());


            ps2.setString(4, DatePickerInicio.getValue().toString());
            ps2.setString(5, DatePickerFim.getValue().toString());
            ps2.setString(6, servExtras);
            ps2.setDouble(7, preco);
            ps2.executeUpdate();
//            ps3 = connection.prepareStatement("UPDATE Quarto SET ativo=? WHERE id=?");
//            ps3.setBoolean(1,true);
//            ps3.setInt(2,cmbIDQuarto.getValue().getId().intValue());
//            ps3.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
  // private void initCombos() {
  //     for (int i = 0; i < Quarto.getQuarto().size(); i++) {
  //         if (Quarto.getQuarto().get(i).getAtivo() == false){
  //          Quarto quartoFalse = Quarto.getQuarto().get(i);
  //             cmbIDQuarto.getItems().addAll(quartoFalse);
  //         }
  //     }
  //     cmbClientes.getItems().addAll(Utilizador.getClientes());
  // }



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
        servicosTable.setItems(Servico.getServicoTable());
    }

    public boolean VerificarDisponibilidade() {
        boolean flag;
            String verificar = "SELECT dataInicio, dataFim FROM Reserva WHERE idQuarto ='" + cmbIDQuarto.getValue().getId() +
                    "' And dataInicio ='" + DatePickerInicio.getValue().toString() + "'";
            try {
                PreparedStatement stmt = DBconn.getConn().prepareStatement(verificar);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    if (rs.getString("dataInicio").equals(DatePickerInicio.getValue().toString())) {
                        DataInicio.setText("A data escolhida está ocupada!");
                        ValidarQuarto.setText("Quarto ocupado na data escolhida!");
                    } else {
                        DataInicio.setText("");
                    }
                }
            } catch (SQLException e) {
                e.getCause();
            }
        if (DataInicio.getText().equals("A data escolhida está ocupada!")) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }
}


