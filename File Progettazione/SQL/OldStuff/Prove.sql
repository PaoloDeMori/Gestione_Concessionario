INSERT INTO CLIENTE (nome, cognome, telefono, e_mail) VALUES
('Mario', 'Rossi', '1234567890', 'mario.rossi@example.com'),
('Luigi', 'Bianchi', '1234567891', 'luigi.bianchi@example.com'),
('Anna', 'Verdi', '1234567892', 'anna.verdi@example.com'),
('Giuseppe', 'Neri', '1234567893', 'giuseppe.neri@example.com'),
('Laura', 'Gialli', '1234567894', 'laura.gialli@example.com'),
('Francesco', 'Blu', '1234567895', 'francesco.blu@example.com'),
('Federica', 'Arancioni', '1234567896', 'federica.arancioni@example.com'),
('Paolo', 'Grigi', '1234567897', 'paolo.grigi@example.com'),
('Silvia', 'Rosa', '1234567898', 'silvia.rosa@example.com'),
('Roberto', 'Viola', '1234567899', 'roberto.viola@example.com');

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



INSERT INTO DIPENDENTE (ID_MARCHIO, nome, cognome, teleofno, e_mail) VALUES
(1, 'Alberto', 'Rossi', '0987654321', 'alberto.rossi@example.com'),
(2, 'Giulia', 'Bianchi', '0987654322', 'giulia.bianchi@example.com'),
(3, 'Marco', 'Verdi', '0987654323', 'marco.verdi@example.com'),
(4, 'Claudia', 'Neri', '0987654324', 'claudia.neri@example.com'),
(5, 'Stefano', 'Gialli', '0987654325', 'stefano.gialli@example.com'),
(6, 'Francesca', 'Blu', '0987654326', 'francesca.blu@example.com'),
(7, 'Antonio', 'Arancioni', '0987654327', 'antonio.arancioni@example.com'),
(8, 'Elena', 'Grigi', '0987654328', 'elena.grigi@example.com'),
(9, 'Matteo', 'Rosa', '0987654329', 'matteo.rosa@example.com'),
(10, 'Silvia', 'Viola', '0987654330', 'silvia.viola@example.com');



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


