package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Quarto {
    private Integer id;
    private String tipoQuarto;
    private Integer piso;

    private Double preco;

    private String numCartao;
    private Boolean ativo ;
    private Boolean cofre;

    public Quarto() {
    }

    public Quarto(Integer id, String tipoQuarto, Integer piso, Double preco, String numCartao,Boolean ativo,Boolean cofre){
        this.id = id;
        this.tipoQuarto = tipoQuarto;
        this.piso = piso;
        this.preco = preco;
        this.numCartao = numCartao;
        this.ativo = ativo;
        this.cofre = cofre;
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

    public Boolean getCofre() {
        return cofre;
    }

    public void setCofre(Boolean cofre) {
        this.cofre = cofre;
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

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
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

    public static ObservableList<Quarto> getQuarto() {
        ObservableList<Quarto> lista3 = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Quarto";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Quarto objQuarto = new Quarto(rs.getInt("id"),rs.getString("tipoQuarto"),rs.getInt("piso"),
                        rs.getDouble("preco"), rs.getString("numeroCartao"),rs.getBoolean("ativo"),rs.getBoolean("cofre"));
                lista3.add(objQuarto);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista3;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", tipoQuarto='" + tipoQuarto + '\'' +
                ", piso=" + piso +
                ", preco=" + preco +
                ", numCartao='" + numCartao + '\'' +
                ", ativo=" + ativo +
                ", cofre=" + cofre;
    }
}
