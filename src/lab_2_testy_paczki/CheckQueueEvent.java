package lab_2_testy_paczki;

import dissimlab.simcore.SimControlException;

import java.util.ArrayList;
import java.util.List;

public class CheckQueueEvent extends dissimlab.simcore.BasicSimEvent<MySimObj, Object> {
    public CheckQueueEvent(MySimObj entity, Object o, double period)
            throws SimControlException {
        super(entity, o, period);
    }

    @Override
    protected void stateChange() throws SimControlException {
        MySimObj mySimObj = getSimObj();
        List<Interesant> doUsuniecia = new ArrayList<>();

        for (Interesant interesant : mySimObj.kolejka) {
            if (simTime() >= (interesant.getCzasPrzybycia() + interesant.getMaxCzasOczekiwania())){
                doUsuniecia.add(interesant);
                mySimObj.incrementNiecierpliwi();
                System.out.println("Interesant niecierpliwy opuszcza kolejkę, czas oczekiwania: " +
                        (simTime() - interesant.getCzasPrzybycia()));
            }
        }

        mySimObj.kolejka.removeAll(doUsuniecia);
        if (!doUsuniecia.isEmpty()) {
            mySimObj.monVar1.setValue(mySimObj.kolejka.size() +
                    mySimObj.stanowiska.stream().filter(Stanowisko::isZajete).count());
        }

        new CheckQueueEvent(mySimObj, null, 0.1);
    }

    @Override
    protected void onTermination() throws SimControlException {
    }

    @Override
    public Object getEventParams() {
        return null;
    }
}