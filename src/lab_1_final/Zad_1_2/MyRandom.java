package lab_1_final.Zad_1_2;

public class MyRandom {
    private final long a;  // mnożnik
    private final long b;  // przyrost
    private final long M;  // moduł
    private long currentSeed; // aktualne ziarno

    public MyRandom(long a, long b, long M, long seed) {
        this.a = a;
        this.b = b;
        this.M = M;
        this.currentSeed = seed;
    }

    public int nextInt() {
        // Implementacja wzoru: x_{n+1} = (a*x_n + b) mod M
        currentSeed = (a * currentSeed + b) % M;
        return (int) currentSeed;
    }

    public double nextDouble() {
        // Generuje liczby z przedziału [0, 1.0)
        return (double) nextInt() / M;
    }

    public double nextDouble(double low, double high) {
        // Generuje liczby z przedziału [low, high)
        if (high < low) {
            throw new IllegalArgumentException("high must be greater than low");
        }
        double u = nextDouble();
        return low + u * (high - low);
    }

    public double exponential(double lambda) {
        // Implementacja metodą odwracania dystrybuanty
        if (lambda <= 0) {
            throw new IllegalArgumentException("lambda must be positive");
        }
        double u = nextDouble();
        return -Math.log(1 - u) / lambda;
    }

    // Metoda dla zadania 2 - rozkład dyskretny
    public double discrete(double[] xx, double[] p) {
        if (xx == null || p == null || xx.length == 0 || p.length == 0 || xx.length != p.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        double u = nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < p.length; i++) {
            cumulativeProbability += p[i];
            if (u < cumulativeProbability) {
                return xx[i];
            }
        }

        return xx[xx.length - 1];
    }

    // Metody pomocnicze do testów statystycznych
    public static double calculateMean(double[] values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    public static double calculateVariance(double[] values, double mean) {
        double sum = 0;
        for (double value : values) {
            sum += Math.pow(value - mean, 2);
        }
        return sum / (values.length - 1);
    }

    // Metoda do obliczania wartości oczekiwanej dla rozkładu dyskretnego
    public static double calculateTheoreticalMean(double[] xx, double[] p) {
        double mean = 0.0;
        for (int i = 0; i < xx.length; i++) {
            mean += xx[i] * p[i];
        }
        return mean;
    }

    // Gettery do testów
    public long getA() {
        return a;
    }

    public long getB() {
        return b;
    }

    public long getM() {
        return M;
    }
}