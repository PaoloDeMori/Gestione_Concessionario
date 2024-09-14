INSERT INTO CLIENTE (nome, cognome, telefono, e_mail, password)
VALUES
('Marco', 'Lupi', '3216549870', 'marco.lupi@example.com', 'clientpass1'),
('Giorgia', 'Grigi', '3216549871', 'giorgia.grigi@example.com', 'clientpass2'),
('Antonio', 'Verdi', '3216549872', 'antonio.verdi@example.com', 'clientpass3'),
('Serena', 'Blu', '3216549873', 'serena.blu@example.com', 'clientpass4'),
('Giovanni', 'Arancioni', '3216549874', 'giovanni.arancioni@example.com', 'clientpass5'),
('Lucia', 'Rosa', '3216549875', 'lucia.rosa@example.com', 'clientpass6'),
('Matteo', 'Neri', '3216549876', 'matteo.neri@example.com', 'clientpass7'),
('Chiara', 'Viola', '3216549877', 'chiara.viola@example.com', 'clientpass8'),
('Roberto', 'Gialli', '3216549878', 'roberto.gialli@example.com', 'clientpass9'),
('Simona', 'Marrone', '3216549879', 'simona.marrone@example.com', 'clientpass10');


BEGIN;
DROP TRIGGER check_modello_dipendente_marchio;
INSERT INTO MARCHIO (Nome) VALUES
('Ferrari'),
('Lamborghini'),
('Porsche'),
('Audi'),
('BMW'),
('Mercedes'),
('Toyota'),
('Honda'),
('Ford'),
('Chevrolet');

INSERT INTO MODELLO (Descrizione, Anno, ID_TIPOLOGIA, ID_MARCHIO) VALUES
('Lamborghini Huracan', 2024, 1, 2),
('Porsche 911', 2024, 2, 3),
('Audi A4', 2024, 2, 4),
('BMW M3', 2024, 3, 5),
('Mercedes-Benz C-Class', 2024, 3, 6),
('Toyota Corolla', 2024, 4, 7),
('Honda Civic', 2024, 4, 8),
('Ford Mustang', 2024, 5, 9),
('Chevrolet Camaro', 2024, 5, 10);

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

COMMIT;


INSERT INTO DIPENDENTE (ID_MARCHIO, nome, cognome, telefono, responsabile, e_mail, password)
VALUES
(1, 'Mario', 'Rossi', '1234567890', TRUE, 'mario.rossi@example.com', 'password123'),
(2, 'Luca', 'Bianchi', '1234567891', FALSE, 'luca.bianchi@example.com', 'password456'),
(3, 'Anna', 'Verdi', '1234567892', FALSE, 'anna.verdi@example.com', 'password789'),
(4, 'Giulia', 'Neri', '1234567893', FALSE, 'giulia.neri@example.com', 'password321'),
(5, 'Paolo', 'Gialli', '1234567894', FALSE, 'paolo.gialli@example.com', 'password654'),
(6, 'Francesco', 'Blu', '1234567895', FALSE, 'francesco.blu@example.com', 'password987'),
(7, 'Stefano', 'Viola', '1234567896', FALSE, 'stefano.viola@example.com', 'password159'),
(8, 'Claudia', 'Arancioni', '1234567897', FALSE, 'claudia.arancioni@example.com', 'password753'),
(9, 'Federico', 'Rosa', '1234567898', TRUE, 'federico.rosa@example.com', 'password369'),
(10, 'Elena', 'Marrone', '1234567899', FALSE, 'elena.marrone@example.com', 'password258');




