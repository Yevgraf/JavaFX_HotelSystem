package BLL;

import DAL.CartaoDAL;
import DAL.QuartoDAL;
import Model.Cartao;
import Model.Quarto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class QuartoBLL {

    // method to add a quarto to the database using the DAL
    public void addQuarto(Quarto quarto) throws SQLException {

        CartaoDAL cdal = new CartaoDAL();
        cdal.addCartao(quarto.getCartao());
        QuartoDAL dal = new QuartoDAL();
        dal.addQuarto(quarto);

    }


    // method to update a quarto in the database using the DAL
    public void updateQuarto(Quarto quarto) {
        QuartoDAL quartoDAL = new QuartoDAL();
        quartoDAL.updateQuarto(quarto);
    }

    public void removeQuarto(int idCartao) throws SQLException {
        QuartoDAL dal = new QuartoDAL();
        dal.deleteQuarto(idCartao);
    }


    public static ObservableList<Quarto> getQuartos() {
        return QuartoDAL.getAllQuartos();
    }

}

