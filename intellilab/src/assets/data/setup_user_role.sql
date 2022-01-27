create
    definer = root@localhost procedure setup_user_role()
BEGIN
    ALTER TABLE il_operation AUTO_INCREMENT = 1;
    INSERT INTO il_user_role ( role_name ) VALUE ( 'Senior Manager' );
    INSERT INTO il_user_role ( role_name ) VALUE ( 'Manager' );
    INSERT INTO il_user_role ( role_name ) VALUE ( 'User' );
    INSERT INTO il_user_role ( role_name ) VALUE ( 'Administrator' );
END;

