-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Tue Sep 10 16:02:16 2024 
-- * LUN file: C:\Users\paolo\Desktop\Concessionario\File Progettazione\Schemi\Gestione Concessionario Logico Relazionale.lun 
-- * Schema: Gestione Concessionario/SQL4 
-- ********************************************* 


-- Database Section
-- ________________ 

create database Gestione_Concessionario;


-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table APPUNTAMENTO (
     ID_APPUNTAMENTO numeric(7) not null,
     data date not null,
     ora date not null,
     Tipologia varchar(50) not null,
     durata numeric(3),
     Numero_Telaio char(17) not null,
     ID_CLIENTE numeric(7) not null,
     ID_DIPENDENTE numeric(7) not null,
     constraint ID_APPUNTAMENTO_ID primary key (ID_APPUNTAMENTO));

create table AUTO (
     Numero_Telaio char(17) not null,
     Immatricolazione char,
     data date,
     targa char(7),
     ID_Configurazione numeric(7) not null,
     constraint ID_AUTO_ID primary key (Numero_Telaio));

create table CLIENTE (
     ID_CLIENTE numeric(7) not null,
     nome varchar(50) not null,
     cognome varchar(50) not null,
     telefono numeric(20) not null,
     e_mail varchar(50) not null,
     constraint ID_CLIENTE_ID primary key (ID_CLIENTE));

create table CONFIGURAZIONE (
     ID_Configurazione numeric(7) not null,
     Motore varchar(50) not null,
     alimentazione varchar(50) not null,
     cc numeric(4) not null,
     horse_power numeric(4) not null,
     ID_MODELLO numeric(7) not null,
     constraint ID_CONFIGURAZIONE_ID primary key (ID_Configurazione));

create table CONTRATTO (
     prezzo numeric(7,2) not null,
     ID_Contratto numeric(7) not null,
     ID_Vendita numeric(7) not null,
     Tipologia varchar(50) not null,
     Nome_banca varchar(50),
     codice_finanziamento numeric(7),
     Intestatario varchar(50),
     metodo_di_pagamento varchar(50),
     constraint ID_CONTRATTO_ID primary key (ID_Contratto),
     constraint SID_CONTR_VENDI_ID unique (ID_Vendita));

create table DIPENDENTE (
     ID_DIPENDENTE numeric(7) not null,
     ID_MARCHIO numeric(7) not null,
     nome varchar(50) not null,
     cognome varchar(50) not null,
     telefono numeric(20) not null,
     e_mail varchar(50) not null,
     constraint ID_DIPENDENTE_ID primary key (ID_DIPENDENTE),
     constraint SID_DIPEN_MARCH_ID unique (ID_MARCHIO));

create table GARANZIA (
     scadenza date not null,
     copertura numeric(4) not null,
     ID_Garanzia numeric(7) not null,
     Numero_Telaio char(17) not null,
     constraint ID_GARANZIA_ID primary key (ID_Garanzia),
     constraint SID_GARAN_AUTO_ID unique (Numero_Telaio));

create table MARCHIO (
     ID_MARCHIO numeric(7) not null,
     Nome varchar(50) not null,
     constraint ID_MARCHIO_ID primary key (ID_MARCHIO));

create table MODELLO (
     ID_MODELLO numeric(7) not null,
     Descrizione varchar(150) not null,
     Anno numeric(4) not null,
     ID_TIPOLOGIA numeric(7) not null,
     ID_MARCHIO numeric(7) not null,
     constraint ID_MODELLO_ID primary key (ID_MODELLO));

create table OFFERTA (
     ID_OFFERTA numeric(7) not null,
     Percentuale numeric(3) not null,
     data_inizio date not null,
     data_fine date not null,
     ID_MARCHIO numeric(7) not null,
     ID_DIPENDENTE numeric(7) not null,
     constraint ID_OFFERTA_ID primary key (ID_OFFERTA));

