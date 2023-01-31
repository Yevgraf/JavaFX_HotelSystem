package BLL;

import java.util.prefs.Preferences;

public class UtilizadorPreferences {

    private static String tipoUtilizador = "";

    private static Integer utilizadorId;


    public static void guardartipoLogin(String tipo) {
        // Set the login type
        tipoUtilizador = tipo;
    }

    public static void apagarTipoLogin() {
        // Remove the login type preference
        tipoUtilizador = "";
    }

    public static int masterCardId() {
        int masterCardId = 9999;
        return masterCardId;
    }

    public static boolean useMasterCard() {
        if (getTipoLogin().equals("Gestor") || getTipoLogin().equals("Funcionario")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean comparaTipoLogin(){
        if (tipoUtilizador.equals("Gestor")){
            return true;
        } else {
            return false;
        }
    }

    public static void guardaridCliente(Integer id) {
        // Set the client id
        utilizadorId = id;
    }

    public static Integer utilizadorId() {
        // Get the login type
        return utilizadorId;
    }

    public static String getTipoLogin() {
        // Get the login type
        return tipoUtilizador;
    }

    public static boolean comparaTipoFuncionario() {
        if (tipoUtilizador.equals("Funcionario")){
            return true;
        } else {
            return false;
        }
    }

    public static boolean comparaTipoUtilizador() {
        if (tipoUtilizador.equals("Cliente")){
            return true;
        } else {
            return false;
        }
    }
}


