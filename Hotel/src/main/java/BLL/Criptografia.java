package BLL;

public class Criptografia {

    public static String CifraTexto(String textoACifrar) {

        int contador, tamanho, codigoASCII;
        String cifrada = "";

        tamanho = textoACifrar.length();
        textoACifrar = textoACifrar.toUpperCase();
        contador = 0;

        while (contador < tamanho) {
            codigoASCII = textoACifrar.charAt(contador) + 130;
            cifrada += (char) codigoASCII;
            contador++;
        }

        return cifrada;
    }
}
