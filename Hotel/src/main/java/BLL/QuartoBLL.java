package BLL;

import DAL.QuartoDal;
import Model.Quarto;

import java.sql.SQLException;

public class QuartoBLL {

    // method to add a quarto to the database using the DAL
    public void addQuarto(Quarto quarto) {
        QuartoDal.QuartoDAL quartoDAL = new QuartoDal.QuartoDAL();
        quartoDAL.addQuarto(quarto);
    }


    // method to update a quarto in the database using the DAL
    public void updateQuarto(Quarto quarto) {
        QuartoDal.QuartoDAL quartoDAL = new QuartoDal.QuartoDAL();
        quartoDAL.updateQuarto(quarto);
    }

    public void removeQuarto(int id) throws SQLException, SQLException {
        QuartoDal.QuartoDAL dal = new QuartoDal.QuartoDAL();
        Quarto quarto = dal.deleteQuarto(id);
        if (quarto != null) {
            dal.deleteQuarto(id);
            dal.deleteCartao(String.valueOf(quarto.getNumCartao()));
        }
    }
}

