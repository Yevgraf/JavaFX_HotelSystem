package Controller;

import BLL.DBconn;
import Model.Reserva;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdicionarReserva {

    @FXML
    private Button addServicoBtn;

    @FXML
    private Button adicionarReservaBtn;

    @FXML
    private ComboBox<?> cmbIDQuarto;

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
        Integer idQuarto = 0;         //falta fazer quartos, vai listar quartos disponíveis
        String dataInicioText = dataInicio.getText();
        String dataFimText = dataFim.getText();
        String servExtras = "SemExtras Teste";  //falta criar lista de serviços
        Double preco = 0.0;

        reservas.add(new Reserva(nif, idColadorador, idQuarto, dataInicioText, dataFimText, servExtras, preco));

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Reserva(nifCliente, idColaborador, idQuarto," +
                    "dataInicio, dataFim, servExtra, preco)" +
                    "VALUES (?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setInt(1, nif);
            ps2.setInt(2, idColadorador);
            ps2.setInt(3, idQuarto);
            ps2.setString(4, dataInicioText);
            ps2.setString(5, dataFimText);
            ps2.setString(6, servExtras);
            ps2.setDouble(7, preco);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    @FXML
    void dataFim(ActionEvent event) {

    }

}
