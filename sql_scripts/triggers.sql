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
END //

CREATE TRIGGER before_update_horses
    BEFORE UPDATE ON Horses
    FOR EACH ROW
BEGIN
    IF NEW.height <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Height must be greater than 0', MYSQL_ERRNO = 1101;
    END IF;

    IF NEW.age <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Age must be greater than 0', MYSQL_ERRNO = 1102;
    END IF;
END //

CREATE TRIGGER before_insert_members
    BEFORE INSERT ON Members
    FOR EACH ROW
BEGIN
    DECLARE username_count INT;
    DECLARE normalized_phone VARCHAR(20);

    IF NEW.phone_number IS NOT NULL THEN
        SET normalized_phone = REPLACE(REPLACE(REPLACE(REPLACE(NEW.phone_number, ' ', ''), '-', ''), '(', ''), ')', '');
        IF LEFT(normalized_phone, 3) = '+48' THEN
            SET normalized_phone = RIGHT(normalized_phone, LENGTH(normalized_phone) - 3);
        END IF;
        SET NEW.phone_number = normalized_phone;
    END IF;

    SELECT COUNT(*)
    INTO username_count
    FROM Members
    WHERE username = NEW.username;

    IF username_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Username must be unique', MYSQL_ERRNO = 1201;
    END IF;

    IF NEW.email IS NOT NULL AND INSTR(NEW.email, '@') = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Email must contain "@" symbol', MYSQL_ERRNO = 1202;
    END IF;

    IF NEW.date_of_birth >= CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Date of birth must be in the past', MYSQL_ERRNO = 1203;
    END IF;
END //

CREATE TRIGGER before_update_members
    BEFORE UPDATE ON Members
    FOR EACH ROW
BEGIN
    DECLARE username_count INT;
    DECLARE normalized_phone VARCHAR(20);

    IF NEW.phone_number IS NOT NULL THEN
        SET normalized_phone = REPLACE(REPLACE(REPLACE(REPLACE(NEW.phone_number, ' ', ''), '-', ''), '(', ''), ')', '');
        IF LEFT(normalized_phone, 3) = '+48' THEN
            SET normalized_phone = RIGHT(normalized_phone, LENGTH(normalized_phone) - 3);
        END IF;
        SET NEW.phone_number = normalized_phone;
    END IF;

    SELECT COUNT(*)
    INTO username_count
    FROM Members
    WHERE username = NEW.username
    AND id != NEW.id;

    IF username_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Username must be unique', MYSQL_ERRNO = 1201;
    END IF;

    IF NEW.email IS NOT NULL AND INSTR(NEW.email, '@') = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Email must contain "@" symbol', MYSQL_ERRNO = 1202;
    END IF;

    IF NEW.date_of_birth >= CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Date of birth must be in the past', MYSQL_ERRNO = 1203;
    END IF;
END //

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

    IF NEW.date_employed > CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Start date cannot be in the future', MYSQL_ERRNO = 1302;
    END IF;
END //

CREATE TRIGGER before_update_employees
    BEFORE UPDATE ON Employees
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

    IF NEW.date_employed > CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Start date cannot be in the future', MYSQL_ERRNO = 1302;
    END IF;
END //

CREATE TRIGGER before_insert_positions
    BEFORE INSERT ON Positions
    FOR EACH ROW
BEGIN
    IF NEW.salary_min > NEW.salary_max THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Minimum salary must not exceed maximum salary', MYSQL_ERRNO = 1401;
    END IF;
END //

CREATE TRIGGER before_update_positions
    BEFORE UPDATE ON Positions
    FOR EACH ROW
BEGIN
    IF NEW.salary_min > NEW.salary_max THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Minimum salary must not exceed maximum salary', MYSQL_ERRNO = 1401;
    END IF;
END //

CREATE TRIGGER before_insert_classes
    BEFORE INSERT ON Classes
    FOR EACH ROW
BEGIN
    IF NEW.date <= CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Class date must be in the future', MYSQL_ERRNO = 1501;
    END IF;
END //

CREATE TRIGGER before_insert_tournament_participants
    BEFORE INSERT ON Tournament_Participants
    FOR EACH ROW
BEGIN
    IF NEW.contestant_place IS NOT NULL AND NEW.contestant_place <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Contestant place must be greater than 0 if specified', MYSQL_ERRNO = 1601;
    END IF;
END //

CREATE TRIGGER before_update_tournament_participants
    BEFORE UPDATE ON Tournament_Participants
    FOR EACH ROW
