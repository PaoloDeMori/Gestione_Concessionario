package it.unibo.gestione_concessionario.commons.dto;

public class Modello {
    private int idModello;
    private String descrizione;
    private Integer anno;
    private String tipologia;
    private int id_tipologia;



    private String marchio;

    public Modello(int idModello, String descrizione, Integer anno, String tipologia, String marchio) {
        this.idModello = idModello;
        this.descrizione = descrizione;
        this.anno = anno;
        this.tipologia = tipologia;
        this.marchio = marchio;
    }

    public Modello(String descrizione, Integer anno, String tipologia, String marchio) {
        this.descrizione = descrizione;
        this.anno = anno;
        this.tipologia = tipologia;
        this.marchio = marchio;
    }

    public Modello(String descrizione, Integer anno, String tipologia, String marchio, int id_tipologia) {
        this.descrizione = descrizione;
        this.anno = anno;
        this.tipologia = tipologia;
        this.marchio = marchio;
        this.id_tipologia=id_tipologia;
    }

    public int idModello() {
        return idModello;
    }

    public String descrizione() {
        return descrizione;
    }

    public Integer anno() {
        return anno;
    }

    public String tipologia() {
        return tipologia;
    }

    public int id_tipologia() {
        return id_tipologia;
    }

    public String marchio() {
        return marchio;
    }

    public void setIdModello(int idModello) {
        this.idModello = idModello;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setMarchio(String marchio) {
        this.marchio = marchio;
    }

    @Override
    public String toString() {
        return this.descrizione;
    }
}
