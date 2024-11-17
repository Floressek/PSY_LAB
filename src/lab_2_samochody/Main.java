package lab_2_samochody;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;

import java.awt.*;

// Główna klasa
public class Main {
    public static void main(String[] args) throws SimControlException {
        SimManager sm = SimManager.getInstance();
        sm.setEndSimTime(1000.0);

        Trasa trasa = new Trasa(
                0.1,    // lambda dla nowych pojazdów
                50,     // minimalna prędkość
                100,    // maksymalna prędkość
                10,     // minimalny czas parkowania
                20,     // maksymalny czas parkowania
                10,     // liczba odcinków
                100     // długość odcinka
        );

        double krok = 0.1;
        NewCarEvent nce = new NewCarEvent(trasa, null, krok);
        CarMovementEvent cme = new CarMovementEvent(trasa, null, krok);
        ParkingEvent pe = new ParkingEvent(trasa, null, krok);

        sm.startSimulation();

        System.out.println("Średnia liczba pojazdów na trasie: " +
                Statistics.weightedMean(trasa.getLiczbaPojazdow()));
        System.out.println("Średni czas przejazdu: " +
                Statistics.arithmeticMean(trasa.getCzasPrzejazdu()));

        Diagram d1 = new Diagram(Diagram.DiagramType.TIME, "Liczba pojazdów na trasie");
        d1.add(trasa.getLiczbaPojazdow(), Color.BLUE);
        d1.show();

        Diagram d2 = new Diagram(Diagram.DiagramType.DISTRIBUTION,
                "Dystrybuanta czasu przejazdu");
        d2.add(trasa.getCzasPrzejazdu(), Color.RED);
        d2.show();
    }
}