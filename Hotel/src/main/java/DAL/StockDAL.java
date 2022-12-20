package DAL;
;
import Model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockDAL {
    public String getDescricaoStock (Stock selectedID) throws SQLException {
    PreparedStatement ps2;
    DBconn dbConn = new DBconn();
    Connection connection = dbConn.getConn();

    ps2 = connection.prepareStatement("SELECT descricao FROM Produto WHERE id = ?");
    ps2.setString(1, selectedID.getIdProduto());
    for (int i = 0; i < ProdutoDAL.getAllProdutos().size(); i++) {
        if (selectedID != null && selectedID.getIdProduto().equals(ProdutoDAL.getAllProdutos().get(i).getIdProduto())) {
            String descricao = ProdutoDAL.getAllProdutos().get(i).getDescricao();
            return descricao;
        }
    }
        return null;
    }
}
