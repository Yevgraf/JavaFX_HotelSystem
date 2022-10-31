package DataBase;
import java.sql.*;

public class ConnectionBD {

    private String url, user, pass;

    public static void main(String[] args) {

        ConnectionBD a = new ConnectionBD();
        a.TestConnection();

    }

    public  ConnectionBD(){
        this.url = "jdbc:sqlserver://ctespbd.dei.isep.ipp.pt;databaseName=2022_F_LP3_G4;TrustServerCertificate=True";
        this.user = "2022_F_LP3_G4";
        this.pass = "123+qwe*123";
    }

    public void TestConnection() {
        try {
            Connection connection = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPass());
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

}
