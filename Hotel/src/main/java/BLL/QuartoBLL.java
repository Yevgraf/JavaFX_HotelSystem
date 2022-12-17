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
        QuartoDal.QuartoDAL dal = new QuartoDal.QuartoDAL();
        CartaoDal cdal = new CartaoDal();
        dal.addQuarto(quarto);
        cdal.addCartao(cartao);


    }


    // method to update a quarto in the database using the DAL
    public void updateQuarto(Quarto quarto) {
        QuartoDal.QuartoDAL quartoDAL = new QuartoDal.QuartoDAL();
        quartoDAL.updateQuarto(quarto);
    }

    public void removeQuarto(int id) throws SQLException, SQLException {
        QuartoDal.QuartoDAL dal = new QuartoDal.QuartoDAL();
        CartaoDal cdal = new CartaoDal();
        Quarto quarto = dal.deleteQuarto(id);
        if (quarto != null) {
            cdal.deleteCartao(String.valueOf(quarto.getNumCartao()));
            dal.deleteQuarto(id);

        }
    }

    public static ObservableList<Quarto> getQuartos() {
        return QuartoDal.QuartoDAL.getAllQuartos();
    }

}

