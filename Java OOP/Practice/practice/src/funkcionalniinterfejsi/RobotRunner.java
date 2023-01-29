package funkcionalniinterfejsi;

import funkcionalniinterfejsi.oprema.Robot;
import funkcionalniinterfejsi.upravljanje.Instrukcija;
import funkcionalniinterfejsi.upravljanje.Provera;
import java.util.List;

final public class RobotRunner {
    final private Robot robot;

    public RobotRunner(Robot robot) {
        this.robot = robot;
    }

    public void run(List<Instrukcija> instrukcije, Provera provera) {
        instrukcije.forEach(i -> i.izvrsi(robot));
        boolean rezultat = provera.ispravnoIzvrseno(robot);
        System.out.printf(
            "Posle izvršavanja svih zadatih instrukcija, utvrđeno je da je robot %s programiran.\n",
            rezultat ? "ispravno" : "neipsravno"
        );
    }
}
