package lab_1.Generator;

import lab_1.CustomRandomGenerator;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class RandomGenerator {
    private final long a;  // mnożnik
    private final long b;  // przyrost
    private final long M;  // moduł
    private long currentSeed; // aktualne ziarno

    /**
     * Konstruktor parametryczny generatora
     *
     * @param a    mnożnik
     * @param b    przyrost
     * @param M    moduł
     * @param seed początkowe ziarno
     */
    public RandomGenerator(long a, long b, long M, long seed) {
        this.a = a;
        this.b = b;
        this.M = M;
        this.currentSeed = seed;
    }

    /**
     * Generuje następną liczbę pseudolosową z przedziału [0, 100)
     * używając generatora liniowego mieszanego
     */
    public int nextInt() {
        // Implementacja wzoru: x_{n+1} = (a*x_n + b) mod M
        currentSeed = (a * currentSeed + b) % M;
        return (int) currentSeed;
    }

    /**
     * Generuje liczbę pseudolosową z przedziału [0, 1.0)
     */
    public double nextDouble() {
        return (double) nextInt() / M;
    }

    /**
     * Generuje liczbę pseudolosową z przedziału [low, high)
     */
    public double nextDouble(double low, double high) {
        double u = nextDouble();
        return low + u * (high - low);
    }

    /**
     * Generuje liczbę pseudolosową o rozkładzie wykładniczym
     *
     * @param lambda parametr rozkładu wykładniczego
     */
    public double exponential(double lambda) {
        double u = nextDouble();
        return -Math.log(1 - u) / lambda;
    }

    public double distribiution_1() {
        double u = nextDouble();

        if (u < 0.5) {
            // For u in [0, 0.5), corresponding to x in [-1, 0]
            return 2 * u - 1;
        } else {
            // For u in [0.5, 1), corresponding to x in [0, 2]
            return 4 * (u - 0.5);
        }
    }

    /**
     * Metoda pomocnicza do przeprowadzenia testów generatorów
     *
     * @param numberOfTrials liczba prób
     * @param generator      generator do testowania
     * @param random         generator Java Random
     * @param M              moduł
     * @return wyniki testów
     */
    public static CustomRandomGenerator.Result getResult(int numberOfTrials, RandomGenerator generator, Random random, int M) {
        // Test of our generator
        AtomicInteger customSum = new AtomicInteger();
        IntStream.range(0, numberOfTrials).forEach(i -> customSum.addAndGet(generator.nextInt()));
        double customMean = customSum.get() / (double) numberOfTrials;

        // Test of the random generator we need to pass M as well
        AtomicInteger randomSum = new AtomicInteger();
        IntStream.range(0, numberOfTrials).forEach(i -> randomSum.addAndGet(random.nextInt(M)));
        double randomMean = randomSum.get() / (double) numberOfTrials;

        // Theoretical Mean
        double theoreticalMean = (M - 1) / 2.0;
        return new CustomRandomGenerator.Result(customMean, randomMean, theoreticalMean);
    }

    /**
     * Klasa pomocnicza do przechowywania wyników testów
     */
    public record Result(double customMean, double randomMean, double theoreticalMean) {
    }
}
