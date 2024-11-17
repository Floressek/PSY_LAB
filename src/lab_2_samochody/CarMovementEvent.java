package lab_2_samochody;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.Iterator;

// Zdarzenie ruchu pojazdów
public class CarMovementEvent extends BasicSimEvent<Trasa, Object> {
    public CarMovementEvent(Trasa entity, Object o, double delay) throws SimControlException {
        super(entity, o, delay);
    }

    @Override
    protected void stateChange() throws SimControlException {
        Trasa trasa = getSimObj();
        Iterator<Pojazd> iterator = trasa.getPojazdyNaTrasie().iterator();

        while(iterator.hasNext()) {
            Pojazd pojazd = iterator.next();
            double nowyDystans;

            if (!pojazd.isKierunekPowrotny()) {
                nowyDystans = pojazd.getPrzebytaDroga() + pojazd.getPredkosc() * 0.1;
            } else {
                nowyDystans = pojazd.getPrzebytaDroga() - pojazd.getPredkosc() * 0.1;
            }

            pojazd.setPrzebytaDroga(nowyDystans);

            int nowyOdcinek;
            if (!pojazd.isKierunekPowrotny()) {
                nowyOdcinek = (int)(nowyDystans / trasa.getDlugoscOdcinka());
            } else {
                nowyOdcinek = trasa.getLiczbaOdcinkow() -
                    (int)((trasa.getLiczbaOdcinkow() * trasa.getDlugoscOdcinka() - nowyDystans)
                    / trasa.getDlugoscOdcinka());
            }

            if (nowyOdcinek != pojazd.getPozycja()) {
                pojazd.setPozycja(nowyOdcinek);
                System.out.println("[" + simTime() + "] Zmiana odcinka. ID: " +
                    pojazd.getId() + ", Pozycja: " + pojazd.getPozycja() +
                    ", Przebyta droga: " + pojazd.getPrzebytaDroga() +
                    ", Kierunek: " + (pojazd.isKierunekPowrotny() ? "powrotny" : "do przodu") +
                    ", Czas jazdy: " + (simTime() - pojazd.getCzasStartu()));

                // Sprawdzenie czy pojazd dotarł do końca trasy
                if (!pojazd.isKierunekPowrotny() && nowyOdcinek >= trasa.getLiczbaOdcinkow()) {
                    iterator.remove();
                    pojazd.setPrzebytaDroga(trasa.getLiczbaOdcinkow() * trasa.getDlugoscOdcinka());
                    trasa.getPojazdyNaParkingu().add(pojazd);
                    System.out.println("[" + simTime() + "] Pojazd zaparkował. ID: " +
                        pojazd.getId() + ", Całkowity czas jazdy: " +
                        (simTime() - pojazd.getCzasStartu()));
                }
                // Sprawdzenie czy pojazd zakończył całą trasę (powrót)
                else if (pojazd.isKierunekPowrotny() && nowyOdcinek <= 0) {
                    iterator.remove();
                    double calkowityCzasPrzejazdu = simTime() - pojazd.getCzasStartu();
                    trasa.getCzasPrzejazdu().setValue(calkowityCzasPrzejazdu);
                    System.out.println("[" + simTime() + "] Pojazd zakończył trasę. ID: " +
                        pojazd.getId() + ", Całkowity czas przejazdu: " + calkowityCzasPrzejazdu);
                }
            }
        }
        trasa.getLiczbaPojazdow().setValue(trasa.getPojazdyNaTrasie().size());
    }

    @Override
    protected void onTermination() throws SimControlException {}

    @Override
    public Object getEventParams() { return null; }
}