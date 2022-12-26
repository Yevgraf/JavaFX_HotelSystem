package BLL;

public class UtilizadorPreferences {

    private static String tipoUtilizador = "";

    public static void guardartipoLogin(String tipo) {
        // Set the login type
        tipoUtilizador = tipo;
    }

    public static String getTipoLogin() {
        // Get the login type
        return tipoUtilizador;
    }

    public static void apagarTipoLogin() {
        // Remove the login type preference
        tipoUtilizador = "";
    }

    public static boolean comparaTipoLogin(){
        if (tipoUtilizador.equals("gestor")){
            return true;
        } else {
            return false;
        }
    }
}


