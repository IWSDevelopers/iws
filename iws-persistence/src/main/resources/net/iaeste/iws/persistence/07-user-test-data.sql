-- Country Data
insert into countries (country_code, country_name, country_name_full, currency, member_since) values ('AT', 'Austria', 'Austria', 'EUR', 1960);
insert into countries (country_code, country_name, country_name_full, currency, member_since) values ('HR', 'Croatia', 'Croatia', 'HRK', 1990);
insert into countries (country_code, country_name, country_name_full, currency, member_since) values ('DK', 'Denmark', 'Denmark', 'DKK', 1948);
insert into countries (country_code, country_name, country_name_full, currency, member_since) values ('DE', 'Germany', 'Germany', 'EUR', 1970);
insert into countries (country_code, country_name, country_name_full, currency, member_since) values ('PL', 'Poland',  'Poland',  'PLN', 1990);
insert into countries (country_code, country_name, country_name_full, currency, member_since) values ('HU', 'Hungary', 'Hungary', 'HUF', 1990);

-- Couple of Member Groups, our Sequence starts with 25, so we only allow a limitted amount of test data
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (10, '2cc7e1bb-01e8-43a2-9643-2e964cbd41c5', 2, null, 1, 'Austria');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (11, 'c7b15f81-4f83-48e8-9ffb-9e73255f5e5e', 5,   10, 1, 'Austria');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (12, '278803ac-e21d-4bfc-8641-506a4b642114', 9,   10, 1, 'Students');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (13, 'adc8dfd4-bc3a-4b27-897b-87d3950db503', 2, null, 2, 'Croatia');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (14, '0cad3aa9-dc44-444d-be48-ec77a3cafef7', 5,   13, 2, 'Croatia');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (15, 'a0e3558e-9d21-49df-ab63-b54e80d72308', 9,   13, 2, 'Students');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (16, '3adbeb2b-05c0-456e-8809-1d1e4743f2c1', 2, null, 3, 'Denmark');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (17, '6f54e025-a703-41e3-b1ff-796a9f8ad04e', 5,   16, 3, 'Denmark');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (18, '7f16a4b0-eacd-4aa0-8526-ed2228b4b35b', 9,   16, 3, 'Students');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (19, 'f1a223ee-7564-4ea4-b2fe-040e289d1f50', 2, null, 4, 'Germany');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (20, '17eb00ac-1386-4852-9934-e3dce3f57c13', 5,   19, 4, 'Germany');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (21, 'fe4cf522-1d77-42ae-835d-023af948537b', 9,   19, 4, 'Students');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (22, 'f36c1de6-e3ae-46da-83f4-f8259486dcf0', 2, null, 5, 'Poland');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (23, 'e60f9897-864b-4d1b-9c1a-1681fd35e97a', 5,   22, 5, 'Poland');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (24, '98c792d4-4bf6-4348-9cbd-14f0d7d92c79', 9,   22, 5, 'Students');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (25, 'b28aa7c1-4a39-4879-a881-13e130624857', 2, null, 6, 'Hungary');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (26, '3b4bdadc-f157-4362-a370-c2191adfd86a', 5,   25, 6, 'Hungary');
insert into Groups (id, external_id, grouptype_id, parent_id, country_id, groupName) values (27, '62fcf733-c905-4b64-806f-7ea341d93349', 9,   25, 6, 'Students');

-- Couple of Users, password is the same as the username
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c50aaeec-c0de-425a-b487-4530cbfbe115', 'ACTIVE', 'austria@iaeste.at', 'austria.ns@iaeste.org', '412d590bfb92c5a71462ce818b88b527d16dbf6a32d7b820e6550e70442ac5d5', 'a360ff7d-6fa5-47c9-81ff-fdcef835297d', 'NS', 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('7a477f34-92ca-4284-b16e-12d67fdadc6d', 'ACTIVE', 'croatia@iaeste.hr', 'croatia.ns@iaeste.org', 'dfef6b7ea492a5096299aed5e4115056fbaccd1d1b5b21fd20007d45f1a1e9a1', '70f0f9c8-4ed7-4d96-9f9b-8988b069ae55', 'NS', 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1c2e4950-feba-4e5c-84eb-6ee27b6489f5', 'ACTIVE', 'denmark@iaeste.dk', 'denmark.ns@iaeste.org', '7361308b08e8e96599c99493388a6b7ddfc851ade0fe156657e702a4eb690287', 'c693847f-1127-4202-a18f-726850e73808', 'NS', 'Denmark');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d1840955-bd5c-421d-961f-a2da6bc97e1d', 'ACTIVE', 'germany@iaeste.de', 'germany.ns@iaeste.org', '7629e14eed100a612a996fb83cdd18186dd8cc5f7a4557dbb7c4e7196ffeadd0', 'c06b3c1e-4d15-40b0-9189-bb26d0ccbef5', 'NS', 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('157c6ebd-66c2-4208-b755-5287a743a608', 'ACTIVE', 'poland@iaeste.pl',  'poland.ns@iaeste.org',  '6ba3b9ab775bc9654e7924870d1079f723c79d8865da0b4598152e77dd4a8edd', '11e8fdab-ae38-4f0e-a7ec-7411fdaf7e0b', 'NS', 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('da1047b3-94f3-46ed-afe3-923fea3a85cd', 'ACTIVE', 'hungary@iaeste.hu', 'hungary.ns@iaeste.org', '48c90821abcc3d8449144ae7ad8a8aebbca15738c44244b5b386b5d5cad3c1b9', '03b870f5-e81b-42f3-9fed-7a4b9366a620', 'NS', 'Hungary');

-- User Group Associations
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6daebd2d-39a2-4855-9785-7c216997938b', 1, 10, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8964c454-fdd0-41c4-8d47-775af2cd9372', 1, 11, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0c87272b-a695-4c20-a35a-1a22739a35a1', 1, 12, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('7296f97d-f95e-429e-9066-3a2f7a5bc666', 2, 13, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3aa4756f-e092-4dbc-ab3b-288c20dbe357', 2, 14, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0f592354-94c9-4652-85a1-668cb828941c', 2, 15, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6901fb7a-19d6-474a-a775-728b0d1f82e2', 3, 16, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bba1c936-561a-441b-90a2-aae5ead1e09d', 3, 17, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('73a4a2c0-3923-40a9-bd9b-cb8590a1db45', 3, 18, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a9b38a61-6905-4909-8cc9-0146b448418c', 4, 19, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('23bdbfa4-2634-4d14-80fa-22b125f25fde', 4, 20, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('d378ceb3-2cef-47c3-9f85-c081498fcc28', 4, 21, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a8fde4ae-c8f4-472f-b99f-8e2ec0acd8ea', 5, 22, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6de59f31-e9e6-4e49-bfec-16b05fa07e69', 5, 23, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6e405601-2d43-4835-82b4-fd0c0f208a00', 5, 24, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ff540413-3175-476f-be30-963cc914ad94', 6, 25, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('af81868c-7e22-4dc9-9076-69d19e95912e', 6, 26, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('324c1727-a6e3-4b16-a381-44bf5d22764b', 6, 27, 1);
