package lab_2;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;

public class Main {
    public static void main(String[] args) throws SimControlException {
        SimManager simMgr = SimManager.getInstance();
        // Ilosc krokow symulacji = 1000 / 0.1 = 10000
        simMgr.setEndSimTime(1000.0);

        MySimObj mySimObj = new MySimObj
                (1.0,
                        8.0,
                        2.0,
                        15.0,
                        30.0,
                        20,
                        2);

        double krok = 0.1;
        MyEvent1 myEvent1 = new MyEvent1(mySimObj, null, krok);
        MyEvent2 myEvent2 = new MyEvent2(mySimObj, null, krok);

        simMgr.startSimulation();

        System.out.println("Średnia liczba interesantów: " +
                Statistics.weightedMean(mySimObj.getMonVar1()));
        System.out.println("Średni czas przebywania w systemie: " +
                Statistics.arithmeticMean(mySimObj.getMonVar2()));

        Diagram d1 = new Diagram(Diagram.DiagramType.TIME, "Liczba interesantów w czasie");
        d1.add(mySimObj.getMonVar1(), java.awt.Color.BLUE);
        d1.show();

        Diagram d2 = new Diagram(Diagram.DiagramType.DISTRIBUTION,
                "Dystrybuanta czasu przebywania w systemie");
        d2.add(mySimObj.getMonVar2(), java.awt.Color.RED);
        d2.show();
    }
}
