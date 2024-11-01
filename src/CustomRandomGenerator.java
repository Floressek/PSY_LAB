import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CustomRandomGenerator {
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
    public CustomRandomGenerator(long a, long b, long M, long seed) {
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

    /**
     * Metoda testowa pokazująca działanie generatora
     */
    public static void main(String[] args) {
        // Tworzenie generatora z zadanymi parametrami
        CustomRandomGenerator generator = new CustomRandomGenerator(97, 11, 100, 1);

        // Test nextInt()
        System.out.println("Generowanie 10 liczb całkowitych [0, 100):");
        IntStream.range(0, 10).mapToObj(i -> generator.nextInt() + " ").forEach(System.out::print);
        System.out.println("\n");

        // Test nextDouble()
        System.out.println("Generowanie 10 liczb [0, 1.0):");
        IntStream.range(0, 10).forEach(i -> System.out.printf("%.4f ", generator.nextDouble()));
        System.out.println("\n");

        // Test nextDouble(low, high)
        System.out.println("Generowanie 10 liczb [-5.0, 5.0):");
        IntStream.range(0, 10).forEach(i -> System.out.printf("%.4f ", generator.nextDouble(-5.0, 5.0)));
        System.out.println("\n");

        // Test exponential(lambda)
        System.out.println("Generowanie 10 liczb o rozkładzie wykładniczym (lambda=1.0):");
        IntStream.range(0, 10).forEach(i -> System.out.printf("%.4f ", generator.exponential(1.0)));
        System.out.println("\n");

        // Test dla sredniej
        AtomicInteger sum = new AtomicInteger();
        int iteration = 1000;
        IntStream.range(0, iteration).forEach(i -> sum.addAndGet(generator.nextInt()));

        System.out.printf("Średnia z %d wartości nextInt(): %.4f\n", iteration, sum.get() / (double) iteration);

        // Test dla Random::nextInt() a naszym nextInt()
        Random random = new Random(1); // to samo ziarno co nasz generator

        // For testing purposes
        int numberOfTrials = 100000;
        int M = 100;

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

        // Wyświetlenie wyników
        System.out.println("Porównanie generatorów dla " + numberOfTrials + " prób:");
        System.out.println("--------------------------------------------");
        System.out.printf("Własny generator - średnia: %.4f%n", customMean);
        System.out.printf("Generator Java Random - średnia: %.4f%n", randomMean);
        System.out.printf("Wartość teoretyczna: %.4f%n", theoreticalMean);
        System.out.println("--------------------------------------------");
        System.out.printf("Różnica od wartości teoretycznej:%n");
        System.out.printf("Własny generator: %.4f%n", Math.abs(customMean - theoreticalMean));
        System.out.printf("Generator Java Random: %.4f%n", Math.abs(randomMean - theoreticalMean));

    }
}