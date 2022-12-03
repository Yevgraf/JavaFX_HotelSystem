package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Produto {

    private String idProduto;

    private  String descricao;

    private Double precoUnidade;

    private Double peso;

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoUnidade() {
        return precoUnidade;
    }

    public void setPrecoUnidade(Double precoUnidade) {
        this.precoUnidade = precoUnidade;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Produto(String idProduto, String descricao, Double precoUnidade, Double peso) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.precoUnidade = precoUnidade;
        this.peso = peso;
    }

    public static ObservableList<Produto> getProduto() {
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM Produto";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Produto obj = new Produto(rs.getString("id"),rs.getString("descricao"),
                        rs.getDouble("precoPorUnidade"),rs.getDouble("peso"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

    public Produto(String idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public String toString() {
        return
                "idProduto='" + idProduto + '\'' +
                ", descricao='" + descricao + '\'' +
                ", precoUnidade=" + precoUnidade +
                ", peso=" + peso;
    }
}
