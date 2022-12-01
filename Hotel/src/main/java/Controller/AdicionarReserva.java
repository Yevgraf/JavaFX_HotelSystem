package Controller;

import BLL.DBconn;
import Model.Quarto;
import Model.Reserva;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private ComboBox<Quarto> cmbIDQuarto;

    @FXML
    private TextField dataFim;

    @FXML
    private TextField dataInicio;

    @FXML
    private TableColumn<?, ?> descricaoServicoTable;

    @FXML
    private TableColumn<?, ?> idServicoTable;

    @FXML
    private TextField nifCliente;

    @FXML
    private TableColumn<?, ?> precoServicoTable;

    @FXML
    private TableView<?> servicosTable;

    @FXML
    private TextField txtPreco;

    @FXML
    private Button voltarBtn;


    @FXML
    void clickAddReservaBrn(ActionEvent event) {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList();
        Integer nif = 0;              //vai verificar se nof existe
        Integer idColadorador = 0;    //vai ser substituído pelo GET depois do login feito

        String dataInicioText = dataInicio.getText();
        String dataFimText = dataFim.getText();
        String servExtras = "SemExtras Teste";  //falta criar lista de serviços
        Double preco = 0.0;

       // reservas.add(new Reserva(nif, idColadorador, cmbIDQuarto, dataInicioText, dataFimText, servExtras, preco));

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Reserva(nifCliente, idColaborador, idQuarto," +
                    "dataInicio, dataFim, servExtra, preco)" +
                    "VALUES (?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setInt(1, nif);
            ps2.setInt(2, idColadorador);

         //   ps2.setInt(3, cmbIDQuarto);
            ps2.setString(4, dataInicioText);
            ps2.setString(5, dataFimText);
            ps2.setString(6, servExtras);
            ps2.setDouble(7, preco);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void initCombos() {

        for (int i = 0; i < Quarto.getQuarto().size(); i++) {
            if (Quarto.getQuarto().get(i).getAtivo() == false) ;
            cmbIDQuarto.getItems().addAll(Quarto.getQuarto());
        }
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

    }

    public void cmbIdQuartoAction(ActionEvent actionEvent) {
        cmbIDQuarto.getSelectionModel().getSelectedItem().getId().toString();
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

    public void cmbClienteAction(ActionEvent actionEvent) {
    }
}
