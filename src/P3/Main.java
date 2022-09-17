package P3;

import P3.data.AdresDAO;
import P3.data.ReizigerDAO;
import P3.data.ReizigerDAOPsql;
import P3.data.AdresDAOPsql;
import P3.domain.Adres;
import P3.domain.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
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

        // Vind reiziger op ID
        System.out.println("[Test] ReizigerDAO.findById(77) geeft de volgende reiziger:");
        Reiziger reizigerToDelete = rdao.findById(77);
        System.out.println(reizigerToDelete);
        System.out.println();

        // Delete een reiziger
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(reizigerToDelete);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update een reiziger
        String newGbdatum = "1981-05-15";
        Reiziger reizigerToUpdate = new Reiziger(77, "F.K", "", "Saai", Date.valueOf(newGbdatum));
        System.out.print("[Test] Eerst was de data van de reiziger: \n" + rdao.findById(77));
        rdao.update(reizigerToUpdate);
        System.out.println("\nNu is de data van de reiziger: \n" + rdao.findById(77));

    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        List<Adres> adresList = adao.findAll();
        for (Adres a : adresList) {
            System.out.println(a);
        }
        System.out.println();

        // Vind een adres op basis van een reiziger
        Adres adres = adao.findByReiziger(rdao.findById(1));
        System.out.println("[Test] ReizigerDAO.findByReiziger() vindt het volgende adres: \n" + adres);
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres nieuwAdres = new Adres(10, "1234AB", "12", "Jan Willemstraat", "Utrecht", rdao.findById(77));
        System.out.println(nieuwAdres);
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDAO.save() ");
        adao.save(nieuwAdres);
        adresList = adao.findAll();
        System.out.println(adresList.size() + " adressen\n");

        // Update een adres uit de database
        String postcodeUpdate = "4321BA";
        Adres updateAdres = new Adres(10, postcodeUpdate, "12", "Jan Willemstraat", "Utrecht", rdao.findById(77));
        System.out.print("[Test] Eerst was de data van het adres: \n" + adao.findByReiziger(rdao.findById(77)));
        adao.update(updateAdres);
        System.out.println("\nNu is de data van het adres: \n" + adao.findByReiziger(rdao.findById(77)));
        System.out.println();

        // Verwijder een adres uit de database
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDAO.delete() ");
        adao.delete(adao.findByReiziger(rdao.findById(77)));
        adresList = adao.findAll();
        System.out.println(adresList.size() + " adressen\n");

    }

    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(conn);
        AdresDAOPsql adresDAO = new AdresDAOPsql(conn, reizigerDAO);
        testReizigerDAO(reizigerDAO);
        testAdresDAO(adresDAO, reizigerDAO);
        closeConnection();
    }
}
