package it.unibo.gestione_concessionario.commons.dto;

import java.util.Optional;
import java.time.LocalDate;
public class Auto{
    String numero_telaio;
    Double prezzo;
    boolean immatricolazione;
    Optional<String> targa;
    Optional<LocalDate> data;
    String descrizioneModello;
    String motore;
    String alimentazione;
    int idConfigurazione;


    public Auto(String numero_telaio, Double prezzo,boolean immatricolazione,Optional<String>targa,Optional<LocalDate> data,String descrizioneModello){
        this.setNumero_telaio(numero_telaio);
        this.setPrezzo(prezzo);
        this.setImmatricolazione(immatricolazione);
        this.setTarga(targa);
        this.setData(data);
        this.setDescrizioneModello(descrizioneModello);
    }

    public Auto(String numero_telaio, Double prezzo,boolean immatricolazione,String descrizioneModello){
        this.setNumero_telaio(numero_telaio);
        this.setPrezzo(prezzo);
        this.setImmatricolazione(immatricolazione);
        this.setTarga(targa);
        this.setData(data);
        this.setDescrizioneModello(descrizioneModello);
    }

    public Auto(String numero_telaio, Double prezzo,boolean immatricolazione,String descrizioneModello,Optional<String>targa,Optional<LocalDate> data){
        this.setNumero_telaio(numero_telaio);
        this.setPrezzo(prezzo);
        this.setImmatricolazione(immatricolazione);
        this.setTarga(targa);
        this.setData(data);
        this.setDescrizioneModello(descrizioneModello);
    }

    public Auto(String numero_telaio, Double prezzo,boolean immatricolazione,Optional<String>targa,Optional<LocalDate> data,String descrizioneModello,String motore,String alimentazione){
        this.setNumero_telaio(numero_telaio);
        this.setPrezzo(prezzo);
        this.setImmatricolazione(immatricolazione);
        this.setTarga(targa);
        this.setData(data);
        this.setDescrizioneModello(descrizioneModello);
        this.setMotore(motore);
        this.setAlimentazione(alimentazione);
    }

    @Override
    public String toString() {
        return numero_telaio;
    }

    public String getNumero_telaio() {
        return numero_telaio;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public boolean isImmatricolazione() {
        return immatricolazione;
    }

    public Optional<String> getTarga() {
        return targa;
    }

    public Optional<LocalDate> getData() {
        return data;
    }

    public String getDescrizioneModello() {
        return descrizioneModello;
    }

    public String getMotore() {
        return motore;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public void setNumero_telaio(String numero_telaio) {
        this.numero_telaio = numero_telaio;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public void setImmatricolazione(boolean immatricolazione) {
        this.immatricolazione = immatricolazione;
    }

    public void setTarga(Optional<String> targa) {
        this.targa = targa;
    }

    public void setData(Optional<LocalDate> data) {
        this.data = data;
    }

    public void setDescrizioneModello(String descrizioneModello) {
        this.descrizioneModello = descrizioneModello;
    }

    public void setMotore(String motore) {
        this.motore = motore;
    }

    public void setAlimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
    }

    public boolean getImmatricolazione(){
        return this.immatricolazione;
    }

    public int getIdConfigurazione() {
        return idConfigurazione;
    }

    public void setIdConfigurazione(int idConfigurazione) {
        this.idConfigurazione = idConfigurazione;
    }

    

}
