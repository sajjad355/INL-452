CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_item_details`()
BEGIN
    DECLARE done1 BOOLEAN DEFAULT FALSE;
    DECLARE idDbid bigint; 
    DECLARE idAmount int;
	DECLARE idComment varchar(255);
	DECLARE idConcentration varchar(255);
	DECLARE idConcentrationunit varchar(255);
	DECLARE idExpiredate date;
	DECLARE idInuse int;
	DECLARE idItemdbid bigint;
	DECLARE idItemname varchar(255);
	DECLARE idLocation varchar(255);
	DECLARE idLotnumber varchar(255);
	DECLARE idParentlotnumber varchar(255);
	DECLARE idReceivedate date;
	DECLARE idSublocation varchar(255);
	DECLARE idApcolumnprepdate date;
	DECLARE idClonality varchar(255);
	DECLARE idConjugate varchar(255);
	DECLARE idHost varchar(255);
	DECLARE idIggdepletion varchar(255);
	DECLARE idItemtype varchar(255);
	DECLARE idPurification varchar(255);
	DECLARE idSpecies varchar(255);
	DECLARE idSupplier varchar(255);
	DECLARE idUsagecolumn varchar(255);
	DECLARE idVolume double;
	DECLARE idVolumeunit varchar(255);
	DECLARE idWeight varchar(255);
	DECLARE idWeightunit varchar(255);
	DECLARE idColumnnumber varchar(255);
	DECLARE idProjectnumber varchar(255);
	DECLARE idReceiveperson varchar(255);
	DECLARE idRecon varchar(255);
	DECLARE idReconstituted int; 
	DECLARE idReserve int;
	DECLARE idUnit varchar(255);
	DECLARE idUnitsize double;
	DECLARE idModifydate date;
	DECLARE idModifyperson varchar(255);
	DECLARE idBatchnumber varchar(255);
	DECLARE idBca varchar(255);
	DECLARE idBindingactivity varchar(255);
	DECLARE idBiomoleculeinfo varchar(255);
	DECLARE idBiotintobiomoleculeratio varchar(255);
	DECLARE idConfile varchar(255);
	DECLARE idConjugatechemistry varchar(255);
	DECLARE idConjugateincorporationratio varchar(255);
	DECLARE idConjugateinfo varchar(255);
	DECLARE idConjugateprepdate date;
	DECLARE idConjugateprepperson varchar(255);
	DECLARE idConstatus varchar(255);
	DECLARE idDescriptionpreparation varchar(255);
	DECLARE idItemcategory varchar(255);
	DECLARE idMoleculeweight varchar(255);
	DECLARE idPurity varchar(255);
	DECLARE idRetestdate date;
	DECLARE idRffile varchar(255);
	DECLARE idRfstatus varchar(255);
	DECLARE idRunnumber varchar(255);
	DECLARE idStoretemperature varchar(255);
	DECLARE idReserveclientid varchar(255);
	DECLARE idReceivepersonid varchar(255);

	DECLARE idReceiveUserId bigint;
	DECLARE idLocationId bigint;
	DECLARE idSubLocationId bigint;
	DECLARE idHazardLevel varchar(255);
    
    DECLARE iInventoryId bigint;
	DECLARE iTotalAmount bigint;
	DECLARE iTotalNumberInUse bigint;
	DECLARE iActive int;
	DECLARE iNewUnit varchar(255);
    
    DECLARE curItemDetails CURSOR FOR
	SELECT   dbid,
			 cast(IFNULL(NULLIF(amount, '' ), 0) as SIGNED) as amount ,
			 NULLIF(comment,'' ) as comment,
			 NULLIF(concentration,'' ) as concentration,
			 NULLIF(concentrationunit,'' ) as concentrationunit,
			 expiredate,
			 cast(IFNULL(NULLIF(inuse, '' ), 0) as SIGNED) as inuse,
			 itemdbid,
			 NULLIF(itemname,'' ) as itemname,
			 NULLIF( NULLIF(location,'' ), '?') as location,
			 NULLIF(lotnumber,'' ) as lotnumber,
			 NULLIF(parentlotnumber,'' ) as parentlotnumber,
			 receivedate,
			 NULLIF(sublocation,'' ) as sublocation,
			 apcolumnprepdate,
			 NULLIF(clonality,'' ) as clonality,
			 NULLIF(conjugate,'' ) as conjugate,
			 NULLIF(host,'' ) as host,
			 NULLIF(iggdepletion,'' ) as iggdepletion,
			 NULLIF(itemtype,'' ) as itemtype,
			 NULLIF(purification,'' ) as purification,
			 NULLIF(species,'' ) as species,
			 NULLIF(supplier,'' ) as supplier,
			 NULLIF(usagecolumn,'' ) as usagecolumn,
			 cast(NULLIF(volume,'' ) as DECIMAL(8,2)) as volume ,
			 NULLIF(volumeunit,'' ) as volumeunit,
			 NULLIF(weight,'' ) as weight,
			 NULLIF(weightunit,'' ) as weightunit,
			 NULLIF(columnnumber,'' ) as columnnumber,
			 NULLIF(projectnumber,'' ) as projectnumber,
			 NULLIF(receiveperson,'' ) as receiveperson,
			 NULLIF(recon,'' ) as recon,
			 cast(NULLIF(reserve,'' ) as SIGNED) as reserve,
			 NULLIF(unit,'' ) as unit,
			 cast(NULLIF(unitsize,'' ) as DECIMAL(8,2)) as unitsize ,
			 modifydate,
			 NULLIF(modifyperson,'' ) as modifyperson,
			 NULLIF(batchnumber,'' ) as batchnumber,
			 NULLIF(bca,'' ) as bca,
			 NULLIF(bindingactivity,'' ) as bindingactivity,
			 NULLIF(biomoleculeinfo,'' ) as biomoleculeinfo,
			 NULLIF(biotintobiomoleculeratio,'' ) as biotintobiomoleculeratio,
			 NULLIF(confile,'' ) as confile,
			 NULLIF(conjugatechemistry,'' ) as conjugatechemistry,
			 NULLIF(conjugateincorporationratio,'' ) as conjugateincorporationratio,
			 NULLIF(conjugateinfo,'' ) as conjugateinfo,
			 conjugateprepdate,
			 NULLIF(conjugateprepperson,'' ) as conjugateprepperson,
			 NULLIF(constatus,'' ) as constatus,
			 NULLIF(descriptionpreparation,'' ) as descriptionpreparation,
			 NULLIF(itemcategory,'' ) as itemcategory,
			 NULLIF(moleculeweight,'' ) as moleculeweight,
			 NULLIF(purity,'' ) as purity,
			 retestdate,
			 NULLIF(rffile,'' ) as rffile,
			 NULLIF(rfstatus,'' ) as rfstatus,
			 NULLIF(runnumber,'' ) as runnumber,
			 NULLIF(storetemperature,'' ) as storetemperature,
			 NULLIF(reserveclientid,'' ) as reserveclientid,
			 NULLIF(receivepersonid,'' ) as receivepersonid
	FROM somrusoft.item_details 
	where lotnumber is not null and
		  lotnumber <> ''
	order by itemdbid asc;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;
    
    
    OPEN curItemDetails;
	getItemDetails: LOOP
        set iTotalAmount = 0;
        set iTotalNumberInUse = 0;
        set iInventoryId = null;
    
		FETCH FROM curItemDetails INTO
				idDbid, 
				idAmount ,
				idComment ,
				idConcentration ,
				idConcentrationunit ,
				idExpiredate ,
				idInuse ,
				idItemdbid,
				idItemname ,
				idLocation ,
				idLotnumber ,
				idParentlotnumber ,
				idReceivedate ,
				idSublocation ,
				idApcolumnprepdate ,
				idClonality ,
				idConjugate ,
				idHost ,
				idIggdepletion ,
				idItemtype ,
				idPurification ,
				idSpecies ,
				idSupplier ,
				idUsagecolumn ,
				idVolume ,
				idVolumeunit ,
				idWeight ,
				idWeightunit ,
				idColumnnumber ,
				idProjectnumber ,
				idReceiveperson ,
				idRecon ,
				idReserve ,
				idUnit ,
				idUnitsize ,
				idModifydate ,
				idModifyperson ,
				idBatchnumber ,
				idBca ,
				idBindingactivity ,
				idBiomoleculeinfo ,
				idBiotintobiomoleculeratio ,
				idConfile ,
				idConjugatechemistry ,
				idConjugateincorporationratio ,
				idConjugateinfo ,
				idConjugateprepdate ,
				idConjugateprepperson ,
				idConstatus ,
				idDescriptionpreparation ,
				idItemcategory ,
				idMoleculeweight ,
				idPurity ,
				idRetestdate ,
				idRffile ,
				idRfstatus ,
				idRunnumber ,
				idStoretemperature ,
				idReserveclientid ,
				idReceivepersonid;
		if done1 THEN
			CLOSE curItemDetails;
			LEAVE getItemDetails;
		end if;
        
        CALL get_inventory_data( idItemdbid, iInventoryId, iTotalAmount, iTotalNumberInUse, iActive, iNewUnit );
        
        if ( iInventoryId is null ) then
          iterate getItemDetails;
        end if;
        
        set idReceiveUserId = null;
		if ( idReceiveperson is not null ) then
		  CALL get_user_id_from_name(idReceiveperson,idReceiveUserId);
		end if;

		set idLocationId = null;
		if ( idLocation is not null ) then 
		  CALL get_location_id_from_name( idLocation, idLocationId );
		end if;

		set idSubLocationId = null;
		if ( idSublocation is not null and idLocationId is not null ) then
		  CALL get_location_id_from_name_and_parent( idSublocation, idLocationId, idSubLocationId );
		end if;

		if ( idRecon = 'Yes' ) then
		  set idReconstituted = 1;
		else
		  set idReconstituted = 0;
		end if;

		if ( idAmount is null ) then
		  set idAmount = 0;
		end if;
		set iTotalAmount = iTotalAmount + idAmount;
		set iTotalNumberInUse = iTotalNumberInUse + idInuse; 

		insert into somrusoft.il_inventory_detail(
				name,
				active,
				affinity_purification_preparation_date,
				amount,
				column_preparation_number,
				column_usage,
				comment,
				concentration,
				concentration_unit,
				container_size,
				drug_lot_type,
				edited_by,
				equipment_number,
				expiry_date,
				igg_depletion,
				immunization_cycle_day,
				lot_number,
				model_number,
				modified_on,
				number_in_use,
				project_number,
				purification,
				received_date,
				reconstituted,
				reference_lot_number,
				reserve,
				retest_date,
				titer_assay,
				unit,
				volume,
				volume_unit,
				inventory_id,
				location_id,
				receive_user_id,
				sub_location_id,
				batch_number,
				store_temperature,
				purity,
				host,
				hazard_level
			)
			values (
					   idItemname,
					   iActive,
					   idApcolumnprepdate,
					   IFNULL(idAmount, 0),
					   idColumnnumber,
					   idUsagecolumn,
					   idComment,
					   idConcentration ,
					   idConcentrationunit ,
					   idUnitsize,
					   null,
					   IFNULL( idModifyperson, 'peter' ),
					   null,
					   idExpiredate,
					   idIggdepletion,
					   null,
					   idLotnumber,
					   null,
					   IFNULL( idModifydate, Now() ),
					   idInuse,
					   idProjectnumber,
					   idPurification,
					   idReceivedate,
					   idReconstituted,
					   null,
					   IFNULL(idReserve, 0 ),
					   idRetestdate,
					   null,
					   iNewUnit,
					   idVolume,
					   idVolumeunit,
					   iInventoryId,
					   idLocationId,
					   idReceiveUserId,
					   idSubLocationId,
					   idBatchnumber,
					   idStoretemperature,
					   idPurity,
					   idHost,
					   idHazardLevel
		);
		
        update somrusoft.il_inventory 
        set amount = iTotalAmount,
            number_in_use = iTotalNumberInUse
        where old_inventory_id = idItemdbid;
        
		-- reset amount for next loop
		set iTotalAmount = 0;
		set iTotalNumberInUse = 0;
        
        
	END LOOP getItemDetails;
        
        
        
    
END