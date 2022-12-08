package Controller;

import DAL.DBconn;
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
    private CheckBox checkboxWifi;

    @FXML
    private ImageView imgGestorAdionarProd;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private Label lblData;

    @FXML
    private Label lblDataFim;

    @FXML
    private Label lblDataIniicio;

    @FXML
    private Label lblGestaoProdutoAdicionar;

    @FXML
    private Label lblGestaoReservas;

    @FXML
    private Label lblHoras;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblIdClientes;

    @FXML
    private Label lblIdQuarto;

    @FXML
    private Label lblSamos;

    @FXML
    private Label lblServExtra;

    @FXML
    private TableColumn<Quarto, Integer> tbl_id;

    @FXML
    private TableColumn<Quarto, String> tbl_piso;

    @FXML
    private TableColumn<Quarto, Double> tbl_preco;

    @FXML
    private TableColumn<Quarto, String > tbl_tipQuarto;

    @FXML
    private TableColumn<Quarto, Boolean> tbl_wifi;

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
    void clickAddQuarto(ActionEvent event) {
        VerifyCartao();

        if (cmbPiso.getItems().isEmpty() == false && cmbTipoQuarto.getItems().isEmpty() == false && txt_preco.getText().isEmpty() == false && txt_numcartao.getText().isEmpty() == false) {
            if (VerifyCartao() == true) {
                ADDCartao();
                ADDQuarto();
            }
        } else {
            EmptyMessage.setText("Preencha todos os campos");
        }

    }

    void ADDQuarto(){
        PreparedStatement ps2;

        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Quarto (tipoQuarto,piso,preco,numeroCartao, ativo) VALUES (?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, cmbTipoQuarto.getValue());
            ps2.setString(2, cmbPiso.getValue());
            ps2.setDouble(3, Double.parseDouble(txt_preco.getText()));
            ps2.setDouble(4, Double.parseDouble(txt_numcartao.getText()));
            ps2.setBoolean(5,false);

            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Tipo de Quarto inserido", "Informação Tipo de quarto");

        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(ex);

        }

    }


    void ADDCartao(){
        PreparedStatement ps2;

        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Cartao (numeroCartao,ativo) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1,(txt_numcartao.getText()));
            ps2.setBoolean(2,false);

            ps2.executeUpdate();

        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(ex);

        }

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


    private void initTable() {

        tbl_id.setResizable(false);
        tbl_tipQuarto.setResizable(false);
        tbl_piso.setResizable(false);
        tbl_preco.setResizable(false);

        tbl_id.setCellValueFactory(new PropertyValueFactory<Quarto, Integer>("id"));
        tbl_tipQuarto.setCellValueFactory(new PropertyValueFactory<Quarto, String>("tipoQuarto"));
        tbl_piso.setCellValueFactory(new PropertyValueFactory<Quarto, String>("piso"));
        tbl_preco.setCellValueFactory(new PropertyValueFactory<Quarto, Double>("preco"));

        tv_Quarto.setItems(Quarto.getQuarto());
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
        initTable();
        initCombos();
    }


    public void clickEditar(ActionEvent actionEvent) {

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Quarto selectedID = tv_Quarto.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("UPDATE FROM Quarto WHERE id = ?");
                ps2.setString(1, cmbTipoQuarto.getValue());
                ps2.setString(2, cmbPiso.getValue());
                ps2.setDouble(3, Double.parseDouble(txt_preco.getText()));

                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Quarto alterado", "Information");

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clickRmvQuarto(ActionEvent actionEvent) {

        PreparedStatement ps2;
        PreparedStatement ps;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Quarto selectedID = tv_Quarto.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Quarto WHERE id = ?");
                ps = connection.prepareStatement("DELETE FROM Cartao WHERE numeroCartao = ?");
                ps2.setInt(1, selectedID.getId());

                for (int i = 0; i < Cartao.getCartao().size(); i++) {
                    String cartaoN = Cartao.getCartao().get(i).getNumCartao();
                    if (cartaoN.equals(selectedID.getNumCartao())){

                        ps.setString(1,cartaoN);
                    }
                }

                ps2.executeUpdate();
                ps.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Quarto Removido", "Information");

            }
        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Reserva existente com estes dados","Reserva existente");
            throw new RuntimeException(ex);

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

}