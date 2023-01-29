package spa;

public class Main {
    public static double[][] f1(double[] niz, double[][] matrica) throws Exception {
        if (niz.length == 0) {
            throw new Exception("Niz ne može biti prazan.");
        }

        if (matrica.length == 0 || matrica[0].length == 0) {
            throw new Exception("Matrica ne može biti prazna.");
        }

        if (niz.length * niz.length != matrica.length * matrica[0].length) {
            throw new Exception("Nisu ispravne dimenzije niza i matrice za ovu funkciju.");
        }

        double[][] matrica2 = new double[matrica.length][matrica[0].length];

        for (int i=0; i<matrica2.length * matrica2[0].length; i++) {
            int indeksNiza = i % niz.length;

            int y = i / matrica2[0].length;
            int x = i % matrica2[0].length;

            matrica2[y][x] = matrica[y][x] * niz[indeksNiza];
        }

        return matrica2;
    }

    public static double[][] f2(double[][] matrica) throws Exception {
        if (matrica.length == 0 || matrica[0].length == 0) {
            throw new Exception("Matrica ne može biti prazna.");
        }

        double[][] matrica2 = new double[matrica[0].length][matrica.length];

        for (int y=0; y<matrica.length; y++) {
            for (int x=0; x<matrica[0].length; x++) {
                matrica2[matrica[0].length-x-1][y] = matrica[y][x];
            }
        }

        return matrica2;
    }

    public static void print(double[][] matrica) {
        System.out.println("Matrica:");

        if (matrica.length == 0 || matrica[0].length == 0) {
            System.out.println("Matrica je prazna.");
            return;
        }

        for (double[] red : matrica) {
            for (int x = 0; x<matrica[0].length; x++) {
                System.out.printf("%8.2f ", red[x]);
            }
            System.out.println("");
        }

        System.out.println("");
    }

    public static void print(double[] niz) {
        System.out.println("Niz:");

        if (niz.length == 0) {
            System.out.println("Niz je prazan.");
        }

        for (int i=0; i<niz.length; i++) {
            System.out.printf("%8.2f ", niz[i]);
        }

        System.out.println("");
    }

    public static void main(String[] args) {
        try {
            double[] niz = new double[] { 1.34, -4.1, 11.0, 0 };

            double[][] matrica = new double[][] {
                new double[] {  2.0,  1.1 },
                new double[] { -1.3, -9.1 },
                new double[] { 11.4,  0.0 },
                new double[] {  0.5, -0.1 },
                new double[] {  5.6, 24.6 },
                new double[] { -8.2,  1.9 },
                new double[] {  0.0,  2.0 },
                new double[] {  1.3, 24.7 }
            };

            print(niz);
            print(matrica);

            double[][] matrica2 = f1(niz, matrica);

            print(matrica2);

            double[][] matrica3 = f2(matrica2);

            print(matrica3);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