create table OPTIONAL (
     ID_Optional numeric(7) not null,
     descrizione varchar(50) not null,
     prezzo numeric(5,2) not null,
     constraint ID_OPTIONAL_ID primary key (ID_Optional));

create table Personalizzazione (
     Numero_Telaio char(17) not null,
     ID_Optional numeric(7) not null,
     constraint ID_Personalizzazione_ID primary key (Numero_Telaio, ID_Optional));

create table SCONTO (
     ID_SCONTO numeric(7) not null,
     Percentuale numeric(3) not null,
     data_inizio date not null,
     data_fine date not null,
     Numero_Telaio char(17) not null,
     ID_DIPENDENTE numeric(7) not null,
     constraint ID_SCONTO_ID primary key (ID_SCONTO));

create table Supporto (
     ID_MODELLO numeric(7) not null,
     ID_Optional numeric(7) not null,
     constraint ID_Supporto_ID primary key (ID_MODELLO, ID_Optional));

create table TIPOLOGIA (
     ID_TIPOLOGIA numeric(7) not null,
     nome varchar(50) not null,
     caratteristiche varchar(100) not null,
     constraint ID_TIPOLOGIA_ID primary key (ID_TIPOLOGIA));

create table VENDITA (
     ID_Vendita numeric(7) not null,
     Numero_Telaio char(17) not null,
     data date not null,
     ora date not null,
     ID_DIPENDENTE numeric(7) not null,
     ID_CLIENTE numeric(7) not null,
     constraint ID_VENDITA_ID primary key (ID_Vendita),
     constraint SID_VENDI_AUTO_ID unique (Numero_Telaio));


-- Constraints Section
-- ___________________

alter table APPUNTAMENTO add constraint REF_APPUN_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO (Numero_Telaio);

alter table APPUNTAMENTO add constraint REF_APPUN_CLIEN_FK
     foreign key (ID_CLIENTE)
     references CLIENTE (ID_CLIENTE);

alter table APPUNTAMENTO add constraint REF_APPUN_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE (ID_DIPENDENTE);

alter table AUTO add constraint REF_AUTO_CONFI_FK
     foreign key (ID_Configurazione)
     references CONFIGURAZIONE (ID_Configurazione);

alter table CONFIGURAZIONE add constraint REF_CONFI_MODEL_FK
     foreign key (ID_MODELLO)
     references MODELLO (ID_MODELLO);

alter table CONTRATTO add constraint SID_CONTR_VENDI_FK
     foreign key (ID_Vendita)
     references VENDITA (ID_Vendita);

alter table DIPENDENTE add constraint SID_DIPEN_MARCH_FK
     foreign key (ID_MARCHIO)
     references MARCHIO (ID_MARCHIO);

alter table GARANZIA add constraint SID_GARAN_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO (Numero_Telaio);

alter table MODELLO add constraint EQU_MODEL_TIPOL_FK
     foreign key (ID_TIPOLOGIA)
     references TIPOLOGIA (ID_TIPOLOGIA);

alter table MODELLO add constraint EQU_MODEL_MARCH_FK
     foreign key (ID_MARCHIO)
     references MARCHIO (ID_MARCHIO);

alter table OFFERTA add constraint REF_OFFER_MARCH_FK
     foreign key (ID_MARCHIO)
     references MARCHIO (ID_MARCHIO);

alter table OFFERTA add constraint REF_OFFER_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE (ID_DIPENDENTE);


alter table Personalizzazione add constraint EQU_Perso_OPTIO_FK
     foreign key (ID_Optional)
     references OPTIONAL (ID_Optional);

alter table Personalizzazione add constraint REF_Perso_AUTO
     foreign key (Numero_Telaio)
     references AUTO (Numero_Telaio);

alter table SCONTO add constraint REF_SCONT_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO (Numero_Telaio);

alter table SCONTO add constraint REF_SCONT_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE (ID_DIPENDENTE);

alter table Supporto add constraint REF_Suppo_OPTIO_FK
     foreign key (ID_Optional)
     references OPTIONAL (ID_Optional);

