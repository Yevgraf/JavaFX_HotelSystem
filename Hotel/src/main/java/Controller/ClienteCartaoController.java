package Controller;
import BLL.RegistoBLL;
import BLL.UtilizadorPreferences;
import DAL.ServicoDAL;
import DAL.StockDAL;
import Model.MessageBoxes;
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
import java.util.prefs.BackingStoreException;

public class ClienteCartaoController implements Initializable {

    @FXML
    private TableColumn<Servico, String> descricaoTable;

    @FXML
    private TableColumn<Servico, Integer> idTable;

    @FXML
    private TableView<Servico> servicos;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button passarCartao;



    @FXML
    void clickBtnVoltar(ActionEvent event) throws IOException {
        if (UtilizadorPreferences.comparaTipoLogin()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) btnVoltar.getScene().getWindow();
            stage.setTitle("Pagina Gestor");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } else if (UtilizadorPreferences.comparaTipoFuncionario()){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) btnVoltar.getScene().getWindow();
            stage.setTitle("Pagina Funcionario");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } else if (UtilizadorPreferences.comparaTipoUtilizador()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCliente.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) btnVoltar.getScene().getWindow();
            stage.setTitle("Pagina Cliente");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }
    }

    @FXML
    void passarCartao(ActionEvent event) {
        boolean useMasterCard = UtilizadorPreferences.useMasterCard();
        try {
            int idCliente = UtilizadorPreferences.utilizadorId();
            int idCartao;
            if (useMasterCard) {
                idCartao = UtilizadorPreferences.masterCardId();
            } else {
                idCartao = RegistoBLL.getCardIdByClientId(idCliente);
            }
            String local = servicos.getSelectionModel().getSelectedItem().getServico();
            Calendar calendar = Calendar.getInstance();
            java.util.Date utilDate = calendar.getTime();
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

            RegistoBLL.addNewRegisto(idCartao, idCliente, local, sqlTimestamp);
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION,"Registo gravado","Gravado");
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"falha ao gravar registo","ERRO");
            e.printStackTrace();
        }
    }




    private void initTable() {
        idTable.setResizable(false);
        descricaoTable.setResizable(false);

        idTable.setCellValueFactory(new PropertyValueFactory<Servico, Integer>("idServico"));
        descricaoTable.setCellValueFactory(new PropertyValueFactory<Servico, String>("servico"));
        if(UtilizadorPreferences.comparaTipoFuncionario()) {
            servicos.setItems(ServicoDAL.getAllServicos());
        } else if(UtilizadorPreferences.comparaTipoLogin()) {
            servicos.setItems(ServicoDAL.getAllServicosAndQuartos());
        }else {
            servicos.setItems(ServicoDAL.getServicosByClientId());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}
