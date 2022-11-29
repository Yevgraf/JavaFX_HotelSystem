package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Quarto {
    private int id;
    private String TipoQuarto;
    private int piso;
    private boolean wifi;
    private double preco;

    private String numCartao;

    public Quarto() {
    }

    public Quarto(int id, String TipoQuarto, int piso, boolean wifi, double preco, String numCartao) {
        this.id = id;
        this.TipoQuarto = TipoQuarto;
        this.piso = piso;
        this.wifi = wifi;
        this.preco = preco;
        this.numCartao = numCartao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoQuarto() {
        return TipoQuarto;
    }

    public void setIdTipoQuarto(String TipoQuarto) {
        this.TipoQuarto = TipoQuarto;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public double getPreco() {
        return preco;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public static ObservableList<Quarto> getQuarto() {
        ObservableList<Quarto> lista3 = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Quarto";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Quarto objQuarto = new Quarto(rs.getInt("id"),rs.getString("tipoQuarto"),rs.getInt("piso"),rs.getBoolean("wifi"),
                        rs.getDouble("preco"), rs.getNString("numerocartao"));
                lista3.add(objQuarto);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista3;
    }


}
