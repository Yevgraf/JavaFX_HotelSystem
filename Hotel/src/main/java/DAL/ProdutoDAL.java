package DAL;

import Model.MessageBoxes;
import Model.Produto;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class ProdutoDAL {
    public void addProduto(ObservableList<Produto> produtos) {
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            for (int iteradorProduto = 0; iteradorProduto < produtos.size(); iteradorProduto++) {
                String id = produtos.get(iteradorProduto).getIdProduto();
                if (!verificaProdutoExistente(id, connection)) {
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Produto(id, descricao, peso, precoPorUnidade)" +
                            "VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    insertProduto(produtos.get(iteradorProduto), insertStatement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            MessageBoxes.ShowMessage(Alert.AlertType.ERROR, "Erro inesperado!", "Erro");
        }
    }

    private boolean verificaProdutoExistente(String id, Connection connection) {
        Statement ps2;
        try {
            ps2 = connection.createStatement();
            ResultSet rs = ps2.executeQuery("SELECT id FROM Produto WHERE id = '" + id + "'");
            return rs.isBeforeFirst();
        } catch (SQLException e) {
            return false;
        }
    }

    private void insertProduto(Produto produto, PreparedStatement statement) throws SQLException {
        statement.setString(1, produto.getIdProduto());
        statement.setString(2, produto.getDescricao());
        statement.setDouble(3, produto.getPeso());
        statement.setDouble(4, produto.getPrecoUnidade());
        statement.executeUpdate();
        statement.close();
    }
}
