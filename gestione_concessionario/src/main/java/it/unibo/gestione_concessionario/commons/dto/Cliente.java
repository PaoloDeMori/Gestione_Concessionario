package it.unibo.gestione_concessionario.commons.dto;

public record Cliente(
    int codCliente,
    String nome,
    String cognome,
    String telefono,
    String eMail
) {}
