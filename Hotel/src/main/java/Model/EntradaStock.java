package Model;

public class EntradaStock {

    private Double taxa;

    private Integer caixas;

    private String descricao;

    private String identificacao;

    private String local;

    private Double peso;

    private Double precoSemTaxa;

    private Double precoUnidade;

    private Integer unidades;

    private Double valorTaxa;

    public Double getTaxa() {
        return taxa;
    }

    public Integer getCaixas() {
        return caixas;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public String getLocal() {
        return local;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getPrecoSemTaxa() {
        return precoSemTaxa;
    }

    public Double getPrecoUnidade() {
        return precoUnidade;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public Double precoTotal;

    public Double getValorTaxa() {
        return valorTaxa;
    }

    public EntradaStock(Double taxa, Integer caixas, String descricao, String identificacao, String local,
                        Double peso, Double precoSemTaxa, Double precoUnidade,
                        Integer unidades, Double valorTaxa, Double precoTotal) {
        this.taxa = taxa;
        this.caixas = caixas;
        this.descricao = descricao;
        this.identificacao = identificacao;
        this.local = local;
        this.peso = peso;
        this.precoSemTaxa = precoSemTaxa;
        this.precoUnidade = precoUnidade;
        this.unidades = unidades;
        this.valorTaxa = valorTaxa;
        this.precoTotal = precoTotal;
    }

    public EntradaStock() {
    }
}
