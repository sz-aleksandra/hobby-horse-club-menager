USE horses-database;

DELIMITER //

CREATE TRIGGER before_insert_addresses
BEFORE INSERT ON Addresses
FOR EACH ROW
BEGIN
    IF LEFT(NEW.country, 1) = 'A' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Country cannot start with A', MYSQL_ERRNO = 1001;
    ELSEIF LEFT(NEW.country, 1) = 'B' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Country cannot start with B', MYSQL_ERRNO = 1002;
    END IF;
END//

DELIMITER ;

