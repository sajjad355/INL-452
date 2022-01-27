CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_settings`()
BEGIN
 declare settingId INTEGER;
 delete from somrusoft.il_setting_value;
 delete from somrusoft.il_setting;

 set settingId = 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'kitPackSize', 'peter', Now(), 'kitPackSize' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1 x 96 wells', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '2 x 96 wells', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '5 x 96 wells', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '10 x 96 wells', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '20 x 96 wells', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'partnerClients', 'peter', Now(), 'partnerClients' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Celerion', settingId, '0' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Intas/Lambda', settingId, '5' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Enzene', settingId, '2' );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'kitVialList', 'peter', Now(), 'kitVialList' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'narrow mouth', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'white bottle', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'polypropylene', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'wide mouth', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 're-labelled reagent', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'N/A', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'kitMethod', 'peter', Now(), 'kitMethod' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Misc', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'ADA', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'PK', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Characterization', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Biomarker', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'NAb', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Enzymatic', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'kitStatus', 'peter', Now(), 'kitStatus' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Qualified with innovator', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Validated with innovator', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Development in progress', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Development in progress', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Developed (kit ready but not qualified)', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'massUnit', 'peter', Now(), 'massUnit' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'pg', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'ng', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'µg', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mg', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'g', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'kg', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'inventoryItemType', 'peter', Now(), 'inventoryItemType' );


 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Matrix', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Anti-Sera', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Commercial Protein', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Cell Culture', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Solution', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Chemical', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Molecular', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Consumable', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Drug', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Kit', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'In-House Protein', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'concentrationUnit', 'peter', Now(), 'concentrationUnit' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'pg/µL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'M', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mIU/mL', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'pg/mL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mM', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'pg/L', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'µM', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'ng/µL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'ng/mL', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'ng/L', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'µg/µL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'µg/mL', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'µg/L', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mg/µL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mg/mL', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mg/L', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'g/µL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'g/mL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'g/L', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'volumeUnit', 'peter', Now(), 'volumeUnit' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'µL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mL', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'L', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'kL', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'otherUnit', 'peter', Now(), 'otherUnit' );


 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'plate(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Column(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'case(s)', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'kit(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'unit(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'pack(s)', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'carton(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'bag(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'box(es)', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'prep(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'each', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'vial(s)', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'bottle(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'flask(s)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mmol', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'mol', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'oz', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'lb', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'fl oz', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'gal', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'cloneType', 'peter', Now(), 'cloneType' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'monoclonal', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'polyclonal', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'purification', 'peter', Now(), 'purification' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Affinity Purification', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Melon gel Purification', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Human IgG depletion only', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'IgY Precipitation', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'His-Tagged Protein Purification', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'host', 'peter', Now(), 'host' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Chicken', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Rabbit', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Rat', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Goat', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Sheep', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Mouse', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Hamster', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'species', 'peter', Now(), 'species' );


 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Human', settingId, '0' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Mouse', settingId, '1' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Rat', settingId, '2' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Monkey', settingId, '3' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Chicken', settingId, '4' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Donkey', settingId, '5' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Goat', settingId, '6' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Rabbit', settingId, '7' );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'paymentTerms', 'peter', Now(), 'paymentTerms' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Prepayment', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Net 30', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Due upon receipt', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Cash Against Documents (CAD)', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'incoTerm', 'peter', Now(), 'incoTerm' );


 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'CPT', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'EXW', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'FCA', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'FAS', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'FOB', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'CFR', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'CIF', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'CIP', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'DAT', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'DAP', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'DDP', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'tax', 'peter', Now(), 'tax' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'AB - 5%', '5', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'BC - 12%', '12', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'MB - 13%', '13', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'NB - 15%', '15', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'NL - 15%', '15', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'NT - 5%', '5', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'NS - 15%', '15', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'NU - 5%', '5', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'ON - 13%', '13', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'PE - 15%', '15', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'QC - 14.975%', '14.975', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'SK - 11%', '11', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'YT - 5%', '5', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, attribute, setting_id )
 values ( 'peter', Now(), 'No Tax', '0', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'catalogCategoryType', 'peter', Now(), 'catalogCategoryType' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Antibody(monoclonal)', settingId, '1' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Antibody(polyclonal)', settingId, '2' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Cell Line', settingId, '3');
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Protein', settingId, '4' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Misc', settingId, '5' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'ADA Kit or Component', settingId, '6' );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'PK Kit or Component', settingId, '7' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Method Transfer', settingId, '8' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'NAb Kit or Component', settingId, '9' );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Biomarker', settingId, '10' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Characterization Kit', settingId, '11' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Software', settingId, '12' );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Reagent', settingId, '13' );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'ordersProjectList', 'peter', Now(), 'ordersProjectList' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'A.General supplies', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'B.Reagents to fill order', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'C.Reagents kit dev', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'E.Reagents for other R&D', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'G. AIF', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'H.Ames', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'I. COV19', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'chickenProjectNames', 'peter', Now(), 'chickenProjectNames' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'AIF', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'MICCSA', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'MUN', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'IRAP III', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'IRAP I', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'SOMRU', settingId );



 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'productType', 'peter', Now(), 'productType' );



 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Antibody', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Cell Line', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Protein', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Misc', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'ADA', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'PK', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Method Transfer', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Characterization Kit', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Biomarker', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Associated Kit Component', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Software', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Reagent', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'productSafetyWarning', 'peter', Now(), 'productSafetyWarning' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'This product should be handled by trained personnel only. Please contact our customer service at customer@somrubioscience.com for more details.', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'applicationNote', 'peter', Now(), 'applicationNote' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Please Note: Optimal dilutions should be determined by each laboratory for each application.', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'invoiceOption', 'peter', Now(), 'invoiceOption' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Product Invoice', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Service Invoice', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'eggInventoryTiter', 'peter', Now(), 'eggInventoryTiter' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:1', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:100', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:562,500', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:1000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:10,000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:100,000', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:1,000,000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:10,000,000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:100,000,000', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:5,000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:12,500', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:25,000', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:62,000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:200,000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:312,000', settingId );
  insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:500,000', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:1,562,500', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '1:7,812,500', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'conjugateChemistry', 'peter', Now(), 'conjugateChemistry' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Amine', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Carboxy', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'conjugateRatio', 'peter', Now(), 'conjugateRatio' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '10 : 1', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '20 : 1', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '50 : 1', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'inventoryCategory', 'peter', Now(), 'inventoryCategory' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Reference Material', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Critical Reagent', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Biological Matrix', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Reagent', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Materials', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'orderAgentEmail', 'peter', Now(), 'orderAgentEmail' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'peter.scott@somrubioscience.com', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'dateformat', 'peter', Now(), 'dateformat' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'ddmmmyyyy', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyName', 'peter', Now(), 'companyName' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Somru BioScience', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyAddressLine1', 'peter', Now(), 'companyAddressLine1' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), '19 Innovation Way', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyAddressLine2', 'peter', Now(), 'companyAddressLine2' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Charlottetown, PE', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyAddressLine3', 'peter', Now(), 'companyAddressLine3' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'C1E 0B7 Canada', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyPhone', 'peter', Now(), 'companyPhone' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Phone: 1 (902) 367-4322', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyEmail', 'peter', Now(), 'companyEmail' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Email: customer@somrubioscience.com', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyHST', 'peter', Now(), 'companyHST' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'HST No: 80673 3887 RT00018', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyWebsite', 'peter', Now(), 'companyWebsite' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'www.somrubioscience.com', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyFax', 'peter', Now(), 'companyFax' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Fax: 902-367-4323', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companySalesRepName', 'peter', Now(), 'companySalesRepName' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Susan Holland', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'companyPaymentInfo', 'peter', Now(), 'companyPaymentInfo' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Somru BioScience Inc. -- Payment must be made via wire transfer.\nBank details for wire transfer:\nBank Name: Scotia Bank\nBank Address: 135 St. Peters Road, Charlottetown, PE, C1A 5P3\nRouting Number: 026002532\nAccount Number: 510030035211\nSwift code: NOSCCATT', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'drugType', 'peter', Now(), 'drugType' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Biosimilar', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Innovator (NA)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Innovator (EU)', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'N/A (Client Provided)', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'conjugationType', 'peter', Now(), 'conjugationType' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Biotinylation', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Digoxigenation', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Horseradish Peroxidase Conjugation', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'Protein Crosslinking', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'orderedItemsApproverEmails', 'peter', Now(), 'orderedItemsApproverEmails' );

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'zumer.fatima@somrubioscience.com', settingId );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id )
 values ( 'peter', Now(), 'peter.scott@somrubioscience.com', settingId );

 set settingId = settingId + 1;
 insert into somrusoft.il_setting( setting_id, description, edited_by, modified_on, name )
 values ( settingId,  'experimentType', 'peter', Now(), 'experimentType' );

-- the values you want to appear in the drop down should look like this

 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Validation', settingId, null );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Sample Analysis', settingId, null );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Method Development', settingId, null );
 insert into somrusoft.il_setting_value( edited_by, modified_on, value, setting_id, attribute )
 values ( 'peter', Now(), 'Other', settingId, null );



END
