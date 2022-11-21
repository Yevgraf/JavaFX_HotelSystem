package Model;

import java.time.LocalDate;

public class RegistoCartao {
    private String local;
    private LocalDate tempo;


    public RegistoCartao(){

    }

    public RegistoCartao(String local, LocalDate tempo) {
        this.local = local;
        this.tempo = tempo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDate getTempo() {
        return tempo;
    }

    public void setTempo(LocalDate tempo) {
        this.tempo = tempo;
    }
}
