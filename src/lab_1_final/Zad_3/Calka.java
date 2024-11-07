package lab_1_final.Zad_3;

import java.util.Random;

public class Calka {
    private final Random random;

    public Calka() {
        this.random = new Random();
    }

    public double calculate(double a, double b, IFunc f, int rep) {
        if (a >= b) {
            throw new IllegalArgumentException("Incorrect range [a, b], a must be less than b");
        }
        if (rep <= 0) {
            throw new IllegalArgumentException("Number of repetitions must be greater than 0");
        }

        // Obliczanie wartości maksymalnej funkcji na przedziale [a, b]
        double yMax = f.max(a, b);
        double yMin = -yMax;
        double area = (b - a) * (yMax - yMin);
        int pointsUnder = 0;

        // Obliczanie pola powierzchni pod wykresem
        for (int i = 0; i < rep; i++) {
            double x = a + (b - a) * random.nextDouble();
            double y = yMin + (yMax - yMin) * random.nextDouble();

            // Obliczanie wartości funkcji w punkcie x
            double fValue = f.func(x);
            if ((y >= 0 && y <= fValue) || (y < 0 && y >= fValue)) {
                pointsUnder++;
            }
        }

        // Obliczanie pola powierzchni pod wykresem
        return area * pointsUnder / rep;
    }

    // Metoda do obliczania błędu względnego
    public static double relativeError(double calculated, double exact) {
        return Math.abs((calculated - exact) / exact) * 100;
    }
}