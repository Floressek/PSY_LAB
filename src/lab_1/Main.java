package lab_1;

import lab_1.Generator.RandomGenerator;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static lab_1.Generator.RandomGenerator.getResult;
import static lab_1.Generator.RandomGenerator.Result;


public class Main {
    public static void main(String[] args) {
        testTask1();
        testTask2();
        testTask3();
    }

    public static void testTask1() {
        // Generator parameters
        int a = 97;
        int b = 11;
        int M = 100;
        int seed = 1;

        // For displaying results
        int numberRepetition = 100;

        // For testing purposes
        int numberOfTrials = 100000;

        RandomGenerator generator = new RandomGenerator(a, b, M, seed);
        Random rndJava = new Random(seed);

        System.out.println("--------------------------------------------");
        System.out.printf("%d wartosci dla zadanych parametrow -> [ a= %d, b= %d, M= %d, seed= %d ]\n",numberRepetition, a, b, M, seed);
        System.out.println("--------------------------------------------");
        // Test nextInt()
        System.out.printf("Generowanie %d liczb całkowitych [0, 100):", numberRepetition);
        IntStream.range(0, numberRepetition).mapToObj(i -> generator.nextInt() + " ").forEach(System.out::print);
        System.out.println("\n");

        // Test nextDouble()
        System.out.printf("Generowanie %d liczb [0, 1.0):", numberRepetition);
        IntStream.range(0, numberRepetition).forEach(i -> System.out.printf("%.4f ", generator.nextDouble()));
        System.out.println("\n");

        // Test nextDouble(low, high)
        System.out.printf("Generowanie %d liczb [-5.0, 5.0):", numberRepetition);
        IntStream.range(0, numberRepetition).forEach(i -> System.out.printf("%.4f ", generator.nextDouble(-5.0, 5.0)));
        System.out.println("\n");

        // Test exponential(lambda)
        System.out.printf("Generowanie %d liczb o rozkładzie wykładniczym (lambda=1.0):", numberRepetition);
        IntStream.range(0, numberRepetition).forEach(i -> System.out.printf("%.4f ", generator.exponential(1.0)));
        System.out.println("\n");

        // Test dla sredniej
        AtomicInteger sum = new AtomicInteger();
        int iteration = 1000;
        IntStream.range(0, iteration).forEach(i -> sum.addAndGet(generator.nextInt()));
        var r = IntStream.range(0, iteration)
                .map(i -> generator.nextInt())
                .sum();

        System.out.printf("Średnia z %d wartości nextInt(): %.4f\n", iteration, sum.get() / (double) iteration);

        // Test dla Random::nextInt() a naszym nextInt()
        Random random = new Random(1); // to samo ziarno co nasz generator


        // Wyniki testów dla generatorów
        CustomRandomGenerator.Result result = getResult(numberOfTrials, generator, random, M);

        // Wyświetlenie wyników
        System.out.println("Porównanie generatorów dla " + numberOfTrials + " prób:");
        System.out.println("--------------------------------------------");
        System.out.printf("Własny generator - średnia: %.4f%n", result.customMean());
        System.out.printf("Generator Java Random - średnia: %.4f%n", result.randomMean());
        System.out.printf("Wartość teoretyczna: %.4f%n", result.theoreticalMean());
        System.out.println("--------------------------------------------");
        System.out.printf("Różnica od wartości teoretycznej:%n");
        System.out.printf("Własny generator: %.4f%n", Math.abs(result.customMean() - result.theoreticalMean()));
        System.out.printf("Generator Java Random: %.4f%n", Math.abs(result.randomMean() - result.theoreticalMean()));
    }

    public static void testTask2() {
        RandomGenerator rnd = new RandomGenerator(97, 11, 100, 1);
        int iteration = 1000;

        double sum = 0;
        for(int i = 0; i < iteration; i++) {
            sum += rnd.distribiution_1();
        }
        System.out.println("--------------------------------------------");
        System.out.println("rozklad1() (wykres i.) srednia: " + sum / iteration + ", wartosc oczekiwana: " +  1.0 / 4.0);
        System.out.println("--------------------------------------------");

//        STR
//                . "Today's weather is \{ feelsLike }, with a temperature of \{ temperature } degrees \{ unit }" ;

    }

    public static void testTask3() {
        // Initialize arrays
        double[] xx = {-2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0};
        double[] p = {0.1, 0.2, 0.1, 0.05, 0.1, 0.3, 0.2}; // probabilities sum to 1.0


        RandomGenerator rnd = new RandomGenerator(97, 11, 100, 1);

        // Generate 1000 values
        int iterations = 1000;
        double sum = 0;
        for(int i = 0; i < iterations; i++) {
            sum += rnd.discrete(xx, p);
        }

        // Calculate mean
        double empiricalMean = sum / iterations;

        // Calculate expected value
        double expectedValue = RandomGenerator.calculateTheoreticalMean(xx, p);

        // Print results
        System.out.println("Discrete Distribution Test Results:");
        System.out.println("--------------------------------------------");
        System.out.printf("Empirical Mean (n=%d): %.4f%n", iterations, empiricalMean);
        System.out.printf("Theoretical Mean: %.4f%n", expectedValue);
        System.out.printf("Absolute Difference: %.4f%n", Math.abs(empiricalMean - expectedValue));
    }
}
