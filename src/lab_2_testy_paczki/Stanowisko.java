package lab_2_testy_paczki;

public class Stanowisko {

    // Parametry stanowiska
    private boolean zajete;
    private double czasKoncaObslugi;
    private Interesant aktywnyInteresant; // Interesant, ktory jest obslugiwany

    public Stanowisko() {
        this.zajete = false;
        this.czasKoncaObslugi = 0.0;
    }

    // Getters
    public boolean isZajete() {
        return zajete;
    }

    public Interesant getAktywnyInteresant() {
        return aktywnyInteresant;
    }

    public double getCzasKoncaObslugi() {
        return czasKoncaObslugi;
    }

    // Rozpoczecie obslugi interesanta
    public void rozpocznijObsluge(Interesant interesant, double czasObslugi) {
        this.zajete = true;
        this.aktywnyInteresant = interesant;
        this.czasKoncaObslugi = czasObslugi;
    }

    public void zakonczObsluge() {
        System.out.println("Obsługa zakończona dla interesanta.");
        this.zajete = false;
        this.aktywnyInteresant = null;
    }

}
