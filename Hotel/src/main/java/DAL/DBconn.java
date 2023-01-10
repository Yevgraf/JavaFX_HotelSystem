package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

import BLL.SearchFile;

public class DBconn {

    static Connection connect = null;

    public static Connection getConn() {
        List<String> list = SearchFile.SearchDB();
        String[] array = list.toArray(new String[0]);
        String url = array[0];
        String user = array[1];
        String pass = array[2];
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao conectar a base de dados! \n Erro: ");
            e.printStackTrace();
        }
        return connect;
    }
}
