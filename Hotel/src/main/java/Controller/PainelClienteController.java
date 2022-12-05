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

public class PainelClienteController {

    @FXML
    private AnchorPane PanelCliente;

    @FXML
    private ImageView imgGestorColab;

    @FXML
    private ImageView btnDefClinte;

    @FXML
    private ImageView imgLogOut;

    @FXML
    private ImageView imgQuarto;

    @FXML
    private ImageView imgGinasio;

    @FXML
    private ImageView imgCozinha;

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

}
