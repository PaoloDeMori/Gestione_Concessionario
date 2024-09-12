package it.unibo.gestione_concessionario.dto;

public record Configurazione(
    int idConfigurazione,
    Modello modello,
    String  motore,
    String alimentazione,
    int cc,
    int horsePower
) {

}
