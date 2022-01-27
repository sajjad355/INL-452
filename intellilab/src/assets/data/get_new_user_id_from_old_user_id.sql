CREATE DEFINER=`root`@`localhost` PROCEDURE `get_new_user_id_from_old_user_id`(
  IN old_user_id bigint, 
  out new_user_id bigint)
BEGIN
  DECLARE done BOOLEAN DEFAULT FALSE;
  DECLARE curUser CURSOR FOR 
  SELECT iu.user_id
  FROM somrusoft.il_user iu, somrusoft.user u
  WHERE UPPER(iu.name) = UPPER(u.name) and
  u.dbid = old_user_id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  
  OPEN curUser;
  getUser: LOOP
	FETCH FROM curUser INTO  new_user_id;
    IF done THEN 
		CLOSE curUser; 
		LEAVE getUser;
	END IF;
  END LOOP getUser;  
END