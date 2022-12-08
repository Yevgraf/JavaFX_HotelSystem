package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProdutoQuarto {
    private int id;
    private int idQuarto;
    private String idProduto;

    private int quantidade;

    public ProdutoQuarto(){

    }

    public ProdutoQuarto(int id,int idQuarto, String idProduto, int quantidade) {
        this.id = id;
        this.idQuarto = idQuarto;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public static ObservableList<ProdutoQuarto> getProdutoQuarto() {
        ObservableList<ProdutoQuarto> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM ProdutoQuarto";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                ProdutoQuarto obj = new ProdutoQuarto(rs.getInt("id"),rs.getInt("idQuarto"),rs.getString("idProduto"),
                        rs.getInt("quantidade"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public String toString() {
        return "ProdutoQuartoController{" +
                "idQuarto=" + idQuarto +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                '}';
    }
}
