package P5;

import P5.data.*;
import P5.domain.Adres;
import P5.domain.OVChipkaart;
import P5.domain.Product;
import P5.domain.Reiziger;

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

    private static void testReizigerDAO(ReizigerDAO rdao) {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Vind een reiziger op id
        System.out.println("[Test] ReizigerDAO.findById() vindt de volgende reiziger:");
        Reiziger findSietske = rdao.findById(77);
        System.out.println(findSietske);
        System.out.println();

        // Vind een reiziger op geboortedatum
        System.out.println("[Test] ReizigerDAO.findByGbdatum() vindt de volgende reizigers:");
        List<Reiziger> listReizigers = rdao.findByGbdatum("2002-12-03");
        for (Reiziger r : listReizigers) {
            System.out.println(r);
        }
        System.out.println();
        // Verwijder een reiziger uit de database
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(findSietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update een reiziger en persisteer deze in de database
        String gbdatumUpdate = "1982-03-14";
        Reiziger updateSietske = new Reiziger(77, "S", "", "Harms", java.sql.Date.valueOf(gbdatumUpdate));
        System.out.print("[Test] Eerst was de data van de reiziger: \n" + rdao.findById(77));
        rdao.update(updateSietske);
        System.out.println("\nEn nu is de data van de reiziger geupdate naar: \n" + rdao.findById(77));
        System.out.println();
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
        System.out.println("\nEn nu is de data van het adres geupdate naar: \n" + adao.findByReiziger(rdao.findById(77)));
        System.out.println();

        // Verwijder een adres uit de database
        System.out.print("[Test] Eerst " + adresList.size() + " adressen, na AdresDAO.delete() ");
        adao.delete(adao.findByReiziger(rdao.findById(77)));
        adresList = adao.findAll();
        System.out.println(adresList.size() + " reizigers\n");

    }

    private static void testOVChipkaartDAO(OVChipkaartDAO ovcdao, ReizigerDAO rdao, ProductDAO pdao) throws SQLException {
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
        System.out.print("[Test] Eerst was de data van de kaart: \n" + ovcdao.findById(12345));
        updateOVChipkaart.addProduct(pdao.findById(6));
        ovcdao.update(updateOVChipkaart);
        System.out.println("\nEn nu is de data van de kaart geupdate naar: \n" + ovcdao.findById(12345));
        System.out.println();

        // Verwijder een kaart uit de database
        System.out.print("[Test] Eerst " + ovChipkaartList.size() + " kaarten, na OVChipkaartDAO.delete() ");
        ovcdao.delete(ovcdao.findById(12345));
        ovChipkaartList = ovcdao.findAll();
        System.out.println(ovChipkaartList.size() + " kaarten\n");
    }

    private static void testProduct(ProductDAO pdao, OVChipkaartDAO ovcdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Vind een lijst Producten op basis van een OVChipkaart
        System.out.println("[Test] ProductDAO.findByOVChipkaart() geeft de volgende producten:");
        List<OVChipkaart> ovChipkaartList = ovcdao.findByReiziger(rdao.findById(2));
        for (OVChipkaart o : ovChipkaartList) {
            List<Product> productList = pdao.findByOVChipkaart(o);
            for (Product p : productList) {
                System.out.println(p);
            }
        }
        System.out.println();

        // Haal alle producten op uit de database
        System.out.println("[Test] ProductDAO.findAll() vindt de volgende producten:");
        List<Product> productList = pdao.findAll();
        for (Product p : productList) {
            System.out.println(p);
        }
        System.out.println();

        // Maak een nieuw product aan en persisteer deze in de database
        Product newProduct = new Product(7, "Dagkaart 1e klas", "Een hele dag onbeperkt reizen met de trein.", 75.00);
        System.out.print("[Test] Eerst " + productList.size() + " producten, na ProductDAO.save() ");
        newProduct.addOvChipkaart(ovcdao.findById(57401).getId());
        pdao.save(newProduct);
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten\n");

        // Update een product uit de database
        String beschrijvingUpdate = "Geen heerlijke reis met de trein.";
        Product updateProduct = new Product(7, "Dagkaart 1e klas", beschrijvingUpdate, 75.00);
        updateProduct.addOvChipkaart(ovcdao.findById(57401).getId());
        System.out.print("[Test] Eerst was de data van het product: \n" + pdao.findById(7));
        pdao.update(updateProduct);
        System.out.println("\nEn nu is de data van het product geupdate naar: \n" + pdao.findById(7));
        System.out.println();

        // Delete een product uit de database
        System.out.print("[Test] Eerst " + productList.size() + " producten, na ProductDAO.delete() ");
        pdao.delete(pdao.findById(7));
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten\n");
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        ReizigerDAOPsql reizigerDAO = new ReizigerDAOPsql(connection);
        AdresDAOPsql adresDAO = new AdresDAOPsql(connection, reizigerDAO);
        OVChipkaartDAOPsql ovChipkaartDAO = new OVChipkaartDAOPsql(connection, reizigerDAO);
        ProductDAOPsql productDAO = new ProductDAOPsql(connection, ovChipkaartDAO);
        testReizigerDAO(reizigerDAO);
        testAdresDAO(adresDAO, reizigerDAO);
        testOVChipkaartDAO(ovChipkaartDAO, reizigerDAO, productDAO);
        testProduct(productDAO, ovChipkaartDAO, reizigerDAO);

        closeConnection();
    }
}
