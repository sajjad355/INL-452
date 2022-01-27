CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_id_from_name`(
  IN username VARCHAR(255), 
  out userid bigint)
BEGIN
  DECLARE done BOOLEAN DEFAULT FALSE;
  DECLARE curUser CURSOR FOR 
  SELECT user_id
  FROM somrusoft.il_user
  WHERE UPPER(name) = UPPER(username);
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  
  OPEN curUser;
  getUser: LOOP
	FETCH FROM curUser INTO  userid;
    IF done THEN 
		CLOSE curUser; 
		LEAVE getUser;
	END IF;
  END LOOP getUser;    
  
END