INSERT INTO AUTO (Numero_Telaio, Prezzo, Immatricolazione, data, targa, ID_Configurazione) VALUES
('1HGBH41JXMN109186', 20000.00, TRUE, '2024-01-10', 'AB123CD', 1),
('2HGBH41JXMN109187', 25000.00, TRUE, '2024-02-11', 'BC234DE', 2),
('3HGBH41JXMN109188', 30000.00, FALSE, '2024-03-12', 'CD345EF', 3),
('4HGBH41JXMN109189', 15000.00, TRUE, '2024-04-13', 'DE456FG', 4),
('5HGBH41JXMN109190', 18000.00, FALSE, '2024-05-14', 'EF567GH', 5),
('6HGBH41JXMN109191', 22000.00, TRUE, '2024-06-15', 'FG678HI', 6),
('7HGBH41JXMN109192', 27000.00, TRUE, '2024-07-16', 'GH789IJ', 7),
('8HGBH41JXMN109193', 32000.00, TRUE, '2024-08-17', 'HI890JK', 8),
('9HGBH41JXMN109194', 17000.00, FALSE, '2024-09-18', 'IJ901KL', 9),
('0HGBH41JXMN109195', 19000.00, TRUE, '2024-10-19', 'JK012LM', 1);

INSERT INTO TIPOLOGIA (nome, caratteristiche) VALUES
('Coupé', 'Due porte, design sportivo, spesso due posti'),
('Sedan', 'Quattro porte, comoda e spaziosa, adatta per famiglie'),
('Hatchback', 'Cinque porte, versatilità elevata con spazio aggiuntivo'),
('Station Wagon', 'Cinque porte, spazio extra nel bagagliaio'),
('SUV', 'Sport Utility Vehicle, alte prestazioni off-road e comfort'),
('Crossover', 'Combinazione di SUV e berlina, design moderno e funzionale'),
('Convertible', 'Auto con tetto apribile, design elegante e sportivo'),
('Pickup', 'Veicolo con cassone posteriore, progettato per trasportare carichi'),
('Minivan', 'Veicolo spazioso con capacità di trasporto per famiglie numerose'),
('Roadster', 'Auto a due posti con tetto rimovibile, progettata per guidare all’aria aperta');

INSERT INTO CONFIGURAZIONE (Motore, alimentazione, cc, horse_power, ID_MODELLO) VALUES
('Motore V8', 'Benzina', 4000, 400, 1),
('Motore V6', 'Diesel', 3000, 300, 2),
('Motore I4', 'Elettrico', 2000, 150, 3),
('Motore I3', 'Ibrido', 1500, 120, 4),
('Motore V8', 'Benzina', 4000, 450, 5),
('Motore V6', 'Diesel', 3000, 320, 6),
('Motore I4', 'Elettrico', 2000, 160, 7),
('Motore I3', 'Ibrido', 1500, 130, 8),
('Motore V8', 'Benzina', 4000, 420, 9),
('Motore V6', 'Diesel', 3000, 310, 9);

INSERT INTO APPUNTAMENTO (ID_APPUNTAMENTO, data, ora, Tipologia, durata, Numero_Telaio, ID_CLIENTE, ID_DIPENDENTE)
VALUES 
(1, '2024-10-05', '18:31:00', 'Test-Drive', '00:30:00', '1HGBH41JXMN109186', 2, 11);

INSERT INTO GARANZIA (scadenza, copertura, Numero_Telaio)
VALUES 
('2026-12-31', 'Furto', '1HGBH41JXMN109186'),
('2025-11-30', 'Incendio', '2HGBH41JXMN109187'),
('2027-01-15', 'kasko', '3HGBH41JXMN109188'),
('2026-09-10', 'grandine', '4HGBH41JXMN109189'),
('2025-08-25', 'furto e incendio', '5HGBH41JXMN109190');
BEGIN;
DROP TRIGGER check_optional_personalizzazione;
INSERT INTO OPTIONAL (descrizione, prezzo)
VALUES 
('Tetto panoramico', 1200),
('Sistema audio premium', 1500),
('Sedili riscaldati', 800),
('Navigatore satellitare', 1000),
('Vernice metallizzata', 500);

INSERT INTO Personalizzazione (Numero_Telaio, ID_Optional)
VALUES 
('1HGBH41JXMN109186', 1),  -- Tetto panoramico for this car
('2HGBH41JXMN109187', 2),  -- Sistema audio premium for this car
('3HGBH41JXMN109188', 3),  -- Sedili riscaldati for this car
('4HGBH41JXMN109189', 4),  -- Navigatore satellitare for this car
('5HGBH41JXMN109190', 5);  -- Vernice metallizzata for this car

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
COMMIT;