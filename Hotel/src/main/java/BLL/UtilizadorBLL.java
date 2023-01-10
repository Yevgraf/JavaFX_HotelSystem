package BLL;

import DAL.ReservaDAL;
import DAL.ServicoDAL;
import DAL.UtilizadorDAL;
import Model.MessageBoxes;
import Model.Servico;
import Model.Utilizador;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import static Controller.Encriptacao.encrypt;
import static DAL.UtilizadorDAL.nifUtilizador;

public class UtilizadorBLL {
    public Utilizador Login(String utilizador, String password) {
        try {
            UtilizadorDAL dal = new UtilizadorDAL();
            String passwordCifrada = encrypt(password);

            Utilizador loggedIn = dal.Login(utilizador, passwordCifrada);

            UtilizadorPreferences.guardaridCliente(loggedIn.getId());
            UtilizadorPreferences.guardartipoLogin(loggedIn.getTipoUser().getTipo());

            return loggedIn;
        }
        catch (Exception ex) {

        }
        return null;
    }


    public void createUtilizador(String nome, String nif, String morada, Date dataNascimento, String email, String contacto, String utilizador, String password, String tipoUser) {
        try {
            UtilizadorDAL.CriarUtilizador(nome, nif, morada, dataNascimento, email, contacto, utilizador, password, tipoUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUtilizador(int id) throws SQLException {
        UtilizadorDAL udal = new UtilizadorDAL();
        Utilizador utilizador = udal.deleteUtilizador(id);
        if (utilizador != null) {
            udal.deleteUtilizador(id);
        }
    }


    public static Boolean verificarNifUtilizador(int nif) throws SQLException {
        if  (nifUtilizador(nif)){
            return true;
        } else {
            return false;
        }
    }

    public static ObservableList<Utilizador> getAllUtilizadores() {
        return UtilizadorDAL.getTodosUtilizadores();
    }

    public static ObservableList<Utilizador> getAllGestores() {
        return UtilizadorDAL.getGestores();
    }

    public static ObservableList<Utilizador> getAllFuncionarios() {
        return UtilizadorDAL.getFuncionario();
    }

    public static ObservableList<Utilizador> getAllClientes() {
        return UtilizadorDAL.getClientes();
    }

}
