DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE Horses (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        breed VARCHAR(255),
                        height DECIMAL(5, 2),
                        color VARCHAR(50),
                        eye_color VARCHAR(50),
                        age INT,
                        origin VARCHAR(255),
                        hairstyle VARCHAR(255),
                        accessories_id INT,
                        FOREIGN KEY (accessories_id) REFERENCES Accessories(id)
);

CREATE TABLE Accessories (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL
);

CREATE TABLE Members (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         surname VARCHAR(255) NOT NULL,
                         date_of_birth DATE NOT NULL,
                         address_id INT NOT NULL,
                         phone_number VARCHAR(20),
                         email VARCHAR(255),
                         is_active BOOLEAN NOT NULL DEFAULT TRUE,
                         licence_id INT,
                         FOREIGN KEY (address_id) REFERENCES Addresses(id),
                         FOREIGN KEY (licence_id) REFERENCES Licences(id)
);

CREATE TABLE Riders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        member_id INT NOT NULL,
                        parent_consent_id BOOLEAN NOT NULL,
                        group_id INT,
                        horse_id INT,
                        FOREIGN KEY (member_id) REFERENCES Members(id),
                        FOREIGN KEY (group_id) REFERENCES Groups(id),
                        FOREIGN KEY (horse_id) REFERENCES Horses(id)
);
);

CREATE TABLE Employees (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           member_id INT NOT NULL,
                           position_id INT NOT NULL,
                           salary DECIMAL(10, 2) NOT NULL,
                           date_employed DATE NOT NULL,
                           FOREIGN KEY (member_id) REFERENCES Members(id),
                           FOREIGN KEY (position_id) REFERENCES Positions(id)
);

CREATE TABLE Positions (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           salary_min DECIMAL(10, 2) NOT NULL,
                           salary_max DECIMAL(10, 2) NOT NULL,
                           licence_id INT,
                           coaching_licence_id INT,
                           speciality VARCHAR(255),
                           FOREIGN KEY (licence_id) REFERENCES Licences(id),
                           FOREIGN KEY (coaching_licence_id) REFERENCES Licences(id)
);

CREATE TABLE Positions_History (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   employee_id INT NOT NULL,
                                   position_id INT NOT NULL,
                                   date_start DATE NOT NULL,
                                   date_end DATE,
                                   FOREIGN KEY (employee_id) REFERENCES Employees(id),
                                   FOREIGN KEY (position_id) REFERENCES Positions(id)
);

CREATE TABLE Groups (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        class_id INT,
                        max_group_members INT NOT NULL,
                        FOREIGN KEY (class_id) REFERENCES Classes(id)
);

CREATE TABLE Classes (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         type VARCHAR(255) NOT NULL,
                         date DATE NOT NULL,
                         trainer_id INT,
                         group_id INT,
                         stable_id INT,
                         FOREIGN KEY (trainer_id) REFERENCES Members(id),
                         FOREIGN KEY (group_id) REFERENCES Groups(id),
                         FOREIGN KEY (stable_id) REFERENCES Stables(id)
);

CREATE TABLE Stables (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         address_id INT NOT NULL,
                         FOREIGN KEY (address_id) REFERENCES Addresses(id)
);

CREATE TABLE Addresses (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           country VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           street VARCHAR(255) NOT NULL,
                           street_no VARCHAR(50) NOT NULL,
                           postal_code VARCHAR(20) NOT NULL
);

CREATE TABLE Tournaments (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             address_id INT NOT NULL,
                             FOREIGN KEY (address_id) REFERENCES Addresses(id)
);

CREATE TABLE Tournament_Participants (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         tournament_id INT NOT NULL,
                                         contestant_id INT NOT NULL,
                                         contestant_place INT,
                                         FOREIGN KEY (tournament_id) REFERENCES Tournaments(id),
                                         FOREIGN KEY (contestant_id) REFERENCES Members(id)
);

CREATE TABLE Licences (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          licence_level VARCHAR(255) NOT NULL
);

INSERT INTO "Accessories" ("name") VALUES
('Siodełko'),
('Kask'),
('Ogłowie'),
('Derka'),
('Bandaże'),
('Bat'),
('Popręg'),
('Napierśnik'),
('Podkładka pod siodło'),
('Końcówki do batów');

INSERT INTO "Licences" ("licence_level") VALUES
('Licencja poziom 1'),
('Licencja poziom 2'),
('Licencja poziom 3'),
('Licencja poziom 4'),
('Licencja poziom 5');

INSERT INTO "Addresses" ("country", "city", "street", "street_no", "postal_code") VALUES
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

