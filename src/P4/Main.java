package P4;

import P4.data.*;
import P4.domain.Adres;
import P4.domain.OVChipkaart;
import P4.domain.Reiziger;

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

    private static void testOVChipkaartDAO(OVChipkaartDAO ovcdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle adressen op uit de database
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende adressen:");
        List<OVChipkaart> ovChipkaartList = ovcdao.findAll();
        for (OVChipkaart o : ovChipkaartList) {
            System.out.println(o);
        }
        System.out.println();

        // Vind een lijst OVChipkaarten op basis van een reiziger
        System.out.println("[Test] OVChipkaartDAO.findByReiziger() vindt de volgende kaarten:");
        List<OVChipkaart> ovChipkaartListReiziger = ovcdao.findByReiziger(rdao.findById(2));
        for (OVChipkaart o : ovChipkaartListReiziger) {
            System.out.println(o);
        }
        System.out.println();

        // Maak een nieuwe kaart aan en persisteer deze in de database
        OVChipkaart newOVChipkaart = new OVChipkaart(12345, Date.valueOf("2023-05-31"), 2, 25.00, rdao.findById(2));
        System.out.print("[Test] Eerst " + ovChipkaartList.size() + " kaarten, na OVChipkaartDAO.save() ");
        ovcdao.save(newOVChipkaart);
        ovChipkaartList = ovcdao.findAll();
        System.out.println(ovChipkaartList.size() + " kaarten\n");

        // Update een kaart uit de database
        String dateUpdate = "2023-05-30";
        OVChipkaart updateOVChipkaart = new OVChipkaart(12345, Date.valueOf(dateUpdate), 1, 50.00, rdao.findById(2));
        System.out.print("[Test] Eerst was de data van de kaart: \n" + ovcdao.findByReiziger(rdao.findById(2)));
        ovcdao.update(updateOVChipkaart);
        System.out.println("\nEn nu is de data van de kaart geupdate naar: \n" + ovcdao.findByReiziger(rdao.findById(2)));
        System.out.println();

        // Verwijder een kaart uit de database
        System.out.print("[Test] Eerst " + ovChipkaartList.size() + " kaarten, na OVChipkaartDAO.delete() ");
        ovcdao.delete(updateOVChipkaart);
        ovChipkaartList = ovcdao.findAll();
        System.out.println(ovChipkaartList.size() + " kaarten\n");
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(conn);
        AdresDAOPsql adresDAO = new AdresDAOPsql(conn, reizigerDAO);
        OVChipkaartDAOPsql  ovChipkaartDAO = new OVChipkaartDAOPsql(conn, reizigerDAO);
        testReizigerDAO(reizigerDAO);
        testAdresDAO(adresDAO, reizigerDAO);
        testOVChipkaartDAO(ovChipkaartDAO, reizigerDAO);
        closeConnection();
    }
}
