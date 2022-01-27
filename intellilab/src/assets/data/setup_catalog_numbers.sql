CREATE DEFINER=`root`@`localhost` PROCEDURE `setup_catalog_numbers`()
BEGIN
  insert into somrusoft.il_catalog_sequence(  catalog_number, catalog_number_type)
  values ( 500, 'Product' );
  insert into somrusoft.il_catalog_sequence(  catalog_number, catalog_number_type)
  values ( 500, 'Kit' );
END