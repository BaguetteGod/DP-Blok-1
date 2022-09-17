package P2;

import java.sql.*;

public class Main {
    private static Connection getConnection() throws SQLException {
        String dbURL = "jdbc:postgresql://localhost:5432/ovchip";
        String user = "postgres";
        String pass = "root";

        return DriverManager.getConnection(dbURL, user, pass);
    }

    private static void closeConnection () throws SQLException {
        getConnection().close();
    }
    public static void main(String[] args) throws SQLException {

    }
}
