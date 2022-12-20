package Model;

import java.sql.Date;

public class EntradaStock {

    private Double taxa;

    private Integer caixas;

    private String idProduto;

    private String local;

    private Double precoSemTaxa;

    private Integer unidades;

    private Double valorTaxa;

    private String ordemNum;

    private String ordemData;

    private String idFornecedor;

    private String moradaFornecedor;

    private String codPostalFornecedor;

    private String paisFornecedor;

    private String nomeFornecedor;

    private String cidadeFornecedor;

    private Double precoTotal;

    public Double getTaxa() {
        return taxa;
    }

    public Integer getCaixas() {
        return caixas;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public String getLocal() {
        return local;
    }

    public Double getPrecoSemTaxa() {
        return precoSemTaxa;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public Double getValorTaxa() {
        return valorTaxa;
    }

    public String getOrdemNum() {
        return ordemNum;
    }

    public String getOrdemData() {
        return ordemData;
    }

    public String getIdFornecedor() {
        return idFornecedor;
    }

    public String getMoradaFornecedor() {
        return moradaFornecedor;
    }

    public String getCodPostalFornecedor() {
        return codPostalFornecedor;
    }

    public String getPaisFornecedor() {
        return paisFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public String getCidadeFornecedor() {
        return cidadeFornecedor;
    }

    public EntradaStock(Double taxa, Integer caixas, String idProduto, String local, Double precoSemTaxa,
                        Integer unidades, Double valorTaxa, Double precoTotal) {

        this.taxa = taxa;
        this.caixas = caixas;
        this.idProduto = idProduto;
        this.local = local;
        this.precoSemTaxa = precoSemTaxa;
        this.unidades = unidades;
        this.valorTaxa = valorTaxa;
        this.precoTotal = precoTotal;
    }

    public EntradaStock(String ordemNum, String ordemData, String idFornecedor, String moradaFornecedor, String codPostalFornecedor,
                        String paisFornecedor, String nomeFornecedor, String cidadeFornecedor) {
        this.ordemNum = ordemNum;
        this.ordemData= ordemData;
        this.idFornecedor = idFornecedor;
        this.moradaFornecedor = moradaFornecedor;
        this.codPostalFornecedor = codPostalFornecedor;
        this.paisFornecedor = paisFornecedor;
        this.nomeFornecedor = nomeFornecedor;
        this.cidadeFornecedor = cidadeFornecedor;
    }

    public EntradaStock() {
    }
}
