package lab_2_samochody;


import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

// Zdarzenie generowania nowych pojazdów
public class NewCarEvent extends BasicSimEvent<Trasa, Object> {
    public NewCarEvent(Trasa entity, Object o, double delay) throws SimControlException {
        super(entity, o, delay);
    }

    @Override
    protected void stateChange() throws SimControlException {
        Trasa trasa = getSimObj();

        if(trasa.getNextCarTime() <= simTime()) {
            Pojazd pojazd = new Pojazd(trasa.getRandomSpeed());
            trasa.getPojazdyNaTrasie().add(pojazd);

            System.out.println("[" + simTime() + "] Nowy pojazd na trasie. ID: " +
                    pojazd.getId() + ", Pozycja: " + pojazd.getPozycja() +
                    ", Przebyta droga: " + pojazd.getPrzebytaDroga() +
                    ", Czas jazdy: " + (simTime() - pojazd.getCzasStartu()));

            trasa.setNextCarTime(trasa.getRandomNewCarDelay());
            trasa.getLiczbaPojazdow().setValue(trasa.getPojazdyNaTrasie().size());
        }
    }

    @Override
    protected void onTermination() throws SimControlException {}

    @Override
    public Object getEventParams() { return null; }
}
