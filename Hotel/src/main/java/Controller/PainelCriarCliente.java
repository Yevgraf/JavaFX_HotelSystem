package Controller;

import Model.Cliente;
import Model.Colaborador;
import Model.MessageBoxes;
import com.example.hotel.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import BLL.DBconn;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PainelCriarCliente {

    @FXML
    private AnchorPane PainelCriarCliente;

    @FXML
    private ImageView btnBack;

    @FXML
    private ImageView btnCloseApp;

    @FXML
    private ImageView btnDefGestor;

    @FXML
    private ImageView btnLogOut;

    @FXML
    private ImageView btnMinimizateApp;

    @FXML
    private Button btn_add_cliente;

    @FXML
    private Button btn_remove_cliente;

    @FXML
    private Button btn_update_Voltar;

    @FXML
    private Button btn_update_cliente;

    @FXML
    private ImageView imgGestorAdionarProd;

    @FXML
    private ImageView imgGestorGestaoProduto;

    @FXML
    private Label lblGestaoProdutos;

    @FXML
    private Label lblHoras11;

    @FXML
    private Label lblHoras111;

    @FXML
    private Label lblHoras1211;

    @FXML
    private Label lblHoras13;

    @FXML
    private Label lblHoras131;

    @FXML
    private Label lblHoras1311;

    @FXML
    private Label lblSamos;

    @FXML
    private TableColumn<Cliente, String> tbl_contacto;

    @FXML
    private TableColumn<Cliente, String> tbl_email;

    @FXML
    private TableColumn<Cliente, String> tbl_name;

    @FXML
    private TableColumn<Cliente, String> tbl_nif;

    @FXML
    private TableColumn<Cliente, String> tbl_password;

    @FXML
    private TableColumn<Cliente, String> tbl_utilizador;

    @FXML
    private TableView<Cliente> tv_clientes;

    @FXML
    private TextField txt_contacto;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_nif;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_utilizador;

    ObservableList<Cliente> clientlist = FXCollections.observableArrayList();

    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;


    @FXML
    void clickAddClienteBtn(ActionEvent event) {

    }

    @FXML
    void onActionAddCliente(ActionEvent event) {

        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();
        PreparedStatement ps2;
        int contador, tamanho, codigoASCII;
        String password;
        String passwordCriptografada = "";
        try {
            ps2 = connection.prepareStatement("INSERT INTO Cliente(nome, contacto, email, utilizador, palavrapasse, nif) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_nome.getText());
            ps2.setString(2, txt_contacto.getText());
            ps2.setString(3, txt_email.getText());
            ps2.setString(4, txt_utilizador.getText());

            ps2.setString(6, txt_nif.getText());
            password = txt_password.getText();
            tamanho = password.length();
            password = password.toUpperCase();
            contador = 0;

            while (contador < tamanho) {
                codigoASCII = password.charAt(contador) + 130;
                passwordCriptografada = passwordCriptografada + (char) codigoASCII;
                contador++;
            }
            ps2.setString(5, passwordCriptografada);

            ps2.executeUpdate();
            MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Cliente inserido", "Informação Cliente");


        } catch (SQLException ex) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Introduza os dados corretamente", "Erro Inserir");
            throw new RuntimeException(ex);

        }
    }




    @FXML
    void onActionRemoveCliente(ActionEvent event) {

        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            Cliente selectedID = (Cliente) tv_clientes.getSelectionModel().getSelectedItem();
            if (selectedID != null) {
                ps2 = connection.prepareStatement("DELETE FROM Cliente WHERE id = ?");
                ps2.setInt(1, selectedID.getNif());
                ps2.executeUpdate();
                MessageBoxes.ShowMessage(Alert.AlertType.INFORMATION, "Cliente Removido", "Information");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @FXML
    void onActionUpdateCliente(ActionEvent event) throws IOException, SQLException {
            /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelCriarCliente.fxml"));
            Stage stage = new Stage();
            Stage newStage = (Stage) btn_update_cliente.getScene().getWindow();
            stage.setTitle("Criar Cliente");
            newStage.hide();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
             */

        try {
            clientlist.clear();

            String tablequery = "Select * FROM Cliente";
            preparedStatement = DBconn.getConn().prepareStatement(tablequery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clientlist.add(new Cliente(
                        resultSet.getString("nome"),
                        resultSet.getString("contacto"),
                        resultSet.getString("email"),
                        resultSet.getString("utilizador"),
                        resultSet.getString("password"),
                        resultSet.getInt("nif")));
                tv_clientes.setItems(clientlist);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PainelCriarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void onActionVoltar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PainelFuncionario_GestaoCliente.fxml"));
        Stage stage = new Stage();
        Stage newStage = (Stage) btn_update_Voltar.getScene().getWindow();
        stage.setTitle("Pagina Gestor");
        newStage.hide();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

}

