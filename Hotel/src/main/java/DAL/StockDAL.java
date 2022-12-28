package DAL;
;
import Model.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

    public static ObservableList<Stock> getStock() {
        ObservableList<Stock> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM Stock";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Stock obj = new Stock(rs.getString("idProduto"), rs.getInt("quantidade"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
}
