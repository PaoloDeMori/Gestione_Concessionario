package it.unibo.gestione_concessionario.commons.dto;

public record Configurazione(
    String  motore,
    String alimentazione,
    int cc,
    int horsePower,
    int id_modello
) {

}
