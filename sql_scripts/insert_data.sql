USE horses-database;

-- Insert statements for the Addresses table
INSERT INTO Addresses (country, city, street, street_no, postal_code) VALUES
('Poland', 'Warsaw', 'Main Street', '123', '00-001'),
('Poland', 'Warsaw', 'Pulawska', '20', '02-515'),
('Poland', 'Warsaw', 'Marszalkowska', '45', '00-676'),
('Poland', 'Warsaw', 'Krakowskie Przedmiescie', '56', '00-322'),
('Poland', 'Piaseczno', 'Warszawska', '10', '05-500'),
('Poland', 'Konstancin-Jeziorna', 'Koscielna', '5', '05-510'),
('Poland', 'Warsaw', 'Nowy Swiat', '30', '00-029'),
('Poland', 'Warsaw', 'Plac Defilad', '1', '00-901'),
('Poland', 'Pruszkow', 'Glowna', '3', '05-800'),
('Poland', 'Legionowo', 'Polna', '6', '05-120'),
('Poland', 'Grodzisk Mazowiecki', 'Rynek', '7', '05-825');

-- Insert statements for the Licences table
INSERT INTO Licences (licence_level) VALUES
('No licence'),
('Licence Level 1'),
('Licence Level 2'),
('Licence Level 3'),
('Licence Level 4'),
('Licence Level 5'),
('No Coaching Licence'),
('Coaching Licence Level 1'),
('Coaching Licence Level 2'),
('Coaching Licence Level 3'),
('Coaching Licence Level 4'),
('Coaching Licence Level 5');

-- Insert statements for the Groups table
INSERT INTO `Groups` (name, max_group_members) VALUES
('Beginner Group', 10),
('Intermediate Group', 15),
('Advanced Group', 8);

-- Insert statements for the Horses table
INSERT INTO Horses (breed, height, color, eye_color, age, origin, hairstyle) VALUES
('Hucul', 150, 'Chestnut', 'Brown', 5, 'Poland', 'Mane'),
('Malopolski', 160, 'Gray', 'Blue', 8, 'Poland', 'Mane'),
('Friesian', 155, 'Black', 'Black', 7, 'Netherlands', 'Mane'),
('Polish', 145, 'Bay', 'Brown', 10, 'Poland', 'Mane'),
('Connemara', 148, 'Dark Bay', 'Green', 6, 'Ireland', 'Braided'),
('Anglo-Arab', 152, 'Chestnut', 'Brown', 9, 'Poland', 'Braided'),
('Wielkopolski', 165, 'Bay', 'Black', 4, 'Poland', 'Braided'),
('Dutch', 157, 'Gray', 'Brown', 12, 'Netherlands', 'Flowing'),
('Andalusian', 162, 'Gray', 'Blue', 6, 'Spain', 'Flowing'),
('Hanoverian', 154, 'Dark Bay', 'Brown', 11, 'Germany', 'Flowing'),
('Arabian', 150, 'White', 'Black', 5, 'Arabia', 'Flowing'),
('Thoroughbred', 158, 'Bay', 'Brown', 7, 'United Kingdom', 'Braided');

-- Insert statements for the AccessoryTypes table
INSERT INTO AccessoryTypes (type_name) VALUES
('Saddles'),
('Bridles'),
('Blankets');

-- Insert statements for the Accessories table
INSERT INTO Accessories (name, type_id) VALUES
('English Saddle', 1),
('Summer Blanket', 3),
('Winter Blanket', 3);

