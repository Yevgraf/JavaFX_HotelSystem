package BLL;

import DAL.UserDAL;
import Model.User;

public class UserBLL {
    public User Login(String utilizador, String password) {
        try {
            UserDAL dal = new UserDAL();
            String passwordCifrada = Criptografia.CifraTexto(password);

            return dal.Login(utilizador, passwordCifrada);
        }
        catch (Exception ex) {

        }
        return null;
    }
}
