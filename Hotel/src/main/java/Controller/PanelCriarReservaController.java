package Controller;

import Model.Colaborador;
import Model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import BLL.DBconn;

public class PanelCriarReservaController {
    @FXML
    private AnchorPane PainelGestorReservaAdicionar;

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
    private ImageView imgFuncGestaoReserva;

    @FXML
    private ImageView imgFuncAdionarReserva;

    @FXML
    private Button btnGestorAdiconarReserva;

    @FXML
    private Label lblPainelFunc;

    @FXML
    private Label lblBemVindoAdm;

    @FXML
    private Label lblHoras;

    @FXML
    private Label lblData;

    @FXML
    private Label lblSamos;

    @FXML
    private Label lblHotel;

    @FXML
    private Label lblGestaoReservas;

    @FXML
    private Label lblGestaoReservaAdicionar;

    @FXML
    private Label lblIdClientes;

    @FXML
    private Label lblPreço;

    @FXML
    private Label lblDataInicio;

    @FXML
    private Label lblDataFim;

    @FXML
    private Label lblServExtra;

    @FXML
    private Label lblIDReserva;

    @FXML
    private Label lblIdColab;

    @FXML
    private Label lblIdQuarto;

    @FXML
    private Label lblIdCartao;

    @FXML
    private TextField txtDataInicio;

    @FXML
    private TextField txtDataFim;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtIDReserva;

    @FXML
    private ComboBox<Reserva> cmbIDCliente;

    @FXML
    private ComboBox<Reserva> cmbIDColaborador;

    @FXML
    private ComboBox<Reserva> cmbIDQuarto;

    @FXML
    private ComboBox<Reserva> cmbIDCartao;

    @FXML
    private CheckBox cbServExtra;

    void setCmbIDCliente()
    {
        DBconn dbConn = new DBconn();
        String Query = "select * from Cliente";

        try
        {
            psmt = dbConn.pre
            BDconnection.Open();
            reader = cmddatabase.ExecuteReader();

            while (reader.Read())
            {

                string sName = reader.GetString("NOME");
                CMBvendedor.Items.Add(sName);

            }
            BDconnection.Close();
        }

        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);

        }

    }


}
