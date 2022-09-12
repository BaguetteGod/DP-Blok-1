import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String dbURL = "jdbc:postgresql://localhost:5432/ovchip";
        String user = "postgres";
        String pass = "root";

        Connection conn = DriverManager.getConnection(dbURL, user, pass);

        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from reiziger");
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        while (resultSet.next()) {
            String row = "";
            for (int i = 1; i <= columnCount; i++) {
                row += resultSet.getString(i) + ", ";
            }
            System.out.println(row);
        }
    }
}