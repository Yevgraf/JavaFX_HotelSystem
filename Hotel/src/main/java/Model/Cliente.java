package Model;


public class Cliente {
    private String nome;
    private int contacto;
    private String email;
    private String utilizador;
    private String password;
    private int nif;


   public Cliente(){

   }
    public Cliente(String nome, int contacto, String email, String utilizador, String password, int nif) {

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

    public void setContacto(int contacto) {
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

    public int getContacto() {
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
