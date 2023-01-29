public class StudentMaster extends Student{

    public StudentMaster(String ime, String prezime, String smer, String brojIndeksa) {
        super(ime, prezime, smer, brojIndeksa);
    }

    public String toString(){
        if(!isValid()){
            return "Ovaj objekat nije validan";
        }

        return String.format("Ja sam %s %s. Student sam master studija na smeru %s", getIme(), getPrezime(), getSmer());
    }
}
