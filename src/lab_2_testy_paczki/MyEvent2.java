package lab_2_testy_paczki;

import dissimlab.simcore.SimControlException;

//public class MyEvent2 extends dissimlab.simcore.BasicSimEvent<MySimObj, Object> {
//
//    public MyEvent2(MySimObj entity, Object o, double period)
//            throws SimControlException {
//        super(entity, o, period);
//    }
//
//    @Override
//    protected void stateChange() throws SimControlException {
//        MySimObj mySimObj = getSimObj();
//        System.out.println("\nCzas: " + simTime() + " - Sprawdzanie stanowisk");
//
//        // Sprawdź każde stanowisko
//        for (Stanowisko stanowisko : mySimObj.stanowiska) {
//            // 1. Sprawdzenie czy stanowisko kończy obsługę
//            if (stanowisko.isZajete() && stanowisko.getCzasKoncaObslugi() <= simTime()) {
//                System.out.println("Stanowisko " + mySimObj.stanowiska.indexOf(stanowisko) + " kończy obsługę interesanta.");
//                Interesant interesant = stanowisko.getAktywnyInteresant();
//                if (interesant != null) {
//                    mySimObj.monVar2.setValue(simTime() - interesant.getCzasPrzybycia());
//                    System.out.println("Interesant obsłużony - czas przebywania w systemie: " +
//                            (simTime() - interesant.getCzasPrzybycia()));
//                }
//                stanowisko.zakonczObsluge();
//                System.out.println("Stanowisko " + mySimObj.stanowiska.indexOf(stanowisko) + " zwolnione");
//            }
//        }
//
//        // 2. Próba obsługi nowych interesantów
//        System.out.println("Próba obsługi interesanta przez wolne stanowiska.");
//        mySimObj.obsluzInteresanta();
//
//        // 3. Aktualizacja monitorowanej zmiennej
//        mySimObj.monVar1.setValue(mySimObj.kolejka.size() +
//            mySimObj.stanowiska.stream().filter(Stanowisko::isZajete).count());
//
//        // 4. Planowanie następnego zdarzenia bez używania reschedule
//        new MyEvent2(getSimObj(), null, 0.1);
//    }

public class MyEvent2 extends dissimlab.simcore.BasicSimEvent<MySimObj, Object> {
    public MyEvent2(MySimObj entity, Object o, double period)
            throws SimControlException {
        super(entity, o, period);
    }

    @Override
    protected void stateChange() throws SimControlException {
        MySimObj mySimObj = getSimObj();
        System.out.println("\nCzas: " + simTime() + " - Sprawdzanie stanowisk");

        for (Stanowisko stanowisko : mySimObj.stanowiska) {
            if (stanowisko.isZajete() && stanowisko.getCzasKoncaObslugi() <= simTime()) {
                System.out.println("Stanowisko " + mySimObj.stanowiska.indexOf(stanowisko) + " kończy obsługę interesanta.");
                Interesant interesant = stanowisko.getAktywnyInteresant();
                if (interesant != null) {
                    mySimObj.monVar2.setValue(simTime() - interesant.getCzasPrzybycia());
                    System.out.println("Interesant obsłużony - czas przebywania w systemie: " +
                            (simTime() - interesant.getCzasPrzybycia()));
                    mySimObj.incrementObsluzeni();
                }
                stanowisko.zakonczObsluge();
            }
        }

        System.out.println("Próba obsługi interesanta przez wolne stanowiska.");
        mySimObj.obsluzInteresanta();

        new MyEvent2(mySimObj, null, 0.1);
    }

    @Override
    protected void onTermination() throws SimControlException {
    }

    @Override
    public Object getEventParams() {
        return null;
    }
}