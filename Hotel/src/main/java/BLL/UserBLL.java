package BLL;

import DAL.UtilizadorDAL;
import Model.Utilizador;

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
}
