delimiter //
CREATE TRIGGER Trigger_Users_INSERT_UPDATE BEFORE UPDATE ON t_Users
    FOR EACH ROW
    BEGIN
        set NEW.LastModified = NOW();
    END;//
delimiter ;