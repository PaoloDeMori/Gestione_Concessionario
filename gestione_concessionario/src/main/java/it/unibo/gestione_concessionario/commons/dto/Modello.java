package it.unibo.gestione_concessionario.commons.dto;

public record Modello(
    int idModello,
    String descrizione,
    Integer anno,
    String tipologia,
    String marchio
) {

    @Override
    public String toString() {
        return this.descrizione;
    }

}
