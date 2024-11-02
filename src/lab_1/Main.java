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
//        testTask3();
    }

    public static void testTask1() {
        RandomGenerator generator = new RandomGenerator(27, 11, 100, 1);
        Random rndJava = new Random();

        System.out.println("--------------------------------------------");
        System.out.println("10 wartosci dla zadanych parametrow -> [ a=97, b=11, M=100, seed=1 ]");
        System.out.println("--------------------------------------------");
        // Test nextInt()
        System.out.println("Generowanie 10 liczb całkowitych [0, 100):");
        IntStream.range(0, 10).mapToObj(i -> generator.nextInt() + " ").forEach(System.out::print);
        System.out.println("\n");

        // Test nextDouble()
        System.out.println("Generowanie 10 liczb [0, 1.0):");
        IntStream.range(0, 100).forEach(i -> System.out.printf("%.4f ", generator.nextDouble()));
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
        int iteration = 100000;

        double sum = 0;
        for(int i = 0; i < iteration; i++) {
            sum += rnd.distribiution_1();
        }
        System.out.println("--------------------------------------------");
        System.out.println("rozklad1() (wykres i.) srednia: " + sum / iteration + ", wartosc oczekiwana: " +  1.0 / 4.0);
        System.out.println("--------------------------------------------");

    }
}
