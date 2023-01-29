package ga_2018201081;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GA_2018201081 {

    public static void main(String[] args) {
        System.out.println("Teodor Petrovic, 2018201081, 19.10.2022.\n");

        List<IndustrijskiRobot> roboti = IndustrijskiRobot.load("ulaz.dat");
        List<IndustrijskiRobot> filtriraniRoboti = new ArrayList<>();

        double suma = 0;
        for (IndustrijskiRobot ir : roboti) {
            suma += ir.getEfikasnost();
        }

        double sredEfik = suma / roboti.size();

        for (IndustrijskiRobot ir : roboti) {
            if (ir.getEfikasnost() >= (sredEfik * 0.75) && ir.getEfikasnost() <= (sredEfik * 1.25)) {
                filtriraniRoboti.add(ir);
            }
        }

        try {
            PrintWriter pw = new PrintWriter("izlaz.txt");

            for (IndustrijskiRobot ir : filtriraniRoboti) {
                pw.println(ir);
            }

            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Greska: Nije dobra lokacija");
        }

    }

}
