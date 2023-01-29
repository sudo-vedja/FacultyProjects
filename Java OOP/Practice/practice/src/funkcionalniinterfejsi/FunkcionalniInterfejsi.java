package funkcionalniinterfejsi;

import funkcionalniinterfejsi.oprema.Robot;
import funkcionalniinterfejsi.upravljanje.Instrukcija;
import java.util.ArrayList;
import java.util.List;

public class FunkcionalniInterfejsi {
    public static void main(String[] args) {
        Robot robot = new Robot();
        
        robot.getAktuatorA().promeniStanje();
        robot.getAktuatorC().promeniStanje();
        robot.getAktuatorC().promeniStanje();
        robot.getAktuatorC().promeniStanje();
        robot.getAktuatorB().promeniStanje();
        
        RobotRunner robotRunner = new RobotRunner(robot);

        List<Instrukcija> instrukcije = new ArrayList<>();

        instrukcije.add( r -> {
            if (!r.getAktuatorA().jeIspruzen()) {
                r.getAktuatorA().promeniStanje();
            }
        } );

        instrukcije.add( t -> {
            if (!t.getAktuatorB().jeIspruzen()) {
                t.getAktuatorB().promeniStanje();
            }
        } );

        instrukcije.add( r -> {
            if (!r.getAktuatorD().jeIspruzen()) {
                r.getAktuatorD().promeniStanje();
            }
        } );

        instrukcije.add( r -> {
            if (r.getAktuatorC().jeIspruzen()) {
                r.getAktuatorC().promeniStanje();
            }
        } );
        
        /*
        instrukcije.add( r -> {
            if (!r.getAktuatorA().jeIspruzen()) {
                r.getAktuatorA().promeniStanje();
            }
            
            if (!r.getAktuatorB().jeIspruzen()) {
                r.getAktuatorB().promeniStanje();
            }
            
            if (r.getAktuatorC().jeIspruzen()) {
                r.getAktuatorC().promeniStanje();
            }
            
            if (!r.getAktuatorD().jeIspruzen()) {
                r.getAktuatorD().promeniStanje();
            }
        });
        */

        robotRunner.run(
            instrukcije,
            r -> {
                boolean a, b, c, d;

                a = r.getAktuatorA().jeIspruzen();
                b = r.getAktuatorB().jeIspruzen();
                c = r.getAktuatorC().jeIspruzen();
                d = r.getAktuatorD().jeIspruzen();

                return a && b && !c && d;
            }
        );
    }
}
