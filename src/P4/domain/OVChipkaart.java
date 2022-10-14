package P4.domain;


import java.sql.Date;

public class OVChipkaart {
    private int id;
    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this. saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getId() {
        return id;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getKlasse() {
        return klasse;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "id=" + id +
                ", geldig_tot=" + geldigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                '}';
    }
}
