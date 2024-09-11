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


CREATE TABLE APPUNTAMENTO (
    ID_APPUNTAMENTO INT(7) NOT NULL,
    data DATE NOT NULL,
    ora TIME NOT NULL,
    Tipologia VARCHAR(50) NOT NULL,
    durata INT(3),
    Numero_Telaio CHAR(17) NOT NULL,
    ID_CLIENTE INT(7) NOT NULL,
    ID_DIPENDENTE INT(7) NOT NULL,
    CONSTRAINT ID_APPUNTAMENTO_ID PRIMARY KEY (ID_APPUNTAMENTO)
);

CREATE TABLE AUTO (
    Numero_Telaio CHAR(17) NOT NULL,
    Immatricolazione CHAR,
    data DATE,
    targa CHAR(7),
    ID_Configurazione INT(7) NOT NULL,
    CONSTRAINT ID_AUTO_ID PRIMARY KEY (Numero_Telaio)
);

CREATE TABLE CLIENTE (
    ID_CLIENTE INT(7) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    telefono INT(20) NOT NULL,
    e_mail VARCHAR(50) NOT NULL,
    CONSTRAINT ID_CLIENTE_ID PRIMARY KEY (ID_CLIENTE)
);

CREATE TABLE CONFIGURAZIONE (
    ID_Configurazione INT(7) NOT NULL,
    Motore VARCHAR(50) NOT NULL,
    alimentazione VARCHAR(50) NOT NULL,
    cc INT(4) NOT NULL,
    horse_power INT(4) NOT NULL,
    ID_MODELLO INT(7) NOT NULL,
    CONSTRAINT ID_CONFIGURAZIONE_ID PRIMARY KEY (ID_Configurazione)
);

CREATE TABLE CONTRATTO (
    prezzo DECIMAL(7 , 2 ) NOT NULL,
    ID_Contratto INT(7) NOT NULL,
    ID_Vendita INT(7) NOT NULL,
    Tipologia VARCHAR(50) NOT NULL,
    Nome_banca VARCHAR(50),
    codice_finanziamento INT(7),
    Intestatario VARCHAR(50),
    metodo_di_pagamento VARCHAR(50),
    CONSTRAINT ID_CONTRATTO_ID PRIMARY KEY (ID_Contratto),
    CONSTRAINT SID_CONTR_VENDI_ID UNIQUE (ID_Vendita)
);

CREATE TABLE DIPENDENTE (
    ID_DIPENDENTE INT(7) NOT NULL,
    ID_MARCHIO INT(7) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    telefono INT(20) NOT NULL,
    e_mail VARCHAR(50) NOT NULL,
    CONSTRAINT ID_DIPENDENTE_ID PRIMARY KEY (ID_DIPENDENTE),
    CONSTRAINT SID_DIPEN_MARCH_ID UNIQUE (ID_MARCHIO)
);

CREATE TABLE GARANZIA (
    scadenza DATE NOT NULL,
    copertura INT(4) NOT NULL,
    ID_Garanzia INT(7) NOT NULL,
    Numero_Telaio CHAR(17) NOT NULL,
    CONSTRAINT ID_GARANZIA_ID PRIMARY KEY (ID_Garanzia),
    CONSTRAINT SID_GARAN_AUTO_ID UNIQUE (Numero_Telaio)
);

CREATE TABLE MARCHIO (
    ID_MARCHIO INT(7) NOT NULL,
    Nome VARCHAR(50) NOT NULL,
    CONSTRAINT ID_MARCHIO_ID PRIMARY KEY (ID_MARCHIO)
);

CREATE TABLE MODELLO (
    ID_MODELLO INT(7) NOT NULL,
    Descrizione VARCHAR(150) NOT NULL,
    Anno INT(4) NOT NULL,
    ID_TIPOLOGIA INT(7) NOT NULL,
    ID_MARCHIO INT(7) NOT NULL,
    CONSTRAINT ID_MODELLO_ID PRIMARY KEY (ID_MODELLO)
);

CREATE TABLE OFFERTA (
    ID_OFFERTA INT(7) NOT NULL,
    Percentuale INT(3) NOT NULL,
    data_inizio DATE NOT NULL,
    data_fine DATE NOT NULL,
    ID_MARCHIO INT(7) NOT NULL,
    ID_DIPENDENTE INT(7) NOT NULL,
    CONSTRAINT ID_OFFERTA_ID PRIMARY KEY (ID_OFFERTA)
);

