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
('PL-2013-0001', '72449120-2f84-4a36-b8b0-20c1932e1120', FALSE, 'PLN', '35', '7', NULL, 'Centrum Diagnostyki i Terapii Laserowej', NULL, '', 'Center of laser diagnostics and therapy in medicin', '50', 'www.p.lodz.pl/laser',
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
('PL-2013-0002', '71797b56-b1d2-42ac-82c3-53f7b9080bd7', FALSE, 'PLN', '30', '6', NULL, 'Pracownia Uslugowa Planowania i Architektury ARCHEX', 'Braci Gierymskich 143', '51-640 Wro Wroclaw', 'Architectural and Convervation Office', '6', NULL,
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
('PL-2013-0003', '8bfdf805-57bc-45e6-bb30-c13e99fb4010', TRUE, 'PLN', '40', '8', NULL, 'COMARCH S.A.', NULL, '', 'IT Solutions to Telecommunications, Finance and Ba', '2200', 'www.comarch.com',
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
('PL-2013-0004', '2abe881f-9f8e-4e33-a799-4854336106b8', TRUE, 'PLN', '30', '6', '20%', 'Warsaw University of Technology, Faculty of Civil Engineering, Dept. of Cnstruction and materials En', NULL, '', 'Civil Engineering', '25', 'www.il.pw.edu.pl',
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
('PL-2013-0005', 'd43611b3-d156-4366-af33-b46698126d2c', FALSE, 'PLN', '40', '8', '20%', 'ANT Sp.z.o.o.', NULL, '', 'Integration and data exchange in automatic systems', '8', 'www.ant.iss.pl',
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
('PL-2013-0006', '6560c1d2-c7c9-4e84-bc3b-81ffa850793b', TRUE, 'PLN', '40', '8', '0 %', 'Comarch S.A.', 'Al. Jana Pawla II 39', '31-864 Krakow', 'International Software House', '2200', 'www.comarch.com',
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
('PL-2013-0007', 'b84105f3-4061-47f9-a4ee-ce6609493c43', FALSE, 'PLN', '40', '8', '20%', 'ANT Sp. z.o.o.', 'Wadowicka 8a', '30-415  Krakow', 'Integration and data exchange in automatic systems', '8', 'www.ant-iss.pl',
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
('PL-2013-0008', '7ad87d75-d56f-4e1c-bc49-a87bfb3ca940', FALSE, 'PLN', '35', '7', NULL, 'Instytut Chemii Ogolnej i Ekilogicznej Pt.', NULL, '', 'Research Institute of general and ecological chemi', '4000', NULL,
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
('PL-2013-0009', '071caaa0-a3da-46c7-9ba5-f730a2132123', FALSE, 'PLN', '30', '6', '0 %', 'Szczecin University of Technology, Faculty of Civil Engineering and Architecture', NULL, ' Szczecin', 'Civil Engineering', '20', 'www.wbia.ps.pl',
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
('PL-2013-0010', '87d6b57c-c935-4430-9d45-1f1e9133ad34', TRUE, 'PLN', '40', '8', '20', 'Nokia Siemens Networks Sp. z o.o.', 'Zupnicza 11', '03-821 Warszawa', 'telecommunication', '1000', '',
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
('PL-2013-0011', '10efa841-cef2-426a-ad62-9f1c68da5acb', FALSE, 'PLN', '40', '8', '0', 'ROMAG Gryziski; Wieczorek s.j.', 'Zlotniki; ul Radosna 1A', '62-002  Suchy Las', 'Designing and manufacturing machinery and equipmen', '50', NULL,
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
('PL-2013-0012', '02d1b18b-900f-40b4-8bd2-d9de33bddb9f', FALSE, 'PLN', '40', '8', '0', 'ROMAG Gryzinski; Wieczorek s.j.', 'Zlotniki; ul Radosna 1A', '62-002 Suchy Las', 'Designing and manufacturing machinery and equipmen', '50', '',
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
('PL-2013-0013', '1ac9d15c-a4ed-4c9c-bd9f-d8ff7c4628fc', TRUE, 'PLN', '40', '8', '0', 'FQS Poland Sp. z o.o. (LTD)', 'Starowislna 13 - 15', '31-038 Krakow', 'sales;marketing;research&development', '20', '',
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
('PL-2013-0014', '3f5f9720-5cf8-4d43-b26e-609df0abd805', FALSE, 'PLN', '40', '8', '0', 'DRQ S.A.', 'Podwale 3', '31-118 Krakow', 'advanced technolog. to develop big scale IT solut.', '140', '',
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
('PL-2013-0015', 'e940eb24-2ec1-47d6-8118-5916eb2b6592', FALSE, 'PLN', '40', '8', '0', 'Union Square Internet Development Polska Sp. z o.o', 'Grodzka 50/3', '31-044  Krakow', 'IT consulting; web site and application developmen', '30', NULL,
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
('PL-2013-0016', '8f4a2af9-e1d8-41ae-8f68-a7354092f12a', TRUE, 'PLN', '40', '8', '0', 'Design Office PROMOST CONSULTING', 'Slowackiego 20', 'Rzeszow', 'Civil Engineering Design and Consulting', '50', '',
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
('PL-2013-0017', 'c48d542c-44eb-401f-b6eb-b08a5ec3d566', FALSE, 'PLN', '40', '8', '20', 'Gemius SA', 'Domaniewska 41', 'Warsaw', 'market and public opinion research', '125', '',
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
('PL-2013-0018', '5f24884c-df4f-417c-8c21-6464c93ff6d6', FALSE, 'PLN', '40', '8', '20', 'MAAS S.C. Architecture Office', 'Lekarska 4', '01-610  Warszawa', 'Architecture Office', '7', NULL,
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
('PL-2013-0019', 'd10759e9-c476-4885-8171-22e02a8186e2', TRUE, 'PLN', '35', '7', '20', 'Isotops Research and Development Centre POLATOM', 'Swierk 24 A', '05-400 Otwock', 'Research on chemistry;radiochemistry&radiophar', '46', '',
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
('PL-2013-0020', '69f57150-e209-4b35-8e79-4715d05e1d97', FALSE, 'PLN', '40', '8', '20', 'POLISH Geological Institute', 'Rakowiecka 4', '00-975 Warsaw', 'earth Sciences:geology; hydrogeology; environment', '730', '',
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
('PL-2013-0021', '1ecae0ef-7127-49df-901d-da1c839f9ee0', FALSE, 'PLN', '40', '8', '20', 'Institute of Electron Technology', 'Al. Lotnikow 32/46', '02-668 Warsaw', 'Electronics;construction&techn. of semico', '292', '',
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
('PL-2013-0022', '672b3e67-1e8d-4daa-ac06-4f2cc0e7d4f3', FALSE, 'PLN', '40', '8', '20', 'PROART Anna Rostkowska', 'Sniadeckich 10', '00-656 Warsaw', 'Architecture', '10', '',
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
('PL-2013-0023', 'e2c8d8f9-0525-4412-ba6f-7a0fc698d101', TRUE, 'PLN', '40', '8', '20', 'Jasinski Kruszewski Architekci', 'Rakowiecka 45/11', '02-528  Warsaw', 'Architecture', '10', NULL,
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
('PL-2013-0024', '27854535-711d-4c52-8caf-367719946265', FALSE, 'PLN', '40', '8', '20', 'POLISH Geological Institute', 'Rakowiecka 4', '00-975 Warsaw', 'earth Sciences:geology; hydrogeology; environment', '730', '',
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
('PL-2013-0025', '2bdd1cbd-8e63-4f8c-ba62-d977fbc820b0', FALSE, 'PLN', '30', '6', '0', 'Szczecin TU; Faculty of Electrical Engineering', '26-Kwietnia 10', '71-126 Szczecin', 'Electronics; Telecommunication', '0', '',
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
('PL-2013-0026', 'c3b98fbf-3eea-4bca-9440-58e0f3fe0450', FALSE, 'PLN', '40', '8', '20', 'Gemius SA', 'Domaniewska 41', 'Warsaw', 'market and public opinion research', '125', '',
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
('PL-2013-0027', '3d33656e-af82-46cb-843a-1f4f555306d2', TRUE, 'PLN', '40', '8', '0', 'Tukaj Mapping Central Europe sp. z o.o.', 'Odrzanska 7', '30-408 Krakow', 'Geographical Information Systems', '69', '',
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
('PL-2013-0028', '979671c1-8220-4de9-9cc1-3fd423ddfbe7', TRUE, 'PLN', '40', '8', '0', 'Tukaj Mapping Central Europe sp. z o.o.', 'Odrzanska 7', '30-408 Krakow', 'Geographical Information Systems', '69', '',
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
('PL-2013-0029', 'b5d30715-8466-43cc-8755-01e7bd535e34', FALSE, 'PLN', '40', '8', '0', 'Przedsibiorstwo Wdra|ania Innow', 'Zgrzebnioka 5', '41-500 Chorzow', 'related to transportation; storage and processing', '18', '',
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
('PL-2013-0030', 'ba6a2ca8-0837-4f14-8764-251ca4f91ed1', FALSE, 'PLN', '40', '8', '0', 'ALDEC-ADT Sp. z. o.o.', 'Widok 23', '40-118 Katowice', 'Product. of software for autom. of electr. project', '61', '',
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
('PL-2013-0031', '086d2886-e29b-4907-8edb-48aad16ec1f1', FALSE, 'PLN', '40', '8', '0', 'Comarch S.A.', 'Al. Jana PawBa II 39A', '31-864 Kraków (subsidiary Katowice)', 'IT Solutions to Telecommunications', '2200', '',
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
('PL-2013-0032', '50347514-5bb1-4130-8296-720b1a8f4a00', FALSE, 'PLN', '40', '8', '0', 'DASTA S.C. Stanislaw Lakota; Stanislaw Radwan', 'WladysBawa Sobocinskiego 5/2', '40-687 Katowice', 'Electronical devices for industry', '17', '',
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
('PL-2013-0033', '860a08aa-527b-4d09-b001-9b67dc8b220c', FALSE, 'PLN', '40', '8', '0', 'Towarzystwo Gospodarcze MEGART Sp. z o.o', 'Wyzwolenia 5', '43-190 Mikolów', 'Trade-technical', '12', '',
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
('PL-2013-0034', '5f83d099-e9ff-4247-9b65-cf36c65d18a5', FALSE, 'PLN', '40', '8', '0', 'LGBS Polska Sp. z o.o', 'Toszecka 101/302', '44-100 Gliwice', 'Informatics', '20', '',
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
('PL-2013-0035', 'b046a990-c40b-431f-b3a9-30f4750da5f1', FALSE, 'PLN', '35', '7', '0', 'UL Department of General and Inorganic Chemistry', 'Narutowicza 68', '90-136 Lodz', 'University', '38', '',
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
('PL-2013-0036', '54d0f8dd-06fd-447c-8f2e-faf6ba851a24', FALSE, 'PLN', '35', '7', '0', 'UL Department of Oganic Chemistry', 'Narutowicza 68', '90-136 Lodz', 'University', '27', '',
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
('PL-2013-0037', 'd21f867b-cb25-40e4-a27f-45998d4ad788', FALSE, 'PLN', '30', '6', '0', 'UL Department of Chemical Technology and Environ.', 'Pomorska 163', '90-236 Lodz', 'University', '36', '',
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
('PL-2013-0038', '819eefe0-298f-4793-b673-f14830b9d012', FALSE, 'PLN', '30', '6', '0', 'UL Department of Chemical Technology and Environ.', 'Pomorska 163', '90-236 Lodz', 'University', '36', '',
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
('PL-2013-0039', '16a6d58c-7473-41c6-91d7-3fd6ed0385f1', FALSE, 'PLN', '35', '7', '0', 'University of Lodz; Department of Mathematics', 'Banacha 22', '90-238 Lodz', 'Research and teaching', '142', '',
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
('PL-2013-0040', '0b6d373b-4364-4d48-8096-c69f265d64fe', FALSE, 'PLN', '35', '7', '0', 'University of Lodz; Department of Mathematics', 'Banacha 22', '90-238 Lodz', 'University', '12', '',
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
('PL-2013-0041', 'a13373f0-1f14-4f83-b76d-de67284f109f', TRUE, 'PLN', '40', '8', '20', 'Nokia Siemens Networks Sp. z o.o.', 'Zupnicza 11', '03-821 Warszawa', 'telecommunication', '1000', '',
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
('PL-2013-0042', '81013011-0295-4f8f-8cac-91151378d2b4', FALSE, 'PLN', '35', '7', '0', 'Institute of Material Science', 'Stefanowskiego 1/15', '90-924   Lodz', 'Material Sc./Material Eng', '70', NULL,
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
('PL-2013-0043', '085b66c1-896a-4454-abbc-404176242939', FALSE, 'PLN', '40', '8', '20%', 'MAAS s.c. Architecture office', 'lekarska 4', '01-610 Warsaw', 'architecture office', '7', 'www.maas.com.pl',
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
('PL-2013-0044', 'ec27031b-a2db-4e1b-bbe2-08950773a65b', TRUE, 'PLN', '35', '7', '0', 'AGH University of science and technology', 'Al. Mickiewicza 30', '30-059 Krakow', 'university', '40', 'www.agh.edu.pl',
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
('PL-2013-0045', 'bd238537-98f1-45eb-9614-2c98f07cdd8e', NULL, 'PLN', '30', '6', '20%', 'Fabryka Substancji Zapachowych Pollena Aroma Spolka z.o.o.', 'Klasykow 10', '03-115 Warsaw', 'Aroma substances, food aromas, cosmetics', '85', 'www.pollenaaroma.com.pl',
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
('PL-2013-0046', '4aaa6510-dc55-4641-a2da-219a7a6b0d96', NULL, 'PLN', '35', '7', '0%', 'Instytut Podstaw Chemii Zywnosci', 'Stefanowskiego 4/10', '90-924 Lodz', 'Research Institute - biotechnology and general foo', '20', NULL,
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
('PL-2013-0047', '43e920a7-f900-42f5-9f52-d74633bf6d34', NULL, 'PLN', '30', '6', '0%', 'AGH University of Science and Technology', 'Al. Mickiewicza 30', '30-059 Krakow', 'University', '40', NULL,
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
('PL-2013-0048', 'ff14868e-9afe-4cfc-9a8b-e51c0c4fb0cc', NULL, 'PLN', '35', '7', '0%', 'Centrum Diagnostyki i Terapii Laserowej', 'Wolczanska 215', '93-005 Lodz', 'Center of Laser Diagnostics & Therapy in Medicine', '50', NULL,
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
('PL-2013-0049', '8bb75ebf-cacf-453f-85c3-2eaf8a446220', TRUE, 'PLN', '40', '8', '0', 'KPG Ltd', 'Mogilska 80', '31-546 Krakow', 'company - Geodesy, Photogrammetry', '120', 'www.kpg.pl',
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
('PL-2013-0050', 'dc350f56-67e6-455e-9d1d-a0a6f4684a0b', FALSE, 'PLN', '40', '8', '0', 'Hartbex Przedsiebiorstwo Budowlane S.P.Z.O.O.', 'Adress 22', '36001 Trzebownisko', 'Building Construction', '425', 'www.hartbex.pl',
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
('PL-2013-0051', '2d90a965-68d2-42dc-9f2f-8fbf140d7c65', TRUE, 'PLN', '40', '8', '20%', 'Capgemini Polska Sp zo.o.', 'al. Jana Pawla II 12', '00-124 Warszawa (subsidiary Wroclaw)', 'Informatics', '2000', 'www.pl.capgemini.com',
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
('PL-2013-0052', '4268e097-53e8-4050-b68f-1c3aabc9c5f5', FALSE, 'PLN', '40', '8', '0', 'BEST Construction Sp. z o.o.', 'Mikolaja Reja 12', '35-211 Rzeszow', 'Execution of complex construction projects', '200', '',
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
('PL-2013-0053', '369bfd88-183d-44a8-94ef-2d6c1913403e', FALSE, 'PLN', '40', '8', '20', 'Volvo Polska Sp. z o.o.', 'ul. Mydlana 2', '51-502 Wroclaw', 'automitive, finantial services center', '2600', '',
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
('PL-2013-0054', '12d1ff38-2a0f-480b-9e97-c8126bee1c3f', FALSE, 'PLN', '40', '8', '0%', 'MakoLab M.i K. Sopek, Ltd.', 'Demokratyczna 46', '93-430 Lodz', 'Web graphics and software', '20', 'www.makolab.pl',
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
('PL-2013-0055', '290efe4c-5a14-421e-86ed-52db29c30944', TRUE, 'PLN', '25', '5', '0%', 'University of Lodz, Departement of Mathematics, Chair of Probability Theory and Statistics', 'Banacha 22', '90-238 Lodz', 'Mathematics', '12', 'www.math.uni.lodz.pl',
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
('PL-2013-0056', 'a12d2503-69f9-4e18-8217-631d64be4a06', TRUE, 'PLN', '40', '8', '20%', 'Are sp.z o.o.', 'Chmielna 24/3', '00-020 Warsaw', 'Architecture', '18', 'www.are.com.pl',
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
('PL-2013-0057', 'e60b2405-cdda-4f8a-9cc3-cde4062e2563', FALSE, 'PLN', '35', '7', '0%', 'INWAT Zaklad Badawczo-Projektowy', 'Sienkiewicza 101', '90-310 LÃ³dz', 'Steam and gas turbines - parts manufacturing, desi', '115', 'www.inwat.com.pl',
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
('PL-2013-0058', 'dd8e4f19-e591-471f-909b-3191d3e3c23d', FALSE, 'PLN', '40', '8', NULL, 'Geosat KrakÃ³w Sp,z.o.o.', 'Lukasiewicza 3', '31-429 Krakow', 'Geodesy, Cartography, Land Surveying', '100', 'www.geosat-krakow.pl',
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
('PL-2013-0059', '7d855e3a-c8f0-43c7-905e-41bfd666f506', FALSE, 'PLN', '35', '7', '0%', 'Centrum Diagnostyki i Terapil Laserowej', 'Wolczanska 215', '93-005 Lodz', 'Center of laser diagnostics & therapy in medicine', '50', 'www.p.lodz.pl/laser',
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
('PL-2013-0060', '3e3586db-751a-4e45-81b8-a7143867b792', TRUE, 'PLN', '40', '8', '20', 'Robobat Polska Sp. z o. o.', 'Radzikowskiego 47A', '31-315 Krakow', 'Civil Eng., Computer Science', '50', '',
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
('PL-2013-0061', '9ce64a0a-6c3f-45a0-83aa-fd2680d5f5bc', TRUE, 'PLN', '40', '8', '15', 'SELFA GE S.A.', 'Bieszczadzka 14', '71-042 Szczecin', 'Elect. heating elements', '130', '',
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
('PL-2013-0062', 'e4143405-e113-44bc-a1ae-c58b5646d00a', TRUE, 'PLN', '36', '6', '0', 'Rzeszow University of Technology, Chair of Building Engineering', 'Wincentego Pola 2', '35-959   Rzeszow', 'University', '50', 'www.prz.edu.pl',
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
('PL-2013-0063', '16e8e89b-ae91-4728-8cae-910fc5a89d2d', FALSE, 'PLN', '40', '8', '20', 'Are sp. z o.o.', 'Chmielna 24/3', '00-020   Warsaw', 'Architecture', '18', 'www.are.com.pl',
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
('PL-2013-0064', '430550c0-4755-42fc-bb81-50f04e70a381', TRUE, 'PLN', '40', '8', '0', 'MTA Engineering Sp. z o.o.', 'Poniatowskiego 14', '35-026   Rzeszow', 'Design office specializing in design of steel and ', '55', 'www.mta-online.net',
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
('PL-2013-0065', '08fa1309-0fd9-4da8-b7e4-9c8c96370bb2', TRUE, 'PLN', '40', '8', '0', 'COMARCH S.A.', 'Al. Jana Pawla II 39a', '31-864   Kraków', 'International software house that provides innovat', '2900', 'www.comarch.com',
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
('PL-2013-0066', 'b953b46e-2ea9-4aa2-b10c-65460b15d952', TRUE, 'PLN', '40', '8', '20', 'Floppy Computer Systems Sp. z o.o.', 'Grunwaldzka 345', '80-309   Gdañsk', 'IT,systems integration', '13', 'www.floppy.pl',
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
('PL-2013-0067', '0bb1bc0c-e028-459a-ac35-6f303f254320', TRUE, 'PLN', '40', '8', '0', 'Miros&sup3;aw Wi&para;niewski Architektura i Urbanistyka', '&macr;eromskiego 10', '90-710 &pound;&oacute;d&frac14;', 'Biuro Architektoniczno-Urbanistyczne', '5', '',
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
('HU-2013-0001', '1994f91b-b497-4349-aa3b-35c099351899', NULL, 'HUF', '40', '8', NULL, 'Elgoscar 2000 Ltd', NULL, '', 'Environmental analysis', '15', NULL,
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
('HU-2013-0002', '89eaf391-54d5-4bc1-997a-c9949e0bfddc', FALSE, 'HUF', '40', '8', '0', 'Pannonpharma Ltd.', 'Hungary-7720 Pécsvárad; Pannonpharma', '', 'medicine making', '70', '',
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
('HU-2013-0003', '9d7d489f-1ba6-424e-bad5-6228cf4eab14', FALSE, 'HUF', '40', '8', '0', 'MSc Ltd.', 'Hungary-1143 Budapest; Hungaria square 113.', '', 'Bridge and structural design', '30', '',
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
('HU-2013-0004', '4b8fc925-46ac-4715-98e8-c146bb3e8ea1', FALSE, 'HUF', '40', '8', '0', 'GE Healthcare', 'Hungary-2040 Budaörs; Akron str. 2.', '', 'Interventional X-ray machines', '400', '',
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
('HU-2013-0005', '99959015-56bf-4f15-9d97-cd04edf8a1eb', FALSE, 'HUF', '40', '8', '0', 'Usity of Miskolc Departm. of Fluid and Heat Eng.', 'Hungary-3515 Miskolc; Egytemváros', '', 'Teaching; research', '23', '',
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
('HU-2013-0006', 'fa667195-16cb-4bc5-8e15-caaed087c6b6', FALSE, 'HUF', '40', '8', '0', 'Telemax GSm Service Kft.', 'Hungary-1145 Budapest; Szugló u. 47.', '', 'Service (Mainly mobil phone)', '80', '',
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
('HU-2013-0007', '91d7ee9f-a95d-4e2d-80e8-91959d7cbd6d', FALSE, 'HUF', '40', '8', '0', 'DGS Kft.', 'Hungary-1118 Budapest; RegQs str. 13.', '', 'Security systems', '7', '',
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
('HU-2013-0008', '82914e86-219e-41d7-a58e-f4bcc9f6ef88', FALSE, 'HUF', '25', '5', '0', 'Pécsi Mélyépít&#33', 'Hungary-7624 Pécs; Budai Nagy Antal str. 1.', '', 'Public utility designed', '5', '',
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
('HU-2013-0009', '7ae8875d-8941-4351-8936-1a3d3c272f75', FALSE, 'HUF', '40', '8', '0', 'Microsoft Magyarország Kft.', 'H-1031 Budapest; Záhonyi street 3.', '', 'trading of commercial software products; consultin', '200', '',
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
('HU-2013-0010', '97b4c0c5-112c-48c4-986e-bd43c17bfe5c', FALSE, 'HUF', '40', '8', '-', 'University of Pecs Tech. Fac. Inst. of Architecture', '7624 Pecs, Rokus u. 2/b', '', 'Architecure', '22', NULL,
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
('HU-2013-0011', 'f08b8abe-3978-406e-b7b5-6d6a539d1728', FALSE, 'HUF', '40', '8', '-', 'MEEI Ltd. TÜV Rheinland Group', '1132 Budapest, Vaci ut 48/a-b', '', 'Product safety service', '93', 'www.meet.hu',
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
('HU-2013-0012', '58332ddf-1ad6-4dd7-9ac3-0922f8b137c4', FALSE, 'HUF', '40', '8', NULL, 'Mecsek-Öko Environmental Co.', '7633 Pecs, Esztergar', '', 'Recultivation', '135', NULL,
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
('HU-2013-0013', 'e665d011-38ae-4934-99e9-c61b89434ae6', NULL, 'HUF', '40', '8', '-', 'Hepenix Ltd', '2049 Diosd, Petofi S. u. 39', '', 'company', '15', 'www.hepenix.hu',
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
('HU-2013-0014', '1d0e5f3f-968f-48a7-8cc7-8e5cb310f8ed', FALSE, 'HUF', '40', '8', '0', 'E-Word Ltd.', 'H-1028 Budapest,', 'Vízesés u. 10.', 'Translation, project management software', '4', '',
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
('HU-2013-0015', 'a6ae3c5a-2543-4b5a-80bb-650b4c44d143', FALSE, 'HUF', '40', '8', '0%', 'Meditcom Ltd.', 'Robert Karoly krt. 59.', '1134 Budapest', 'IT, Healthcare', '20', 'www.meditcom.hu',
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
('HU-2013-0016', '96d3fb7d-f08a-462c-8a57-bf0d5c413860', FALSE, 'HUF', '40', '8', NULL, 'Finta and Partners Architect Studio Ltd.', 'Szt. Istvan krt. 11', '1055 Budapest', 'Architectural design', '30', 'www.fintastudio.hu',
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
('HU-2013-0017', 'f7deb2e6-3901-44d7-b523-a22353046d9c', FALSE, 'HUF', '40', '8', '0', 'Gamaxcom Ltd.', 'Pazsit u. 6', '1026 Budapest', NULL, NULL, NULL,
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
('HU-2013-0018', 'fa70cc64-68d3-4edd-b273-67707ffaa160', FALSE, 'HUF', '40', '8', '0%', 'Alfred Schuon Lt.d.', 'Hazgyari u. 16', '8200 Veszprem', 'Logistics', '30', 'www.schuon.com',
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
('HU-2013-0019', '4c3f2ce8-0153-4ccf-ab2a-9b7870d0e2ba', TRUE, 'HUF', '40', '8', '0', 'GE Hungary Lpc. Healthcare Division', '2040 Budaörs, Akron u. 2.', '', 'Vascular X-ray Imaging Systems', '450', '',
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
('HU-2013-0020', 'de2dbf58-6912-46a9-b93f-580203af5740', FALSE, 'HUF', '40', '8', '0', 'GE Hungary Lpc. Healthcare Division', '2040 Budaörs, Akron u. 2.', '', 'X-ray Imaging Systems', '450', '',
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
('HU-2013-0021', '2de3b490-a647-4e91-8f70-4a9a001d0c5b', FALSE, 'HUF', '40', '8', '0', 'Pioneer Hi-Bred Ltd.', '2040 Budaörs, Neumann J. u. 1.', '', 'Production, marketing and sales of commercial seed', '150', '',
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
('HU-2013-0022', '0a750e60-9c45-44d0-96eb-69c338fa5a83', FALSE, 'HUF', '40', '8', '0', 'MFKK Invention & Research Center', '1119 Budapest, Tétényi út 93.', '', 'Research and development', '22', '',
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
('HU-2013-0023', 'cad27704-6a3d-44a5-8a21-5f6f6e894903', FALSE, 'HUF', '40', '8', '0', 'MFKK Invention & Research Center', '1119 Budapest, Tétényi út 93.', '', 'Research and development', '22', '',
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
('HU-2013-0024', '6c66085c-6655-4d4c-a3ad-2e50150613dc', FALSE, 'HUF', '40', '8', '0', '4 Hungar Agri-Tech Ltd.', '7621 Pécs, Zrínyi u. 1.', '', 'Agricultural Facility', '15', '',
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
('HU-2013-0025', 'e1af9f34-bac5-41a9-84a6-b2e30f7ed92f', TRUE, 'HUF', '40', '8', '0', 'American Appraisal Hungary Ltd.', 'H-1132 Budapest, Váci út 18.', '', 'Valuation', '20', '',
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
('HU-2013-0026', '52b6bb3a-39fe-4a94-a4f3-a7b545264610', FALSE, 'HUF', '40', '8', '0', 'Hídtechnika Ltd.', '1138 Budapest, Karikás Frigyes u. 20.', '', 'Building industry', '33', '',
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
('HU-2013-0027', '63276468-acf3-4a77-bb07-c44f817a4348', TRUE, 'HUF', '40', '8', '0', 'BUTE, Dep. of Automation and Applied Informatics', 'H-1111 Budapest, Goldmann György tér 1.', '', 'Higher education', '15', '',
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
('HU-2013-0028', 'ed7bccd5-74de-481c-ae30-fe7bed93f45c', TRUE, 'HUF', '40', '8', '0', 'BUTE, Dep. of Automation and Applied Informatics', 'H-1111 Budapest, Goldmann György tér 1.', '', 'Higher education', '15', '',
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
('HU-2013-0029', 'ec2967a1-3c86-4515-a588-46ce42139031', FALSE, 'HUF', '40', '8', '0', 'Art Vital Ltd.', 'H-4400 Nyíregyháza, Selyem u. 21.', '', 'Architecture', '18', '',
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
('HU-2013-0030', 'd7ed1785-b819-4c75-9895-526724c60fe6', FALSE, 'HUF', '45', '9', '0', 'Bálint és Társa Architect Office Ltd.', '1051 Budapest, Október 6. u. 3.', '', 'Architectural design', '10', '',
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
('HU-2013-0031', '0a759dae-c2bc-4ea6-abdb-d099811f04bf', TRUE, 'HUF', '20', '4', '0', 'MFKK Invention & Research Center Ltd.', '1119 Budapest, Tétényi út 93.', '', 'Research and development', '25', '',
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
('HU-2013-0032', 'c5612d3a-5467-4f05-88c0-8ece5526baa1', TRUE, 'HUF', '20', '4', '0', 'MFKK Invention & Research Center Ltd.', '1119 Budapest, Tétényi út 93.', '', 'Research and development', '25', '',
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
('HU-2013-0033', 'ef273a79-6cce-4b80-9cfb-ba5aee36f708', TRUE, 'HUF', '20', '4', '0', 'MFKK Invention & Research Center Ltd.', '1119 Budapest, Tétényi út 93.', '', 'Research and development', '25', '',
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
('HU-2013-0034', 'dd23dea0-c3ca-4cc3-a683-cf9339fd85d9', TRUE, 'HUF', '40', '8', NULL, 'Amri Hungary Ltd.', '1031 Budapest, Záhony utca 7', ' ', 'Preparative organic chemistry', '100', NULL,
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
('HU-2013-0035', '7590c363-aa6a-40dd-9bca-e0e2c589cd95', FALSE, 'HUF', '40', '8', NULL, 'MFKK Invention and Research Center ltd.', '1119 Budapest, Tétényi street 93.', ' ', 'Research and Development', '22', NULL,
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
('HU-2013-0036', '69c7b055-9070-496a-b892-64a0b9de46a1', TRUE, 'HUF', '40', '8', NULL, 'BME - Dept. of Hydrodynamic System', '111 Budapest, Mqegyetem rkp.3.', ' ', 'Education - research', '25', NULL,
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
('HU-2013-0037', '8bb6c32a-1a6d-419d-9f2b-92e7f0745067', FALSE, 'HUF', '40', '8', '0', 'Robert Bosch Kft.', NULL, ' Budapest', NULL, '650', NULL,
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
('HU-2013-0038', '0a8624d7-52f2-4670-804e-5b6d1d63c99b', TRUE, 'HUF', '40', '8', '0', 'Cental food Research Institute', '1022 Budapest, Herman Ott&oacute; &uacute;t 15.', '', 'Food research', '100', '',
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
('HU-2013-0039', '54eec7ee-486b-4991-bd10-de77d2a046dc', FALSE, 'HUF', '40', '8', '0', 'Robert Bosch Kft.', '1103 Budapest Gy&ouml;mr&amp;#337;i &uacute;t 120.', 'Hungary', 'Automative Tool Development', '650', '',
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
('HU-2013-0040', '23a9a28d-be54-4521-ad50-2775cf593e10', FALSE, 'HUF', '40', '8', '', 'Bal&aacute;zs &eacute;s V&eacute;csey Ltd.', '4026 Budapest, K&aacute;lvin t&eacute;r 14. fsz. 2.', '', 'Architectural design', '7', '',
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
('HU-2013-0041', 'd51cfb5c-906a-4947-9a5e-ef6524e4a68d', FALSE, 'HUF', '40', '8', '', 'Bachman and Bachmann Ltd.', '7625 P&eacute;cs, B&ouml;ckh J&aacute;nos utca 17.', '', 'Architectural', '0', '',
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
('HU-2013-0042', '17f97570-a262-4cbc-9ce9-8ab7909b0696', FALSE, 'HUF', '40', '8', '0', 'Art Vital Ltd.', '4400 Ny&iacute;regyh&aacute;za, Selyem utca 21.', '', 'Architectural', '18', '',
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
('HU-2013-0043', 'f5d0feb1-8b3c-41ee-b638-cd8d4cf4bb68', TRUE, 'HUF', '40', '8', '', 'University of Miskolc, Dept. of Fluid Mech.', '3515 Miskolc - University Campus (Egyetemv&aacute;ros)', '', 'CFD Analyse', '0', '',
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
('HU-2013-0044', 'b47d9656-6f2c-41fa-82eb-b559c51bb14f', TRUE, 'HUF', '40', '8', '', 'Naturen Ltd.', '1163 Budapest, Czir&aacute;ki u. 32.', '', 'Engineering services, automated test equipment', '10', '',
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
('HU-2013-0045', 'db13f3d9-964a-4380-a456-04933eda1e8d', TRUE, 'HUF', '40', '8', '', '4 Hungar-Tech Ltd.', '7621 P&eacute;cs, Zr&iacute;nyi u. 1.', '', 'Agricultural postharrest technology', '15', '',
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
('HU-2013-0046', '0cc7712f-7e8c-4e4b-a206-86c3299ec29d', FALSE, 'HUF', '40', '8', '', 'MFKK Invention and Research Center ltd.', '1119 Budapest, T&eacute;t&eacute;nyi street 93.', '', 'Research and Development', '22', '',
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
('HU-2013-0047', '251523a8-8a30-4475-a863-2783fe2c4d95', FALSE, 'HUF', '40', '8', '', 'MSc LTD.', '1106 Budapest, Feh&eacute;r &uacute;t 10/a-b', '', 'Bridge and structural design', '30', '',
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
('HU-2013-0048', '03ea7f5b-9de3-4e65-bda5-22d28f537b7e', FALSE, 'HUF', '40', '8', '', 'M&aacute;v&eacute;pcell Kft.', '9500 Celld&ouml;m&ouml;k, Nagy S&aacute;ndor t&eacute;r 14.', '', 'Railway construction and engineering', '250', '',
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
('HU-2013-0049', 'dd43d0f5-bc41-4aa0-82c2-ec32946a4ece', FALSE, 'HUF', '40', '8', '', 'ASA Construction Ltd.', '6800 H&oacute;dmez&amp;#337;v&aacute;rs&aacute;lyhely, Erzs&eacute;bet &uacute;t 9.', '', 'Precast concrete elements', '370', '',
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
('HU-2013-0050', '4b4e4669-9d1c-4b0d-8c95-297e4d5143cc', FALSE, 'HUF', '40', '8', '', 'MFKK Invention and Research Center ltd.', '1119 Budapest, T&eacute;t&eacute;nyi street 93.', '', 'Research and Development', '22', '',
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
('HU-2013-0051', '008ee3ae-ce10-4076-97d5-a82edb4246f4', FALSE, 'HUF', '40', '8', '', 'BME - Dept. of Oganic Chemical Technology', '1111 Budapest, M&amp;#369;egyetem rakpart 3.', '', 'Chemical engineering, research and laboratory eng.', '12', '',
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
('HU-2013-0052', 'c3600a8e-6c4f-4a11-b6a4-33612a3fe197', FALSE, 'HUF', '40', '8', '', 'Bravogroup  Rendszerhaz Ltd.', '1145 Budapest, &Uacute;jvil&aacute;g utca 50-52', '', 'Sales of office machines', '200', '',
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
('HU-2013-0053', '34018f53-2990-4e1e-8da5-15ffcca636a3', FALSE, 'HUF', '40', '8', '', 'Designsoft Inc.', '1067 Budapest, csengery u. 53.', '', 'Software development', '17', '',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '16', 'Budapest, Ferihegy', 'Bus, tram, etc.', '2013-03-31',
'',
'27000', 'WEEKLY', NULL,
'Elektronics, Physics or Architectural software localization from ENGLISH to one of the following language, RUSSIAN, Italian, Dutch',
'Budapest', 'P', 'M', 'ELECTRICAL_ENGINEERING', '', '21');

INSERT INTO offers (ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website,
                    from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level,
                    living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline,
                    other_requirements,
                    payment, payment_frequency, prev_training_req,
                    work_description,
                    working_place, work_type, study_levels, study_fields, specializations, group_id) VALUES
('HU-2013-0054', 'cf3b48bf-ebef-4f0c-bff4-0983e5a23d5e', TRUE, 'HUF', '40', '8', '0', 'Robert Bosch Elektronikai Ltd.', '3000 Hatvan, Robert Bosch &uacute;t 1.', '', 'Production/automotive division', '2700', '',
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
('HU-2013-0055', '116672a3-e5b5-4086-83a1-cb094336b214', TRUE, 'HUF', '40', '8', '', 'Robert Bosch Elektronikai Ltd.', '3000 Hatvan, Robert Bosch &uacute;t 1.', '', 'Production/automotive division', '2700', '',
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
('HU-2013-0056', '54385a25-232b-427f-ae00-f0cf8532bb6b', FALSE, 'HUF', '40', '8', '0', 'Robert Bosch Kft.', '1103 Budapest Gy&ouml;mr&amp;#337;i &uacute;t 120.', '', 'Automative Tool Development', '650', '',
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
('HU-2013-0057', '08c0939e-6f2e-4d41-8b9a-8ce613301680', FALSE, 'HUF', '40', '8', '0', 'Robert Bosch Kft.', '1103 Budapest Gy&ouml;mr&amp;#337;i &uacute;t 120.', '', 'Automative Tool Development', '650', '',
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
('HU-2013-0058', '68efbbef-0a2d-4e7a-ae87-f010517580f7', FALSE, 'HUF', '40', '8', '0', 'Robert Bosch Kft.', '1103 Budapest Gy&ouml;mr&amp;#337;i &uacute;t 120.', 'Hungary', 'Automative Tool Development', '650', '',
'2013-04-01', '2013-12-31', NULL, NULL, NULL, NULL, 'GERMAN', 'G', NULL, NULL, NULL, NULL, NULL, NULL,
'20000', 'WEEKLY', 'IAESTE Hungary', '10000', 'WEEKLY', '8', '38', 'Budapest', 'bus, train, etc.', '2013-03-31',
'knowledge of programming, and FPGA',
'27000', 'WEEKLY', NULL,
'Tasks: Sensors (acceleration, rotation, pressure, etc.). Measuring development. New measurement concepts, procedures for the new products. The further development of existing measurement techniques. Complex electronic regulator development and planning. Developing and programming measurement results evaluation procedures and routines . Improved procedures, electronics, software, equipment installation, commissioning. Laboratory tool calibration. Team work with the GERMAN and HUNGARIAN engineers.   Requirements: Studies in electrical engineering or physics  Knowledge of at least one high-level programming language (eg C, CVI) FPGA knowledge Communication level GERMAN language skills',
'Budapest', 'R', 'M', 'SEE_&QUOT;KIND_OF_WORK&QUOT;', '', '21');
