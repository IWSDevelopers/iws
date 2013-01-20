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


-- Add some offers for testing..
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0001-AB', '27d21139-c478-4c30-be57-aba0aa57ac2d', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 11);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0002-AB', '42dbdd0b-625a-4b97-b2c6-bf8f43bdc2b2', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'O', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B|M', 'IT|MATHEMATICS', '', 11);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0003-AB', '2768e03a-1574-4063-8d1f-3aae55df8cdd', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'A', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B|M|E', 'IT|MATHEMATICS', '', 11);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('AT-2013-0004-AB', '636c7603-6706-460f-82e7-cba97298edeb', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 11);

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HR-2013-0001', '88debcd6-7b50-4320-a3a4-9945070c48c0', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Zagreb University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 13);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HR-2013-0002', '2b1005b7-c7a3-46b2-b127-c621c2c411b2', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Zagreb University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'O', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 13);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HR-2013-0003', '3ce2a1c2-98e3-4289-90b4-adf286f4c5f3', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Zagreb University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'A', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 13);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HR-2013-0004', '081bcb91-04ce-4301-9bdf-81784750a96a', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Zagreb University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 13);

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0010-MU', '930e1616-b214-499c-9531-b833fcc2202b', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Munich University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 17);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0020-BE', '0ec09a52-bfe5-4d4b-b598-e873fcf25cb3', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Berlin University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'O', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 17);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0030-FU', '267a0959-06fb-4ffa-be8f-c7fb38ee8c1b', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Frankfurt University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'A', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 17);
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0040-HB', 'b2b3356c-5b41-474f-a3a1-ef27f0d107d3', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Hamburg University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', '2013-04-15', 'Needs to be funny\nExperience in JAVA', 1250.00, 'MONTHLY', FALSE, 'work bla bla - teis let your imagination lead you!', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', '', 17);

