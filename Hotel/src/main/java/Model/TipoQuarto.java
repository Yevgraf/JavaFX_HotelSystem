package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class    TipoQuarto {
    private int idTipoQuarto;
    private String tipo;
    private boolean vista;

    public TipoQuarto(){

    }

    public TipoQuarto(int idTipoQuarto, String tipo, boolean vista) {
        this.idTipoQuarto = idTipoQuarto;
        this.tipo = tipo;
        this.vista = vista;
    }

    public void setIdTipoQuarto(int idTipoQuarto) {
        this.idTipoQuarto = idTipoQuarto;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }

    public int getIdTipoQuarto() {
        return idTipoQuarto;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isVista() {
        return vista;
    }
    public static ObservableList<TipoQuarto> getTipoQuarto() {
        ObservableList<TipoQuarto> lista = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM TipoQuarto";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                TipoQuarto obj = new TipoQuarto(rs.getInt("id"),rs.getString("tipo"), rs.getBoolean("vista"));
                lista.add(obj);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
    @Override
    public String toString() {
        return this.tipo;
    }


}
