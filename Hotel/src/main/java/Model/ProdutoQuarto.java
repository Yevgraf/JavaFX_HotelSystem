package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProdutoQuarto {
    private int idQuarto;
    private int idProduto;

    private int quantidade;

    public ProdutoQuarto(){

    }

    public ProdutoQuarto(int idQuarto, int idProduto, int quantidade) {
        this.idQuarto = idQuarto;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
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
                ProdutoQuarto obj = new ProdutoQuarto(rs.getInt("idQuarto"),rs.getInt("idProduto"),
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
        return "ProdutoQuarto{" +
                "idQuarto=" + idQuarto +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                '}';
    }
}
