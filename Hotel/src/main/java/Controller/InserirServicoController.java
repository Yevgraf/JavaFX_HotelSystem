package Controller;

import DAL.DBconn;
import Model.MessageBoxes;
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

    @FXML
    void clickAddBtn(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Servico(servico, preco) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, descricao.getText());
            ps2.setDouble(2, Double.parseDouble(preco.getText()));
            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Serviço inserido", "Informação Serviço");
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clickRemoverBtn(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Servico selectedID = servicos.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Servico WHERE id = ?");
                ps2.setInt(1, selectedID.getIdServico());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Serviço Removido", "Informação");

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
    }

    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Painel GestorController");
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
        servicos.setItems(Servico.getServicoTable());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
    }
}
