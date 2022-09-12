package P2.domain;

import java.sql.Date;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    private int getId() {
        return this.id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private String getNaam() {
        return this.voorletters + this.tussenvoegsel + this.achternaam;
    }

    public String toString() {
        return "";
    }
}
