-- ============================================================================
-- Preparing to create test data for users and groups.
-- ============================================================================

delete from user_to_group where user_id > 1000 or group_id > 1000;
delete from sessions where user_id > 1000;
delete from history where user_id > 1000;
delete from students where user_id > 1000;
delete from users where id > 1000;
delete from groups where id > 1000;
delete from countries where id > 1000;
alter sequence country_sequence restart with 1062;
alter sequence group_sequence restart with 1132;
alter sequence user_sequence restart with 1062;

insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('NV', 'Neverland', 'Neverland', 'USD', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values
  ('4e878cbb-f108-4ac4-b422-ba0ed3ea9253', 2, null, 1062, 'Neverland'),
  ('4b43dd02-f341-4578-94a5-7a93928ecee8', 4, 1132, 1062, 'Neverland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values
  ('782a3fa3-b653-4dec-943b-4f89cd4c3ac3', 'ACTIVE', 'neverland@iaeste.nv', 'NS.Neverland@iaeste.org', 'fe1ade81bf65e13fa965f65ae1e012fcaa059621d70391e07f9c011846ec6699', 'c26b14f3-ca89-4007-b103-e810caf30378', 'NS', 'Neverland');
insert into user_to_group (external_id, user_id, group_id, role_id) values
  ('45382bd6-39e2-45c5-bda0-5ae1fbb270e8', 1062, 1132, 1),
  ('02df4174-54f0-4363-888a-1c5c23342ba9', 1062, 1133, 1);

-- starting sequence where they finished in script 90
alter sequence country_sequence restart with 1088;
alter sequence group_sequence restart with 1182;
alter sequence user_sequence restart with 1088;

-- User1, NS (Owner Staff & Member Groups)
-- User2, Staff member (Moderator Staff & Member Groups)
-- User3, LC (Owner LC Group, Moderator Member Group)
-- User4, LC (Member LC, & Member Group)
-- User5, Student (Member Student & Member of Member Group)
-- Additionally, add a Workgroup under the Staff, Member group & LC. Where user 1-4 are present in all three.

insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name, full_name) values
  ('5f88dd38-4aa6-4146-bb1e-a1ef1b074323', 5, 1132, 1062, 'City', 'neverland.city'), -- id=182

  ('d5083fd8-9c85-4754-8fe4-c1ab61478542', 6, 1132, 1062, 'Fund Raising', 'neverland.fund raising'),
  ('27a61e49-afbe-4657-bfd0-181a1acbf80e', 6, 1133, 1062, 'Exchange', 'neverland.exchange'),
  ('83ecaa6e-1d54-4e46-bcd2-ecf75bb8d132', 6, 1182, 1062, 'IT', 'neverland.city.it'),

  ('a5a51fa4-93f3-4d73-b417-49cac26e558f', 7, 1182, 1062, 'Students', 'neverland.city.students');

UPDATE groups
  SET list_name = REPLACE(full_name, ' ', '_')
  WHERE id > 1000;

-- Polish Test users, the NS is defined in the initial-test-data script
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values
  ('de67a0d3-fdce-4df2-a490-dc8a95416b99', 'ACTIVE', 'moderator@iaeste.nv', 'moderator.neverland@iaeste.org', '', '', 'moderator', 'Neverland'), -- id=88
  ('45108e76-f0b7-49ee-9554-59304231a02e', 'ACTIVE', 'owner@city.iaeste.nv', 'owner.city@iaeste.org', '', '', 'owner', 'City'),
  ('2e563a3a-8dc7-4c1d-b660-637e7623f68a', 'ACTIVE', 'member@city.iaeste.nv', 'member.city@iaeste.org', '', '', 'member', 'City'),
  ('a91e570a-654e-46b2-be58-8b6dbfe1d106', 'ACTIVE', 'student@city.iaeste.nv', 'student.city@iaeste.org', '', '', 'member', 'City');

update users set password = 'fe1ade81bf65e13fa965f65ae1e012fcaa059621d70391e07f9c011846ec6699', salt = 'c26b14f3-ca89-4007-b103-e810caf30378' where id >= 1088;

insert into students (user_id) values (1090);

insert into user_to_group (external_id, user_id, group_id, role_id) values
-- NS
  ('02cb2ee0-da80-4bd9-b487-dadd9e019305', 1062, 1183, 3),
  ('99c3d6fc-7ad5-4fb4-872f-80f80d37d294', 1062, 1184, 3),
  ('a0586b77-0d68-4854-b549-7d9192cb72b6', 1062, 1185, 3), -- can NS be a member of LC workgroup if he is not a member of LC?
-- staff member
  ('6b7fb67e-a56c-4beb-85eb-6d23f755bd53', 1088, 1132, 2),
  ('59ba0d75-797d-4c9a-930b-ca95bf12e9c6', 1088, 1133, 2),

  ('1ad4d5aa-3e3b-40ed-afc9-27f930a79397', 1088, 1183, 1),
  ('09ae4d15-6c9e-4f58-9ad4-ed7d6794f9d1', 1088, 1184, 1),
  ('0fb9ef58-76da-40bb-b75c-0646fc9a388d', 1088, 1185, 3), -- can National Staff Member be a member of LC workgroup if he is not a member of LC?
-- LC Owner
  ('6f072599-f93d-4830-8155-d56ec40d9fd1', 1089, 1182, 1),
  ('ac129a1c-ea14-432f-a86c-d3062e648ab0', 1089, 1132, 2),

  ('f6db70ea-84d5-43c2-ac2c-7721f8341192', 1089, 1183, 3),
  ('c13c1ea2-1185-4849-864c-60f4556def81', 1089, 1184, 2),
  ('90e15a7e-0fbc-4f9d-bddf-123d8a6df9e8', 1089, 1185, 3),
-- LC Member
  ('657d93dd-65ce-4499-b286-e768c740f184', 1090, 1132, 3),
  ('248eac0e-cbf5-47cb-a0e8-79c7a075c4c4', 1090, 1182, 3),

  ('b87b4180-571c-4360-95ea-bba8e25e4265', 1090, 1183, 3),
  ('24bda347-1406-4561-b2af-3d8ebed7efd3', 1090, 1184, 3),
  ('a40b2656-cdb4-4d4c-8f26-a3ef611e8d1e', 1090, 1185, 1),
-- Student
  ('abba8788-51f2-46e8-abd7-1c0f7f35a0e6', 1091, 1132, 5),
  ('fab4a5bd-ab38-4631-9311-ec5d0f145573', 1091, 1186, 5), -- should it be role student or member in student group?
  ('f840f9dd-1d03-4577-9c69-0a40a8d499bd', 1091, 1182, 5); -- can student be a student in LC?


-- create private groups for all users
INSERT INTO groups (external_id, grouptype_id, parent_id, country_id, group_name, full_name) VALUES
  ('b0e29d66-be2b-4ac1-a8bc-2ab0a10ab239', 1, NULL, 62, 'poland@iaeste.pl', 'poland@iaeste.pl'),
  ('8d524242-eeaf-4939-839e-cd9a8e2bdb31', 1, NULL, 62, 'neverland@iaeste.nv', 'neverland@iaeste.nv'),
  ('5d29b5c0-5fa1-48db-b954-3a7afdf1831d', 1, NULL, 62, 'moderator@iaeste.nv', 'moderator@iaeste.nv'),
  ('53e92a35-090a-4028-9d4e-2a1ec8f8a1e5', 1, NULL, 62, 'owner@city.iaeste.nv', 'owner@city.iaeste.nv'),
  ('9a4a720c-d76a-439a-bb87-b39d07dd031e', 1, NULL, 62, 'member@city.iaeste.nv', 'member@city.iaeste.nv'),
  ('e29434e1-13a7-440b-a7fb-23faa715b60a', 1, NULL, 62, 'student@city.iaeste.nv', 'student@city.iaeste.nv');

INSERT INTO user_to_group (external_id, user_id, group_id, role_id) VALUES
  ('fe40d0c9-1246-4797-b603-228813de1aac', 1062, 1188, 1),
  ('95840c2d-6411-46e1-a5b9-ba60ffb66999', 1088, 1189, 1),
  ('34dbf894-90a9-41ac-97de-f897228c78f1', 1089, 1190, 1),
  ('f87de124-d131-4f55-9c02-88d1495d0188', 1090, 1191, 1),
  ('721918c7-bec5-470d-9a96-690a32a5606f', 1091, 1192, 1);
-- select * from view_user_group order by username, groupname;

-- Generating Offer Test data for Neverland
insert into addresses (id, street1, street2, postal_code, city, state, pobox, country_id) values
  (1062, 'Pixie Hollow 13', '1040 Neverwood', 'x', 'x', 'x', 'x', 1062);
insert into employers (id, external_id, group_id, name, department, business, working_place, number_of_employees, website, canteen, nearest_airport, nearest_public_transport, weekly_hours, address_id ) values
  (1062, '65665cc4-cc43-440f-bd09-a32e955bac3d', 1133, 'University of Technology', 'University', 'University', 'Neverwood', 9000, 'www.awesome.nv', FALSE, 'NVL', 'Neverwood', 38.5, 1062);
insert into offers (id, ref_no, external_id, employer_id, currency, status, exchange_year, from_date, to_date, from_date_2, to_date_2, unavailable_from, unavailable_to, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level, living_cost, living_cost_frequency, lodging_by, lodging_cost, lodging_cost_frequency, min_weeks, max_weeks, nomination_deadline, other_requirements, payment, payment_frequency, prev_training_req, work_description, work_type, study_levels, study_fields, specializations, deduction) values
  (1062, 'NV-2014-000001', '122c4216-197a-4112-b429-2e1e79f4fd6c', 1062, 'PLN', 'SHARED', 2014, '2014-06-01', '2014-09-30', NULL, NULL, NULL, NULL, 'ENGLISH', 'E', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'MONTHLY', 'IAESTE', 300, 'MONTHLY', 6, 12, CURRENT_DATE + INTERVAL '0-3' YEAR TO MONTH, 'Experience in JAVA', 1250.00, 'MONTHLY', FALSE, 'Working on a project in the field of science to visualize potential threads to economy and counter fight decreasing numbers', 'R', 'B', 'IT|MATHEMATICS', 'BUSINESS_INFORMATICS', 'approx. 30');
