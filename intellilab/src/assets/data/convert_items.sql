CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_items`()
BEGIN
    DECLARE done1, done2, done3 BOOLEAN DEFAULT FALSE;

    DECLARE iDbid bigint;
    DECLARE iActive int;
    DECLARE iCat varchar(255);
    DECLARE iClientspecific varchar(255);
    DECLARE iComment varchar(255);
    DECLARE iLastmodify date;
    DECLARE iManufacturer varchar(255);
    DECLARE iManufacturerlink varchar(255);
    DECLARE iName varchar(255);
    DECLARE iQuantitythreshold int;
    DECLARE iSupplier varchar(255);
    DECLARE iSupplylink varchar(255);
    DECLARE iType varchar(255);
    DECLARE iUnit varchar(255);
    DECLARE iNewUnit varchar(255);
    
    DECLARE iUnitprice varchar(255);
    DECLARE iUnitsize double;
    DECLARE iSuppliercat varchar(255);
    DECLARE iModifyperson varchar(255);
    DECLARE iCategory varchar(255);

    DECLARE iInventoryId bigint;
    DECLARE invSubType varchar(255);
    DECLARE invType varchar(255);
    
    DECLARE curItems CURSOR FOR
        SELECT dbid,
               active,
               NULLIF(cat, '') as cat,
               NULLIF(clientspecific, '') as clientspecific,
               NULLIF(comment, '' ) as comment,
               lastmodify,
               NULLIF(manufacturer, '' ) as manufacturer,
               NULLIF(manufacturerlink, '' ) as manufacturerlink,
               NULLIF(name, '' ) as name,
               cast( IFNULL(NULLIF(quantitythreshold, '' ),0) as signed) as quantitythreshold,
               NULLIF(supplier, '' ) as supplier,
               NULLIF(supplylink,  '' ) as supplylink,
               NULLIF(type,  '' ) as type,
               TRIM(NULLIF(unit,  '' )) as unit,
               cast(NULLIF(unitprice, '' ) as DECIMAL(8,2)) as unitprice,
               cast(NULLIF(unitsize, '' ) as DECIMAL(8,2)) as unitsize,
               NULLIF(suppliercat,  '' ) as suppliercat,
               NULLIF(modifyperson,  '' ) as modifyperson,
               NULLIF(category,  '' ) as category
        FROM somrusoft.item;
        
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;

    OPEN curItems;
    getItems: LOOP
        FETCH FROM curItems INTO
            iDbid,
            iActive,
            iCat,
            iClientspecific,
            iComment,
            iLastmodify,
            iManufacturer,
            iManufacturerlink,
            iName,
            iQuantitythreshold,
            iSupplier,
            iSupplylink,
            iType,
            iUnit,
            iUnitprice,
            iUnitsize,
            iSuppliercat,
            iModifyperson,
            iCategory;
        IF done1 THEN
            CLOSE curItems;
            LEAVE getItems;
        END IF;

        /*  OLD Type                        New Type                            New Sub Tye
            Animal                          Matrix                              null
            Anti serum                      Anti-Sera                           null
            Antibody                        if Manufacturer = Somru
                                              In-House Protein:                 Antibody Unconjugated
                                            otherise
                                              Commercial Protein                Antibody Unconjugated
            Protein and Enzyme              If Manufacturer = Somru
                                              In-House Protein:                 Protein Unconjugated
                                            otherise
                                              Commercial Protein                Protein Unconjugated
            Cell Line                       Cell Culture                        null
            Buffer and Solution             Solution                            null
            Chemical                        Chemical                            null
            Media                           Equipment                           null
            Conjugate Molecule              Molecular                           null
            Consumable                      Consumable                          null
            Drug                            Drug                                null
            General Supply                  Equipment                           null
            Matrix                          Matrix                              null
            Kit                             Kit                                 null
            Molecular                       Molecular                           null
            Solutions                       Solutuon                            null
            Stationary                      Equipment                           null
            Conjugate                       SKIP THESE DEAL WITH CONJ IN FUTURE
            Anti-Sera                       Anti-Sera                           null
        */

        IF iCat IS NULL THEN
            ITERATE getItems;
        END IF;
        
        
        set invType = null;
	    set invSubType = null;
        set iNewUnit = null;
        
        IF iType = 'Animal' then
            set invType = 'Matrix';
        ELSEIF iType = 'Anti serum' then
            set invType = 'Anti-Sera';
        ELSEIF iType = 'Antibody' then
            if iManufacturer = 'Somru' then
                set invType = 'In-House Protein';
                set invSubType = 'Antibody - Unconjugated';
            ELSE
                set invType = 'Commercial Protein';
                set invSubType = 'Antibody - Unconjugated';
            END IF;
        ELSEIF iType = 'Protein and Enzyme' then
            if iManufacturer = 'Somru' then
                set invType = 'In-House Protein';
                set invSubType = 'Protein - Unconjugated';
            ELSE
                set invType = 'Commercial Protein';
                set invSubType = 'Protein - Unconjugated';
            END IF;
        ELSEIF iType = 'Cell Line' then
            set invType = 'Cell Culture';
        ELSEIF iType = 'Buffer and Solution' then
            set invType = 'Solution';
        ELSEIF iType = 'Chemical' then
            set invType = 'Chemical';
            
        ELSEIF iType = 'Media' then
            set invType = 'Equipment';
        ELSEIF iType = 'Conjugate Molecule' then
            set invType = 'Molecular';
        ELSEIF iType = 'Consumable' then
            set invType = 'Consumable';
        ELSEIF iType = 'Drug' then
            set invType = 'Drug';
        ELSEIF iType = 'General Supply' then
            set invType = 'Equipment';
        ELSEIF iType = 'Matrix' then
            set invType = 'Matrix';
        ELSEIF iType = 'Kit' then
            set invType = 'Kit';
        ELSEIF iType = 'Molecular' then
            set invType = 'Molecular';
        ELSEIF iType = 'Solutions' then
            set invType = 'Solution';
        ELSEIF iType = 'Stationary' then
            set invType = 'Equipment';
        ELSEIF iType = 'Anti-Sera' then
            set invType = 'Anti-Sera';
        ELSE
            ITERATE getItems; -- equivalent to continue : do not convert any types other than listed above
        END IF;
        
        if invType = 'Equipment' then
          ITERATE getItems;
          -- todo: add details to new equipment table rather than inventory
        end if;

        -- consolidate user entered unit data into standard units
		IF iUnit = 'Pack of' || iUnit = 'pk' || iUnit = 'pack' || iUnit = 'pack(s)' || iUnit = 'pkg' || iUnit = 'pk of' || iUnit = 'pail of  pack' || iUnit = 'pack  boxes' || iUnit = 'PK' || iUnit = 'pack  strips' then
            set iNewUnit = 'pack(s)';
		ELSEIF iUnit = 'vial(s)' then
            set iNewUnit = 'vial(s)';      
		ELSEIF iUnit = 'unit(s)' || iUnit = 'units' || iUnit = 'U' then
            set iNewUnit = 'unit(s)';
        ELSEIF iUnit = 'EA' || iUnit = 'Each' then
            set iNewUnit = 'each';       
        ELSEIF iUnit = 'case(s)' || iUnit = 'case' || iUnit = 'case of' || iUnit = 'per case' || iUnit = 'Case reams' || iUnit = 'CS'  then
            set iNewUnit = 'case(s)';       
        ELSEIF iUnit = 'bag(s)' || iUnit = 'bag'  || iUnit = 'bags' then
            set iNewUnit = 'bag(s)';       
        ELSEIF iUnit = 'box(es)' || iUnit = 'box' || iUnit = 'BX' || iUnit = 'box of' then
            set iNewUnit = 'box(es)';    
        ELSEIF iUnit = 'plates' || iUnit = 'plate(s)' then
            set iNewUnit = 'plate(s)';
        ELSEIF iUnit = 'gal' || iUnit = 'gallon' then
            set iNewUnit = 'gal';           
        ELSEIF iUnit = 'carton' || iUnit = 'carton(s)' then
            set iNewUnit = 'carton(s)';           
        ELSEIF iUnit = 'Columns' || iUnit = 'Columns(s)' || iUnit = 'columnss' || iUnit = 'columns(s)' || iUnit = 'columnspack' then
            set iNewUnit = 'columns(s)';  
        ELSEIF iUnit = 'kit(s)' || iUnit = 'kits' then
            set iNewUnit =  'kit(s)';      
		ELSEIF iUnit = 'bottle(s)' || iUnit = 'bottles' then
            set iNewUnit =  'bottle(s)';      
        ELSEIF iUnit = 'mmol' then
            set iNewUnit =  'mmol';      
        ELSEIF iUnit = 'mol' then
            set iNewUnit =  'mol';     
        ELSEIF iUnit = 'µl' || iUnit = 'µL' then
            set iNewUnit =  'µl';    
        ELSEIF iUnit = 'ml' || iUnit = 'mL' then
            set iNewUnit = 'mL';
        ELSEIF iUnit = 'l' || iUnit = 'L' then
            set iNewUnit = 'L';    
        ELSEIF iUnit = 'kl' || iUnit = 'kL' then
            set iNewUnit = 'kL';      
        ELSEIF iUnit = 'pg' || iUnit = 'pG' then
            set iNewUnit = 'pg';
        ELSEIF iUnit = 'ng' || iUnit = 'nG' then
            set iNewUnit = 'ng';    
        ELSEIF iUnit = 'µg' || iUnit = 'µG' then
            set iNewUnit = 'µg';
        ELSEIF iUnit = 'mg' || iUnit = 'mG' then
            set iNewUnit = 'mg';
		ELSEIF iUnit = 'g' || iUnit = 'G' then
            set iNewUnit = 'g';
		ELSEIF iUnit = 'kg' || iUnit = 'kG' then
            set iNewUnit = 'kg';		
        ELSE
            set iNewUnit = null;
        END IF;



        -- insert the item table to il_inventory

        insert into somrusoft.il_inventory(
            name,
            amount,
            number_in_use,
            catalog_number,
            active,
            unit_price,
            category,
            checkout_denomination,
            chemical_abstracts_service_number,
            clonality,
            comment,
            compound_name,
            conjugation_type,
            container_size,
            denomination_unit,
            drug_type,
            edited_by,
            host,
            manufacturer,
            manufacturer_link,
            modified_on,
            molar_challenge_ratio,
            molecular_weight,
            pack_denomination,
            quantity_threshold,
            subtype,
            supplier,
            supplier_catalog_number,
            supplier_link,
            trade_name,
            type,
            unit,
            old_inventory_id
        )
        values (
                   iName,
                   0,
                   0,
                   iCat,
                   iActive,
                   IFNULL( iUnitprice, 0),
                   IFNULL(iCategory, 'Materials' ),
                   null,
                   null,
                   null,
                   iComment,
                   null,
                   null,
                   iUnitsize,
                   null,
                   null,
                   IFNULL( iModifyperson, 'peter' ),
                   null,
                   iManufacturer,
                   iManufacturerlink,
                   IFNULL(iLastmodify, Now() ),
                   null,
                   null,
                   null,
                   iQuantitythreshold,
                   invSubType,
                   iSupplier,
                   iSuppliercat,
                   iSupplylink,
                   null,
                   invType,
                   iNewUnit,
                   iDbid
               );

        
    END LOOP getItems;
END