BEGIN
    IF NEW.contestant_place IS NOT NULL AND NEW.contestant_place <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Contestant place must be greater than 0 if specified', MYSQL_ERRNO = 1601;
    END IF;
END //

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
END //

CREATE TRIGGER before_update_horses_accessories
    BEFORE UPDATE ON Horses_Accessories
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
END //

CREATE TRIGGER before_insert_positions_history
    BEFORE INSERT ON Positions_History
    FOR EACH ROW
BEGIN
    IF NEW.date_end IS NOT NULL AND NEW.date_end <= NEW.date_start THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Date end must be later than date start', MYSQL_ERRNO = 1801;
    END IF;
END //

CREATE TRIGGER before_update_positions_history
    BEFORE UPDATE ON Positions_History
    FOR EACH ROW
BEGIN
    IF NEW.date_end IS NOT NULL AND NEW.date_end <= NEW.date_start THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Date end must be later than date start', MYSQL_ERRNO = 1801;
    END IF;
END //

CREATE TRIGGER before_insert_groups
    BEFORE INSERT ON `Groups`
    FOR EACH ROW
BEGIN
    IF NEW.max_group_members <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Max group members must be greater than 0', MYSQL_ERRNO = 1901;
    END IF;
END //

CREATE TRIGGER before_update_groups
    BEFORE UPDATE ON `Groups`
    FOR EACH ROW
BEGIN
    IF NEW.max_group_members <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Max group members must be greater than 0', MYSQL_ERRNO = 1901;
    END IF;
END //

CREATE TRIGGER before_insert_tournaments
    BEFORE INSERT ON Tournaments
    FOR EACH ROW
BEGIN
    DECLARE judge_coaching_licence_level INT;

    SELECT coaching_licence_id
    INTO judge_coaching_licence_level
    FROM Employees e
    JOIN Positions p
    ON e.position_id = p.id
    WHERE e.id = NEW.judge_id;

    IF judge_coaching_licence_level < 11 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Judge must have a Coaching Licence Level of 4 or higher', MYSQL_ERRNO = 2001;
    END IF;
END //

CREATE TRIGGER before_update_tournaments
    BEFORE UPDATE ON Tournaments
    FOR EACH ROW
BEGIN
    DECLARE judge_coaching_licence_level INT;

    SELECT coaching_licence_id
    INTO judge_coaching_licence_level
    FROM Employees e
    JOIN Positions p
    ON e.position_id = p.id
    WHERE e.id = NEW.judge_id;

    IF judge_coaching_licence_level < 11 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Judge must have a Coaching Licence Level of 4 or higher', MYSQL_ERRNO = 2001;
    END IF;
END //

CREATE TRIGGER before_insert_addresses
    BEFORE INSERT ON Addresses
    FOR EACH ROW
BEGIN
    IF NOT (NEW.postal_code REGEXP '^[0-9]{2}-[0-9]{3}$') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Postal code must be in the format 00-000', MYSQL_ERRNO = 2101;
    END IF;
END //

CREATE TRIGGER before_update_addresses
    BEFORE UPDATE ON Addresses
    FOR EACH ROW
BEGIN
    IF NOT (NEW.postal_code REGEXP '^[0-9]{2}-[0-9]{3}$') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Postal code must be in the format 00-000', MYSQL_ERRNO = 2101;
    END IF;
END //

CREATE TRIGGER after_employee_insert
    AFTER INSERT ON Employees
    FOR EACH ROW
BEGIN
    INSERT INTO Positions_History (employee_id, position_id, date_start)
    VALUES (NEW.id, NEW.position_id, NEW.date_employed);
END //

CREATE TRIGGER after_employee_update
    AFTER UPDATE ON Employees
    FOR EACH ROW
BEGIN
    IF NEW.position_id != OLD.position_id THEN
        UPDATE Positions_History
        SET date_end = NEW.date_employed
        WHERE employee_id = OLD.id AND position_id = OLD.position_id AND date_end IS NULL;

        INSERT INTO Positions_History (employee_id, position_id, date_start)
        VALUES (NEW.id, NEW.position_id, NEW.date_employed);
    END IF;
END //

CREATE TRIGGER before_tournament_participant_insert
    BEFORE INSERT ON Tournament_Participants
    FOR EACH ROW
BEGIN
    DECLARE consent BOOLEAN;

    SELECT parent_consent INTO consent
    FROM Riders
    WHERE id = NEW.contestant_id;

    IF consent = FALSE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Participant does not have parent consent.', MYSQL_ERRNO = 2001;
    END IF;
END //

DELIMITER ;
