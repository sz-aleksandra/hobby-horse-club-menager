USE horses-database;

INSERT INTO Accessories (name) VALUES
('Saddle'),
('Helmet'),
('Bridle'),
('Blanket'),
('Bandages'),
('Whip'),
('Girth'),
('Breastplate'),
('Saddle pad'),
('Ends for whips');

INSERT INTO Address (country, city, street) VALUES
('Poland', 'Warsaw', 'Puławska 20'),
('Poland', 'Warsaw', 'Marszałkowska 45'),
('Poland', 'Warsaw', 'Krakowskie Przedmieście 56'),
('Poland', 'Piaseczno', 'Warszawska 10'),
('Poland', 'Konstancin-Jeziorna', 'Kościelna 5'),
('Poland', 'Warsaw', 'Nowy Świat 30'),
('Poland', 'Warsaw', 'Plac Defilad 1'),
('Poland', 'Pruszków', 'Główna 3'),
('Poland', 'Legionowo', 'Polna 6'),
('Poland', 'Grodzisk Mazowiecki', 'Rynek 7');

INSERT INTO Members (name, surname, date_of_birth, address_id, phone_number, email, is_active) VALUES
('Anna', 'Kowalska', '2000-05-15', 1, '123456789', 'anna.kowalska@example.com', TRUE),
('Jan', 'Nowak', '1985-11-02', 2, '987654321', 'jan.nowak@example.com', TRUE),
('Ewa', 'Wiśniewska', '1995-06-20', 3, '123123123', 'ewa.wisniewska@example.com', TRUE),
('Tomasz', 'Zieliński', '1975-12-30', 4, '321321321', 'tomasz.zielinski@example.com', TRUE),
('Maria', 'Wójcik', '1990-01-22', 5, '456456456', 'maria.wojcik@example.com', TRUE),
('Paweł', 'Mazur', '1988-03-12', 6, '987987987', 'pawel.mazur@example.com', TRUE),
('Katarzyna', 'Sikora', '1993-09-07', 7, '654654654', 'katarzyna.sikora@example.com', TRUE),
('Grzegorz', 'Nowicki', '2001-11-18', 8, '789789789', 'grzegorz.nowicki@example.com', TRUE),
('Aleksandra', 'Zalewska', '1983-02-21', 9, '741741741', 'aleksandra.zalewska@example.com', TRUE),
('Piotr', 'Kamiński', '1999-07-13', 10, '852852852', 'piotr.kaminski@example.com', TRUE),
('Jacek', 'Lewandowski', '1982-07-21', 1, '321321123', 'jacek.lewandowski@example.com', TRUE),
('Barbara', 'Kwiatkowska', '1979-04-17', 2, '654987321', 'barbara.kwiatkowska@example.com', TRUE),
('Michał', 'Kaczmarek', '1991-12-03', 3, '789456123', 'michal.kaczmarek@example.com', TRUE),
('Agnieszka', 'Dąbrowska', '1984-06-29', 4, '456123789', 'agnieszka.dabrowska@example.com', TRUE),
('Karolina', 'Szymańska', '1992-03-11', 5, '321456987', 'karolina.szymanska@example.com', TRUE);

INSERT INTO Horses (breed, height, color, eye_color, age, origin, hairstyle_id, accessories_id) VALUES
('Hucul', 150, 'Chestnut', 'Brown', 5, 'Poland', 1, 1),
('Małopolski', 160, 'Dapple gray', 'Blue', 8, 'Poland', 2, 2),
('Friesian', 155, 'Black', 'Black', 7, 'Netherlands', 3, 3),
('Polish', 145, 'Bay', 'Brown', 10, 'Poland', 4, 4),
('Connemara', 148, 'Gray', 'Green', 6, 'Ireland', 5, 5),
('Anglo-Arabian', 152, 'Chestnut', 'Brown', 9, 'Poland', 1, 6),
('Greater Polish', 165, 'Bay', 'Black', 4, 'Poland', 2, 7),
('Dutch Warmblood', 157, 'Dapple gray', 'Brown', 12, 'Netherlands', 3, 8),
('Andalusian', 162, 'Dapple gray', 'Blue', 6, 'Spain', 4, 9),
('Hanoverian', 154, 'Gray', 'Brown', 11, 'Germany', 5, 10);

