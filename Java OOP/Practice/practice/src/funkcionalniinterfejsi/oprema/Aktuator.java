package funkcionalniinterfejsi.oprema;

final public class Aktuator {
    private boolean ispruzen = false;

    public Aktuator() { }

    public boolean jeIspruzen() {
        return ispruzen;
    }

    public Aktuator promeniStanje() {
        this.ispruzen = !this.ispruzen;
        return this;
    }
}
