-- Add a row in the DIPENDENTE table
-- ______________

INSERT INTO DIPENDENTE (ID_DIPENDENTE, ID_MARCHIO, nome, cognome, telefono, e_mail)
VALUES (?, ?, ?, ?, ?, ?);

-- Add a row in the Configurazione table
INSERT INTO CONFIGURAZIONE (ID_Configurazione, Motore, alimentazione, cc, horse_power, ID_MODELLO)
VALUES (?, ?, ?, ?, ?, ?);

INSERT INTO AUTO (Numero_Telaio, Immatricolazione, data, targa, ID_Configurazione)
VALUES (?, ?, ?, ?, ?);

-- Add a row in the Modello table
INSERT INTO MODELLO (ID_MODELLO, Descrizione, Anno, ID_TIPOLOGIA, ID_MARCHIO)
VALUES (?, ?, ?, ?, ?);

-- Shows all the cars of a seller

SELECT A.Numero_Telaio, A.Immatricolazione, A.data, A.targa, M.Descrizione AS Modello, C.Motore, C.alimentazione
FROM AUTO A
JOIN CONFIGURAZIONE C ON A.ID_Configurazione = C.ID_Configurazione
JOIN MODELLO M ON C.ID_MODELLO = M.ID_MODELLO
JOIN MARCHIO MA ON M.ID_MARCHIO = MA.ID_MARCHIO
JOIN DIPENDENTE D ON D.ID_MARCHIO = MA.ID_MARCHIO
WHERE D.ID_DIPENDENTE = ?;

-- Shows all the meetings of a seller
SELECT a.ID_APPUNTAMENTO, a.data, a.ora, a.Tipologia, a.durata, a.Numero_Telaio, a.ID_CLIENTE, a.ID_DIPENDENTE
FROM APPUNTAMENTO a
WHERE a.ID_DIPENDENTE = ?;

-- Shows all the meetings of a seller of testdrive type

SELECT a.ID_APPUNTAMENTO, a.data, a.ora, a.Tipologia, a.durata, a.Numero_Telaio, a.ID_CLIENTE, a.ID_DIPENDENTE
FROM APPUNTAMENTO a 
WHERE a.TIPOLOGIA="testdrive" AND a.ID_DIPENDENTE = ?;

-- Shows all the meetings of a seller of meeting type 

SELECT a.ID_APPUNTAMENTO, a.data, a.ora, a.Tipologia, a.durata, a.Numero_Telaio, a.ID_CLIENTE, a.ID_DIPENDENTE
FROM APPUNTAMENTO a 
WHERE a.TIPOLOGIA="meeting" AND a.ID_DIPENDENTE = ?;

-- Add a row on the sconto table, it add a sconto on a Marchio
INSERT INTO SCONTO (Percentuale, data_inizio, data_fine, Numero_Telaio, ID_DIPENDENTE) 
VALUES (?,?,?,?,?);

