package lab_2_poczta;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;

public class Okienko extends BasicSimObj {
    private static int IDs;
    private int ID;
    private double serviceTime;
    private boolean occupied;
    private Interesant servicedInteresant;

    public Okienko() {
        this.ID = IDs;
        IDs++;
    }

    public void occupy(Interesant interesant, double serviceTime) {
        servicedInteresant = interesant;
        this.serviceTime = simTime() + serviceTime; // simTime() - zwraca aktualny czas symulacji
        this.occupied = true;
    }

    public Interesant free() {
        this.occupied = false;
        // Branie interesanta z okienka i zwracanie go na wolnosc
        Interesant i = servicedInteresant;
        this.servicedInteresant = null;
        return i;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}