package DAL;

import Model.EntradaStock;
import javafx.collections.ObservableList;

import java.sql.*;

public class FornecedorDAL {
    public void addFornecedor(ObservableList<EntradaStock> fornecedores) {
        PreparedStatement ps2;
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            ps2 = connection.prepareStatement("INSERT INTO Fornecedor(id, nome, morada, codigoPostal, pais, cidade)" +
                    "VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < fornecedores.size(); i++) {
                if (!verificaFornecedorExistente(fornecedores.get(i).getFornecedor().getIdFornecedor(), connection)) {
                    ps2.setString(1, fornecedores.get(i).getFornecedor().getIdFornecedor());
                    ps2.setString(2, fornecedores.get(i).getFornecedor().getNome());
                    ps2.setString(3, fornecedores.get(i).getFornecedor().getMorada());
                    ps2.setString(4, fornecedores.get(i).getFornecedor().getCodigoPostal());
                    ps2.setString(5, fornecedores.get(i).getFornecedor().getPais());
                    ps2.setString(6, fornecedores.get(i).getFornecedor().getCidade());
                    ps2.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean verificaFornecedorExistente(String id, Connection connection) {
        Statement ps2;
        try {
            ps2 = connection.createStatement();
            ResultSet rs = ps2.executeQuery("SELECT id FROM Fornecedor WHERE id = '" + id + "'");
            return rs.isBeforeFirst();
        } catch (SQLException e) {
            return false;
        }
    }
}