INSERT INTO "Members" ("name", "surname", "date_of_birth", "address_id", "phone_number", "email", "is_active", "licence_id") VALUES
('Anna', 'Kowalska', '2000-05-15', 1, '123456789', 'anna.kowalska@example.com', TRUE, 1),
('Jan', 'Nowak', '1985-11-02', 2, '987654321', 'jan.nowak@example.com', TRUE, 2),
('Ewa', 'Wiśniewska', '1995-06-20', 3, '123123123', 'ewa.wisniewska@example.com', TRUE, 3),
('Tomasz', 'Zieliński', '1975-12-30', 4, '321321321', 'tomasz.zielinski@example.com', TRUE, 4),
('Maria', 'Wójcik', '1990-01-22', 5, '456456456', 'maria.wojcik@example.com', TRUE, 5),
('Paweł', 'Mazur', '1988-03-12', 6, '987987987', 'pawel.mazur@example.com', TRUE, 1),
('Katarzyna', 'Sikora', '1993-09-07', 7, '654654654', 'katarzyna.sikora@example.com', TRUE, 2),
('Grzegorz', 'Nowicki', '2001-11-18', 8, '789789789', 'grzegorz.nowicki@example.com', TRUE, 3),
('Aleksandra', 'Zalewska', '1983-02-21', 9, '741741741', 'aleksandra.zalewska@example.com', TRUE, 4),
('Piotr', 'Kamiński', '1999-07-13', 10, '852852852', 'piotr.kaminski@example.com', TRUE, 5),
('Jacek', 'Lewandowski', '1982-07-21', 1, '321321123', 'jacek.lewandowski@example.com', TRUE, 1),
('Barbara', 'Kwiatkowska', '1979-04-17', 2, '654987321', 'barbara.kwiatkowska@example.com', TRUE, 2),
('Michał', 'Kaczmarek', '1991-12-03', 3, '789456123', 'michal.kaczmarek@example.com', TRUE, 3),
('Agnieszka', 'Dąbrowska', '1984-06-29', 4, '456123789', 'agnieszka.dabrowska@example.com', TRUE, 4),
('Karolina', 'Szymańska', '1992-03-11', 5, '321456987', 'karolina.szymanska@example.com', TRUE, NULL);

INSERT INTO "Horses" ("breed", "height", "color", "eye_color", "age", "origin", "hairstyle_id", "accessories_id") VALUES
('Hucul', 150, 'Kasztanowy', 'Brązowy', 5, 'Polska', 1, 1),
('Małopolski', 160, 'Siwy', 'Niebieski', 8, 'Polska', 2, 2),
('Fryzyjski', 155, 'Czarny', 'Czarny', 7, 'Holandia', 3, 3),
('Polski', 145, 'Gniady', 'Brązowy', 10, 'Polska', 4, 4),
('Connemara', 148, 'Kary', 'Zielony', 6, 'Irlandia', 5, 5),
('Angloarab', 152, 'Kasztanowy', 'Brązowy', 9, 'Polska', 1, 6),
('Wielkopolski', 165, 'Gniady', 'Czarny', 4, 'Polska', 2, 7),
('Holenderski', 157, 'Siwy', 'Brązowy', 12, 'Holandia', 3, 8),
('Andaluzyjski', 162, 'Siwy', 'Niebieski', 6, 'Hiszpania', 4, 9),
('Hanowerski', 154, 'Kary', 'Brązowy', 11, 'Niemcy', 5, 10);

INSERT INTO "Riders" ("parent_consent", "licence", "group_id", "horse_id") VALUES
('Tak', 'Licencja poziom 1', 1, 1),
('Nie', 'Licencja poziom 2', 2, 2),
('Tak', 'Licencja poziom 3', 3, 3),
('Nie', 'Licencja poziom 4', 4, 4),
('Tak', 'Licencja poziom 5', 5, 5),
('Tak', 'Licencja poziom 1', 1, 6),
('Nie', 'Licencja poziom 2', 2, 7),
('Tak', 'Licencja poziom 3', 3, 8),
('Nie', 'Licencja poziom 4', 4, 9),
('Tak', 'Licencja poziom 5', 5, 10);

INSERT INTO "Employees" ("position_id", "salary", "date_employed") VALUES
(1, 3500.00, '2020-01-15'),
(2, 4000.00, '2019-11-20'),
(3, 4500.00, '2018-07-05'),
(4, 5000.00, '2021-03-10'),
(5, 5500.00, '2022-09-01'),

