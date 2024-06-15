DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE "Horses" (
                          "id" serial PRIMARY KEY,
                          "breed" varchar,
                          "height" integer,
                          "color" varchar,
                          "eye_color" varchar,
                          "age" integer,
                          "origin" varchar,
                          "hairstyle_id" integer,
                          "accessories_id" integer
);

CREATE TABLE "Accessories" (
                               "id" serial PRIMARY KEY,
                               "name" varchar
);

CREATE TABLE "Members" (
                           "id" serial PRIMARY KEY,
                           "name" varchar,
                           "surname" varchar,
                           "date_of_birth" timestamp,
                           "address_id" integer,
                           "phone_number" varchar,
                           "email" varchar,
                           "is_active" bool
);

CREATE TABLE "Riders" (
                          "id" serial PRIMARY KEY,
                          "parent_consent_id" integer,
                          "licence_id" integer,
                          "group_id" integer,
                          "horse_id" integer,
                          FOREIGN KEY ("id") REFERENCES "Members" ("id")
);

CREATE TABLE "Employees" (
                             "id" serial PRIMARY KEY,
                             "position_id" integer,
                             "salary" float,
                             "date_employed" timestamp,
                             FOREIGN KEY ("id") REFERENCES "Members" ("id")
);

CREATE TABLE "Positions" (
                             "id" serial PRIMARY KEY,
                             "name" varchar,
                             "salary_min" float,
                             "salary_max" float,
                             "licence_id" integer,
                             "coaching_licence_id" integer,
                             "speciality" varchar
);

CREATE TABLE "Positions_History" (
                                     "id" serial PRIMARY KEY,
                                     "employee_id" integer,
                                     "position_id" integer,
                                     "date_start" timestamp,
                                     "date_end" timestamp
);

CREATE TABLE "Groups" (
                          "id" serial PRIMARY KEY,
                          "name" varchar,
                          "class_id" integer
);

CREATE TABLE "Classes" (
                           "id" serial PRIMARY KEY,
                           "type" varchar,
                           "date" timestamp,
                           "trainer_id" integer,
                           "group_id" integer,
                           "stable_id" integer
);

CREATE TABLE "Stables" (
                           "id" serial PRIMARY KEY,
                           "name" varchar,
                           "address_id" integer
);

CREATE TABLE "Address" (
                           "id" serial PRIMARY KEY,
                           "country" varchar,
                           "city" varchar,
                           "street" varchar
);

CREATE TABLE "Tournaments" (
                               "id" serial PRIMARY KEY,
                               "name" varchar,
                               "date" timestamp,
                               "address_id" integer
);

CREATE TABLE "Tournament_Participants" (
                                           "id" serial PRIMARY KEY,
                                           "tournament_id" integer,
                                           "contestant_id" integer
);

ALTER TABLE "Horses"
    ADD CONSTRAINT fk_horses_hairstyle FOREIGN KEY ("hairstyle_id") REFERENCES "Accessories" ("id");

ALTER TABLE "Horses"
    ADD CONSTRAINT fk_horses_accessories FOREIGN KEY ("accessories_id") REFERENCES "Accessories" ("id");

ALTER TABLE "Members"
    ADD CONSTRAINT fk_members_address FOREIGN KEY ("address_id") REFERENCES "Address" ("id");

ALTER TABLE "Riders"
    ADD CONSTRAINT fk_riders_horses FOREIGN KEY ("horse_id") REFERENCES "Horses" ("id");

ALTER TABLE "Employees"
    ADD CONSTRAINT fk_employees_position FOREIGN KEY ("position_id") REFERENCES "Positions" ("id");

ALTER TABLE "Positions_History"
    ADD CONSTRAINT fk_positions_history_employee FOREIGN KEY ("employee_id") REFERENCES "Employees" ("id");

ALTER TABLE "Positions_History"
    ADD CONSTRAINT fk_positions_history_position FOREIGN KEY ("position_id") REFERENCES "Positions" ("id");

ALTER TABLE "Groups"
    ADD CONSTRAINT fk_groups_class FOREIGN KEY ("class_id") REFERENCES "Classes" ("id");

ALTER TABLE "Classes"
    ADD CONSTRAINT fk_classes_trainer FOREIGN KEY ("trainer_id") REFERENCES "Employees" ("id");

ALTER TABLE "Classes"
    ADD CONSTRAINT fk_classes_group FOREIGN KEY ("group_id") REFERENCES "Groups" ("id");

ALTER TABLE "Classes"
    ADD CONSTRAINT fk_classes_stable FOREIGN KEY ("stable_id") REFERENCES "Stables" ("id");

ALTER TABLE "Stables"
    ADD CONSTRAINT fk_stables_address FOREIGN KEY ("address_id") REFERENCES "Address" ("id");

ALTER TABLE "Tournaments"
    ADD CONSTRAINT fk_tournaments_address FOREIGN KEY ("address_id") REFERENCES "Address" ("id");

ALTER TABLE "Tournament_Participants"
    ADD CONSTRAINT fk_tournament_participants_tournament FOREIGN KEY ("tournament_id") REFERENCES "Tournaments" ("id");

ALTER TABLE "Tournament_Participants"
    ADD CONSTRAINT fk_tournament_participants_contestant FOREIGN KEY ("contestant_id") REFERENCES "Riders" ("id");

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

