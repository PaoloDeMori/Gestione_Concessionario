package it.unibo.gestione_concessionario.dto;

public record Modello(
    int idModello,
    String nome,
    String descrizione,
    double prezzo,
    Tipologia tipologia,
    Marchio marchio
) {

}
