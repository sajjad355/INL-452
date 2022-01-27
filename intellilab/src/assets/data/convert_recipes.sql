CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_recipes`()
BEGIN
  DECLARE done1, done2 BOOLEAN DEFAULT FALSE;

  DECLARE rRecipeId bigint;
  DECLARE rDbid bigint;
  DECLARE rSomruid varchar(255);
  DECLARE rName varchar(255);
  DECLARE rDescription varchar(255);
  DECLARE rDirection varchar(5000);
  DECLARE rEnteredby varchar(255);
  DECLARE rReviewedby varchar(255);
  DECLARE rApprovedby varchar(255);
  DECLARE rApprovecomment varchar(255);
  DECLARE rEditstatus varchar(255);
  DECLARE rReviewcomment varchar(255);
  DECLARE rUnit varchar(255);
  DECLARE rVolumn varchar(255);
  DECLARE rApprovetime varchar(255);
  DECLARE rEntertime varchar(255);
  DECLARE rReviewtime varchar(255);

  DECLARE iCat varchar(255);
  DECLARE iEditby varchar(255);
  DECLARE iItemname varchar(255);
  DECLARE iModify date;
  DECLARE iRecipeid bigint;
  DECLARE iRecipename varchar(255);
  DECLARE iRecipesomruid varchar(255);
  DECLARE iReqquan double;
  DECLARE iRequnit varchar(255);
  DECLARE iSuppliercat varchar(255);
  DECLARE iUnitmeasuretype varchar(255);
  DECLARE iUnitquan double;
  DECLARE iVendor varchar(255);

  DECLARE ilkcRecipeId bigint;


   DECLARE curRecipe CURSOR FOR
	SELECT dbid,
           REPLACE(somruid, '\n', '') as somruid,
           REPLACE(name,  '\n', '') as name,
           REPLACE(description,  '\n', '') as description,
           REPLACE(direction,  '\n', '') as direction,
           REPLACE(enteredby,  '\n', '') as enteredby,
           REPLACE(reviewedby,  '\n', '') as reviewedby,
           REPLACE(approvedby,  '\n', '') as approvedby,
           REPLACE(approvecomment,  '\n', '') as approvecomment,
           REPLACE(editstatus, '\n', '') as editstatus,
           REPLACE(reviewcomment,  '\n', '') as reviewcomment,
           REPLACE(unit,  '\n', '') as unit,
           REPLACE(volumn,  '\n', '') as volumn,
           IFNULL( approvetime, Now() ) as approvetime,
           IFNULL( entertime, Now() ) as entertime,
           IFNULL( reviewtime, Now() ) as reviewtime
    FROM somrusoft.recipe;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;

  OPEN curRecipe;
  getRecipe: LOOP
	FETCH FROM curRecipe INTO
           rDbid,
		   rSomruid ,
		   rName ,
		   rDescription ,
		   rDirection ,
		   rEnteredby ,
		   rReviewedby ,
		   rApprovedby ,
		   rApprovecomment ,
		   rEditstatus ,
		   rReviewcomment ,
		   rUnit ,
		   rVolumn ,
		   rApprovetime ,
		   rEntertime ,
		   rReviewtime;

	IF done1 THEN
		CLOSE curRecipe;
		LEAVE getRecipe;
	END IF;

    -- insert into il_sop
    insert into somrusoft.il_sop(
		  active,
		  description,
		  direction,
		  edit_status,
		  edit_status_comment,
		  edit_status_time,
		  edited_by,
		  entered_by,
		  entered_time,
		  modified_on,
		  name,
		  sop_identifier,
		  reviewed_by,
		  unit,
		  volume
    )
    values (
           1,
           rDescription ,
           rDirection ,
           'Approved' ,
           null,
           Now(),
           rEnteredby ,
           rEnteredby ,
           rEntertime ,
           rEntertime,
           rName ,
           rSomruid ,
		   rReviewedby ,
		   rUnit ,
		   rVolumn
    );
    SELECT LAST_INSERT_ID() into rRecipeId;


    -- insert into il_sop_component
    BLOCK1 : BEGIN
    DECLARE curIngredients CURSOR FOR
	SELECT REPLACE(cat, '\n', '') as cat,
           IFNULL(editby, 'peter') as editby,
           REPLACE(itemname, '\n', '') as itemname,
           IFNULL(modify, Now() ) as modifiy,
           REPLACE(recipename, '\n', '') as recipename,
           REPLACE(recipesomruid, '\n', '') as recipesomruid,
           cast(reqquan as DECIMAL(8,2)) as reqquan,
           REPLACE(requnit, '\n', '') as requnit,
           REPLACE(suppliercat, '\n', '') as suppliercat,
           REPLACE(vendor, '\n', '') as vendor
    FROM somrusoft.ingredient where recipeid = rDbid;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;

	OPEN curIngredients;
	getIngredients: LOOP
		FETCH FROM curIngredients INTO
          iCat,
          iEditby,
          iItemname,
          iModify,
          iRecipename,
          iRecipesomruid,
		  iReqquan,
          iRequnit,
          iSuppliercat,
          iVendor;

        if done2 THEN
            CLOSE curIngredients;
            SET done2 = FALSE;
            LEAVE getIngredients;
        end if;

        -- deal with cases where units not using correct code table values
        if iRequnit = 'plate' then
          set iRequnit = 'plate(s)';
        end if;

        if iRequnit = 'uL' then
          set iRequnit = 'µL';
        end if;



        if iRecipename is null or iRecipename = '' then
			insert into somrusoft.il_sop_component(
			  catalog_number,
			  edited_by ,
			  item_name ,
			  modified_on,
			  required_quantity,
			  required_unit,
			  supplier_catalog_number,
			  vendor ,
			  sop_id
			)
			values (
			  iCat,
			  iEditby,
			  iItemname,
			  iModify,
			  iReqquan,
			  iRequnit,
			  iSuppliercat,
			  iVendor,
			  rRecipeId
			);
		 end if;


    END LOOP getIngredients;
    END BLOCK1;

  END LOOP getRecipe;
END
