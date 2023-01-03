package Controller;
import DAL.ServicoDAL;
import DAL.StockDAL;
import Model.Servico;
import Model.Stock;
import com.example.hotel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    }

    private void initTable() {
        idTable.setResizable(false);
        descricaoTable.setResizable(false);

        idTable.setCellValueFactory(new PropertyValueFactory<Servico, Integer>("idServico"));
        descricaoTable.setCellValueFactory(new PropertyValueFactory<Servico, String>("servico"));
        servicos.setItems(ServicoDAL.getServicosByClientId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}
