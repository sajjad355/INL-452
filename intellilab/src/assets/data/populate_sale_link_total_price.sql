CREATE DEFINER=`root`@`localhost` PROCEDURE `populate_sale_link_total_price`()
BEGIN
 DECLARE finished INTEGER DEFAULT 0;
 DECLARE totalPrice double DEFAULT 0;
 DECLARE itemDbid bigint;
 DECLARE itemDiscount double DEFAULT 0;
 DECLARE itemQuantity double DEFAULT 0;
 DECLARE itemPrice double DEFAULT 0;
 
 

-- declare cursor for sale link
 DECLARE curSaleLink 
 CURSOR FOR 
	SELECT dbid,
           COALESCE(item_discount, 0) AS item_discount, 
           COALESCE(item_quan, 0) as item_quan, 
           COALESCE(price, 0 ) as price
    FROM somrusoft.sale_link;
    
  -- declare NOT FOUND handler
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
  
  
  OPEN curSaleLink;

  getSaleLink: LOOP
	FETCH curSaleLink INTO itemDbid, itemDiscount, itemQuantity, itemPrice;
	IF finished = 1 THEN 
		LEAVE getSaleLink;
	END IF;
	-- build email list
	SET totalPrice = (1-(itemDiscount/100)) * (itemPrice * itemQuantity);
	
    UPDATE somrusoft.sale_link set total_price = ROUND(totalPrice, 2)
    where dbid = itemDbid;
    
  END LOOP getSaleLink;
  CLOSE curSaleLink;

END