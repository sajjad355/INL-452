CREATE DEFINER=`root`@`localhost` PROCEDURE `setup_sales_number_seq`()
BEGIN
  
  DECLARE max_quote_id bigint;
  DECLARE max_invoice_id bigint;
  
  SELECT max(convert(quote_number, signed)) into max_quote_id from somrusoft.il_quote;
  SELECT max(convert(invoice_number, signed)) into max_invoice_id from somrusoft.il_invoice;
  
  insert into somrusoft.il_sales_number_sequence(
    sales_number_sequence_type, 
    sales_number_sequence_value
  ) values ( 
    'QUOTE',
    max_quote_id
  );
  
   insert into somrusoft.il_sales_number_sequence(
    sales_number_sequence_type, 
    sales_number_sequence_value
  ) values ( 
    'INVOICE',
    max_invoice_id
  );
END