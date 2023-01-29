public class StudentOsnovne extends Student {

    public StudentOsnovne(String ime, String prezime, String smer, String brojIndeksa) {
        super(ime, prezime, smer, brojIndeksa);
    }

    public String toString(){
        if(!isValid()){
            return "Ovaj objekat nije validan";
        }

        return String.format("Ja sam %s %s. Student sam osnovnih studija na smeru %s", getIme(), getPrezime(), getSmer());
    }

}
