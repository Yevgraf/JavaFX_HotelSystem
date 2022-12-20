package Controller;

import BLL.QuartoBLL;
import BLL.ServicoBLL;
import DAL.DBconn;
import Model.Cartao;
import Model.MessageBoxes;
import Model.Quarto;
import Model.Servico;
import com.example.hotel.Main;
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

public class InserirServicoController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private TextField descricao;

    @FXML
    private TableColumn<Servico, String> descricaoTable;

    @FXML
    private TableColumn<Servico, Integer> idTable;

    @FXML
    private TextField preco;

    @FXML
    private TableColumn<Servico, Double> precoTable;

    @FXML
    private Button removerBtn;

    @FXML
    private TableView<Servico> servicos;

    @FXML
    private Button voltarBtn;

    private ServicoBLL servicoBLL = new ServicoBLL();

    @FXML
    void clickAddBtn(ActionEvent event) throws SQLException {
        if (descricao.getText().isEmpty() == false && preco.getText().isEmpty() == false) {
            AddServico();
        } else {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Introduza todos os campos","Erro");
        }
    }

    public void AddServico() throws SQLException {
        Servico servico = new Servico(
                null,
                descricao.getText(),
                Double.parseDouble(preco.getText()));

        servicoBLL.addServico(servico);
        initTable();
    }

    @FXML
    void clickRemoverBtn(ActionEvent event) {
        ServicoBLL bll = new ServicoBLL();
        Servico selectedServico = servicos.getSelectionModel().getSelectedItem();
        if (selectedServico != null) {
            try {
                bll.removeServico(selectedServico.getIdServico());
                servicos.getItems().remove(selectedServico);
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Servico Removido", "Information");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Painel Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    private void initTable() {

        idTable.setResizable(false);
        descricaoTable.setResizable(false);
        precoTable.setResizable(false);

        idTable.setCellValueFactory(new PropertyValueFactory<Servico, Integer>("idServico"));
        descricaoTable.setCellValueFactory(new PropertyValueFactory<Servico, String>("servico"));
        precoTable.setCellValueFactory(new PropertyValueFactory<Servico, Double>("preco"));
        servicos.setItems(ServicoBLL.getServicos());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}
