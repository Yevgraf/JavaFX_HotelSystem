package BLL;

import DAL.CartaoDAL;

import java.sql.SQLException;

public class CartaoBLL {

    public void deleteCartao(int idCartao) throws SQLException, SQLException {
        CartaoDAL dal = new CartaoDAL();
        dal.deleteCartao(idCartao);
    }



}
