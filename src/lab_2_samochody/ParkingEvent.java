package lab_2_samochody;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.Iterator;

// Zdarzenie obsługi parkingu
public class ParkingEvent extends BasicSimEvent<Trasa, Object> {
    public ParkingEvent(Trasa entity, Object o, double delay) throws SimControlException {
        super(entity, o, delay);
    }

    @Override
    protected void stateChange() throws SimControlException {
        Trasa trasa = getSimObj();
        Iterator<Pojazd> iterator = trasa.getPojazdyNaParkingu().iterator();

        while(iterator.hasNext()) {
            Pojazd pojazd = iterator.next();
            if (simTime() >= pojazd.getCzasStartu() + pojazd.getCzasParkowania()) {
                iterator.remove();
                pojazd.setKierunekPowrotny(true);
                pojazd.setPredkosc(trasa.getRandomSpeed());
                double czasParkowania = simTime() - (pojazd.getCzasStartu() + pojazd.getCzasParkowania());
                pojazd.setCzasParkowania(czasParkowania);
                trasa.getPojazdyNaTrasie().add(pojazd);
                System.out.println("[" + simTime() + "] Pojazd rozpoczyna powrót. ID: " +
                    pojazd.getId() + ", Nowa prędkość: " + pojazd.getPredkosc() +
                    ", Czas parkowania: " + czasParkowania);
            }
        }
    }

    @Override
    protected void onTermination() throws SimControlException {}

    @Override
    public Object getEventParams() { return null; }
}