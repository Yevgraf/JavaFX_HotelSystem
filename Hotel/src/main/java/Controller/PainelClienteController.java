package Controller;

import Model.Cliente;
import Model.Colaborador;
import Model.MessageBoxes;
import Model.Servico;
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
    private AnchorPane PainelCliente;

    @FXML
    private ImageView imgServic;

    @FXML
    private ImageView btnDefClinte;

    @FXML
    private ImageView imgLogOut;

    @FXML
    private Button btn_SairServ;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblSamos;

    @FXML
    private Label lblServ;

    @FXML
    private Label lblEncontr;

    @FXML
    private Label lblData;

    @FXML
    private Label lblHoras;

    @FXML
    private TableColumn<Servico, String> tbl_Id;

    @FXML
    private TableColumn<Servico, String> tbl_Desc;

    @FXML
    private TableColumn<Servico, String> tbl_preco;

    @FXML
    private TableView<Servico> tv_Servicos;
    

}
