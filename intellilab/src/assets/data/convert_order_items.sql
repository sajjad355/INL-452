CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_order_items`()
BEGIN
  DECLARE done1, done2  BOOLEAN DEFAULT FALSE; 
  DECLARE oiDbid bigint;
  DECLARE oiAmount double;
  DECLARE oiApproveperson varchar(255);
  DECLARE oiApprovepersonid varchar(255);
  DECLARE approvePersonId bigint;  
  DECLARE oiApprovetime date;
  DECLARE oiCat varchar(255);
  DECLARE oiCategory varchar(255);
  DECLARE oiComment varchar(5000);
  DECLARE oiEmailsendtime date;
  DECLARE oiEta date;
  DECLARE oiExist varchar(255);
  DECLARE oiGrantid varchar(255);
  DECLARE oiManufacturer varchar(255);
  DECLARE oiName varchar(255);
  DECLARE oiOrderperson varchar(255); 
  DECLARE oiOrderpersonid varchar(255);
  DECLARE orderPersonId bigint;
  DECLARE oiOrdertime date;
  DECLARE oiReceiveperson varchar(255);
  DECLARE oiReceivepersonid varchar(255);
  DECLARE receivePersonId bigint;
  DECLARE oiReceivetime date;
  DECLARE oiRequestperson varchar(255);
  DECLARE oiRequestpersonid varchar(255);
  DECLARE requestPersonId bigint;
  DECLARE oiRequesttime date;
  DECLARE oiReserve varchar(255);
  DECLARE oiReserveclientid varchar(255);
  DECLARE oiStatus varchar(255);
  DECLARE oiSupplier varchar(255);
  DECLARE oiSuppliercat varchar(255);
  DECLARE oiType varchar(255);
  DECLARE oiUnit varchar(255);
  DECLARE oiUnitprice double;
  DECLARE oiUnitsize varchar(255);
  DECLARE oiUnreceiveamount double;
  DECLARE oiUrgent int;
  
  DECLARE newClientId bigint;
  DECLARE newExist int;
  DECLARE newContainerSize double;
  
  DECLARE curOrderItems CURSOR FOR 
	   SELECT dbid, 
              cast(amount as DECIMAL(8,2)) as amount,
              approveperson, 
              approvepersonid, 
              approvetime, 
              cat, 
              category, 
              comment, 
              emailsendtime, 
              eta, 
              exist, 
              grantid, 
              manufacturer, 
              name, 
              orderperson, 
              orderpersonid,
              ordertime, 
              receiveperson, 
              receivepersonid,
              receivetime, 
              requestperson, 
              requestpersonid,
              requesttime, 
              reserve, 
              reserveclientid, 
              status, 
              supplier, 
              suppliercat, 
              type, 
              unit, 
              cast(unitprice as DECIMAL(8,2)) as unitprice,
              NULLIF( unitsize, '' ) as unitsize, 
              cast(unreceiveamount as DECIMAL(8,2)) as unreceiveamount,
              urgent
    FROM somrusoft.orderitem where name is not null;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;
     
  
  
  OPEN curOrderItems;
  getOrderItems: LOOP
	FETCH FROM curOrderItems INTO  
       oiDbid ,
	   oiAmount ,
	   oiApproveperson ,
	   oiApprovepersonid ,
	   oiApprovetime ,
	   oiCat ,
	   oiCategory ,
	   oiComment ,
	   oiEmailsendtime ,
	   oiEta ,
	   oiExist ,
	   oiGrantid ,
	   oiManufacturer ,
	   oiName ,
	   oiOrderperson , 
	   oiOrderpersonid ,
	   oiOrdertime ,
	   oiReceiveperson ,
	   oiReceivepersonid ,
	   oiReceivetime ,
	   oiRequestperson ,
	   oiRequestpersonid ,
	   oiRequesttime ,
	   oiReserve ,
	   oiReserveclientid ,
	   oiStatus ,
	   oiSupplier ,
	   oiSuppliercat ,
	   oiType ,
	   oiUnit ,
	   oiUnitprice ,
	   oiUnitsize ,
	   oiUnreceiveamount ,
	   oiUrgent;
    
    IF done1 THEN 
		CLOSE curOrderItems; 
		LEAVE getOrderItems;
	END IF;
    
    set newClientId = null;
    set newContainerSize = 0;
    
    if oiUnitsize is not null then 
      select cast(strip_non_digit(oiUnitsize) as decimal(11,4)) into newContainerSize; 
    end if;
        
    -- fetch new client id based on old one details
    BLOCK2 : BEGIN
    DECLARE curClient CURSOR FOR 
		select ilc.client_company_id 
		from somrusoft.client_company cc, somrusoft.il_client_company ilc
		where cc.company = ilc.company_name and cc.dbid = oiReserveclientid;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;
    
	OPEN curClient;
	getClient: LOOP

		FETCH FROM curClient INTO newClientId;
        if done2 THEN
            CLOSE curClient;
            SET done2 = FALSE;
            LEAVE getClient;
        end if;
        
	END LOOP getClient;
    END BLOCK2;
    
    -- fetch user ids if not present but names are
    set approvePersonId = null;
	set orderPersonId = null;
	set receivePersonId = null;
	set requestPersonId = null;
    
    if oiApproveperson is not null  then
	  CALL get_user_id_from_name( oiApproveperson, approvePersonId  );
	else 
      CALL get_new_user_id_from_old_user_id( cast( oiApprovepersonid as unsigned ), approvePersonId );
    end if;
    
    if oiOrderperson is not null then
	  CALL get_user_id_from_name( oiOrderperson, orderPersonId  );	
	else
      CALL get_new_user_id_from_old_user_id( cast( oiOrderpersonid as unsigned ), orderPersonId );
    end if;
    
    if oiReceiveperson is not null  then
	  CALL get_user_id_from_name( oiReceiveperson, receivePersonId  );	
	else
      CALL get_new_user_id_from_old_user_id( cast( oiReceivepersonid as unsigned ), receivePersonId  );
    end if;
    
    if oiRequestperson is not null then
      CALL get_user_id_from_name( oiRequestperson, requestPersonId  );	 
    else
      CALL get_new_user_id_from_old_user_id( cast( oiRequestpersonid as unsigned ), requestPersonId );
    end if;
    
    -- insert into new order item
    
    insert into somrusoft.il_order_item(
       amount, 
       approve_time, 
       back_ordered_amount, 
       catalog_number, 
       category, 
       comment, 
       edited_by, 
       eta, 
       manufacturer, 
       modified_on, 
       name, 
       order_time, 
       project, 
       receive_time, 
       request_time, 
       status, 
       supplier, 
       supplier_catalog_number, 
       type, 
       unit, 
       unit_price, 
       container_size, 
       urgent, 
       approve_user_id, 
       order_user_id, 
       recieve_user_id, 
       request_user_id, 
       reserve_client_id
    )
    values (   
	   oiAmount,
       oiApprovetime ,
       oiUnreceiveamount,
	   oiCat ,
       oiCategory ,
	   oiComment ,
       'peter',
	    oiEta ,
	   oiManufacturer ,
       Now(),
       oiName,
       oiOrdertime,
       oiGrantid ,
       oiReceivetime ,
       oiRequesttime ,
       oiStatus ,
	   oiSupplier ,
       oiSuppliercat ,
       oiType ,
	   oiUnit ,
       oiUnitprice,
       newContainerSize,
       oiUrgent,
       approvePersonId ,
	   orderPersonId ,
	   receivePersonId ,
	   requestPersonId ,
	   newClientId
    );
	
  END LOOP getOrderItems;
  
END