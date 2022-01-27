CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_clients`()
BEGIN
 DECLARE done1, done2, done3 BOOLEAN DEFAULT FALSE; 
 
 
 
 DECLARE il_client_company_id bigint;
 DECLARE ccDbid bigint;
 DECLARE ccAddress varchar(255);
 DECLARE ccAddress2 varchar(255);
 DECLARE ccBAddress varchar(255);
 DECLARE ccBAddress2 varchar(255);
 DECLARE ccBCity varchar(255);
 DECLARE ccBCountry varchar(255);
 DECLARE ccBPostalCode varchar(255);
 DECLARE ccBProvince varchar(255);
 DECLARE ccCity varchar(255);
 DECLARE ccCompany varchar(255);
 DECLARE ccCountry varchar(255);
 DECLARE ccPostalCode varchar(255);
 DECLARE ccProvince varchar(255);
 
 DECLARE cEmail varchar(255);
 DECLARE cExt varchar(255);
 DECLARE cFax varchar(255);
 DECLARE cName varchar(255);
 DECLARE cPhone varchar(255);
 DECLARE cRole varchar(255);
 

 DECLARE sAddress varchar(255);
 DECLARE sAddress2 varchar(255);
 DECLARE sAddressCompanyName varchar(255); 
 DECLARE sCity varchar(255); 
 DECLARE sCountry varchar(255); 
 DECLARE sPostal_code varchar(255);
 DECLARE sProvince varchar(255);
 
 
 
 DECLARE curClients CURSOR FOR 
	SELECT dbid,
           REPLACE(address, "\n", ""), 
           REPLACE(address2,  "\n", ""), 
           REPLACE(baddress,  "\n", ""),
           REPLACE(baddress2,  "\n", ""),
           REPLACE(bcity,  "\n", ""),
           REPLACE(bcountry,  "\n", ""),
           REPLACE(bpostal_code,  "\n", ""),
           REPLACE(bprovince,  "\n", ""),
           REPLACE(city,  "\n", ""),
           REPLACE(company, "\n", ""),
           REPLACE(country,  "\n", ""),
           REPLACE(postal_code,  "\n", ""),
           REPLACE(province,  "\n", "")
    FROM somrusoft.client_company;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE;
     
  
  
  OPEN curClients;
  getClients: LOOP
	FETCH FROM curClients INTO  ccDbid, ccAddress, ccAddress2, ccBAddress, ccBAddress2, ccBCity, ccBCountry, ccBPostalCode, ccBProvince, ccCity, ccCompany, ccCountry, ccPostalCode, ccProvince;
	IF done1 THEN 
		CLOSE curClients; 
		LEAVE getClients;
	END IF;
	
    -- insert the billing address row
    insert into somrusoft.il_address(address_line1, address_line2, city, country, edited_by, modified_on, postal_code, province ) 
    values ( ccBAddress, ccBAddress2, ccBCity, ccBCountry, 'peter', Now(), ccBPostalCode, ccBProvince );
	
    -- insert the company
    insert into somrusoft.il_client_company( active, company_name, edited_by, modified_on, billing_address_id )
    values ( 1, ccCompany, 'peter', Now(), LAST_INSERT_ID() );
    
    SELECT LAST_INSERT_ID() into il_client_company_id;
    
    -- insert the non billing address fields from company into shipping address if they exist
	/* Not needed as is duplicated in shipping address
    if ( ccAddress is not null ) then
       insert into somrusoft.il_company_shipping_address(  address_line1, address_line2, city, country, edited_by, modified_on, postal_code, province, client_company_id)
	   values ( ccAddress, ccAddress2, ccCity, ccCountry, 'peter', Now(), ccPostalCode, ccProvince, il_client_company_id );
    end if;   
    */
    
	-- get all the contacts
    BLOCK1 : BEGIN
    DECLARE curContacts CURSOR FOR 
	SELECT REPLACE(email, "\n", ""), 
           REPLACE(ext, "\n", ""),  
           REPLACE(fax, "\n", ""),  
           REPLACE(name, "\n", ""), 
           REPLACE(phone, "\n", ""), 
           REPLACE(role, "\n", "")
    FROM somrusoft.client_contact where company_id = ccDbid;  
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE;
    
	OPEN curContacts;
	getContacts: LOOP
		FETCH FROM curContacts INTO cEmail,cExt,cFax,cName,cPhone,cRole;
        if done2 THEN
            CLOSE curContacts;
            SET done2 = FALSE;
            LEAVE getContacts;
        end if;
        
        insert into somrusoft.il_client_contact( active, edited_by, email, ext, fax, modified_on, name, phone, role, client_company_id)
        values ( 1, 'peter', cEmail, cExt, cFax, Now() , cName, cPhone, cRole, il_client_company_id );
        
        
	END LOOP getContacts;
    END BLOCK1;
    
    -- insert the shipping address
    
    BLOCK2 : BEGIN
    DECLARE curShipAddress CURSOR FOR 
	SELECT REPLACE( addresscompanyname, "\n", ""), 
           REPLACE(address, "\n", ""),  
           REPLACE(address2, "\n", ""),  
           REPLACE(city, "\n", ""), 
           REPLACE(country, "\n", ""), 
           REPLACE(postal_code, "\n", ""), 
           REPLACE(province, "\n", "")
    FROM somrusoft.shipping_address where companydbid = ccDbid;  
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done3 = TRUE;
    
	OPEN curShipAddress;
	getShipAddress: LOOP
    
  
		FETCH FROM curShipAddress INTO sAddressCompanyName, sAddress, sAddress2, sCity, sCountry, sPostal_code, sProvince;
        if done3 THEN
            CLOSE curShipAddress;
            SET done3 = FALSE;
            LEAVE getShipAddress;
        end if;
        
        insert into somrusoft.il_company_shipping_address( active, location_name, address_line1, address_line2, city, country, edited_by, modified_on, postal_code, province, client_company_id)
        values ( 1, sAddressCompanyName, sAddress, sAddress2, sCity, sCountry, 'peter', Now(), sPostal_code, sProvince, il_client_company_id );
        
        
	END LOOP getShipAddress;
    END BLOCK2;
    
    
  END LOOP getClients;
 
  
END