INSERT INTO "Address" ("country", "city", "street") VALUES
('Polska', 'Warszawa', 'Puławska 20'),
('Polska', 'Warszawa', 'Marszałkowska 45'),
('Polska', 'Warszawa', 'Krakowskie Przedmieście 56'),
('Polska', 'Piaseczno', 'Warszawska 10'),
('Polska', 'Konstancin-Jeziorna', 'Kościelna 5'),
('Polska', 'Warszawa', 'Nowy Świat 30'),
('Polska', 'Warszawa', 'Plac Defilad 1'),
('Polska', 'Pruszków', 'Główna 3'),
('Polska', 'Legionowo', 'Polna 6'),
('Polska', 'Grodzisk Mazowiecki', 'Rynek 7');

INSERT INTO "Members" ("name", "surname", "date_of_birth", "address_id", "phone_number", "email", "is_active") VALUES
('Anna', 'Kowalska', '2000-05-15', 1, '123456789', 'anna.kowalska@example.com', TRUE),
('Jan', 'Nowak', '1985-11-02', 2, '987654321', 'jan.nowak@example.com', TRUE),
('Ewa', 'Wiśniewska', '1995-06-20', 3, '123123123', 'ewa.wisniewska@example.com', TRUE),
('Tomasz', 'Zieliński', '1975-12-30', 4, '321321321', 'tomasz.zielinski@example.com', TRUE),
('Maria', 'Wójcik', '1990-01-22', 5, '456456456', 'maria.wojcik@example.com', TRUE),
('Paweł', 'Mazur', '1988-03-12', 6, '987987987', 'pawel.mazur@example.com', TRUE),
('Katarzyna', 'Sikora', '1993-09-07', 7, '654654654', 'katarzyna.sikora@example.com', TRUE),
('Grzegorz', 'Nowicki', '2001-11-18', 8, '789789789', 'grzegorz.nowicki@example.com', TRUE),
('Aleksandra', 'Zalewska', '1983-02-21', 9, '741741741', 'aleksandra.zalewska@example.com', TRUE),
('Piotr', 'Kamiński', '1999-07-13', 10, '852852852', 'piotr.kaminski@example.com', TRUE);

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
(1, 3200.00, '2018-05-22'),
(2, 3700.00, '2017-12-11'),
(3, 4200.00, '2016-10-18'),
(4, 4800.00, '2015-04-25'),
(5, 5300.00, '2020-08-09');

INSERT INTO "Positions" ("name", "salary_min", "salary_max", "licence_id", "coaching_licence_id", "speciality") VALUES
('Trener', 3000.00, 5000.00, 1, 1, 'Skoki przez przeszkody'),
('Instruktor', 2500.00, 4000.00, 2, 2, 'Ujeżdżenie'),
('Weterynarz', 4000.00, 6000.00, 3, NULL, 'Medycyna koni'),
('Opiekun', 2000.00, 3500.00, 4, NULL, 'Codzienna opieka'),
('Administrator', 3500.00, 5500.00, 5, NULL, 'Zarządzanie klubem'),
('Koordynator', 3300.00, 5300.00, 1, 1, 'Organizacja turniejów'),
('Technik', 2200.00, 3700.00, 2, NULL, 'Utrzymanie sprzętu'),
('Specjalista IT', 4500.00, 6500.00, 3, NULL, 'Wsparcie techniczne'),
('Marketingowiec', 3000.00, 5000.00, 4, NULL, 'Promocja klubu'),
('HR', 3800.00, 5800.00, 5, NULL, 'Zarządzanie personelem');

INSERT INTO "Positions_History" ("employee_id", "position_id", "date_start", "date_end") VALUES
(1, 1, '2019-01-01', '2020-01-01'),
(2, 2, '2018-05-01', '2019-05-01'),
(3, 3, '2020-06-01', '2021-06-01'),
(4, 4, '2019-07-01', '2020-07-01'),
(5, 5, '2021-08-01', '2022-08-01'),
(6, 1, '2021-09-01', '2022-09-01'),
(7, 2, '2020-10-01', '2021-10-01'),
(8, 3, '2019-11-01', '2020-11-01'),
(9, 4, '2018-12-01', '2019-12-01'),
(10, 5, '2017-01-01', '2018-01-01');

INSERT INTO "Groups" ("name", "class_id") VALUES
('Początkujący', 1),
('Średniozaawansowani', 2),
('Zaawansowani', 3),
('Profesjonaliści', 4),
('Eksperci', 5),
('Młodzież', 1),
('Dorośli', 2),
('Seniorzy', 3),
('Amatorzy', 4),
('Hobbyści', 5);

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
('Turniej Zaawansowanych', '2024-08-01', 3),
('Turniej Profesjonalistów', '2024-08-15', 4),
('Turniej Ekspertów', '2024-09-01', 5),
('Mistrzostwa Młodzieży', '2024-09-10', 6),
('Otwarte Zawody', '2024-09-20', 7),
('Letni Puchar', '2024-10-01', 8),
('Jesienne Rozgrywki', '2024-10-15', 9),
('Zimowe Turnieje', '2024-11-01', 10);

INSERT INTO "Tournament_Participants" ("tournament_id", "contestant_id") VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(1, 6),
(2, 7),
(3, 8),
(4, 9),
(5, 10);

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
