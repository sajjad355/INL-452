CREATE DEFINER=`root`@`localhost` PROCEDURE `get_inventory_data`(
    IN oldInventoryId bigint, 
    OUT newInventoryId bigint,
    OUT newAmount bigint,
    OUT newNumberInUse bigint,
    OUT activeFlag int,
    OUT invUnit varchar(255)
    )
BEGIN
  DECLARE done BOOLEAN DEFAULT FALSE;
  DECLARE curInv CURSOR FOR 
  select inventory_id, amount, number_in_use, active, unit  
  from somrusoft.il_inventory where old_inventory_id = oldInventoryId;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  
  OPEN curInv;
  getInv: LOOP
	FETCH FROM curInv INTO  newInventoryId, newAmount, newNumberInUse, activeFlag, invUnit;
    IF done THEN 
		CLOSE curInv; 
		LEAVE getInv;
	END IF;
  END LOOP getInv;  
END