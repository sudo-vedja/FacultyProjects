package funkcionalniinterfejsi.oprema;

public class Robot {
    final private Aktuator aktuatorA;
    final private Aktuator aktuatorB;
    final private Aktuator aktuatorC;
    final private Aktuator aktuatorD;

    public Robot() {
        this.aktuatorA = new Aktuator();
        this.aktuatorB = new Aktuator();
        this.aktuatorC = new Aktuator();
        this.aktuatorD = new Aktuator();
    }

    public Aktuator getAktuatorA() {
        return aktuatorA;
    }

    public Aktuator getAktuatorB() {
        return aktuatorB;
    }

    public Aktuator getAktuatorC() {
        return aktuatorC;
    }

    public Aktuator getAktuatorD() {
        return aktuatorD;
    }
}
