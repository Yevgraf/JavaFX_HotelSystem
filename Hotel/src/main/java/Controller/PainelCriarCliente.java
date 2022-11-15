package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import Model.Cliente;
import BLL.DBconn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private TableView<?> tv_clientes;

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

    @FXML
    void onActionAddCliente(ActionEvent event) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Cliente(nome, contacto, email, utilizador, password, nif) VALUES (?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, txt_nome.getText());
            ps2.setString(2, txt_contacto.getText());
            ps2.setString(3, txt_email.getText());
            ps2.setString(4, txt_utilizador.getText());
            ps2.setString(5, txt_password.getText());
            ps2.setString(6, txt_nif.getText());
            ps2.setString(8, "cliente");
            ps2.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

        @FXML
        void onActionRemoveCliente(ActionEvent event){

        }

        @FXML
        void onActionUpdateCliente(ActionEvent event){

        }

        @FXML
        void onActionVoltar(ActionEvent event){

        }

    }

