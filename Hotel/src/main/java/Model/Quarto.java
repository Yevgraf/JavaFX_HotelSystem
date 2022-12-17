package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Quarto {
    private Integer id;
    private String tipoQuarto;
    private String piso;

    private Double preco;

    private String numCartao;
    private Boolean ativo ;


    public Quarto() {
    }

    public Quarto(Integer id, String tipoQuarto, String piso, Double preco, String numCartao,Boolean ativo){
        this.id = id;
        this.tipoQuarto = tipoQuarto;
        this.piso = piso;
        this.preco = preco;
        this.numCartao = numCartao;
        this.ativo = ativo;

    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }


    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }



    @Override
    public String toString() {
        return
                "id=" + id +
                ", tipoQuarto='" + tipoQuarto + '\'' +
                ", piso=" + piso +
                ", preco=" + preco +
                ", numCartao='" + numCartao;


    }
}
