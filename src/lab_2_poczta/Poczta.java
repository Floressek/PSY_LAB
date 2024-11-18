package lab_2_poczta;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.random.RNGenerator;
import dissimlab.simcore.BasicSimObj;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Poczta extends BasicSimObj {
    private RNGenerator rnd;
    private double miST;
    private double sigmaST;
    private double ATLambda;
    private double minAT;
    private double maxAT;
    private double minWT;
    private double maxWT;

    private double queueMaxLength;
    private double nextInteresantTime = 0.0;
    private static int interesantId = 0;


    private int windowsCount;
    private LinkedList<Interesant> queue;

    private List<Okienko> windows;
    private MonitoredVar queueTime;
    private MonitoredVar queueLength;
    private int niecierpliwi = 0;
    private int obsluzeni = 0;

    public Poczta(double miST, double sigmaST, double ATLambda, double minAT, double maxAT, double minWT, double maxWT,
                  double queueMaxLength, int windowsCount) {
        this.rnd = new RNGenerator();
        this.miST = miST;
        this.windowsCount = windowsCount;
        this.sigmaST = sigmaST;
        this.ATLambda = ATLambda;
        this.minAT = minAT;
        this.maxAT = maxAT;
        this.minWT = minWT;
        this.maxWT = maxWT;
        this.queueMaxLength = queueMaxLength;
        this.queueTime = new MonitoredVar();
        this.queueLength = new MonitoredVar();
        this.queue = new LinkedList<>();
        this.windows = new ArrayList<>();
        for (int i = 0; i < windowsCount; i++) {
            windows.add(new Okienko());
        }
    }

    public double getRandomNewInteresantDelay() {
        return minAT + (maxAT - minAT) * rnd.exponential(ATLambda);
    }

    public double getRandomServiceTime() {
        return rnd.normal(miST, sigmaST);
    }

    public double getRandomWaitTime() {
        return rnd.uniform(minWT, maxWT);
    }

    public LinkedList<Interesant> getQueue() {
        return queue;
    }

    public List<Okienko> getWindows() {
        return windows;
    }

    public double getNextInteresantTime() {
        return nextInteresantTime;
    }

    public void setNextInteresantTime(double nextInteresantTime) {
        this.nextInteresantTime = nextInteresantTime + simTime();
    }

    public double getQueueMaxLength() {
        return queueMaxLength;
    }

    public int getInteresantId() {
        int id = interesantId;
        interesantId++;
        return id;
    }

    public MonitoredVar getQueueTime() {
        return queueTime;
    }

    public MonitoredVar getQueueLength() {
        return queueLength;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }

    public void incrementObsluzeni() {
        obsluzeni++;
    }

    public void incrementNiecierpliwi() {
        niecierpliwi++;
    }

    public int getNiecierpliwi() {
        return niecierpliwi;
    }

    public int getObsluzeni() {
        return obsluzeni;
    }
}