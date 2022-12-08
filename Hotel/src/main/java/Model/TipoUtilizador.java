package Model;

import DAL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class TipoUtilizador {
    int id ;
    String tipo;

    public TipoUtilizador() {
    }

    public TipoUtilizador(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static ObservableList<TipoUtilizador> getTipoUtilizador() {
        ObservableList<TipoUtilizador> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM TipoUtilizador";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                TipoUtilizador obj = new TipoUtilizador(rs.getInt("id"),rs.getString("tipo"));
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
