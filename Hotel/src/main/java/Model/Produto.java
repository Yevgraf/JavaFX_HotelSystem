package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Produto {

    private String idProduto;

    private String descricao;

    private Double precoUnidade;

    private Double peso;

    private Boolean consumivel;

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

    public Boolean getConsumivel() {
        return consumivel;
    }

    public void setConsumivel(Boolean consumivel) {
        this.consumivel = consumivel;
    }

    public Produto() {
    }

    public Produto(String idProduto, String descricao, Double precoUnidade, Double peso, Boolean consumivel) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.precoUnidade = precoUnidade;
        this.peso = peso;
        this.consumivel = consumivel;
    }
}