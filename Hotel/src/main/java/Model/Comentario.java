package Model;

public class Comentario {

    private Integer id;

    private Integer idCliente;

    private String comentario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Comentario(Integer id, Integer idCliente, String comentario) {
        this.id = id;
        this.idCliente = idCliente;
        this.comentario = comentario;
    }

    public Comentario() {
    }

}