--SELECT `RefNo`, `Canteen`, `Currency`, `HoursWeekly`, `HoursDaily`, `Deduction`, `Employer`, `Address1`, `Address2`, `Business`, `Employees`, `ResponsibleWeb`, `From`, `To`, NULL, NULL, NULL, NULL, Language1, `Language1Level`, `Language1Or`, `Language2`, `Language2Level`, `Language2Or`, `Language3`, `Language3Level`, `LivingCost`, `LivingCostFrequency`, `Lodging`, `LodgingCost`, `LodgingCostFrequency`, `WeeksMin`, `WeeksMax`, `Airport`, `Transport`, `Deadline`, `OtherRequirements`, `Payment`, `PaymentFrequency`, `TrainingRequired`, `Workkind`, `Workplace`, `WorkType`, `Study`, `Faculty`, `Specialization`, 21 FROM `xs_Offer` where country = 'Hungary'
--INSERT (ref_no, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date,
--  language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES

-- =============================================================================
-- Offer Poland
-- =============================================================================
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0001', 'bef370cf-f3af-41a9-b806-bcbec12594da', FALSE, 'PLN', '35', '7', NULL, 'PL Employer1', 'Address1', 'Address2', 'Center of laser diagnostics and therapy in medicin', '50', 'www.employer1.com',
'2013-07-01', '2013-08-12', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
900, 'MONTHLY', 'arranged by IAESTE', 300, 'MONTHLY', '6', '6', 'Warszawa', NULL, '2013-03-31',
'MUST START ON 1st OF JULY!!! Team work!!',
1000, 'MONTHLY', NULL,
'Student will be part of team working in a researching projects related to image analysis in medical applications. Investigation of optical properties of tissue, influence of laser light on living cells, introduction of new healing methods by means of laser technologies, photodynamic diagnostics. Results of these investigations are implemented in photodynamic diagnostics and laser theray of tumors of urinary bladder, rheumatic disease healing, muliplex sclerosis, surgical laser therapy of burns and others (see Cener web  site) wit cooperation with Lodz Medical University',
'Lodz', NULL, 'M|E', 'PHYSICS', 'Applied Physics, Medicine', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0002', 'd916d2fa-9487-4043-9a5c-85e49c90c660', FALSE, 'PLN', '30', '6', NULL, 'PL Employer2', 'Address1', 'Address2', 'Architectural and Convervation Office', '6', 'www.employer2.com',
'2013-07-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'900', 'MONTHLY', 'arranged by IAESTE', 350, 'MONTHLY', '6', '6', 'Wroclaw', 'Bus, Tram, Taxi', '2013-03-31',
NULL,
1000, 'MONTHLY', NULL,
'Project Assistant\r\n\r\n-Helping in urban and landscape designing\r\n-Planning',
'Wroclaw', 'P', 'M|E', 'ARCHITECTURE', 'Landscape Architecture', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0003', 'e939d9a8-3ac6-474e-8c6d-2b45477fe189', TRUE, 'PLN', '40', '8', NULL, 'PL Employer3', 'Address1', 'Address2', 'IT Solutions to Telecommunications, Finance and Ba', '2200', 'www.employer3.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
880, 'MONTHLY', 'arranged by IAESTE', 330, 'MONTHLY', '8', '12', 'Krakow', NULL, '2013-03-31',
'Information Systems faculty prefered. G average grade at studies. Intention of further employment in one of Comarch Group companies after training.',
1500, 'MONTHLY', NULL,
'Telco platform\r\n\r\nAssistant and research work by the building modules of Telco networking management platform. Comarch offers complete suites of business support system (BSS) and operations support system (OSS) solutions that adress the critical strategic activities of fulfilment, assurance and billing, and enable communication providers to ensure new revenue streams. For our trainees we offer a very interesting work in production anddevelopment of our systems.',
'Krakow', 'R', 'E', 'IT', 'Information Systems', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0004', '4d9158f1-f885-4aec-b652-89a497b57bc0', TRUE, 'PLN', '30', '6', '20%', 'PL Employer4', 'Address1', 'Address2', 'Civil Engineering', '25', 'www.employer4.com',
'2013-05-14', '2013-07-18', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
1000, 'WEEKLY', 'arranged by IAESTE', 450, 'MONTHLY', '6', '6', 'Warszawa', NULL, '2013-03-31',
NULL,
1300, 'MONTHLY', NULL,
'Research and develop\r\n\r\nStudent will be involved in theoretical and practical work within self-compacting concrete, polymer-cement concrete, polymer concrete, bituminous composites. Road construction. Construction repairs-diagnosis and methods of repairs.',
'Warsaw', 'R', 'M', 'CIVIL_ENGINEERING', 'Building Materials', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0005', 'aa2c2fb0-98a6-4266-b3d1-5571bb4bdf38', FALSE, 'PLN', '40', '8', '20%', 'PL Employer5', 'Address1', 'Address2', 'Integration and data exchange in automatic systems', '8', 'www.employer5.com',
'2013-06-01', '2013-12-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
880, 'MONTHLY', 'arranged by IAESTE', 300, 'MONTHLY', '6', '12', 'Krakow', NULL, '2013-03-31',
'Knowledge of measurement and control equipment trade, data exchange protocol (MODBUS, M-BUS, OPC, DDE, SQL)',
900, 'MONTHLY', NULL,
'Programmer\r\n\r\nStudents work will involve\r\n\r\n1)introduction to ANT STUDIO software\r\n2)creating data exchange applicaations by student , writing programs in TCL/TK\r\n3)introduction to applications\r\n4)introduction to telemetry GPRS systems',
'Krakow', 'P', 'M|E', 'MECHANICAL_ENGINEERING', 'Drivers PLC, SCANDA Systems', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0006', '07855852-0a30-47e9-87d3-cbdfd0abb4e5', TRUE, 'PLN', '40', '8', '0 %', 'PL Employer6', 'Address1', 'Address2', 'International Software House', '2200', 'www.employer6.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'880', 'MONTHLY', 'IAESTE', '330', 'MONTHLY', '8', '12', 'Krakow Balice (KRK)', 'Krakow train/coach', '2013-03-31',
'Computer Science (or related) faculty preferred.\r\nG average grade at studies.\r\nIntention of further employment in one of Comarch Group companies after training.\r\nPerson from any UE country.',
'1500', 'MONTHLY', NULL,
'Assistant and research work by the building modules of Telco networking management platform. Comarch offers complete suites of business support system (BSS) and operations support system (OSS) solutions that adress the critical strategic activities of fulfilment, assurance and billing, and enable communication providers to ensure new revenue streams. For our trainees we offer a very intersting work in production and development of our systems.',
'Krakow', 'P', 'E', 'IT', 'non specific', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0007', '9ee20f30-2969-4c28-9070-8ad313c2ff99', FALSE, 'PLN', '40', '8', '20%', 'PL Employer7', 'Address1', 'Address2', 'Integration and data exchange in automatic systems', '8', 'www.employer7.com',
'2013-06-01', '2013-12-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'880', 'MONTHLY', 'arranged by IAESTE', '300', 'MONTHLY', '6', '12', 'Krakow - Balice intenational', 'Bus', '2013-03-31',
'Experience in programming in C/C++, java, SQL bases',
'900', 'MONTHLY', NULL,
'Students work will involve\r\n1. introduction to ANT STUDIO software used in integration of devices, communication, data exchange.\r\n2. introduction to TLC/TK language - writing modules to ANT STUDIO\r\n3. development of the sofware - specific project',
'Krakow', 'P', 'M|E', 'IT', 'Programming', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0008', 'c71099ab-3a5d-4e52-9814-6ffd9d57fbeb', FALSE, 'PLN', '35', '7', NULL, 'PL Employer8', 'Address1', 'Address2', 'Research Institute of general and ecological chemi', '4000', 'www.employer8.com',
'2013-06-15', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
900, 'MONTHLY', 'arranged by IAESTE', 300, 'MONTHLY', '6', '6', 'Warszawa', NULL, '2013-03-31',
NULL,
1000, 'MONTHLY', NULL,
'Member of one of the research teams working on the following projects (depending on student´s specialization and interests):\r\n1. Environmental Chemistry- electrochemical methods in the environmental protection. Student will apply electrochemical oxidation and/or reduction in the treatment of non-biodegradable components of wastewaters. Galwanostatic cyclic voltammetry and electrochemical impedance spectroscopy will be used in the investigations - asocha@p.lodz.pl\r\n2. Physics-chemical characterization of heterogeneous catalysts. Student will investigate various catalysts with application of temperature programmed reduction. XRD, TOF-SIMS, SEM, JCP jacryn@p.lodz.pl\r\n3. Crystal and molecular structure determination by XRD methods:-monocrystal diffractometers for data collection, computing data reduction, structure solution and refinement, structure description and anlysis-computer graphics- marekglo@p.lodz.pl',
'Lodz', 'R', 'M|E', 'ECOLOGY', 'Chemistry', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0009', 'aa4d0712-4c05-47ff-833b-87356a5229fa', FALSE, 'PLN', '30', '6', '0 %', 'PL Employer9', 'Address1', 'Address2', 'Civil Engineering', '20', 'www.employer9.com',
'2013-03-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'700', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '8', 'Berling, Goleniow, Warsaw', 'railway', '2013-03-31',
NULL,
'800', 'MONTHLY', NULL,
'Help in laboratory works (production and testing of samples made of asphalt and concrete; research of basic physical and mechenical properties of bitumes and asphalts). Help in field measurements of traffic noise.',
'Szczecin', 'R', 'E', 'CIVIL_ENGINEERING', NULL, '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0010', 'df16f5f9-68a4-4236-89fa-c281125e949e', TRUE, 'PLN', '40', '8', '20', 'PL Employer10', 'Address1', 'Address2', 'telecommunication', '1000', 'www.employer10.com',
'2013-06-18', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '300', NULL, '6', '8', 'Wroslaw', 'Bus', '2013-06-30',
'preferred ENGLISH NATIVE SPEAKER',
'1200', 'MONTHLY', NULL,
'Student will be involved in the documentation project for Intelligent Network. Student will be working as a technical writer and will be cooperating with software developers. Student will be also responsible for language correction of the existing documentation.  We offer:  - cutting edge technology;  - participation in a highly motivated and dynamic team;  - training period in multinational environment.',
'Wroclaw', 'P', 'M', 'IT', 'any', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0011', '06e5862b-0e03-4d72-85fc-c202a3b9788f', FALSE, 'PLN', '40', '8', '0', 'PL Employer11', 'Address1', 'Address2', 'Designing and manufacturing machinery and equipmen', '50', 'www.employer11.com',
'2013-07-01', '2013-09-01', NULL, NULL, NULL, NULL, 'GERMAN', 'E', 'A', 'ENGLISH', 'F', 'A', NULL, 'F',
'950', 'MONTHLY', 'IAESTE', '250', NULL, '4', '8', 'Poznan', 'Bus', '2013-06-15',
'Knowledge of technical drawing; MS Excell',
'250', NULL, NULL,
'The preparation of orders to delivery. The negotiation by purchase of materials.',
'Zlotniki/Poznan', 'P', 'M', 'MECHANICAL_ENGINEERING', 'Manufacturing Engineering', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0012', 'ab367c53-cfda-4d26-99d0-301080b4eec0', FALSE, 'PLN', '40', '8', '0', 'PL Employer12', 'Address1', 'Address2', 'Designing and manufacturing machinery and equipmen', '50', 'www.employer12.com',
'2013-06-18', '2013-09-21', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'950', 'MONTHLY', 'IAESTE', '250', NULL, '9', '12', 'Poznan', 'Bus', '2013-06-14',
'',
'250', 'WEEKLY', NULL,
'Help with creating technical documentation. Modelling 3D in programme CATIA v.5. Participation in projects realized by the company. Participation in business trips to customers.',
'Zlotniki/Poznan', 'P', 'E', 'MECHANICAL_ENGINEERING', 'Mechanical Eng.', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0013', 'd6bc8bf7-ade3-4258-a61e-25173bc14884', TRUE, 'PLN', '40', '8', '0', 'PL Employer13', 'Address1', 'Address2', 'sales;marketing;research&development', '20', 'www.employer13.com',
'2013-06-01', '2013-05-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'O', 'GERMAN', 'E', 'O', 'SPANISH', 'E',
'880', 'MONTHLY', 'IAESTE', '330', NULL, '12', '52', 'Krakow', 'train/coach', '2013-06-14',
'student with deep knowledge of chemistry as it is required to be well prepared during dealing with clients.',
'1500', 'MONTHLY', NULL,
'We are offering a great opportunity for internship in international company; with solid chemistry background and business aspects included.  Responsibilities of the trainee would be:  - mainly helping with telesales and telemarketing (cold calling)  - sales and sales administration  - preparing marketing / training materials for customers  - organizing seminars and customers visits  - organizing promotions  - marketing research / sales projects  - preparing PP presentations  - helping with competitive analysis  - maintaining customer databases  - contacts with some of the clients  - web pages',
'Krakow', 'N', 'M', 'CHEMISTRY', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0014', '2d9582f6-0c32-4abc-b6c0-f53521b9d25f', FALSE, 'PLN', '40', '8', '0', 'PL Employer14', 'Address1', 'Address2', 'advanced technolog. to develop big scale IT solut.', '140', 'www.employer14.com',
'2013-07-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'O', 'POLISH', 'G', NULL, NULL, NULL,
'550', 'MONTHLY', 'IAESTE', '0', NULL, '11', '12', 'Krakow', 'train/coach', '2013-06-14',
'',
'880', 'MONTHLY', NULL,
'Our expectations are:  - ability to code in Java - J2EE  - knowledge in code in one of the following products will be an advantage:  - WebMethods Integration/EAI  - PeopleSoft CRM  - Products of ATG Company (ATG Dynamo; ATG Portal)',
'Krakow', 'P', 'M', 'IT', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0015', '78774724-aef7-4b87-868e-76121d103895', FALSE, 'PLN', '40', '8', '0', 'PL Employer15', 'Address1', 'Address2', 'IT consulting; web site and application developmen', '30', 'www.employer15.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'550', 'MONTHLY', 'IAESTE', '0', NULL, '8', '12', 'Krakow', 'train', '2013-06-14',
NULL,
'1000', 'MONTHLY', NULL,
'-programming in object oriented languages  -learning extreme programming concepts in practice (pair programming; test first development; refactoring; etc.)  - working on web and desktop apllications  - helping with project management  - interacting with project teams and clients  - office management tasks  - other duties as assigned',
'Krakow', 'N', 'M', 'IT', NULL, '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0016', 'b917f1e8-bafe-4f20-9305-bea4b0d15a96', TRUE, 'PLN', '40', '8', '0', 'PL Employer16', 'Address1', 'Address2', 'Civil Engineering Design and Consulting', '50', 'www.employer16.com',
'2013-06-01', '2013-10-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '350', NULL, '4', '8', 'Rzeszow - Jasionka', 'Bus', '2013-06-30',
'A student needs to have a good knowledge of such programs as: Auto Cad; Math Cad; 3D Studio. He/She should also be able to use computer programs for analysis of engineering constructions',
'2000', 'MONTHLY', NULL,
'Promost Consulting provides investment services in scope of civil engineering and communication building. It specializes in bridge projecting; consulting and project management. The company provides testing services on existing bridges to assure their quality and on construction materials.  Student will focus on roads and bridges projecting (calculations; technical drawings; graphs; 3D pictures) using AutoCAD; Plateia; Sofistik; etc. He/ She will also check his/her models with the Inspection Office and cooperete with it at construction site.',
'Rzeszow', 'P', 'E', 'CIVIL_ENGINEERING', 'Department of Bridges', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0017', '38475101-ff7b-4db5-aa7d-06e19fe85ce6', FALSE, 'PLN', '40', '8', '20', 'PL Employer17', 'Address1', 'Address2', 'market and public opinion research', '125', 'www.employer17.com',
'2013-06-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '8', '12', 'Warsaw', 'bus; train', '2013-01-01',
'',
'2500', 'MONTHLY', NULL,
'A trainee will have a chance to take part in computer science projects under supervision of an experienced programmer. Trainees duties will include participation in developing applications designed for data processing; analysis and presentation. Within the training; works connected with data bases; network protocols and user interfaces are expected',
'Warsaw', 'P', 'E', 'IT', 'Software Engineering', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0018', '78619c37-0340-4405-9591-b66945947ff8', FALSE, 'PLN', '40', '8', '20', 'PL Employer18', 'Address1', 'Address2', 'Architecture Office', '7', 'www.employer18.com',
'2013-07-01', '2013-07-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '4', '4', 'Warsaw', 'bus', '2013-06-14',
'Previous training in architecture office working at preparation of technical documentation. Experience with AUTOCAD; ARCHICAD',
'1600', 'MONTHLY', NULL,
'Working out technical documentation. Different tasks connected with project review. Student will be involved in designing process with the assistance of the staff.',
'Warsaw', 'P', 'E', 'ARCHITECTURE', 'Architecture', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0019', '6b9b23a2-8e2b-4b96-af50-28669acb1000', TRUE, 'PLN', '35', '7', '20', 'PL Employer19', 'Address1', 'Address2', 'Research on chemistry;radiochemistry&radiophar', '46', 'www.employer19.com',
'2013-07-01', '2013-08-16', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '4', '6', 'Warsaw', 'bus', '2013-06-14',
'',
'1300', 'MONTHLY', NULL,
'Works in research laboratory cover methods of quality control and biological activity of radiopharmaceutics.',
'Otwock (near Warsaw)', 'R', 'M', 'BIOTECHNOLOGY', 'Biotechnology', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0020', 'd2ce950d-a9b7-45ec-8dce-aa58e29d5bd4', FALSE, 'PLN', '40', '8', '20', 'PL Employer20', 'Address1', 'Address2', 'earth Sciences:geology; hydrogeology; environment', '730', 'www.employer20.com',
'2013-08-01', '2013-08-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '4', '4', 'Warsaw', 'bus', '2013-06-14',
'',
'1300', 'MONTHLY', NULL,
'At the beginning of the training; the Student will be acknowledged with the structure of Hydrogeology and Engineering Geology department. Different scopes of research; which are carried out by separate workshops; will be presented in a broad outline. The Department is divided into five workshops-the student will spend some time in each of them. If certain scope of research is particularly interesting for the student there is possibility of changing the schedule in order to dedicate more time to chosen workshop/scope of research. The main of research:   POLISH Hydro geological Survey (identifying; sustainable management and groundwater protection in order to obtain efficient utilization of water by society and industry)   Groundwater Monitoring (groundwater quantity and quality monitoring; the structure of the nationwide monitoring network; physical-chemical groundwater analysis; field work   Hydro geological Data Base  HYDRO Bank (collecting; managing and updating information about h',
'Warsaw', 'R', 'E', 'GEOLOGY', 'Hydroheology; Engineering Geology', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0021', '09587d4c-0db4-4659-9980-b466f1c27b2e', FALSE, 'PLN', '40', '8', '20', 'PL Employer21', 'Address1', 'Address2', 'Electronics;construction&techn. of semico', '292', 'www.employer21.com',
'2013-09-01', '2013-11-15', NULL, NULL, NULL, NULL, 'ENGLISH', 'F', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '4', '8', 'Warsaw', 'bus', '2013-06-14',
'',
'1450', 'MONTHLY', NULL,
'Examination of MEMS structures:  Modeling and simulation of MEMS structures with ConventorWare environment using reducted models and finite-element method.  Computer simulatons of MEMS structures manufacture processes with SEMulator3D and Etch3D tools. Electromechanical measurements (voltage; displacement; strain) of microsystem structures. Examination of microelectronic structures:  Electrical measurement (I-V; C-V) of tested CMOS structures; detectors; ISFET devices at the computer controled site; analysis of measurement data. Implemantation; verification of algorithms of characterization of microelectronic structures and extraction of parameters of semiconductor device models; especially CMOS and ISFET devices. Computer simulations of microelectronic structures using tools:SSUPREM4; SPISCES (SILVACO) on Sun SparcStation4 machine or LIGAMENT; DESSIS (SYNOPSYS) on PC machine; especially analysis of radiation detector characteristics and phenomena during radiation',
'Warsaw', 'N', 'M', 'ELECTRONICS', 'Microelectronics; microsystems', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0022', 'f22ef7c0-a68b-4f17-b163-572f9cc4c238', FALSE, 'PLN', '40', '8', '20', 'PL Employer22', 'Address1', 'Address2', 'Architecture', '10', 'www.employer22.com',
'2013-06-14', '2013-10-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '6', '8', 'Warsaw', 'bus', '2013-06-14',
'',
'1300', 'MONTHLY', NULL,
'Company specializes in renovation and modernization of the hidtorical buildings and monuments; as well as doing the projects of turn key buildings. Student will be involved in current ongoin projects including preparation of drawings; information sheets etc.',
'Warsaw', 'P', 'M', 'ARCHITECTURE', 'Town planning', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0023', 'fa7d3d7e-0d10-40d8-8d3a-2e7047547cbc', TRUE, 'PLN', '40', '8', '20', 'PL Employer23', 'Address1', 'Address2', 'Architecture', '10', 'www.employer23.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'F', 'A', NULL, 'F', 'A', NULL, 'F',
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '6', '12', 'Warsaw', 'Underground', '2013-06-14',
'Knowledge of AutoCad and Adobe Photoshop; portfolio.',
'1300', 'MONTHLY', NULL,
'Student will be involved in projects conducted in the office. Training includes work in team; designing; detailing; preparing drawings using architectural software.',
'Warsaw', 'P', 'E', 'ARCHITECTURE', NULL, '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0024', 'c454fe2e-8468-4103-8dbe-4431ea3acfd7', FALSE, 'PLN', '40', '8', '20', 'PL Employer24', 'Address1', 'Address2', 'earth Sciences:geology; hydrogeology; environment', '730', 'www.employer24.com',
'2013-08-01', '2013-08-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '4', '4', 'Warsaw', 'bus', '2013-06-14',
'',
'1300', 'MONTHLY', NULL,
'At the beginning of the training; the Student will be acknowledged with the structure of Hydrogeology and Engineering Geology department. Different scopes of research; which are carried out by separate workshops; will be presented in a broad outline. The Department is divided into five workshops-the student will spend some time in each of them. If certain scope of research is particularly interesting for the student there is possibility of changing the schedule in order to dedicate more time to chosen workshop/scope of research. The main of research:   POLISH Hydro geological Survey (identifying; sustainable management and groundwater protection in order to obtain efficient utilization of water by society and industry)   Groundwater Monitoring (groundwater quantity and quality monitoring; the structure of the nationwide monitoring network; physical-chemical groundwater analysis; field work   Hydro geological Data Base  HYDRO Bank (collecting; managing and updating information about h',
'Warsaw', 'R', 'E', 'GEOLOGY', 'Hydroheology; Engineering Geology', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0025', '42555529-674e-4fdf-a050-d215024fbf16', FALSE, 'PLN', '30', '6', '0', 'PL Employer25', 'Address1', 'Address2', 'Electronics; Telecommunication', '0', 'www.employer25.com',
'2013-09-03', '2013-10-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'F', NULL, NULL, NULL, NULL, NULL, NULL,
'700', 'MONTHLY', 'IAESTE', '300', NULL, '6', '8', 'Warsaw; Berlin; Goleniow', 'Szczecin Railway Station', '2013-06-15',
'Basic knowledge of optoelectronics or optical telecommunication; knowledge of MathCad; MatLab; Labview',
'800', 'MONTHLY', NULL,
'Analysis of linear and nonlinear effects in the optical fiber systems (single and multiwavelenght transmission in the 3-rd window)  Numercial methods for optical waveguides analysis (beam propagation method; finite element method)  Measurements in optical fiber line (reflectometry)',
'Szczecin', 'R', 'M', 'ELECTRONICS', 'Optoelectronics', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0026', '3137ca3c-1f1b-4821-aaf4-61b996f417fa', FALSE, 'PLN', '40', '8', '20', 'PL Employer26', 'Address1', 'Address2', 'market and public opinion research', '125', 'www.employer26.com',
'2013-06-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', NULL, '8', '12', 'Warsaw', 'bus; train', '2013-01-01',
'',
'2500', 'MONTHLY', NULL,
'A trainee will have a chance to take part in computer science projects under supervision of an experienced programmer. Trainees duties will include participation in developing applications designed for data processing; analysis and presentation. Within the training; works connected with data bases; network protocols and user interfaces are expected',
'Warsaw', 'P', 'E', 'IT', 'Software Engineering', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0027', '4b8b2075-0740-4650-b391-288225d427ec', TRUE, 'PLN', '40', '8', '0', 'PL Employer27', 'Address1', 'Address2', 'Geographical Information Systems', '69', 'www.employer27.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'550', 'MONTHLY', 'IAESTE', '0', NULL, '8', '12', 'Krakow', 'train.coach', '2013-06-20',
'knowledge of the Intergraph Corp software especially Image Station; GeoMedia or Othopro or Visual Basic or Oacle',
'800', 'MONTHLY', NULL,
'- Vector data compilation using photogrammetric station  - Digital orthophoto production  - Quality check and data processing',
'Krakow', 'P', 'M', 'GEODESY', 'Photogrammetry and Remote Sensing', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0028', '4a6b4f07-a4e8-4a83-8fe0-70e4df28b75c', TRUE, 'PLN', '40', '8', '0', 'PL Employer28', 'Address1', 'Address2', 'Geographical Information Systems', '69', 'www.employer28.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'550', 'MONTHLY', 'IAESTE', '0', NULL, '8', '12', 'Krakow', 'train.coach', '2013-06-20',
'knowledge of the Intergraph Corp software especially Image Station; GeoMedia or Othopro or Visual Basic or Oacle',
'800', 'MONTHLY', NULL,
'- Vector data compilation using photogrammetric station  - Digital orthophoto production  - Quality check and data processing',
'Krakow', 'P', 'M', 'GEODESY', 'Photogrammetry and Remote Sensing', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0029', '12421af3-59a8-4554-a92b-64a3ba474395', FALSE, 'PLN', '40', '8', '0', 'PL Employer29', 'Address1', 'Address2', 'related to transportation; storage and processing', '18', 'www.employer29.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'RUSSIAN', 'G', 'O', 'UKRAINIAN', 'G',
'800', 'MONTHLY', 'IAESTE', '350', NULL, '6', '6', 'Katowice', 'train', '2013-06-20',
'Basic previous training required; ability to use AUTOCAD',
'220', 'WEEKLY', NULL,
'Full participation in work of company; projects in AUTOCAD; contacts with costumers and making buisiness documentation',
'Chorzow', 'P', 'E', 'MECHANICAL_ENGINEERING', 'any', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0030', 'a232f3f6-2bd4-4cab-8d92-a5859eb04e04', FALSE, 'PLN', '40', '8', '0', 'PL Employer30', 'Address1', 'Address2', 'Product. of software for autom. of electr. project', '61', 'www.employer30.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '350', NULL, '6', '12', 'Katowice', 'City bus; Train', '2013-06-20',
'Knowledge of C++; HDL (VHDL; Verilog) or Systemc.',
'220', 'WEEKLY', NULL,
'Participation in production and testing of EDA (HDL simulator; UI environment). Preparing test HDL projects for students of electronics.',
'Katowice', 'P', 'E', 'IT', 'Software; Programming', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0031', '358ce287-cf94-46cf-9dbf-d2c94713a7d6', FALSE, 'PLN', '40', '8', '0', 'PL Employer31', 'Address1', 'Address2', 'IT Solutions to Telecommunications', '2200', 'www.employer31.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '350', NULL, '8', '12', 'Katowice', 'bus', '2013-06-20',
'G average grade at studies.Intention of further employment in one of Comatch Group companies after training.',
'1500', 'MONTHLY', NULL,
'Assistance and research work by the building modules of Comarch products.   Comarch offers CRM and loyalty systems based on AURUM product line. Their software ORLANDO supports the management of financial institutions and a complex IT support for insurance companies; both in terms of property and life insurance is provided by Comarch SUBREA.  For the treainees they offer a very interesting work in production and development of their systems.',
'Katowice', 'R', 'E', 'IT', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0032', '46b7449c-366e-41ed-b0cf-6fc98cf873f6', FALSE, 'PLN', '40', '8', '0', 'PL Employer32', 'Address1', 'Address2', 'Electronical devices for industry', '17', 'www.employer32.com',
'2013-06-04', '2013-09-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'F', 'O', 'RUSSIAN', 'F', 'O', 'POLISH', 'F',
'800', 'MONTHLY', 'IAESTE', '350', NULL, '6', '8', 'Katowice', 'bus', '2013-06-20',
'',
'220', 'WEEKLY', NULL,
'Working on creation and development of electronical devices for industry.',
'Katowice', 'P', 'M', 'ELECTRONICS', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0033', 'ef7690ae-6c04-4431-bee1-bb82d8cdb5ee', FALSE, 'PLN', '40', '8', '0', 'PL Employer33', 'Address1', 'Address2', 'Trade-technical', '12', 'www.employer33.com',
'2013-07-01', '2013-01-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'RUSSIAN', 'F', NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '350', NULL, '6', '31', 'Katowice', 'train', '2013-07-20',
'driving license (valid in Poland); good skills in MS Office',
'300', 'WEEKLY', NULL,
'Work in a trade company; in the department of specialized grease. Student will have to acquaint himself with the offer of one of the leading GERMAN producers; with possibility of the products application and methods of delivery to the end user. He will also be responsible for creating offers; providing expertise and for contacts with producers and clients.',
'Mikolow', 'P', 'E', 'CHEMISTRY', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0034', '1c0fdc4d-caa8-4c55-baac-1f104bd01039', FALSE, 'PLN', '40', '8', '0', 'PL Employer34', 'Address1', 'Address2', 'Informatics', '20', 'www.employer34.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '350', NULL, '6', '6', 'Katowice', 'bus', '2013-06-20',
'',
'220', 'WEEKLY', NULL,
'Programming with use of Microsoft technologies; especially .NET and ASP.NET.',
'Gliwice', 'P', 'E', 'IT', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0035', '76a2f01d-9afb-4a6d-8b17-aee0a44942c2', FALSE, 'PLN', '35', '7', '0', 'PL Employer35', 'Address1', 'Address2', 'University', '38', 'www.employer35.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'FRENCH', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '0', NULL, '6', '8', 'Warsaw / Lodz', 'bus', '2013-06-05',
'fundamental knowledge of laboratory techniques of electrochemistry (CVA; EIS; ESTM; STM; AFM); analytical methods',
'630', 'MONTHLY', NULL,
'The objective of the training is student participation in various kinds of tasks in the field of modified electrodes; e.g. working on ones own research; modifying existing data; gathering new information.',
'Lodz', 'R', 'M', 'CHEMISTRY', 'modified electrodes', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0036', 'aa1f029d-0d3a-45da-acc5-3198273cfeca', FALSE, 'PLN', '35', '7', '0', 'PL Employer36', 'Address1', 'Address2', 'University', '27', 'www.employer36.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'FRENCH', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '0', NULL, '6', '8', 'Warsaw / Lodz', 'bus', '2013-06-05',
'fundamental knowledge of laboratory techniques and spectroscopic (IR; NMR; MS) methods',
'630', 'MONTHLY', NULL,
'The objective of the training is student participation in various kind of tasks in the field of Synthesis and characterization of the organoiron compounds (ferrocenes and sandwich complexes) displaying useful physicochemical or biological properties.',
'Lodz', 'R', 'M', 'CHEMISTRY', 'Oganometallic chemistry', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0037', 'aca829ef-8006-4b54-8b93-c6617f6ed97d', FALSE, 'PLN', '30', '6', '0', 'PL Employer37', 'Address1', 'Address2', 'University', '36', 'www.employer37.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '0', NULL, '6', '8', 'Warsaw / Lodz', 'bus', '2013-06-05',
'Basic skills in the field of chemical laboratory work; easy operation of computers; basic knowledge in the field of chemistry and physics on the academic level',
'630', 'MONTHLY', NULL,
'Synthesis of ceramic and organo-ceramic thin and ultra thin layers based on:  - LB (Longmuir Blodgett) techniques  - SAM (self assemble monolayer)  - Sol-Gel techniques and chemistry  - Surface modification by specific compounds  Focused on their tribological properties; chemical analysis of these layers by: FT-IR (infrared spectroscopy) various surface techniques; MS mass spectroscopy (SIMS); analysis of structure of these layers by AFM (atomic force microscopy) and STM (scanning tunnelling microscopy); Triobological characterization of thin layers in nano-scale (AFM) and micro-macro scale by specialized tribometer devices.',
'Lodz', 'R', 'M', 'CHEMISTRY', 'Advanced Materials', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0038', '3488b9b8-f8bf-4c93-a9e2-0d19919651c1', FALSE, 'PLN', '30', '6', '0', 'PL Employer38', 'Address1', 'Address2', 'University', '36', 'www.employer38.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '0', NULL, '6', '8', 'Warsaw / Lodz', 'bus', '2013-06-05',
'Basic skills in the field of chemical laboratory work; easy operation of computers; basic knowledge in the field of chemistry and physics on the academic level',
'630', 'MONTHLY', NULL,
'Synthesis of ceramic and organo-ceramic thin and ultra thin layers based on:  - LB (Longmuir Blodgett) techniques  - SAM (self assemble monolayer)  - Sol-Gel techniques and chemistry  - Surface modification by specific compounds  Focused on their tribological properties; chemical analysis of these layers by: FT-IR (infrared spectroscopy) various surface techniques; MS mass spectroscopy (SIMS); analysis of structure of these layers by AFM (atomic force microscopy) and STM (scanning tunnelling microscopy); Triobological characterization of thin layers in nano-scale (AFM) and micro-macro scale by specialized tribometer devices.',
'Lodz', 'R', 'M', 'CHEMISTRY', 'Advanced Materials', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0039', 'ca843c41-2279-4142-9e9e-cfe1e1b544e6', FALSE, 'PLN', '35', '7', '0', 'PL Employer39', 'Address1', 'Address2', 'Research and teaching', '142', 'www.employer39.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'F', 'A', 'RUSSIAN', 'G',
'600', 'MONTHLY', 'IAESTE', '0', NULL, '6', '8', 'Warsaw / Lodz', 'bus', '2013-06-05',
'',
'630', 'MONTHLY', NULL,
'The objective of the training is student participation in research or programming in nonlinear analysis; artificial neural network; numerical analysis',
'Lodz', 'R', 'M', 'MATHEMATICS', 'nonlinear analysis;artificial neural network;num.', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0040', '42a42828-54db-4ff8-95f7-e8551b76c132', FALSE, 'PLN', '35', '7', '0', 'PL Employer40', 'Address1', 'Address2', 'University', '12', 'www.employer40.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '0', NULL, '6', '8', 'Warsaw / Lodz', 'bus', '2013-06-05',
'',
'630', 'MONTHLY', NULL,
'The objective of the training is student participation in:  - design of various linguistic algorithms and implementing them;  - taking part in a larger project of automatic speech recognition',
'Lodz', 'R', 'M', 'IT', 'related to linguistics', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0041', '20d7f999-72ce-4e1d-b396-c0f028f3df96', TRUE, 'PLN', '40', '8', '20', 'PL Employer41', 'Address1', 'Address2', 'telecommunication', '1000', 'www.employer41.com',
'2013-06-18', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '300', NULL, '6', '8', 'Wroslaw', 'Bus', '2013-06-30',
'preferred ENGLISH NATIVE SPEAKER',
'1200', 'MONTHLY', NULL,
'Student will be involved in the documentation project for Intelligent Network. Student will be working as a technical writer and will be cooperating with software developers. Student will be also responsible for language correction of the existing documentation.  We offer:  - cutting edge technology;  - participation in a highly motivated and dynamic team;  - training period in multinational environment.',
'Wroclaw', 'P', 'M', 'IT', 'any', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0042', 'fe404b23-f690-4d2b-abb9-b69e0ed478a5', FALSE, 'PLN', '35', '7', '0', 'PL Employer42', 'Address1', 'Address2', 'Material Sc./Material Eng', '70', 'www.employer42.com',
'2013-06-15', '2013-09-15', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '300', NULL, '8', '12', 'Warszawa', 'Bus', '2013-06-15',
NULL,
'1000', 'MONTHLY', NULL,
'The training comprises an active participation of the student in a project concerning plasma enhanced chemical vapor deposition (PECVD) of thin oxide films for optical applications. A proper stack sequence of selected oxide films; characterized by well defined values of their thickness d index of refraction n and extinction coefficient k; results in a formation of an optical filter of a desired position and width ofthe windowo The following oxides: Ti02; Si02; Ta20S and Nb20s are of a particular interest and metal¬organic compounds the respective elements will be used as precursors. The student will be working in the phase of an assembly of a new large volume radio frequency PECVD equipment; built especially for the project; as well as in the phase oftesting this equipment.',
'Lodz', 'R', 'M', 'PHYSICS', 'vacuum', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0043', '4b14667c-6b64-41e0-a050-29cc6fe357d3', FALSE, 'PLN', '40', '8', '20%', 'PL Employer43', 'Address1', 'Address2', 'architecture office', '7', 'www.employer43.com',
'2013-07-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', 'MONTHLY', '6', '8', 'warsaw', 'tram, bus, subway', '2013-03-31',
NULL,
'500', 'WEEKLY', NULL,
'Architectural design. Initial industry training in both construction and drafting. participation in works currently in progress.',
'warsaw', 'P', 'Begin', 'ARCHITECTURE', NULL, '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0044', 'f7df8874-3994-418b-9b6c-15e31f781642', TRUE, 'PLN', '35', '7', '0', 'PL Employer44', 'Address1', 'Address2', 'university', '40', 'www.employer44.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'RUSSIAN', 'G', NULL, NULL, NULL,
'550', 'MONTHLY', 'IAESTE', '0', 'MONTHLY', '6', '6', 'krakow', 'train/coach', '2013-03-31',
NULL,
'680', 'MONTHLY', NULL,
'Measurement of nuclear physics experiments. Experiments of radiation dosimetry. Computing, model calculations, simulation calculations of nuclear physics',
'krakow', 'R', 'M|E', 'PHYSICS', 'Nuclear Physics, Nuclear Engineering', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0045', '067db9fa-be4e-408d-9881-2d2441ec63e4', NULL, 'PLN', '30', '6', '20%', 'PL Employer45', 'Address1', 'Address2', 'Aroma substances, food aromas, cosmetics', '85', 'www.employer45.com',
'2013-02-02', '2013-08-22', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', 'MONTHLY', '6', '6', 'warsaw', 'bus tram', '2013-03-31',
'basic knowledge of fragrance compounds and nomenclature used in perfume technology',
'325', 'WEEKLY', NULL,
'lab work. student will be incolced in deceloping new fragrance compound used in companys products (fragrance compounds, aromas, essentials and food emulsions, synthesised flacours and essential oils for aromatherapy and natural herbal cosmetics flavourings healing cosmetics)',
'Warsaw', 'R', 'M', 'BIOTECHNOLOGY|CHEMISTRY|BIOLOGY', 'Fragrance compounds, food flavourings healing cosmetics, synthetic flavourings', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0046', '23c9f150-d598-4117-a6bb-11f7608a2d80', NULL, 'PLN', '35', '7', '0%', 'PL Employer46', 'Address1', 'Address2', 'Research Institute - biotechnology and general foo', '20', 'www.employer46.com',
'2013-07-02', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '8', 'Warszawa', 'City bus, tram', '2013-03-31',
'none',
'1000', 'MONTHLY', NULL,
'Co-Worker.\r\nStudent will be based in research centre of Institute of Generals Food Chemistry and will become part of a project team with the responsibility of projects concerning electro-generated chemiluminescence in biochemical analysis. Trainee will take part in practical laboratory works assisting the university researchers.',
'Lodz', 'R', 'M|E', 'BIOCHEMISTRY|PHARMACEUTICAL_STUDIES|FOOD_TECHNOLOGY', NULL, '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0047', '71ee90f5-75b5-4bc8-a625-efff640ec0d7', NULL, 'PLN', '30', '6', '0%', 'PL Employer47', 'Address1', 'Address2', 'University', '40', 'www.employer47.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'550', 'MONTHLY', 'IAESTE', '0', 'MONTHLY', '6', '8', 'Krakow Balice (KRK)', 'Krakow Train/Coach', '2013-03-31',
'none',
'680', 'MONTHLY', NULL,
'Multichannel audio and speech signal processing algorithms implementation in a real-time on Motorola DSP56399 and/or Analog Devices SHARC and/or Texas Instruments C6000 platform in a form suitable for laboratory courses. Design, simulation in Matlab and implementation of beam forming algorithms.',
'Krakow', 'R', 'E', 'ELECTRICAL_ENGINEERING|ELECTRONICS', NULL, '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0048', 'd0f61a5a-c696-4a20-9cb7-f75554561594', NULL, 'PLN', '35', '7', '0%', 'PL Employer48', 'Address1', 'Address2', 'Center of Laser Diagnostics & Therapy in Medicine', '50', 'www.employer48.com',
'2013-07-02', '2013-08-12', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '6', 'Warszawa', 'City bus, tram', '2013-03-31',
'Must start on 1st of July! - team work!',
'1000', 'MONTHLY', NULL,
'Student will be part of team working in researching projects related to image analysis in medical applications.\r\nInvestigation of optical properties of tissue, influence of laser light on living cells, introduction of new healing methods by means of laser technologies, photodynamic diagnostics. Results of these investigations are implemented in photodynamic diagnostics and laser therapy of tumors of urinary bladder, rheumatic disease healing, multiplex sclerosis, surgical laser therapy of burns and others (see Cener web site) with cooperation with Lodz Medical University.',
'Lodz', 'P', 'M|E', 'PHYSICS', 'Applied Physics, Medicine', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0049', '094136cc-45bd-489e-a99f-6dd67556dee2', TRUE, 'PLN', '40', '8', '0', 'PL Employer49', 'Address1', 'Address2', 'company - Geodesy, Photogrammetry', '120', 'www.employer49.com',
'2013-06-20', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'950', 'MONTHLY', 'IAESTE', '450', 'MONTHLY', '4', '8', 'Krakow', 'Train/Coach', '2013-03-31',
'G knowledge of MicroStation and/or Autocad software, knowledge of photoshop & 3D studio software will be highly appreciated, good knowledge of professional measurement equipment maintenance (total station/ level)',
'1100', 'MONTHLY', NULL,
'Land surveying. Outdoor work, assistance with survey work (total station, level, laser scanner measurement) in and outside of Krakow. Office work: post- processing of collected date, maps editing in MicroStation and AutoCad, cartography data capturing using 3D digital station, maps printing, orthophoto production.',
'Krakow', 'P', 'E', 'GEODESY', 'Surveying, Cartography, Photogrammetry, Terrestrial Surveying', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0050', '9e6e80a6-ae82-4d15-825f-c1d20ec7ef99', FALSE, 'PLN', '40', '8', '0', 'PL Employer50', 'Address1', 'Address2', 'Building Construction', '425', 'www.employer50.com',
'2013-06-25', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'E', NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '350', 'MONTHLY', '8', '10', 'Rzeszow Jasionka', 'Rzesow', '2013-03-31',
NULL,
'1200', 'MONTHLY', NULL,
'Student will take part in firms current projects. Company is willing to pay for the ticket to Poland',
NULL, 'P', 'E', 'ENVIRONMENTAL_ENGINEERING|CIVIL_ENGINEERING', 'Construction (halls no bridges) industrial engineering', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0051', 'd5d6458f-bec6-492e-bf40-10cf91e4a57c', TRUE, 'PLN', '40', '8', '20%', 'PL Employer51', 'Address1', 'Address2', 'Informatics', '2000', 'www.employer51.com',
'2013-07-01', '2013-10-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', 'O', 'POLISH', 'F', NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '10', '12', 'Wroclaw', 'bus, tram', '2013-03-31',
'advanced programming skills in JAVA, dotNET or C++\r\nA desire to work in Poland after graduation will be an advantage. Employer would like to verify knowledge of the candidate before acception',
'1700', 'MONTHLY', NULL,
'Kind of work: programming\r\nStudent will take part in current programming projects. Trainee will be responsible for creating and testing IT solutions according to provided specification.',
'Wroclaw', 'P', 'E', 'IT|ELECTRONICS', 'Programming, Software Design', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0052', '4fb5472a-0ee0-4ed2-9d05-7809fcbca085', FALSE, 'PLN', '40', '8', '0', 'PL Employer52', 'Address1', 'Address2', 'Execution of complex construction projects', '200', 'www.employer52.com',
'2013-06-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', 'O', 'RUSSIAN', 'G',
'850', 'MONTHLY', 'IAESTE', '400', 'MONTHLY', '8', '8', 'Jasionka', '', '2013-06-01',
'',
'1200', 'MONTHLY', NULL,
'Student will:  - get familiar with organization of the company  - have short practise in everty department connected with production  - get familiar with technology and have training on selected building site  - get familiar with responsibilities of construction site engineer, foreman and manager  - get familiar with concrete, steel and brick technologies',
'Rzeszow', 'P', 'M', 'CIVIL_ENGINEERING', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0053', '4b419665-7db4-4ed5-96fe-5a3365ee0891', FALSE, 'PLN', '40', '8', '20', 'PL Employer53', 'Address1', 'Address2', 'automitive, finantial services center', '2600', 'www.employer53.com',
'2013-06-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'FINNISH', 'G', NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '400', 'MONTHLY', '52', '52', 'Wroclaw', '', '2013-05-20',
'',
'2700', 'MONTHLY', NULL,
'Traineeship plan (for 12 months) Main responsibility - cooperation with FINNISH customers Volvo Finland AB in AR  accounting area.',
'Wroclaw', 'R', 'E', 'ECONOMICS', '', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0054', '0c6cd4ba-d799-49e3-bae1-998ddf708c60', FALSE, 'PLN', '40', '8', '0%', 'PL Employer54', 'Address1', 'Address2', 'Web graphics and software', '20', 'www.employer54.com',
'2013-06-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '12', '24', 'F.Chopin International Airport, Warsaw (WAW)', 'Bus, tram, metro', '2013-03-31',
'Knowledge of DHTML, ASP, VBScript, SQL\r\ninterview possible',
'1200', 'MONTHLY', NULL,
'Web designer\r\n\r\nWeb design (DHTML, ASP, VBScript), working with MSSQL. Computer Graphic (PhotoShop), student will be involved in some scopes of company activity that includes:\r\n- Development and implementation of Web systems for management of business processes, \r\n- Creation of advanced visual projects for applications, web pages, multimedia presentations and DTP\r\n- Development of software developer tools based on genuine technologies\r\n- Providing advanced ISP services, including hosting and collocation provided in Data Center, as well as providing Internet access for companies. ',
'Lodz', 'P', 'M|E', 'IT', 'Information Technology, Web desing with sql database', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0055', '0ff9d6db-6a80-4bcb-8a8e-353c1eb53886', TRUE, 'PLN', '25', '5', '0%', 'PL Employer55', 'Address1', 'Address2', 'Mathematics', '12', 'www.employer55.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '0', 'MONTHLY', '4', '8', 'Warsaw Airport/Lodz, W. Reymont Airport', 'Tram, Bus', '2013-03-31',
NULL,
'710', 'MONTHLY', NULL,
'Research project in infinitely divisible laws (classical probability theory) or in quantum statistics - quantum computing (quantum probability). ',
'Lodz', 'R', 'M|E', 'MATHEMATICS', 'Probability Theory: Classical and Quantum', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0056', '88f7cad7-e967-48b6-82d6-c941e545a41b', TRUE, 'PLN', '40', '8', '20%', 'PL Employer56', 'Address1', 'Address2', 'Architecture', '18', 'www.employer56.com',
'2013-06-15', '2013-10-15', NULL, NULL, NULL, NULL, 'ENGLISH', 'F', NULL, NULL, NULL, NULL, NULL, NULL,
'1000', 'MONTHLY', 'IAESTE', '450', 'MONTHLY', '6', '8', 'F.Chopin International Airport, Warsaw (WAW)', 'Bus, tram, metro', '2013-03-31',
'Required knowledge of AutoCAD and previous practice/work in architectural office. \r\nDesired previous experience in making projects.',
'500', 'WEEKLY', NULL,
'Trainee will gain experience collaborating with professionals at ongoing projects of the office, which includes projecting and planning of industrial, residential, office and public use buildings. ',
'Warsaw', 'P', 'E', 'ARCHITECTURE', 'Architectural Projects', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0057', '4da0c75f-7527-44c5-b2f5-a57d50b7110f', FALSE, 'PLN', '35', '7', '0%', 'PL Employer57', 'Address1', 'Address2', 'Steam and gas turbines - parts manufacturing, desi', '115', 'www.employer57.com',
'2013-07-01', '2013-08-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '8', 'Warszawa', 'City bus, tram', '2013-03-31',
NULL,
'1000', 'MONTHLY', NULL,
'Co-worker\r\n\r\nTrainee will be a support in tasks related to:\r\n# preparation of technical drawings\r\n# participation in design work (determination of operating parameters & characteristics)\r\n# modernization and repair of turbines\r\n# testing & evaluation of the technical wear\r\n# cooperation in the development of turbines - determination of the optimal criteria of the turbo set start-up',
'LÃ³dz', 'P', 'M|E', 'MECHANICAL_ENGINEERING', 'Mechanical Engineering, Fluid Flow Machines', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0058', '99ca43b8-22b6-49dc-8478-b3e3735ca240', FALSE, 'PLN', '40', '8', NULL, 'PL Employer58', 'Address1', 'Address2', 'Geodesy, Cartography, Land Surveying', '100', 'www.employer58.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'0', 'MONTHLY', 'Company', '0', 'MONTHLY', '6', '12', 'Krakow Balice', 'Krakow public transport', '2013-03-31',
'Studylevel: B/M/E',
'1500', 'MONTHLY', NULL,
'Student will familiarize himself with the company organization and structure. Training will cover general issues connected with geophysical project, especially geodetic measurements (using GPS, RTK technique). During week (mon-fri) work in open-area (field measurements) away from Krakow. Employer provide accomodation and meals at the hotel for free also transport to Krakow for weekends. Possibility to use games like billiard, darts at hotel. \r\nThere is a possibility to get full-time and well-paid job after practice in the students motherland in the company. ',
'depends on the project (Krakow)', 'W', 'Begin', 'GEODESY', 'Geology', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0059', '74b0b2a7-79e6-4f9b-99cf-e949601a6741', FALSE, 'PLN', '35', '7', '0%', 'PL Employer59', 'Address1', 'Address2', 'Center of laser diagnostics & therapy in medicine', '50', 'www.employer59.com',
'2013-06-15', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '6', 'Warzawa', 'City bus, tram', '2013-03-31',
'Must start on 1st of July!\r\nTeamwork',
'1000', 'MONTHLY', NULL,
'Student will be part of team working in a researching projects related to image analysis in medical appications.\r\nInvestigation of optical properties of tissue, influence of laser light on living cells, introduction of new healing methods by means of laser technologies, photodynamic diagnostics.\r\nResults of these investigations are implemented in photodynamic diagnostics and laser therapy of tumors of urinary bladder, rheumatic disease healing, multiplex sclerosis, surgical laser therapy of burns and others with cooperation with Lodz Medical University',
'Lodz', 'R', 'M|E', 'PHYSICS', 'Applied Physics, Medicine', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0060', '999f5400-a8eb-4504-aa45-aa67081f5161', TRUE, 'PLN', '40', '8', '20', 'PL Employer60', 'Address1', 'Address2', 'Civil Eng., Computer Science', '50', 'www.employer60.com',
'2013-06-01', '2013-10-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '360', 'MONTHLY', '4', '12', 'Krakow Balice International Airport', 'Krakow', '2013-03-17',
'Nationality: US/Canada, Western Europe, Australia, Japan',
'2000', 'MONTHLY', NULL,
'Construct. consultan - Providing guideline on product customization to reflect local codes and standards, preparing market analyse of competitor products, preparing product specyfications in local languages, co-operation with computer scientists at creating bespoke software for civil engeenering, testing new computer programs, find disadvantage of programs as computer science could improve those programe, team working is base.',
'Krakow', 'R', 'E', 'CIVIL_ENG.', 'Construction', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0061', '817613a7-9574-4b32-bb7e-ede7b1ac31d1', TRUE, 'PLN', '40', '8', '15', 'PL Employer61', 'Address1', 'Address2', 'Elect. heating elements', '130', 'www.employer61.com',
'2013-03-03', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '8', 'Szczecin, Berlin', 'Szczecin', '2013-03-17',
'',
'1200', 'MONTHLY', NULL,
'Trading Department - 1. Familiarize with the companys structure, operation range and offered products.2. Familiarize with the technology, construction and production of the heaters manufactured by the company.3. Familiarize with the circulation of documents: enquiries, orders, construction, technology, storage, shipping.4. Training in the Marketing Department and Sales Department (offer, calculation, invoicing).5. FRENCH market analysis in the field of heating elements and photovoltaic cells.',
'Szczecin', 'N', 'E', 'POWER_ENGINEERING', 'Power engineering or Renewable energy', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0062', '30c215c0-da1d-422d-98d1-ca21267f1fdc', TRUE, 'PLN', '36', '6', '0', 'PL Employer62', 'Address1', 'Address2', 'University', '50', 'www.employer62.com',
'2013-06-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'E', NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '450', 'MONTHLY', '6', '6', 'Rzeszow-Jasionka', 'Rzeszow', '2013-02-22',
'Austrian nationality',
'1000', 'MONTHLY', NULL,
'- - Practice will involve working on Austrian military objects from the nineteenth century, Student will get familiar with the Austrian military objects of the nineteenth century, S/He will have to prepare inventarisation and historical studies of objects, S/He will also have to make some drawings and visualisations of buildings',
'Rzeszow', 'R', 'M|E', 'ARCHITECTURE', '-', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0063', '47b085c2-2ab7-4048-87b8-dc43d3412c10', FALSE, 'PLN', '40', '8', '20', 'PL Employer63', 'Address1', 'Address2', 'Architecture', '18', 'www.employer63.com',
'2013-06-30', '2013-10-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'1100', 'MONTHLY', 'IAESTE', '450', 'MONTHLY', '4', '12', 'Warsaw F. Chopin Airport', 'Metro, tram, bus', '2013-02-22',
'Knowledge of AutoCAD.\r\nNationality required: Austrian or GERMAN.',
'500', 'WEEKLY', NULL,
'Office work - Student will work on ongoing projects of an architecture office, which are residential, industrial or public buildings and interior designs. Trainee will join the team composed of talented, experienced and enthusiastic  professionals, prepared to lead clients through the challenges and conflicts occurring during a design process in a way, which will fully satisfy both, the client and the company.  The office is on the market since 1991 and collaborates with many companies of different fields in Warsaw.',
'Warsaw', 'P', 'E', 'ARCHITECTURE', 'architecture', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0064', 'd3230685-c4be-4e8d-b329-c0f09f93a198', TRUE, 'PLN', '40', '8', '0', 'PL Employer64', 'Address1', 'Address2', 'Design office specializing in design of steel and ', '55', 'www.employer64.com',
'2013-06-01', '2013-10-31', NULL, NULL, NULL, NULL, 'GERMAN', 'E', 'A', 'ENGLISH', 'G', NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '450', 'MONTHLY', '8', '20', 'Rzeszow-Jasionka', 'Rzeszow', '2013-02-22',
'- Very good Knowledge of AutoCad\r\n- Nationality of trainee: GERMAN, Swiss or Austrian.',
'1400', 'MONTHLY', NULL,
'- - Trainee will be involved in the implementation of the technical documentation for steel construction projects realized by MTA - the performance of workshop drawings, lists of parts, assembly plans, etc. Besides, student can also familiarize oneself with the environment of the company and take part in the outside projects.',
'Rzeszow', 'P', 'E', 'CIVIL_ENGINEERING', 'Steels constructions, Engineering constructions', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0065', 'b9ea55dd-4c1e-4d84-b35f-8db71db248d8', TRUE, 'PLN', '40', '8', '0', 'PL Employer65', 'Address1', 'Address2', 'International software house that provides innovat', '2900', 'www.employer65.com',
'2013-07-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'950', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '8', '12', 'Krakow Balice (KRK)', 'bus/tram', '2013-02-22',
'- Person from France, GERMANy, Austria, Switzerland\r\n- IT or related faculty preferred\r\n- technical skills: C#/.NET, SQL\r\n- good average grade at studies \r\n- intention of further employment in one of Comarch Group companies after training',
'1500', 'MONTHLY', NULL,
'Position: Assistant/ Researcher\r\n\r\nWe offer the opportunity to gain experience in writing code, complete programming, and performing testing and debugging of complex applications using current programming languages and technologies. This is also a great chance to embark on an internship in the friendly and dynamic work environment of an international software house with the highest working standards, in a modern office located in Krakow.',
'Krakow', 'P', 'E', 'IT', 'nonspecific', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0066', 'bf7045b7-233f-4f0b-a306-7ee7bf116763', TRUE, 'PLN', '40', '8', '20', 'PL Employer66', 'Address1', 'Address2', 'IT,systems integration', '13', 'www.employer66.com',
'2013-06-01', '2013-10-29', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'800', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '12', 'Gdañsk (GDN)', 'Tram', '2013-02-22',
'Nationality: Norwegian, Swede, Dane, GERMAN, Austrian, Swiss.\r\n\r\nExperience in IT solutions especially IBM Lotus Notes and IBM WebSphere most welcome.G interpersonal and teamwork skills.',
'1200', 'MONTHLY', NULL,
'Sales manager - Activities focused on Workgroups solutions based on IBM software platforms, mainly Lotus Notes applications. Software solutions promoting, selling, training and implementing. International business development, foreign Partners search and initial contacts. Product development and documentation, websites maintenance and content management. Other activities according to discussed and agreed scenario.',
'Gdañsk', 'P', 'E', 'IT', 'Any', '19');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('PL-2013-0067', '4f47536f-4b1d-4c18-91b9-e916eefe4f4e', TRUE, 'PLN', '40', '8', '0', 'PL Employer67', 'Address1', 'Address2', 'Biuro Architektoniczno-Urbanistyczne', '5', 'www.employer67.com',
'2013-06-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ITALIAN', 'G', 'O', 'FRENCH', 'G', NULL, NULL, NULL,
'900', 'MONTHLY', 'IAESTE', '300', 'MONTHLY', '6', '6', 'Lodz W. Reymont Airport', 'city bus,tram', '2013-02-22',
'Preffered knowledge of AutoCAD or ArchiCAD.There can be a possibility of extension period of traineeship if company will have more work for student.portfolio',
'1000', 'MONTHLY', NULL,
'designer - Student will support in tasks related to: - development of architectural projects, - technical documentation of town-planning, - architectural and interior designing together with a complete set of studies of structures of different scale and function, - participation in design work, - building planning, - urban projects- computer visualisations of designed structures.',
'&pound;&oacute;d&frac14;', 'P', 'M', 'ARCHITECTURE', 'any', '19');




