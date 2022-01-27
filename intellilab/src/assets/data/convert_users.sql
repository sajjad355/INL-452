CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_users`()
BEGIN
  DECLARE done1, done2, done3 BOOLEAN DEFAULT FALSE;
  DECLARE uDbid bigint;
  DECLARE uEmail varchar(255);
  DECLARE uName varchar(255);
  DECLARE uPassword varchar(255);
  DECLARE uRole varchar(255);
  DECLARE uActive int;
  DECLARE uInvalidlogincount bigint;
  DECLARE uRoleId bigint;

  DECLARE curUsers CURSOR FOR
	SELECT
      dbid, email, name, password, role, active, invalidlogincount
    FROM somrusoft.user;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;

  OPEN curUsers;
  getUsers: LOOP
	FETCH FROM curUsers INTO
         uDbid,
         uEmail,
         uName,
         uPassword,
		 uRole,
		 uActive,
         uInvalidlogincount;
    IF done1 THEN
		CLOSE curUsers;
		LEAVE getUsers;
	END IF;

    -- insert user
    insert into somrusoft.il_user(
      user_id, active, edited_by, email, invalidlogincount, modified_on, name, password
    )
    values (
      uDbid, uActive, 'peter', uEmail, uInvalidlogincount,  Now(), uName, uPassword
    );
    -- inser their role


    if uRole = 'Senior Manager' then
      set uRoleId = 1;
    elseif uRole = 'Manager' then
      set uRoleId = 2;
    elseif uRole = 'User' then
      set uRoleId = 3;
    else
      set uRoleId = 4;
    end if;

    insert into somrusoft.il_user_user_role(
      user_id, user_role_id
    ) values (
      uDbid, uRoleId
    );
  END LOOP getUsers;


END
