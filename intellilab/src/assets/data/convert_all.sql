SET SQL_SAFE_UPDATES = 0;
CALL setup_user_role();
CALL convert_users();
CALL setup_operation();
CALL map_role_to_operation();

CALL convert_locations();
CALL convert_settings();
CALL setup_catalog_numbers();
UPDATE somrusoft.client_company set somrusoft.client_company.address = 'c/o World Baltic Trade UAB'
where somrusoft.client_company.address like '%World Baltic Trade UAB%';
-- deal with dup cat #s
UPDATE somrusoft.product p set p.cat = 'SB-014-052-1' where p.cat = 'SB-014-052' and p.dbid = 51;
UPDATE somrusoft.product p set p.cat = 'SB-02-730-1' where p.cat = 'SB-02-730' and p.dbid = 98;
UPDATE somrusoft.product p set p.cat = 'SB-06-019DET-1' where p.cat = 'SB-06-019DET' and p.dbid = 11;
CALL convert_clients();
CALL convert_products();
CALL convert_recipes();
CALL create_recipe_children();
-- deal with dup cat #s
UPDATE somrusoft.kit k set k.cat = 'SB-05-0103-1' where cat = 'SB-05-0103';
UPDATE somrusoft.kit k set k.cat = 'SB-05-0127-1' where cat = 'SB-05-0127';
UPDATE somrusoft.kit k set k.cat = 'SB-001-0167-1' where cat = 'SB-001-0167';
CALL convert_kits();
CALL convert_items();
CALL convert_item_details();
CALL convert_quotes();
CALL convert_order_items();
CALL convert_user_histories();
CALL convert_invoices();
CALL setup_sales_number_seq();
