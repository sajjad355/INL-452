CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_locations`()
BEGIN
  declare locationId INTEGER;
  declare slocationId INTEGER;
  delete from somrusoft.il_location where parent_location_id is not null; -- sublocations
  delete from somrusoft.il_location; -- locations
  
  set locationId = 1; 
  set slocationId = locationId;
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Acid Cabinet', Now() );
  
  set locationId = slocationId + 1;
  set slocationId = locationId;
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-80 Freezer #1', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Top Shelf', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Somru Antibodies', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Formycon Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Drug 2', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Drugs 1', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Sino Biologicals', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'MAB Tech and ANP', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Client (General)', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Miscellaneous', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Sigma', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'R&D Systems', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Prospec and PeproTech', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Jackson', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (slocationId,  'peter', 'Acro BioSystems', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Abcam and AbNova', Now(), locationId );
  
  
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-80 Freezer #2', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Shelf 3', Now(), locationId );
 
  
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-20 Freezer #1 (SOM829) Eggs', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Fitzgerald Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Miscellaneous Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Prospec Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Sino Biological Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'MabTech Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Purified Antibodies Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Abcam Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'AbD Serotec Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Cedarlane Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Somru Conjugates Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id ,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Drg INTM Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Nanocs Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Jackson Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'R&S Systems Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id, edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Door', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Amplatto Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Alpha Diagnostics Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Peprotech Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Client', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'HRP Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Invivogen', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Sigma', Now(), locationId );
  
      
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-20 Freezer #2', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Formycon Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 3', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 4', Now(), locationId );
  

  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-20 Freezer #3', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 5', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 6', Now(), locationId );
  
   
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-20 Freezer #4', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 4', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Shelf 2', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 1', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Shelf 8', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 7', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 3', Now(), locationId );


  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-20 Freezer SOM046', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Shelf 10', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Shelf 9', Now(), locationId );
  
    
  set locationId = slocationId + 1;
  set slocationId = locationId;
  

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Fridge #1', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Cedarlane Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Client', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'AbD Serotec Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Door', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'R&S Systems Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Abcam Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Bio Rad', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Peprotech Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Prospec Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Somru Conjugates Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Sino Biological Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'MabTech Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Fitzgerald Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Jackson Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Miscellaneous Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Nanocs Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Alpha Diagnostics Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'General Box', Now(), locationId );
   set slocationId = slocationId + 1;
   insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Sigma Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (slocationId,  'peter', 'ACRO', Now(), locationId );

  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Fridge #2', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Door', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Veggie Drawer', Now(), locationId );
  

  set locationId = slocationId + 1;
  set slocationId = locationId;
  

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Fridge #3', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Precipitated hIgG Depleted only Box;', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (slocationId,  'peter', 'Purified Antibodies Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Second Shelf', Now(), locationId );

  set locationId = slocationId + 1;
  set slocationId = locationId;
  

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Fridge #4', Now() );
  
  set locationId = slocationId + 1;
  set slocationId = locationId;
    
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Fridge #5 (Egg Fridge) - NO S', Now() );
  
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Fridge SOM088', Now() );
  
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Level 6', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Level 5', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Level 1', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Top', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Level 2', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Middle', Now(), locationId );
 
  
  set locationId = slocationId + 1;
  set slocationId = locationId;


  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Lab shelf', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Fume Hood', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Packing bench', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Reagent shelf', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #1', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #2', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #3', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #4', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #5', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #6', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #7', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #8', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #9', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bench #10', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Cell culture storage room', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Bay Office Table', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'IT Office', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Production Storage Room', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;

  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Cell culture room', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;


  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Microbiology room', Now() );
   set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-20 Freezer/Fridge SOM262', Now() );
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Liquid nitrogen', Now() );
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', '-20 Freezer #5', Now() );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Fitzgerald Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Miscellaneous Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Prospec Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Sino Biological Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'MabTech Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'Purified Antibodies Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Abcam Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (  slocationId,'peter', 'AbD Serotec Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values (slocationId,  'peter', 'Cedarlane Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Somru Conjugates Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Drg INTM Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Nanocs Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Jackson Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'R&S Systems Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Door', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Amplatto Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Alpha Diagnostics Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Peprotech Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Client', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'HRP Box', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Invivogen', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'Sigma', Now(), locationId );
  set slocationId = slocationId + 1;
  insert into somrusoft.il_location( location_id,edited_by, location_name, modified_on, parent_location_id ) 
  values ( slocationId, 'peter', 'RayBiot Tech', Now(), locationId );
  
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  insert into somrusoft.il_location(location_id, edited_by, location_name, modified_on ) 
  values ( locationId, 'peter', 'Shipped Externally', Now() );
  set locationId = slocationId + 1;
  set slocationId = locationId;
  
  
 

END