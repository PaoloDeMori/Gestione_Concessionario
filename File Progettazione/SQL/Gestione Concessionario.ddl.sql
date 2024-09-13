-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Thu Sep 12 18:35:46 2024 
-- * LUN file: C:\Users\paolo\Desktop\Concessionario\File Progettazione\SQL\Schemi\Gestione ConcessionarioLogico Relazionale.lun 
-- * Schema: Gestione Concessionario/SQL1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database Gestione_Concessionario_prova;


-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table APPUNTAMENTO (
     ID_APPUNTAMENTO INT(7) AUTO_INCREMENT,
     data date not null,
     ora TIME not null,
     Tipologia varchar(15) not null,
     durata TIME,
     Numero_Telaio char(17) not null,
     ID_CLIENTE INT(7) not null,
     ID_DIPENDENTE INT(7) not null,
     constraint ID_APPUNTAMENTO_ID primary key (ID_APPUNTAMENTO));

create table AUTO (
     Numero_Telaio char(17) not null,
     Prezzo INT(7) not null,
     Immatricolazione BOOLEAN,
     data DATE,
     targa varchar(10),
     ID_Configurazione INT(7) not null,
     constraint ID_AUTO_ID primary key (Numero_Telaio));

create table CLIENTE (
     ID_CLIENTE INT(7) AUTO_INCREMENT,
     nome varchar(25) not null,
     cognome varchar(25) not null,
     telefono varchar(20) not null,
     e_mail varchar(50) not null,
     password varchar(25) not null,
     constraint ID_CLIENTE_ID primary key (ID_CLIENTE));

create table CONFIGURAZIONE (
     ID_Configurazione INT(7) AUTO_INCREMENT,
     Motore varchar(25) not null,
     alimentazione varchar(25) not null,
     cc INT(4) not null,
     horse_power INT(4) not null,
     ID_MODELLO INT(7) not null,
     constraint ID_CONFIGURAZIONE_ID primary key (ID_Configurazione));

create table CONTRATTO (
     prezzo INT(7) not null,
     ID_Contratto INT(7) AUTO_INCREMENT,
     Tipologia varchar(25) not null,
     Nome_banca varchar(25),
     codice_finanziamento varchar(25),
     Intestatario varchar(25),
     metodo_di_pagamento varchar(25),
     constraint ID_CONTRATTO_ID primary key (ID_Contratto));

create table DIPENDENTE (
     ID_DIPENDENTE INT(7) AUTO_INCREMENT,
     ID_MARCHIO INT(7) not null,
     nome varchar(25) not null,
     cognome varchar(25) not null,
     telefono varchar(20) not null,
     responsabile boolean not null,
     e_mail varchar(50) not null,
     password varchar(25) not null,
     constraint ID_DIPENDENTE_ID primary key (ID_DIPENDENTE),
     constraint SID_DIPEN_MARCH_ID unique (ID_MARCHIO));

create table GARANZIA (
     scadenza date not null,
     copertura varchar(100) not null,
     ID_Garanzia INT(7) AUTO_INCREMENT,
     Numero_Telaio char(17) not null,
     constraint ID_GARANZIA_ID primary key (ID_Garanzia),
     constraint SID_GARAN_AUTO_ID unique (Numero_Telaio));

create table MARCHIO (
     ID_MARCHIO INT(7) AUTO_INCREMENT,
     Nome varchar(25) not null,
     constraint ID_MARCHIO_ID primary key (ID_MARCHIO));

create table MODELLO (
     ID_MODELLO INT(7) AUTO_INCREMENT,
     Descrizione varchar(100) not null,
     Anno INT(4) not null,
     ID_TIPOLOGIA INT(7) not null,
     ID_MARCHIO INT(7) not null,
     constraint ID_MODELLO_ID primary key (ID_MODELLO));

create table OFFERTA (
     ID_OFFERTA INT(7) AUTO_INCREMENT,
     Percentuale INT(3) not null,
     data_inizio date not null,
     data_fine date not null,
     ID_MARCHIO INT(7) not null,
     ID_DIPENDENTE INT(7) not null,
     constraint ID_OFFERTA_ID primary key (ID_OFFERTA));

