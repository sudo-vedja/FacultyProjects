import java.util.ArrayList;
import java.util.List;

public class RepositoryOfExecutables {

    private RepositoryOfExecutables() {
    }

    private static final RepositoryOfExecutables instance = new RepositoryOfExecutables();

    public static RepositoryOfExecutables getInstance(){
        return instance;
    }
    private List<Executable> lista = new ArrayList<>();

    public void add(Executable executable){
        lista.add(executable);
    }


    public void executeNext(){
        int counter = 0;
        lista.get(counter).execute();
        lista.remove(counter);
       // counter ++;
    }

    public int count(){

        return lista.size();
    }
}
