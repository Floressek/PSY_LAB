package lab_2_samochody;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;

// Klasa reprezentująca pojazd
class Pojazd extends BasicSimObj {
    private static int ids = 0;
    private int id;
    private double predkosc;
    private double przebytaDroga;
    private int pozycja; // numer odcinka
    private double czasStartu;
    private double czasParkowania;
    private boolean kierunekPowrotny;

    public Pojazd(double predkosc) {
        this.id = ids++;
        this.predkosc = predkosc;
        this.przebytaDroga = 0;
        this.pozycja = 0;
        this.czasStartu = simTime();
        this.czasParkowania = 0;
        this.kierunekPowrotny = false;
    }

    // Gettery i settery
    public int getId() { return id; }
    public double getPredkosc() { return predkosc; }
    public void setPredkosc(double predkosc) { this.predkosc = predkosc; }
    public double getPrzebytaDroga() { return przebytaDroga; }
    public void setPrzebytaDroga(double droga) { this.przebytaDroga = droga; }
    public int getPozycja() { return pozycja; }
    public void setPozycja(int pozycja) { this.pozycja = pozycja; }
    public double getCzasStartu() { return czasStartu; }
    public boolean isKierunekPowrotny() { return kierunekPowrotny; }
    public void setKierunekPowrotny(boolean kierunekPowrotny) {
        this.kierunekPowrotny = kierunekPowrotny;
    }

    public double getCzasParkowania() {
        return czasParkowania;
    }

    public void setCzasParkowania(double czasParkowania) {
        this.czasParkowania = czasParkowania;
    }

//    // Metoda zwracająca całkowity czas przejazdu wliczając parkowanie
//    public double getCzasPrzejazdu() {
//        return simTime() - czasStartu + czasParkowania;
//    }
//
//    public void rozpocznijPowrot(double nowaPredkosc) {
//        this.predkosc = nowaPredkosc;
//        this.kierunekPowrotny = true;
//        // Nie resetujemy przebytej drogi - będziemy liczyć dalej
//    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {}

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
