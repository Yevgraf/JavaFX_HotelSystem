package Model;

import java.sql.Date;

public class Registo {
private Integer id;
private Integer idCartao;
private Integer idCliente;
private String Local;
private java.sql.Date Data;

    public Registo() {
    }

    public Registo(Integer id, Integer idCartao, Integer idCliente, String Local, Date Data) {
        this.id = id;
        this.idCartao = idCartao;
        this.idCliente = idCliente;
        this.Local = Local;
        this.Data = Data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String Local) {
        this.Local = Local;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
    }

    @Override
    public String toString() {
        return "Registo{" +
                "id=" + id +
                ", idCartao=" + idCartao +
                ", idCliente=" + idCliente +
                ", local='" + Local + '\'' +
                ", date=" + Data +
                '}';
    }
}