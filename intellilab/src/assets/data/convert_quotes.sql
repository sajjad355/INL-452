CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_quotes`()
BEGIN
  DECLARE done1, done2, done3, done4, done5 BOOLEAN DEFAULT FALSE;

  DECLARE qDbid bigint;
  DECLARE qClientid bigint;
  DECLARE qComplete int;
  DECLARE qCreate_date date;
  DECLARE qExpire_date date;
  DECLARE qPayment_type varchar(255);
  DECLARE qQuote_num bigint;
  DECLARE qRevision bigint;
  DECLARE qRevision_date date;
  DECLARE qSale_repid bigint;
  DECLARE qHandlefee double;
  DECLARE qShippingfee double;
  DECLARE qTax double;
  DECLARE qTaxrate varchar(255);
  DECLARE qNote varchar(5000);
  DECLARE qEditby varchar(255);
  DECLARE qModify date;
  DECLARE qCurrency varchar(255);
  DECLARE qCurrencyrate double;
  DECLARE qShippingaddressid bigint;

  DECLARE qClientid2 bigint;
  DECLARE qContactid2 bigint;
  DECLARE qShippingaddressid2 bigint;

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
  DECLARE quoteId bigint;

  DECLARE curQuotes CURSOR FOR
	SELECT dbid,
           clientid,
           complete,
           create_date,
           expire_date,
           payment_type,
           quote_num,
           revision,
           revision_date,
           sale_repid,
           handlefee,
           shippingfee,
           tax,
           taxrate,
           note,
           editby,
           modify,
           currency,
           currencyrate,
           shippingaddressid
    FROM somrusoft.quote;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;


  OPEN curQuotes;
  getQuotes: LOOP
	FETCH FROM curQuotes INTO qDbid,qClientid,qComplete,qCreate_date,qExpire_date,qPayment_type,qQuote_num,qRevision,qRevision_date,qSale_repid,qHandlefee,qShippingfee,qTax,qTaxrate,qNote,qEditby,qModify,qCurrency,qCurrencyrate,qShippingaddressid;
	IF done1 THEN
		CLOSE curQuotes;
		LEAVE getQuotes;
	END IF;

    set qClientid2 = null;
	set qContactid2 = null;
    set qShippingaddressid2 = null;

    -- get equiv client id in new model
    BLOCK1 : BEGIN
    DECLARE curClient CURSOR FOR
	SELECT icc.client_company_id
    FROM somrusoft.client_company cc, somrusoft.il_client_company icc
    where REPLACE(cc.company, '\n', '' ) = icc.company_name and
          cc.dbid =  qClientid;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;

	OPEN curClient;
	getClient: LOOP
		FETCH FROM curClient INTO qClientid2;
        if done2 THEN
            CLOSE curClient;
            SET done2 = FALSE;
            LEAVE getClient;
        end if;

	END LOOP getClient;
    END BLOCK1;


    -- get equiv contact id in new model
    BLOCK2 : BEGIN
    DECLARE curContact CURSOR FOR
	SELECT icc.client_company_id
    FROM somrusoft.client_contact cc, somrusoft.il_client_contact icc
    where REPLACE(cc.name, '\n', '' ) = icc.name and
          cc.dbid =  qSale_repid;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;

	OPEN curContact;
	getContact: LOOP
		FETCH FROM curContact INTO qContactid2;
        if done2 THEN
            CLOSE curContact;
            SET done2 = FALSE;
            LEAVE getContact;
        end if;

	END LOOP getContact;
    END BLOCK2;


    -- get equiv shipping address id in new model
    BLOCK3 : BEGIN
    DECLARE curAddress CURSOR FOR
	SELECT icsa.address_id
    FROM somrusoft.shipping_address sa, somrusoft.il_company_shipping_address icsa
    where sa.address = icsa.address_line1 and
          sa.city = icsa.city and
          sa.dbid =  qShippingaddressid;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done3 = TRUE;

	OPEN curAddress;
	getAddress: LOOP
		FETCH FROM curAddress INTO qShippingaddressid2;
        if done3 THEN
            CLOSE curAddress;
            SET done3 = FALSE;
            LEAVE getAddress;
        end if;

	END LOOP getAddress;
    END BLOCK3;

    -- just get any arbitrary shipping address for the company if it is not defined
    if qShippingaddressid2 is null then
      select min(address_id)
      into qShippingaddressid2
      from somrusoft.il_company_shipping_address
      where client_company_id  = qClientid;
    end if;

    -- default is US dollars
    if qCurrency is null or qCurrency = '' then
      set qCurrency = 'USD';
    end if;

    if qCurrencyrate is null or qCurrencyrate = 0 then
      set qCurrencyrate = 1;
    end if;


    -- insert the quote
    if (qClientid2 is null) or (qContactid2 is null) or (qShippingaddressid2 is null) then
       iterate getQuotes;
    end if;

    insert into somrusoft.il_quote(
	  active,
	  complete,
	  created_date,
	  currency,
	  currency_rate,
	  edited_by,
	  expiration_date,
	  handling_fee,
	  modified_on,
	  note,
	  payment_type,
	  quote_number,
	  revision,
	  revision_date,
	  shipping_fee,
	  tax,
	  tax_rate,
	  client_id,
	  client_contact_id,
	  shipping_address_id,
      old_quote_id)
    values (
	   1,
	   qComplete,
	   qCreate_date,
	   qCurrency,
	   qCurrencyrate,
	   IFNULL( qEditby, 'peter' ),
	   qExpire_date,
	   qHandlefee,
	   IFNULL( qModify, qCreate_date ),
	   qNote,
	   qPayment_type,
	   qQuote_num,
	   qRevision,
	   qRevision_date,
	   qShippingfee,
	   qTax,
	   qTaxrate,
	   qClientid2,
	   qContactid2,
	   qShippingaddressid2,
       qDbid
    );

    SELECT LAST_INSERT_ID() into quoteId;


    -- now get the line items and insert them
    BLOCK4 : BEGIN
    DECLARE curLineItems CURSOR FOR
	select
      cat, footnote, item_discount, item_id, item_quan, item_type, name, price, size, editby, modify, total_price
    from somrusoft.sale_link
    where link_type = 'quote'
    and link_id = qDbid;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done4 = TRUE;

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
        if done4 THEN
            CLOSE curLineItems;
            SET done4 = FALSE;
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

        insert into somrusoft.il_quote_line_item(
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
			 quote_id,
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
             quoteId,
             salesItemId
        );


	END LOOP getLineItems;
    END BLOCK4;

 END LOOP getQuotes;
END
