package it.unibo.gestione_concessionario.commons.dto;

import java.util.Optional;

public class Contratto {
    private int idContratto;
    private double prezzo;
    private String tipologia;
    private Optional<String> nomeBanca;
    private Optional<String> codiceFinanziamento;
    private Optional<String> intestatario;
    private Optional<String> metodoDiPagamento;

    // Costruttore completo
    public Contratto(int idContratto, double prezzo, String tipologia, Optional<String> nomeBanca,
            Optional<String> codiceFinanziamento, Optional<String> intestatario,
            Optional<String> metodoDiPagamento) {
        this.idContratto = idContratto;
        this.prezzo = prezzo;
        this.tipologia = tipologia;
        this.nomeBanca = nomeBanca != null ? nomeBanca : Optional.empty();
        this.codiceFinanziamento = codiceFinanziamento != null ? codiceFinanziamento : Optional.empty();
        this.intestatario = intestatario != null ? intestatario : Optional.empty();
        this.metodoDiPagamento = metodoDiPagamento != null ? metodoDiPagamento : Optional.empty();
    }

    // Costruttore parziale (solo campi obbligatori)
    public Contratto(int idContratto, double prezzo, String tipologia) {
        this(idContratto, prezzo, tipologia, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public Contratto(double prezzo, String tipologia, Optional<String> nomeBanca,
            Optional<String> codiceFinanziamento, Optional<String> intestatario,
            Optional<String> metodoDiPagamento) {
        this.prezzo = prezzo;
        this.tipologia = tipologia;
        this.nomeBanca = nomeBanca != null ? nomeBanca : Optional.empty();
        this.codiceFinanziamento = codiceFinanziamento != null ? codiceFinanziamento : Optional.empty();
        this.intestatario = intestatario != null ? intestatario : Optional.empty();
        this.metodoDiPagamento = metodoDiPagamento != null ? metodoDiPagamento : Optional.empty();
    }

    // Getter e Setter
    public int getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(int idContratto) {
        this.idContratto = idContratto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Optional<String> getNomeBanca() {
        return nomeBanca;
    }

    public void setNomeBanca(Optional<String> nomeBanca) {
        this.nomeBanca = nomeBanca;
    }

    public Optional<String> getCodiceFinanziamento() {
        return codiceFinanziamento;
    }

    public void setCodiceFinanziamento(Optional<String> codiceFinanziamento) {
        this.codiceFinanziamento = codiceFinanziamento;
    }

    public Optional<String> getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(Optional<String> intestatario) {
        this.intestatario = intestatario;
    }

    public Optional<String> getMetodoDiPagamento() {
        return metodoDiPagamento;
    }

    public void setMetodoDiPagamento(Optional<String> metodoDiPagamento) {
        this.metodoDiPagamento = metodoDiPagamento;
    }

    @Override
    public String toString() {
        return "Contratto{" +
                "idContratto=" + idContratto +
                ", prezzo=" + prezzo +
                ", tipologia='" + tipologia + '\'' +
                ", nomeBanca=" + nomeBanca +
                ", codiceFinanziamento=" + codiceFinanziamento +
                ", intestatario=" + intestatario +
                ", metodoDiPagamento=" + metodoDiPagamento +
                '}';
    }
}