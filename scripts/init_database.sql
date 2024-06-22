DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE Addresses (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        country VARCHAR(255) NOT NULL,
                        city VARCHAR(255) NOT NULL,
                        street VARCHAR(255) NOT NULL,
                        street_no VARCHAR(50) NOT NULL,
                        postal_code VARCHAR(20) NOT NULL
);


CREATE TABLE Licences (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        licence_level VARCHAR(255) NOT NULL
);


CREATE TABLE Groups (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        max_group_members INT NOT NULL,
);

CREATE TABLE Horses (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        breed VARCHAR(255) NOT NULL,
                        height DECIMAL(5, 2) NOT NULL,
                        color VARCHAR(50) NOT NULL,
                        eye_color VARCHAR(50) NOT NULL,
                        age INT NOT NULL,
                        origin VARCHAR(255) NOT NULL,
                        hairstyle VARCHAR(255) NOT NULL,
);

CREATE TABLE AccessoryTypes (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        type_name VARCHAR(255) NOT NULL
);


CREATE TABLE Accessories (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        type_id INT NOT NULL,
                        FOREIGN KEY (type_id) REFERENCES AccessoryTypes(id)
);


CREATE TABLE Horses_Accessories (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        horse_id INT NOT NULL,
                        accessory_id INT NOT NULL,
                        FOREIGN KEY (horse_id) REFERENCES Horses(id) ON DELETE CASCADE,
                        FOREIGN KEY (accessory_id) REFERENCES Accessories(id) ON DELETE CASCADE
);

CREATE TABLE Stables (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        address_id INT NOT NULL,
                        FOREIGN KEY (address_id) REFERENCES Addresses(id) ON DELETE RESTRICT
);

CREATE TABLE Positions (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        salary_min DECIMAL(10, 2) NOT NULL,
                        salary_max DECIMAL(10, 2) NOT NULL,
                        licence_id INT NOT NULL,
                        coaching_licence_id INT NOT NULL,
                        speciality VARCHAR(255),
                        FOREIGN KEY (licence_id) REFERENCES Licences(id) ON DELETE RESTRICT,
                        FOREIGN KEY (coaching_licence_id) REFERENCES Licences(id) ON DELETE RESTRICT
);

CREATE TABLE Members (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        surname VARCHAR(255) NOT NULL,
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        date_of_birth DATE NOT NULL,
                        address_id INT NOT NULL,
                        phone_number VARCHAR(20),
                        email VARCHAR(255),
                        is_active BOOLEAN NOT NULL DEFAULT TRUE,
                        licence_id INT NOT NULL,
                        FOREIGN KEY (address_id) REFERENCES Addresses(id) ON DELETE RESTRICT,
                        FOREIGN KEY (licence_id) REFERENCES Licences(id) ON DELETE RESTRICT
);

CREATE TABLE Riders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        member_id INT NOT NULL,
                        parent_consent BOOLEAN NOT NULL,
                        group_id INT NOT NULL,
                        horse_id INT NOT NULL,
                        FOREIGN KEY (member_id) REFERENCES Members(id) ON DELETE CASCADE,
                        FOREIGN KEY (group_id) REFERENCES Groups(id) ON DELETE RESTRICT,
                        FOREIGN KEY (horse_id) REFERENCES Horses(id) ON DELETE RESTRICT
);

CREATE TABLE Employees (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        member_id INT NOT NULL,
                        position_id INT NOT NULL,
                        salary DECIMAL(10, 2) NOT NULL,
                        date_employed DATE NOT NULL,
                        FOREIGN KEY (member_id) REFERENCES Members(id) ON DELETE CASCADE,
                        FOREIGN KEY (position_id) REFERENCES Positions(id) ON DELETE RESTRICT
);


CREATE TABLE Positions_History (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        employee_id INT NOT NULL,
                        position_id INT NOT NULL,
                        date_start DATE NOT NULL,
                        date_end DATE,
                        FOREIGN KEY (employee_id) REFERENCES Employees(id) ON DELETE CASCADE,
                        FOREIGN KEY (position_id) REFERENCES Positions(id) ON DELETE CASCADE
);


CREATE TABLE Classes (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        type VARCHAR(255) NOT NULL,
                        date DATE NOT NULL,
                        trainer_id INT,
                        group_id INT,
                        stable_id INT,
                        FOREIGN KEY (trainer_id) REFERENCES Employees(id) ON DELETE RESTRICT,
                        FOREIGN KEY (group_id) REFERENCES Groups(id) ON DELETE CASCADE,
                        FOREIGN KEY (stable_id) REFERENCES Stables(id) ON DELETE CASCADE
);


CREATE TABLE Tournaments (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        address_id INT NOT NULL,
                        judge_id INT,
                        FOREIGN KEY (address_id) REFERENCES Addresses(id) ON DELETE RESTRICT,
                        FOREIGN KEY (judge_id) REFERENCES Employees(id) ON DELETE RESTRICT
);

CREATE TABLE Tournament_Participants (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        tournament_id INT NOT NULL,
                        contestant_id INT NOT NULL,
                        contestant_place INT,
                        FOREIGN KEY (tournament_id) REFERENCES Tournaments(id) ON DELETE CASCADE,
                        FOREIGN KEY (contestant_id) REFERENCES Members(id) ON DELETE CASCADE
);
