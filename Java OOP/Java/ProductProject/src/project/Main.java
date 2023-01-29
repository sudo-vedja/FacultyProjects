package igb_2018201081;

import java.util.ArrayList;
import java.util.List;

public class IGB_2018201081 {

    public static void main(String[] args) {
        List<Proizvod> proizvodi = Proizvod.load("proizvodi.dat");
        
        for(Proizvod p : proizvodi) {
            System.out.println(p);
        }
        
        System.out.println("---------------------");
        
        //Provera
        List<Proizvod> filtriraniProizvodi = new ArrayList<>();
        
        for(Proizvod p : proizvodi) {
            if (p.getAdekvatnost() < 240) {
                filtriraniProizvodi.add(p);
            }
        }
        
        for(Proizvod p : filtriraniProizvodi) {
            System.out.println(p);
        }
        
        double posto = 1.0 * filtriraniProizvodi.size() / proizvodi.size();
        System.out.printf("Procenat oslaih proizvoda nakon filtriranja %6.2f%%.", posto * 100);
    }

}
