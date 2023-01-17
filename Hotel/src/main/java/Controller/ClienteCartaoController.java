package Controller;
import BLL.RegistoBLL;
import BLL.UtilizadorPreferences;
import DAL.QuartoDAL;
import DAL.ReservaDAL;
import DAL.ServicoDAL;
import DAL.StockDAL;
import Model.MessageBoxes;
import Model.Quarto;
import Model.Servico;
import Model.Stock;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ClienteCartaoController implements Initializable {

    @FXML
    private TableColumn<Servico, String> descricaoTable;

    @FXML
    private TableColumn<Servico, Integer> idTable;

    @FXML
    private TableView<Servico> servicos;

    @FXML
    private TableColumn<Quarto, String> descricaoQuarto;

    @FXML
    private TableColumn<Quarto, Integer> idQuarto;

    @FXML
    private TableView<Quarto> quartos;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button passarCartao;



    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCliente.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btnVoltar.getScene().getWindow();
        stage.setTitle("Pagina cliente");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void passarCartao(ActionEvent event) {
        try {
            int idCliente = UtilizadorPreferences.utilizadorId();
            int idCartao = RegistoBLL.getCardIdByClientId(idCliente);
            int idQuarto = RegistoBLL.getRoomIdByClientId(idCliente);
            String local = servicos.getSelectionModel().getSelectedItem().getServico();
            String localQuarto = quartos.getSelectionModel().getSelectedItem().getTipoQuarto();
            Calendar calendar = Calendar.getInstance();
            java.util.Date utilDate = calendar.getTime();
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

            RegistoBLL.addNewRegisto( idCartao, idCliente, local, sqlTimestamp);
            RegistoBLL.addNewRegistoQuarto(idQuarto, idCliente, localQuarto, sqlTimestamp);
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION,"Registo gravado","Gravado");
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"falha ao gravar registo","ERRO");
            e.printStackTrace();
        }
    }


    private void initTable() {
        ServicoDAL servicoDAL = new ServicoDAL();

        idTable.setCellValueFactory(new PropertyValueFactory<>("idServico"));
        descricaoTable.setCellValueFactory(new PropertyValueFactory<>("servico"));
        idQuarto.setCellValueFactory(new PropertyValueFactory<>("id"));
        descricaoQuarto.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));

        servicos.setItems(servicoDAL.getServicosByClientId());
        quartos.setItems(QuartoDAL.getQuartoByClientId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}
