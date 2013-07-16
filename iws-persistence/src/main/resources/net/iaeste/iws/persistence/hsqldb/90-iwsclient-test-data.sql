-- =============================================================================
-- Please add all test data here, for integration tests & demonstrations
-- =============================================================================

-- Austrian Test users, the NS is defined in the init_data script, UserId >= 7
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('e0d85817-8fc7-4a4f-bb4c-06f77b0f2c03', 'ACTIVE', 'austria1', 'User1.Austria@iaeste.org', 'ed77a47bc71c98f46ffffddbb90bed7cf6d10497c9f1cbae4c9d4c0dec474dba', 'a2bae4e6-19f5-4193-9a41-83679b043b87', 'User1', 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d200510e-b890-4143-83e1-504dcab0702d', 'ACTIVE', 'austria2', 'User2.Austria@iaeste.org', '5416ecdaad900c727606a3adc56a118bda285eea2f17049d345cf43cce100fd7', '739c735c-bb72-470b-95b9-f6adc8b3852b', 'User2', 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('7ec477e4-7841-47b9-9eb7-ab4a9848e704', 'ACTIVE', 'austria3', 'User3.Austria@iaeste.org', 'f561a1edd66b4f190f49201d24bb2e4de12f4d8c9df24f1f9bebf4de8c5233e8', '7931c07b-ea14-4f46-b1da-6dd3f1ff5b50', 'User3', 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('aeedc06e-4d7e-457a-83e0-252191237e60', 'ACTIVE', 'austria4', 'User4.Austria@iaeste.org', 'ed91ba38ed1246725b20099bb7b16250e0f7cad537f31b3448933cb2306b9575', 'ad2c4425-5a96-4ab1-b764-d1bd504af661', 'User4', 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('6170e527-92b3-4891-9cbb-242dd7e2a925', 'ACTIVE', 'austria5', 'User5.Austria@iaeste.org', '64152ca479467e92de9643c412219e159c41f55cd5655fe4ec55f5679196a691', '59f91419-3784-4764-97ee-6dd14f324ac3', 'User5', 'Austria');

-- Adding the Austrian Users to the Austrian Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values ( 7, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 7, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 7, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 8, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 8, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 8, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 9, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 9, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 9, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values (10, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values (10, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values (10, 12, 2);
insert into user_to_group (user_id, group_id, role_id) values (11, 10, 2);
insert into user_to_group (user_id, group_id, role_id) values (11, 11, 2);
insert into user_to_group (user_id, group_id, role_id) values (11, 12, 2);

-- Croatian Test users, the NS is defined in the init_data script, UserId >= 12
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('643e3e9c-4a63-4ddf-8817-7d993344e8f9', 'ACTIVE', 'croatia1', 'User1.Croatia@iaeste.org', '9f301c2a5ae29cc5f4f7b0aa05a9f2bb115c04dca7f7dd83a420010a75989d42', '72731dd3-464a-41a4-b984-600504457903', 'User1', 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a540fb5e-b22d-411b-851f-5438fbbeb4ad', 'ACTIVE', 'croatia2', 'User2.Croatia@iaeste.org', '19fcc3e27f4c6b6d9b8682286e74b2faafc7063aca383abd3c1ac32e10894512', 'eb65938b-2690-424a-b5eb-234cb384de4e', 'User2', 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fa75b255-67c8-43e1-a31c-b2ef81a719d1', 'ACTIVE', 'croatia3', 'User3.Croatia@iaeste.org', '7453d30c5697371a86884e5f1b99bc577756d2099ab754978469d73dec44141e', 'd0c312b2-dc41-4c9f-842c-b2c161daedec', 'User3', 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('36e51fde-dcdc-4711-9101-8021fe4fc04d', 'ACTIVE', 'croatia4', 'User4.Croatia@iaeste.org', '993f20cca445f9af4db32dfd6e4aa3843352045ef81e4b466cbbe7c8597b7311', '7c8dee7f-b8a5-40e8-9aa0-73e371dcf1d3', 'User4', 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('0559e87a-d929-439b-b64d-715b24bc1161', 'ACTIVE', 'croatia5', 'User5.Croatia@iaeste.org', 'f2766162d35713c3ecb3d6249ce848405001b2d8782ec426b458e4202f84f300', 'a1c79d31-3f50-49bd-a453-43280c798e0a', 'User5', 'Croatia');

-- Adding the Croatian Users to the Croatian Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (12, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (12, 14, 2);
insert into user_to_group (user_id, group_id, role_id) values (12, 15, 2);
insert into user_to_group (user_id, group_id, role_id) values (13, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (13, 14, 2);
insert into user_to_group (user_id, group_id, role_id) values (13, 15, 2);
insert into user_to_group (user_id, group_id, role_id) values (14, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (14, 14, 2);
insert into user_to_group (user_id, group_id, role_id) values (14, 15, 2);
insert into user_to_group (user_id, group_id, role_id) values (15, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (15, 14, 2);
insert into user_to_group (user_id, group_id, role_id) values (15, 15, 2);
insert into user_to_group (user_id, group_id, role_id) values (16, 13, 2);
insert into user_to_group (user_id, group_id, role_id) values (16, 14, 2);
insert into user_to_group (user_id, group_id, role_id) values (16, 15, 2);

-- German Test users, the NS is defined in the init_data script, UserId >= 17
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('7492b415-af79-4659-a956-5ad72dc358e1', 'ACTIVE', 'germany1', 'User1.Germany@iaeste.org', '07dc796ec8c62aba7e984d5c9e95040cf1c5409fb87eefe65468b77b9750b9b8', 'ac6b97cb-cc10-48a3-b3b1-e8244d838b04', 'User1', 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('6d444290-5d1f-49c7-ae01-372a92e6c253', 'ACTIVE', 'germany2', 'User2.Germany@iaeste.org', '249aa9e71ed1981e4189d1e031b59fedb9fe70cec71f35d462ddc06a0966eb66', '04e4b95b-8390-4933-8d56-360ca033affa', 'User2', 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1018e987-3612-4035-8b66-9e0f003e059e', 'ACTIVE', 'germany3', 'User3.Germany@iaeste.org', '471d629a24e5069ccb05198fde5e8370328cc14c662b38f036a49f03710c32ae', 'f995bc57-c286-4cf2-93f5-ad015edb132e', 'User3', 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a03b7c05-53fc-463d-bbd6-c28082ddde8c', 'ACTIVE', 'germany4', 'User4.Germany@iaeste.org', '6b89babb035b6913dd73f8bc03bc1f1c7e59b6dad75b09381e187c2c320c5f11', 'abf45c4f-ee23-4037-8e10-54b952bb43b8', 'User4', 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ddf734c0-7743-4073-bf67-f411de373ec7', 'ACTIVE', 'germany5', 'User5.Germany@iaeste.org', '99e679922896448133d2ca249244e41cffc1c93d21b2e62c294b569efca0db60', '06595734-3764-4f1e-8d2e-e39e6081dbb0', 'User5', 'Germany');

-- Adding the German Users to the German Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (17, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (17, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (17, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (18, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (18, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (18, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (19, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (19, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (19, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (20, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (20, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (20, 21, 2);
insert into user_to_group (user_id, group_id, role_id) values (21, 19, 2);
insert into user_to_group (user_id, group_id, role_id) values (21, 20, 2);
insert into user_to_group (user_id, group_id, role_id) values (21, 21, 2);

-- Hungarian Test users, the NS is defined in the init_data script, UserId >= 22
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a25fb28b-14e6-4b70-9727-a260ec8ca1ca', 'ACTIVE', 'hungary1', 'User1.Hungary@iaeste.org', 'fbdf6fabdbd97f872f3f005ecedd3f8b7da8e855a4a4f68063ff3119a7de906e', '76e2a272-651a-4a80-9e75-b1f856e56f73', 'User1', 'Hungary');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a742bb2a-575f-4e90-b680-f3f47f959638', 'ACTIVE', 'hungary2', 'User2.Hungary@iaeste.org', '726d29600f90c2da8b178bc56a4b64c717b0c8d8bfd045c254d3e64504964c49', 'b3b13a8b-c09c-4c9b-9e0f-82904f347aee', 'User2', 'Hungary');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b4c3aa8a-3fc5-440a-8d0f-99389444794c', 'ACTIVE', 'hungary3', 'User3.Hungary@iaeste.org', 'b89b84385c3f5721bfd20c5871c488876c38d229a024efe25801c83641b8da20', 'e91ab34d-8199-4f21-92f0-2ce2477a51eb', 'User3', 'Hungary');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('f019f130-f348-4018-9342-c6e174fc30f2', 'ACTIVE', 'hungary4', 'User4.Hungary@iaeste.org', 'b4507b5947db046ee40aa3ff0f84e5129185b5ce6351ac11c76128297ff17a12', 'ca26e36e-6a82-45e5-b97b-e036e15d537d', 'User4', 'Hungary');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b35c7df8-d769-40fd-b829-30f2b2482b44', 'ACTIVE', 'hungary5', 'User5.Hungary@iaeste.org', 'af40157a2cb424956d5e34bbe33a0e07bad763dbdbd6217df0c88ae7998321ae', '9dee4ee9-e5a3-4b7f-b9ce-3842a16a8df3', 'User5', 'Hungary');

-- Adding the Hungarian Users to the Hungarian Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (22, 25, 2);
insert into user_to_group (user_id, group_id, role_id) values (22, 26, 2);
insert into user_to_group (user_id, group_id, role_id) values (22, 27, 2);
insert into user_to_group (user_id, group_id, role_id) values (23, 25, 2);
insert into user_to_group (user_id, group_id, role_id) values (23, 26, 2);
insert into user_to_group (user_id, group_id, role_id) values (23, 27, 2);
insert into user_to_group (user_id, group_id, role_id) values (24, 25, 2);
insert into user_to_group (user_id, group_id, role_id) values (24, 26, 2);
insert into user_to_group (user_id, group_id, role_id) values (24, 27, 2);
insert into user_to_group (user_id, group_id, role_id) values (25, 25, 2);
insert into user_to_group (user_id, group_id, role_id) values (25, 26, 2);
insert into user_to_group (user_id, group_id, role_id) values (25, 27, 2);
insert into user_to_group (user_id, group_id, role_id) values (26, 25, 2);
insert into user_to_group (user_id, group_id, role_id) values (26, 26, 2);
insert into user_to_group (user_id, group_id, role_id) values (26, 27, 2);

-- Polish Test users, the NS is defined in the init_data script, UserId >= 27
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('46e69124-4796-4d4b-ab5c-9609aac37660', 'ACTIVE', 'poland1', 'User1.Poland@iaeste.org', 'e750a40dcc9f19fe48ac45edbbe88fcd9fa5295a75dc4f80785d783c01b6fd4a', 'c98df82a-ccfb-421a-a14d-d8b0cb2cfa51', 'User1', 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('bebc45c3-e64f-4d19-8317-71a191139a99', 'ACTIVE', 'poland2', 'User2.Poland@iaeste.org', 'c60d8c82677665475e7a1cbd3a7409ca1f1520e6ea2f1bb27a794c795540ed03', '1b269a5b-eb85-4a87-9f23-4456fad07d17', 'User2', 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('7602df82-8c6b-4e39-9d03-d566eaa63cab', 'ACTIVE', 'poland3', 'User3.Poland@iaeste.org', '2c0bd255c581c676ec5eef149cb8eb03604eae4d9b7a10927eecccaafffafa19', '3e7539b0-23d2-4e75-bc21-cf6b8f1508b9', 'User3', 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a272b1bb-536a-4d02-80e2-ca03cd1ad910', 'ACTIVE', 'poland4', 'User4.Poland@iaeste.org', 'faabcd5cbb7e4500dbee4e03b98e29fc50d71cfb3aa9053ef3210445497e97db', 'fdf0ccd1-f8f3-4ac5-b511-024196c43c78', 'User4', 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('bd615302-12ef-4c7e-84da-953878f4edaf', 'ACTIVE', 'poland5', 'User5.Poland@iaeste.org', '66776c844e2a73eb0aa34af9569a4c4868821069ab9d1fe8a01042d54a2e1a9c', '34938048-09ea-4a66-a633-4f65d61a3933', 'User5', 'Poland');

-- Adding the Polish Users to the Polish Member & National Groups
insert into user_to_group (user_id, group_id, role_id) values (27, 22, 2);
insert into user_to_group (user_id, group_id, role_id) values (27, 23, 2);
insert into user_to_group (user_id, group_id, role_id) values (27, 24, 2);
insert into user_to_group (user_id, group_id, role_id) values (28, 22, 2);
insert into user_to_group (user_id, group_id, role_id) values (28, 23, 2);
insert into user_to_group (user_id, group_id, role_id) values (28, 24, 2);
insert into user_to_group (user_id, group_id, role_id) values (29, 22, 2);
insert into user_to_group (user_id, group_id, role_id) values (29, 23, 2);
insert into user_to_group (user_id, group_id, role_id) values (29, 24, 2);
insert into user_to_group (user_id, group_id, role_id) values (30, 22, 2);
insert into user_to_group (user_id, group_id, role_id) values (30, 23, 2);
insert into user_to_group (user_id, group_id, role_id) values (30, 24, 2);
insert into user_to_group (user_id, group_id, role_id) values (31, 22, 2);
insert into user_to_group (user_id, group_id, role_id) values (31, 23, 2);
insert into user_to_group (user_id, group_id, role_id) values (31, 24, 2);

-- Add some offers for testing..
--- not postgres doesn't support the way how the nomination date is calculates. use this for PG: CURRENT_DATE + '3 month'::INTERVAL
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(1, 'AT-2013-0001-AB', '27d21139-c478-4c30-be57-aba0aa57ac2d', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Experience in JAVA', 1250.00, 'MONTHLY', FALSE, 'Working on a project in the field of science to visualize potential threads to economy and counter fight decreasing numbers', 'Vienna', 'R', 'B', 'IT|MATHEMATICS', 'BUSINESS_INFORMATICS', 11, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(2, 'AT-2013-0002-AB', '42dbdd0b-625a-4b97-b2c6-bf8f43bdc2b2', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'O', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), '', 1250.00, 'MONTHLY', FALSE, 'Work on a simulation of nicotine addiction on monkeys', 'Vienna', 'R', 'B|M', 'IT|CHEMISTRY', 'BUSINESS_INFORMATICS', 11, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(3, 'AT-2013-0003-AB', '2768e03a-1574-4063-8d1f-3aae55df8cdd', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'A', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Has built three houses', 1250.00, 'MONTHLY', FALSE, 'World first dog hotel', 'Vienna', 'R', 'B|M|E', 'CIVIL_ENGINEERING', '', 11, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(4, 'AT-2013-0004-AB', '636c7603-6706-460f-82e7-cba97298edeb', FALSE, 'EUR', 38.5, 7.7, 'approx. 30', 'Vienna University of Technology', 'Karlsplatz 13', '1040 Wien', 'University', 9000, 'www.tuwien.ac.at', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'VIE', 'Karlsplatz', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Good tongue and great appetite', 1250.00, 'MONTHLY', FALSE, 'Inspection of food\nDetermination of parameters\nCreation of tasteless narcotic', 'Vienna', 'R', 'B', 'FOOD_SCIENCE', '', 11, 'SHARED');

INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(5, 'HR-2013-0001', '88debcd6-7b50-4320-a3a4-9945070c48c0', FALSE, 'HRK', 38.5, 7.7, 'approx. 10', 'University of Zagreb', 'Marshal Tito Square 14', '10000, Zagreb', 'University', 5000, 'www.unizg.hr', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'ZAG', 'Zagreb Glavni Kolod', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Experience in JAVA', 1250.00, 'MONTHLY', FALSE, 'Working on a project in the field of science to visualize potential threads to economy and counter fight decreasing numbers', 'Zagreb', 'R', 'B', 'IT|MATHEMATICS', 'BUSINESS_INFORMATICS', 14, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(6, 'HR-2013-0002', '2b1005b7-c7a3-46b2-b127-c621c2c411b2', FALSE, 'HRK', 38.5, 7.7, 'approx. 10', 'University of Zagreb', 'Marshal Tito Square 14', '10000, Zagreb', 'University', 5000, 'www.unizg.hr', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'CROATIAN', 'G', 'O', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'ZAG', 'Zagreb Glavni Kolod', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), '', 1250.00, 'MONTHLY', FALSE, 'Work on a simulation of nicotine addiction on monkeys', 'Zagreb', 'R', 'B', 'IT|CHEMISTRY', '', 14, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(7, 'HR-2013-0003', '3ce2a1c2-98e3-4289-90b4-adf286f4c5f3', FALSE, 'HRK', 38.5, 7.7, 'approx. 10', 'University of Zagreb', 'Marshal Tito Square 14', '10000, Zagreb', 'University', 5000, 'www.unizg.hr', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'CROATIAN', 'G', 'A', 'HUNGARIAN', 'F', 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'ZAG', 'Zagreb Glavni Kolod', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Has built three houses', 1250.00, 'MONTHLY', FALSE, 'World first dog hotel', 'Zagreb', 'R', 'B', 'CIVIL_ENGINEERING', '', 14, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(8, 'HR-2013-0004', '081bcb91-04ce-4301-9bdf-81784750a96a', FALSE, 'HRK', 38.5, 7.7, 'approx. 10', 'University of Zagreb', 'Marshal Tito Square 14', '10000, Zagreb', 'University', 5000, 'www.unizg.hr', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'CROATIAN', 'G', NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, 'ZAG', 'Zagreb Glavni Kolod', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Good tongue and great appetite', 1250.00, 'MONTHLY', FALSE, 'Inspection of food\nDetermination of parameters\nCreation of tasteless narcotic', 'Zagreb', 'R', 'B', 'FOOD_SCIENCE', '', 14, 'SHARED');

INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(9, 'DE-2013-0010-BE', '930e1616-b214-499c-9531-b833fcc2202b', FALSE, 'EUR', 38.5, 7.7, 'see homepage', 'Ludwig Maximilian University of Munich', 'Geschwister-Scholl-Platz 1', '80539 Munich', 'University', 15000, 'www.en.uni-muenchen.de', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 400, 'MONTHLY', 'IAESTE', 200, 'MONTHLY', 6, 12, 'BER', 'U3/U6 Universität', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Experience in JAVA', 700.00, 'MONTHLY', FALSE, 'Working on a project in the field of science to visualize potential threads to economy and counter fight decreasing numbers', 'Berlin', 'R', 'B', 'IT|MATHEMATICS', 'BUSINESS_INFORMATICS', 20, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(10, 'DE-2013-0020-BE', '0ec09a52-bfe5-4d4b-b598-e873fcf25cb3', FALSE, 'EUR', 38.5, 7.7, 'see homepage', 'Ludwig Maximilian University of Munich', 'Geschwister-Scholl-Platz 1', '80539 Munich', 'University', 15000, 'www.en.uni-muenchen.de', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'O', 'POLISH', 'F', 400, 'MONTHLY', 'IAESTE', 200, 'MONTHLY', 6, 12, 'BER', 'U3/U6 Universität', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), '', 700.00, 'MONTHLY', FALSE, 'Work on a simulation of nicotine addiction on monkeys', 'Berlin', 'R', 'B', 'IT|CHEMISTRY', 'BUSINESS_INFORMATICS', 20, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(11, 'DE-2013-0030-BE', '267a0959-06fb-4ffa-be8f-c7fb38ee8c1b', FALSE, 'EUR', 38.5, 7.7, 'see homepage', 'Ludwig Maximilian University of Munich', 'Geschwister-Scholl-Platz 1', '80539 Munich', 'University', 15000, 'www.en.uni-muenchen.de', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', 'A', 'POLISH', 'F', 400, 'MONTHLY', 'IAESTE', 200, 'MONTHLY', 6, 12, 'BER', 'U3/U6 Universität', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Has built three houses', 700.00, 'MONTHLY', FALSE, 'World first dog hotel', 'Berlin', 'R', 'B', 'CIVIL_ENGINEERING', '', 20, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(12, 'DE-2013-0040-BE', 'b2b3356c-5b41-474f-a3a1-ef27f0d107d3', FALSE, 'EUR', 38.5, 7.7, 'see homepage', 'Ludwig Maximilian University of Munich', 'Geschwister-Scholl-Platz 1', '80539 Munich', 'University', 15000, 'www.en.uni-muenchen.de', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'GERMAN', 'G', NULL, NULL, NULL, 400, 'MONTHLY', 'IAESTE', 200, 'MONTHLY', 6, 12, 'BER', 'U3/U6 Universität', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Good tongue and great appetite', 700.00, 'MONTHLY', FALSE, 'Inspection of food\nDetermination of parameters\nCreation of tasteless narcotic', 'Berlin', 'R', 'B', 'FOOD_SCIENCE', '', 20, 'SHARED');

INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(13, 'PL-2013-0010-WZ', '60aeb42d-471b-43e8-8e5c-1e7f1573d9ea', FALSE, 'PLN', 35.5, 7.7, '0', 'University of Warsaw', 'Krakowskie Przedmieście 26/28', '00-927 Warsaw', 'University', 8000, 'www.uw.edu.pl/en/', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 800, 'MONTHLY', 'IAESTE', 500, 'MONTHLY', 6, 10, 'WAW', 'Metro Świętokrzyska', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Experience in JAVA', 1400.00, 'MONTHLY', FALSE, 'Working on a project in the field of science to visualize potential threads to economy and counter fight decreasing numbers', 'Warsaw', 'R', 'B', 'IT|MATHEMATICS', 'BUSINESS_INFORMATICS', 22, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(14, 'PL-2013-0020-WS', '09123feb-6f0e-44cc-bf62-749b243d3d5d', FALSE, 'PLN', 35.5, 7.7, '0', 'University of Warsaw', 'Krakowskie Przedmieście 26/28', '00-927 Warsaw', 'University', 8000, 'www.uw.edu.pl/en/', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'RUSSIAN', 'G', 'O', 'POLISH', 'F', 800, 'MONTHLY', 'IAESTE', 500, 'MONTHLY', 6, 10, 'WAW', 'Metro Świętokrzyska', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), '', 1400.00, 'MONTHLY', FALSE, 'Work on a simulation of nicotine addiction on monkeys', 'Warsaw', 'R', 'B', 'IT|CHEMISTRY', 'BUSINESS_INFORMATICS', 22, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(15, 'PL-2013-0030-WZ', '97e2ffab-7e44-4b87-a894-bb3b59a7a299', FALSE, 'PLN', 35.5, 7.7, '0', 'University of Warsaw', 'Krakowskie Przedmieście 26/28', '00-927 Warsaw', 'University', 8000, 'www.uw.edu.pl/en/', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'RUSSIAN', 'G', 'A', 'POLISH', 'F', 800, 'MONTHLY', 'IAESTE', 500, 'MONTHLY', 6, 10, 'WAW', 'Metro Świętokrzyska', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Has built three houses', 1400.00, 'MONTHLY', FALSE, 'World first dog hotel', 'Warsaw', 'R', 'B', 'CIVIL_ENGINEERING', '', 22, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(16, 'PL-2013-0040-WS', '353bc1bf-2ac3-429d-9c9e-534dec476aa9', FALSE, 'PLN', 35.5, 7.7, '0', 'University of Warsaw', 'Krakowskie Przedmieście 26/28', '00-927 Warsaw', 'University', 8000, 'www.uw.edu.pl/en/', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'RUSSIAN', 'G', NULL, NULL, NULL, 800, 'MONTHLY', 'IAESTE', 500, 'MONTHLY', 6, 10, 'WAW', 'Metro Świętokrzyska', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Good tongue and great appetite', 1400.00, 'MONTHLY', FALSE, 'Inspection of food\nDetermination of parameters\nCreation of tasteless narcotic', 'Warsaw', 'R', 'B', 'FOOD_SCIENCE', '', 22, 'SHARED');

INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(17, 'HU-2013-0001', 'e53bc2bf-7ae6-4b62-8964-c7a4d9f47a1b', FALSE, 'HUF', 35.5, 7.7, '0', 'Budapest University of Technology and Economics', 'Műegyetem rkp. 3-9.', 'H-1111 Budapest', 'University', 10000, 'english.www.bme.hu', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 2000, 'MONTHLY', 'IAESTE', 1000, 'MONTHLY', 6, 10, 'BUD', 'Szent Gellért tér', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Experience in JAVA', 3500.00, 'MONTHLY', FALSE, 'Working on a project in the field of science to visualize potential threads to economy and counter fight decreasing numbers', 'Budapest', 'R', 'B', 'IT|MATHEMATICS', 'BUSINESS_INFORMATICS', 25, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(18, 'HU-2013-0002', '3c1ff1b3-1f5e-44b5-9c33-6b7398c25d3d', FALSE, 'HUF', 35.5, 7.7, '0', 'Budapest University of Technology and Economics', 'Műegyetem rkp. 3-9.', 'H-1111 Budapest', 'University', 10000, 'english.www.bme.hu', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'HUNGARIAN', 'G', 'O', 'POLISH', 'F', 2000, 'MONTHLY', 'IAESTE', 1000, 'MONTHLY', 6, 10, 'BUD', 'Szent Gellért tér', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), '', 3500.00, 'MONTHLY', FALSE, 'Work on a simulation of nicotine addiction on monkeys', 'Budapest', 'R', 'B', 'IT|CHEMISTRY', 'BUSINESS_INFORMATICS', 25, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(19, 'HU-2013-0003', 'cab649c0-470d-493e-a837-2355fe755ba9', FALSE, 'HUF', 35.5, 7.7, '0', 'Budapest University of Technology and Economics', 'Műegyetem rkp. 3-9.', 'H-1111 Budapest', 'University', 10000, 'english.www.bme.hu', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'HUNGARIAN', 'G', 'A', 'POLISH', 'F', 2000, 'MONTHLY', 'IAESTE', 1000, 'MONTHLY', 6, 10, 'BUD', 'Szent Gellért tér', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Has built three houses', 3500.00, 'MONTHLY', FALSE, 'World first dog hotel', 'Budapest', 'R', 'B', 'CIVIL_ENGINEERING', '', 25, 'SHARED');
INSERT INTO offers (id, ref_no, external_id, canteen, currency, weekly_hours, daily_hours, deduction, employer_name, employer_address, employer_address_2, employer_business, employer_employees_cnt, employer_website, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nearest_airport, nearest_pub_transport, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, working_place, work_type, study_levels, study_fields, specializations, group_id, status) VALUES
(20, 'HU-0013-0004', '3482517b-19ce-40e4-a607-982b1618cfa1', FALSE, 'HUF', 35.5, 7.7, '0', 'Budapest University of Technology and Economics', 'Műegyetem rkp. 3-9.', 'H-1111 Budapest', 'University', 10000, 'english.www.bme.hu', '2013-06-01', '2013-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', 'A', 'HUNGARIAN', 'G', NULL, NULL, NULL, 2000, 'MONTHLY', 'IAESTE', 1000, 'MONTHLY', 6, 10, 'BUD', 'Szent Gellért tér', DATE_ADD ( TODAY(), INTERVAL 3 MONTH ), 'Good tongue and great appetite', 3500.00, 'MONTHLY', FALSE, 'Inspection of food\nDetermination of parameters\nCreation of tasteless narcotic', 'Budapest', 'R', 'B', 'FOOD_SCIENCE', '', 25, 'SHARED');

INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (1, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (1, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (1, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (1, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (2, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (2, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (2, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (2, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (3, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (3, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (3, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (3, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (4, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (4, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (4, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (4, 25, 7, 7);

INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (5, 11, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (5, 20, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (5, 22, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (5, 25, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (6, 10, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (6, 20, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (6, 22, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (6, 25, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (7, 11, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (7, 20, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (7, 22, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (7, 25, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (8, 11, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (8, 20, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (8, 22, 22, 22);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (8, 25, 22, 22);

INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (9, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (9, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (9, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (9, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (10, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (10, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (10, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (10, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (11, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (11, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (11, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (11, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (12, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (12, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (12, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (12, 25, 7, 7);

INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (13, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (13, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (13, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (13, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (14, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (14, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (14, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (14, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (15, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (15, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (15, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (15, 25, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (16, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (16, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (16, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (16, 25, 7, 7);

INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (17, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (17, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (17, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (17, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (18, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (18, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (18, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (18, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (19, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (19, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (19, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (19, 22, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (20, 11, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (20, 14, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (20, 20, 7, 7);
INSERT INTO offer_to_group (offer_id, group_id, modified_by, created_by) VALUES (20, 22, 7, 7);
