-- =============================================================================
-- Please add all test data here, for integration tests & demonstrations
-- =============================================================================

-- Austrian Test users, the NS is defined in the init_data script, UserId >= 7
insert into users (external_id, status, username, password, firstname, lastname) values ('39e6ff57-bcd9-4b5f-9b56-323a2434b5f8', 'ACTIVE', 'austria1', '1a72c0aee217335849f53b734abcb4328cb6d3ddf51f03568e1d322f1e4e134c', 'User1', 'Austria');
insert into users (external_id, status, username, password, firstname, lastname) values ('98cb1a37-1b53-4706-98df-1206afa8b1a5', 'ACTIVE', 'austria2', '25881e68fd8be6d4702e0ad4226145d3bf2c0ff01cb993824a1ebba63e495936', 'User2', 'Austria');
insert into users (external_id, status, username, password, firstname, lastname) values ('f65dd21e-611f-404d-9e35-a9724b05c90a', 'ACTIVE', 'austria3', 'f87cba3df6b6c3867465a8aa5d9f9232aaea3e1a6e8e6c985e02681f4f499a9a', 'User3', 'Austria');
insert into users (external_id, status, username, password, firstname, lastname) values ('1c22a778-755a-4ae1-84ca-ec9ab7b5b521', 'ACTIVE', 'austria4', '0fe6501c94463f47c77d81dd5d53156663b5bcd8ff06a2f9fe47487b8a66717d', 'User4', 'Austria');
insert into users (external_id, status, username, password, firstname, lastname) values ('bc9c9a21-e237-4d5d-aab0-6da3b06f28af', 'ACTIVE', 'austria5', '49fcb4754387644d6b917bea2d1dabc5e557fda7e8f034101c6154679d422865', 'User5', 'Austria');

