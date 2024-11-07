package lab_1_final.Zad_3;

public class Funkcja1 implements IFunc {
    @Override
    public double func(double x) {
        return 3 / x; // Calka z 3/x od 1 do e = 3
    }

    @Override
    public double max(double a, double b) {
        return Math.max(func(a), func(b));
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

}
