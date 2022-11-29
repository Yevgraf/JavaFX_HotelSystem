package Model;

import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class TipoColaborador {
    int id ;
    String tipo;

    public TipoColaborador() {
    }

    public TipoColaborador(int id, String tipo) {
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

    public static ObservableList<TipoColaborador> getTipoColaborador() {
        ObservableList<TipoColaborador> lista = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM TipoColaborador";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                TipoColaborador obj = new TipoColaborador(rs.getInt("id"),rs.getString("tipo"));
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
