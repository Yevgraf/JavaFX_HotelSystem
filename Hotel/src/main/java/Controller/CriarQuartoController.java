package Controller;

import BLL.QuartoBLL;
import DAL.DBconn;
import DAL.QuartoDAL;
import Model.*;
import com.example.hotel.Main;
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

    private QuartoBLL quartoBLL = new QuartoBLL();

    @FXML
    void clickAddQuarto(ActionEvent event) throws SQLException {
        VerifyCartao();

        if (cmbPiso.getItems().isEmpty() == false && cmbTipoQuarto.getItems().isEmpty() == false && txt_preco.getText().isEmpty() == false && txt_numcartao.getText().isEmpty() == false) {
            if (VerifyCartao() == true) {
                ADDQuarto();
            }
        } else {
            EmptyMessage.setText("Preencha todos os campos");
        }

    }

    public void ADDQuarto() throws SQLException {
        // create a quarto object with the values from the UI controls
        Quarto quarto = new Quarto(
                null,
                cmbTipoQuarto.getValue(),
                cmbPiso.getValue(),
                Double.parseDouble(txt_preco.getText()),
                txt_numcartao.getText(),
                false
        );

        // create a cartao object with the values from the UI controls
        Cartao cartao = new Cartao(
                null,
                txt_numcartao.getText(),
                true
        );

        // add the quarto and cartao to the database using the BLL
        quartoBLL.addQuarto(quarto, cartao);
    }


    @FXML
    void clickVoltarBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelGestor.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) voltarBtn.getScene().getWindow();
        stage.setTitle("Adicionar Tipo de quarto");
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
        QuartoBLL bll = new QuartoBLL();
        Quarto selectedQuarto = tv_Quarto.getSelectionModel().getSelectedItem();
        if (selectedQuarto != null) {
            try {
                bll.removeQuarto(selectedQuarto.getId());
                tv_Quarto.getItems().remove(selectedQuarto);
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Quarto Removido", "Information");
            } catch (SQLException ex) {
                MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Reserva existente com estes dados", "Reserva existente");
                throw new RuntimeException(ex);
            }
        }
    }

    public void RedirectProdutoQuarto(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GestaoProdutoQuarto.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) ProdutoQuarto.getScene().getWindow();
        stage.setTitle("Adicionar Tipo de quarto");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    public boolean VerifyCartao() {
        boolean flag;
        String verificar = "SELECT count(1) FROM Cartao WHERE numeroCartao ='" + txt_numcartao.getText() + "'";
        try {
            PreparedStatement stmt = DBconn.getConn().prepareStatement(verificar);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    ValidarCartao.setText("O cartao já existe!");
                } else {
                    ValidarCartao.setText("");
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        if (ValidarCartao.getText().equals("O cartao já existe!")) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    private void initTable() {
        tbl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_piso.setCellValueFactory(new PropertyValueFactory<>("piso"));
        tbl_preco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tbl_tipQuarto.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));
        tv_Quarto.setItems(QuartoBLL.getQuartos());
    }

}