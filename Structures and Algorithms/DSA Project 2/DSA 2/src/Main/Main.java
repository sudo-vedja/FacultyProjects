package Main;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Integer[][] m = new Integer[][] {
            new Integer[] {  1,  3, -5 },
            new Integer[] {  7,  2,  8 },
            new Integer[] {  4, -5,  7 },
            new Integer[] { -4, -9,  9 }
        };

        Integer[] res = vratiKoordinateOkviraNajveceSume(m);

        System.out.println("y1 = " + res[0] + ", x1 = " + res[1] + " -> y2 = " + res[2] + ", x2 = " + res[3]);
    }

    public static Integer[] vratiKoordinateOkviraNajveceSume(Integer[][] matrica) throws Exception {
        if (matrica.length < 2 || matrica[0].length == 0) {
            throw new Exception("Matrica je prazna.");
        }

        Map<Integer[], Integer> isprobani = new HashMap<>();

        for (int y1=0; y1<matrica.length-1; y1++) {
            for (int x1=0; x1<matrica[0].length-1; x1++) {
                for (int y2=y1+1; y2<matrica.length; y2++) {
                    for (int x2=x1+1; x2<matrica[0].length; x2++) {
                        Integer[] koordinate = new Integer[] { y1, x1, y2, x2 };

                        int suma = 0;
                        for (int y=y1; y<=y2; y++) {
                            for (int x=x1; x<=x2; x++) {
                                suma += matrica[y][x];
                            }
                        }

                        isprobani.put(koordinate, suma);
                    }
                }
            }
        }

        Integer[] koordinateNajveceSume = (Integer[]) isprobani.keySet().toArray()[0];
        Integer najvecaSuma = (Integer) isprobani.values().toArray()[0];

        for (Integer[] koordinate : isprobani.keySet()) {
            // System.out.println("y1 = " + koordinate[0] + ", x1 = " + koordinate[1] + " -> y2 = " + koordinate[2] + ", x2 = " + koordinate[3] + ": suma = " + isprobani.get(koordinate));

            if (najvecaSuma < isprobani.get(koordinate)) {
                koordinateNajveceSume = koordinate;
                najvecaSuma = isprobani.get(koordinate);
            }
        }

        return koordinateNajveceSume;
    }
}
