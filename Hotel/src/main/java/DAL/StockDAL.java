package DAL;

;
import Model.Reserva;
import Model.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class StockDAL {

    /**
     * A função getDescricaoStock serve para ver a descrição do produto
     * @param selectedID recebe o id do stock
     * @return devolve a descrição do produto
     * @throws SQLException mostra a informacao do erro
     */
    public String getDescricaoStock(Stock selectedID) throws SQLException {
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


    /**
     * A função getStock serve para guardar o Stock
     * @return devolve uma lista do stock
     */
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


    /**
     * A função verificaSeProdutoTemQuantidadeSuficiente serve para verificar se o produto tem quantidade suficiente para ser usado
     * @param idProduto recebe o id do produto
     * @param quantDesejada recebe a quantidade desejada
     * @return devolve se existe ou não quantidade suficiente
     */
    public Boolean verificaSeProdutoTemQuantidadeSuficiente(String idProduto, int quantDesejada) {
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "SELECT s.quantidade as quantidade FROM Stock s " +
                    "INNER JOIN Produto p on p.id = s.idProduto " +
                    "WHERE p.id = ?";
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.setString(1, idProduto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantExistente = rs.getInt("quantidade");
                if (quantExistente > quantDesejada) {
                    return true;
                } else {
                    return null;
                }
            }
            ps.close();
        } catch (Exception ex) {
        }
        return null;
    }
}
