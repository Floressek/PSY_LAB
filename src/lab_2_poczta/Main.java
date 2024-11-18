package lab_2_poczta;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws SimControlException {
        SimManager sm = SimManager.getInstance();
        sm.setEndSimTime(1000);

        Poczta poczta = new Poczta(10, 2, 2, 1, 5, 8, 10, 5, 4);
        NewInteresantEvent nie = new NewInteresantEvent(poczta, null, 0.1);
        LeaveQueueEvent lqe = new LeaveQueueEvent(poczta, null, 0.1);
        ServiceEvent se = new ServiceEvent(poczta, null, 0.1);

        sm.startSimulation();

        Diagram d1 = new Diagram(Diagram.DiagramType.TIME, "Dlugosc kolejki w czasie");
        d1.add(poczta.getQueueLength(), Color.ORANGE);
        d1.show();

        Diagram d2 = new Diagram(Diagram.DiagramType.DISTRIBUTION, "Dystrybuanta czasu przebywania w kolejce");
        d2.add(poczta.getQueueTime(), Color.BLUE);
        d2.show();

        System.out.println("Srednia liczba interesantow: " + Statistics.weightedMean(poczta.getQueueLength()));
        System.out.println("Sredni czas w kolejce: " + Statistics.weightedMean(poczta.getQueueTime()));
    }
}
