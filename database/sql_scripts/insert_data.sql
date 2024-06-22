USE horses-database;

-- Inserty do tabeli Addresses
INSERT INTO Addresses (country, city, street, street_no, postal_code)
VALUES ('Poland', 'Warsaw', 'Main Street', '123', '00-001'),
       ('Germany', 'Berlin', 'Central Avenue', '456', '12345'),
       ('France', 'Paris', 'Rue de la Paix', '789', '75001');

-- Inserty do tabeli Licences
INSERT INTO Licences (licence_level)
VALUES ('Level 1'),
       ('Level 2'),
       ('Level 3');

-- Inserty do tabeli Groups
INSERT INTO `Groups` (name, max_group_members)
VALUES ('Beginners', 10),
       ('Intermediate', 8),
       ('Advanced', 6);


-- Inserty do tabeli Horses
INSERT INTO Horses (breed, height, color, eye_color, age, origin, hairstyle)
VALUES ('Thoroughbred', 170.5, 'Bay', 'Brown', 5, 'USA', 'Mane'),
       ('Quarter Horse', 160.0, 'Chestnut', 'Blue', 7, 'Mexico', 'Braided'),
       ('Arabian', 155.0, 'Gray', 'Black', 6, 'Saudi Arabia', 'Flowing');

-- Inserty do tabeli AccessoryTypes
INSERT INTO AccessoryTypes (type_name)
VALUES ('Saddles'),
       ('Bridles'),
       ('Blankets');

-- Inserty do tabeli Accessories
INSERT INTO Accessories (name, type_id)
VALUES ('English Saddle', 1),
       ('Western Bridle', 2),
       ('Winter Blanket', 3);

-- Inserty do tabeli Horses_Accessories (przykładowe powiązania akcesoriów z końmi)
INSERT INTO Horses_Accessories (horse_id, accessory_id)
VALUES (1, 1),  -- Thoroughbred with English Saddle
       (2, 2),  -- Quarter Horse with Western Bridle
       (3, 3);  -- Arabian with Winter Blanket

-- Inserty do tabeli Stables
INSERT INTO Stables (name, address_id)
VALUES ('Green Pastures Stable', 1),
       ('Blue Ridge Ranch', 2),
       ('Golden Meadows Farm', 3);

-- Inserty do tabeli Positions
INSERT INTO Positions (name, salary_min, salary_max, licence_id, coaching_licence_id, speciality)
VALUES ('Trainer', 2500.00, 3500.00, 1, 2, 'Show Jumping'),
       ('Assistant Trainer', 1800.00, 2500.00, 1, 2, 'General Training'),
       ('Groom', 1500.00, 1800.00, 1, 2, 'Horse Care');


-- Inserty do tabeli Members
INSERT INTO Members (name, surname, username, password, date_of_birth, address_id, phone_number, email, is_active, licence_id)
VALUES ('John', 'Doe', 'johndoe', 'hashedpassword', '1990-05-15', 1, '123456789', 'john.doe@example.com', TRUE, 2),
       ('Jane', 'Smith', 'janesmith', 'hashedpassword', '1995-08-22', 2, '987654321', 'jane.smith@example.com', TRUE, 1),
       ('Michael', 'Brown', 'michaelbrown', 'hashedpassword', '1988-12-10', 3, '555555555', 'michael.brown@example.com', TRUE, 3);

-- Inserty do tabeli Riders
INSERT INTO Riders (member_id, parent_consent, group_id, horse_id)
VALUES (1, TRUE, 1, 1),  -- John Doe as a rider in Beginners group with Thoroughbred
       (2, FALSE, 2, 2), -- Jane Smith as a rider in Intermediate group with Quarter Horse
       (3, TRUE, 3, 3);  -- Michael Brown as a rider in Advanced group with Arabian

-- Inserty do tabeli Employees
INSERT INTO Employees (member_id, position_id, salary, date_employed)
VALUES (1, 1, 3000.00, '2015-06-01'), -- John Doe as a Trainer
       (2, 2, 2000.00, '2018-01-15'), -- Jane Smith as an Assistant Trainer
       (3, 3, 1600.00, '2020-03-10'); -- Michael Brown as a Groom

-- Inserty do tabeli Positions_History
INSERT INTO Positions_History (employee_id, position_id, date_start, date_end)
VALUES (1, 1, '2015-06-01', NULL),  -- John Doe starting as a Trainer
       (2, 2, '2018-01-15', NULL),  -- Jane Smith starting as an Assistant Trainer
       (3, 3, '2020-03-10', NULL);  -- Michael Brown starting as a Groom

-- Inserty do tabeli Classes
INSERT INTO Classes (type, date, trainer_id, group_id, stable_id)
VALUES ('Jumping', '2023-07-10', 1, 1, 1),   -- Jumping class with John Doe as trainer, Beginners group, Green Pastures Stable
       ('Dressage', '2023-07-12', 1, 2, 2), -- Dressage class with no specific trainer, Intermediate group, Blue Ridge Ranch
       ('Cross-Country', '2023-07-15', 1, 3, 3); -- Cross-Country class with John Doe as trainer, Advanced group, Golden Meadows Farm

-- Inserty do tabeli Tournaments
INSERT INTO Tournaments (name, address_id, judge_id)
VALUES ('Summer Show', 1, 1),  -- Summer Show tournament at Green Pastures Stable, judged by John Doe
       ('Spring Cup', 2, 3),   -- Spring Cup tournament at Blue Ridge Ranch, judged by Michael Brown
       ('Winter Classic', 3, 2);  -- Winter Classic tournament at Golden Meadows Farm, no specific judge

-- Inserty do tabeli Tournament_Participants
INSERT INTO Tournament_Participants (tournament_id, contestant_id, contestant_place)
VALUES (1, 1, 2),   -- John Doe (member_id = 1) participated in Summer Show and placed 2nd
       (1, 2, 1),   -- Jane Smith (member_id = 2) participated in Summer Show and placed 1st
       (2, 3, 3);   -- Michael Brown (member_id = 3) participated in Spring Cup and placed 3rd
