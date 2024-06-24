USE horses-database;

DELIMITER //

CREATE TRIGGER before_insert_horses
    BEFORE INSERT ON Horses
    FOR EACH ROW
BEGIN
    IF NEW.height <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Height must be greater than 0', MYSQL_ERRNO = 1101;
    END IF;

    IF NEW.age <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Age must be greater than 0', MYSQL_ERRNO = 1102;
    END IF;
END//

CREATE TRIGGER before_insert_members
    BEFORE INSERT ON Members
    FOR EACH ROW
BEGIN
    DECLARE username_count INT;

    SELECT COUNT(*)
    INTO username_count
    FROM Members
    WHERE username = NEW.username;

    IF username_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Username must be unique', MYSQL_ERRNO = 1201;
    END IF;

    IF NEW.date_of_birth >= CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Date of birth must be in the past', MYSQL_ERRNO = 1202;
    END IF;
END//

CREATE TRIGGER before_insert_employees
    BEFORE INSERT ON Employees
    FOR EACH ROW
BEGIN
    DECLARE min_salary DECIMAL(10, 2);
    DECLARE max_salary DECIMAL(10, 2);

    SELECT salary_min, salary_max
    INTO min_salary, max_salary
    FROM Positions
    WHERE id = NEW.position_id;

    IF NEW.salary < min_salary OR NEW.salary > max_salary THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Salary must be within the defined range for the position', MYSQL_ERRNO = 1301;
    END IF;
END//

CREATE TRIGGER before_insert_positions
    BEFORE INSERT ON Positions
    FOR EACH ROW
BEGIN
    IF NEW.salary_min > NEW.salary_max THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Minimum salary must not exceed maximum salary', MYSQL_ERRNO = 1401;
    END IF;
END//

CREATE TRIGGER before_insert_classes
    BEFORE INSERT ON Classes
    FOR EACH ROW
BEGIN
    IF NEW.date <= CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Class date must be in the future', MYSQL_ERRNO = 1501;
    END IF;
END//

CREATE TRIGGER before_insert_tournament_participants
    BEFORE INSERT ON Tournament_Participants
    FOR EACH ROW
BEGIN
    IF NEW.contestant_place IS NOT NULL AND NEW.contestant_place <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Contestant place must be greater than 0 if specified', MYSQL_ERRNO = 1601;
    END IF;
END//

CREATE TRIGGER before_insert_horses_accessories
    BEFORE INSERT ON Horses_Accessories
    FOR EACH ROW
BEGIN
    DECLARE accessory_exists INT;
    DECLARE horse_exists INT;

    SELECT COUNT(*)
    INTO accessory_exists
    FROM Accessories
    WHERE id = NEW.accessory_id;

    SELECT COUNT(*)
    INTO horse_exists
    FROM Horses
    WHERE id = NEW.horse_id;

    IF accessory_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Accessory does not exist', MYSQL_ERRNO = 1701;
    END IF;

    IF horse_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Horse does not exist', MYSQL_ERRNO = 1702;
    END IF;
END//

CREATE TRIGGER before_insert_positions_history
    BEFORE INSERT ON Positions_History
    FOR EACH ROW
BEGIN
    IF NEW.date_end IS NOT NULL AND NEW.date_end <= NEW.date_start THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Date end must be later than date start', MYSQL_ERRNO = 1801;
    END IF;
END//

CREATE TRIGGER before_insert_groups
    BEFORE INSERT ON `Groups`
    FOR EACH ROW
BEGIN
    IF NEW.max_group_members <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Max group members must be greater than 0', MYSQL_ERRNO = 1901;
    END IF;
END//


-- Trigger for INSERT operation on Employees table
CREATE TRIGGER after_employee_insert
AFTER INSERT ON Employees
FOR EACH ROW
BEGIN
    INSERT INTO Positions_History (employee_id, position_id, date_start)
    VALUES (NEW.id, NEW.position_id, NEW.date_employed);
END//

-- Trigger for UPDATE operation on Employees table
CREATE TRIGGER after_employee_update
AFTER UPDATE ON Employees
FOR EACH ROW
BEGIN
    IF NEW.position_id != OLD.position_id THEN
        -- Update the end date of the previous position history record
        UPDATE Positions_History
        SET date_end = NEW.date_employed
        WHERE employee_id = OLD.id AND position_id = OLD.position_id AND date_end IS NULL;

        -- Insert a new position history record for the new position
        INSERT INTO Positions_History (employee_id, position_id, date_start)
        VALUES (NEW.id, NEW.position_id, NEW.date_employed);
    END IF;
END//

CREATE TRIGGER before_tournament_participant_insert
BEFORE INSERT ON Tournament_Participants
FOR EACH ROW
BEGIN
    DECLARE consent BOOLEAN;

    -- Pobranie wartości parent_consent dla danego uczestnika
    SELECT parent_consent INTO consent
    FROM Riders
    WHERE id = NEW.contestant_id;

    -- Sprawdzenie, czy participant ma zgodę rodziców
    IF consent = FALSE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Participant does not have parent consent.', MYSQL_ERRNO = 2001;
    END IF;
END//


DELIMITER ;
