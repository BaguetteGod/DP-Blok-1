package P2;

import P2.data.ReizigerDAO;
import P2.data.ReizigerDAOsql;
import P2.domain.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {
    private static Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:postgresql://localhost:5432/ovchip";
        String user = "postgres";
        String pass = "root";
        return DriverManager.getConnection(dbUrl, user, pass);
    }

    private static void closeConnection() throws SQLException {
        getConnection().close();
    }
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        ReizigerDAO reizigerDAO = new ReizigerDAOsql(conn);
        testReizigerDAO(reizigerDAO);
    }
}
