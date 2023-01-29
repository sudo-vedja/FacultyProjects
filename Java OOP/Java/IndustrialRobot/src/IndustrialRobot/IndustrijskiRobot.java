package ga_2018201081;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
podataka o nazivu proizvođača, modelu, godini proizvodnje,
broju stepeni slobode kretanja, potrošnji električne energije,
maksimalnom garantovanom broju sati rada i vrsti pogona pokretnih delova.
Za sve članove samostalno odrediti tipove podataka osim za član podatak za
vrstu pogona pokretnih delova koji treba da bude enum tip mogućih vrednosti:
hidraulični pogon, elektromotorni pogon ili magnetni linearni aktuator.
 */
public class IndustrijskiRobot {

    public enum VrstaPogona {
        HP, EP, MLA
    }

    private String nazivProizvodjaca;
    private String model;
    private int godina;
    private int bssk;
    private double energija;
    private int maxSati;
    private VrstaPogona pogon;

    public IndustrijskiRobot(String nazivProizvodjaca, String model, int godina, int bssk, double energija, int maxSati, VrstaPogona pogon) {
        this.nazivProizvodjaca = nazivProizvodjaca;
        this.model = model;
        this.godina = godina;
        this.bssk = bssk;
        this.energija = energija;
        this.maxSati = maxSati;
        this.pogon = pogon;
    }

    public String getNazivProizvodjaca() {
        return nazivProizvodjaca;
    }

    public String getModel() {
        return model;
    }

    public int getGodina() {
        return godina;
    }

    public int getBssk() {
        return bssk;
    }

    public double getEnergija() {
        return energija;
    }

    public int getMaxSati() {
        return maxSati;
    }

    public VrstaPogona getPogon() {
        return pogon;
    }

    public double getEfikasnost() {

        double L = 0;

        switch (this.pogon) {
            case MLA:
                L = 2.0;
                break;
            case EP:
                L = 1.0;
                break;
            case HP:
                L = 0.33;
                break;
        }

        return this.energija * Math.sqrt(this.maxSati) / ((this.bssk - 2) * L);
    }

    @Override
    public String toString() {
        String rezultat = "";

        rezultat += String.format(
                "%-20s%-20s%-30s%9.2f W%n",
                "Naziv proizvođača:",
                this.nazivProizvodjaca,
                "Potrošnja energije:",
                this.energija
        );

        rezultat += String.format(
                "%-20s%-20s%-30s%6d sati%n",
                "Naziv modela:",
                this.model,
                "Maks. broj sati rada:",
                this.maxSati
        );

        rezultat += String.format(
                "%-20s%19d %-30s%6d sslk\n",
                "Godina proizvodnje:",
                this.godina,
                "Broj stepeni slobode kretanja:",
                this.bssk
        );

        String pogonPrikaz = "";

        switch (this.pogon) {
            case MLA:
                pogonPrikaz = "Pogon: Magnetni linearni aktuator";
                break;
            case EP:
                pogonPrikaz = "Pogon: EP";
                break;
            case HP:
                pogonPrikaz = "Pogon: HP";
                break;
        }

        rezultat += String.format(
                "%-40s%-30s%9.2f #\n",
                pogonPrikaz,
                "Efikasnost:",
                getEfikasnost()
        );

        return rezultat;
    }

    public static List<IndustrijskiRobot> load(String imeDatoteke) {
        List<IndustrijskiRobot> roboti = new ArrayList<>();

        FileInputStream fis;
        try {
            fis = new FileInputStream(imeDatoteke);
            Scanner s = new Scanner(fis);

            while (s.hasNext()) {
                double energija = s.nextDouble();
                int maxSati = s.nextInt();
                int godina = s.nextInt();
                String nazivProizvodjaca = s.nextLine().trim();
                int bssk = s.nextInt();
                String pogonS = s.next().trim();
                String model = s.nextLine().trim();

                VrstaPogona vp = null;

                if (pogonS.equals("MLA")) {
                    vp = VrstaPogona.MLA;
                } else if (pogonS.equals("EMP")) {
                    vp = VrstaPogona.EP;
                } else if (pogonS.equals("HP")) {
                    vp = VrstaPogona.HP;
                }

                roboti.add(new IndustrijskiRobot(nazivProizvodjaca, model, godina, bssk, energija, maxSati, vp));
            }

            s.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Greska: Ne moze da se ucita fajl");
        } catch (IOException ex) {
            System.out.println("Greska: Ne moze da se zatvori fajl");
        }

        return roboti;
    }

}