alter table Supporto add constraint REF_Suppo_MODEL
     foreign key (ID_MODELLO)
     references MODELLO (ID_MODELLO);

alter table VENDITA add constraint REF_VENDI_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE (ID_DIPENDENTE);

alter table VENDITA add constraint SID_VENDI_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO (Numero_Telaio);

alter table VENDITA add constraint REF_VENDI_CLIEN_FK
     foreign key (ID_CLIENTE)
     references CLIENTE (ID_CLIENTE);


-- Index Section
-- _____________

create unique index ID_APPUNTAMENTO_IND
     on APPUNTAMENTO (ID_APPUNTAMENTO);

create index REF_APPUN_AUTO_IND
     on APPUNTAMENTO (Numero_Telaio);

create index REF_APPUN_CLIEN_IND
     on APPUNTAMENTO (ID_CLIENTE);

create index REF_APPUN_DIPEN_IND
     on APPUNTAMENTO (ID_DIPENDENTE);

create unique index ID_AUTO_IND
     on AUTO (Numero_Telaio);

create index REF_AUTO_CONFI_IND
     on AUTO (ID_Configurazione);

create unique index ID_CLIENTE_IND
     on CLIENTE (ID_CLIENTE);

create unique index ID_CONFIGURAZIONE_IND
     on CONFIGURAZIONE (ID_Configurazione);

create index REF_CONFI_MODEL_IND
     on CONFIGURAZIONE (ID_MODELLO);

create unique index ID_CONTRATTO_IND
     on CONTRATTO (ID_Contratto);

create unique index SID_CONTR_VENDI_IND
     on CONTRATTO (ID_Vendita);

create unique index ID_DIPENDENTE_IND
     on DIPENDENTE (ID_DIPENDENTE);

create unique index SID_DIPEN_MARCH_IND
     on DIPENDENTE (ID_MARCHIO);

create unique index ID_GARANZIA_IND
     on GARANZIA (ID_Garanzia);

create unique index SID_GARAN_AUTO_IND
     on GARANZIA (Numero_Telaio);

create unique index ID_MARCHIO_IND
     on MARCHIO (ID_MARCHIO);

create unique index ID_MODELLO_IND
     on MODELLO (ID_MODELLO);

create index EQU_MODEL_TIPOL_IND
     on MODELLO (ID_TIPOLOGIA);

create index EQU_MODEL_MARCH_IND
     on MODELLO (ID_MARCHIO);

create unique index ID_OFFERTA_IND
     on OFFERTA (ID_OFFERTA);

create index REF_OFFER_MARCH_IND
     on OFFERTA (ID_MARCHIO);

create index REF_OFFER_DIPEN_IND
     on OFFERTA (ID_DIPENDENTE);

create unique index ID_OPTIONAL_IND
     on OPTIONAL (ID_Optional);

create unique index ID_Personalizzazione_IND
     on Personalizzazione (Numero_Telaio, ID_Optional);

create index EQU_Perso_OPTIO_IND
     on Personalizzazione (ID_Optional);

create unique index ID_SCONTO_IND
     on SCONTO (ID_SCONTO);

create index REF_SCONT_AUTO_IND
     on SCONTO (Numero_Telaio);

create index REF_SCONT_DIPEN_IND
     on SCONTO (ID_DIPENDENTE);

create unique index ID_Supporto_IND
     on Supporto (ID_MODELLO, ID_Optional);

create index REF_Suppo_OPTIO_IND
     on Supporto (ID_Optional);

create unique index ID_TIPOLOGIA_IND
     on TIPOLOGIA (ID_TIPOLOGIA);

create unique index ID_VENDITA_IND
     on VENDITA (ID_Vendita);

create index REF_VENDI_DIPEN_IND
     on VENDITA (ID_DIPENDENTE);

create unique index SID_VENDI_AUTO_IND
     on VENDITA (Numero_Telaio);

create index REF_VENDI_CLIEN_IND
     on VENDITA (ID_CLIENTE);