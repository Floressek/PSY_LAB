package lab_2_poczta;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class LeaveQueueEvent extends BasicSimEvent<Poczta, Object> {

    public LeaveQueueEvent(Poczta entity, Object o, double delay) throws SimControlException {
        super(entity, o, delay);
    }
    @Override
    protected void stateChange() throws SimControlException {
        Poczta poczta = super.getSimObj();

        for(int i = 0; i < poczta.getQueue().size(); i++) {
            Interesant interesant = poczta.getQueue().get(i);

            if(interesant.getMaxWT() <= simTime()) { // maxWT - maksymalny czas oczekiwania, chca odejsc po tym czasie z kolejki
                poczta.getQueue().remove(interesant);
                System.out.println("[" + simTime() + "] Interesant zniecierpliwil sie i opuszcza kolejke. " +
                        "ID Interesanta: " + interesant.getId() + " Liczba niecierpliwych klientow: " + poczta.getNiecierpliwi()
                        + " Liczba oblsuzonych: " + poczta.getObsluzeni() + " Dlugosc kolejki: " + poczta.getQueue().size());
                poczta.incrementNiecierpliwi();
                poczta.getQueueTime().setValue(simTime() - interesant.getEnterTime());
            }
        }
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    public Object getEventParams() {
        return null;
    }
}