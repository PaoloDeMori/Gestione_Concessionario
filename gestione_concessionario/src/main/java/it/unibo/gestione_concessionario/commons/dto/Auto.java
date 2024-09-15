package it.unibo.gestione_concessionario.commons.dto;

import java.util.Optional;
import java.time.LocalDate;
public record Auto(
    String numero_telaio,
    Double prezzo,
    boolean immatricolazione,
    Optional<String> targa,
    Optional<LocalDate> data,
    String descrizioneModello,
    String motore,
    String alimentazione
) {

    @Override
    public String toString() {
        return numero_telaio;
    }

}
