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

    public void deleteComentario(int id) throws SQLException{
        ComentarioDAL dal = new ComentarioDAL();
        dal.deleteComentario(id);
    }

    public static ObservableList<Comentario> getAllComentarios() {
        return ComentarioDAL.getAllComentarios();
    }

    public static ObservableList<Comentario> getAllSugestoes() {
        return ComentarioDAL.getAllSugestoes();
    }

    public static ObservableList<Comentario> getAllQueixas() {
        return ComentarioDAL.getAllQueixas();
    }

}
