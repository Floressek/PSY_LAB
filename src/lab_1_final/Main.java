package lab_1_final;

import lab_1_final.Zad_3.Calka;
import lab_1_final.Zad_3.Funkcja1;
import lab_1_final.Zad_3.Funkcja2;
import lab_1_final.Zad_3.IFunc;
import lab_1_final.Zad_4.Poczta;
import dissimlab.random.RNGenerator;
import lab_1_final.Zad_1_2.MyRandom;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        testZad1();
        testZad2();
        testZad3();
        testZad4();
    }

    public static void testZad1() {
        // Generator parameters
        int a = 23;
        int b = 11;
        int M = 100;
        int seed = 1;

        // For displaying results
        int numberRepetition = 100;

        // For testing purposes
        int numberOfTrials = 1000;

        MyRandom generator = new MyRandom(a, b, M, seed);
        Random rndJava = new Random(seed);

        System.out.println("--------------------------------------------");
        System.out.printf("%d wartości dla zadanych parametrów -> [ a= %d, b= %d, M= %d, seed= %d ], Szymon Florek WCY22IJ1S1%n",
                numberRepetition, a, b, M, seed);
        System.out.println("--------------------------------------------");

        // Test nextInt()
        System.out.printf("Generowanie %d liczb całkowitych [0, %d):%n", numberRepetition, M);
        IntStream.range(0, numberRepetition)
                .mapToObj(i -> generator.nextInt() + " ")
                .forEach(System.out::print);
        System.out.println("\n");

        // Test nextDouble()
        System.out.printf("Generowanie %d liczb [0, 1.0):%n", numberRepetition);
        IntStream.range(0, numberRepetition)
                .mapToDouble(i -> generator.nextDouble())
                .forEach(d -> System.out.printf("%.4f ", d));
        System.out.println("\n");

        // Test nextDouble(low, high)
        System.out.printf("Generowanie %d liczb [-5.0, 5.0):%n", numberRepetition);
        IntStream.range(0, numberRepetition)
                .mapToDouble(i -> generator.nextDouble(-5.0, 5.0))
                .forEach(d -> System.out.printf("%.4f ", d));
        System.out.println("\n");

        // Test exponential(lambda)
        System.out.printf("Generowanie %d liczb o rozkładzie wykładniczym (lambda=1.0):%n", numberRepetition);
        IntStream.range(0, numberRepetition)
                .mapToDouble(i -> generator.exponential(1.0))
                .forEach(d -> System.out.printf("%.4f ", d));
        System.out.println("\n");

        // Test dla średniej
        double[] samples = new double[numberOfTrials];
        for (int i = 0; i < numberOfTrials; i++) {
            samples[i] = generator.nextInt();
        }

        double mean = MyRandom.calculateMean(samples);
        double variance = MyRandom.calculateVariance(samples, mean);
        double theoreticalMean = (M - 1) / 2.0;
        double relativeError = Math.abs((mean - theoreticalMean) / theoreticalMean) * 100;

        System.out.println("Analiza statystyczna:");
        System.out.println("--------------------------------------------");
        System.out.printf("Średnia empiryczna: %.4f%n", mean);
        System.out.printf("Wariancja empiryczna: %.4f%n", variance);
        System.out.printf("Średnia teoretyczna: %.4f%n", theoreticalMean);
        System.out.printf("Błąd względny średniej: %.2f%%%n", relativeError);
    }

    public static void testZad2() {
        // Initialize arrays
        double[] xx = {-2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0};
        double[] p = {0.1, 0.2, 0.1, 0.05, 0.1, 0.3, 0.2}; // probabilities sum to 1.0

        MyRandom rnd = new MyRandom(23, 11, 100, 1);

        System.out.println("\nZADANIE 2 - Rozkład dyskretny");
        System.out.println("--------------------------------------------");

        // Test dla pojedynczych wartości
        System.out.println("Pierwsze 10 wygenerowanych wartości:");
        for (int i = 0; i < 10; i++) {
            System.out.printf("%.2f ", rnd.discrete(xx, p));
        }
        System.out.println("\n");

        // Generate 1000 values
        int iterations = 1000;
        double sum = 0;
        for (int i = 0; i < iterations; i++) {
            sum += rnd.discrete(xx, p);
        }

        // Calculate mean
        double empiricalMean = sum / iterations;

        // Calculate expected value
        double theoreticalMean = MyRandom.calculateTheoreticalMean(xx, p);

        // Print results
        System.out.println("Analiza statystyczna:");
        System.out.println("--------------------------------------------");
        System.out.printf("Średnia empiryczna (n=%d): %.4f%n", iterations, empiricalMean);
        System.out.printf("Średnia teoretyczna: %.4f%n", theoreticalMean);
        System.out.printf("Błąd względny: %.4f%%%n",
                Math.abs((empiricalMean - theoreticalMean) / theoreticalMean) * 100);
    }

    public static void testZad3() {
        System.out.println("ZADANIE 3 - Całkowanie metodą Monte Carlo");
        System.out.println("--------------------------------------------");

        // Initialize objects
        Calka calka = new Calka();
        IFunc f1 = new Funkcja1();
        IFunc f2 = new Funkcja2();

        // Parameters for the integral 1
        double a1 = 1.0;
        double b1 = Math.E;

        // Parameters for the integral 2
        double a2 = 0;
        double b2 = 4;


        // Exact value of the integral 1
        double exact1 = 3.0;
        // Exact value of the integral 2
        double exact2 = 16.0; // całka z 2x od 0 do 4 = 16

        int[] points = {1000, 10000, 100000, 1000000};

        // Test dla funkcji f(x) = 3/x
        System.out.println("\nFunkcja 1: f(x) = 3/x w przedziale [1, e]");
        System.out.println("Dokładna wartość całki: " + exact1);
        System.out.println("Wartość maksymalna funkcji w przedziale: " + f1.max(a1, b1));
        System.out.println("\nLiczba punktów\tWynik\t\tBłąd względny");
        System.out.println("--------------------------------------------");

        for(int n: points) {
            double result = calka.calculate(a1, b1, f1, n);
            double error = Calka.relativeError(result, exact1);
            System.out.printf("%d\t\t%.4f\t%.4f%%%n", n, result, error);
        }

        System.out.println("--------------------------------------------");
        // Test dla funkcji f(x) = 3/x
        System.out.println("\nFunkcja 2: f(x) = 2x w przedziale [0, 4]");
        System.out.println("Dokładna wartość całki: " + exact2);
        System.out.println("Wartość maksymalna funkcji w przedziale: " + f2.max(a2, b2));
        System.out.println("\nLiczba punktów\tWynik\t\tBłąd względny");
        System.out.println("--------------------------------------------");

        for(int n: points) {
            double result = calka.calculate(a2, b2, f2, n);
            double error = Calka.relativeError(result, exact2);
            System.out.printf("%d\t\t%.4f\t%.4f%%%n", n, result, error);
        }

        // Wyświetlenie dodatkowych informacji o metodzie
        System.out.println("\nInformacje o metodzie Monte Carlo na 1 przykaldzie:");
        System.out.println("1. Losujemy punkty w prostokącie [1,e] × [-3,3]");
        System.out.println("2. Sprawdzamy, czy punkt leży pod wykresem funkcji");
        System.out.println("3. Całka = (pole prostokąta) * (punkty pod wykresem / wszystkie punkty)");
    }

    public static void testZad4() {
        System.out.println("ZADANIE 4 - Symulacja systemu pocztowego");
        System.out.println("--------------------------------------------");

        double mi = 1.0/3.0;  // średnia intensywność obsługi (średnio 3 minuty na klienta)
        int[] liczbyOkienek = {1, 2, 3, 4, 5};
        int liczbaInteresantow = 1000;

        for(int liczbaOkienek : liczbyOkienek) {
            System.out.printf("\nTest dla %d okienek:%n", liczbaOkienek);
            Poczta poczta = new Poczta(liczbaOkienek, mi);
            poczta.symuluj(liczbaInteresantow);
            poczta.wypiszStatystyki();
        }
    }
}