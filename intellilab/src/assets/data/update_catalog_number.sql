update somrusoft.il_catalog_sequence set catalog_number_type = 'ALL' where catalog_number_type = 'Product';
delete from somrusoft.il_catalog_sequence where catalog_number_type = 'Kit';