package BLL;

import DAL.CartaoDAL;
import DAL.QuartoDAL;
import Model.Cartao;
import Model.Quarto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class QuartoBLL {

    // method to add a quarto to the database using the DAL
    public void addQuarto(Quarto quarto, Cartao cartao) throws SQLException {

        CartaoDAL cdal = new CartaoDAL();
        cdal.addCartao(cartao);

        QuartoDAL dal = new QuartoDAL();
        dal.addQuarto(quarto);

    }


    // method to update a quarto in the database using the DAL
    public void updateQuarto(Quarto quarto) {
        QuartoDAL quartoDAL = new QuartoDAL();
        quartoDAL.updateQuarto(quarto);
    }

    public void removeQuarto(int id) throws SQLException, SQLException {
        QuartoDAL dal = new QuartoDAL();
        CartaoDAL cdal = new CartaoDAL();
        Quarto quarto = dal.deleteQuarto(id);
        if (quarto != null) {
            cdal.deleteCartao(String.valueOf(quarto.getNumCartao()));
            dal.deleteQuarto(id);

        }
    }

    public static ObservableList<Quarto> getQuartos() {
        return QuartoDAL.getAllQuartos();
    }

}

