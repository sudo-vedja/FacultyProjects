public class OtvaranjeVrata extends Zadatak {

    final private Vrata vrata;

    public OtvaranjeVrata(Vrata vrata) {
        this.vrata = vrata;
    }

    @Override
    public void execute() {
        if(vrata.getTrenutnoStanje() == Vrata.Stanje.ZATVORENA){
            vrata.setTrenutnoStanje(Vrata.Stanje.OTVORENA);
            System.out.println("Otvaram vrata");
        }else{
            System.out.println("Vec su otvorena");
        }
    }

    @Override
    public String getName() {
        return "Otvarac Vrata";
    }
}
