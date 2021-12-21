package Kuba;

public class Obiekt {
    String kraj;
    String rok;
    String wartosc;

    public Obiekt(String kraj, String rok, String wartosc) {
        this.kraj = kraj;
        this.rok = rok;
        this.wartosc = wartosc;
    }

    public String getKraj() {
        return kraj;
    }

    public String getRok() {
        return rok;
    }

    public String getWartosc() {
        return wartosc;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public void setWartosc(String wartosc) {
        this.wartosc = wartosc;
    }
}
