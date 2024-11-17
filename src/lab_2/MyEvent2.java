package lab_2;

import dissimlab.simcore.SimControlException;

public class MyEvent2 extends dissimlab.simcore.BasicSimEvent<MySimObj, Object>{

    private final double period;

    public MyEvent2(MySimObj entity, Object o, double period)
            throws SimControlException {
        super(entity, o, period);
        this.period = period;
    }

    @Override
    protected void stateChange() throws SimControlException {
        MySimObj mySimObj = getSimObj();
        System.out.println("\nCzas: " + simTime() + " - Sprawdzanie stanowisk");

    for (Stanowisko stanowisko : mySimObj.stanowiska) {
        if (stanowisko.isZajete() && stanowisko.getCzasKoncaObslugi() <= mySimObj.simTime()) {
            System.out.println("Stanowisko " + mySimObj.stanowiska.indexOf(stanowisko) + " kończy obsługę interesanta.");
            Interesant interesant = stanowisko.getAktywnyInteresant();
            if (interesant != null) {
                mySimObj.monVar2.setValue(mySimObj.simTime() - interesant.getCzasPrzybycia());
                System.out.println("Interesant obsłużony - czas przebywania w systemie: " +
                        (mySimObj.simTime() - interesant.getCzasPrzybycia()));
            }
            stanowisko.zakonczObsluge();
            System.out.println("Stanowisko " + mySimObj.stanowiska.indexOf(stanowisko) + " zwolnione");
            mySimObj.monVar1.setValue(mySimObj.kolejka.size() +
                    mySimObj.stanowiska.stream().filter(Stanowisko::isZajete).count());
        }
    }
    System.out.println("Próba obsługi interesanta przez wolne stanowiska.");
    mySimObj.obsluzInteresanta();
    this.reschedule(this.period);
}

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    public Object getEventParams() {
        return null;
    }
}