create table OPTIONAL (
     ID_Optional INT(7) AUTO_INCREMENT,
     descrizione varchar(100) not null,
     prezzo INT(5) not null,
     constraint ID_OPTIONAL_ID primary key (ID_Optional));

create table Personalizzazione (
     Numero_Telaio char(17) not null,
     ID_Optional INT(7) not null,
     constraint ID_Personalizzazione_ID primary key (Numero_Telaio, ID_Optional));

create table SCONTO (
     ID_SCONTO INT(7) AUTO_INCREMENT,
     Percentuale INT(3) not null,
     data_inizio date not null,
     data_fine date not null,
     Numero_Telaio char(17) not null,
     ID_DIPENDENTE INT(7) not null,
     constraint ID_SCONTO_ID primary key (ID_SCONTO));

create table Supporto (
     ID_MODELLO INT(7) not null,
     ID_Optional INT(7) not null,
     constraint ID_Supporto_ID primary key (ID_MODELLO, ID_Optional));

create table TIPOLOGIA (
     ID_TIPOLOGIA INT(7) AUTO_INCREMENT,
     nome varchar(25) not null,
     caratteristiche varchar(100) not null,
     constraint ID_TIPOLOGIA_ID primary key (ID_TIPOLOGIA));

create table VENDITA (
     ID_Vendita INT(7) AUTO_INCREMENT,
     Numero_Telaio char(17) not null,
     ID_Contratto INT(7) not null,
     data date not null,
     ora TIME not null,
     ID_DIPENDENTE INT(7) not null,
     ID_CLIENTE INT(7) not null,
     constraint ID_VENDITA_ID primary key (ID_Vendita),
     constraint SID_VENDI_AUTO_ID unique (Numero_Telaio),
     constraint SID_VENDI_CONTR_ID unique (ID_Contratto));


-- Constraints Section
-- ___________________ 

alter table APPUNTAMENTO add constraint REF_APPUN_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO (Numero_Telaio)
     ON DELETE CASCADE; -- removes the meetings if the car doesn't exist

alter table APPUNTAMENTO add constraint REF_APPUN_CLIEN_FK
     foreign key (ID_CLIENTE)
     references CLIENTE(ID_CLIENTE)
     ON DELETE CASCADE; -- removes the meetings if the client is removed

alter table APPUNTAMENTO add constraint REF_APPUN_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE(ID_DIPENDENTE)
     ON DELETE CASCADE; -- removes the meeting if the seller doesn't exist anymore 

alter table AUTO add constraint REF_AUTO_CONFI_FK
     foreign key (ID_Configurazione)
     references CONFIGURAZIONE(ID_Configurazione)
     ON DELETE RESTRICT; -- it's impossible to remove a row from configurazione if it is used by a car

alter table CONFIGURAZIONE add constraint REF_CONFI_MODEL_FK
     foreign key (ID_MODELLO)
     references MODELLO(ID_MODELLO)
     ON DELETE RESTRICT; -- it's impossible to remove a row from modello table if it used by a row in configurazione table

alter table DIPENDENTE add constraint SID_DIPEN_MARCH_FK
     foreign key (ID_MARCHIO)
     references MARCHIO(ID_MARCHIO);

alter table GARANZIA add constraint SID_GARAN_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO(Numero_Telaio)
     ON DELETE CASCADE; -- removes the GARANZIA row if the row in AUTO table is removed

alter table MODELLO add constraint REF_MODEL_TIPOL_FK
     foreign key (ID_TIPOLOGIA)
     references TIPOLOGIA(ID_TIPOLOGIA)
     ON DELETE RESTRICT; -- It's impossible to remove a row in TIPOLOGIA if it is used by a row in MODELLO

alter table MODELLO add constraint EQU_MODEL_MARCH_FK
     foreign key (ID_MARCHIO)
     references MARCHIO(ID_MARCHIO)
     ON DELETE RESTRICT; -- it's impossible to remove a row in MARCHIO if it is used by a row in MODELLO

