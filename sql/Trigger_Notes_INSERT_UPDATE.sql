delimiter //
CREATE TRIGGER Trigger_Notes_INSERT_UPDATE BEFORE UPDATE ON t_Notes
    FOR EACH ROW
    BEGIN
        set NEW.LastModified = NOW();
    END;//
delimiter ;