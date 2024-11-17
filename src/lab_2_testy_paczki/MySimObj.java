package lab_2_testy_paczki;

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

    private int liczbaObsluzonych = 0;
    private int liczbaNiecierpliwych = 0;
    private double nextInteresantTime = 0.0;

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

    // Dodaj gettery
    public int getLiczbaObsluzonych() { return liczbaObsluzonych; }
    public int getLiczbaNiecierpliwych() { return liczbaNiecierpliwych; }

    // Dodaj metody inkrementacji
    public void incrementObsluzeni() { liczbaObsluzonych++; }
    public void incrementNiecierpliwi() { liczbaNiecierpliwych++; }

    // Dodaj gettery i settery
    public double getNextInteresantTime() {
        return nextInteresantTime;
    }

    public void setNextInteresantTime(double time) {
        this.nextInteresantTime = time;
    }

    public MonitoredVar getMonVar1() {
        return monVar1;
    }

    public MonitoredVar getMonVar2() {
        return monVar2;
    }

    public void dodajInteresanta() {
        if (kolejka.size() < L) {
            // Czas przybycia interesanta
            double czasPrzybycia = this.simTime();
            // Czas cierpliwosci interesanta jako zmienna rownomierna
            double czasCierpliwosci = rng.uniform(minCzasOczekiwania, maxCzasOczekiwania);

            Interesant nowyInteresant = new Interesant(czasPrzybycia, czasCierpliwosci);
            this.kolejka.add(nowyInteresant);

            // Ustaw czas następnego interesanta
            this.nextInteresantTime = simTime() + rng.exponential(lambda);

            System.out.println("Dodano interesanta - czas przybycia: " + czasPrzybycia +
                    ", max czas oczekiwania: " + czasCierpliwosci +
                    ", długość kolejki: " + kolejka.size());

            // Monitorowanie liczby interesantow w systemie
            monVar1.setValue(kolejka.size() + stanowiska.stream().filter(Stanowisko::isZajete).count());
            System.out.println("Aktualna liczba interesantów w systemie: " +
                    (kolejka.size() + stanowiska.stream().filter(Stanowisko::isZajete).count()));
        } else {
            System.out.println("Kolejka pełna - interesant odrzucony");
        }
    }

    public void obsluzInteresanta() {
        for (Stanowisko stanowisko : stanowiska) {
            if (!stanowisko.isZajete() && !kolejka.isEmpty()) {
                System.out.println("Stanowisko " + stanowiska.indexOf(stanowisko) + " obsługuje interesanta.");
                // Kolejka FIFO
                Interesant obslugiwanyInteresant = kolejka.removeFirst();
                // Czas obsługi interesanta jako zmienna losowa z rozkładu normalnego
                double czasObslugi;
                do {
                    czasObslugi = rng.normal(mi, sigma);
                } while (czasObslugi <= 0); // Upewniamy się, że czas obsługi jest dodatni

                // Ustawiamy czas końca obsługi
                double czasKoncaObslugi = this.simTime() + czasObslugi;
                stanowisko.rozpocznijObsluge(obslugiwanyInteresant, czasKoncaObslugi);

                System.out.println("Rozpoczęto obsługę interesanta - czas przybycia: " +
                        obslugiwanyInteresant.getCzasPrzybycia() +
                        ", czas obsługi: " + czasObslugi +
                        ", planowany koniec: " + czasKoncaObslugi);

                // Monitorowanie liczby interesantów w systemie oraz czasu przebywania interesanta w systemie
                monVar1.setValue(kolejka.size() + stanowiska.stream().filter(Stanowisko::isZajete).count());
                monVar2.setValue(this.simTime() - obslugiwanyInteresant.getCzasPrzybycia());

                System.out.println("Aktualna liczba interesantów w systemie: " +
                        (kolejka.size() + stanowiska.stream().filter(Stanowisko::isZajete).count()) +
                        ", czas w systemie: " + (this.simTime() - obslugiwanyInteresant.getCzasPrzybycia()));
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