-- =============================================================================
-- Offer Hungary
-- =============================================================================
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0001', '696ef516-765f-4659-b69a-c74bc0102c6d', NULL, 'HUF', '40', '8', NULL, 'HU Employer1', 'Address1', 'Address2', 'Environmental analysis', '15', 'www.employer1.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
20000, 'WEEKLY', 'arranged by IAESTE', 10000, 'WEEKLY', '8', '12', 'Budapest', NULL, '2013-03-31',
NULL,
25000, 'WEEKLY', NULL,
'Chemical analysis of environmental samples (water, groundwater, soil). Instrumental analysis: GC (HP, ECO), GC/MS, HPLC. Development of water-purification technologies.',
'Balatofüzfö', 'W', 'M', 'WATER_TREATMENT|CHEMISTRY', 'Analytic', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0002', '60f30b82-7e31-4b8a-9736-49b5f4f4acc9', FALSE, 'HUF', '40', '8', '0', 'HU Employer2', 'Address1', 'Address2', 'medicine making', '70', 'www.employer2.com',
'2013-08-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', NULL, NULL, NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '8', '8', 'Budapest (Ferihegy)', 'Pécs', '2013-05-20',
'-',
'25', 'WEEKLY', NULL,
'1. Training in pharmaceutical control laboratory. the job covers physical; physical-chemical and chemical drug control. 2. Training in tabletting plant.',
'Pécsvárad', 'P', 'E', 'CHEMISTRY', '-', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0003', '9f0a2d92-4c2a-4f19-a60a-3ed9ba01dd37', FALSE, 'HUF', '40', '8', '0', 'HU Employer3', 'Address1', 'Address2', 'Bridge and structural design', '30', 'www.employer3.com',
'2013-06-01', '2013-08-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'FRENCH', 'G', NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '8', '8', 'Budapest (Ferihegy)', 'Budapest', '2013-05-10',
'No smoking (if possible)',
'25', 'WEEKLY', NULL,
'Preparing detailed structural drawings for bridges or towers by AutoCad. Making statical calculation of existing or yet-to-be-bulit bridges by finite element programmes.',
'Budapest', 'P', 'E', 'CIVIL_ENGINEERING', 'Bridge or structural', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0004', '7dde83a6-1cd8-41b0-9a02-8b49acde4dbb', FALSE, 'HUF', '40', '8', '0', 'HU Employer4', 'Address1', 'Address2', 'Interventional X-ray machines', '400', 'www.employer4.com',
'2013-06-01', '2013-06-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '40', '52', 'Budapest (Ferihegy)', 'Budapest', '2013-05-15',
'-',
'42', 'WEEKLY', NULL,
'Design our next generation Cardiovascular X-Ray imaging systems. Software and hardware design and compliance to medical industry standards. Functional and architectural specification; Image quality. Risk retirement and feasibility. Coordinate the SW & HW implementation design teams. Design and execution of test; Clinical evaluations follow up.',
'Budaörs', 'R', 'B', 'IT', 'MEdical Engineering; Image Processing (optional)', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0005', '36716a36-924b-488d-bb19-b2c4fa1d2840', FALSE, 'HUF', '40', '8', '0', 'HU Employer5', 'Address1', 'Address2', 'Teaching; research', '23', 'www.employer5.com',
'2013-06-26', '2013-08-06', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '6', '6', 'Budapest (Ferihegy)', 'Miskolc', '2013-05-10',
'-',
'25', 'WEEKLY', NULL,
'Numerical simulation of the vacuum-cleaner. The trainee will use the CFD package FLUENT. The Gambit cemmercial Package will be used to mesh the body. Flow-field will be calculated by the FLUENT. the trainee will determine the acoustic power of the vacuum-cleaner. The trainee makes recommendation how to reduce the acoustic power.',
'Miskolc', 'R', 'M', 'MECHANICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0006', '0bc8ffe0-6f71-4c3c-a5b6-fdd2cfce725e', FALSE, 'HUF', '40', '8', '0', 'HU Employer6', 'Address1', 'Address2', 'Service (Mainly mobil phone)', '80', 'www.employer6.com',
'2013-06-01', '2013-06-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '8', '52', 'Ferihegy (Budapest)', 'Budapest', '2013-05-30',
'-',
'25', 'WEEKLY', NULL,
'Our company is the largest repair center in Hungary. We repair different brand mobile phones like Motorola; Siemens; Sharp; Samsung; LG. We repair OMRON PLC and entertainment electonics products (TV; DVD). We would like to change experience with a collegues; who have some working knowledge in this area.',
'Hungary-2040 Budaörs; Kinizsi u. 2/b.', 'W', 'E', 'ELECTRICAL_ENGINEERING', 'Electroniccs (entertaining) knowledge', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0007', 'f7ebdee0-0b20-4ae3-9519-b85bafccdf58', FALSE, 'HUF', '40', '8', '0', 'HU Employer7', 'Address1', 'Address2', 'Security systems', '7', 'www.employer7.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '8', '12', 'Ferihegy (Budapest)', 'Budapest', '2013-05-30',
'Native speaker (from): US; England; Canada',
'25', 'WEEKLY', NULL,
'Installing; maintaining; programming any security systems.',
'Hungary-1118 Budapest; RegQs u. 13.', 'P', 'M', 'SYSTEMS_ENGINEERING', 'Security Systems (DSC; Paradox; Pelco; Computar)', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0008', 'bd277e96-8a8f-41f0-9075-27123c313219', FALSE, 'HUF', '25', '5', '0', 'HU Employer8', 'Address1', 'Address2', 'Public utility designed', '5', 'www.employer8.com',
'2013-06-01', '2013-10-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'F', NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '8', '12', 'Ferihegy (Budapest)', 'Pécs', '2013-05-30',
'-',
'25', 'WEEKLY', NULL,
'The Pécsi Deep Constructive Office Co. Ltd. provides full range of quality engineering and consulting sevices. Our offices main activities are the public utilities of canalisation; water-supply and water-diversion. We would like to employ public utility designer; to the scope of duties bellow: on-site examination; plotting; longitudinal-section; cross-section; hydraulically and hydrological analyses. We need students who have experiences in AutoCad and MS Office software.',
'Pécs', 'R', 'M', 'CIVIL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0009', '10fb0aa2-6bee-4bb8-a8a8-61ce4168a4a6', FALSE, 'HUF', '40', '8', '0', 'HU Employer9', 'Address1', 'Address2', 'trading of commercial software products; consultin', '200', 'www.employer9.com',
'2013-08-28', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'E', NULL, NULL, NULL,
'20', 'WEEKLY', 'IAESTE Hungary', '10', NULL, '52', '52', 'Budapest; Ferihegy', 'Budapest', '2013-09-12',
'-',
'190', 'MONTHLY', NULL,
'Our new trainee; as a part of an international team will have the possibility to learn and use broad and indepth product knowledge to provide and facilitate the responsive delivery of technical solutions and information to Microsoft Enterprise Customers who use Microsoft Exchange Server and/or Outlokk products.  Responsibilities: - Participation and learing possibility in analyziong and solving complex product and solutions related technical problems.  Requirements: - Knowledge of Microsoft Exchange Server is an advantage; but not obligatory; E written and verbal GERMAN and ENGLISH language skills; Enthusiasm for Microsoft Products; and passion for Microsoft technologies; Quick learning and developing possibilities; Using cutting edge technology on a daily basis.',
'Budapest', 'P', 'M', 'INFORMATION_TECHNOLOGY', 'IT', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0010', '4220df90-c4ac-473e-ac6b-a6ea23b100fd', FALSE, 'HUF', '40', '8', '-', 'HU Employer10', 'Address1', 'Address2', 'Architecure', '22', 'www.employer10.com',
'2013-05-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'20000.00', 'WEEKLY', 'IAESTE', '10000.00', 'WEEKLY', '8', '12', 'Budapest', 'Bus', '2013-03-31',
NULL,
'25000.00', 'WEEKLY', NULL,
'All kind of architectural works: study-plans permission / work plans teaching of architectural subjects: architecural design, interial design, drawing, cad. Working on projects: living facilities, world heritage surviving.',
'7624 Pecs, Rokus u. 2/b', 'P', 'M', 'ARCHITECTURE', NULL, '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0011', 'a34761be-0a0c-4783-bff6-c082fc5f9d7a', FALSE, 'HUF', '40', '8', '-', 'HU Employer11', 'Address1', 'Address2', 'Product safety service', '93', 'www.employer11.com',
'2013-05-01', '2013-10-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', NULL, NULL, NULL,
'20000.00', 'WEEKLY', 'IAESTE', '10000.00', 'WEEKLY', '6', '8', 'Budapest', 'Bus', '2013-03-31',
NULL,
'25000.00', 'WEEKLY', NULL,
'Revition of available equipments, draft testing plan making, participation/assistance to develop new equipments, investigation assistance.\r\nMEEI is member of Group TÜV Rheinland Group, a global leader in independent testing and assessment services.',
'1132 Budapest, Vaci ut 48/a-b', 'R', 'M|E', 'ELECTRICAL_ENGINEERING', NULL, '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0012', 'e8442735-8a5f-4d22-a7bd-4960cf17807d', FALSE, 'HUF', '40', '8', NULL, 'HU Employer12', 'Address1', 'Address2', 'Recultivation', '135', 'www.employer12.com',
'2013-07-01', '2013-08-15', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
NULL, NULL, NULL, NULL, NULL, '6', '7', 'Budapest', 'Bus', '2013-03-31',
NULL,
NULL, NULL, NULL,
'Environmental damage assessment and examination, remediation of damage, recultivation, environmental monitoring, technological control, treatment of contaminated mine water, mining activities.',
'7633 Pecs, Esztergar', 'P', 'E', '_MINING|GEOLOGY', 'Hidrogeology', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0013', 'd0a29f0e-9958-4a6a-91e7-5fd783ca4306', NULL, 'HUF', '40', '8', '-', 'HU Employer13', 'Address1', 'Address2', 'company', '15', 'www.employer13.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', NULL, NULL, NULL, NULL, NULL,
'20000.00', 'WEEKLY', 'IAESTE', '10000.00', 'WEEKLY', '8', '8', 'Budapest (Ferihegy)', 'Bus', '2013-03-31',
'CAD knowledge',
'26000.00', 'WEEKLY', NULL,
'During our past decade, the main areas of activity have been the delivery of technical and engineering services and the design, manufacturing, commissioning and maintenance of tailor-made automated production machinery and purpose-built equipment. In the course of our everday work we are regardful of our quality directives set forth in our Quality Policies: we create the products of the company at the highest possible standards, we reach the satisfaction of our customers through the creation of new know-how and we wish to make our brand well-known in our domestic and foreign markets. The main task of the student will be to take part in the development process of the mechanical design of automatic components which are development process of the mechanical design of automatic components with are developed to replace manual work.',
NULL, 'P', 'E', 'MECHANICAL_ENGINEERING', '-', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0014', '2bfe936e-6347-4024-b563-d17aa921ad6c', FALSE, 'HUF', '40', '8', '0', 'HU Employer14', 'Address1', 'Address2', 'Translation, project management software', '4', 'www.employer14.com',
'2013-01-01', '2013-04-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE', '10000', 'WEEKLY', '8', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-12-01',
FALSE,
'25000', 'WEEKLY', NULL,
'To develope a project management system used locally. Previous experience in SAP might be an advantage. The system requirements are already set, we are in the development stage already.',
'H-1028 Budapest, Vízesés u. 10.', 'P', 'M', 'IT', 'Software Development', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0015', 'da9a910c-edb8-4dbf-a505-25775b007b3d', FALSE, 'HUF', '40', '8', '0%', 'HU Employer15', 'Address1', 'Address2', 'IT, Healthcare', '20', 'www.employer15.com',
'2013-03-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE', '10000', 'WEEKLY', '8', '20', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
NULL,
'40000', 'WEEKLY', NULL,
'Design, development, testing and support of healthcare connected IT products. Java, C# development. UML software design. Hospital administration systems. System integration projects. ',
'4025 Debrecen, Hatvan u. 58', 'R', 'M|E', 'IT|MEDICAL_ENGINEERING', NULL, '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0016', '2771fe06-daf5-4842-9240-3584ca602527', FALSE, 'HUF', '40', '8', NULL, 'HU Employer16', 'Address1', 'Address2', 'Architectural design', '30', 'www.employer16.com',
'2013-07-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G',
'20000', 'WEEKLY', 'IAESTE', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc', '2013-03-31',
'A portfolio is required with the nomination',
'28000', 'WEEKLY', NULL,
'To take part in the process of architectural design (in designing of houses), to work on CAD station, designing with architectural planning software. ',
'Budapest', 'P', 'E', 'ARCHITECTURE', NULL, '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0017', '9900da6f-adf7-4bce-9ba1-31f42f8d4991', FALSE, 'HUF', '40', '8', '0', 'HU Employer17', 'Address1', 'Address2', NULL, NULL, 'www.employer17.com',
'2013-08-03', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'E', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE', '10000', 'WEEKLY', '8', '8', 'Budapest', 'Bus, tram, etc.', '2013-03-31',
NULL,
'27000', 'WEEKLY', NULL,
'Our company deals mainly with software development and consulting activities. We are looking for a sudent who is interested in Computer Science and in Marketing, management areas as well. The main tasks are preparing company brochures, communication for IT-F in Berlin.',
NULL, 'P', 'M|E', 'IT', NULL, '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0018', '0fdc77f1-12a1-4926-95e5-bdbec78ef8ac', FALSE, 'HUF', '40', '8', '0%', 'HU Employer18', 'Address1', 'Address2', 'Logistics', '30', 'www.employer18.com',
'2013-04-01', '2013-07-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'E', NULL, NULL, NULL,
'20000', 'WEEKLY', 'Company', '10000', 'WEEKLY', '10', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
NULL,
'25000.00', 'WEEKLY', NULL,
'The companys main activity is the international forwarding and logistics. Its main partners are from GERMANy and Austria. the expectations of the scope of activities: taking care of the extant partnerships, looking for new partners, acquisition and administration.',
'Veszprem', 'P', 'M', 'ECONOMICS_OR_TECHNICAL_MANAGEMENT', 'Logistics', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0019', '6d56cfc7-b4a6-4d1b-8ccb-449daab35f95', TRUE, 'HUF', '40', '8', '0', 'HU Employer19', 'Address1', 'Address2', 'Vascular X-ray Imaging Systems', '450', 'www.employer19.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '26', '52', 'Budapest, Ferihegy', 'Bus', '2013-03-31',
'Programming experiance and HUNGARIAN or FRENCH language knowledge are advantages.',
'39000', 'WEEKLY', NULL,
'Image Quality Engineer. Design Image Quality improvements to our next generation Cardiovascular X-ray imaging systems. Improve the optimization of x-ray techniques and the real-time exposure management algorithm to obtain more information with the same dose level. Develop, prototype and optimize image processing algorithm to ensure optimal visibility of anatomy and objects and implementation teams, finally design and execute tests to ensure customer satisfaction and compliance to medical industry standards. Follow-up clinical evaluations.',
'2040 Budaörs, Akron u. 2.', 'R', 'M', 'IT', 'Medical Eng., Image Processing', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0020', '9cd73273-b4f8-4182-93b7-1e4e4d5bf283', FALSE, 'HUF', '40', '8', '0', 'HU Employer20', 'Address1', 'Address2', 'X-ray Imaging Systems', '450', 'www.employer20.com',
'2013-01-01', '2013-05-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '26', '52', 'Budapest, Ferihegy', 'Bus', '2013-05-10',
'Faculty: Computer Science, Electrical or Mechanical Engineering, Physics. Specialisation: Medical Engineering, Image Processing. Programming experience, HUNGARIAN or FRENCH language knowledge are advantages.',
'39000', 'WEEKLY', NULL,
'1 kind of training place is open: Image Quality Engineer. Design Image Quality improvements to our next generation Cardiovascular X-ray imaging systems. Improve the optimization of x-ray techniques and the real-time exposure management algorithm to obtain more information with the same dose level. Develop, prototype and optimize image processing algorithm to ensure optimal visibility of anatomy and objects and implementation teams, finally design and execute tests to ensure customer satisfaction and compliance to medical industry standards. Follow-up clinical evaluations.',
'2040 Budaörs, Akron u. 2.', 'R', 'B', 'IT', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0021', '7bbf7e5f-6f85-478d-9c33-cd1cc62681d3', FALSE, 'HUF', '40', '8', '0', 'HU Employer21', 'Address1', 'Address2', 'Production, marketing and sales of commercial seed', '150', 'www.employer21.com',
'2013-03-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'UKRAINIAN', 'E', NULL, NULL, NULL,
'20000', 'MONTHLY', 'IAESTE Hungary', '10000', 'MONTHLY', '26', '26', 'Budapest, Ferihegy', 'Bus', '2013-03-15',
FALSE,
'27000', 'WEEKLY', NULL,
'Participation in plant breeding, product testing and characterization. Data management and field work are also part of intern program.',
'2040 Budaörs, Neumann J. u. 1.', 'R', 'M', 'AGRONOMY', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0022', '12c632c8-d2a0-405a-ae3a-762ec5b76d5b', FALSE, 'HUF', '40', '8', '0', 'HU Employer22', 'Address1', 'Address2', 'Research and development', '22', 'www.employer22.com',
'2013-03-18', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '24', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
FALSE,
'35000', 'WEEKLY', NULL,
'We are looking for a person to help design and create websites for our running and new projects. They would have to work with our engineers and be able to have a basic understanding of the technologies used in the projects. Their main task would be to design the webpage and to update it with new information. The candidate is required to have Php, MySQL, JavaScript, & Flash programming knowledge. MFKK is a very dynamic company with around 20 running projects in different areas. In addition to the website related tasks the candidate will have the opportunity to get involved in projects related to his/her abilities. MFKK offers a very flexible and diverse job that truly helps develop the intern in many fields and ensures a variety of different task with the opportunity to travel, but we require somebody who is up for the challenge. We prefer somebody who has a social personality as has no trouble to communicate with strangers.',
'1119 Budapest, Tétényi út 93.', 'R', 'M', 'IT', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0023', '7cfbf499-7427-445f-9204-0b16e968b650', FALSE, 'HUF', '40', '8', '0', 'HU Employer23', 'Address1', 'Address2', 'Research and development', '22', 'www.employer23.com',
'2013-03-18', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '24', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
FALSE,
'35000', 'WEEKLY', NULL,
'We are looking for a person to help carry out research and development tasks in a specific project and if possible get involved in other projects as well. It is essential that the candidate has Multiphysics MEMS Simulation background as this would the main field of activity. The main task will be to model and to simulate a given electrostatic MEMS actuator combined with micro-fluidics. MFKK is a very dynamic company with around 20 running projects in different areas. The candidate will be involved in a specific project but has to be open to help in other ones as well if required. The candidate will be required to help the company all-around, which means when not doing technical work, the candidate will have to help with the development of new project concepts. MFKK offers a very flexible and diverse job that truly helps develop the intern in many fields and ensures a variety of different task with the opportunity to travel, but we require somebody who is up for the challenge. We prefe',
'1119 Budapest, Tétényi út 93.', 'R', 'M', 'MECHANICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0024', '819dabe4-ee1a-43f0-9e58-e559682ce09b', FALSE, 'HUF', '40', '8', '0', 'HU Employer24', 'Address1', 'Address2', 'Agricultural Facility', '15', 'www.employer24.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '10', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-05-10',
FALSE,
'27000', 'WEEKLY', NULL,
'The student is going to take part in our usual work including sheet metal fabrication, facility design, part design, workshop work, electrical wiring field installation.',
'7621 Pécs, Zrínyi u. 1.', 'P', 'M', 'MECHANICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0025', '91c22180-bc95-41d0-a41b-b804d52aade3', TRUE, 'HUF', '40', '8', '0', 'HU Employer25', 'Address1', 'Address2', 'Valuation', '20', 'www.employer25.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '24', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-05-10',
FALSE,
'25000', 'WEEKLY', NULL,
'To take part in the work of the collective in the field of real estate valuation, to take part in market research and site inspection.',
'H-1132 Budapest, Váci út 18.', 'P', 'M', 'CIVIL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0026', 'c663d017-7b16-41bf-9895-c3deb69a043f', FALSE, 'HUF', '40', '8', '0', 'HU Employer26', 'Address1', 'Address2', 'Building industry', '33', 'www.employer26.com',
'2013-07-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-06-10',
'HUNGARIAN is an advantage.',
'27000', 'WEEKLY', NULL,
'We offer a position as foreman or expert of technology. The student will work in a project connected with reinforce concrete insulation, steel insulation or bridge and road insulation.',
'1138 Budapest, Karikás Frigyes u. 20.', 'P', 'M', 'CIVIL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0027', '86adcc20-16bc-4c55-96e7-cf257aea0c9b', TRUE, 'HUF', '40', '8', '0', 'HU Employer27', 'Address1', 'Address2', 'Higher education', '15', 'www.employer27.com',
'2013-05-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-05-31',
'Native ENGLISH!',
'27000', 'WEEKLY', NULL,
'Software modeling, software model processing.',
'1117 Budapest, Magyar Tudósok útja 2', 'R', 'E', 'ELECTRICAL_ENGINEERING', 'Computer Science', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0028', 'f888e03f-1eb2-4827-b3ea-df041d965c6d', TRUE, 'HUF', '40', '8', '0', 'HU Employer28', 'Address1', 'Address2', 'Higher education', '15', 'www.employer28.com',
'2013-05-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-05-31',
'Native ENGLISH!',
'27000', 'WEEKLY', NULL,
'Software modeling, software model processing.',
'1117 Budapest, Magyar Tudósok útja 2', 'R', 'E', 'ELECTRICAL_ENGINEERING', 'Computer Science', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0029', 'd4c363a3-718f-41ee-9ff4-c20b09987199', FALSE, 'HUF', '40', '8', '0', 'HU Employer29', 'Address1', 'Address2', 'Architecture', '18', 'www.employer29.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'RUSSIAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, train', '2013-05-31',
'CAD program knowledge',
'27000', 'WEEKLY', NULL,
'To take part in the architectural design process, to work with CAN (ArchiCAD).',
'H-4400 Nyíregyháza, Selyem u. 21.', 'P', 'E', 'ARCHITECTURE', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0030', '5ff0507c-758a-4d30-bbd9-3ae5a60c1540', FALSE, 'HUF', '45', '9', '0', 'HU Employer30', 'Address1', 'Address2', 'Architectural design', '10', 'www.employer30.com',
'2013-08-10', '2013-10-04', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-06-30',
'Portfolio',
'27000', 'WEEKLY', NULL,
'We are busy with designing buildings, developing areas. Our office is also interested in urban developing plans. We design family houses, too, so we are working in all the fields of architecture.',
'1051 Budapest, Október 6. u. 3.', 'P', 'E', 'ARCHITECTURE', 'Architectural design', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0031', '79177619-f33f-4115-ae09-80c58115356a', TRUE, 'HUF', '20', '4', '0', 'HU Employer31', 'Address1', 'Address2', 'Research and development', '25', 'www.employer31.com',
'2013-08-01', '2013-11-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'MONTHLY', 'IAESTE Hungary', '10000', 'MONTHLY', '12', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-07-30',
FALSE,
'27000', 'WEEKLY', NULL,
'The MFKK Invention and Research Center Ltd. is a private research and development organisation based in Budapest. MFKK is active in initiating and implementing R&D projects co-financed by the Seventh Framework Programme of the European Commission. We invite applications for the following position: Project Assistant. Part-time (20hrs/week, flexible working hours between 8-18), contract renewal is an option. Starting date: as soon as possible. There are currently several openings available. General Description of the Position: The candidates will support the preparation of 2-3 R&D project  proposals in close cooperation with the Project Managers.  Responsibilities: Identify possible project partners with relevant expertise all over Europe through web based research/databases etc. Establish first contact via e-mail and over the phone. Provide project information and answer inquires. Collect specific data in aid of proposal writing, literature research. What we are looking for: Fluent in ',
'1119 Budapest, Tétényi út 93.', 'R', 'B', 'MANAGEMENT', '-', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0032', '6e1a274f-932a-4c7c-b1bc-8de4ad146a7b', TRUE, 'HUF', '20', '4', '0', 'HU Employer32', 'Address1', 'Address2', 'Research and development', '25', 'www.employer32.com',
'2013-08-01', '2013-11-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-08-01',
FALSE,
'27000', 'WEEKLY', NULL,
'The MFKK Invention and Research Center Ltd. is a private research and development organisation based in Budapest. MFKK is active in initiating and implementing R&D projects co-financed by the Seventh Framework Programme of the European Commission. We invite applications for the following position: Project Assistant. Part-time (20hrs/week, flexible working hours between 8-18), contract renewal is an option. Starting date: as soon as possible. There are currently several openings available. General Description of the Position: The candidates will support the preparation of 2-3 R&D project  proposals in close cooperation with the Project Managers.  Responsibilities: Identify possible project partners with relevant expertise all over Europe through web based research/databases etc. Establish first contact via e-mail and over the phone. Provide project information and answer inquires. Collect specific data in aid of proposal writing, literature research. What we are looking for: Fluent in ',
'1119 Budapest, Tétényi út 93.', 'R', 'B', 'MANAGEMENT', '-', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0033', '45e2b783-a79e-422e-808f-06e6d283ab2d', TRUE, 'HUF', '20', '4', '0', 'HU Employer33', 'Address1', 'Address2', 'Research and development', '25', 'www.employer33.com',
'2013-08-01', '2013-11-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-08-01',
FALSE,
'27000', 'WEEKLY', NULL,
'The MFKK Invention and Research Center Ltd. is a private research and development organisation based in Budapest. MFKK is active in initiating and implementing R&D projects co-financed by the Seventh Framework Programme of the European Commission. We invite applications for the following position: Project Assistant. Part-time (20hrs/week, flexible working hours between 8-18), contract renewal is an option. Starting date: as soon as possible. There are currently several openings available. General Description of the Position: The candidates will support the preparation of 2-3 R&D project  proposals in close cooperation with the Project Managers.  Responsibilities: Identify possible project partners with relevant expertise all over Europe through web based research/databases etc. Establish first contact via e-mail and over the phone. Provide project information and answer inquires. Collect specific data in aid of proposal writing, literature research. What we are looking for: Fluent in ',
'1119 Budapest, Tétényi út 93.', 'R', 'B', 'MANAGEMENT', '-', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0034', '09419f87-8812-4ad7-b016-8c70d8edc052', TRUE, 'HUF', '40', '8', NULL, 'HU Employer34', 'Address1', 'Address2', 'Preparative organic chemistry', '100', 'www.employer34.com',
'2013-06-15', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
NULL,
'27000', 'WEEKLY', NULL,
'preparative heterocyclic organic chemistry lab work. Synthesizing known compounds efficiently using literature or in-house notebook procedures. Modifying reaction conditions in order to improve yields. (Designing of new or alternate reaction sequences is not a normal expectation, but will enchance the performance evaluation of the trainee). Purifying synthesized chemicals to an appropriate degree. The trainee will be able to use the full range og chromatographic, crystallization and distillation techniques.',
'Budapest', 'R', 'E', 'CHEMISTRY', 'Oganic Chemistry, Drug Chemistry, Heterocyclic Ch', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0035', 'b9d19c3c-61ed-4593-9e8f-1a391e85a964', FALSE, 'HUF', '40', '8', NULL, 'HU Employer35', 'Address1', 'Address2', 'Research and Development', '22', 'www.employer35.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '20', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'Php, MySQL, javaScript & amp, Flash program',
'35000', NULL, NULL,
'We are looking for a person to help design and create website for our running and new projects. They would have to work with our engineers and be able to have a basic understanding of the technologies used in the projects. Their main task would be to design the webpage and to update it with new information, the website related tasks the candidate will have the opportunity to get involve in projects related to his/her abilities.',
'Budapest', 'R', 'M', 'IT', NULL, '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0036', 'ea636f9c-2e95-4c7e-a44d-c70175e0d815', TRUE, 'HUF', '40', '8', NULL, 'HU Employer36', 'Address1', 'Address2', 'Education - research', '25', 'www.employer36.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', 'O', 'HUNGARIAN', 'E',
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '20', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
NULL,
'27000', 'WEEKLY', NULL,
'Computer simulations or laboratory experiment in the field of fluid mechanics. For example hydraulic transents, cavitation, fluid machinery, aeroacustics, hemodynamics. Knowledge of Matlab or _Ansys-CFX is an advantage.',
'Budapest', 'R', 'E', 'MECHANICAL_ENGINEERING', 'Fluid mechanics', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0037', '34ef6df1-8160-41cc-91d3-651553f3a3bf', FALSE, 'HUF', '40', '8', '0', 'HU Employer37', 'Address1', 'Address2', NULL, '650', 'www.employer37.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', 'O', 'ENGLISH', 'F', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE', '10000', 'WEEKLY', '26', '38', 'Budapest', 'bus, train, etc.', '2013-04-01',
NULL,
'27000', 'WEEKLY', NULL,
'System development for airbag-systems:\r\n- conception, development, testing and documentation of airbag-algorithm and software parts\r\n- broaden and automation of test plans for evaluation of robustness and signals of the algorithm\r\n- configuration and adaptation of simulation and test tool for airbag-systems\r\n\r\nRequirements:\r\nknowledge in digital signal processing, Matlab, LabWindows/CVI, C++, able to work independently, good team work ability, high motivation level, responsibility, skills of systematical processing and coordination',
'Budapest', 'R', 'M', 'IT|ELECTRICAL_ENGINEERING|PHYSICS', NULL, '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0038', '48cdb54e-3cb0-4528-8226-c3fc34750701', TRUE, 'HUF', '40', '8', '0', 'HU Employer38', 'Address1', 'Address2', 'Food research', '100', 'www.employer38.com',
'2013-09-01', '2013-10-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '6', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'The food economy and quality department is responsible for sensory evaluation, investigation of consumer reference and bahaviour examination. This department owns a well equied laboratory where these eaminations are going on. A high qualified team works in the department the title topic; questing for the experimental auction method. The tasks of the student include the following topics; literature searching, literature synthesizyng, participation in the organizing and transaction of the experimental auction.',
'Budapest', 'R', 'E', 'FOOD_TECHNOLOGY', 'Food science and agriculture', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0039', 'bb436c85-a468-4f52-b2b0-341134678ee2', FALSE, 'HUF', '40', '8', '0', 'HU Employer39', 'Address1', 'Address2', 'Automative Tool Development', '650', 'www.employer39.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '38', 'Budapest', 'bus, train, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'Mechanical engineering or Mechatronics -Supporting engineering work with simulations -Writing engineering reports -Finite element simulations -Electric engines for windscreen wipers',
'Budapest', 'R', 'B', 'SEE_`KIND_OF_WORK`', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0040', 'f56560f3-27cf-4d16-ba18-ff0816ba349c', FALSE, 'HUF', '40', '8', '', 'HU Employer40', 'Address1', 'Address2', 'Architectural design', '7', 'www.employer40.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'CAD program knowledge. A portfolio is required with the nomination.',
'27000', 'WEEKLY', NULL,
'To take part in the process of arcitectural design (in designing of house), to work on CAD station, designing with architectural planning software.',
'Debrecen', 'P', 'E', 'ARCHITECTURE', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0041', 'df97bd25-bdb2-4861-8a8a-6dbb5a063e75', FALSE, 'HUF', '40', '8', '', 'HU Employer41', 'Address1', 'Address2', 'Architectural', '0', 'www.employer41.com',
'2013-06-01', '2013-09-15', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '4', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'Conceptual design, architectural details, design in 3D application (archicad or autocad, 3D MAX)',
'P&eacute;cs', 'P', 'M', 'ARCHITECTURE', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0042', 'aadd4883-91c5-4cd2-97cf-caacd5380951', FALSE, 'HUF', '40', '8', '0', 'HU Employer42', 'Address1', 'Address2', 'Architectural', '18', 'www.employer42.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'HUNGARIAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'To take part of architectural design process, to work on lad (archicad)',
'Ny&iacute;regyh&aacute;za', 'P', 'E', 'ARCHITECTURE', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0043', '33999803-e273-4a62-b300-ac48319cdd1e', TRUE, 'HUF', '40', '8', '', 'HU Employer43', 'Address1', 'Address2', 'CFD Analyse', '0', 'www.employer43.com',
'2013-05-24', '2013-07-04', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '6', '6', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'- Numerical analyse in the fluid-technical items - Preparation of Geometry and numerical net (with GAMBIT software) - Numerical analyse with the FLUENT program - Processing and evaluation',
'Miskolc', 'R', 'M', 'MECHANICAL_ENGINEERING', 'Fluid engineering', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0044', 'b82132c4-fb9f-41f5-8d4e-4afc73e39934', TRUE, 'HUF', '40', '8', '', 'HU Employer44', 'Address1', 'Address2', 'Engineering services, automated test equipment', '10', 'www.employer44.com',
'2013-07-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'Software developmet, Mechanical engineering, System design, Requirement engineering, Product development and research can be involved. Naturen Ltd. deals primarly with R&amp;amp;D and engineering services. Our experience ranges from the design, development, simulation, virtual and rapid prototype of automated measurement and date acquisition system, customized test equipment and inelligent machines to actual manufacturing, installation and support of complex industrial systems, using of National Instr.products.',
'Budapest', 'R', 'M', 'MECHANICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0045', '2a97aba6-63db-47ab-86bf-2ea305059cad', TRUE, 'HUF', '40', '8', '', 'HU Employer45', 'Address1', 'Address2', 'Agricultural postharrest technology', '15', 'www.employer45.com',
'2013-06-01', '2013-09-15', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '10', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'Sheet metal fabrication, welding, field installation, design with 3D CAD automation basics',
'P&eacute;cs', 'W', 'M', 'MECHANICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0046', '94f8b983-9b89-4b61-9255-22695502e7bd', FALSE, 'HUF', '40', '8', '', 'HU Employer46', 'Address1', 'Address2', 'Research and Development', '22', 'www.employer46.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '20', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'Php, MySQL, javaScript and Flash program',
'35000', 'WEEKLY', NULL,
'We are looking for a person to help design and create website for our running and new projects. They would have to work with our engineers and be able to have a basic understanding of the technologies used in the projects. Their main task would be to design the webpage and to update it with new information, the website related tasks the candidate will have the opportunity to get involve in projects related to his/her abilities.',
'Budapest', 'R', 'M', 'IT', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0047', '56be5ba8-7406-4745-975b-26732c4b7c5c', FALSE, 'HUF', '40', '8', '', 'HU Employer47', 'Address1', 'Address2', 'Bridge and structural design', '30', 'www.employer47.com',
'2013-06-01', '2013-08-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'FRENCH', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'No smoking (if possible)',
'27000', 'WEEKLY', NULL,
'Preparing details structural drawing for bridges or rowers by AutoCAD or making statical cakculations of existing or a new bridges by finete element programs.',
'Budapest', 'P', 'E', 'CIVIL_ENGINEERING', 'Bridge and structural', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0048', '4db38646-87be-49a1-a82f-aaf15d3ad113', FALSE, 'HUF', '40', '8', '', 'HU Employer48', 'Address1', 'Address2', 'Railway construction and engineering', '250', 'www.employer48.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '10', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'Railway construction, practical and site experience, performance orientated tasks. The trainee can get familiar with current construction technology and methods.',
'Celld&ouml;m&ouml;k', 'W', 'M', 'CIVIL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0049', '2f352b44-3937-48d3-a814-67daf2ac6f7f', FALSE, 'HUF', '40', '8', '', 'HU Employer49', 'Address1', 'Address2', 'Precast concrete elements', '370', 'www.employer49.com',
'2013-07-01', '2013-09-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'The prospective construction project in summer we are looking for a trainee in the position of a project assistent, partly working in the plenty partly on the building site.',
'H&oacute;dmez&amp;#337;v&aacute;s&aacute;rhely', 'W', 'E', 'CIVIL_ENGINEERING', 'Construction; building, bridge, infrastructure', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0050', 'b4c5ab89-78a8-4151-a0d4-8e5ae8170fcf', FALSE, 'HUF', '40', '8', '', 'HU Employer50', 'Address1', 'Address2', 'Research and Development', '22', 'www.employer50.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'O', 'ENGLISH', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '12', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'E internet based research skills',
'35000', 'WEEKLY', NULL,
'We invite application for the following position; Project Assistant. The candidates will support the preparation of 2-3 R&amp;amp;amp;D project proposalsin close cooperation with the Project Managers.  Responsibilities; Identify possible project partners with relevant expertise all over Europe through web based research/datebases etc. Establish first contact via e-mail and over the phone. Provide project information and answer inquires. Collect specific data in aid of proposal writing, literature research.',
'Budapest', 'R', 'B', 'PROJECT_ASSISTANT', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0051', 'c277af5b-c25b-4ee7-a6a1-dc8ac65411aa', FALSE, 'HUF', '40', '8', '', 'HU Employer51', 'Address1', 'Address2', 'Chemical engineering, research and laboratory eng.', '12', 'www.employer51.com',
'2013-02-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '6', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'30000', 'WEEKLY', NULL,
'Laboratory work in polymer technology, polymer compositen, pharmaceutical application of polymers, fire retardancy of polymers, recycling of polymers',
'Budapest', 'R', 'M', 'CHEMISTRY', 'Polymer technology', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0052', 'cbd0dbc6-806c-4365-a175-ae8eb17acee8', FALSE, 'HUF', '40', '8', '', 'HU Employer52', 'Address1', 'Address2', 'Sales of office machines', '200', 'www.employer52.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'HUNGARIAN', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '8', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'34500', 'WEEKLY', NULL,
'Get acquinted with the organisation and the processes of the company, a kind of consumer research in the EU region; find possible partners, contacts from the student\\s country, If the cooperation with the student is succesful, he/she can be a future contact of the company.',
'Budapest', 'P', 'M', 'TECHNOLOGICAL_MANAGEMENT', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0053', 'ed706942-69f4-41a0-a958-89e19f4c1975', FALSE, 'HUF', '40', '8', '', 'HU Employer53', 'Address1', 'Address2', 'Software development', '17', 'www.employer53.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '16', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'Elektronics, Physics or Architectural software localization from ENGLISH to one of the following language, RUSSIAN, ITALIAN, Dutch',
'Budapest', 'P', 'M', 'ELECTRICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0054', '480031a2-1925-450b-93ff-b4567ec96110', TRUE, 'HUF', '40', '8', '0', 'HU Employer54', 'Address1', 'Address2', 'Production/automotive division', '2700', 'www.employer54.com',
'2013-04-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'Company', '10000', 'WEEKLY', '12', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-01-01',
'MS Office, Benefit: SAP knowledge',
'27000', 'WEEKLY', NULL,
'1.Muster dates analisys -&amp;amp;gt; the store of producer items/spare parts. Make report those dates in GERMAN. Keep the contact with the Bosch of GERMANy\\s industries and the process engineering of Hatvan. 2. Make thesis plan; - Requirements in the electronic manufacturing/environment - Which environment is needs? - examination and comparison - experiment nad evaluation',
'Hatvan', 'P', 'B', 'ELECTRICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0055', 'ce524381-3758-4d2d-b681-37c23722b94b', TRUE, 'HUF', '40', '8', '', 'HU Employer55', 'Address1', 'Address2', 'Production/automotive division', '2700', 'www.employer55.com',
'2013-09-01', '2013-11-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'Company', '10000', 'WEEKLY', '12', '12', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-08-15',
'MS Office, Benefit: SAP knowledge',
'27000', 'WEEKLY', NULL,
'1.Muster dates analisys -&amp;amp;gt; the store of producer items/spare parts. Make report those dates in GERMAN. Keep the contact with the Bosch of GERMANys industries and the process engineering of Hatvan. 2. Make thesis plan; - Requirements in the electronic manufacturing/environment - Which environment is needs? - examination and comparison - experiment nad evaluation',
'Hatvan', 'P', 'M', 'ELECTRICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0056', 'eaf1d101-e839-4d8b-8a7d-891058de0fc4', FALSE, 'HUF', '40', '8', '0', 'HU Employer56', 'Address1', 'Address2', 'Automative Tool Development', '650', 'www.employer56.com',
'2013-04-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE', '10000', 'WEEKLY', '26', '52', 'Budapest', 'bus, train, etc.', '2013-04-01',
FALSE,
'27000', 'WEEKLY', NULL,
'See attached.',
'Budapest', 'R', 'M', 'IT', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0057', '9ba5bac1-ae6e-4c15-adf3-a368e6cdc471', FALSE, 'HUF', '40', '8', '0', 'HU Employer57', 'Address1', 'Address2', 'Automative Tool Development', '650', 'www.employer57.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', 'O', 'ENGLISH', 'F', NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE', '10000', 'WEEKLY', '26', '38', 'Budapest', 'but, train, etc.', '2013-04-01',
FALSE,
'27000', 'WEEKLY', NULL,
'Studies: electrical engineering, telecommunication, computer science, physics Tester for video based driver assistant system: -broaden and automation of test specifications -configuration and adaption of test tool-environment for video systems Requirements: knowledge in digital  signal and image processing - knowledge of analytical and test-methods, PERL, CAPL, C - able to work independently,  - good team wok ability - high motivation level, - responsibility - analytical way of thinking,  - skills of systematical processing and coordination',
'Budapest', 'R', 'M', 'SEE_KIND_OF_WORK', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0058', 'a5a732de-3ad6-4b63-91d3-3b3e4f728dbb', FALSE, 'HUF', '40', '8', '0', 'HU Employer58', 'Address1', 'Address2', 'Automative Tool Development', '650', 'www.employer58.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '38', 'Budapest', 'bus, train, etc.', '2013-03-31',
'knowledge of programming, and FPGA',
'27000', 'WEEKLY', NULL,
'Tasks: Sensors (acceleration, rotation, pressure, etc.). Measuring development. New measurement concepts, procedures for the new products. The further development of existing measurement techniques. Complex electronic regulator development and planning. Developing and programming measurement results evaluation procedures and routines . Improved procedures, electronics, software, equipment installation, commissioning. Laboratory tool calibration. Team work with the GERMAN and HUNGARIAN engineers.   Requirements: Studies in electrical engineering or physics  Knowledge of at least one high-level programming language (eg C, CVI) FPGA knowledge Communication level GERMAN language skills',
'Budapest', 'R', 'M', 'SEE_&QUOT;KIND_OF_WORK&QUOT;', '', '21');


