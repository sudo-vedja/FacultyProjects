public final class Vrata {

    public enum Stanje{
        OTVORENA,
        ZATVORENA
    }
    Stanje trenutnoStanje = Stanje.ZATVORENA;

    public Vrata() {

    }

    public Stanje getTrenutnoStanje() {
        return trenutnoStanje;
    }

    public void setTrenutnoStanje(Stanje trenutnoStanje) {
        this.trenutnoStanje = trenutnoStanje;
    }
}
