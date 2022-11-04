package Model;

public class Fornecedor {
    private int idFornecedor;
    private String nome;
    private String email;
    private String morada;

    public Fornecedor(){

    }

    public Fornecedor(int idFornecedor, String nome, String email, String morada) {
        this.idFornecedor = idFornecedor;
        this.nome = nome;
        this.email = email;
        this.morada = morada;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getMorada() {
        return morada;
    }
}