INSERT INTO Riders (parent_consent, licence, group_id, horse_id, member_id) VALUES
('Yes', 'Level 1 License', 1, 1, 1),
('No', 'Level 2 License', 2, 2, 2),
('Yes', 'Level 3 License', 3, 3, 3),
('No', 'Level 4 License', 4, 4, 4),
('Yes', 'Level 5 License', 5, 5, 5),
('Yes', 'Level 1 License', 6, 6, 11),
('No', 'Level 2 License', 7, 7, 12),
('Yes', 'Level 3 License', 8, 8, 13),
('No', 'Level 4 License', 9, 9, 14),
('Yes', 'Level 5 License', 10, 10, 15);

INSERT INTO Employees (position_id, salary, date_employed, member_id) VALUES
(1, 3500.00, '2020-01-15', 1),
(2, 4000.00, '2019-11-20', 2),
(3, 4500.00, '2018-07-05', 3),
(4, 5000.00, '2021-03-10', 4),
(5, 5500.00, '2022-09-01', 5),
(1, 3200.00, '2018-05-22', 11),
(2, 3700.00, '2017-12-11', 12),
(3, 4200.00, '2016-10-18', 13),
(4, 4800.00, '2015-04-25', 14),
(5, 5300.00, '2020-08-09', 15);

INSERT INTO Positions (name, salary_min, salary_max, licence_id, coaching_licence_id, speciality) VALUES
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

INSERT INTO Positions_History (employee_id, position_id, date_start, date_end) VALUES
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

INSERT INTO `Groups` (name, class_id) VALUES
('Beginners', 1),
('Intermediate', 2),
('Advanced', 3),
('Professionals', 4),
('Experts', 5),
('Youth', 1),
('Adults', 2),
('Seniors', 3),
('Amateurs', 4),
('Hobbyists', 5);


INSERT INTO Classes (type, date, trainer_id, group_id, stable_id) VALUES
('Recreational riding', '2024-06-01', 1, 1, 1),
('Sport riding', '2024-06-02', 2, 2, 2),
('Dressage', '2024-06-03', 3, 3, 3),
('Show jumping', '2024-06-04', 4, 4, 4),
('Cross-country', '2024-06-05', 5, 5, 5),
('Terrain riding', '2024-06-06', 1, 6, 1),
('Competition outing', '2024-06-07', 2, 7, 2),
('Specialized training', '2024-06-08', 3, 8, 3),
('Exercises with horses', '2024-06-09', 4, 9, 4),
('Training clinic', '2024-06-10', 5, 10, 5);

INSERT INTO Stables (name, address_id) VALUES
('Stable A', 1),
('Stable B', 2),
('Stable C', 3),
('Stable D', 4),
('Stable E', 5),
('Stable F', 6),
('Stable G', 7),
('Stable H', 8),
('Stable I', 9),
('Stable J', 10);

INSERT INTO Tournaments (name, date, address_id) VALUES
('Beginners Tournament', '2024-07-01', 1),
('Intermediate Tournament', '2024-07-15', 2),
('Advanced Tournament', '2024-08-01', 3),
('Professionals Tournament', '2024-08-15', 4),
('Experts Tournament', '2024-09-01', 5),
('Youth Championships', '2024-09-10', 6),
('Open Competition', '2024-09-20', 7),
('Summer Cup', '2024-10-01', 8),
('Autumn Games', '2024-10-15', 9),
('Winter Tournaments', '2024-11-01', 10);


INSERT INTO Tournament_Participants (tournament_id, contestant_id) VALUES
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

