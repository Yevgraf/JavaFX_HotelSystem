package Model;

public class Reserva {

    private Integer id;
    private Integer idCliente;
    private Integer idQuarto;
    private String dataInicio;
    private String dataFim;
    private String servExtra;
    private Double preco;




    public Reserva() {

    }

    public Reserva(Integer id, Integer idCliente, Integer idQuarto, String dataInicio, String dataFim, String servExtra, Double preco) {
        this.id = id;
        this.idCliente = idCliente;
        this.idQuarto = idQuarto;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.servExtra = servExtra;
        this.preco = preco;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(Integer idQuarto) {
        this.idQuarto = idQuarto;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getServExtra() {
        return servExtra;
    }

    public void setServExtra(String servExtra) {
        this.servExtra = servExtra;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }


}

