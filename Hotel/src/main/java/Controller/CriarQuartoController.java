package Controller;

import BLL.CartaoBLL;
import BLL.QuartoBLL;
import BLL.UtilizadorPreferences;
import DAL.DBconn;
import DAL.QuartoDAL;
import Model.*;
import com.example.hotel.Main;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CriarQuartoController implements Initializable {

    @FXML
    private Button BtnAddProduto;

    @FXML
    private Label ValidarPiso;

    @FXML
    private Label EmptyMessage;

    @FXML
    private Label ValidarCartao;

    @FXML
    private Button ProdutoQuarto;

    @FXML
    private AnchorPane CriarQuarto;

    @FXML
    private Button btnAddQuarto;

    @FXML
    private Button btnAddTipoQuarto;

    @FXML
    private ImageView btnBack;

    @FXML
    private ComboBox<String> cmbPiso;

    @FXML
    private ComboBox<String> cmbTipoQuarto;

    @FXML
    private TableColumn<Quarto, Integer> tbl_id;

    @FXML
    private TableColumn<Quarto, String> tbl_piso;

    @FXML
    private TableColumn<Quarto, Double> tbl_preco;

    @FXML
    private TableColumn<Quarto, String> tbl_tipQuarto;

    @FXML
    private TableColumn<Quarto, String> tbl_idCartao;

    @FXML
    private TableView<Quarto> tv_Quarto;

    @FXML
    private TextField txt_numcartao;

    @FXML
    private TextField txt_piso;

    @FXML
    private TextField txt_preco;

    @FXML
    private Button btnRmvQuarto;

    @FXML
    private Button btnEditar;
    @FXML
    private Button voltarBtn;

    @FXML
    private Button addProdutosQuarto;

    private QuartoBLL quartoBLL = new QuartoBLL();

    @FXML
    void clickAddQuarto(ActionEvent event) throws SQLException {

        if (cmbPiso.getItems().isEmpty() == false && cmbTipoQuarto.getItems().isEmpty() == false && txt_preco.getText().isEmpty() == false && txt_numcartao.getText().isEmpty() == false) {
            ADDQuarto();
        } else {
            EmptyMessage.setText("Preencha todos os campos");
        }
    }

    public void ADDQuarto() throws SQLException {
        try {
            String floor = cmbPiso.getValue();
            String roomType = cmbTipoQuarto.getValue();

            if (floor != null && (floor.equals("1") || floor.equals("2"))) {
                if (roomType != null && (roomType.equals("Singular") || roomType.equals("Duplo") || roomType.equals("Familiar"))) {
                    Quarto quarto = new Quarto(
                            null,
                            roomType,
                            floor,
                            Double.parseDouble(txt_preco.getText().trim()),
                            false,
                            new Cartao(Integer.parseInt(txt_numcartao.getText().trim())));

                    quartoBLL.addQuarto(quarto);
                    initTable();
                } else {
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Tipo quarto invalido", "Erro!");
                }
            } else {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Piso invalido", "Erro!");
            }
        } catch (NumberFormatException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Apenas números para o ID Cartão", "Erro!");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Não foi possível adicionar o quarto.", "Erro!");
            throw new RuntimeException(e);
        }
    }


    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {

        if (UtilizadorPreferences.comparaTipoLogin()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) voltarBtn.getScene().getWindow();
            stage.setTitle("Pagina Gestor");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) voltarBtn.getScene().getWindow();
            stage.setTitle("Pagina Funcionario");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }

    }

    @FXML
    void addProdutosQuartoClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoProdutoQuarto.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) addProdutosQuarto.getScene().getWindow();
        stage.setTitle("Gestao Produtos Quarto");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }


    private void initCombos() {
        cmbTipoQuarto.getItems().add("Singular");
        cmbTipoQuarto.getItems().add("Duplo");
        cmbTipoQuarto.getItems().add("Familiar");
        cmbPiso.getItems().add("1");
        cmbPiso.getItems().add("2");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
        initTable();
    }


    public void clickEditar(ActionEvent actionEvent) {
        // get the selected quarto
        Quarto selectedQuarto = tv_Quarto.getSelectionModel().getSelectedItem();

        // check if a quarto is selected
        if (selectedQuarto != null) {
            // update the quarto with the values from the UI controls
            selectedQuarto.setTipoQuarto(cmbTipoQuarto.getValue());
            selectedQuarto.setPiso(cmbPiso.getValue());
            selectedQuarto.setPreco(Double.parseDouble(txt_preco.getText()));

            // update the quarto in the database using the BLL
            quartoBLL.updateQuarto(selectedQuarto);

            // show a success message
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Quarto alterado", "Information");
        }
    }

    public void clickRmvQuarto(ActionEvent actionEvent) {
        QuartoBLL quartoBLL = new QuartoBLL();
        CartaoBLL cartaoBLL = new CartaoBLL();
        Quarto selectedQuarto = tv_Quarto.getSelectionModel().getSelectedItem();
        if (selectedQuarto != null) {
            try {
                // delete the entry in the Quarto table
                quartoBLL.removeQuarto(selectedQuarto.getId());

                // delete the entry in the Cartao table
                cartaoBLL.deleteCartao(selectedQuarto.getCartao().getId());

                tv_Quarto.getItems().remove(selectedQuarto);
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Quarto Removido", "Information");
            } catch (SQLException ex) {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Reserva existente com estes dados", "Reserva existente");
                throw new RuntimeException(ex);
            }
        } else {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Selecione um quarto para eliminar!", "Erro:");
        }
    }

    private void initTable() {
        tbl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_piso.setCellValueFactory(new PropertyValueFactory<>("piso"));
        tbl_preco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tbl_tipQuarto.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));
        tbl_idCartao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCartao().getId().toString()));
        tv_Quarto.setItems(QuartoBLL.getQuartos());
    }

}