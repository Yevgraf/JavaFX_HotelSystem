package Model;

public class Servico {
    private int idServico;
    private String servico;

    public Servico(){

    }

    public Servico(int idServico, String servico) {
        this.idServico = idServico;
        this.servico = servico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public int getIdServico() {
        return idServico;
    }

    public String getServico() {
        return servico;
    }
}
