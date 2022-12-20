package BLL;

import DAL.CartaoDal;
import DAL.QuartoDal;
import Model.Cartao;
import Model.Quarto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class QuartoBLL {

    // method to add a quarto to the database using the DAL
    public void addQuarto(Quarto quarto, Cartao cartao) throws SQLException {

        CartaoDal cdal = new CartaoDal();
        cdal.addCartao(cartao);

        QuartoDal dal = new QuartoDal();
        dal.addQuarto(quarto);

    }


    // method to update a quarto in the database using the DAL
    public void updateQuarto(Quarto quarto) {
        QuartoDal quartoDAL = new QuartoDal();
        quartoDAL.updateQuarto(quarto);
    }

    public void removeQuarto(int id) throws SQLException, SQLException {
        QuartoDal dal = new QuartoDal();
        CartaoDal cdal = new CartaoDal();
        Quarto quarto = dal.deleteQuarto(id);
        if (quarto != null) {
            cdal.deleteCartao(String.valueOf(quarto.getNumCartao()));
            dal.deleteQuarto(id);

        }
    }

    public static ObservableList<Quarto> getQuartos() {
        return QuartoDal.getAllQuartos();
    }

}

