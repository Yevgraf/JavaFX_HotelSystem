package BLL;

import DAL.ComentarioDAL;
import Model.Comentario;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class ComentarioBLL {

    public void addComentario(Comentario comentario) throws SQLException {
        ComentarioDAL dal = new ComentarioDAL();
        dal.addComentario(comentario);
    }

    public static ObservableList<Comentario> getAllComentarios() {
        return ComentarioDAL.getAllComentarios();
    }

}
