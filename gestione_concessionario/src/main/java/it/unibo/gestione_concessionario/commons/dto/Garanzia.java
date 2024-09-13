package it.unibo.gestione_concessionario.commons.dto;

public record Garanzia(
    int idGaranzia,
    String scadenza,
    String copertura,
    Auto nuremo_telaio
) {

}