--===========================================
-- GERMANY
---================
INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0001', '1c7cef53-0541-41a8-92a5-c3fe1685ac84', FALSE, NULL, '40', '8', '', 'DE Employer1', 'Address1', 'Address2', NULL, '0', 'www.employer1.com',
'2006-12-01', '2006-11-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
600, 'MONTHLY', 'Employer', 300, NULL, '30', '52', 'Munich', 'F&uuml;rstenfeldbruck', '2006-12-15',
'',
720, 'MONTHLY', NULL,
'Build forums (private portals) between distributors; channel marketing and product lines to facilitate new tools in managing the channel effectivity. Required: Experience of handling cross-browser and cross-platform web issues.  Understanding of HTML/XHTML; DHTML and Javascript. Basic Photo shop skills. Understanding of Macromedia Flash. Solid communication skills.',
'F&uuml;rstenfeldbruck near Munich', 'P', 'B', 'IT_AND_ECONOMICS', 'Marketing', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0002', '907d0e8f-3606-4c86-8d98-f3f3b36e92ed', FALSE, 'EUR', '39', '8', 'www.iaeste.de/Englis', 'DE Employer2', 'Address1', 'Address2', 'Planning Services', '70', 'www.employer2.com',
'2013-01-31', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Your reception committee', '220', 'MONTHLY', '12', '26', 'Hamburg', 'Baden (Kreis-Verden)', '2013-03-31',
NULL,
'500', 'MONTHLY', NULL,
'Planning services (wastewater-treatment, waste-treatment and recycling).',
'Achim', 'R', 'E', 'ENVIRONMENTAL_ENGINEERING', 'Environmental Engineering', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0003', 'eda06a89-2a48-45a1-b61b-60fa6c09f1ed', TRUE, 'EUR', '40', '8', NULL, 'DE Employer3', 'Address1', 'Address2', 'University Institute', '30', 'www.employer3.com',
'2013-03-31', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '220', 'MONTHLY', '12', '12', 'Duesseldorf', 'Bochum', '2013-03-31',
'Please refer to website: www.iaeste.de/ENGLISH pages',
'650', 'MONTHLY', NULL,
'Human Biomonitoring (Hg, Pb in Blood; As, Cd, Cr, Ni, Hg in Urine). Water quality control. Trace element analysis. Measurement by atomic absorption spectroskopy and photometry.',
'Bochum', 'R', 'M|E', 'CHEMISTRY', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0004', '9871d337-c6df-4450-a863-3546bb15aa50', TRUE, 'EUR', '35', '7', NULL, 'DE Employer4', 'Address1', 'Address2', 'research institute', '250', 'www.employer4.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', NULL, 'F', 'A', NULL, 'F',
'600', 'MONTHLY', 'LC', '220', 'MONTHLY', '12', '26', 'Köln', NULL, '2013-03-31',
NULL,
'600', 'MONTHLY', NULL,
'various projects in the fields of medical systems; electronic consumer products; lighting',
'Aachen', 'R', 'M', 'ELECTRICAL_ENGINEERING', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0005', '61859a52-17e1-43d8-932c-8f6f8d475913', TRUE, 'EUR', '40', '8', 'see', 'DE Employer5', 'Address1', 'Address2', 'Automobile Industry', '13', 'www.employer5.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'E', 'A', 'ENGLISH', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'arranged by employer', '250', 'MONTHLY', '12', '26', 'Stuttgart', 'Neckarsulm', '2013-03-31',
'Basic knowledge of german is not sufficient.',
'800', 'MONTHLY', NULL,
'Training in technical development. Production (logistics, planning); quality protection',
'Neckarsulm', 'P', 'M|E', 'ENGINEERING_(VEHICLE_ENG.|MECHANICAL_ENG.|ELECTRICAL_ENG.)', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0006', '52d80965-1c68-4f86-a43c-d1793c72756d', TRUE, 'EUR', '40', '8', '', 'DE Employer6', 'Address1', 'Address2', 'university institute', '8', 'www.employer6.com',
'2013-06-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE LC', '220', NULL, '12', '12', 'Stuttgart', 'Stuttgart', '2013-05-20',
'bachelor sc. or equivalent required; basic lab skills',
'615', 'MONTHLY', NULL,
'1. Membrane protein biochemistry 2. electron microscopy/tomography of single membrane proteins and membrane protein complexes 3. Electrophysiology/single channel analysis of protein translocases and gap-junctions.',
'Stuttgart', 'R', 'E', 'BIOCHEMISTRY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0007', '0c762aa3-f6bd-44d7-89d8-9bcfc678f1a6', FALSE, 'EUR', '33', '6', '', 'DE Employer7', 'Address1', 'Address2', 'IAESTE LC', '8', 'www.employer7.com',
'2013-06-01', '2013-11-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE LC', '220', NULL, '24', '24', 'München', 'München', '2013-05-31',
'',
'615', 'MONTHLY', NULL,
'LC organization; responsibility for the trainees´ cares; organisation for trips and events',
'Freising', 'N', 'B', 'BUSINESS_STUDIES', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0008', 'cf7c9911-f59b-4f46-b73c-d6f36c42e4e5', FALSE, 'EUR', '40', '8', '', 'DE Employer8', 'Address1', 'Address2', 'IAESTE LC', '50', 'www.employer8.com',
'2013-06-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Lc', '220', NULL, '12', '12', 'Berlin', '', '2013-05-31',
'',
'615', 'MONTHLY', NULL,
'Act as a link between the other IAESTE trainees and the LC; organising arrival and departure procedure of trainees; cultural programme; availability at all times (especially in the evenings and weekends; free days during the week).',
'Berlin', 'N', 'M', 'GERMAN_STUDIES', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0009', '83e7233e-3424-49f7-99ae-a331426a1404', FALSE, 'EUR', '40', '8', '', 'DE Employer9', 'Address1', 'Address2', 'IAESTE LC', '50', 'www.employer9.com',
'2013-08-01', '2013-10-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE LC', '220', NULL, '12', '12', 'Berlin', '', '2013-05-31',
'',
'615', 'MONTHLY', NULL,
'Act as a link between the other trainees and the LC; organising arrival and departure procedure of trainees; cultural programme; availability at all times (especially in the evenings and weekends; free days during the week).',
'Berlin', 'N', 'M', 'GERMAN_STUDIES', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0010', 'ad4bbbd1-95dd-4bab-9d0e-ba5531af7d0b', FALSE, 'EUR', '35', '7', '', 'DE Employer10', 'Address1', 'Address2', 'IAESTE LC', '15', 'www.employer10.com',
'2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE LC', '220', NULL, '12', '12', 'Dortmund', '', '2013-05-31',
'MS Office; Internet research; HTML knowledge',
'615', 'MONTHLY', NULL,
'contactperson for trainees; organization of summer events; troubleshooting; coordination of accomodation; reception; help with public authorities',
'Bochum', 'N', 'M', 'BUSINESS_STUDIES', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0011', 'cfc5fee5-caf7-4c82-b93e-7e2299efb8d0', FALSE, 'EUR', '40', '8', '', 'DE Employer11', 'Address1', 'Address2', 'university institute', '0', 'www.employer11.com',
'2013-06-01', '2013-12-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'reception committee', '220', NULL, '8', '8', 'Hamburg; Berlin', 'Wismar', '2013-06-30',
'knowledge in matlab or  computer language required',
'0', 'MONTHLY', NULL,
'The candidate should work on data processing. Weather data should be interpreted to predict windpower earnings by the Method of Weiboll distribution. Subject will be teached; programming skills should be known before. The daily earnings should be stored in calendar format.',
'Wismar', 'R', 'M', 'ELECTRICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0012', '39114518-c4e9-47ad-8bc8-e316ae5f49bc', FALSE, 'EUR', '40', '8', '', 'DE Employer12', 'Address1', 'Address2', 'university institute', '0', 'www.employer12.com',
'2013-06-01', '2013-12-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'reception committee', '220', NULL, '8', '8', 'Hamburg; Berlin', 'Wismar', '2013-06-30',
'knowledge in matlab or  computer language required',
'0', 'MONTHLY', NULL,
'The candidate should work on data processing. Weather data should be interpreted to predict windpower earnings by the Method of Weiboll distribution. Subject will be teached; programming skills should be known before. The daily earnings should be stored in calendar format.',
'Wismar', 'R', 'M', 'ELECTRICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0013', 'cc48292b-2514-42c7-873f-56858cd57935', FALSE, 'EUR', '8', '40', '', 'DE Employer13', 'Address1', 'Address2', 'IT services', '5', 'www.employer13.com',
'2013-06-01', '2013-04-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Freiburg', '220', NULL, '24', '24', 'Stuttgart', 'Freiburg', '2013-05-31',
'Experience in Web technologies (xml; html; javascript; Sql databases',
'600', 'MONTHLY', NULL,
'Trainee will work in a small team of highly motivated people currently deploying and expanding of a web-based telecommunications consulting business.',
'Freiburg', 'P', 'M', 'IT', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0014', 'fdcfc570-d7c5-471e-821b-7d550deb011f', FALSE, 'EUR', '40', '8', '', 'DE Employer14', 'Address1', 'Address2', 'internet marketplace; e-commerce', '10', 'www.employer14.com',
'2013-05-01', '2013-04-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Aachen', '220', NULL, '24', '24', 'Cologne', 'Aachen', '2013-06-15',
'experience in Linux beneficial',
'600', 'MONTHLY', NULL,
'antibodies-online is a marketplace for biomedical research supplies; i.e. research antibodies. Tasks: Adding new features to the marketplace; optimizing database structure; optimizing data imports and exports.',
'Aachen', 'P', 'B', 'IT', 'php; mysql programming', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0015', 'd67b15d7-8fb4-46e6-9bd2-e71305a49e9a', FALSE, 'EUR', '40', '8', '', 'DE Employer15', 'Address1', 'Address2', 'university institute', '0', 'www.employer15.com',
'2013-06-01', '2013-12-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'reception committee', '220', NULL, '8', '8', 'Hamburg; Berlin', 'Wismar', '2013-06-30',
'knowledge in matlab or  computer language required',
'0', 'MONTHLY', NULL,
'The candidate should work on data processing. Weather data should be interpreted to predict windpower earnings by the Method of Weiboll distribution. Subject will be teached; programming skills should be known before. The daily earnings should be stored in calendar format.',
'Wismar', 'R', 'M', 'ELECTRICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0016', '2b782dde-a873-4688-8878-4ff6cd07706e', TRUE, 'EUR', '40', '8', '', 'DE Employer16', 'Address1', 'Address2', 'research institute', '0', 'www.employer16.com',
'2013-09-01', '2013-03-01', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Stuttgart', '220', NULL, '16', '16', '', '', '2013-06-15',
'basic knowledge of composite materials desired',
'615', 'MONTHLY', NULL,
'Development of Ceramic Matrix Composites (CMCs); Liquid Silicon Infiltration Processes; Joining Methods; Coatings; Design and Manufacture of Components of reinforced Ceramics. Applications on Earth: High performance brake disks and clutches (Formula 1; civil engineering); Components with low thermal expansion (calibrating plates; telescopes); New components for engergy production (Heat exchanger; Combustion chambers). Aerospace Applications: Heat shields for re-entry capsules X 38/CRV; Aeronautic Applications: Gearing technique; flow profiles; diffuser.',
'Stuttgart', 'R', 'M', 'AERONAUTICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0017', 'b58607fe-9292-4139-91b7-045ef41b9b23', TRUE, 'EUR', '40', '8', '', 'DE Employer17', 'Address1', 'Address2', 'university institute', '0', 'www.employer17.com',
'2013-06-01', '2013-03-31', NULL, NULL, NULL, NULL, 'GERMAN', 'F', 'A', 'FRENCH', 'F', NULL, NULL, NULL,
'600', 'MONTHLY', 'reception committee', '220', NULL, '16', '16', 'Frankfurt', 'Bingen', '2013-06-30',
'knowledge of current PC software required',
'615', 'MONTHLY', NULL,
'Set up a data bank of environmental legislation of Maghreb countries Analyze options to improve recycling and treatment of electronic waste in developping countries',
'Bingen', 'R', 'M', 'ECONOMICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0018', '105c4fa4-fed9-46d0-99f5-e42e1969ab6f', FALSE, 'EUR', '40', '8', '', 'DE Employer18', 'Address1', 'Address2', 'building contractors', '80', 'www.employer18.com',
'2013-05-01', '2013-09-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Freiberg', '220', NULL, '8', '8', 'Dresden', 'Freiberg', '2013-05-15',
'',
'600', 'MONTHLY', NULL,
'Assistance on building sites; customer contacts; coordination of capacities',
'Freiberg', 'P', 'M', 'CIVIL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0019', '91dbc1d9-131c-4c4f-88de-448ef1a8a1ed', TRUE, 'EUR', '40', '8', '', 'DE Employer19', 'Address1', 'Address2', 'Dairy and vegetable farm', '2', 'www.employer19.com',
'2013-01-01', '2013-02-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'F', NULL, NULL, NULL,
'0', 'MONTHLY', 'Employer', '0', NULL, '24', '24', 'Hannover', 'Salzwedel', '2013-06-15',
'one year of study completed',
'500', 'MONTHLY', NULL,
'working with dairy cattle; office work; quality assurance; documentation; accounting. Agricultural work in the field of potato and onion cultivation.  Free board and lodging; GERMAN language course possible.',
'Lüchow', 'P', 'B', 'AGRONOMY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0020', '9ffea414-1809-4c79-81da-f0a295aed81e', TRUE, 'EUR', '40', '8', '', 'DE Employer20', 'Address1', 'Address2', 'research institute', '110', 'www.employer20.com',
'2013-07-01', '2013-02-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Duisburg', '220', NULL, '8', '12', 'Duesseldorf', 'Muelheim', '2013-07-15',
'E marks required',
'615', 'MONTHLY', NULL,
'Experimental research in laboratories in the fields of: bioinorganic chemistry; bioinorganic spectroscopy; bioorganic chemistry; biophysical chemistry; electrochemistry; electron transfer chemistry; EPR; inorganic chemistry; laser spectroscopy; magnetic susceptibility; metal-organic chemistry; metalproteins; moessbauer spectroscopy; photobiological; photochemistry; radical chemistry; synthetic organic chemistry.',
'Muelheim/Ruhr', 'R', 'M', 'CHEMISTRY', 'bioorganic chemistry', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0021', '656424ca-3788-4ef5-9bd1-5a1aea7c1d8e', FALSE, 'EUR', '40', '8', '', 'DE Employer21', 'Address1', 'Address2', 'university institute', '0', 'www.employer21.com',
'2013-07-01', '2013-12-14', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Freiburg', '220', NULL, '16', '16', 'Frankfurt', 'Freiburg', '2013-06-15',
'',
'615', 'MONTHLY', NULL,
'The work comprises metallurgical analysis of solder structures for electronic deviceds; recrystalization; formatin of intermetallic compounds and recrystalization-assisted cracking of the bulk solder due to thermo-mechanical stress and flux of electric flux. Work consists mainly of practical work: preparation of metallographical cuts; but also of analysis of the results; photography; creation of plots; etc.',
'Freiburg', 'R', 'M', 'SYSTEMS_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0022', '158e5f72-204e-42bc-b74e-5d0dd8ee5856', FALSE, 'EUR', '40', '8', '', 'DE Employer22', 'Address1', 'Address2', 'university institute', '0', 'www.employer22.com',
'2013-07-01', '2013-12-14', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Freiburg', '220', NULL, '16', '16', 'Frankfurt', 'Freiburg', '2013-06-15',
'',
'615', 'MONTHLY', NULL,
'The work comprises metallurgical analysis of solder structures for electronic deviceds; recrystalization; formatin of intermetallic compounds and recrystalization-assisted cracking of the bulk solder due to thermo-mechanical stress and flux of electric flux. Work consists mainly of practical work: preparation of metallographical cuts; but also of analysis of the results; photography; creation of plots; etc.',
'Freiburg', 'R', 'M', 'SYSTEMS_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0023', '4212b070-61bc-4866-b067-46f43ad7f743', FALSE, 'EUR', '40', '8', '', 'DE Employer23', 'Address1', 'Address2', NULL, '0', 'www.employer23.com',
'2013-09-01', '2013-11-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'LC Wilhelmshaven', '220', NULL, '8', '8', 'Hamburg', 'Wilhelmshaven', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'Joining research in avian ecology; with special emphasis on spatio-temporal distribution of coastal birds; breeding and foraging  in coastal birds and sea birds; nutritional adaptions in migrating songbirds; scientific bird banding',
'Wilhelmshaven', 'R', 'M', 'ZOOLOGY', 'ornithology', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0024', '0e0f012a-b41e-4e62-8ef7-9142a88f6fe4', FALSE, 'EUR', '40', '8', '', 'DE Employer24', 'Address1', 'Address2', 'University institute', '40', 'www.employer24.com',
'2013-08-01', '2013-12-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Dresden', '220', NULL, '8', '8', 'Dresden', 'Dresden', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'Investigation of Laserbeam-Ceramic-Interaction. Investigation of two phase flow phenomena in a circulation loop in special consideration of the Borate transport. Mathematical simulation of experimental results with codes (e.g. Matlab-Simulink)',
'Dresden', 'R', 'E', 'POWER_ENGINEERING', 'laser technology', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0025', '49e91792-6b1f-4fa9-9d2b-2a43c27fe467', FALSE, 'EUR', '40', '8', '', 'DE Employer25', 'Address1', 'Address2', 'University institute', '0', 'www.employer25.com',
'2013-08-01', '2013-03-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Freiberg', '220', NULL, '8', '8', 'Dresden', 'Freiberg', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'digital processing of remote sensing data; geomorphological and environmental analyses; digital elevation model generation and processing',
'Freiberg', 'R', 'B', 'GEOSCIENCE', 'Remote sensing', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0026', 'e1befbf7-8169-46c7-a365-affb4cdda376', FALSE, 'EUR', '40', '8', '', 'DE Employer26', 'Address1', 'Address2', 'University institute', '0', 'www.employer26.com',
'2013-08-01', '2013-03-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Freiberg', '220', NULL, '8', '8', 'Dresden', 'Freiberg', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'digital processing of remote sensing data; geomorphological and environmental analyses; digital elevation model generation and processing',
'Freiberg', 'R', 'B', 'GEOSCIENCE', 'Remote sensing', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0027', '580bb6c7-94ec-4231-8f0c-bd94ebef5007', FALSE, 'EUR', '40', '8', '', 'DE Employer27', 'Address1', 'Address2', 'University institute', '0', 'www.employer27.com',
'2013-08-01', '2013-03-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Freiberg', '220', NULL, '8', '8', 'Dresden', 'Freiberg', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'digital processing of remote sensing data; geomorphological and environmental analyses; digital elevation model generation and processing',
'Freiberg', 'R', 'B', 'GEOSCIENCE', 'Remote sensing', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0028', 'dbabd35a-6fb1-4408-849a-291835533542', TRUE, 'EUR', '40', '8', '', 'DE Employer28', 'Address1', 'Address2', 'university clinic', '2000', 'www.employer28.com',
'2013-09-01', '2013-02-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '8', '8', 'Berlin', 'Greifswald', '2013-07-11',
'experience in laboratory work. Native ENGLISH speaker if possible',
'615', 'MONTHLY', NULL,
'Practical training in a laboratory for molecular biology; biochemistry; histology. Examination of samples on the effect of drugs on certain types of tissue',
'Greifswald', 'R', 'M', 'MOLECULAR_BIOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0029', '7b67d491-b73a-4005-81bb-01d9a9e3c60a', FALSE, 'EUR', '40', '8', '', 'DE Employer29', 'Address1', 'Address2', NULL, '0', 'www.employer29.com',
'2013-08-01', '2013-02-28', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '220', NULL, '8', '16', 'Leipzig', 'Dollstädt/Erfurt', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'Cooperation at the registration of the avifauna of special game-parks in Thuringia and Brandenburg; registration of lawn breeding birds ; cooperation at the development of protection and maintenance plans; working in the forest; consulting tasks for public administration and companies',
'Gierstädt', 'N', 'M', 'ECOLOGY', 'forestry', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0030', 'e71f46e6-d73b-43fc-9a53-54cb84ea5319', FALSE, 'EUR', '40', '8', '0', 'DE Employer30', 'Address1', 'Address2', 'Radiation treatment', '58', 'www.employer30.com',
'2013-09-01', '2013-02-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Dresden', '220', NULL, '12', '16', 'Dresden', '', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'Investigations to the operation fo an industrial electron irradiation facility. Material research for high-dose irradiation. Measuring programmes and shielding design in radiation protection',
'Radeberg near Dresden', 'R', 'E', 'PROCESS_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0031', '6f01284a-4d23-4a7b-bd9e-e6862b941e54', FALSE, 'EUR', '8', '40', '', 'DE Employer31', 'Address1', 'Address2', 'University', '0', 'www.employer31.com',
'2013-09-01', '2013-03-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '12', '12', 'Hannover', 'Clausthal', '2013-07-11',
'solid knowledge of mathematics and science',
'615', 'MONTHLY', NULL,
'Extractive metallurgy; foundry technology; thermodynamic and kinetic investigations; electrochemistry; corrosion; sensors; fuel cell technology',
'Clausthal', 'R', 'M', 'PROCESS_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0032', '8952cb06-9a2a-4c5b-b340-6abe9ed7b683', TRUE, 'EUR', '40', '8', '', 'DE Employer32', 'Address1', 'Address2', 'University institute', '0', 'www.employer32.com',
'2013-09-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Bochum', '220', NULL, '16', '16', 'Düsseldorf', 'Bochum', '2013-07-11',
'',
'615', 'MONTHLY', NULL,
'Synthetic inorganic chemistry. The applicant should have experiences in synthetic chemistry; preferably also with inert-gas techniques (glove-box)',
'Bochum', 'R', 'B', 'CHEMISTRY', 'organometallic chemistry', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0033', '3f71344a-1058-40ad-9cfd-ec79e6a3d7b6', FALSE, 'EUR', '40', '8', '', 'DE Employer33', 'Address1', 'Address2', 'biotechnology', '0', 'www.employer33.com',
'2013-08-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', '', '', '2013-07-11',
'master student desired',
'615', 'MONTHLY', NULL,
'Practical training in the fields of chemical analysis; proof of drug substances and products; cell biology; physiochemistry and informatics',
'Saarbrücken', 'R', 'B', 'PHARMACY', 'biotechnology', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0034', 'dd356f9e-15d4-4e0d-819b-3882bedfdb43', FALSE, 'EUR', '40', '8', '', 'DE Employer34', 'Address1', 'Address2', 'research institute', '400', 'www.employer34.com',
'2013-08-01', '2013-12-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '220', NULL, '12', '12', 'Dresden', 'Dresden', '2013-07-11',
'',
'610', 'MONTHLY', NULL,
'Production and characterisation of superconductors; magnetic materials and amorphous metals',
'Dresden', 'R', 'E', 'PHYSICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0035', '65ebad2f-79d6-467f-a50e-24f50c1ac39b', FALSE, 'EUR', '40', '8', '', 'DE Employer35', 'Address1', 'Address2', 'Mining company', '0', 'www.employer35.com',
'2013-01-01', '2013-01-01', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'0', 'MONTHLY', 'Reception committee', '220', NULL, '8', '8', 'Düsseldorf', '', '2013-08-01',
'',
'615', 'MONTHLY', NULL,
'Working in a mine;only underground',
'info later', 'P', 'M', 'MINING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0036', 'f3cdf27e-b1b7-405c-ae4f-c288ef73841c', FALSE, 'EUR', '40', '8', '', 'DE Employer36', 'Address1', 'Address2', 'Mining company', '0', 'www.employer36.com',
'2013-01-01', '2013-01-01', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'0', 'MONTHLY', 'Reception committee', '220', NULL, '8', '8', 'Düsseldorf', '', '2013-08-01',
'',
'615', 'MONTHLY', NULL,
'Working in a mine;only underground',
'info later', 'P', 'M', 'MINING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0037', 'd1fa88b3-b348-4d08-b10b-4ead503d0363', FALSE, 'EUR', '40', '8', '', 'DE Employer37', 'Address1', 'Address2', 'Mining company', '0', 'www.employer37.com',
'2013-01-01', '2013-01-01', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'0', 'MONTHLY', 'Reception committee', '220', NULL, '8', '8', 'Düsseldorf', '', '2013-08-01',
'',
'615', 'MONTHLY', NULL,
'Working in a mine;only underground',
'info later', 'P', 'M', 'MINING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0038', '92308e41-3a10-4027-a85f-1bb8f634816f', FALSE, 'EUR', '40', '8', '', 'DE Employer38', 'Address1', 'Address2', 'University institute', '41', 'www.employer38.com',
'2013-08-01', '2013-02-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '8', '12', 'Dresden', 'Jena', '2013-07-11',
'',
'0', 'MONTHLY', NULL,
'Glass science and technology. Research and development of optial glasses; glass ceramics and coloured glasses. Studies on structure of glass.',
'Jena', 'R', 'E', 'CHEMISTRY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0039', '69d4c983-58c4-4ad0-9246-e2d3b2f96abd', FALSE, 'EUR', '40', '8', '', 'DE Employer39', 'Address1', 'Address2', 'Food company', '0', 'www.employer39.com',
'2013-08-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', 'München', 'München', '2013-07-11',
'Internship has to be compulsory part of studies. Enrollment at university for whole period of training required. Skills in Microsoft office. Team worker.',
'600', 'MONTHLY', NULL,
'Internship Regulatory Affairs; Support in preparing and updation product packaging and labeling. Preparation of nutrive labeling. Support in getting technical specifications licence approval for raw materials and finished products. Support of the project and document management.',
'München', 'P', 'M', 'FOOD_TECHNOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0040', 'faab7a37-30c0-41cf-b5c7-eb048fcbdb42', FALSE, 'EUR', '40', '8', '', 'DE Employer40', 'Address1', 'Address2', 'Food company', '0', 'www.employer40.com',
'2013-08-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', 'München', 'München', '2013-07-11',
'Internship has to be compulsory part of studies. Enrollment at university for whole period of training required. Skills in Microsoft office. Team worker.',
'600', 'MONTHLY', NULL,
'Internship Regulatory Affairs; Support in preparing and updation product packaging and labeling. Preparation of nutrive labeling. Support in getting technical specifications licence approval for raw materials and finished products. Support of the project and document management.',
'München', 'P', 'M', 'FOOD_TECHNOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0041', 'c85ff8c6-332a-4690-b0f8-f4f24e7d96c2', FALSE, 'EUR', '40', '8', '', 'DE Employer41', 'Address1', 'Address2', 'Food company', '0', 'www.employer41.com',
'2013-08-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', 'München', 'München', '2013-07-11',
'Internship has to be compulsory part of studies. Enrollment at university for whole period of training required. Skills in Microsoft office. Team worker.',
'600', 'MONTHLY', NULL,
'Internship Confectionary Technology: Assist scientist in execution of ongoing confectionary projects. Run pilot plant equipment and perform measurements (e.g. rheology; particle size). Analysis of industrial processes for the production of chocolate. Working together with different R and D functions and production plants. Develop test methods related to physical properties of chocolate.',
'München', 'P', 'M', 'MECHANICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0042', '67568261-ca2f-413d-8c2d-080ee58ae4ef', FALSE, 'EUR', '40', '8', '', 'DE Employer42', 'Address1', 'Address2', 'Food company', '0', 'www.employer42.com',
'2013-08-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', 'München', 'München', '2013-07-11',
'Internship has to be compulsory part of studies. Enrollment at university for whole period of training required. Skills in Microsoft office. Team worker.',
'600', 'MONTHLY', NULL,
'Internship Confectionary Technology: Assist scientist in execution of ongoing confectionary projects. Run pilot plant equipment and perform measurements (e.g. rheology; particle size). Analysis of industrial processes for the production of chocolate. Working together with different R and D functions and production plants. Develop test methods related to physical properties of chocolate.',
'München', 'P', 'M', 'MECHANICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0043', 'b6c796d4-64fd-4203-be9d-acfbd7269589', FALSE, 'EUR', '40', '8', '', 'DE Employer43', 'Address1', 'Address2', 'Food company', '0', 'www.employer43.com',
'2013-08-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', 'München', 'München', '2013-07-11',
'Internship has to be compulsory part of studies. Enrollment at university for whole period of training required. Skills in Microsoft office. Team worker.',
'600', 'MONTHLY', NULL,
'Internship &quot;Confectionary Technology&quot;: Assist scientist in execution of ongoing confectionary projects. Run pilot plant equipment and perform measurements (e.g. rheology; particle size). Analysis of industrial processes for the production of chocolate. Working together with different R and D functions and production plants. Develop test methods related to physical properties of chocolate.',
'München', 'P', 'M', 'MECHANICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0044', 'db9612fc-ea62-4814-94cd-4b4ee0192ebe', FALSE, 'EUR', '40', '8', '', 'DE Employer44', 'Address1', 'Address2', 'Food company', '0', 'www.employer44.com',
'2013-08-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', 'München', 'München', '2013-07-11',
'Internship has to be compulsory part of studies. Enrollment at university for whole period of training required. Skills in Microsoft office. Team worker.',
'600', 'MONTHLY', NULL,
'Internship Packaging Laboratory. Determining and evaluatiing packaging material properties. Testing of interactions between  packaging and food. Making mock ups and test packages. Maintaining lab equípment.',
'München', 'P', 'M', 'FOOD_TECHNOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0045', '13a821b7-6849-4e25-8501-cde2b84946ca', FALSE, 'EUR', '40', '8', '', 'DE Employer45', 'Address1', 'Address2', 'research institute', '0', 'www.employer45.com',
'2013-07-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '26', '26', 'Dresden', 'Jena', '2013-01-01',
'',
'615', 'MONTHLY', NULL,
'RoMA (Routine Measurements & Analysis) is one fo the service facilities of the Max-Planck-Institute for Biogeochemistry. Key aspects of activity in our lab comprise the determination of single elements (C; N); ions (nutrients; anions) as well as sum parameters (TOC. TIC; TNb) in liquids (e.g. groundwaters; seawaters; leachates and extracts) and solids (e.g. soils; sediments; rocks; plants and other biological materials). The analytical methods we offer are  Elemental Analysis; Sum Parameter Analysis; Continuous Flow Analysis and Ion Chromatography',
'Jena', 'R', 'B', 'CHEMISTRY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0046', '98abe6f4-fdeb-46b7-852c-52d8c666e5c3', FALSE, 'EUR', '40', '8', '', 'DE Employer46', 'Address1', 'Address2', 'research institute', '0', 'www.employer46.com',
'2013-07-01', '2013-10-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Reception committee', '220', NULL, '8', '8', 'Dresden', 'Jena', '2013-07-18',
'',
'615', 'MONTHLY', NULL,
'Chemical synthesis of polymers and composites for biomedical applications; biological in vitro characterization of biomaterials',
'Jena', 'R', 'M', 'CHEMISTRY', 'polymer chemistry; biomaterials', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0047', 'b9aab9f8-345a-47ed-a857-d89bc80c1b3b', TRUE, 'EUR', '40', '8', '', 'DE Employer47', 'Address1', 'Address2', 'university institute', '12', 'www.employer47.com',
'2013-01-01', '2013-01-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'local committee', '220', NULL, '12', '12', 'Hamburg', 'Hamburg', '2013-07-15',
'CAD',
'615', 'MONTHLY', NULL,
'Construction with CAD-Systems Interpretation and calculation of construction projects practical experiments with constructed conversion tools',
'Hamburg', 'R', 'M', 'MECHANICAL_ENGINEERING', 'conversion technology', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0048', '8c7f4b03-bb17-435b-af21-9460032f0b60', TRUE, 'EUR', '40', '8', '', 'DE Employer48', 'Address1', 'Address2', 'university institute', '12', 'www.employer48.com',
'2013-01-01', '2013-01-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'local committee', '220', NULL, '12', '12', 'Hamburg', 'Hamburg', '2013-07-15',
'CAD',
'615', 'MONTHLY', NULL,
'Construction with CAD-Systems Interpretation and calculation of construction projects practical experiments with constructed conversion tools',
'Hamburg', 'R', 'M', 'MECHANICAL_ENGINEERING', 'conversion technology', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0049', '355bc337-c30e-46be-8767-8b55c6f51d07', FALSE, 'EUR', '40', '8', '', 'DE Employer49', 'Address1', 'Address2', 'E-commerce', '12', 'www.employer49.com',
'2013-07-01', '2013-03-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'reception committee', '220', NULL, '12', '12', 'Hamburg', 'Hamburg', '2013-08-01',
'',
'615', 'MONTHLY', NULL,
'Web development. Programming in Linux; MySql; Java; php Experience required.',
'Hamburg', 'P', 'M', 'IT', 'Web development', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0050', 'f1c10208-3974-4dc9-9dfc-aa6d0bf37d76', TRUE, 'EUR', '40', '8', '', 'DE Employer50', 'Address1', 'Address2', 'university institute', '12', 'www.employer50.com',
'2013-07-01', '2013-03-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'local committee', '220', NULL, '12', '12', 'Hamburg', 'Hamburg', '2013-07-15',
'CAD',
'615', 'MONTHLY', NULL,
'FEM simulation with MSC Superform 2005 Simulations of size effects in microtechnology Examination of simulation results by practical experiments',
'Hamburg', 'R', 'M', 'MECHANICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0051', '9439ed13-0d89-40bf-adb3-7c98f91740a6', FALSE, 'EUR', '38', '8', '', 'DE Employer51', 'Address1', 'Address2', 'Informatics', '10500', 'www.employer51.com',
'2013-12-01', '2013-06-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', 'A', 'ENGLISH', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE Muenchen', '220', NULL, '24', '24', 'Munich', '', '2013-07-31',
'',
'615', 'MONTHLY', NULL,
'Work on international people development topics; support team concerning talentmanagement; upward appraisal; etc;  support team in administrative work; Languages: Fluent GERMAN and ENGLISH. MS office required Training period: 6 months',
'Muenchen', 'P', 'M', 'INDUSTRIAL_ENGINEERING', 'personel development', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0052', '323a5ecd-e08c-46cb-8b42-64d4d3c6027d', FALSE, 'EUR', '40', '8', '', 'DE Employer52', 'Address1', 'Address2', 'architects office', '2', 'www.employer52.com',
'2013-11-01', '2013-06-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '250', 'MONTHLY', '8', '24', 'Berlin', 'Berlin', '2013-11-09',
'basic GERMAN desired, but not neccessary',
'650', 'MONTHLY', NULL,
'Planning and detailing of projects to be built, competitions, presentations, modelmaking. Student needed as soon as possible.',
'Berlin', 'P', 'M', 'ARCHITECTURE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0053', '28c72436-65ad-459e-ae93-926e0264e37b', TRUE, 'EUR', '40', '8', '', 'DE Employer53', 'Address1', 'Address2', 'Software Development', '20', 'www.employer53.com',
'2013-02-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'630', 'MONTHLY', 'IAESTE Karlsruhe', '250', 'MONTHLY', '26', '26', 'Stuttgart', 'Karlsruhe', '2013-12-07',
'E knowledge in C, C++, Java, C#, XML, Software design, Software specifications, Distributed Applications, TCP/IP',
'650', 'MONTHLY', NULL,
'Being part of the Quality Assurance team. Creating testsuites, analysing software, enhancing given test- and metric tools, testing, writing test reports, etc.',
'Karlsruhe', 'P', 'E', 'SOFTWARE', 'software development', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0054', '2bc2309a-a392-4e7c-bed1-8b5110475c3c', TRUE, 'EUR', '40', '8', '', 'DE Employer54', 'Address1', 'Address2', 'University institute', '15', 'www.employer54.com',
'2013-01-01', '2013-02-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE-LC', '220', 'MONTHLY', '8', '8', 'München', 'Freising', '2013-12-15',
'',
'650', 'MONTHLY', NULL,
'Integration in a research project on Carbon storage on wetland ecosystems. Sampling of CO2-exchange via closed-chamber technique on different vegetation types along a degradation-restoration gradient, biomass sampling, determination of LAI, data-processing, parametization of CO2-exchange models, modelling of gas-exchange balances for part of the season, literature review in relevant fields.',
'Freising', 'R', 'B', 'BIOLOGY', 'ecology, geography, ressource management, agricult', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0055', '72c22160-f557-451c-a273-92e1e1144dde', TRUE, 'EUR', '40', '8', '', 'DE Employer55', 'Address1', 'Address2', 'University institute', '15', 'www.employer55.com',
'2013-01-01', '2013-02-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE-LC', '220', 'MONTHLY', '8', '8', 'München', 'Freising', '2013-12-15',
'',
'650', 'MONTHLY', NULL,
'Integration in a research project on Carbon storage on wetland ecosystems. Sampling of CO2-exchange via closed-chamber technique on different vegetation types along a degradation-restoration gradient, biomass sampling, determination of LAI, data-processing, parametization of CO2-exchange models, modelling of gas-exchange balances for part of the season, literature review in relevant fields.',
'Freising', 'R', 'B', 'BIOLOGY', 'ecology, geography, ressource management, agricult', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0056', '59cbf8c0-6785-4c1f-ac7a-12fae6356942', TRUE, 'EUR', '40', '8', '', 'DE Employer56', 'Address1', 'Address2', 'University institute', '15', 'www.employer56.com',
'2013-03-01', '2013-04-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE-LC', '220', 'MONTHLY', '8', '8', 'München', 'Freising', '2013-12-15',
'',
'650', 'MONTHLY', NULL,
'Integration in a research project on Carbon storage on wetland ecosystems. Sampling of CO2-exchange via closed-chamber technique on different vegetation types along a degradation-restoration gradient, biomass sampling, determination of LAI, data-processing, parametization of CO2-exchange models, modelling of gas-exchange balances for part of the season, literature review in relevant fields.',
'Freising', 'R', 'B', 'BIOLOGY', 'ecology, geography, ressource management, agricult', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0057', 'd1a530d0-9eee-4031-9b8e-8e01cee1e94e', TRUE, 'EUR', '40', '8', '', 'DE Employer57', 'Address1', 'Address2', 'University institute', '15', 'www.employer57.com',
'2013-03-01', '2013-04-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE-LC', '220', 'MONTHLY', '8', '8', 'München', 'Freising', '2013-12-15',
'',
'650', 'MONTHLY', NULL,
'Integration in a research project on Carbon storage on wetland ecosystems. Sampling of CO2-exchange via closed-chamber technique on different vegetation types along a degradation-restoration gradient, biomass sampling, determination of LAI, data-processing, parametization of CO2-exchange models, modelling of gas-exchange balances for part of the season, literature review in relevant fields.',
'Freising', 'R', 'B', 'BIOLOGY', 'ecology, geography, ressource management, agricult', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0058', '729006b9-d806-4712-8be2-639c29b0517a', FALSE, 'EUR', '40', '8', '', 'DE Employer58', 'Address1', 'Address2', 'semiconductor producer', '40000', 'www.employer58.com',
'2013-01-01', '2013-07-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'F', 'O', 'PORTUGUESE', 'F',
'650', 'MONTHLY', 'IAESTE München', '220', 'MONTHLY', '26', '26', 'Munich', 'Munich Central Station', '2013-01-15',
'Technical school degree in electrical eng., electronics or equivalent qualification',
'800', 'MONTHLY', NULL,
'Performing EMC measurements and analysis. Characterization of ESD (Electrostatic  discharge) protection structures for automotive power ICs. Documentation and presentation of results. Automation of measurement setups and test benches using labview. Experience in test and measurement techniques, Labview programming, EMC, or semiconductor device physics or analog circuit design required.',
'München', 'R', 'B', 'ELECTRICAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0059', '8bea8241-b460-478b-a4ef-fb51ba69a2ea', TRUE, 'EUR', '40', '8', '', 'DE Employer59', 'Address1', 'Address2', 'Food and beverages', '300', 'www.employer59.com',
'2013-01-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE Muenchen', '250', 'MONTHLY', '8', '8', 'Munich', '', '2013-02-01',
'Internship must be compulsory part of studies.',
'880', 'MONTHLY', NULL,
'Flavour research project. Identify key flavour compounds. Identify innovative routes of flavour management in industrial processes. Prepare research report and present technical recommendations to management.',
'München', 'N', 'M', 'CHEMISTRY', 'Food chemistry', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0060', 'd86d995f-9d9e-4b32-ba79-78cbe742408b', TRUE, 'EUR', '40', '8', '', 'DE Employer60', 'Address1', 'Address2', 'Food and beverages', '300', 'www.employer60.com',
'2013-01-01', '2013-08-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE Muenchen', '250', 'MONTHLY', '26', '26', 'Munich', '', '2013-02-01',
'Internship must be compulsory part of studies.',
'880', 'MONTHLY', NULL,
'Image lab training. Support pilot project of 3D models from technical drawings or animatics to visualization of opening processes of packages. Support image lab in production of concept boards. Solid knowledge of 3D programme Light Wave from Newtec or similar. Working knowledge of Photoshop and Adobe Creative Suite required.',
'München', 'R', 'M', 'IT', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0061', 'ee2d1fef-a206-40eb-a0fc-3c291f3959d9', FALSE, 'EUR', '40', '8', 'see www.iaeste.de/en', 'DE Employer61', 'Address1', 'Address2', 'university', '8', 'www.employer61.com',
'2013-08-01', '2013-10-01', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, 'GERMAN', 'F', NULL, NULL, NULL,
'600', 'MONTHLY', 'employer', '220', 'MONTHLY', '8', '8', 'Berlin', 'Berlin Ostbahnhof', '2013-03-31',
'If possible please apply for full months. Experience in C-programming',
'650', 'MONTHLY', NULL,
'Nonlinear optimisation, algorithmic differentiation, differentiation, programm transformation',
'Berlin-Adlershof', NULL, 'E', 'MATHEMATICS|IT|SCIENTIFIC_COMPUTING', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0062', '7319d8d6-6875-44f8-9d59-0ba3fdd8c56a', FALSE, 'EUR', '40', '8', 'please refer to webs', 'DE Employer62', 'Address1', 'Address2', 'university', '800', 'www.employer62.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'F', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '220', 'MONTHLY', '8', '8', 'Hamburg', 'Lüneburg', '2013-03-31',
'If possible apply for full months',
'650', 'MONTHLY', NULL,
'Research and development work in : optics, medical technology, automotice assistence systems',
'Lüneburg', NULL, 'M|E', 'MECHATRONICS|PHYSICS|TECHNICAL_OPTICS', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0063', '02c7be96-b60f-4549-b30d-8de3b08bfb77', TRUE, 'EUR', '40', '8', 'www.iaeste.de/Englis', 'DE Employer63', 'Address1', 'Address2', 'research institute', '120', 'www.employer63.com',
'2013-10-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '260', 'MONTHLY', '8', '8', 'Hannover', 'Goslar Hbf', '2013-03-31',
'If possible, please apply for full months.',
'650', 'MONTHLY', NULL,
'Waste water treatment; sewage sludge treatment; air purification processes; biological processes; thermal waste treatment; chemical/biological laboratories; energy processes',
'Clausthal-Zellerhof', 'R', 'M|E', 'WASTE_WATER_TREATMENT|ENVIRONMENTAL_ENGINEERING', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0064', 'c9173049-9daf-4670-bb1f-9bb23b2d918f', TRUE, 'EUR', '40', '8', '', 'DE Employer64', 'Address1', 'Address2', 'electrical eng.', '0', 'www.employer64.com',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'630', 'MONTHLY', 'IAESTE Stuttart', '220', 'MONTHLY', '26', '26', 'Stuttgart', 'Stuttgart', '2013-03-31',
'',
'630', 'MONTHLY', NULL,
'Subject of the internship is the sumulation and modeling of MEMS. Reduced-order models of MEMS inertial sensors are modeled with simulink. The ASIC that controls drive and sense of the sensor is modeled with an FPGA. Both models should be copled to allow for investigation of the interaction and full system simulation. Additionally the simuling model of the sensor should be transformed to VHDL-AMS to allow for a coupled simulation of sensor and ASIC within a VHDL-ASMS-Simulator. We are looking for a student with fundamental background in mathematics and physics. The student must have in depth knowledge of Matlab and Simulink. Preferalby the condidate should have knowledge of FPGA and of the programming language C.',
'Stuttgart', 'P', 'E', 'ELECTRICAL_ENGINEERING', 'micro systems eng.', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0065', '555fb10a-f2fb-4928-961f-8d186789aea6', TRUE, 'EUR', '38', '8', '', 'DE Employer65', 'Address1', 'Address2', 'National Railway', '230000', 'www.employer65.com',
'2013-05-01', '2013-03-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE Local committee', '220', 'MONTHLY', '12', '26', 'info later', '', '2013-04-15',
'excellent student required',
'650', 'MONTHLY', NULL,
'Students will be matched individually with projects, eg. high speed train maintenance yards, rail car and infrastructure technical components and simulations, control system technology, organization and sales of Europe-wide rail freight services, international strategy, rail network planning and maintenance, communication techniques',
'Berlin or Frankfurt or Hamburg or Munich or Essen', 'P', 'M', 'INDUSTRIAL_ENGINEERING', 'civil eng., mechanical eng., etc.', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0066', '7640a899-ac62-42ec-8e46-fd8f5ea715c7', TRUE, 'EUR', '38', '8', '', 'DE Employer66', 'Address1', 'Address2', 'National Railway', '230000', 'www.employer66.com',
'2013-05-01', '2013-03-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE Local committee', '220', 'MONTHLY', '12', '26', 'info later', '', '2013-04-15',
'excellent student required',
'650', 'MONTHLY', NULL,
'Students will be matched individually with projects, eg. high speed train maintenance yards, rail car and infrastructure technical components and simulations, control system technology, organization and sales of Europe-wide rail freight services, international strategy, rail network planning and maintenance, communication techniques',
'Berlin or Frankfurt or Hamburg or Munich or Essen', 'P', 'M', 'INDUSTRIAL_ENGINEERING', 'civil eng., mechanical eng., etc.', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0067', '69393706-5c8c-4a03-8615-58934340158c', TRUE, 'EUR', '38', '8', '', 'DE Employer67', 'Address1', 'Address2', 'National Railway', '230000', 'www.employer67.com',
'2013-05-01', '2013-03-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE Local committee', '220', 'MONTHLY', '12', '26', 'info later', '', '2013-04-15',
'excellent student required',
'650', 'MONTHLY', NULL,
'Students will be matched individually with projects, eg. high speed train maintenance yards, rail car and infrastructure technical components and simulations, control system technology, organization and sales of Europe-wide rail freight services, international strategy, rail network planning and maintenance, communication techniques',
'Berlin or Frankfurt or Hamburg or Munich or Essen', 'P', 'M', 'INDUSTRIAL_ENGINEERING', 'civil eng., mechanical eng., etc.', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0068', '1ac61d86-34a7-4317-bdf1-25fcf2dd790c', TRUE, 'EUR', '38', '8', '', 'DE Employer68', 'Address1', 'Address2', 'National Railway', '230000', 'www.employer68.com',
'2013-05-01', '2013-03-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE Local committee', '220', 'MONTHLY', '12', '26', 'info later', '', '2013-04-15',
'excellent student required',
'650', 'MONTHLY', NULL,
'Students will be matched individually with projects, eg. high speed train maintenance yards, rail car and infrastructure technical components and simulations, control system technology, organization and sales of Europe-wide rail freight services, international strategy, rail network planning and maintenance, communication techniques',
'Berlin or Frankfurt or Hamburg or Munich or Essen', 'P', 'M', 'INDUSTRIAL_ENGINEERING', 'civil eng., mechanical eng., etc.', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0069', 'aa0d3409-c13c-453e-b7e7-91ef24b23ac1', TRUE, 'EUR', '38', '8', '', 'DE Employer69', 'Address1', 'Address2', 'National Railway', '230000', 'www.employer69.com',
'2013-05-01', '2013-03-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE Local committee', '220', 'MONTHLY', '12', '26', 'info later', '', '2013-04-15',
'excellent student required',
'650', 'MONTHLY', NULL,
'Students will be matched individually with projects, eg. high speed train maintenance yards, rail car and infrastructure technical components and simulations, control system technology, organization and sales of Europe-wide rail freight services, international strategy, rail network planning and maintenance, communication techniques',
'Berlin or Frankfurt or Hamburg or Munich or Essen', 'P', 'M', 'INDUSTRIAL_ENGINEERING', 'civil eng., mechanical eng., etc.', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0070', '12f9bbcd-e220-464b-b235-c4e17c27e560', TRUE, 'EUR', '40', '8', '', 'DE Employer70', 'Address1', 'Address2', 'civil engineering', '40', 'www.employer70.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'400', 'MONTHLY', 'Employer', '0', 'MONTHLY', '12', '12', 'Frankfurt/M.', 'Rottweil', '2013-05-31',
'',
'450', 'MONTHLY', NULL,
'Project management',
'Rottweil', 'P', 'E', 'CIVIL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0071', '19307182-90fb-41a3-b64e-e74d7a7378c4', TRUE, 'EUR', '40', '8', '', 'DE Employer71', 'Address1', 'Address2', 'motorcycle helmets, ski goggles', '0', 'www.employer71.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'employer', '220', 'MONTHLY', '12', '24', 'Frankfurt', 'Chamerau', '2013-05-31',
'Master student required',
'650', 'MONTHLY', NULL,
'Uvex Sports proposes different developement projects concerning plastic materials having tailor-made properties suitable for ski-goggles, ski-helmets and motorcycle-helmets. 1. Optimization of the plasma process for an effective surface activation of plastic, 2. Development of new polymeric material for ski goggles meeting the uvex requirements, 3. development of water-based paints and coatings for goggles and helmets, 4. development of transparent and elastic adhesives for goggle lens laminates.',
'Chamerau', 'R', 'E', 'PLASTICS_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0072', '48ef13b9-e621-48c9-84f5-315eb9c506f1', TRUE, 'EUR', '40', '8', '', 'DE Employer72', 'Address1', 'Address2', 'motorcycle helmets, ski goggles', '0', 'www.employer72.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'employer', '220', 'MONTHLY', '12', '24', 'Frankfurt', 'Chamerau', '2013-05-31',
'Master student required',
'650', 'MONTHLY', NULL,
'Implementation of the measurement system wavefront-sensor, comparison of different measurement systems, measurement systems analysis',
'Chamerau', 'R', 'E', 'ENGINEERING_PHYSICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0073', 'e4b5e24a-def5-4e92-8c4c-f92e2ec07440', TRUE, 'EUR', '40', '8', '', 'DE Employer73', 'Address1', 'Address2', 'motorcycle helmets, ski goggles', '0', 'www.employer73.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'employer', '220', 'MONTHLY', '12', '24', 'Frankfurt', 'Chamerau', '2013-05-31',
'Master student required',
'650', 'MONTHLY', NULL,
'Creating a scenario for the topic head protection and new media, related to products (ski goggles, motorcycle helmets) customers and markets',
'Chamerau', 'R', 'E', 'INDUSTRIAL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0074', 'cc43a70c-b53a-4874-9af2-2607211259ed', TRUE, 'EUR', '40', '8', '', 'DE Employer74', 'Address1', 'Address2', 'university institute', '5', 'www.employer74.com',
'2013-01-01', '2013-06-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'Berlin', '', '2013-03-31',
'',
'650', 'MONTHLY', NULL,
'Research in the field of information systems, knowledge management, internet applications. Programming in  PHP, MySQL. Webdesign.',
'Berlin', 'R', 'M', 'ELECTRONIC_BUSINESS|PROCESS_SC.|ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0075', '24e41635-1764-47c8-bfb7-1ae9bf14d53a', TRUE, 'EUR', '40', '0', '', 'DE Employer75', 'Address1', 'Address2', 'university institute', '0', 'www.employer75.com',
'2013-03-09', '2013-06-26', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'0', 'MONTHLY', 'IAESTE-LC U Bremen', '240', 'MONTHLY', '8', '8', 'Bremen', '', '2013-01-31',
'',
'650', 'MONTHLY', NULL,
'(Further) development of, e.g. a connection / integration of the local university library system into our learning platform AULIS/ILIAS: fluent php (very important), exchange protocols',
'Bremen', 'R', 'M', 'IT', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0076', '1e6c2baf-e40a-4043-9ccf-a4cefa3fc593', TRUE, 'EUR', '40', '0', '', 'DE Employer76', 'Address1', 'Address2', 'university institute', '0', 'www.employer76.com',
'2013-03-09', '2013-06-26', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'0', 'MONTHLY', 'IAESTE-LC U Bremen', '240', 'MONTHLY', '8', '8', 'Bremen', '', '2013-01-31',
'Mathematics, MATLAB/Maple!',
'650', 'MONTHLY', NULL,
'(Further) development of a system to generate mathematical tests and to access learners answers (at least right/wrong) using a learning platform (ILIAS) and a computer algebra-system (Matlab/Maple) connected by JAVA',
'Bremen', 'R', 'M', 'MATHEMATICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0077', 'b60dbf0f-a23f-46b8-a029-f05df824660e', TRUE, 'EUR', '40', '8', NULL, 'DE Employer77', 'Address1', 'Address2', NULL, '100', 'www.employer77.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'Stuttgart', NULL, '2013-03-31',
'if possible please apply for full months',
'650', 'MONTHLY', NULL,
'Work group "Particle-Liquid-Systems": solid-liquid-separation; Agglomeration, Dispersion; Mixing; Nanoparticle-Systems; experimental work in the bench-and-pilot-scale.',
'Karlsruhe', 'R', 'M|E', 'PROCESS_ENGINEERING', 'chemical engineering', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0078', 'af377b82-930f-4d88-859e-90dccc21388e', TRUE, 'EUR', '37.5', '8', NULL, 'DE Employer78', 'Address1', 'Address2', 'chemical industry', '33000', 'www.employer78.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '24', 'Frankfurt a. M.', NULL, '2013-03-31',
'if possible please apply for full months',
'800', 'MONTHLY', NULL,
'chemical engineering, literature research; work in laboratory; pilot plant; process simulation; presentation of results; writing reports about the work done; planning and doing of experiments in pilot plant.',
'Ludwigshafen', 'P', 'E', 'CHEMISTRY', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0079', '2d7a7432-4b12-4fab-ae11-4e18d6195db6', FALSE, 'EUR', '40', '8', NULL, 'DE Employer79', 'Address1', 'Address2', NULL, '12', 'www.employer79.com',
'2013-01-01', '2013-09-01', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, 'ENGLISH', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '26', 'Stuttgart', NULL, '2013-03-31',
'if possible please apply for full months',
'700', 'MONTHLY', NULL,
'back office and field work in geology and geophysics, reservoir, engineering and energy technology',
'Karlsruhe', 'R', 'E', 'GEOSCIENCE', 'Geophysics, Geology', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0080', 'ff5e0501-8e10-494b-bd63-73665683cf0b', TRUE, 'EUR', '40', '8', NULL, 'DE Employer80', 'Address1', 'Address2', NULL, '120', 'www.employer80.com',
'2013-01-01', '2013-06-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'Hamburg, Berlin', NULL, '2013-03-31',
'If possible please apply for full months.\r\n\r\neither January-June or August to November!!!',
'650', 'MONTHLY', NULL,
'Calculation of dynamics of biomolecules on titanium dioxides surfaces on high performance computers; Experimental studies on surfaces in vacuum laboratory: Mass spectroscopy. Auger-spectroscopy and quartz crystal microbalance on titanium surfaces: Analysis of data from molecular dynamics simulation (Computer work, use of program packages and eventually script programming); Practical work in vacuum lab, assistance during experiments with vacuum and electronic equipment',
'Greifswald', 'R', 'E', 'PHYSICAL_CHEMISTRY|COMPUTATIONAL_CHEMISTRY', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0081', '377c81fa-473a-4ad7-ae08-2ac6981cf817', TRUE, 'EUR', '40', '8', NULL, 'DE Employer81', 'Address1', 'Address2', NULL, '950', 'www.employer81.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', 'info later', NULL, '2013-03-31',
'if possible please apply for full months. G knowledge in AUTOCAD and MS-Office, interest in general technical planning, very good knowledge in GERMAN',
'800', 'MONTHLY', NULL,
'participation in projects in the field of general technical planning, energy concepts and economy of energy',
'info later', 'P', 'M|E', 'ELECTRICAL_ENGINEERING|ENVIRONMENTAL_ENGINEERING|FACILITY_MANAGEMENT', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0082', '834b410f-c787-4812-a22d-66be4926305b', TRUE, 'EUR', '40', '8', 'please refer to webs', 'DE Employer82', 'Address1', 'Address2', 'Real estate and construction', '950', 'www.employer82.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', NULL, NULL, '2013-03-31',
'If possible please apply for full months.\r\nG knowledge in AUTOCAD and MS-Office\r\nInterest in general technical planning\r\nvery good knowledge in GERMAN',
'800', 'MONTHLY', NULL,
'Participation in projects in the field of general technical planning, energy conecpts and economy of energy.',
'info later, after acceptance workplace will be in', 'P', 'M|E', 'ELECTRICAL_ENGINEERING|ENVIRONMENTAL_ENGINEERING|FACILITY_MANAGEMENT', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0083', '63987eb9-b90f-4241-b209-cf54767abd35', TRUE, 'EUR', '40', '8', 'please refer to webs', 'DE Employer83', 'Address1', 'Address2', 'Real estate and construction', '950', 'www.employer83.com',
'2013-01-24', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', NULL, NULL, '2013-03-31',
'If possible please apply for full months. G knowledge in AUTOCAD and MS-Office Interest in general technical planning very good knowledge in GERMAN',
'800', 'MONTHLY', NULL,
'Participation in projects in the field of general technical planning, energy conecpts and economy of energy.',
'info later, after acceptance workplace will be in', 'P', 'M|E', 'ELECTRICAL_ENGINEERING|ENVIRONMENTAL_ENGINEERING|FACILITY_MANAGEMENT', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0084', '9769cff6-3809-4180-9e2f-4114b702cc05', FALSE, 'EUR', '40', '8', 'Please refer to our ', 'DE Employer84', 'Address1', 'Address2', 'Real estate and construction', '950', 'www.employer84.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'LC', '240', 'MONTHLY', '12', '52', 'info later', 'info later', '2013-03-31',
'If possible please apply for full months: Practical experience desired.',
'650', 'MONTHLY', NULL,
'Participation in projects in the realm of project management, real estate consulting services and engineering.',
'info later', 'P', 'M', 'CIVIL_ENGINEERING', 'architecture, industrial eng., management', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0085', 'f6cfc004-9d1a-4975-86eb-14ae94d8f22c', FALSE, 'EUR', '40', '8', 'Please refer to our ', 'DE Employer85', 'Address1', 'Address2', 'Real estate and construction', '950', 'www.employer85.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'LC', '240', 'MONTHLY', '12', '52', 'info later', 'info later', '2013-03-31',
'If possible please apply for full months. Practical experience desired.',
'650', 'MONTHLY', NULL,
'Participation in projects in the realm of project management, real estate consulting services and engineering.',
'info later', 'P', 'M', 'CIVIL_ENGINEERING', 'architecture, industrial eng., management', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0086', 'e6b3cbfd-05a8-4cde-9379-5914987173c5', FALSE, 'EUR', '40', '8', 'Please refer to our ', 'DE Employer86', 'Address1', 'Address2', 'Real estate and construction', '950', 'www.employer86.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'LC', '240', 'MONTHLY', '12', '52', 'info later', 'info later', '2013-03-31',
'If possible please apply for full months. Practical experience desired.',
'650', 'MONTHLY', NULL,
'Participation in projects in the realm of project management, real estate consulting services and engineering.',
'info later', 'P', 'M', 'CIVIL_ENGINEERING', 'architecture, industrial eng., management', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0087', 'ef4af672-03a6-472d-b2b5-76811516a23f', FALSE, 'EUR', '40', '8', 'Please refer to our ', 'DE Employer87', 'Address1', 'Address2', 'Real estate and construction', '950', 'www.employer87.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'LC', '240', 'MONTHLY', '12', '52', 'info later', 'info later', '2013-03-31',
'If possible please apply for full months. Practical experience desired.',
'650', 'MONTHLY', NULL,
'Participation in projects in the realm of project management, real estate consulting services and engineering.',
'info later', 'P', 'M', 'CIVIL_ENGINEERING', 'architecture, industrial eng., management', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0088', 'ebfe4128-9c66-4883-9448-3a374c122d9e', TRUE, 'EUR', '40', '8', '', 'DE Employer88', 'Address1', 'Address2', 'Research Institute', '4300', 'www.employer88.com',
'2013-01-01', '2013-06-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'Reception Committee', '240', 'MONTHLY', '12', '12', 'Düsseldorf', 'Jülich', '2013-01-31',
'student must agree to work with 14C labeled chemicals. Practical Lab experience required, independent working behaviour, ability to communicate',
'650', 'MONTHLY', NULL,
'Trainee will be involved in the ongoing research on pesticides  behaviour in soils. Sorption/desproption processes and extraction of aged 14C labeled xenobiotics, microbial access and mineralization of these compounds, physio-chemical analysis such as LC-MSMS, HPLC, Oxidzer, LSC, etc. will be major subjects of investigation.',
'Jülich', 'R', 'M', 'ENVIRONMENTAL_SCIENCE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0089', '9237e59a-fc47-40f7-a968-6d6b2babb8be', TRUE, 'EUR', '40', '8', '0', 'DE Employer89', 'Address1', 'Address2', 'National Metrology Institute', '30', 'www.employer89.com',
'2013-02-01', '2013-01-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'F', NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '300', 'MONTHLY', '8', '8', 'Bremen', 'Braunschweig', '2013-01-31',
'',
'650', 'MONTHLY', NULL,
'You will work on a new laser spectrometer (the measuring principle is TDLAS, Tuneable Diode Laser Absorption Spectroscopy) for the analysis of pressurized process gases to be used and applied to high pressure biomass gasification. For this purpose, a number of tasks has to be accomplished. Main task: to perform spectroscopic high pressure measurements and to improve the individual spectrometer components.',
'Braunschweig', 'R', 'M', 'PHYSICS|LASER_TECHNOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0090', '7737ea20-3138-43bf-b9ed-1e9439a85bed', TRUE, 'EUR', '40', '8', '0', 'DE Employer90', 'Address1', 'Address2', 'National Metrology Institute', '30', 'www.employer90.com',
'2013-01-01', '2013-01-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'O', 'GERMAN', 'G', NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '300', 'MONTHLY', '8', '8', 'Bremen', 'Braunschweig', '2013-01-31',
'',
'650', 'MONTHLY', NULL,
'You will work on a new laser spectrometer (the measuring principle is TDLAS, Tuneable Diode Laser Absorption Spectroscopy) for the analysis of pressurized process gases to be used and applied to high pressure biomass gasification.',
'Braunschweig', 'R', 'M', 'PHYSICS|LASER_TECHNOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0091', '4d2a968c-f2ac-4881-ae54-3841040a223c', TRUE, 'EUR', '39', '8', '0', 'DE Employer91', 'Address1', 'Address2', 'university institute', '10', 'www.employer91.com',
'2013-04-01', '2013-05-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'München', 'Freising', '2013-01-01',
'good physical conditions (intensive field work/measuremente) balance between field / computer work',
'650', 'MONTHLY', NULL,
'Integration in a research project on Carbon storage of wetland ecosystems. Sampling of CO2-exchange via closed-chamber technique on different vegetation types along a degradation-restoration gradient, biomass sampling, determination of LAI, data-processing, parametization of CO2-exchange models, modelling of gas-exchange balances for part of the season, literature review in relevant fields.',
'Freising', 'P', 'B', 'BIOLOGY|ECOLOGY|GEOGRAPHY|RESSOURCE_MANAGEMENT|AGRICULTURE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0092', '1d544df9-4334-4edd-97e2-2416d2d33b99', TRUE, 'EUR', '39', '8', '0', 'DE Employer92', 'Address1', 'Address2', 'university institute', '10', 'www.employer92.com',
'2013-04-01', '2013-05-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'München', 'Freising', '2013-01-01',
'good physical conditions (intensive field work/measuremente) balance between field / computer work',
'650', 'MONTHLY', NULL,
'Integration in a research project on Carbon storage of wetland ecosystems. Sampling of CO2-exchange via closed-chamber technique on different vegetation types along a degradation-restoration gradient, biomass sampling, determination of LAI, data-processing, parametization of CO2-exchange models, modelling of gas-exchange balances for part of the season, literature review in relevant fields.',
'Freising', 'P', 'B', 'BIOLOGY|ECOLOGY|GEOGRAPHY|RESSOURCE_MANAGEMENT|AGRICULTURE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0093', '37e8cf0a-53be-4983-bd73-78fd4994a2c7', TRUE, 'EUR', '40', '8', '0', 'DE Employer93', 'Address1', 'Address2', 'research institute', '0', 'www.employer93.com',
'2013-01-01', '2013-06-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '12', 'Köln/Bonn', 'Jülich', '2013-12-31',
'Applicant must agree to work with 14C-labeled chemicals/under control area conditions; practical lab-experiences are strongly recommended; independent work behaviour, open and interested mind and the ability to communicate are required.',
'650', 'MONTHLY', NULL,
'The trainee will be involved in the ongoing research on pesticides behaviour in soils. Sorption/desorption processes and extraction of aged 14C-labeled xenobiotics, microbial access and mineralization of these compounds, physico-chemical analysis such as',
'Jülich', 'R', 'E', 'ENVIRONMENTAL_SC.|CHEMISTRY|BIOLOGY|AGRICULTURAL_SC.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0094', '656dd33a-928a-48a0-94b7-f39989014728', FALSE, 'EUR', '40', '88', 'please refer to webs', 'DE Employer94', 'Address1', 'Address2', 'engineering office', '160', 'www.employer94.com',
'2013-01-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '24', 'info later', 'info later', '2013-03-31',
'If possible please apply for full months. Knowledge in CATIA V4/V5 Robcad or eM-plaber/process designer',
'650', 'MONTHLY', NULL,
'CAD-Construction, planning, robot simulation (automotion and manufactoring technique). Software: CATIA V4/V5, eM-Planner/ Process Designer, Robcad /Process Simulate',
'Hannover or Wolfsburg or Bremen', 'R', 'M|E', 'MECHANICAL_ENGINEERING|MECHANOTRONICS', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0095', '57ae5c74-40ba-4df7-bc54-3b4f86f59abe', FALSE, 'EUR', '40', '8', 'please refer to webs', 'DE Employer95', 'Address1', 'Address2', 'project development', '12', 'www.employer95.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '240', 'MONTHLY', '8', '26', 'Stuttgart', 'Karlsruhe', '2013-03-31',
'If possible please apply for full month.\r\n\r\nlanguages desired: ENGLISH good',
'700', 'MONTHLY', NULL,
'back office and field work in geology and geophysics, reservoir engineering and energy technology',
'Karlsruhe', 'R', 'E', 'GEOLOGY|GEOPHYSICS|GEOLOGY|ENERGY-_AND_ENVIRONMENTAL_ENGINEERING|CIVIL_ENGINEERING|MINING_ENGIN', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0096', '945a2ebe-df1d-4ea4-abfa-9d59ec42844c', TRUE, 'EUR', '40', '8', '0', 'DE Employer96', 'Address1', 'Address2', 'planning office', '170', 'www.employer96.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', 'Köln/Bonn', 'Köln Hauptbahnhof', '2013-03-31',
'Practical experience is not a must but is desired. Handling with CAD Software is required.+\r\n\r\nlanguage desired:\r\nFRENCH good',
'800', 'MONTHLY', NULL,
'- Detail design and technical dimensioning of building services installations in the fiels of sanitary engineering, heating, air cooling, ventialtion, fire protection, electrical and communications engineering.\r\n- development of technical concelts for heating, air conditioning and ventilation systems\r\n- development of concepts on indoor climate with compartision of different variants.\r\n- development of concepts on electrical and communications installations\r\n- analysis of profitability, eco-efficientcy and user comfort for teh developed concepts\r\n- presentation of the concepts in co-operation with the project engineer in charge\r\n- attend coordination meetings with architect and building owners\r\n- market analysis for technical products\r\n- participate in the development of bill of quanities and bid proposal',
'Köln', 'P', 'M', 'BUILDING_SERVICES_ENG.|ELECTRICAL_ENG.|COMMUNICATIONS_ENG.', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0097', '38d73511-3fb2-4dd6-98d0-8ba7bdca53f6', TRUE, 'EUR', '38', '7.5', '0', 'DE Employer97', 'Address1', 'Address2', 'university institute', '0', 'www.employer97.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '200', 'MONTHLY', '12', '12', 'Dresden', 'Freiberg', '2013-03-31',
'if possible please apply for full months. ',
'650', 'MONTHLY', NULL,
'The trainee will be integrated in one of the projects concerning mountain building and its interaction with climate and erosion. The candidate should be motivated to learn new methods and be flexible. Team work is fundamental. The main project concerns remote sensoring and modelling.',
'Freiberg', 'R', 'E', 'GEOSCIEREMOTE_SENSING|GEOMODELLING|TECTONICS|GEOMORPHOLOGY', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0098', '597d2f3b-3c8b-4386-a4d6-1964072bbde5', TRUE, 'EUR', '38', '8', 'please refer to webs', 'DE Employer98', 'Address1', 'Address2', 'plastics industry', '330', 'www.employer98.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', 'Dortmund', 'Kaarst', '2013-03-31',
'if possible pleas apply for full month.',
'713', 'MONTHLY', NULL,
'Projects in the field of research and development, process eng., product and application development.',
'Willich', 'R', 'M', 'PLASTICS_ENGINEERING|PROCESS_ENG.|PRODUCT_MANAGEMENT|QUALITY_CONTROL', NULL, '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0099', 'd0bd3151-f800-41c5-9c76-20491716c90a', TRUE, 'EUR', '40', '8', '0', 'DE Employer99', 'Address1', 'Address2', 'university institute', '0', 'www.employer99.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'Employer', '240', 'MONTHLY', '12', '12', 'Dresden', 'Magdeburg', '2013-03-31',
'Programming skills required.',
'650', 'MONTHLY', NULL,
'Usage of the Discrete Element Method in the a.m. field.',
'Magdeburg', 'R', 'M', 'MATERIALS_HANDLING_ENG.|PROCESS_ENG.|CHEMICAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0100', 'b7f25db2-fda1-44d3-85ca-a772c7f01fb8', TRUE, 'EUR', '40', '8', '0', 'DE Employer100', 'Address1', 'Address2', 'telecommunication', '70', 'www.employer100.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '0', 'MONTHLY', '12', '26', 'info later', 'info later', '2013-03-31',
'Knowledge of MS-Office. Employer will provide free accomodation.',
'650', 'MONTHLY', NULL,
'We are the market research department of T-Home, the devision of Deutsche Telekom for private customers and broad band solutions. We provide our marketing with field and desk research regarding to new products, the success of advertising or customers',
'Bonn or Darmstadt', 'P', 'M', 'MARKET_RESEARCH', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0101', '2d72b6df-dfda-46b8-bf1a-915ee459889d', TRUE, 'EUR', '40', '8', '0', 'DE Employer101', 'Address1', 'Address2', 'telecommunication', '70', 'www.employer101.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '0', 'MONTHLY', '12', '26', 'info later', 'info later', '2013-03-31',
'Knowledge of MS-Office. Employer will provide free accomodation.',
'650', 'MONTHLY', NULL,
'We are the market research department of T-Home, the devision of Deutsche Telekom for private customers and broad band solutions. We provide our marketing with field and desk research regarding to new products, the success of advertising or customers',
'Bonn or Darmstadt', 'P', 'M', 'MARKET_RESEARCH', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0102', '00842646-9300-4f48-bcdb-bde061b61fdf', TRUE, 'EUR', '40', '8', '0', 'DE Employer102', 'Address1', 'Address2', 'engineering office', '114', 'www.employer102.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '24', 'M&uuml;nchen', 'N&uuml;rnberg', '2013-03-31',
'Previous practical training is an advantage.',
'650', 'MONTHLY', NULL,
'Developing energy technological concepts. Planning of building services engineering. Calculation and detailing of technical systems.',
'N&uuml;rnberg', 'P', 'E', 'ENERGY_TECHNOLOGY|CLIMATE_TECHNOLOGY|ELECTRICAL_ENERGY_TECHNOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0103', 'b14e2ebc-d49f-42c0-9569-4f77cbdd68b6', TRUE, 'EUR', '40', '8', '0', 'DE Employer103', 'Address1', 'Address2', 'survey office', '35', 'www.employer103.com',
'2013-05-01', '2013-11-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '0', 'MONTHLY', '12', '12', 'Leipzig', 'Chemnitz', '2013-03-31',
'Free accomodation',
'650', 'MONTHLY', NULL,
'surveying external work: handling with geodetic measuring instruments; surveying indoor service: analysis with CAD programms',
'Chemnitz', 'R', 'B', 'GEODESY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0104', 'a330759c-87e3-4471-926f-cd3f7eb8a05a', TRUE, 'EUR', '40', '8', '0', 'DE Employer104', 'Address1', 'Address2', 'Manufacturer of semi-finished and special products', '6', 'www.employer104.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '240', 'MONTHLY', '8', '26', 'Stuttgart', 'Ulm', '2013-03-31',
'',
'650', 'MONTHLY', NULL,
'According to students qualifications',
'Ulm/V&ouml;hringen', 'P', 'M', 'ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0105', '77dd7fbe-fb8e-4fef-bf4f-8d6d1147c011', TRUE, 'EUR', '40', '8', '0', 'DE Employer105', 'Address1', 'Address2', 'architecture office', '45', 'www.employer105.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', 'A', 'GERMAN', 'F', NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '0', 'MONTHLY', '8', '24', 'Hamburg', 'Hamburg', '2013-03-31',
'',
'700', 'MONTHLY', NULL,
'Support and assistance of engineers for planning and design and inspection of bridges',
'Hamburg', 'P', 'E', 'CIVIL_ENGINEERING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0106', '45108a2b-4c84-4717-9351-06db0a2b76f9', TRUE, 'EUR', '40', '8', '0', 'DE Employer106', 'Address1', 'Address2', 'software development', '65', 'www.employer106.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '26', '26', 'M&uuml;nchen', 'Vierkirchen', '2013-03-31',
'You can choose between to different sections: automation &amp; simulation / telecommunication &amp; netmanagement.',
'800', 'MONTHLY', NULL,
'The sector Automation &amp;amp; Simulation offers development services &amp;amp; systems to manufacturers/sub-conctractors in the industries automotive, railway, research &amp;amp; science as well as aerospace. We provide the complete hard-/software-equipment for the automated test of technical applications and/or of systems controlled electronically: Hardware-in-the-Loop simulation &amp;amp; testautomation. / The sector Telecommunication &amp;amp; Network Management provides our customer with high-quality development services &amp;amp; complete solutions for activation, operation, monitoring of telecommunication networks. Our gamut of services in this field include: E-to-E solutions, integration services &amp;amp; enterprise solutions.',
'Vierkirchen', 'R', 'M', 'ELECTRICAL_ENG.|INFORMATION_TECHNOLOGY|INFORMATICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0107', '0ee3a2c1-4502-4593-8e76-a493ca51c753', TRUE, 'EUR', '40', '8', '0', 'DE Employer107', 'Address1', 'Address2', 'research institute', '270', 'www.employer107.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '24', 'Munich or Stuttgart', 'Freiburg', '2013-03-31',
'Skills in either one or more following fields are required: FPGA development (VHDL/Verilog), &micro;C applications, C/C++ programming, PCB circuit design and layout, digital signal processing.',
'700', 'MONTHLY', NULL,
'Development of sub-systems for Pico/nano satellites (hardware and/or software): Power management system design. Developing and implementation of radiation tolerant main bus systems. Digital signal processing algorithms for data acquisition (sensor signals, image processing camera control). High speed communication sub-system design and implementation. Protocols and software for high-speed communication.',
'Freiburg', 'R', 'M', 'ELECTRICAL_ENG.|COMPUTER_ENG.|SOFTWARE_ENG._/_IT|COMPUTER_SC.|TELECOMMUNICATIONS|COMMUNICATIONS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0108', '0bcc7dbd-0767-4353-8588-a4512ee4f05a', TRUE, 'EUR', '40', '8', '0', 'DE Employer108', 'Address1', 'Address2', 'engineer service supplier', '8', 'www.employer108.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '48', 'Frankfurt', 'Kassel', '2013-03-31',
'MS Office.',
'700', 'MONTHLY', NULL,
'Active participation in current industrial and research projects in the areas of energy efficiency in production, energy monotoring, energy and material flow simulation. Regular visits to current customers offer insights into varous production plants.',
'Kassel', 'P', 'E', 'MECHANICAL_ENG.|IT|ENERGY_TECHNOLOGY|ELECTRICAL_AND_ELECTRONICAL_ENG.|PROCESS_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0109', '523d5da7-b420-489f-8fac-146bcd90aa5b', TRUE, 'EUR', '40', '8', '0', 'DE Employer109', 'Address1', 'Address2', 'sales and marketing', '107', 'www.employer109.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '52', 'M&uuml;nchen', 'Freising', '2013-03-31',
'Deep knowledge in object oriented programming, ideally in Python. G understanding of low-level (close to hardware) software development, ideally good understanding of JTAG/boundaary scan. Self-starter who feels comfortable with challenges.',
'800', 'MONTHLY', NULL,
'Gleichmann offers ASIC/FPGA develepment platforms and tools (www.ge-research.com). For instance a JTAG/boundary scan tool (www.hpe-jtag.com) allowing developers to setting up PCB prototypes or debugging Ics. These tools can be extended through',
'Freising', 'P', 'M', 'COMPUTER_SC.|ELECTRICAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0110', 'ddb43f5f-9a91-433c-8d44-2d5e846fb416', TRUE, 'EUR', '40', '8', '0', 'DE Employer110', 'Address1', 'Address2', 'sales and marketing', '107', 'www.employer110.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '52', 'M&uuml;nchen', 'Freising', '2013-03-31',
'Deep knowledge in object oriented programming, ideally in Python. G understanding of low-level (close to hardware) software development, ideally good understanding of JTAG/boundaary scan. Self-starter who feels comfortable with challenges.',
'800', 'MONTHLY', NULL,
'Gleichmann offers ASIC/FPGA develepment platforms and tools (www.ge-research.com). For instance a JTAG/boundary scan tool (www.hpe-jtag.com) allowing developers to setting up PCB prototypes or debugging Ics. These tools can be extended through',
'Freising', 'P', 'M', 'COMPUTER_SC.|ELECTRICAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0111', 'c4316ef9-874a-470f-a431-29e14fbe6355', TRUE, 'EUR', '40', '8', '0', 'DE Employer111', 'Address1', 'Address2', 'steal construction', '200', 'www.employer111.com',
'2013-05-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'Employer', '200', 'MONTHLY', '24', '32', 'Dresden', 'Plauen', '2013-03-31',
'The student should be interested in both design and analysis and realisation (manufacturing and assembly) of bigger constructions for bridges (highways, railways).',
'650', 'MONTHLY', NULL,
'Participance in the phase of design and of determination of structural analysis for a big bridge construction for a Swedish highway project including manufacturing and assembly studies within our Bridge Construction  Division.',
'Plauen', 'P', 'M', 'STRUCTURAL_ENG.|INFRASTRUCTURE_ENG.|CIVIL_ENG.|ARCHITECTURE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0112', '984b481b-38db-4cc1-a755-7eb236052f0f', TRUE, 'EUR', '40', '8', '0', 'DE Employer112', 'Address1', 'Address2', 'automobile company', '0', 'www.employer112.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', 'M&uuml;nchen', 'Stuttgart', '2013-03-31',
'',
'700', 'MONTHLY', NULL,
'Practical training in the fields of vehicle eng., aeronautical eng., mechanical eng., process eng., industrial eng.',
'Stuttgart', 'P', 'M', 'VEHICLE_ENG.|AERONAUTICAL_ENG.|MECHANICAL_ENG.|PROCESS_ENG.|INDUSTRIAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0113', '4ea5cc8d-56e8-4754-be5a-f69c3b70dce3', TRUE, 'EUR', '40', '8', '0', 'DE Employer113', 'Address1', 'Address2', 'Software development', '15', 'www.employer113.com',
'2013-07-01', '2013-04-30', NULL, NULL, NULL, NULL, 'GERMAN', 'F', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', 'Hamburg', 'L&uuml;neburg', '2013-07-31',
'',
'650', 'MONTHLY', NULL,
'Forecast Developement. Task will include the analysis of real economic and financial time data as well as their prediction. Skills needed in at least one of the following topics: mathematics, mathematical economics, staticstics or business informatics. Student must be able to build computerized implementations.',
'L&uuml;neburg', 'R', 'M', 'IT|MATHEMATICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0114', 'b77b303d-77b4-4179-9b25-ab21b4d19705', TRUE, 'EUR', '40', '8', '0', 'DE Employer114', 'Address1', 'Address2', 'Software development', '15', 'www.employer114.com',
'2013-07-01', '2013-04-30', NULL, NULL, NULL, NULL, 'GERMAN', 'F', 'A', 'ENGLISH', 'F', NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', 'Hamburg', 'L&uuml;neburg', '2013-07-31',
'',
'650', 'MONTHLY', NULL,
'Java-Software development. Skills required: SQL (MySQL), JDBC, javascript, HTML, OR: struts, webservices, tomcat; good skills in Java 5 or 6. Company works with eclipse, SVN; student should be able to help increase the quality of companys data',
'L&uuml;neburg', 'R', 'M', 'IT|MATHEMATICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0115', '3ecd3158-b110-4ea6-ba03-e6b14f615c3e', TRUE, 'EUR', '40', '8', '0', 'DE Employer115', 'Address1', 'Address2', 'Software development', '15', 'www.employer115.com',
'2013-07-01', '2013-04-30', NULL, NULL, NULL, NULL, 'GERMAN', 'F', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '24', '24', 'Hamburg', 'L&uuml;neburg', '2013-07-31',
'',
'650', 'MONTHLY', NULL,
'SQL-Database development. Skills required in SQL (MySQLwould be beneficial). Skills in Java 5 or 6 as well as in JDBC advantageous. A good linguistic proficiency in ENGLISH and perhaps even in GERMAN is neccessary due to companys international team. Student should be highly motivated and ambitional for new technology. Basic skills in PORTUGUESE, SPANISH, FRENCH or ITALIAN are advantageous. Company works with eclipse, SVN.',
'L&uuml;neburg', 'R', 'M', 'IT|MATHEMATICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0116', '8f95405e-53a6-4706-a434-b536a95bbbf0', TRUE, 'EUR', '39', '8', '0', 'DE Employer116', 'Address1', 'Address2', 'university institute', '10', 'www.employer116.com',
'2013-01-01', '2013-01-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'M&uuml;nchen', 'Freising', '2013-01-01',
'good physical conditions (intensive field work/measuremente) balance between field / computer work',
'650', 'MONTHLY', NULL,
'Integration in a research project on Carbon storage of wetland ecosystems. Sampling of CO2-exchange via closed-chamber technique on different vegetation types along a degradation-restoration gradient, biomass sampling, determination of LAI, data-processing, parametization of CO2-exchange models, modelling of gas-exchange balances for part of the season, literature review in relevant fields.',
'Freising', 'P', 'B', 'BIOLOGY|ECOLOGY|GEOGRAPHY|RESSOURCE_MANAGEMENT|AGRICULTURE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0117', '97e4c158-16fb-4e28-99ee-0df1a8ba8574', TRUE, 'EUR', '40', '8', '0', 'DE Employer117', 'Address1', 'Address2', 'Zoo', '0', 'www.employer117.com',
'2013-04-01', '2013-02-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '12', 'M&uuml;nchen', 'N&uuml;rnberg/Erlangen', '2013-03-31',
'Computer knowledge and GERMAN are helpful.',
'650', 'MONTHLY', NULL,
'Our research group works with national parks and zoos in different areas. Work in our group concerns behaviour, management and ecology of endangered mammals both in- and ex situ, i.e. captive breeding and field work. Students will work in ethological data collection and analysis, animal husbandry, studbook management, literature research etc. Most works concerns marsupials, ungulates and some carnivores.',
'Zoo in N&uuml;rnberg', 'R', 'B', 'ZOOLOGY|WILDLIFE_MANAGEMENT|VETERINARY_AGRICULTURE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0118', 'bb677dde-e463-4df9-958d-1743583144b6', TRUE, 'EUR', '40', '8', '0', 'DE Employer118', 'Address1', 'Address2', 'Zoo', '0', 'www.employer118.com',
'2013-04-01', '2013-02-28', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '12', 'M&uuml;nchen', 'N&uuml;rnberg/Erlangen', '2013-03-31',
'Computer knowledge and GERMAN are helpful.',
'650', 'MONTHLY', NULL,
'Our research group works with national parks and zoos in different areas. Work in our group concerns behaviour, management and ecology of endangered mammals both in- and ex situ, i.e. captive breeding and field work. Students will work in ethological data collection and analysis, animal husbandry, studbook management, literature research etc. Most works concerns marsupials, ungulates and some carnivores.',
'Zoo in N&uuml;rnberg', 'R', 'B', 'ZOOLOGY|WILDLIFE_MANAGEMENT|VETERINARY_AGRICULTURE', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0119', '29b89b20-2f0b-4fbd-b152-d754bd0b306f', TRUE, 'EUR', '40', '8', '0', 'DE Employer119', 'Address1', 'Address2', 'university institute', '60', 'www.employer119.com',
'2013-04-01', '2013-01-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'Hannover', 'Clausthal-Zellerfeld', '2013-03-31',
'No beginning in Aug.',
'650', 'MONTHLY', NULL,
'Laser spectroscopy, laser physics, development of fiber-optic sensor concepts, nano technology for photonic applications',
'Goslar', 'R', 'E', 'PHYSICS', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0120', 'a465907d-2fd3-481b-81d0-028bb71f43c4', TRUE, 'EUR', '39', '8', '0', 'DE Employer120', 'Address1', 'Address2', 'research institute', '610', 'www.employer120.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '8', '8', 'K&ouml;ln/Bonn', 'Bonn-Mehlem', '2013-03-31',
'knowledges in programming',
'650', 'MONTHLY', NULL,
'Software design and implementation: computer networks, wireless communications, C,C++ Linux, ns-2, matlab.',
'Wachtberg', 'P', 'M', 'COMPUTER_SC.|IT_SECURITY|EE_(COMMUNICATIONS)', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0121', '1259f1b2-4306-4f61-a7d9-0c526dc4db7c', TRUE, 'EUR', '40', '8', '0', 'DE Employer121', 'Address1', 'Address2', 'polytechnic institute', '0', 'www.employer121.com',
'2013-09-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '12', 'Hamburg', 'Heide', '2013-03-31',
'The Student should have finished his/her basic study in electrical eng. and should have some practical programming experience with the the language C and/or VHDL. Experiences in microcontrol, FPGA and DSP are desired',
'650', 'MONTHLY', NULL,
'We are looking for a student with interest for the digital image processing. Especially the hardware algorithm design is the major topic of our research. We are developing 2D/3D optical inspection systems for quality control applications in the industry. These systems have to come with a low price tag and hence we are building specially designed hardware solutions for the customers. Standard machine vision algorithm cannot easily be executed on these hardware and have to be modified before they can run on these systems. Hence we are looking for a student with experience in microcontroller, DSP and FPGA programming. Furthermore it would be beneficial to have experience with layout systems and modelling tools such as PSPICE and Modelsim.',
'Heide', 'P', 'E', 'ELECTRICAL_ENG._(MICROCONTROLPROGRAMMING;_DSP_AND_FPGA_PROGRAMMING)', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0122', '1949de94-4b1c-430e-a1e2-bbcfe66d302d', TRUE, 'EUR', '40', '8', '0', 'DE Employer122', 'Address1', 'Address2', 'university institute', '0', 'www.employer122.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '26', '26', 'M&uuml;nchen', 'Garching-Forschungszentrum', '2013-03-31',
'',
'650', 'MONTHLY', NULL,
'Combustion in gas turbines; IC engines, two phase flows, heat and mass transfer.Activities for students: Experimental investigation by means of optical (LIF, chemiluminescence, PIV, holography etc.,) numerical simulation,',
'Garching', 'R', 'E', 'MECHANICAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0123', '56f06c77-985a-4cd7-8d6b-7863e5c21b86', TRUE, 'EUR', '40', '8', '0', 'DE Employer123', 'Address1', 'Address2', 'university institute', '0', 'www.employer123.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '26', '26', 'M&uuml;nchen', 'Garching-Forschungszentrum', '2013-03-31',
'',
'650', 'MONTHLY', NULL,
'Combustion in gas turbines; IC engines, two phase flows, heat and mass transfer.Activities for students: Experimental investigation by means of optical (LIF, chemiluminescence, PIV, holography etc.,) numerical simulation,',
'Garching', 'R', 'E', 'MECHANICAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0124', '9466e733-d459-4838-bb14-f9d2396b0b4e', TRUE, 'EUR', '40', '8', '0', 'DE Employer124', 'Address1', 'Address2', 'university institute', '0', 'www.employer124.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '26', '26', 'M&uuml;nchen', 'Garching-Forschungszentrum', '2013-03-31',
'',
'650', 'MONTHLY', NULL,
'Combustion in gas turbines; IC engines, two phase flows, heat and mass transfer.Activities for students: Experimental investigation by means of optical (LIF, chemiluminescence, PIV, holography etc.,) numerical simulation,',
'Garching', 'R', 'E', 'MECHANICAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0125', '09bec9e8-65b6-4238-a947-32f240257830', TRUE, 'EUR', '39', '8', '0', 'DE Employer125', 'Address1', 'Address2', 'university institute', '200', 'www.employer125.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '12', 'K&ouml;ln/Bonnn', 'Aachen-West', '2013-03-31',
'',
'650', 'MONTHLY', NULL,
'The trainee will participate in ongoing research projects. This can either involve experimental or theoretical work or computer simulations, depending on the trainee&amp;acute;s interests and qualifications. Our main areas of research are: spinning machinery, weaving',
'Aachen', 'R', 'M', 'MECHANICAL_ENG.|TEXTILE_TECHNOLOGY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0126', 'ac702d2f-0019-4e47-a436-c4901b665516', TRUE, 'EUR', '39', '8', '0', 'DE Employer126', 'Address1', 'Address2', 'university institute', '50', 'www.employer126.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '16', 'Frankfurt a. M.', 'Erlangen/N&uuml;rnberg', '2013-03-31',
'',
'650', 'MONTHLY', NULL,
'Experimental and theoretical work in the field of particle technology: adsorption, comminution, coating, sintering, particle characterization, crystallisation/precipitation of colloidal particles and nanoparticles.',
'Erlangen/N&uuml;rnberg', 'R', 'E', 'MECHANICAL_ENG.|CHEMISTRY|CHEMICAL_ENG._(ADSORPTION|REDUCING|PARTICLE_TECHNOLOGY|PRECIPITATION)', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0127', '0ca205f2-a516-41fc-9e5a-c2aadb4e916f', TRUE, 'EUR', '40', '8', '0', 'DE Employer127', 'Address1', 'Address2', 'university institute', '0', 'www.employer127.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '12', '26', 'K&ouml;ln', 'Bochum', '2013-03-31',
'Please see attached information.',
'650', 'MONTHLY', NULL,
'Please see attached information.',
'Bochum', 'R', 'E', 'CHEMISTRY|BIOCHEMISTRY', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0128', '11436e36-7da4-42d2-8c93-c12ab98ef5b5', TRUE, 'EUR', '40', '8', '0', 'DE Employer128', 'Address1', 'Address2', 'Software development', '0', 'www.employer128.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '26', '52', 'Stuttgart', 'Freiburg', '2013-03-31',
'programming experience required',
'650', 'MONTHLY', NULL,
'Software development (C++, CUDA, PHP, C#, Java), Database backend, user interface',
'Freiburg', 'P', 'M', 'INFORMATICS|MARKETING', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0129', '4d36c8af-4196-4709-b555-bb977e7889c1', TRUE, 'EUR', '40', '8', '0', 'DE Employer129', 'Address1', 'Address2', 'electric industry', '0', 'www.employer129.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'600', 'MONTHLY', 'IAESTE', '0', 'MONTHLY', '12', '26', 'D&uuml;sseldorf', 'Wuppertal', '2013-03-31',
'',
'750', 'MONTHLY', NULL,
'Traineeship in the field of research and development: basic research, theorectical and practical ; work in lab, building and tests of prototypes etc.',
'Wuppertal-Oberbarmen', 'R', 'M', 'MECHANICAL_ENG.|ELECTRICAL_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0130', 'f2a3f6c8-30c6-409f-8c3b-8bbe5bd2d0c0', TRUE, 'EUR', '40', '8', '0', 'DE Employer130', 'Address1', 'Address2', 'software development', '20', 'www.employer130.com',
'2013-01-01', '2013-03-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'IAESTE', '240', 'MONTHLY', '26', '26', 'Stuttgart', 'Karlsruhe Hbf.', '2013-09-15',
'Very good knowledge in: C/C++, Java, C#, Software Design, Software Specifications, Client/Server, Distributed Applications, TCP/IP, XML.',
'650', 'MONTHLY', NULL,
'Being part of the quality assurance (QA). This is: creating testsuites; analyzing software; enhancing given test- and metric tools; testing; writing testreports and much more.',
'Karlsruhe', 'P', 'M', 'COMPUTER_SC.|SOFTWARE_ENG.', '', '17');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('DE-2013-0131', 'efac736a-d535-45e5-a8ce-54867ff818b2', TRUE, 'EUR', '40', '8', '0', 'DE Employer131', 'Address1', 'Address2', 'university institute', '6', 'www.employer131.com',
'2013-01-01', '2013-12-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'650', 'MONTHLY', 'Employer', '240', 'MONTHLY', '40', '40', 'Hannover', 'Wernigerode', '2013-03-31',
'Interest and experiences in computer graphics',
'650', 'MONTHLY', NULL,
'Our company offers an internship in the field of computer sciences mainly focussing on computer graphics. The trainee will be part of a small team of developers and his main task will be the design and implementation of a new visualisation system for complex process states in the process industry (http://iai-wr.de/projekte/3dpid). The visualisation system already exists as a prototype in VRML and now this solution should be transferred into an industrial-suited version with a low level graphic engine like OpenGL or DirektX. First studies for these technologies have already been done by other students.For interaction with our staff members and our students, the trainee should have good ENGLISH skills and a basic knowledge of GERMAN language is helpful for the everyday life. Wernigerode is small village in the middle of GERMANy (www.wernigerode.de) with about 36000 inhabitants. Our company is a research institute of the Harz Uni- versity and therefore the trainee can easily connect to U',
'Wernigerode', 'P', 'M', 'IT|COMPUTER_GRAPHICS', '', '17');
