package it.unibo.gestione_concessionario.commons.dto;

public record Cliente(
    String nome,
    String cognome,
    String telefono,
    String eMail,
    String password
) {}
