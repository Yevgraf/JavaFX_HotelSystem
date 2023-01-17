package DAL;

import BLL.UtilizadorPreferences;
import Controller.CriarQuartoController;
import Model.Cartao;
import Model.MessageBoxes;
import Model.Quarto;
import Model.TipoUtilizador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class QuartoDAL {

        public void addQuarto(Quarto quarto) {
            PreparedStatement ps2;

            try {
                // create a database connection
                DBconn dbConn = new DBconn();
                Connection connection = dbConn.getConn();

                // prepare the insert statement
                ps2 = connection.prepareStatement("INSERT INTO Quarto (tipoQuarto,piso,preco,idCartao) VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                // set the values for the prepared statement
                ps2.setString(1, quarto.getTipoQuarto());
                ps2.setString(2, quarto.getPiso());
                ps2.setDouble(3, quarto.getPreco());
                ps2.setInt(4, quarto.getCartao().getId());

                // execute the insert statement
                ps2.executeUpdate();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }



    public void updateQuarto(Quarto quarto) {
        PreparedStatement ps2;

        try {
            // create a database connection
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();

            // prepare the update statement
            ps2 = connection.prepareStatement("UPDATE Quarto SET tipoQuarto = ?, piso = ?, preco = ? WHERE id = ?");

            // set the values for the prepared statement
            ps2.setString(1, quarto.getTipoQuarto());
            ps2.setString(2, quarto.getPiso());
            ps2.setDouble(3, quarto.getPreco());
            ps2.setInt(4, quarto.getId());

            // execute the update statement
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void deleteQuarto(int idCartao) throws SQLException {
        DBconn dbConn = new DBconn();
        Connection connection = dbConn.getConn();

        PreparedStatement ps = connection.prepareStatement("DELETE FROM Quarto WHERE id = ?");
        ps.setInt(1, idCartao);
        ps.executeUpdate();
    }



    public static ObservableList<Quarto> getAllQuartos() {
        ObservableList<Quarto> list = FXCollections.observableArrayList();

        try {
            String cmd = "SELECT q.*, c.id as cId FROM Quarto q " +
                    "LEFT OUTER JOIN Cartao c ON c.Id = q.idCartao";

            Statement st = DBconn.getConn().createStatement();

            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {
                Quarto objQuarto = new Quarto(rs.getInt("id"), rs.getString("tipoQuarto"),
                        rs.getString("piso"), rs.getDouble("preco"), rs.getBoolean("ativo"),
                new Cartao(rs.getInt("cId")));
                list.add(objQuarto);
            }

            st.close();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return list;
    }

    public void updateAtivo(int reservationId, boolean ativo) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            DBconn dbConn = new DBconn();
            connection = dbConn.getConn();
            ps = connection.prepareStatement("UPDATE Quarto SET ativo = ? WHERE id = (SELECT idQuarto FROM Reserva WHERE id = ?)");
            ps.setBoolean(1, ativo);
            ps.setInt(2, reservationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public double getPreco(int quartoId) throws SQLException {
        String sql = "SELECT preco " +
                "FROM Quarto q " +
                "WHERE q.id = ?";

        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quartoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("preco");
                }
            }
        }

        return 0;
    }

    public static boolean VerificarQuartoPiso(int piso){
        String verificarquarto1 = "select count(id) from Quarto where piso = " + piso;
        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(verificarquarto1)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) >= 10 ) {
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Limite de Quartos Alcancado!", "ERRO");
                    CriarQuartoController.vP = false;
                }else{
                    CriarQuartoController.vP = true;
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        return CriarQuartoController.vP;
    }

    public static boolean VerificarTipoQuarto(int qtdQuartos, String tipoquarto){
        String verificarquarto1 = "select count(id) from Quarto where tipoQuarto = '" + tipoquarto + "'";
        try (Connection conn = DBconn.getConn();
             PreparedStatement stmt = conn.prepareStatement(verificarquarto1)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) >= qtdQuartos ) {
                    MessageBoxes.ShowMessage(Alert.AlertType.ERROR,"Limite de Quartos Alcancado!", "ERRO");
                    CriarQuartoController.vTQ = false;
                }else{
                    CriarQuartoController.vTQ = true;
                    CriarQuartoController.ADDQuarto();
                }
            }
        } catch (SQLException e) {
            e.getCause();
        }
        return CriarQuartoController.vTQ;
    }

    public static ObservableList<Quarto> getQuartoByClientId() {
        Integer id = UtilizadorPreferences.utilizadorId();
        ObservableList<Quarto> lista = FXCollections.observableArrayList();
        try {
            DBconn dbConn = new DBconn();
            Connection connection = dbConn.getConn();
            String cmd = "Select q.id as sId, q.tipoQuarto as servico From Quarto q JOIN Reserva r On r.idQuarto = q.id JOIN EstadoReserva er " +
                    "On er.reserva = r.id Where er.estado like 'checkin' and r.idCliente = " + id;
            PreparedStatement ps = connection.prepareStatement(cmd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quarto obj = new Quarto(rs.getInt("sId"), rs.getString("servico"));
                lista.add(obj);
            }
            ps.close();
        } catch (Exception ex) {
        }
        return lista;
    }

}
