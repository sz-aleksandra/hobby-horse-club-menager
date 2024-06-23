USE horses-database;

-- Inserty do tabeli Addresses
INSERT INTO Addresses (country, city, street, street_no, postal_code) VALUES ('Poland', 'Warsaw', 'Main Street', '123', '00-001'),
('Polska', 'Warszawa', 'Puławska', '20', '02-515'),
('Polska', 'Warszawa', 'Marszałkowska', '45', '00-676'),
('Polska', 'Warszawa', 'Krakowskie Przedmieście', '56', '00-322'),
('Polska', 'Piaseczno', 'Warszawska', '10', '05-500'),
('Polska', 'Konstancin-Jeziorna', 'Kościelna', '5', '05-510'),
('Polska', 'Warszawa', 'Nowy Świat', '30', '00-029'),
('Polska', 'Warszawa', 'Plac Defilad', '1', '00-901'),
('Polska', 'Pruszków', 'Główna', '3', '05-800'),
('Polska', 'Legionowo', 'Polna', '6', '05-120'),
('Polska', 'Grodzisk Mazowiecki', 'Rynek', '7', '05-825');

-- Inserty do tabeli Licences
INSERT INTO Licences (licence_level) VALUES
('Licencja poziom 1'),
('Licencja poziom 2'),
('Licencja poziom 3'),
('Licencja poziom 4'),
('Licencja poziom 5');

-- Inserty do tabeli Groups
INSERT INTO `Groups` (name, max_group_members) VALUES
('Grupa początkująca', 10),
('Grupa średniozaawansowana', 15),
('Grupa zaawansowana', 8);

-- Inserty do tabeli Horses
INSERT INTO Horses (breed, height, color, eye_color, age, origin, hairstyle) VALUES
('Hucul', 150, 'Kasztanowy', 'Brązowy', 5, 'Polska', 'Mane'),
('Małopolski', 160, 'Siwy', 'Niebieski', 8, 'Polska', 'Mane'),
('Fryzyjski', 155, 'Czarny', 'Czarny', 7, 'Holandia', 'Mane'),
('Polski', 145, 'Gniady', 'Brązowy', 10, 'Polska', 'Mane'),
('Connemara', 148, 'Kary', 'Zielony', 6, 'Irlandia', 'Braided'),
('Angloarab', 152, 'Kasztanowy', 'Brązowy', 9, 'Polska', 'Braided'),
('Wielkopolski', 165, 'Gniady', 'Czarny', 4, 'Polska', 'Braided'),
('Holenderski', 157, 'Siwy', 'Brązowy', 12, 'Holandia', 'Flowing'),
('Andaluzyjski', 162, 'Siwy', 'Niebieski', 6, 'Hiszpania', 'Flowing'),
('Hanowerski', 154, 'Kary', 'Brązowy', 11, 'Niemcy', 'Flowing');


-- Inserty do tabeli AccessoryTypes
INSERT INTO AccessoryTypes (type_name) VALUES
('Siodła'),
('Uzdy'),
('Koce');

-- Inserty do tabeli Accessories
INSERT INTO Accessories (name, type_id) VALUES
('Angielskie siodło', 1),
('Letni koc', 3),
('Zimowy koc', 3);

