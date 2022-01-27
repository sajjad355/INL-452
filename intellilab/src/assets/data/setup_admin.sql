CREATE
    DEFINER = root@localhost PROCEDURE setup_admin()
BEGIN
    -- Create an administrator account
    INSERT INTO il_user
        ( active, edited_by, email, invalidlogincount, modified_on, name, password )
    VALUES (
            1,
            'Administrator',
            'admin@somrubioscience.com',
            0,
            CURRENT_TIMESTAMP(),
            'Administrator',
            'WEAK_ADMIN_PASSWORD'
           );

    -- Get the administrator account's ID
    SELECT user_id FROM il_user WHERE password = 'WEAK_ADMIN_PASSWORD' INTO @adminID;

    -- Get the administrator role's ID
    SELECT user_role_id FROM il_user_role WHERE role_name = 'Administrator' INTO @adminRoleID;

    -- Assign the administrator role to the administrator account
    INSERT INTO il_user_user_role ( user_id, user_role_id ) VALUES ( @adminID, @adminRoleID );

    -- Get number of operations
    SELECT COUNT(*) FROM il_operation INTO @operation_count;

    -- Assign the administrator role to all operations
    SET @i = 1;
    mapping_loop: LOOP
        IF @i > @operation_count THEN LEAVE mapping_loop; END IF;
        INSERT INTO il_role_operation_mapping ( user_role_id, operation_id ) VALUES ( @adminRoleID, @i );
        SET @i = @i +1;
    END LOOP;
END;
