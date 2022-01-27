CREATE DEFINER=`root`@`localhost` PROCEDURE `setup_operation`()
BEGIN
    ALTER TABLE il_operation AUTO_INCREMENT = 1;
    INSERT INTO il_operation ( operation_name ) VALUE ( 'View Clients' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Clients' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View Sales' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Sales' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'View User History' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View Users' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Users' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'Generate Catalogs' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View Inventory' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Inventory' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View Immunizing Schedules' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Immunizing Schedules' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View SOPs' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Edit SOPs' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'Review SOPs' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'View Kits' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'Edit Kits' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'Review Kits' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View Orders' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'Request Orders' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Orders' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View Products' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Products' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Review Products' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'View Equipment' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Manage Equipment' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Review Equipment' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'View Worksheets' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Edit Worksheet Design' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Execute Worksheet Instance' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Review Worksheets' );
	INSERT INTO il_operation ( operation_name ) VALUE ( 'View Config and Locations' );
    INSERT INTO il_operation ( operation_name ) VALUE ( 'Edit Config and Locations' );
    
END