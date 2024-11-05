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

    @Override
    public String toString() {
        return "Giorno " + data.toString() + " Ora " + ora.toString() + " Di tipo: "+ tipologia + " Durata: "+ durata.toString()+ " Per l'auto: " + numero_telaio;
    }
    

}
