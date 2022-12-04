package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Reserva {

    private int id;
    private Integer nifCliente;
    private Integer idColaborador;
    private Integer idQuarto;
    private String dataInicio;
    private String dataFim;
    private String servExtra;
    private Double preco;




    public Reserva() {

    }

    public Reserva(int id, Integer nifCliente, Integer idColaborador, Integer idQuarto, String dataInicio, String dataFim, String servExtra, Double preco) {
        this.id = id;
        this.nifCliente = nifCliente;
        this.idColaborador = idColaborador;
        this.idQuarto = idQuarto;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.servExtra = servExtra;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNifCliente() {
        return nifCliente;
    }

    public void setNifCliente(Integer nifCliente) {
        this.nifCliente = nifCliente;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
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

    public static ObservableList<Reserva> getReservas() {
        ObservableList<Reserva> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM Reserva";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Reserva obj = new Reserva(rs.getInt("id"),rs.getInt("nifCliente"),
                        rs.getInt("idColaborador"), rs.getInt("idQuarto"),
                        rs.getString("dataInicio"), rs.getString("dataFim"),
                        rs.getString("servExtra"), rs.getDouble("preco"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

}

