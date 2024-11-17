package lab_2_samochody;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.random.RNGenerator;
import dissimlab.simcore.BasicSimObj;

import java.util.ArrayList;
import java.util.List;

// Główny obiekt symulacyjny
public class Trasa extends BasicSimObj {
    private RNGenerator rng;

    // Parametry symulacji
    private double lambda; // parametr rozkładu wykładniczego dla nowych pojazdów
    private double minPredkosc;
    private double maxPredkosc;
    private double minCzasParkowania;
    private double maxCzasParkowania;
    private int liczbaOdcinkow;
    private double dlugoscOdcinka;
    private double nextCarTime = 0.0;

    // Stan systemu
    private List<Pojazd> pojazdyNaTrasie;
    private List<Pojazd> pojazdyNaParkingu;

    // Monitorowane zmienne
    private MonitoredVar liczbaPojazdow;
    private MonitoredVar czasPrzejazdu;

    public Trasa(double lambda, double minPredkosc, double maxPredkosc,
                 double minCzasParkowania, double maxCzasParkowania,
                 int liczbaOdcinkow, double dlugoscOdcinka) {
        this.rng = new RNGenerator();
        this.lambda = lambda;
        this.minPredkosc = minPredkosc;
        this.maxPredkosc = maxPredkosc;
        this.minCzasParkowania = minCzasParkowania;
        this.maxCzasParkowania = maxCzasParkowania;
        this.liczbaOdcinkow = liczbaOdcinkow;
        this.dlugoscOdcinka = dlugoscOdcinka;

        this.pojazdyNaTrasie = new ArrayList<>();
        this.pojazdyNaParkingu = new ArrayList<>();
        this.liczbaPojazdow = new MonitoredVar(0);
        this.czasPrzejazdu = new MonitoredVar();
    }

    public double getRandomNewCarDelay() {
        return -Math.log(1 - rng.uniform(0, 1)) / lambda;
    }

    public double getRandomSpeed() {
        return rng.uniform(minPredkosc, maxPredkosc);
    }

    public double getRandomParkingTime() {
        return rng.uniform(minCzasParkowania, maxCzasParkowania);
    }

    // Gettery
    public List<Pojazd> getPojazdyNaTrasie() { return pojazdyNaTrasie; }
    public List<Pojazd> getPojazdyNaParkingu() { return pojazdyNaParkingu; }
    public MonitoredVar getLiczbaPojazdow() { return liczbaPojazdow; }
    public MonitoredVar getCzasPrzejazdu() { return czasPrzejazdu; }
    public double getNextCarTime() { return nextCarTime; }
    public int getLiczbaOdcinkow() { return liczbaOdcinkow; }
    public double getDlugoscOdcinka() { return dlugoscOdcinka; }

    public void setNextCarTime(double time) {
        this.nextCarTime = time + simTime();
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {}

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }

}
