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
    private Boolean wifi;
    private Double preco;

    private String numCartao;
    private Boolean ativo ;

    public Quarto() {
    }

    public Quarto(Integer id, String tipoQuarto, Integer piso, Boolean wifi, Double preco, String numCartao,Boolean ativo ){
        this.id = id;
        this.tipoQuarto = tipoQuarto;
        this.piso = piso;
        this.wifi = wifi;
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

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
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
                Quarto objQuarto = new Quarto(rs.getInt("id"),rs.getString("tipoQuarto"),rs.getInt("piso"),rs.getBoolean("wifi"),
                        rs.getDouble("preco"), rs.getString("numeroCartao"),rs.getBoolean("ativo"));
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
        return "Quarto{" +
                "id=" + id +
                ", tipoQuarto='" + tipoQuarto + '\'' +
                ", piso=" + piso +
                ", wifi=" + wifi +
                ", preco=" + preco +
                ", numCartao='" + numCartao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
