public class ZatvaranjeVrata extends Zadatak{

    final private Vrata vrata;

    public ZatvaranjeVrata(Vrata vrata) {
        this.vrata = vrata;
    }

    @Override
    public void execute() {
        if(vrata.getTrenutnoStanje() == Vrata.Stanje.OTVORENA){
            System.out.println("Zatvaram vrata");
            vrata.setTrenutnoStanje(Vrata.Stanje.ZATVORENA);
        }else{
            System.out.println("Vec su zatvorena");
        }
    }

    @Override
    public String getName() {
        return "Zatvarac Vrata";
    }


}
