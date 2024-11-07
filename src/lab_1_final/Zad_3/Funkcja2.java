package lab_1_final.Zad_3;

public class Funkcja2 implements IFunc {
    @Override
    public double func(double x) {
        return 2 * x;  // f(x) = 2x
    }

//    @Override
//    public double max(double a, double b) {
//        // Przeszukujemy przedział [a,b] małymi krokami
//        double step = (b - a) / 1000;
//        double maxVal = Math.abs(func(a));
//
//        for(double x = a; x <= b; x += step) {
//            double absVal = Math.abs(func(x));
//            if(absVal > maxVal) {
//                maxVal = absVal;
//            }
//        }
//
//        return maxVal;
//    }

    @Override
    public double max(double a, double b) {
        return Math.max(func(a), func(b));
    }
}
