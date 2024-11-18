package lab_2_poczta;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;

public class Interesant extends BasicSimObj {
    private static int IDs = 0;
    private int ID;
    private double maxWT;
    private double enterTime;

    public Interesant(double maxWT) {
        this.ID = IDs;
        IDs++;
        this.enterTime = simTime();
        this.maxWT = simTime() + maxWT;
    }

    public int getID() {
        return ID;
    }

    public double getMaxWT() {
        return maxWT;
    }

    public double getEnterTime() {
        return enterTime;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}