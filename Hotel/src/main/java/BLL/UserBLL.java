package BLL;

import DAL.UtilizadorDAL;
import Model.Utilizador;

import java.sql.Date;
import java.sql.SQLException;

public class UserBLL {
    public Utilizador Login(String utilizador, String password) {
        try {
            UtilizadorDAL dal = new UtilizadorDAL();
            String passwordCifrada = Criptografia.CifraTexto(password);

            return dal.Login(utilizador, passwordCifrada);
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
}
