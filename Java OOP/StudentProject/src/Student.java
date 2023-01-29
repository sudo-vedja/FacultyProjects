import java.util.regex.Pattern;

public abstract class Student implements Validatable{
    private String ime,prezime,smer,brojIndeksa;

    public Student(String ime, String prezime, String smer, String brojIndeksa) {
        this.ime = ime;
        this.prezime = prezime;
        this.smer = smer;
        this.brojIndeksa = brojIndeksa;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getSmer() {
        return smer;
    }

    public String getBrojIndeksa() {
        return brojIndeksa;
    }

    @Override
    public boolean isValid() {
        if(this.ime.length() < 2 || this.prezime.length() < 2 || this.smer.length() <2){
            return false;
        }


        return Pattern.compile("^[0-9]{10}$").matcher(brojIndeksa).matches();
    }
    public abstract String toString();
}
