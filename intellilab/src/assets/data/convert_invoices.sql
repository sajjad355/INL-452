CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_invoices`()
BEGIN
   DECLARE done1, done2, done3, done4, done5, done6, done7 BOOLEAN DEFAULT FALSE;

   DECLARE iDbid bigint;
   DECLARE iBill_address varchar(255);
   DECLARE iBill_attn varchar(255);
   DECLARE iClient_id bigint;
   DECLARE iCourier varchar(255);
   DECLARE iCurrency varchar(255);
   DECLARE iCurrencyrate double;
   DECLARE iDate_create date;
   DECLARE iEditby varchar(255);
   DECLARE iHandle_fee double;
   DECLARE iInvoice_num bigint;
   DECLARE iModify date;
   DECLARE iNote varchar(5000);
   DECLARE iPayment_term varchar(255);
   DECLARE iPo_num varchar(255);
   DECLARE iProform varchar(255);
   DECLARE iQuote_id bigint;
   DECLARE iRequisitioner varchar(255);
   DECLARE iRevision bigint;
   DECLARE iRevision_date date;
   DECLARE iShip_address varchar(255);
   DECLARE iShip_attn varchar(255);
   DECLARE iShipping_term varchar(255);
   DECLARE iShippingaddressid bigint;
   DECLARE iShippingfee double;
   DECLARE iTax double;
   DECLARE iType varchar(255);

   DECLARE iClientid2 bigint;
   DECLARE iBillingaddressid2 bigint;
   DECLARE iShippingaddressid2 bigint;
   DECLARE iQuoteId2 bigint;
   DECLARE iClientContactid2 bigint;

   DECLARE slCat varchar(255);
   DECLARE slFootnote varchar(500);
   DECLARE slItem_discount double;
   DECLARE slItem_id bigint;
   DECLARE slItem_quan double;
   DECLARE slItem_type varchar(255);
   DECLARE slName varchar(255);
   DECLARE slPrice double;
   DECLARE slSize varchar(255);
   DECLARE slEditby varchar(255);
   DECLARE slModify date;
   DECLARE slTotal_price double;

   DECLARE salesItemId bigint;
   DECLARE invoiceId bigint;
   DECLARE invoiceProforma int;

   DECLARE curInvoices CURSOR FOR
	SELECT   dbid,
			 bill_address,
			 bill_attn,
			 client_id,
			 courier,
			 currency,
			 currencyrate,
			 date_create,
			 editby,
			 handle_fee,
			 invoice_num,
			 modify,
			 note,
			 payment_term,
			 po_num,
			 proform,
			 quote_id,
			 requisitioner,
			 revision,
			 revision_date,
			 ship_address,
			 ship_attn,
			 shipping_term,
			 shippingaddressid,
			 shippingfee,
			 tax,
             type
    FROM somrusoft.invoice;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;

  OPEN curInvoices;
  getInvoices: LOOP
	FETCH FROM curInvoices INTO
        iDbid ,
		iBill_address ,
		iBill_attn ,
		iClient_id ,
		iCourier ,
		iCurrency ,
		iCurrencyrate ,
		iDate_create ,
		iEditby ,
		iHandle_fee ,
		iInvoice_num ,
		iModify ,
		iNote ,
		iPayment_term ,
		iPo_num ,
		iProform ,
		iQuote_id ,
		iRequisitioner ,
		iRevision ,
		iRevision_Date ,
		iShip_address ,
		iShip_attn ,
		iShipping_term ,
		iShippingaddressid ,
		iShippingfee ,
		iTax ,
		iType;

    IF done1 THEN
		CLOSE curInvoices;
		LEAVE getInvoices;
	END IF;

    set iClientid2 = null;
	set iBillingaddressid2 = null;
    set iShippingaddressid2 = null;
    set iQuoteid2 = null;
    set iClientContactid2 = null;

    if iType = 'Product invoice' then
      set iType = 'Product Invoice';
    else
      set iType = 'Service Invoice';
    end if;

    if iProform = 'Yes' then
      set invoiceProforma = 1;
    else
      set invoiceProforma = 0;
    end if;

    -- get equiv client id in new model
    BLOCK1 : BEGIN
    DECLARE curClient CURSOR FOR
	SELECT icc.client_company_id
    FROM somrusoft.client_company cc, somrusoft.il_client_company icc
    where REPLACE(cc.company, '\n', '' ) = icc.company_name and
          cc.dbid =  iClient_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;

	OPEN curClient;
	getClient: LOOP
		FETCH FROM curClient INTO iClientid2;
        if done2 THEN
            CLOSE curClient;
            SET done2 = FALSE;
            LEAVE getClient;
        end if;

	END LOOP getClient;
    END BLOCK1;

    -- get equiv shipping address id in new model
    BLOCK2 : BEGIN
    DECLARE curAddress CURSOR FOR
	SELECT icsa.address_id
    FROM somrusoft.shipping_address sa, somrusoft.il_company_shipping_address icsa
    where sa.address = icsa.address_line1 and
          sa.city = icsa.city and
          sa.dbid =  iShippingaddressid;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done3 = TRUE;

	OPEN curAddress;
	getAddress: LOOP
		FETCH FROM curAddress INTO iShippingaddressid2;
        if done3 THEN
            CLOSE curAddress;
            SET done3 = FALSE;
            LEAVE getAddress;
        end if;

	END LOOP getAddress;
    END BLOCK2;

    -- just get any arbitrary shipping address for the company if it is not defined
    if iShippingaddressid2 is null then
      select min(address_id)
      into iShippingaddressid2
      from somrusoft.il_company_shipping_address
      where client_company_id  = iClientid2;
    end if;

    -- get equiv billing address id in new model
    BLOCK3 : BEGIN
    DECLARE curBillAddress CURSOR FOR
	SELECT ia.address_id
    FROM somrusoft.il_address ia, somrusoft.il_client_company icc
    where icc.billing_address_id = ia.address_id and
          icc.client_company_id = iClientid2;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done4 = TRUE;

    OPEN curBillAddress;
	getBillAddress: LOOP
		FETCH FROM curBillAddress INTO iBillingaddressid2;
        if done4 THEN
            CLOSE curBillAddress;
            SET done4 = FALSE;
            LEAVE getBillAddress;
        end if;

	END LOOP getBillAddress;
    END BLOCK3;

    BLOCK4 : BEGIN
    DECLARE curQuote CURSOR FOR
	SELECT quote_id
    FROM somrusoft.il_quote
    where old_quote_id = iQuote_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done5 = TRUE;

    OPEN curQuote;
	getQuote: LOOP
		FETCH FROM curQuote INTO iQuoteId2;
        if done5 THEN
            CLOSE curQuote;
            SET done5 = FALSE;
            LEAVE getQuote;
        end if;

	END LOOP getQuote;
    END BLOCK4;


    BLOCK5 : BEGIN
    DECLARE curContact CURSOR FOR
	SELECT client_contact_id
    FROM somrusoft.il_client_contact
    where client_company_id = iClientid2 and
		  name = iRequisitioner;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done6 = TRUE;

    OPEN curContact;
	getContact: LOOP
		FETCH FROM curContact INTO iClientContactid2;
        if done6 THEN
            CLOSE curContact;
            SET done6 = FALSE;
            LEAVE getContact;
        end if;

	END LOOP getContact;
    END BLOCK5;

    -- if not found get any client contact
    if iClientContactid2 is null then
		BLOCK6 : BEGIN
		DECLARE curContact2 CURSOR FOR
		SELECT client_contact_id
		FROM somrusoft.il_client_contact
		where client_company_id = iClientid2;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done7 = TRUE;

		OPEN curContact2;
		getContact2: LOOP
			FETCH FROM curContact2 INTO iClientContactid2;
			if done7 THEN
				CLOSE curContact2;
				SET done7 = FALSE;
				LEAVE getContact2;
			end if;

		END LOOP getContact2;
		END BLOCK6;
    end if;

    -- default is US dollars
    if iCurrency is null or iCurrency = '' then
      set iCurrency = 'USD';
    end if;

    if iCurrencyrate is null or iCurrencyrate = 0 then
      set iCurrencyrate = 1;
    end if;



    if ( iClientid2 is null or
	    iBillingaddressid2 is null or
        iShippingaddressid2 = null ) then
	   iterate getInvoices;
    end if;

    -- insert the invoice
    insert into il_invoice(
		 billing_attention,
		 courier,
		 currency,
		 currency_rate,
		 date_created,
		 edited_by,
		 handling_fee,
		 invoice_number,
		 modified_on,
		 note,
		 payment_type,
		 proforma,
		 purchase_order_number,
		 quote_id,
		 revision,
		 revision_date,
		 shipping_attention,
		 shipping_fee,
		 shipping_term,
		 tax,
         tax_rate,
		 type,
		 billing_address_id,
		 client_id,
         client_contact_id,
		 shipping_address_id,
         old_invoice_id,
         active)
   values (
         iBill_attn,
         iCourier,
         iCurrency,
         iCurrencyrate,
         ifnull(iDate_create, Now() ),
         ifnull( iEditby, 'peter' ),
         ifnull( iHandle_fee, 0),
         iInvoice_num,
         ifnull(iModify, Now() ),
         iNote,
         iPayment_term,
         invoiceProforma,
         iPo_num,
         iQuoteId2,
         iRevision,
         iRevision_date,
         iShip_attn,
         iShippingfee,
         iShipping_term,
         iTax,
         'No Tax',
         iType,
         iBillingaddressid2,
         iClientid2,
         iClientContactid2,
         iShippingaddressid2,
         iDbid,
         1
   );

   SELECT LAST_INSERT_ID() into invoiceId;


    -- now get the line items and insert them
    BLOCK5 : BEGIN
    DECLARE curLineItems CURSOR FOR
	select
      cat, footnote, item_discount, item_id, item_quan, item_type, name, price, size, editby, modify, total_price
    from somrusoft.sale_link
    where link_type = 'invoice'
    and link_id = iDbid;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done6 = TRUE;

	OPEN curLineItems;
	getLineItems: LOOP
		FETCH FROM curLineItems INTO
		  slCat,
          slFootnote,
          slItem_discount,
          slItem_id,
          slItem_quan,
          slItem_type,
          slName,
          slPrice,
          slSize,
          slEditby,
          slModify,
          slTotal_price;
        if done6 THEN
            CLOSE curLineItems;
            SET done6 = FALSE;
            LEAVE getLineItems;
        end if;

        -- get sales item id equivalent to item id and type
        if ( slItem_type = 'inventory' ) then
          iterate getLineItems;
		elseif ( slItem_type = 'kit' ) then
          select kit_id into salesItemId
          from somrusoft.il_kit where old_kit_id = slItem_id;
		elseif ( slItem_type = 'product' ) then
           select product_id into salesItemId
          from somrusoft.il_product where old_product_id = slItem_id;
        end if;

        if isnull(salesItemId) then
          iterate getLineItems;
        end if;

        insert into somrusoft.il_invoice_line_item(
             catalog_number,
			 editby,
			 foot_note,
			 item_discount,
			 item_quantity,
			 modified_on,
			 name,
			 price,
			 size,
			 total_price,
			 invoice_id,
			 sales_item_id
        ) values (
			 slCat,
			 IFNULL( slEditby, 'peter' ),
             slFootnote,
             slItem_discount,
             slItem_quan,
             IFNULL( slModify, Now() ),
             slName,
             slPrice,
             slSize,
             slTotal_price,
             invoiceId,
             salesItemId
        );


	END LOOP getLineItems;
    END BLOCK5;


  END LOOP getInvoices;
END