INSERT INTO "Positions" ("name", "salary_min", "salary_max", "licence_id", "coaching_licence_id", "speciality") VALUES
('Trener', 3000.00, 6000.00, 2, 3, 'Jeździectwo'),
('Instruktor', 2500.00, 5000.00, 2, 4, 'Instruktor jeździectwa'),
('Weterynarz', 4000.00, 8000.00, 3, NULL, 'Opieka nad zwierzętami'),
('Opiekun', 3000.00, 6000.00, NULL, NULL, 'Opieka nad końmi'),
('Administrator', 3500.00, 7000.00, 1, NULL, 'Administracja'),
('Koordynator', 3200.00, 6500.00, 2, NULL, 'Koordynacja projektów'),
('Technik', 2800.00, 5600.00, NULL, NULL, 'Techniczne wsparcie'),
('Specjalista IT', 3700.00, 7400.00, 1, NULL, 'Technologie informatyczne'),
('Marketingowiec', 3000.00, 6000.00, NULL, NULL, 'Marketing'),
('HR', 3300.00, 6600.00, 2, NULL, 'Zarządzanie zasobami ludzkimi');

INSERT INTO "Positions_History" ("employee_id", "position_id", "date_start", "date_end") VALUES
(1, 1, '2019-01-01', '2020-01-01'),
(2, 2, '2018-05-01', '2019-05-01'),
(3, 3, '2020-06-01', '2021-06-01'),
(4, 4, '2019-07-01', '2020-07-01'),
(5, 5, '2021-08-01', '2022-08-01'),

INSERT INTO "Groups" ("name", "class_id", "max_group_members") VALUES
    ('Grupa początkująca', 1, 10),
    ('Grupa średniozaawansowana', 2, 15),
    ('Grupa zaawansowana', 3, 8);

INSERT INTO "Classes" ("type", "date", "trainer_id", "group_id", "stable_id") VALUES
('Jazda rekreacyjna', '2024-06-01', 1, 1, 1),
('Jazda sportowa', '2024-06-02', 2, 2, 2),
('Ujeżdżenie', '2024-06-03', 3, 3, 3),
('Skoki przez przeszkody', '2024-06-04', 4, 4, 4),
('Kros', '2024-06-05', 5, 5, 5),
('Teren', '2024-06-06', 1, 6, 1),
('Wyjazd na zawody', '2024-06-07', 2, 7, 2),
('Trening specjalistyczny', '2024-06-08', 3, 8, 3),
('Ćwiczenia z końmi', '2024-06-09', 4, 9, 4),
('Klinika szkoleniowa', '2024-06-10', 5, 10, 5);

INSERT INTO "Stables" ("name", "address_id") VALUES
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

INSERT INTO "Tournaments" ("name", "date", "address_id") VALUES
('Turniej Początkujących', '2024-07-01', 1),
('Turniej Średniozaawansowanych', '2024-07-15', 2),

INSERT INTO "Tournament_Participants" ("tournament_id", "contestant_id", "contestant_place") VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 4),
(1, 4, 3),
(2, 1, 2),
(2, 2, 1),
(2, 3, 2),
(2, 4, 4),
(2, 5, 5);

INSERT INTO "Members" ("name", "surname", "date_of_birth", "address_id", "phone_number", "email", "is_active") VALUES
('Jacek', 'Lewandowski', '1982-07-21', 1, '321321123', 'jacek.lewandowski@example.com', TRUE),
('Barbara', 'Kwiatkowska', '1979-04-17', 2, '654987321', 'barbara.kwiatkowska@example.com', TRUE),
('Michał', 'Kaczmarek', '1991-12-03', 3, '789456123', 'michal.kaczmarek@example.com', TRUE),
('Agnieszka', 'Dąbrowska', '1984-06-29', 4, '456123789', 'agnieszka.dabrowska@example.com', TRUE),
('Karolina', 'Szymańska', '1992-03-11', 5, '321456987', 'karolina.szymanska@example.com', TRUE);

INSERT INTO "Employees" ("position_id", "salary", "date_employed") VALUES
(6, 3600.00, '2022-03-12'),
(7, 2800.00, '2021-11-08'),
(8, 4700.00, '2020-05-25'),
(9, 3400.00, '2019-08-16'),
(10, 3900.00, '2018-12-30');

INSERT INTO "Riders" ("parent_consent", "licence", "group_id", "horse_id") VALUES
('Tak', 'Licencja poziom 1', 6, 6),
('Nie', 'Licencja poziom 2', 7, 7),
('Tak', 'Licencja poziom 3', 8, 8),
('Nie', 'Licencja poziom 4', 9, 9),
('Tak', 'Licencja poziom 5', 10, 10);
