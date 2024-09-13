package it.unibo.gestione_concessionario.commons.dto;

public record Configurazione(
    int idConfigurazione,
    String modello,
    String  motore,
    String alimentazione,
    int cc,
    int horsePower
) {

}
