package it.unibo.gestione_concessionario.commons.dto;
import java.time.LocalDate;
import java.time.LocalTime;
public record Vendita(
    int idVendita,
    LocalDate data,
    LocalTime ora,
    String tipologia,
    Contratto idContratto,
    Auto nuremo_telaio,
    Cliente cliente,
    Dipendente dipendente
) {

}
