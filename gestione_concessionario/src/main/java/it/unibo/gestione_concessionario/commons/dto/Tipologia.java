package it.unibo.gestione_concessionario.commons.dto;

public class Tipologia{
        int idTipologia;
        String nome;
        String caratteristiche; 

        public Tipologia(int idTipologia, String nome, String caratteristiche) {
                this.idTipologia = idTipologia;
                this.nome = nome;
                this.caratteristiche = caratteristiche;
        }

        public Tipologia(String nome, String caratteristiche) {
                this.nome = nome;
                this.caratteristiche = caratteristiche;
        }

        @Override
        public String toString() {
                return nome;
        }

        public int idTipologia() {
                return idTipologia;
        }

        public void setIdTipologia(int idTipologia) {
                this.idTipologia = idTipologia;
        }

        public String nome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String caratteristiche() {
                return caratteristiche;
        }

        public void setCaratteristiche(String caratteristiche) {
                this.caratteristiche = caratteristiche;
        }

}
