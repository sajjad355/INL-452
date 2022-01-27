CREATE DEFINER=`root`@`localhost` PROCEDURE `get_location_id_from_name`(
 IN locationName VARCHAR(255), 
 out locationId bigint)
BEGIN
  DECLARE done BOOLEAN DEFAULT FALSE;
  DECLARE curLoc CURSOR FOR 
    SELECT location_id
  FROM somrusoft.il_location
  WHERE UPPER(location_name) = UPPER(locationName);
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  
  OPEN curLoc;
  getLoc: LOOP
	FETCH FROM curLoc INTO  locationId;
    IF done THEN 
		CLOSE curLoc; 
		LEAVE getLoc;
	END IF;
  END LOOP getLoc;
  
END