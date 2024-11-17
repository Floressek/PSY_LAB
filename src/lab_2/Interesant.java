package lab_2;

public class Interesant {
    private final double czasPrzybycia;
    private final double maxCzasOczekiwania;

    public Interesant(double czasPrzybycia, double maxCzasOczekiwania) {
        this.czasPrzybycia = czasPrzybycia;
        this.maxCzasOczekiwania = maxCzasOczekiwania;
    }

    public double getCzasPrzybycia() {
        return czasPrzybycia;
    }

    public double getMaxCzasOczekiwania() {
        return maxCzasOczekiwania;
    }
}