-- Inserty do tabeli Horses_Accessories (przykładowe powiązania akcesoriów z końmi)
INSERT INTO Horses_Accessories (horse_id, accessory_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Inserty do tabeli Stables
INSERT INTO Stables (name, address_id) VALUES
('Stajnia A', 1),
('Stajnia B', 2),
('Stajnia C', 3),
('Stajnia D', 4),
('Stajnia E', 5),
('Stajnia F', 6),
('Stajnia G', 7),
('Stajnia H', 8),
('Stajnia I', 9),
('Stajnia J', 10);

-- Inserty do tabeli Positions
INSERT INTO Positions (name, salary_min, salary_max, licence_id, coaching_licence_id, speciality) VALUES
('Trener', 3000.00, 6000.00, 2, 3, 'Jeździectwo'),
('Instruktor', 2500.00, 5000.00, 2, 4, 'Instruktor jeździectwa'),
('Weterynarz', 4000.00, 8000.00, 3, 3, 'Opieka nad zwierzętami'),
('Opiekun', 3000.00, 6000.00, 3, 3, 'Opieka nad końmi'),
('Administrator', 3500.00, 7000.00, 1, 3, 'Administracja'),
('Koordynator', 3200.00, 6500.00, 2, 3, 'Koordynacja projektów'),
('Technik', 2800.00, 5600.00, 3, 3, 'Techniczne wsparcie'),
('Specjalista IT', 3700.00, 7400.00, 1, 3, 'Technologie informatyczne'),
('Marketingowiec', 3000.00, 6000.00, 3, 3, 'Marketing'),
('HR', 3300.00, 6600.00, 2, 3, 'Zarządzanie zasobami ludzkimi');


-- Inserty do tabeli Members
INSERT INTO Members (name, surname, username, password, date_of_birth, address_id, phone_number, email, is_active, licence_id) VALUES
('Jacek', 'Lewandowski', 'jlewan', 'qwerty', '1982-07-21', 1, '321321123', 'jacek.lewandowski@example.com', TRUE, 1),
('Barbara', 'Kwiatkowska', 'bkwat', 'qwer', '1979-04-17', 2, '654987321', 'barbara.kwiatkowska@example.com', TRUE, 2),
('Michał', 'Kaczmarek', 'mkaczmar', '12345', '1991-12-03', 3, '789456123', 'michal.kaczmarek@example.com', TRUE, 1),
('Agnieszka', 'Dąbrowska', 'adab', '09876', '1984-06-29', 4, '456123789', 'agnieszka.dabrowska@example.com', TRUE, 2),
('Karolina', 'Szymańska', 'kszyman', 'ytrewq', '1992-03-11', 5, '321456987', 'karolina.szymanska@example.com', TRUE, 3);

-- Inserty do tabeli Riders
INSERT INTO Riders (member_id, parent_consent, group_id, horse_id)
VALUES (1, TRUE, 1, 1),
       (2, FALSE, 2, 2),
       (3, TRUE, 3, 3);

-- Inserty do tabeli Employees
INSERT INTO Employees (member_id, position_id, salary, date_employed) VALUES
(1, 6, 3600.00, '2022-03-12'),
(2, 7, 2800.00, '2021-11-08'),
(3, 8, 4700.00, '2020-05-25'),
(4, 9, 3400.00, '2019-08-16'),
(5, 10, 3900.00, '2018-12-30');

-- Inserty do tabeli Positions_History
INSERT INTO Positions_History (employee_id, position_id, date_start, date_end) VALUES
(1, 1, '2019-01-01', '2020-01-01'),
(2, 2, '2018-05-01', '2019-05-01'),
(3, 3, '2020-06-01', '2021-06-01'),
(4, 4, '2019-07-01', '2020-07-01'),
(5, 5, '2021-08-01', '2022-08-01');

-- Inserty do tabeli Classes
INSERT INTO Classes (type, date, trainer_id, group_id, stable_id) VALUES
('Jazda rekreacyjna', '2024-06-01', 1, 1, 1),
('Jazda sportowa', '2024-06-02', 2, 2, 2),
('Ujeżdżenie', '2024-06-03', 3, 3, 3),
('Skoki przez przeszkody', '2024-06-04', 4, 1, 4),
('Kros', '2024-06-05', 5, 2, 5),
('Teren', '2024-06-06', 1, 2, 1),
('Wyjazd na zawody', '2024-06-07', 2, 2, 2),
('Trening specjalistyczny', '2024-06-08', 3, 3, 3),
('Ćwiczenia z końmi', '2024-06-09', 4, 3, 4),
('Klinika szkoleniowa', '2024-06-10', 5, 3, 5);

-- Inserty do tabeli Tournaments
INSERT INTO Tournaments (name, date, address_id, judge_id) VALUES
('Turniej Początkujących', '2024-07-01', 1, 1),
('Turniej Średniozaawansowanych', '2024-07-15', 2, 1);

-- Inserty do tabeli Tournament_Participants
INSERT INTO Tournament_Participants (tournament_id, contestant_id, contestant_place) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 4),
(1, 4, 3),
(2, 1, 2),
(2, 2, 1),
(2, 3, 2),
(2, 4, 4),
(2, 5, 5);
