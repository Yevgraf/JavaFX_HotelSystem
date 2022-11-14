package Model;

import java.util.Date;

public class Reserva {
    private int idReserva;
    private int idCliente;
    private int idColaborador;
    private int idQuarto;
    private int idCartao;
    private Date dataInicio;
    private Date dataFim;
    private boolean servExtra;
    private double preco;
    private int idPagamento;

    public Reserva(){

    }

    public Reserva(int idReserva, int idCliente, int idColaborador, int idQuarto, int idCartao, Date dataInicio, Date dataFim, boolean servExtra, double preco, int idPagamento) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idColaborador = idColaborador;
        this.idQuarto = idQuarto;
        this.idCartao = idCartao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.servExtra = servExtra;
        this.preco = preco;
        this.idPagamento = idPagamento;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public void setIdCartao(int idCartao) {
        this.idCartao = idCartao;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void setServExtra(boolean servExtra) {
        this.servExtra = servExtra;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }


    public int getIdReserva() {
        return idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public int getIdCartao() {
        return idCartao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public boolean isServExtra() {
        return servExtra;
    }

    public double getPreco() {
        return preco;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public int getIdPagamento() {
        return idPagamento;
    }
}
