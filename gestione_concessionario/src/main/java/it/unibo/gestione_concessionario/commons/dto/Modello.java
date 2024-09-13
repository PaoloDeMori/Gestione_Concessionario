package it.unibo.gestione_concessionario.commons.dto;

public record Modello(
    int idModello,
    String nome,
    String descrizione,
    double prezzo,
    Tipologia tipologia,
    Marchio marchio
) {

}
