package Controller;

import BLL.DBconn;
import Model.Cliente;
import Model.MessageBoxes;
import Model.Quarto;
import Model.Reserva;
import Model.Servico;
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

public class AdicionarReserva implements Initializable {
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
    private void initCombos() {
        for (int i = 0; i < Quarto.getQuarto().size(); i++) {
            if (Quarto.getQuarto().get(i).getAtivo() == false){
             Quarto quartoFalse = Quarto.getQuarto().get(i);
                cmbIDQuarto.getItems().addAll(quartoFalse);
            }
        }
        cmbClientes.getItems().addAll(Cliente.getCliente());
    }



    @FXML
    void clickAddServicoBtn(ActionEvent event) {

    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario_GestaoReservas.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
        initTable();
    }

    public void cmbIdQuartoAction(ActionEvent actionEvent) {
        //cmbIDQuarto.getSelectionModel().getSelectedItem().getId().toString();
    }

    public void btnRedictCriarCliente(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCriarCliente.fxml"));
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


}


