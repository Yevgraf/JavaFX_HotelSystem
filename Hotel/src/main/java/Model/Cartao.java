package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Cartao {

    private String numCartao;
    private boolean cartaoMestre;

    public Cartao(){

    }

    public Cartao(String numCartao, boolean cartaoMestre) {
        this.numCartao = numCartao;
        this.cartaoMestre = cartaoMestre;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public void setCartaoMestre(boolean cartaoMestre) {
        this.cartaoMestre = cartaoMestre;
    }


    public String getNumCartao() {
        return numCartao;
    }

    public boolean isCartaoMestre() {
        return cartaoMestre;
    }

    public static ObservableList<Cartao> getCartao() {
        ObservableList<Cartao> lista2 = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Cartao";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Cartao objCartao = new Cartao(rs.getString("numCartao"),rs.getBoolean("cartaoMestre"));
                lista2.add(objCartao);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista2;
    }
}
