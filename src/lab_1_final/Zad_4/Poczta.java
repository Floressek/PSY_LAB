package lab_1_final.Zad_4;

import dissimlab.random.RNGenerator;

public class Poczta {
    private final int N;  // liczba okienek
    private final double[] czasZajetosciOkienek;  // tablica czasów zajętości okienek
    private final double mi; // średnia intensywność obsługi
    private final RNGenerator rng;
    private double simTime;  // czas symulacji
    private int obsluzeni;   // liczba obsłużonych klientów
    private int odrzuceni;   // liczba odrzuconych klientów

    public Poczta(int liczbaOkienek, double mi) {
        this.N = liczbaOkienek;
        this.czasZajetosciOkienek = new double[N];
        this.mi = mi;
        this.rng = new RNGenerator();
        this.simTime = 0.0;
        this.obsluzeni = 0;
        this.odrzuceni = 0;
    }

    public void symuluj(int liczInteresantow) {
        double czasNastepnegoKlienta = generujCzasInteresanta();

        while((obsluzeni + odrzuceni) < liczInteresantow) {
            // Przejście do czasu następnego klienta
            simTime = czasNastepnegoKlienta;

            // Szukamy wolnego okienka, zaczynając zawsze od pierwszego
            boolean obsluzony = false;
            int i = 0;
            while (i < N) {
                if (simTime >= czasZajetosciOkienek[i]) {
                    // Okienko jest wolne
                    double czasObslugi = generujCzasObslugi();
                    czasZajetosciOkienek[i] = simTime + czasObslugi;
                    obsluzony = true;
                    obsluzeni++;
                    break;
                }
                i++;
            }

            if(!obsluzony) {
                odrzuceni++;
            }

            // Generowanie czasu przyjścia następnego klienta
            czasNastepnegoKlienta = simTime + generujCzasInteresanta();
        }
    }

    private double generujCzasInteresanta() {
        return rng.exponential(1.0/2.0);  // średnio co 2 minuty
    }

    private double generujCzasObslugi() {
        return rng.exponential(mi);
    }

    public void wypiszStatystyki() {
        int laczenieInteresanci = obsluzeni + odrzuceni;

        System.out.println("Statystyki symulacji poczty:");
        System.out.println("--------------------------------------------");
        System.out.printf("Liczba okienek: %d%n", N);
        System.out.printf("Średnia intensywność obsługi (mi): %.2f%n", mi);
        System.out.printf("Całkowity czas symulacji: %.2f minut%n", simTime);
        System.out.printf("Całkowita liczba interesantów: %d%n", laczenieInteresanci);
        System.out.printf("Obsłużeni interesanci: %d (%.2f%%)%n",
                obsluzeni, (100.0 * obsluzeni / laczenieInteresanci));
        System.out.printf("Odrzuceni interesanci: %d (%.2f%%)%n",
                odrzuceni, (100.0 * odrzuceni / laczenieInteresanci));

        System.out.println("\nCzasy ostatniej zajętości okienek:");
        for (int i = 0; i < N; i++) {
            System.out.printf("Okienko %d: %.2f minut%n", i+1, czasZajetosciOkienek[i]);
        }
        System.out.println("--------------------------------------------");
    }
}