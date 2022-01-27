CREATE DEFINER=`root`@`localhost` PROCEDURE `map_role_to_operation`()
BEGIN
  /* Assuming ops are as follows as confignured by setup_operations proc
1	View Clients
2	Manage Clients
3	View Sales
4	Manage Sales
5	View User History
6	View Users
7	Manage Users
8	Generate Catalogs
9	View Inventory
10	Manage Inventory
11	View Immunizing Schedules
12	Manage Immunizing Schedules
13	View SOPs
14	Edit SOPs
15	Review SOPs
16	View Kits
17	Edit Kits
18	Review Kits
19	View Orders
20	Request Orders
21	Manage Orders
22	View Products
23	Manage Products
24	Review Products
25	View Equipment
26	Manage Equipment
27	Review Equipment
28	View Worksheets
29	Edit Worksheet Design
30	Execute Worksheet Instance
31	Review Worksheets
32  View Config and Locations
33  Edit Config and Locations
  */
  -- give all managers (1,2,4) all operations
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 1 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 2 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 3 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 4 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 5 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 6 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 7 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 8 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 9 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 10 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 11 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 12 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 13 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 14 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 15 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 16 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 17 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 18 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 19 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 20 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 21 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 22 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 23 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 24 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 25 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 26 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 27 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 28 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 29 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 30 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 1, 31 );

  
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 1 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 2 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 3 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 4 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 5 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 6 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 7 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 8 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 9 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 10 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 11 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 12 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 13 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 14 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 15 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 16 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 17 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 18 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 19 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 20 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 21 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 22 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 23 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 24 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 25 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 26 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 27 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 28 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 29 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 30 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 2, 31 );

  
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 1 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 2 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 3 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 4 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 5 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 6 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 7 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 8 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 9 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 10 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 11 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 12 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 13 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 14 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 15 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 16 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 17 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 18 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 19 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 20 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 21 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 22 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 23 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 24 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 25 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 26 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 27 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 28 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 29 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 30 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 4, 31 );

  
  -- give reg users (3) access to view plus 'sciency' stuff
  /*
1	View Clients
3	View Sales
5	View User History
6	View Users
8	Generate Catalogs
9	View Inventory
10	Manage Inventory
11	View Immunizing Schedules
12	Manage Immunizing Schedules
13	View SOPs
14	Edit SOPs
16	View Kits
17	Edit Kits
19	View Orders
20	Request Orders
22	View Products
23	Manage Products
25	View Equipment
26	Manage Equipment
28	View Worksheets
30	Execute Worksheet Instance
  */
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 1 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 3 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 5 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 6 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 8 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 9 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 10 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 11 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 12 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 13 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 14 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 16 );
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 17);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 19);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 20);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 22);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 23);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 25);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 26);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 28);
  insert into il_role_operation_mapping( user_role_id, operation_id ) values ( 3, 30);
  
  
  
  
  
END