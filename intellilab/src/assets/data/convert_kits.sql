CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_kits`()
BEGIN
  DECLARE done1, done2, done3 BOOLEAN DEFAULT FALSE; 
  
  DECLARE pSalesItemId bigint;
  DECLARE kBiosimilar varchar(255);
  DECLARE kCatalogNumber varchar(255);
  DECLARE kClient varchar(255);
  DECLARE kComment varchar(255);
  DECLARE kDescription varchar(255);
  DECLARE kEditStatus varchar(255);
  DECLARE kEditStatusComment varchar(255);
  DECLARE kEditStatusTime date;
  DECLARE kEditedBy varchar(255);
  DECLARE kEnteredBy varchar(255);
  DECLARE kEnteredTime date;
  DECLARE kKitSize varchar(255);
  DECLARE kMethod varchar(255);
  DECLARE kModifiedOn date;
  DECLARE kMolecular varchar(255);
  DECLARE kName varchar(255);
  DECLARE kPrice double;
  DECLARE kReviewedBy varchar(255);
  DECLARE kStatus varchar(255);
  DECLARE kKitId bigint;
  
  DECLARE kcAmount varchar(255); 
  DECLARE kcComponent varchar(255); 
  DECLARE kcComponentId varchar(255); 
  DECLARE kcIskit  varchar(255); 
  DECLARE iIskit int; 
  
  DECLARE kcKitId bigint; 
  DECLARE kcPackaging varchar(255); 
  DECLARE kcPartNumber varchar(255); 
  DECLARE kcReagent varchar(255); 
  DECLARE kcRecipeId varchar(255); 
  DECLARE kcRecipeName varchar(255); 
  DECLARE kcUnit varchar(255); 
  DECLARE kcVialDescription varchar(255); 
  DECLARE kcVialId varchar(255); 
  DECLARE kcVialSize varchar(255); 
  DECLARE kcEditby varchar(255);
  DECLARE kcModify date;
  
  DECLARE ilkcRecipeId bigint;
  


  DECLARE curKit CURSOR FOR 
	SELECT dbid,
           REPLACE(biosimilar, '\n', '') as biosimilar, 
           REPLACE(cat,  '\n', '') as cat, 
           REPLACE(clientspecific,  '\n', '') as clientspecific,
           REPLACE(comment,  '\n', '') as comment,
           REPLACE(description,  '\n', '') as description,
           REPLACE(editstatus,  '\n', '') as editstatus,
           NULLIF( REPLACE(enteredby,  '\n', ''), '' ) as enteredby,
           REPLACE(lastmodify,  '\n', '') as lastmodify,
           REPLACE(method,  '\n', '') as method,
           REPLACE(molecular, '\n', '') as molecular,
           REPLACE(name,  '\n', '') as name,
           cast(price as DECIMAL(8,2)) as price,
           REPLACE(size,  '\n', '') as size,
           REPLACE(status,  '\n', '') as status
    FROM somrusoft.kit;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;
  
  OPEN curKit;
  getKit: LOOP
	FETCH FROM curKit INTO  kKitId, kBiosimilar, kCatalogNumber, kClient, kComment, kDescription, kEditStatus, kEnteredBy, kModifiedOn, kMethod, kMolecular, kName, kPrice, kKitSize, kStatus;
	IF done1 THEN 
		CLOSE curKit; 
		LEAVE getKit;
	END IF;
    
    insert into somrusoft.il_sales_item(name, catalog_number, active, unit_price, edit_status, reviewed_by, edit_status_comment, edit_status_time, pack_size ) 
    values (kName, kCatalogNumber, 1, kPrice, 'Approved', null, 'Moin', Now(), kKitSize );
    SELECT LAST_INSERT_ID() into pSalesItemId;
    -- insert into il_kit
    insert into somrusoft.il_kit( biosimilar, 
                                  client, 
                                  comment, 
                                  description, 
                                  edited_by, 
                                  entered_by, 
                                  entered_time, 
                                  method, 
                                  modified_on, 
                                  molecular, 
                                  status, 
                                  kit_id,
                                  old_kit_id ) 
                                  values ( 
                                  kBiosimilar,
                                  kClient, 
                                  kComment, 
                                  kDescription, 
                                  IFNULL( kEnteredBy, 'peter'),
                                  IFNULL( kEnteredBy, 'peter'),
                                  kModifiedOn,
                                  kMethod,
                                  kModifiedOn,
                                  kMolecular, 
                                  kStatus,
                                  pSalesItemId,
                                  kKitId
                                  );
    
    -- get components for kit and insert into il_kit_component
    BLOCK1 : BEGIN
    DECLARE curKitComponents CURSOR FOR 
	SELECT REPLACE(amount, '\n', '') as amount, 
           REPLACE(component, '\n', '') as component,  
           REPLACE(componentid, '\n', '') as componentid,  
           REPLACE(iskit, '\n', '') as iskit, 
           REPLACE(packaging, '\n', '') as packaging, 
           REPLACE(partnumber, '\n', '') as partnumber,
           REPLACE(reagent, '\n', '') as reagent,
           REPLACE(recipeid, '\n', '') as recipeid,
           REPLACE(recipename, '\n', '') as recipename,
           REPLACE(unit, '\n', '') as unit,
           REPLACE(vialdescription, '\n', '') as vialdescription,
           REPLACE(vialid, '\n', '') as vialid,
           REPLACE(vialsize, '\n', '') as vialsize,
           NULLIF(REPLACE(editby, '\n', ''), '' ) as editby,
           IFNULL(modify, Now() ) as modifiy
    FROM somrusoft.kit_components where kitid = kKitId;  
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;
    
    OPEN curKitComponents;
	getKitComponents: LOOP
		FETCH FROM curKitComponents INTO kcAmount,
										 kcComponent,
                                         kcComponentId,
                                         kcIskit,
                                         kcPackaging,
                                         kcPartNumber,
                                         kcReagent,
                                         kcRecipeId,
                                         kcRecipeName,
                                         kcUnit,
                                         kcVialDescription,
                                         kcVialId,
                                         kcVialSize,
                                         kcEditby,
                                         kcModify;
        if done2 THEN
            CLOSE curKitComponents;
            SET done2 = FALSE;
            LEAVE getKitComponents;
        end if;
        
        if kcIskit = 'No' then
          SET iIsKit = 0;
        else
          SET iIsKit = 1;
        end if;
        
        -- insert into il_kit_component
        insert into somrusoft.il_kit_component( 
          amount, 
          catalog_number, 
          name, 
          edited_by, 
          modified_on, 
          packaging,          
          reagent, 
          unit, 
          kit_id
        ) values ( 
          kcAmount,
          kcComponentId,
		  kcComponent,
          IFNULL(kcEditby, 'peter' ),
          kcModify,
          kcPackaging,
          kcReagent,
          kcUnit,
          pSalesItemId);
                                         
	END LOOP getKitComponents;
    END BLOCK1;
  
  END LOOP getKit;
  
END