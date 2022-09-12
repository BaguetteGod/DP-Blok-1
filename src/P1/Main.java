package P1;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String dbURL = "jdbc:postgresql://localhost:5432/ovchip";
        String user = "postgres";
        String pass = "root";

        Connection conn = DriverManager.getConnection(dbURL, user, pass);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select * from reiziger");
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();

        while (rs.next()) {
            String row = "";
            for (int i = 1; i <= columnCount; i++) {
                row += rs.getString(i) + " ";
            }
            System.out.println(row);
        }
    }
}