-- Insert statements for the Horses_Accessories table (example associations of accessories with horses)
INSERT INTO Horses_Accessories (horse_id, accessory_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Insert statements for the Stables table
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

-- Insert statements for the Positions table
INSERT INTO Positions (name, salary_min, salary_max, licence_id, coaching_licence_id, speciality) VALUES
('Trainer', 3000.00, 6000.00, 2, 7, 'Equestrianism'),
('Senior Trainer', 4000.00, 7000.00, 3, 8, 'Equestrianism'),
('Instructor', 5500.00, 9000.00, 4, 9, 'Equestrian Instructor'),
('Senior Instructor', 7500.00, 10000.00, 5, 10, 'Equestrian Instructor'),
('Director of Training', 9000.00, 12000.00, 6, 11, 'Equestrian Program Management'),
('Veterinarian', 4000.00, 8000.00, 1, 7, 'Animal Care'),
('Caretaker', 3000.00, 6000.00, 1, 7, 'Horse Care'),
('Administrator', 3500.00, 7000.00, 1, 7, 'Administration'),
('Coordinator', 3200.00, 6500.00, 1, 7, 'Project Coordination'),
('Technician', 2800.00, 5600.00, 1, 7, 'Technical Support'),
('IT Specialist', 3700.00, 7400.00, 1, 7, 'Information Technology'),
('Marketer', 3000.00, 6000.00, 1, 7, 'Marketing'),
('HR', 3300.00, 6600.00, 1, 7, 'Human Resources Management'),
('Owner', 10000, 14000, 6, 12, 'Management');

-- Insert statements for the Members table
INSERT INTO Members (name, surname, username, password, date_of_birth, address_id, phone_number, email, is_active, licence_id) VALUES
('Jacek', 'Lewandowski', 'jlewan', 'qwerty', '1982-07-21', 1, '321321123', 'jacek.lewandowski@example.com', TRUE, 1),
('Barbara', 'Kwiatkowska', 'bkwat', 'qwer', '1979-04-17', 2, '654987321', 'barbara.kwiatkowska@example.com', TRUE, 2),
('Michal', 'Kaczmarek', 'mkaczmar', '12345', '1991-12-03', 3, '789456123', 'michal.kaczmarek@example.com', TRUE, 1),
('Agnieszka', 'Dabrowska', 'adab', '09876', '1984-06-29', 4, '456123789', 'agnieszka.dabrowska@example.com', TRUE, 2),
('Karolina', 'Szymanska', 'kszyman', 'ytrewq', '1992-03-11', 5, '321456987', 'karolina.szymanska@example.com', TRUE, 5),
('Tomasz', 'Wisniewski', 'twis', 'password1', '1985-09-23', 6, '111222333', 'tomasz.wisniewski@example.com', TRUE, 3),
('Anna', 'Nowak', 'anowak', 'pass123', '1990-08-15', 7, '444555666', 'anna.nowak@example.com', TRUE, 4),
('Pawel', 'Zielinski', 'pziel', 'mypass', '1977-03-12', 8, '777888999', 'pawel.zielinski@example.com', TRUE, 5),
('Ewa', 'Majewska', 'emajew', 'password2', '1983-11-19', 9, '123123123', 'ewa.majewska@example.com', TRUE, 1),
('Jan', 'Kowalski', 'jkowal', 'letmein', '1987-05-25', 10, '234234234', 'jan.kowalski@example.com', TRUE, 2),
('Magdalena', 'Wojcik', 'mwojcik', 'securepass', '1993-02-17', 11, '345345345', 'magdalena.wojcik@example.com', TRUE, 1),
('Piotr', 'Jankowski', 'pjanko', 'piotrpass', '1981-04-30', 1, '456456456', 'piotr.jankowski@example.com', TRUE, 6),
('Katarzyna', 'Wozniak', 'kwozniak', 'wozniakpass', '1986-10-05', 2, '567567567', 'katarzyna.wozniak@example.com', TRUE, 2),
('Marek', 'Mazur', 'mmazur', 'mazurpass', '1995-07-14', 3, '678678678', 'marek.mazur@example.com', TRUE, 1),
('Joanna', 'Krawczyk', 'jkraw', 'krawpass', '1994-06-20', 4, '789789789', 'joanna.krawczyk@example.com', TRUE, 2),
('Rafal', 'Krol', 'rkrol', 'kingpass', '1989-01-01', 5, '890890890', 'rafal.krol@example.com', TRUE, 6),
('Sylwia', 'Ostrowska', 'sostro', 'ostropass', '1976-12-12', 6, '901901901', 'sylwia.ostrowska@example.com', TRUE, 5),
('Adam', 'Duda', 'aduda', 'dudapass', '1998-09-09', 7, '012012012', 'adam.duda@example.com', TRUE, 5),
('Maria', 'Pawlak', 'mpawlak', 'pawpass', '1997-03-03', 8, '345678123', 'maria.pawlak@example.com', TRUE, 4),
('Dariusz', 'Wlodarczyk', 'dwlodar', 'wlopass', '1988-10-10', 9, '456789234', 'dariusz.wlodarczyk@example.com', TRUE, 6),
('Alicja', 'Gorska', 'agorska', 'gorpass', '1982-11-11', 10, '567890345', 'alicja.gorska@example.com', TRUE, 5);

-- Insert statements for the Riders table
INSERT INTO Riders (member_id, parent_consent, group_id, horse_id) VALUES
(1, TRUE, 1, 1),
(2, TRUE, 2, 2),
(3, TRUE, 2, 4),
(4, TRUE, 2, 5),
(5, TRUE, 2, 6),
(6, TRUE, 1, 7),
(7, TRUE, 1, 8),
(8, TRUE, 1, 9),
(9, TRUE, 3, 10),
(10, TRUE, 3, 11),
(11, TRUE, 3, 12);

-- Insert statements for the Employees table
INSERT INTO Employees (member_id, position_id, salary, date_employed) VALUES
(12, 14, 13600.00, '2018-03-12'),
(13, 13, 3800.00, '2021-11-08'),
(14, 12, 4700.00, '2020-05-25'),
(15, 11, 4400.00, '2019-08-16'),
(16, 4, 8900.00, '2018-12-30'),
(17, 3, 6400.00, '2019-08-16'),
(18, 2, 4400.00, '2019-08-16'),
(19, 3, 6400.00, '2019-08-16'),
(20, 5, 11400.00, '2019-08-16'),
(21, 4, 8800.00, '2019-08-16');

-- Insert statements for the Classes table
INSERT INTO Classes (type, date, trainer_id, group_id, stable_id) VALUES
('Recreational Riding', '2025-06-01', 6, 1, 1),
('Sport Riding', '2025-06-02', 10, 2, 2),
('Dressage', '2025-06-03', 9, 3, 3),
('Show Jumping', '2025-06-04', 10, 2, 4),
('Cross Country', '2025-06-05', 5, 3, 5),
('Trail Riding', '2025-06-06', 6, 1, 1),
('Competition Trip', '2025-06-07', 10, 2, 2),
('Special Training', '2025-06-08', 9, 3, 3),
('Horse Exercises', '2025-06-09', 10, 2, 4),
('Training Clinic', '2025-06-10', 5, 3, 5);

-- Insert statements for the Tournaments table
INSERT INTO Tournaments (name, date, address_id, judge_id) VALUES
('Beginner Tournament', '2025-07-01', 1, 1),
('Intermediate Tournament', '2025-07-15', 2, 1);

-- Insert statements for the Tournament_Participants table
INSERT INTO Tournament_Participants (tournament_id, contestant_id, contestant_place) VALUES
(1, 1, NULL),
(1, 3, NULL),
(1, 4, NULL),
(1, 5, NULL),
(1, 6, NULL),
(1, 7, NULL),
(1, 8, NULL),
(2, 1, NULL),
(2, 2, NULL),
(2, 3, NULL);
