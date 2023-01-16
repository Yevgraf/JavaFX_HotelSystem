package DAL;

import Model.MessageBoxes;
import Model.Produto;

import Model.Servico;
import Model.Stock;
import javafx.collections.FXCollections;
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
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Produto(id, descricao, peso, precoPorUnidade, consumivel, precoParaCliente)" +
                            "VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

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
        statement.setBoolean(5, false);
        statement.setDouble(6, 1.0);
        statement.executeUpdate();
        statement.close();
    }

    public static ObservableList<Produto> getAllProdutos() {
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM Produto";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Produto obj = new Produto(rs.getString("id"), rs.getString("descricao"),
                        rs.getDouble("precoPorUnidade"), rs.getDouble("peso"),
                        rs.getBoolean("consumivel"));
                lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

    public static ObservableList<Produto> getAllProdutosPrecosClientes() {
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        try {
            String cmd = "SELECT * FROM Produto";
            Statement st = DBconn.getConn().createStatement();
            ResultSet rs = st.executeQuery(cmd);
            while (rs.next()) {
                Produto obj = new Produto(rs.getString("id"), rs.getString("descricao"),
                        rs.getDouble("precoPorUnidade"), rs.getDouble("precoParaCliente"));
                        lista.add(obj);
            }
            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

    public void updateConsumivelProduto(Produto selectedID, boolean estado) throws SQLException {
        PreparedStatement ps2;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        ps2 = connection.prepareStatement("UPDATE Produto SET consumivel = ? WHERE id = ?");
        ps2.setBoolean(1, estado);
        ps2.setString(2, selectedID.getIdProduto());
        ps2.executeUpdate();
    }

    public Integer getQuantidadeProduto (Produto selectedID) throws SQLException {
        PreparedStatement ps2;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        ps2 = connection.prepareStatement("SELECT quantidade FROM Stock WHERE idProduto = ?");
        ps2.setString(1, selectedID.getIdProduto());
        for (int i = 0; i < StockDAL.getStock().size(); i++) {
            if (selectedID != null && selectedID.getIdProduto().equals(StockDAL.getStock().get(i).getIdProduto())){
                Integer quantidade = StockDAL.getStock().get(i).getQuantidade();
                return quantidade;
            }
        }
        return null;
    }

    public void updatePrecoProdutoParaCliente(Produto selectedID, Double quantidade) throws SQLException {
        PreparedStatement ps2;
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        ps2 = connection.prepareStatement("UPDATE Produto SET precoParaCliente = ? WHERE id = ?");
        ps2.setDouble(1, quantidade);
        ps2.setString(2, selectedID.getIdProduto());
        ps2.executeUpdate();
    }

}
