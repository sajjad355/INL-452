CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_products`()
BEGIN
  DECLARE done1, done2 BOOLEAN DEFAULT FALSE; 
  DECLARE pSalesItemId bigint;
  DECLARE pDbid bigint;
  DECLARE pApprovecomment varchar(255);
  DECLARE pApprovedby varchar(255);
  DECLARE pApprovetime date;
  DECLARE pBuffer varchar(255);
  DECLARE pCat varchar(255);
  DECLARE pClonality varchar(255);
  DECLARE pComment varchar(2000);
  DECLARE pDescription varchar(255);
  DECLARE pEnteredby varchar(255);
  DECLARE pEntertime date;
  DECLARE pHost varchar(255);
  DECLARE pImmunogen varchar(255);
  DECLARE pIsotype varchar(255);
  DECLARE pName varchar(255);
  DECLARE pOldcat varchar(255);
  DECLARE pPacksize varchar(255);
  DECLARE pPurification varchar(255);
  DECLARE pReconstitution varchar(255);
  DECLARE pReviewcomment varchar(255);
  DECLARE pReviewedby varchar(255);
  DECLARE pReviewtime date;
  DECLARE pSpecificity varchar(255);
  DECLARE pStatus varchar(255);
  DECLARE pStorage varchar(255);
  DECLARE pType varchar(255);
  DECLARE pUnit varchar(255);
  DECLARE dUnitPrice double;
  DECLARE dUnitSize double;
  
  DECLARE aComment varchar(255);
  DECLARE aNote varchar(5000);
  DECLARE aProductdbid bigint; 
  DECLARE aPurpose varchar(255);
  DECLARE aRecommenconcentration varchar(255); 
  
  
  DECLARE curProducts CURSOR FOR 
	SELECT dbid,
           REPLACE(approvecomment, "\n", "") as approvecomment, 
           REPLACE(approvedby,  "\n", "") as approvedby,
           approvetime,
           REPLACE(buffer,  "\n", "") as buffer,
           REPLACE(cat,  "\n", "") as cat,
           REPLACE(clonality,  "\n", "") as clonality,
           REPLACE(comment,  "\n", "") as comment,
           REPLACE(description,  "\n", "") as description,
           REPLACE(enteredby,  "\n", "") as enteredby,
           entertime,
           REPLACE(host,  "\n", "") as host,
           REPLACE(immunogen, "\n", "") as immunogen,
           REPLACE(isotype,  "\n", "") as isotype,
           REPLACE(name,  "\n", "") as name,
           REPLACE(oldcat,  "\n", "") as oldcat,
		   REPLACE(packsize,  "\n", "") as packsize,
		   REPLACE(purification,  "\n", "") as purification,
           REPLACE(reconstitution,  "\n", "") as reconstitution,
           REPLACE(reviewcomment,  "\n", "") as reviewcomment,
           REPLACE(reviewedby,  "\n", "") as reviewedby,
           reviewtime,
           REPLACE(specificity,  "\n", "") as specificity,
           REPLACE(status,  "\n", "") as status,
		   REPLACE(storage,  "\n", "") as storage,
           REPLACE(type,  "\n", "") as type,
		   REPLACE(unit,  "\n", "") as unit ,
		   cast(unitprice as DECIMAL(8,2)) as unitprice ,
           cast(unitsize as DECIMAL(8,2)) as unitsize
	FROM somrusoft.product;
    
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;
  
  OPEN curProducts;
  getProducts: LOOP
  
  
	FETCH FROM curProducts INTO   
	   pDbid,
	   pApprovecomment ,
	   pApprovedby ,
	   pApprovetime ,
	   pBuffer ,
	   pCat ,
	   pClonality ,
	   pComment,
	   pDescription ,
	   pEnteredby ,
	   pEntertime ,
	   pHost ,
	   pImmunogen ,
	   pIsotype ,
	   pName ,
	   pOldcat ,
	   pPacksize ,
	   pPurification ,
	   pReconstitution ,
	   pReviewcomment ,
	   pReviewedby ,
	   pReviewtime ,
	   pSpecificity ,
	   pStatus ,
	   pStorage ,
	   pType ,
	   pUnit ,
	   dUnitPrice ,
	   dUnitSize;   
    
    
    IF done1 THEN 
		CLOSE curProducts; 
		LEAVE getProducts;
	END IF;
    
    
    
    
    insert into somrusoft.il_sales_item(name, catalog_number, active, unit_price, edit_status, reviewed_by, edit_status_comment, edit_status_time, pack_size ) 
    values ( pName , pCat, 1, dUnitPrice, 'Approved', pReviewedby, pReviewcomment , pReviewtime,  pPacksize  );
    SELECT LAST_INSERT_ID() into pSalesItemId;
    insert into somrusoft.il_product(
      buffer, 
      clonality, 
      comment, 
      description, 
      edited_by, 
      entered_by, 
      entered_time, 
      immunogen, 
      host, 
      isotype, 
      modified_on, 
      purification, 
      reconstitution, 
      specificity, 
      storage, 
      type, 
      unit, 
      unit_size, 
      product_id,
      old_product_id
    ) values (
      pBuffer,  
      pClonality, 
      pComment, 
      pDescription, 
      'peter', 
      pEnteredby, 
      pEntertime , 
      pImmunogen, 
      pHost , 
      pIsotype , 
      Now(), 
      pPurification ,
      pReconstitution ,
      pSpecificity ,
      pStorage ,
      pType ,
      pUnit, 
	  dUnitSize, 
      pSalesItemId,
      pDbid );
      
        -- get all the applications
		BLOCK1 : BEGIN
		DECLARE curApplicationss CURSOR FOR 
		SELECT comment, note,  purpose, recommenconcentration from somrusoft.application
        where productdbid = pDbid;
		 
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;
		
		OPEN curApplicationss;
		getApplications: LOOP
          
			FETCH FROM curApplicationss INTO aComment,aNote,aPurpose,aRecommenconcentration;
			if done2 THEN
				CLOSE curApplicationss;
				SET done2 = FALSE;
				LEAVE getApplications;
			end if;
			
			insert into somrusoft.il_application( comment, edited_by, modified_on, note, purpose, recommended_concentration, product_id)
			values ( aComment, 'peter', Now(), aNote, aPurpose, aRecommenconcentration, pSalesItemId );
			
			
		END LOOP getApplications;
		END BLOCK1;

    
   END LOOP getProducts; 
END