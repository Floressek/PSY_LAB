package lab_1;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

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

    // Zadanie 2
    public double rozklad1() {
        double u = nextDouble();

        if (u < 0.5) {
            // For u in [0, 0.5), corresponding to x in [-1, 1]
            return 2 * u - 1;
        } else {
            // For u in [0.5, 1), corresponding to x in [1, 2]
            return 4 * (u - 0.5);
        }
    }


    /**
     * Metoda testowa pokazująca działanie generatora
     */
    public static void main(String[] args) {
        // Tworzenie generatora z zadanymi parametrami
//        CustomRandomGenerator generator = new CustomRandomGenerator(97, 11, 100, 1);
        CustomRandomGenerator generator = new CustomRandomGenerator(16807, 0, Integer.MAX_VALUE, 1);

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
        Result result = getResult(numberOfTrials, generator, random, M);

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

        distributionTest(10000000, 100);  // twoje obecne testy
        visualizeDistribution(1000000, generator);  // nowa wizualizacja

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
    private static Result getResult(int numberOfTrials, CustomRandomGenerator generator, Random random, int M) {
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
        return new Result(customMean, randomMean, theoreticalMean);
    }

    /**
     * Klasa pomocnicza do przechowywania wyników testów
     */
    private record Result(double customMean, double randomMean, double theoreticalMean) {
    }

    public static void distributionTest(int iterations, int M) {
        // Tworzymy generator z tymi samymi parametrami co w main
        CustomRandomGenerator generator_dist = new CustomRandomGenerator(97, 11, M, 1);

        // Zmienne do obliczeń statystycznych
        double sum = 0;
        double sumSquared = 0;

        // Tablica do histogramu - podzielimy zakres [-1,2] na 10 przedziałów
        int[] histogram = new int[15];
        double minValue = -1;
        double maxValue = 2;
        double binWidth = (maxValue - minValue) / histogram.length;

        // Zbieranie danych
        for (int i = 0; i < iterations; i++) {
            double value = generator_dist.rozklad1();
            sum += value;
            sumSquared += value * value;

            // Aktualizacja histogramu
            int bin = (int) ((value - minValue) / binWidth);
            if (bin >= 0 && bin < histogram.length) {
                histogram[bin]++;
            }
        }

        // Obliczenia statystyczne
        double empiricalMean = sum / iterations;
        double theoreticalMean = 0.25; // wartość teoretyczna
        double empiricalVariance = (sumSquared / iterations) - (empiricalMean * empiricalMean);

        // Wyświetlanie wyników
        System.out.println("\nANALIZA ROZKŁADU");
        System.out.println("Liczba iteracji: " + iterations);
        System.out.println("----------------------------------------");
        System.out.printf("Średnia empiryczna: %.6f%n", empiricalMean);
        System.out.printf("Średnia teoretyczna: %.6f%n", theoreticalMean);
        System.out.printf("Różnica bezwzględna: %.6f%n", Math.abs(empiricalMean - theoreticalMean));
        System.out.printf("Wariancja empiryczna: %.6f%n", empiricalVariance);

        // Wyświetlanie histogramu
        System.out.println("\nHISTOGRAM");
        System.out.println("----------------------------------------");
        for (int i = 0; i < histogram.length; i++) {
            double rangeStart = minValue + (i * binWidth);
            double rangeEnd = rangeStart + binWidth;
            double percentage = (double) histogram[i] / iterations * 100;

            System.out.printf("[%5.2f,%5.2f): %6.2f%% %s%n",
                    rangeStart,
                    rangeEnd,
                    percentage,
                    "*".repeat((int) (percentage * 2)) // wizualizacja gwiazdkami
            );
        }
    }

    public static void visualizeDistribution(int iterations, CustomRandomGenerator generator) {
        // Tworzenie okna
        JFrame frame = new JFrame("Rozkład wartości");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Panel z własnym rysowaniem
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int padding = 50;

                // Zbieranie danych do histogramu
                int[] histogram = new int[10];
                double minValue = -1;
                double maxValue = 2;
                double binWidth = (maxValue - minValue) / histogram.length;

                // Generowanie danych - najpierw zbieramy dane
                for (int i = 0; i < iterations; i++) {
                    double value = generator.rozklad1();
                    int bin = (int) ((value - minValue) / binWidth);
                    if (bin >= 0 && bin < histogram.length) {
                        histogram[bin]++;
                    }
                }

                // Normalizacja histogramu - TERAZ normalizujemy zebrane dane
                double[] normalizedHistogram = new double[histogram.length];
                for (int i = 0; i < histogram.length; i++) {
                    normalizedHistogram[i] = histogram[i] / (double)(iterations * binWidth);
                }

                // Znajdź maksymalną wartość w znormalizowanym histogramie
                double maxCount = 0;
                for (double count : normalizedHistogram) {
                    maxCount = Math.max(maxCount, count);
                }

                // Rysowanie osi
                g2.setColor(Color.BLACK);
                g2.drawLine(padding, height - padding, width - padding, height - padding); // oś X
                g2.drawLine(padding, height - padding, padding, padding); // oś Y

                // Rysowanie histogramu
                double scaleX = (double) (width - 2 * padding) / histogram.length;
                double scaleY = (double) (height - 2 * padding) / 0.6; // ustaw stałą skalę dla maksimum 0.5

                // Rysowanie słupków histogramu
                g2.setColor(new Color(100, 149, 237, 128));
                for (int i = 0; i < histogram.length; i++) {
                    int barHeight = (int) (normalizedHistogram[i] * scaleY);
                    g2.fill(new Rectangle2D.Double(
                            padding + i * scaleX,
                            height - padding - barHeight,
                            scaleX - 1,
                            barHeight
                    ));
                }

                // Rysowanie teoretycznej funkcji gęstości
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(2.0f));

                // Przekształcenie wartości teoretycznych na skalę wykresu
                int mid = padding + (width - 2 * padding) / 3;
                int heightHalf = (int)(height - padding - (0.5 * scaleY)); // wysokość dla gęstości 1/2
                int heightQuarter = (int)(height - padding - (0.25 * scaleY)); // wysokość dla gęstości 1/4

                // Rysowanie poziomych linii dla gęstości 1/2 i 1/4
                g2.drawLine(padding, heightHalf, mid, heightHalf);
                g2.drawLine(mid, heightQuarter, width - padding, heightQuarter);

                // Opisywanie osi
                g2.setColor(Color.BLACK);
                g2.drawString("-1", padding, height - padding + 15);
                g2.drawString("0", mid, height - padding + 15);
                g2.drawString("2", width - padding, height - padding + 15);
                g2.drawString("1/2", padding - 30, heightHalf);
                g2.drawString("1/4", padding - 30, heightQuarter);
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }

}