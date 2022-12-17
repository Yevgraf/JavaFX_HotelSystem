package Model;

public class Cartao {

    private Integer id;
    private String numCartao;

    private Boolean ativo;



    public Cartao(Integer id, String numCartao, Boolean ativo) {
        this.id = id;
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

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }



}
