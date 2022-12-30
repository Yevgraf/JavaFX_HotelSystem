package Model;

public class Checkout {


    private Integer idReserva;
    private Double preco;
    private String metodoPagamento;

    public Checkout() {

    }

    public Checkout(Integer idReserva, Double preco, String metodoPagamento) {

        this.idReserva = idReserva;
        this.preco = preco;
        this.metodoPagamento = metodoPagamento;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
