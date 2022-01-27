CREATE DEFINER=`root`@`localhost` PROCEDURE `get_location_id_from_name_and_parent`(
IN locationName VARCHAR(255), 
IN parentId bigint, 
OUT locationId bigint)
BEGIN
  DECLARE done BOOLEAN DEFAULT FALSE;
  DECLARE curLoc CURSOR FOR 
    SELECT location_id
  FROM somrusoft.il_location
  WHERE parent_location_id = parentId and
        UPPER(location_name) = UPPER(locationName);
        
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