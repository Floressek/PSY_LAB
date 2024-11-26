package lab_2_testy_paczki;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;

public class Main {
    public static void main(String[] args) throws SimControlException {
        SimManager sm = SimManager.getInstance();
        // Ustawienie kroku czasowego
        sm.setSimTimeStep(0.1);
        sm.setEndSimTime(1000.0);

        MySimObj mySimObj = new MySimObj(
                0.1,    // lambda
                8.0,    // mi
                2.0,    // sigma
                15.0,   // minCzasOczekiwania
                30.0,   // maxCzasOczekiwania
                20,     // L
                2);     // liczbaOkienek

        double krok = 0.1;
        new MyEvent1(mySimObj, null, krok);
        new MyEvent2(mySimObj, null, krok);
        new CheckQueueEvent(mySimObj, null, krok);

        sm.startSimulation();

        // Wyświetl wyniki
        System.out.println("Średnia liczba interesantów: " +
                Statistics.weightedMean(mySimObj.getMonVar1()));
        System.out.println("Średni czas przebywania w systemie: " +
                Statistics.arithmeticMean(mySimObj.getMonVar2()));

        // Pokaż wykresy
        Diagram d1 = new Diagram(Diagram.DiagramType.TIME, "Liczba interesantów w czasie");
        d1.add(mySimObj.getMonVar1(), java.awt.Color.BLUE);
        d1.show();

        Diagram d2 = new Diagram(Diagram.DiagramType.DISTRIBUTION,
                "Dystrybuanta czasu przebywania w systemie");
        d2.add(mySimObj.getMonVar2(), java.awt.Color.RED);
        d2.show();
    }
}