alter table OFFERTA add constraint REF_OFFER_MARCH_FK
     foreign key (ID_MARCHIO)
     references MARCHIO(ID_MARCHIO)
     ON DELETE CASCADE; -- removes the row in offerta if the related row in MARCHIO is removed

alter table OFFERTA add constraint REF_OFFER_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE(ID_DIPENDENTE)
     ON DELETE CASCADE; -- removes the offerta if the dipendente is deleted

alter table Personalizzazione add constraint EQU_Perso_OPTIO_FK
     foreign key (ID_Optional)
     references OPTIONAL(ID_Optional);

alter table Personalizzazione add constraint REF_Perso_AUTO
     foreign key (Numero_Telaio)
     references AUTO(Numero_Telaio);

alter table SCONTO add constraint REF_SCONT_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO(Numero_Telaio)
      ON DELETE CASCADE;

alter table SCONTO add constraint REF_SCONT_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE(ID_DIPENDENTE)
      ON DELETE CASCADE;

alter table Supporto add constraint REF_Suppo_OPTIO_FK
     foreign key (ID_Optional)
     references OPTIONAL(ID_Optional);

alter table Supporto add constraint REF_Suppo_MODEL
     foreign key (ID_MODELLO)
     references MODELLO(ID_MODELLO);

alter table VENDITA add constraint REF_VENDI_DIPEN_FK
     foreign key (ID_DIPENDENTE)
     references DIPENDENTE(ID_DIPENDENTE);

alter table VENDITA add constraint SID_VENDI_AUTO_FK
     foreign key (Numero_Telaio)
     references AUTO(Numero_Telaio);

alter table VENDITA add constraint REF_VENDI_CLIEN_FK
     foreign key (ID_CLIENTE)
     references CLIENTE(ID_CLIENTE);

alter table VENDITA add constraint SID_VENDI_CONTR_FK
     foreign key (ID_Contratto)
     references CONTRATTO(ID_Contratto);


-- Triggers
-- Trigger to ensure correct associations between MARCHIO and MODELLO tables
-- This trigger is activated before inserting a new row in the MARCHIO table.
-- It checks if at least one row in the MODELLO table is associated with the MARCHIO being inserted.

DELIMITER $$

CREATE TRIGGER check_modello_dipendente_marchio
BEFORE INSERT ON MARCHIO
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM MODELLO WHERE MODELLO.ID_MARCHIO = NEW.ID_MARCHIO) THEN
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

-- __________________________________________________________________________

DELIMITER $$

CREATE TRIGGER check_employee_appointment
BEFORE INSERT ON APPUNTAMENTO
FOR EACH ROW
BEGIN
    -- Check if the tipologia is valid
    IF NEW.Tipologia NOT IN ('Test-Drive', 'Consulenza') THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid appointment type. Only "Test-Drive" and "Consulenza" are allowed.';
    END IF;

    -- Check if the employee has another appointment in the same time range
    IF EXISTS (
        SELECT 1 
        FROM APPUNTAMENTO 
        WHERE ID_DIPENDENTE = NEW.ID_DIPENDENTE 
          AND data = NEW.data 
          AND (NEW.ora BETWEEN ora AND ADDTIME(ora, durata)
          OR ADDTIME(NEW.ora, NEW.durata) BETWEEN ora AND ADDTIME(ora, durata))
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The employee already has an appointment scheduled during this time.';
    END IF;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER check_responsabile_insert
BEFORE INSERT ON DIPENDENTE
FOR EACH ROW
BEGIN
    DECLARE is_responsabile BOOLEAN;

    -- Check if the current user inserting the new dipendente is a responsabile
    SELECT responsabile INTO is_responsabile 
    FROM DIPENDENTE
    WHERE ID_DIPENDENTE = NEW.ID_DIPENDENTE;

    IF is_responsabile = FALSE THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Only a responsible employee can add a new employee.';
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

create index REF_MODEL_TIPOL_IND
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

create unique index SID_VENDI_CONTR_IND
     on VENDITA (ID_Contratto);

