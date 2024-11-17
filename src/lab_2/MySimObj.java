package lab_2;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.random.RNGenerator;

import java.util.ArrayList;
import java.util.List;

public class MySimObj extends dissimlab.simcore.BasicSimObj {
    RNGenerator rng;

    double lambda;      // rozklad wykladniczy czasu pomiedzy zdarzeniami
    double mi;          // srednia czasu obslugi
    double sigma;       // odchylenie standardowe czasu obslugi
    double minCzasOczekiwania;
    double maxCzasOczekiwania;
    int L;              // maksymalna dlugosc kolejki
    int liczbaOkienek;

    List<Interesant> kolejka;
    List<Stanowisko> stanowiska;

    // Liczba interesantow w systemie
    MonitoredVar monVar1;
    // Czas przebywania interesanta w systemie
    MonitoredVar monVar2;

    public MySimObj(double lambda, double mi, double sigma, double minCzasOczekiwania, double maxCzasOczekiwania, int L, int liczbaOkienek) {
        this.lambda = lambda;
        this.mi = mi;
        this.sigma = sigma;
        this.minCzasOczekiwania = minCzasOczekiwania;
        this.maxCzasOczekiwania = maxCzasOczekiwania;
        this.L = L;
        this.liczbaOkienek = liczbaOkienek;

        this.rng = new RNGenerator();

        // Inicjalizacja listy interesantow oraz stanowisk
        this.kolejka = new ArrayList<>();
        this.stanowiska = new ArrayList<>();
        for (int i = 0; i < liczbaOkienek; i++) {
            this.stanowiska.add(new Stanowisko());
        }

        // Inicjalizacja monitorow
        this.monVar1 = new MonitoredVar(0);
        this.monVar2 = new MonitoredVar();
    }

    public MonitoredVar getMonVar1() {
        return monVar1;
    }

    public MonitoredVar getMonVar2() {
        return monVar2;
    }

    public void dodajInteresanta(Interesant interesant) {
        if(kolejka.size() < L) {
            double czasPrzybycia = this.simTime();
            double czasCierpliwosci = rng.uniform(minCzasOczekiwania, maxCzasOczekiwania);
            Interesant nowyInteresant = new Interesant(czasPrzybycia, czasCierpliwosci);
            this.kolejka.add(nowyInteresant);
            monVar1.setValue(kolejka.size() + stanowiska.stream().filter(Stanowisko::isZajete).count());
        }
    }

    public void obsluzInteresanta(Interesant interesant) {
        for (Stanowisko stanowisko : stanowiska) {
            if (!stanowisko.isZajete() && !kolejka.isEmpty()) {
                Interesant obslugiwanyInteresant = kolejka.removeFirst();
                double czasObslugi = rng.normal(mi, sigma);
                stanowisko.rozpocznijObsluge(obslugiwanyInteresant,simTime() + czasObslugi);
                monVar1.setValue(kolejka.size() + stanowiska.stream().filter(Stanowisko::isZajete).count());
                monVar2.setValue(simTime() - obslugiwanyInteresant.getCzasPrzybycia());
            }
        }
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
