package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Cartao {

    private int id;
    private String numCartao;

    private Boolean ativo;

    public Cartao() {
    }

    public Cartao(int id, String numCartao, Boolean ativo) {
        this.id = id;
        this.numCartao = numCartao;
        this.ativo = ativo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }


    public static ObservableList<Cartao> getCartao() {
       ObservableList<Cartao> lista2 = FXCollections.observableArrayList();

      try {
          String cmd = "SELECT * FROM Cartao";

          Statement st = DBconn.getConn().createStatement();

          ResultSet rs = st.executeQuery(cmd);

          while (rs.next()) {
              Cartao objCartao = new Cartao(rs.getInt("id"),rs.getString("numeroCartao"),rs.getBoolean("ativo"));
              lista2.add(objCartao);
          }

          st.close();
      } catch (Exception ex) {
          System.err.println("Erro: " + ex.getMessage());
      }
      return lista2;
  }

}
