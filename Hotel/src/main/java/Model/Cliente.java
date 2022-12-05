package Model;


import BLL.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Cliente {
    private String nome;
    private String contacto;
    private String email;
    private String utilizador;
    private String password;
    private int nif;


   public Cliente(){

   }
    public Cliente(String nome, String contacto, String email, String utilizador, String password, int nif) {

        this.nome = nome;
        this.contacto = contacto;
        this.email = email;
        this.utilizador = utilizador;
        this.password = password;
        this.nif = nif;
    }



    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }



    public String getNome() {
        return nome;
    }

    public String getContacto() {
        return contacto;
    }

    public String getEmail() {
        return email;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public String getPassword() {
        return password;
    }

    public int getNif() {
        return nif;
    }

    public static ObservableList<Cliente> getCliente() {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT * FROM Cliente";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Cliente obj = new Cliente(rs.getString("nome"),rs.getString("contacto"),rs.getString("email"),
                        rs.getString("utilizador"),rs.getString("palavrapasse"),rs.getInt("nif"));
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
        return
                "nome='" + nome + '\'' +
                ", contacto='" + contacto + '\'' +
                ", email='" + email + '\'' +
                ", utilizador='" + utilizador + '\'' +
                ", nif=" + nif;
    }
}
