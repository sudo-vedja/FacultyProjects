package igb_2018201081;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Napraviti klasu Proizvod koja ima članove podatke za čuvanje naziva,
proizvođača, godine proizvodnje, mase i materijale od kojeg je proizvod
napravljen. Za sve članove samostalno odrediti tipove osim za materijalu,
koji treba da bude enum tip mogućih vrednosti: DRVO, PLASTIKA, METAL, STAKLO,
PLATNO ili DRUGI_MATERIJAL.
Napraviti getter metode za članove podatke i konstruktor koji uzima argumente
za vrednosti podataka klase.
 */
public class Proizvod {

    public enum TipMaterijala {
        DRVO, PLASTIKA, METAL, STAKLO, PLATNO, DRUGI_MATERIJAL
    }

    private String nazivProizvoda;
    private String nazivProizvodjaca;
    private int godina;
    private int masa;
    private TipMaterijala materijal;

    public Proizvod(String nazivProizvoda, String nazivProizvodjaca, int godina, int masa, TipMaterijala materijal) {
        this.nazivProizvoda = nazivProizvoda;
        this.nazivProizvodjaca = nazivProizvodjaca;
        this.godina = godina;
        this.masa = masa;
        this.materijal = materijal;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public String getNazivProizvodjaca() {
        return nazivProizvodjaca;
    }

    public int getGodina() {
        return godina;
    }

    public int getMasa() {
        return masa;
    }

    public TipMaterijala getMaterijal() {
        return materijal;
    }

    public double getAdekvatnost() {

        double k = 0;

        switch (this.materijal) {
            case DRVO:
                k = 0.98;
                break;
            case PLASTIKA:
                k = 0.93;
                break;
            case METAL:
                k = 0.21;
                break;
            case STAKLO:
                k = 1.45;
                break;
            case PLATNO:
                k = 1.10;
                break;
            case DRUGI_MATERIJAL:
                k = 2.12;
                break;
        }

        return (2050 - this.godina) * Math.sqrt(this.masa) / (1 - k);
    }

    @Override
    public String toString() {
        String rezultat = "";

        rezultat += String.format(
                "%-12s%-20s%-13s%13.3f kg\n",
                "Naziv:",
                this.nazivProizvoda,
                "Masa:",
                (this.masa / 1000.0)
        );

        rezultat += String.format(
                "%-12s%-20s%-13s%8d. godina\n",
                "Proizvođač:",
                this.nazivProizvodjaca,
                "Proizvedeno:",
                this.godina
        );

        String materijaS = "";

        switch (this.materijal) {
            case DRVO:
                materijaS = "drvo";
                break;
            case PLASTIKA:
                materijaS = "plastika";
                break;
            case METAL:
                materijaS = "metal";
                break;
            case STAKLO:
                materijaS = "staklo";
                break;
            case PLATNO:
                materijaS = "platno";
                break;
            case DRUGI_MATERIJAL:
                materijaS = "drugi materijal";
                break;
        }

        rezultat += String.format(
                "%-12s%-20s%-13s%16.2f\n",
                "Materijal:",
                materijaS,
                "Adekvatnost:",
                getAdekvatnost()
        );

        return rezultat;
    }

    public static List<Proizvod> load(String imeDatoteke) {
        List<Proizvod> proizvodi = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(imeDatoteke);
            Scanner s = new Scanner(fis);

            while(s.hasNext()) {
                String imeProizvoda = s.nextLine().trim();
                String imeProizvodjaca = s.nextLine().trim();
                int godina = s.nextInt();
                int masa = s.nextInt();
                String materijalS = s.nextLine().trim();
                
                TipMaterijala materijal = null;
                
                if (materijalS.equals("D")) {
                    materijal = TipMaterijala.DRVO;
                } else if (materijalS.equals("P")) {
                    materijal = TipMaterijala.PLASTIKA;
                } else if (materijalS.equals("M")) {
                    materijal = TipMaterijala.METAL;
                } else if (materijalS.equals("S")) {
                    materijal = TipMaterijala.STAKLO;
                } else if (materijalS.equals("T")) {
                    materijal = TipMaterijala.PLATNO;
                } else if (materijalS.equals("O")) {
                    materijal = TipMaterijala.DRUGI_MATERIJAL;
                }
                
                proizvodi.add(new Proizvod(imeProizvoda, imeProizvodjaca, godina, masa, materijal));
                
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Greska: Ne moze da se otvori fajl");
        }

        return proizvodi;
    }

}
