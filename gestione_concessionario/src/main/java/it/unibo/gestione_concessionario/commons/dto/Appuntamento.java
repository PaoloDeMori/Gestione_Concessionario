package it.unibo.gestione_concessionario.commons.dto;

import java.time.LocalDate;
import java.time.LocalTime;
public record Appuntamento(
    LocalDate data,
    LocalTime ora,
    String tipologia,
    LocalTime durata,
    String numero_telaio,
    int  id_dipendente,
    int  id_cliente
) {

}
