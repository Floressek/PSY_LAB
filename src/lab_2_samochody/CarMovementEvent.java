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
        double calkowitaDlugoscTrasy = trasa.getLiczbaOdcinkow() * trasa.getDlugoscOdcinka();

        while(iterator.hasNext()) {
            Pojazd pojazd = iterator.next();
            double nowyDystans;

            if (!pojazd.isKierunekPowrotny()) {
                // Jazda do przodu - zwiększamy dystans
                nowyDystans = pojazd.getPrzebytaDroga() + pojazd.getPredkosc() * 0.1;
            } else {
                // Jazda powrotna - kontynuujemy liczenie dystansu powyżej całkowitej długości
                nowyDystans = pojazd.getPrzebytaDroga() + pojazd.getPredkosc() * 0.1;
            }

            pojazd.setPrzebytaDroga(nowyDystans);

            int nowyOdcinek;
            if (!pojazd.isKierunekPowrotny()) {
                // W drodze do końca trasy
                nowyOdcinek = (int)(nowyDystans / trasa.getDlugoscOdcinka());
            } else {
                // W drodze powrotnej - obliczamy pozycję od końca
                double dystansOdKonca = nowyDystans - calkowitaDlugoscTrasy;
                nowyOdcinek = trasa.getLiczbaOdcinkow() - 1 -
                    (int)(dystansOdKonca / trasa.getDlugoscOdcinka());
            }

            if (nowyOdcinek != pojazd.getPozycja()) {
                pojazd.setPozycja(nowyOdcinek);
                System.out.println("[" + simTime() + "] Zmiana odcinka. ID: " +
                    pojazd.getId() + ", Pozycja: " + pojazd.getPozycja() +
                    ", Przebyta droga: " + String.format("%.2f", pojazd.getPrzebytaDroga()) +
                    ", Kierunek: " + (pojazd.isKierunekPowrotny() ? "powrotny" : "do przodu") +
                    ", Czas jazdy: " + String.format("%.2f", (simTime() - pojazd.getCzasStartu())));

                // Dotarcie do końca trasy (pierwsza połowa podróży)
                if (!pojazd.isKierunekPowrotny() && nowyOdcinek >= trasa.getLiczbaOdcinkow()) {
                    iterator.remove();
                    pojazd.setPrzebytaDroga(calkowitaDlugoscTrasy);
                    trasa.getPojazdyNaParkingu().add(pojazd);
                    double czasJazdy = simTime() - pojazd.getCzasStartu();
                    System.out.println("[" + simTime() + "] Pojazd zaparkował. ID: " +
                        pojazd.getId() + ", Czas jazdy w jedną stronę: " +
                        String.format("%.2f", czasJazdy));
                }
                // Dotarcie do początku (koniec całej podróży)
                else if (pojazd.isKierunekPowrotny() && nowyOdcinek <= 0) {
                    iterator.remove();
                    double calkowityCzasPrzejazdu = simTime() - pojazd.getCzasStartu();
                    trasa.getCzasPrzejazdu().setValue(calkowityCzasPrzejazdu);
                    System.out.println("[" + simTime() + "] Pojazd zakończył całą trasę. ID: " +
                        pojazd.getId() + ", Całkowity czas przejazdu: " +
                        String.format("%.2f", calkowityCzasPrzejazdu));
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