CREATE TABLE OPTIONAL (
    ID_Optional INT(7) NOT NULL,
    descrizione VARCHAR(50) NOT NULL,
    prezzo DECIMAL(5 , 2 ) NOT NULL,
    CONSTRAINT ID_OPTIONAL_ID PRIMARY KEY (ID_Optional)
);

CREATE TABLE Personalizzazione (
    Numero_Telaio CHAR(17) NOT NULL,
    ID_Optional INT(7) NOT NULL,
    CONSTRAINT ID_Personalizzazione_ID PRIMARY KEY (Numero_Telaio , ID_Optional)
);

CREATE TABLE SCONTO (
    ID_SCONTO INT(7) NOT NULL,
    Percentuale INT(3) NOT NULL,
    data_inizio DATE NOT NULL,
    data_fine DATE NOT NULL,
    Numero_Telaio CHAR(17) NOT NULL,
    ID_DIPENDENTE INT(7) NOT NULL,
    CONSTRAINT ID_SCONTO_ID PRIMARY KEY (ID_SCONTO)
);

CREATE TABLE Supporto (
    ID_MODELLO INT(7) NOT NULL,
    ID_Optional INT(7) NOT NULL,
    CONSTRAINT ID_Supporto_ID PRIMARY KEY (ID_MODELLO , ID_Optional)
);

CREATE TABLE TIPOLOGIA (
    ID_TIPOLOGIA INT(7) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    caratteristiche VARCHAR(100) NOT NULL,
    CONSTRAINT ID_TIPOLOGIA_ID PRIMARY KEY (ID_TIPOLOGIA)
);

CREATE TABLE VENDITA (
    ID_Vendita INT(7) NOT NULL,
    Numero_Telaio CHAR(17) NOT NULL,
    data DATE NOT NULL,
    ora TIME NOT NULL,
    ID_DIPENDENTE INT(7) NOT NULL,
    ID_CLIENTE INT(7) NOT NULL,
    CONSTRAINT ID_VENDITA_ID PRIMARY KEY (ID_Vendita),
    CONSTRAINT SID_VENDI_AUTO_ID UNIQUE (Numero_Telaio)
);


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
     
     
-- triggers
-- _________________________
-- Trigger to ensure correct associations between MARCHIO, MODELLO, and DIPENDENTE tables
-- This trigger is activated before inserting a new row in the MARCHIO table.
-- It checks if at least one row in the MODELLO or DIPENDENTE table is associated with the MARCHIO being inserted.

DELIMITER $$

CREATE TRIGGER check_modello_dipendente_marchio
BEFORE INSERT ON MARCHIO
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM MODELLO WHERE MODELLO.ID_MARCHIO = NEW.ID_MARCHIO)
    AND NOT EXISTS (SELECT 1 FROM DIPENDENTE WHERE DIPENDENTE.ID_MARCHIO = NEW.ID_MARCHIO) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Errore: Il marchio deve avere almeno un modello o un dipendente associato.';
    END IF;
END$$

DELIMITER ;

-- Trigger to ensure correct associations between OPTIONAL, and Personalizzazione tables
-- This trigger is activated before inserting a new row in the OPTIONAL table.
-- It checks if at least one row in the Personalizzazione table is associated with the OPTIONAL being inserted.

DELIMITER $$

CREATE TRIGGER check_optional_personalizzazione
BEFORE INSERT ON OPTIONAL
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Personalizzazione WHERE Personalizzazione.ID_Optional = NEW.ID_Optional) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Errore: optional deve essere associato a una personalizzazione.';
    END IF;
END$$

DELIMITER ;

-- Trigger to ensure correct associations between TIPOLOGIA, and MODELLO tables
-- This trigger is activated before inserting a new row in the TIPOLOGIA table.
-- It checks if at least one row in the MODELLO table is associated with the TIPOLOGIA being inserted.

DELIMITER $$

CREATE TRIGGER check_tipologia_modello
BEFORE INSERT ON TIPOLOGIA
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM MODELLO WHERE MODELLO.ID_TIPOLOGIA = NEW.ID_TIPOLOGIA) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Errore: La tipologia deve essere associata a un modello.';
    END IF;
END$$

DELIMITER ;

-- Trigger to ensure correct associations between VENDITA, and CONTRATTO tables
-- This trigger is activated before inserting a new row in the VENDITA table.
-- It checks if at least one row in the CONTRATTO table is associated with the VENDITA being inserted.


DELIMITER $$

CREATE TRIGGER check_vendita_contratto
BEFORE INSERT ON VENDITA
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM CONTRATTO WHERE CONTRATTO.ID_Vendita = NEW.ID_Vendita) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Errore: La vendita deve essere associata a un contratto.';
    END IF;
END$$

DELIMITER ;


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