package it.unibo.gestione_concessionario.commons.dto;

public class Configurazione {
    int id_Configurazione;
    String motore;
    String alimentazione;
    int cc;
    int horsePower;
    int id_modello;

    public Configurazione(int id_Configurazione, String motore, String alimentazione, int cc, int horsePower,
            int id_modello) {
        this.id_Configurazione = id_Configurazione;
        this.motore = motore;
        this.alimentazione = alimentazione;
        this.cc = cc;
        this.horsePower = horsePower;
        this.id_modello = id_modello;
    }

    public Configurazione(String motore, String alimentazione, int cc, int horsePower, int id_modello) {
        this.motore = motore;
        this.alimentazione = alimentazione;
        this.cc = cc;
        this.horsePower = horsePower;
        this.id_modello = id_modello;
    }

    @Override
    public String toString() {
        return motore + " " + alimentazione + " " + Integer.toString(cc) + "cc " + Integer.toString(horsePower) + "hp";
    }

    public String motore() {
        return motore;
    }

    public String alimentazione() {
        return alimentazione;
    }

    public int cc() {
        return cc;
    }

    public int horsePower() {
        return horsePower;
    }

    public int id_modello() {
        return id_modello;
    }

    public int id_Configurazione() {
        return id_Configurazione;
    }

}
