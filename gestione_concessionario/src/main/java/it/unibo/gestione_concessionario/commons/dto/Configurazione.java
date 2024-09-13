package it.unibo.gestione_concessionario.commons.dto;

public record Configurazione(
    int idConfigurazione,
    String  motore,
    String alimentazione,
    int cc,
    int horsePower,
    int id_modello
) {

}
