package it.unibo.gestione_concessionario.commons.dto;

import java.time.LocalDate;

public class Garanzia{
    int idGaranzia;
    LocalDate scadenza;
    String copertura;
    String numeroTelaio;
    
    public Garanzia(int idGaranzia, LocalDate scadenza, String copertura, String numeroTelaio) {
        this.idGaranzia = idGaranzia;
        this.scadenza = scadenza;
        this.copertura = copertura;
        this.numeroTelaio = numeroTelaio;
    }

    public Garanzia(LocalDate scadenza, String copertura, String numeroTelaio) {
        this.scadenza = scadenza;
        this.copertura = copertura;
        this.numeroTelaio = numeroTelaio;
    }
    public int idGaranzia() {
        return idGaranzia;
    }
    public void setIdGaranzia(int idGaranzia) {
        this.idGaranzia = idGaranzia;
    }
    public LocalDate scadenza() {
        return scadenza;
    }
    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }
    public String copertura() {
        return copertura;
    }
    public void setCopertura(String copertura) {
        this.copertura = copertura;
    }
    public String numeroTelaio() {
        return numeroTelaio;
    }
    public void setNumeroTelaio(String numeroTelaio) {
        this.numeroTelaio = numeroTelaio;
    }

}