-- Adding the Austrian Users to the Austrian Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values ( 7, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 7, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 8, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 8, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 9, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 9, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values (10, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values (10, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values (11, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values (11, 11, 2);

-- Croatian Test users, the NS is defined in the init_data script, UserId >= 12
insert into users (external_id, status, username, password, firstname, lastname) values ('6a112a56-fb00-449a-a1b7-b07d79a81733', 'ACTIVE', 'croatia1', '7d50ce2dc79e9a017fe8160468619154379f1e3605b08011cbc2abf2e591edd6', 'User1', 'Croatia');
insert into users (external_id, status, username, password, firstname, lastname) values ('18093bba-3e24-4ddb-8e3b-db4d4f9a5381', 'ACTIVE', 'croatia2', 'f43b026ca33b17e44dd9b248aee32fb1f17a0294a59bbc68949efd4a85f4c849', 'User2', 'Croatia');
insert into users (external_id, status, username, password, firstname, lastname) values ('21ea1fc3-c6b7-4e5e-914e-8c0e3537c0f6', 'ACTIVE', 'croatia3', '4c97697fc3c210ac987380bc91961e22e7c199dddf2399a56a930ef820ce9f1e', 'User3', 'Croatia');
insert into users (external_id, status, username, password, firstname, lastname) values ('d0865ed9-e9d6-4cf0-8626-b813797958b8', 'ACTIVE', 'croatia4', '90a1f8166d689dcf31f9ad68df103adcd6e16df19d02064cae3e4793170eba20', 'User4', 'Croatia');
insert into users (external_id, status, username, password, firstname, lastname) values ('f57a1c4a-ab45-4b5f-9b3d-93a1f43711e3', 'ACTIVE', 'croatia5', '8078dfc2a4fbc953a994b8d2a1f7314e444ebc108d22e3a8694adc8f7281faf7', 'User5', 'Croatia');

-- Adding the Croatian Users to the Croatian Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (12, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values (12, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (13, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values (13, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (14, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values (14, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (15, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values (15, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (16, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values (16, 13, 2);

-- German Test users, the NS is defined in the init_data script, UserId >= 17
insert into users (external_id, status, username, password, firstname, lastname) values ('63459109-550a-43ee-afa8-10ef3f0e609d', 'ACTIVE', 'germany1', '8475a2e11b1b2669d453655e729079724c9fc0d617b3b7bb295239841ff8ac4d', 'User1', 'Germany');
insert into users (external_id, status, username, password, firstname, lastname) values ('ee75749a-c484-4775-920e-98bdcbb6beeb', 'ACTIVE', 'germany2', 'd37c63ad15b819c6bf5f1a9f935331de5b6ff80e662b8a2564571beec1548c18', 'User2', 'Germany');
insert into users (external_id, status, username, password, firstname, lastname) values ('6b7541d4-e81b-4833-82eb-c49db934760e', 'ACTIVE', 'germany3', 'e4e162db7d885bdcacf9375300e5e7e1fc264a04cc37fadf4a4e5bcc89f11791', 'User3', 'Germany');
insert into users (external_id, status, username, password, firstname, lastname) values ('8d44cd1e-8484-47c2-844d-ccf01efab735', 'ACTIVE', 'germany4', 'de40554bc4c14ae46a0d1772187a6427c64e88b07e90a9c63907f4a005e510ab', 'User4', 'Germany');
insert into users (external_id, status, username, password, firstname, lastname) values ('83eee10e-4653-4420-b723-56a4df4f8b4e', 'ACTIVE', 'germany5', 'dd656825487c25f247b12245d589e4439ec30d5f1462135c6e23db80ca707ca3', 'User5', 'Germany');

-- Adding the German Users to the German Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (17, 16, 2);
insert into user_to_group (user_id, group_id, role_id) values (17, 17, 2);
insert into user_to_group (user_id, group_id, role_id) values (18, 16, 2);
insert into user_to_group (user_id, group_id, role_id) values (18, 17, 2);
insert into user_to_group (user_id, group_id, role_id) values (19, 16, 2);
insert into user_to_group (user_id, group_id, role_id) values (19, 17, 2);
insert into user_to_group (user_id, group_id, role_id) values (20, 16, 2);
insert into user_to_group (user_id, group_id, role_id) values (20, 17, 2);
insert into user_to_group (user_id, group_id, role_id) values (21, 16, 2);
insert into user_to_group (user_id, group_id, role_id) values (21, 17, 2);

-- Hungarian Test users, the NS is defined in the init_data script, UserId >= 22
insert into users (external_id, status, username, password, firstname, lastname) values ('3f80737f-5236-454d-8f6f-24173ca26cdb', 'ACTIVE', 'hungary1', '19fb4417f129a87c2a40ff60f4c0db14d43afb028d3d409c0f9bee6998f99cc1', 'User1', 'Hungary');
insert into users (external_id, status, username, password, firstname, lastname) values ('b6baf2d1-0ad5-49d7-8126-995391c71cbc', 'ACTIVE', 'hungary2', '035ca18f9240db0031702df4aa98828dcbcdfd68a0d49e6028eab7713b6d401f', 'User2', 'Hungary');
insert into users (external_id, status, username, password, firstname, lastname) values ('bc48f823-c8d4-42fc-8311-1bb7902b69a6', 'ACTIVE', 'hungary3', '5f65549d55ffffe88f1bca7fdca1711a8c341c7ff00e1d79376a50c125cba563', 'User3', 'Hungary');
insert into users (external_id, status, username, password, firstname, lastname) values ('a61f849f-5863-4b78-8954-c349f3422f3a', 'ACTIVE', 'hungary4', '8a4caaf668fa9770bb5df3031f4349ea955bc8bc6b81feda48afd547f1ed4d48', 'User4', 'Hungary');
insert into users (external_id, status, username, password, firstname, lastname) values ('1cab0fd3-89a9-462e-aa1c-e844576c551b', 'ACTIVE', 'hungary5', '0d76ccea08d283aab0c26b79e6c0b2e5e52e5b1586931d6423d1d518af03b04e', 'User5', 'Hungary');

-- Adding the Hungarian Users to the Hungarian Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (22, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (22, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (23, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (23, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (24, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (24, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (25, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (25, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (26, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (26, 21, 2);

-- Polish Test users, the NS is defined in the init_data script, UserId >= 27
insert into users (external_id, status, username, password, firstname, lastname) values ('b2acb9cd-7787-4c24-a34a-79527d2ffa8b', 'ACTIVE', 'poland1', 'dbc24290ec385e2f64273a3d5705719791afc6e07a913ffeb438b7aaa6416f39', 'User1', 'Poland');
insert into users (external_id, status, username, password, firstname, lastname) values ('b98c8639-cb61-46c1-b0af-74c14f2505c7', 'ACTIVE', 'poland2', '5ea6f3c38511755d1da08bcf045819dbe76e3615f61da93ae479cf56adf70f1a', 'User2', 'Poland');
insert into users (external_id, status, username, password, firstname, lastname) values ('a3c2e480-be1c-4e8d-b2fc-01711d5d8a6d', 'ACTIVE', 'poland3', 'd33980bd6447d4990f5cb68fdb81fcc326df455cb0bafbd7ebfde0ec8daf9c2c', 'User3', 'Poland');
insert into users (external_id, status, username, password, firstname, lastname) values ('9069e18a-5f67-43d5-977a-2e199dcc543b', 'ACTIVE', 'poland4', 'd069e568ab639b69c5d0c124260d0ab509b86e98cba344e257439d8f54b2cfb8', 'User4', 'Poland');
insert into users (external_id, status, username, password, firstname, lastname) values ('2a8db514-60a6-4c41-a188-51080aab8f76', 'ACTIVE', 'poland5', 'b600159d9cc150e02f5504c9e236b46edb3f7e6130c231fda0bbe785598b1720', 'User5', 'Poland');

-- Adding the Polish Users to the Polish Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (27, 18, 2);
insert into user_to_group (user_id, group_id, role_id) values (27, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (28, 18, 2);
insert into user_to_group (user_id, group_id, role_id) values (28, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (29, 18, 2);
insert into user_to_group (user_id, group_id, role_id) values (29, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (30, 18, 2);
insert into user_to_group (user_id, group_id, role_id) values (30, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (31, 18, 2);
insert into user_to_group (user_id, group_id, role_id) values (31, 19, 2);


INSERT INTO offers (ref_no, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0001-AB', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 11);
INSERT INTO offers (ref_no, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0002-AB', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 11);
INSERT INTO offers (ref_no, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0003-AB', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 11);
INSERT INTO offers (ref_no, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0004-AB', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 11);
