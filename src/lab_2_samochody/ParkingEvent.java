package lab_2_samochody;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Zdarzenie obsługi parkingu
public class ParkingEvent extends BasicSimEvent<Trasa, Object> {
    private Map<Pojazd, Double> czasStartuParkowania = new HashMap<>();
    private Map<Pojazd, Double> czasKoncaParkowania = new HashMap<>();

    public ParkingEvent(Trasa entity, Object o, double delay) throws SimControlException {
        super(entity, o, delay);
    }

    @Override
    protected void stateChange() throws SimControlException {
        Trasa trasa = getSimObj();
        Iterator<Pojazd> iterator = trasa.getPojazdyNaParkingu().iterator();

        while(iterator.hasNext()) {
            Pojazd pojazd = iterator.next();

            // Jeśli pojazd właśnie zaparkował, wyznacz mu czas końca parkowania
            if (!czasKoncaParkowania.containsKey(pojazd)) {
                double czasParkowania = trasa.getRandomParkingTime();
                czasStartuParkowania.put(pojazd, simTime());
                czasKoncaParkowania.put(pojazd, simTime() + czasParkowania);
                System.out.println("[" + simTime() + "] Pojazd rozpoczyna parkowanie. ID: " +
                    pojazd.getId() + ", Zaplanowany czas postoju: " + String.format("%.2f", czasParkowania));
            }

            // Sprawdź czy już kończy parkowanie
            if (simTime() >= czasKoncaParkowania.get(pojazd)) {
                iterator.remove();
                pojazd.setKierunekPowrotny(true);
                pojazd.setPredkosc(trasa.getRandomSpeed());

                double faktycznyCzasPostoju = simTime() - czasStartuParkowania.get(pojazd);
                System.out.println("[" + simTime() + "] Pojazd rozpoczyna powrót. ID: " +
                    pojazd.getId() + ", Nowa prędkość: " + pojazd.getPredkosc() +
                    ", Faktyczny czas postoju: " + String.format("%.2f", faktycznyCzasPostoju));

                czasStartuParkowania.remove(pojazd);
                czasKoncaParkowania.remove(pojazd);
                trasa.getPojazdyNaTrasie().add(pojazd);
            }
        }
    }

    @Override
    protected void onTermination() throws SimControlException {}

    @Override
    public Object getEventParams() { return null; }
}