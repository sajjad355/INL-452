-- insert the setting
insert into somrusoft.il_setting( name, description, edited_by, modified_on) 
values ( 'companyCountry', 'The country where the company is located', 'Peter Scott', Now() );

-- insert the setting value	
insert into somrusoft.il_setting_value( value, setting_id, edited_by, modified_on )
values ( 'Canada', LAST_INSERT_ID(), 'Peter Scott', Now() );