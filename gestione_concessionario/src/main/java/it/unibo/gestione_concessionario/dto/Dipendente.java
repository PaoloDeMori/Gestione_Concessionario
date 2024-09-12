package it.unibo.gestione_concessionario.dto;

public record Dipendente(
    int idDipendente,
    String nome,
    String cognome,
    String telefono,
    String eMail,
    Marchio marchio
) {

}
