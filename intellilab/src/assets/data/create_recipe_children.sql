CREATE DEFINER=`root`@`localhost` PROCEDURE `create_recipe_children`()
BEGIN
  DECLARE done1, done2, done3 BOOLEAN DEFAULT FALSE; 
  
  DECLARE iIngredientRecipeId bigint;
  DECLARE iIngredientRecipeName varchar(255);
  DECLARE iIngredientQuantity varchar(255);
  DECLARE iIngredientQuantityUnit varchar(255);
  
  DECLARE linkFromRecipeId bigint;
  DECLARE linkToRecipeId bigint;
  
 
  DECLARE curIngredients CURSOR FOR 
	select recipeid, recipename, reqquan, requnit
    from somrusoft.ingredient 
    where recipename <> '' and recipename is not null;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;
    
	OPEN curIngredients;
	getIngredients: LOOP
		FETCH FROM curIngredients INTO 
        iIngredientRecipeId, iIngredientRecipeName, iIngredientQuantity, iIngredientQuantityUnit;
          
  
        if done1 THEN
            CLOSE curIngredients;
            SET done1 = FALSE;
            LEAVE getIngredients;
        end if;
        
        BLOCK1 : BEGIN
        DECLARE curRecipeLink CURSOR FOR
        SELECT ir.sop_id 
        FROM somrusoft.il_sop ir, somrusoft.recipe r 
        where ir.name = r.name and 
              r.dbid = iIngredientRecipeId;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;
        
        OPEN curRecipeLink;
	    getRecipeLink: LOOP
			FETCH FROM curRecipeLink INTO linkFromRecipeId;
			  
			if done2 THEN
				CLOSE curRecipeLink;
				SET done2 = FALSE;
				LEAVE getRecipeLink;
			end if;  
       
	   END LOOP getRecipeLink;    
       END BLOCK1;
        
        
        BLOCK2 : BEGIN
        DECLARE curRecipe CURSOR FOR
        SELECT sop_id 
        FROM somrusoft.il_sop where name = iIngredientRecipeName;  
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done3 = TRUE;
        
        OPEN curRecipe;
	    getRecipe: LOOP
			FETCH FROM curRecipe INTO linkToRecipeId;
			  
			if done3 THEN
				CLOSE curRecipe;
				SET done3 = FALSE;
				LEAVE getRecipe;
			end if;  
            
            -- finally insert the link between the two recipes
            
            insert into somrusoft.il_sop_link( edited_by, modified_on, required_quantity, required_unit, referenced_sop_id, referencing_sop_id )
            values ( 'peter', Now(), iIngredientQuantity, iIngredientQuantityUnit, linkToRecipeId, linkFromRecipeId );
		
       
	   END LOOP getRecipe;    
       END BLOCK2;
         
   
    
  END LOOP getIngredients;  
END