package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Cartao {

    private String numCartao;
    private boolean cartaoMestre;
    private RegistoCartao registoCartao;

    public Cartao(String numCartao, boolean cartaoMestre, String registo){

    }

    public Cartao(String numCartao, boolean cartaoMestre, RegistoCartao registoCartao) {
        this.numCartao = numCartao;
        this.cartaoMestre = cartaoMestre;
        this.registoCartao = registoCartao;
    }

   public static ObservableList<Cartao> getCartao() {
       ObservableList<Cartao> lista2 = FXCollections.observableArrayList();

      try {
          String cmd = "SELECT * FROM Cartao";

          Statement st = DBconn.getConn().createStatement();

          ResultSet rs = st.executeQuery(cmd);

          while (rs.next()) {
              Cartao objCartao = new Cartao(rs.getString("numCartao"),rs.getBoolean("cartaoMestre"),rs.getString("registo"));
              lista2.add(objCartao);
          }

          st.close();
      } catch (Exception ex) {
          System.err.println("Erro: " + ex.getMessage());
      }
      return lista2;
  }
}
