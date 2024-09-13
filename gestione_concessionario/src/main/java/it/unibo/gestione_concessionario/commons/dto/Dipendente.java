package it.unibo.gestione_concessionario.commons.dto;

public record Dipendente(
    int idmarchio,
    String nome,
    String cognome,
    String telefono,
    String eMail
) {

}
