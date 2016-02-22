-- ============================================================================
-- Preparing to create test data for users.
-- ============================================================================

-- First reset the existing tables & sequences, regardless!
delete from user_to_group;
delete from sessions;
delete from history;
delete from users;
delete from groups where id>= 10;
delete from countries;
alter sequence country_sequence restart with 1;
alter sequence group_sequence restart with 10;
alter sequence user_sequence restart with 1;

-- Generating Test data for Albania
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AL', 'Albania', 'Albania', 'ALL', 2013, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('6a63dfd3-7dd7-4eb6-a209-c57447a56393', 2, null, 1, 'Albania');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('7b603907-0730-4d2d-9e97-0e1fe94f1ade', 4, 10, 1, 'Albania');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('659f4642-4d6d-4feb-9275-89c3ce401782', 'ACTIVE', 'albania@iaeste.al', 'NS.Albania@iaeste.org', '194ce8eba0a7110da654fa02d3d67212b17cc4811539aee12782e108b6468fdf', '4cbd6e2f-d802-425e-9184-f159b0d419d1', 'NS', 'Albania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e34f0917-9ee4-4ec0-a416-0d6a694dcf82', 1, 10, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fb3296de-250f-43ee-a352-76a95f4fbb7a', 1, 11, 1);
-- Completed generating test data for Albania

-- Generating Test data for Argentina
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AR', 'Argentina', 'Argentina', 'ARS', 1961, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('902cf65a-4220-4428-8167-d6695c9b84d9', 2, null, 2, 'Argentina');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('125041b8-d3f5-48ba-84e6-c3cc0e83a86a', 4, 12, 2, 'Argentina');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('75a5de01-6bfa-4798-aa21-e088d2e34e61', 'ACTIVE', 'argentina@iaeste.ar', 'NS.Argentina@iaeste.org', '79a80aefc9244fe789f5874912817b577bdfea462fd11b0069ec9fad4abf686c', '87d600eb-64b6-40df-854e-4a55d7a61fe6', 'NS', 'Argentina');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('54e1a3b1-1854-4455-8e37-12418b51235d', 2, 12, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e180cd2e-9c52-4b23-801f-b21023a59663', 2, 13, 1);
-- Completed generating test data for Argentina

-- Generating Test data for Australia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AU', 'Australia', 'Australia', 'AUD', 1996, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1f6fc75f-f8af-433c-bf9e-bfecb6970239', 2, null, 3, 'Australia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('dc2eff97-c6ef-4915-b2c8-9bde039c62ca', 4, 14, 3, 'Australia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1fc09f85-2e93-4612-bc42-78a4f202b535', 'ACTIVE', 'australia@iaeste.au', 'NS.Australia@iaeste.org', 'a964534a2ebdad6278ec8b255bcb66f7da2f7c85bcccf37e990848ddb02a31ef', '678d52a2-1642-444b-b4a7-a5dad6fd12d1', 'NS', 'Australia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('835f79bc-0f73-48d7-b23d-b57c2d74fa2d', 3, 14, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('467f693c-1033-4104-8a3b-4179e322a0bc', 3, 15, 1);
-- Completed generating test data for Australia

-- Generating Test data for Austria
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AT', 'Austria', 'Austria', 'EUR', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('da799505-cbf4-4bb8-bbcc-7e7705a7892f', 2, null, 4, 'Austria');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9f2c4db6-38c9-4a2f-bdaf-141bd1eb4c13', 4, 16, 4, 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('47e241b6-1bde-44e3-b886-7e81cd3cfbcb', 'ACTIVE', 'austria@iaeste.at', 'NS.Austria@iaeste.org', '707cfe78d687e92e3a1695cf3782cdb61b1bfe19126231ed5e88d6f48809d614', 'b335a0d8-e7fc-4c0c-b9a7-5ea892b9f4ce', 'NS', 'Austria');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a1a3184e-0ca7-4446-b7ef-aff14f3403aa', 4, 16, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e1fe2021-798f-427b-8363-35c53af4ca1b', 4, 17, 1);
-- Completed generating test data for Austria

-- Generating Test data for Azerbaijan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AZ', 'Azerbaijan', 'Azerbaijan', 'AZN', 2010, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c64483d8-2f73-48bc-8030-676e28e83323', 2, null, 4, 'Azerbaijan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('df404581-d167-4c93-8d3f-9c71e71a8e92', 4, 18, 4, 'Azerbaijan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('464b3e13-adab-40f5-9840-0d3eee33a9d5', 'ACTIVE', 'azerbaijan@iaeste.az', 'NS.Azerbaijan@iaeste.org', 'e48e4795b75b43fdeb4f29532b5495ea688460305ed8eab2053770d362c06352', '3f8cec9d-2092-454b-9fe3-c1dae126ebec', 'NS', 'Azerbaijan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('05fc1b8a-7f6b-4b0f-84e8-792bbbfacacc', 5, 18, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6a5894aa-5f91-433c-8524-8382d2f104ed', 5, 19, 1);
-- Completed generating test data for Azerbaijan

-- Generating Test data for Bangladesh.CAT
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BD', 'Bangladesh.CAT', 'Bangladesh.CAT', 'BDT', 2013, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('44c9edd1-86d1-495a-876b-95b8c6c322c8', 2, null, 6, 'Bangladesh.CAT');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f2de0d7a-e502-49ea-afe1-308884d5f22f', 4, 20, 6, 'Bangladesh.CAT');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('82188175-558e-4bd4-b979-ec785722e0a5', 'ACTIVE', 'bangladesh.cat@iaeste.bd', 'NS.Bangladesh.CAT@iaeste.org', '9ba2adae30333b43a883b54678af7b2b4582089eb132533f5dcfcb8836758d3d', 'f0ff942a-7e9b-4dd0-8c48-528e07cc48cf', 'NS', 'Bangladesh.CAT');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('91554e70-1004-470a-9f13-98fb6ba89725', 6, 20, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('94a23a9e-630d-4828-8e95-7981bdf11db0', 6, 21, 1);
-- Completed generating test data for Bangladesh.Aviation

-- Generating Test data for Belarus
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BY', 'Belarus', 'Belarus', 'BYR', 2000, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('fea5b4ff-58a5-4d9b-a981-7f9520d9f488', 2, null, 7, 'Belarus');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('da122a82-b195-4746-9652-5d9323f4a1f6', 4, 22, 7, 'Belarus');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('9fb67c89-e278-4224-84fb-5421f82fc21a', 'ACTIVE', 'belarus@iaeste.by', 'NS.Belarus@iaeste.org', '8e64cbf61a37819875b70cbf1c8ff3753340f8f9a882d154acd525a143f393ea', '731d4b1c-15a2-4005-9e59-60f3fd95e772', 'NS', 'Belarus');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cbeb51df-c8df-43d8-b272-f557408366da', 7, 22, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('08434b07-2d09-40ce-8283-c1480f00eb69', 7, 23, 1);
-- Completed generating test data for Belarus

-- Generating Test data for Belgium
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BE', 'Belgium', 'Belgium', 'EUR', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e375c184-dd9b-422d-ad26-7e03a3ba7dca', 2, null, 8, 'Belgium');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('02b5ddc0-541f-47eb-ae77-6d3b9a33f2a9', 4, 24, 8, 'Belgium');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('f742efb4-252f-44ca-8426-cbf427fde5ae', 'ACTIVE', 'belgium@iaeste.be', 'NS.Belgium@iaeste.org', '8fdcce7e6244f87b3cbe8269dca0baf11b78d182c7b3c1bde8a9ae6b2ab66eda', '08b2949f-3e18-4576-b889-8e183aa00eb4', 'NS', 'Belgium');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('586ad291-74c0-4f52-9031-cf653e526e17', 8, 24, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('329cefbe-9f6a-4afa-81d8-a55f6318079d', 8, 25, 1);
-- Completed generating test data for Belgium

-- Generating Test data for Bolivia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BO', 'Bolivia', 'Bolivia', 'BOB', 2013, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a38b52a0-d05b-4bbb-a794-1ac5a17a04c1', 2, null, 9, 'Bolivia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('cac42b77-f69a-4e0e-966f-588a4960d231', 4, 26, 9, 'Bolivia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a6de553a-f9fb-418b-b8eb-2d7d63c8b0aa', 'ACTIVE', 'bolivia@iaeste.bo', 'NS.Bolivia@iaeste.org', 'ea575f506f58f169cde9913b34a314375288055c1ebd83cfcd0979b715cc1fb3', 'eeecfa9c-be17-4c8d-995a-d41aa986a9df', 'NS', 'Bolivia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ca4faa6f-1b7a-4395-81a6-4f83df41a8a3', 9, 26, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('932ad701-055c-4a8c-aa92-8eee430617db', 9, 27, 1);
-- Completed generating test data for Bolivia

-- Generating Test data for Bosnia and Herzegovina
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BA', 'BosniaandHerzegovina', 'BosniaandHerzegovina', 'BAM', 2000, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b11dea53-4350-4353-8008-27052e276917', 2, null, 10, 'BosniaandHerzegovina');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9da0c7b2-f3fa-40c8-89e9-126bb677a514', 4, 28, 10, 'BosniaandHerzegovina');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('518e9c03-c096-4256-8f93-87800c89d148', 'ACTIVE', 'bosniaandherzegovina@iaeste.ba', 'NS.BosniaAndHerzegovina@iaeste.org', 'bcaa147c9ecb543503ee677d193c5c63aa72ef8952fd2c560db829d367231c04', '88563008-58f4-4868-9afd-7dc3776776e9', 'NS', 'BosniaAndHerzegovina');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('97cb85b2-4c53-4beb-83ab-963568771e3b', 10, 28, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('65c1a98b-07f5-47fd-9627-c547b8bbda5c', 10, 29, 1);
-- Completed generating test data for Bosnia and Herzegovina

-- Generating Test data for Brazil
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BR', 'Brazil', 'Brazil', 'BRL', 1982, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1dd2f402-7fbc-4ca2-9e46-036edd95b914', 2, null, 11, 'Brazil');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('54523d72-a048-4e7b-8a49-d615377fc0f7', 4, 30, 11, 'Brazil');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('57243110-7e4d-4bf1-94c7-045f8b90c998', 'ACTIVE', 'brazil@iaeste.br', 'NS.Brazil@iaeste.org', '2e32539e1be6c8edee062e8efdc16856e7695053c6d699e1e4a6a996a1463b7d', 'f91afd2d-c637-4ab4-a1df-3d63838aa7e3', 'NS', 'Brazil');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('17a6b95e-2c51-4f7e-8f06-4219497b618a', 11, 30, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e0e644c1-de37-467d-8f0e-1f360668ea77', 11, 31, 1);
-- Manually added, we need to have someone who's working with International Groups
insert into user_to_group (external_id, user_id, group_id, role_id) values ('dd6635da-2483-433c-9663-8b7ab55fba82', 11,  3, 1);
-- Completed generating test data for Brazil

-- Generating Test data for Canada
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CA', 'Canada', 'Canada', 'CAD', 1953, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9fcebe71-d3fa-426e-b5ed-97d0cd4d4582', 2, null, 12, 'Canada');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('be543ebb-5052-4748-9542-4908f5769d25', 4, 32, 12, 'Canada');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('364f7ce3-7432-4493-bb19-cdea5593bf88', 'ACTIVE', 'canada@iaeste.ca', 'NS.Canada@iaeste.org', 'd42c2449c7a53934af34e2bb0775e38d2af586320d1bdcec9652b7ce77160851', 'e5b3473c-696b-41fb-9850-1761f7c5389b', 'NS', 'Canada');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('06a4e199-cb44-41e4-8a9f-b53995f7940f', 12, 32, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1402c121-c438-4894-acc2-7ffe9ad3ba4e', 12, 33, 1);
-- Completed generating test data for Canada

-- Generating Test data for Chile
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CL', 'Chile', 'Chile', 'CLP', 2013, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('47d78e60-4370-4533-b936-152fc29698c5', 2, null, 13, 'Chile');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d28f856d-8b8c-4288-a193-a7dd4a2490a9', 4, 34, 13, 'Chile');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c2e4c509-b621-44e7-a460-e17ec2a539a1', 'ACTIVE', 'chile@iaeste.cl', 'NS.Chile@iaeste.org', 'c174f17e24c0d6866a32b30ffc8202a2adb0e28f8cd59705a0fafcc14969d5a7', 'dc14a2e4-071b-4d55-add6-722019d3a49b', 'NS', 'Chile');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9b9ef55e-529d-460b-af3c-1b564b570ecd', 13, 34, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e8b1b80c-f26d-4e0d-af46-87b0dad8dfcb', 13, 35, 1);
-- Completed generating test data for Chile

-- Generating Test data for China
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CN', 'China', 'China', 'CNY', 2000, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('98f3aed9-4246-4bfe-bf34-a88c173c7370', 2, null, 14, 'China');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('92219b23-3bc2-4593-928c-92aee1d8dc44', 4, 36, 14, 'China');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1d67bb1b-9ab0-4d93-9d8a-3f340bc735a8', 'ACTIVE', 'china@iaeste.cn', 'NS.China@iaeste.org', '569976d4ef2d8cdbec7f93e6373106c9a5cf9764759531af29e12d511c10927b', 'f896005f-f4f9-4542-a0fa-6a356e2f1001', 'NS', 'China');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b988c4c5-df5a-411c-b869-193ee043a788', 14, 36, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('29be4afe-a489-4df3-a58e-5c4e8619af63', 14, 37, 1);
-- Completed generating test data for China

-- Generating Test data for Colombia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CO', 'Colombia', 'Colombia', 'COP', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e4012e2b-b444-4ead-9223-4efabbeaac68', 2, null, 15, 'Colombia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('34823d36-9b1d-4058-88f2-deaeb95f11ab', 4, 38, 15, 'Colombia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2f2500ce-5aa0-446d-a315-a809480708b7', 'ACTIVE', 'colombia@iaeste.co', 'NS.Colombia@iaeste.org', '5177bc0b07b093375b2e79b2fe9307b422d7076fd96b5ac4edafd6f23288629e', '29fa33e8-5f1a-4db9-aaac-6fcba6197a43', 'NS', 'Colombia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('f2e5369b-9d0f-4132-ae04-ed0876071636', 15, 38, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('138b754c-9bbe-4e8c-9257-4e7e6ceef5b5', 15, 39, 1);
-- Completed generating test data for Colombia

-- Generating Test data for Croatia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('HR', 'Croatia', 'Croatia', 'HRK', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('aa681bc9-0707-4e3c-9cd1-d33c4af13f00', 2, null, 16, 'Croatia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a47fd465-bb39-4c8c-8a9e-d51671790567', 4, 40, 16, 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('4548e436-abf4-4c1a-a88d-fc7b639033d3', 'ACTIVE', 'croatia@iaeste.hr', 'NS.Croatia@iaeste.org', '707ce8cfbd52ce145b78b8ba28b9b9f5d1b69de453b672196412fb7d9bd0c7b2', '6570fe76-94a7-478b-b6ef-56e14ac92930', 'NS', 'Croatia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('abebaecf-3870-4ade-b6d2-5ce84033fc36', 16, 40, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b618d07b-20c4-4382-9e58-a4106a227211', 16, 41, 1);
-- Completed generating test data for Croatia

-- Generating Test data for Cyprus
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CY', 'Cyprus', 'Cyprus', 'EUR', 1980, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('56dd0070-e1f3-403e-bf58-87f4b6b5b53e', 2, null, 17, 'Cyprus');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ff8b0c56-dfdc-43cf-9fb9-a000cbd14c56', 4, 42, 17, 'Cyprus');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('72921df0-7408-473d-97a1-54f86729c708', 'ACTIVE', 'cyprus@iaeste.cy', 'NS.Cyprus@iaeste.org', '025cb914aafbbd31bf9e3ef68bbec8cd94146bd25cdb328ba1de9da034b5b7e9', 'cca40219-441a-4b78-a402-7ebaf4adf07a', 'NS', 'Cyprus');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('7cd6485d-e255-4ecc-be11-f64633d222e8', 17, 42, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('87e8f405-b646-4522-8cb5-34fa5a65e2d2', 17, 43, 1);
-- Completed generating test data for Cyprus

-- Generating Test data for CzechRepublic
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CZ', 'CzechRepublic', 'CzechRepublic', 'CZK', 1965, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('04beae66-f474-4942-8daf-e22c3b2a9bd8', 2, null, 18, 'CzechRepublic');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('78206b95-d772-41fb-89fb-6e9993b05f80', 4, 44, 18, 'CzechRepublic');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('3a090e97-f6a9-4dbf-a430-87dec58e2067', 'ACTIVE', 'czechrepublic@iaeste.cz', 'NS.CzechRepublic@iaeste.org', '7fef07335641a261447d45ad1b1fe6facb34de9fa500ac60edd528df83c0c306', '939d5598-729d-4171-8c8f-2f9750c2f2c5', 'NS', 'CzechRepublic');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ac79fe79-c87f-4900-b624-a0f24588c887', 18, 44, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9deaa63e-b729-4969-b068-ee2abfc80356', 18, 45, 1);
-- Completed generating test data for CzechRepublic

-- Generating Test data for Denmark
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('DK', 'Denmark', 'Denmark', 'DKK', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('23fdd892-7cc1-49f1-b8e5-dee37f16e74d', 2, null, 19, 'Denmark');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('82c0bf70-80d3-4fbb-9832-1c772ca300c5', 4, 46, 19, 'Denmark');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2035e240-56ce-4da6-b160-2637000459ea', 'ACTIVE', 'denmark@iaeste.dk', 'NS.Denmark@iaeste.org', '31a4868fff9915e2c73fba2f76bf0a9f25e917c048c04001e82b5a88d0ebb102', 'a5f9fc04-81bc-4ca5-9eae-bf22d7adf013', 'NS', 'Denmark');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bcdda30b-aa0e-4fdf-93f7-50f7345fb5b2', 19, 46, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('09a511cf-9f76-4078-bb7e-863702cd0694', 19, 47, 1);
-- Completed generating test data for Denmark

-- Generating Test data for Ecuador
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('EC', 'Ecuador', 'Ecuador', 'USD', 1999, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c723609d-4623-459f-bd8c-7486a49b2364', 2, null, 20, 'Ecuador');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('06c51fe0-8d43-4a06-aa96-faaff3cdcce6', 4, 48, 20, 'Ecuador');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1e288252-d383-49b2-a4a1-0099cffc5a66', 'ACTIVE', 'ecuador@iaeste.ec', 'NS.Ecuador@iaeste.org', '3e22928d71c9c19e609782c2284895129942c83bc669523174bb7045caa4bad4', 'c3072897-82aa-4998-861d-1ddc1bf0c334', 'NS', 'Ecuador');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('f59461f5-775f-499c-a500-8874bc0fb3d5', 20, 48, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bc43d69b-26fe-4326-b32f-0b036b691072', 20, 49, 1);
-- Completed generating test data for Ecuador

-- Generating Test data for Egypt
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('EG', 'Egypt', 'Egypt', 'EGP', 1961, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ec08a712-f75b-40a7-a74d-388230f7ee0c', 2, null, 21, 'Egypt');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a37b639f-b2e9-4100-b447-59ee217ebf12', 4, 50, 21, 'Egypt');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('32a7c522-5154-4eda-8495-7d5792c28b7a', 'ACTIVE', 'egypt@iaeste.eg', 'NS.Egypt@iaeste.org', 'b3dffdd3893a60199d9368252c72756dafcb5fcacc15bb8736aa4a2be12f1716', 'c26a1b91-a180-4e87-9209-0d72764fabe0', 'NS', 'Egypt');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1bf6ecc3-882a-4d1e-ae76-903a028ea152', 21, 50, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('5b408d31-f59b-494b-95a6-2e070d2d9c7a', 21, 51, 1);
-- Completed generating test data for Egypt

-- Generating Test data for Estonia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('EE', 'Estonia', 'Estonia', 'EUR', 2010, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('eb7f2270-a777-4e82-bb49-f8d67a487594', 2, null, 22, 'Estonia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2007aabf-b156-42bc-afcc-c2086d6f879f', 4, 52, 22, 'Estonia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('96a5beab-7932-4428-a5ef-26e2416c8174', 'ACTIVE', 'estonia@iaeste.ee', 'NS.Estonia@iaeste.org', '8d7494df85d6a539c1cfcaf1c115015de9e553fddbf88345bd357c7d3e21d72f', 'd864eade-8b37-44d0-9421-87a76d3d5a8b', 'NS', 'Estonia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('206c89ea-c432-48c4-b326-c52b7323fd4c', 22, 52, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fbf41d4d-9d9c-438b-a632-52978e67c98f', 22, 53, 1);
-- Completed generating test data for Estonia

-- Generating Test data for Finland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('FI', 'Finland', 'Finland', 'EUR', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('de8546e4-67dd-4329-9aed-0391c4390e5f', 2, null, 23, 'Finland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('544d1a86-9163-4409-aabd-54f1096100e3', 4, 54, 23, 'Finland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2e838316-b5e7-424f-bccf-f72c1af56122', 'ACTIVE', 'finland@iaeste.fi', 'NS.Finland@iaeste.org', '065a4a63e3caa8e732bd7d1896388cd4ad8cae8add8a1ff3a424e713c570d1b4', 'b62f8cb7-0ed6-4092-8d77-7da90deb04fa', 'NS', 'Finland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0ec4f780-35d3-4b64-83e7-31db6fb48ceb', 23, 54, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('94c27471-a7d1-49b1-bd38-4bbd12feb4c8', 23, 55, 1);
-- Completed generating test data for Finland

-- Generating Test data for France
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('FR', 'France', 'France', 'EUR', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('714c6061-4df8-469f-9998-87f17620322c', 2, null, 24, 'France');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9f26d467-f35f-44ba-8c2e-510ba05d9e11', 4, 56, 24, 'France');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('0502f219-225d-4c9e-a055-8ef68d18a6a6', 'ACTIVE', 'france@iaeste.fr', 'NS.France@iaeste.org', '4f35a9be11b091dbe42c0cb01ab2e773d085dd9a5db78d08c164a8d316fdbdb6', '52f2f3a0-872a-4e0b-ac0c-a19d0a390a4b', 'NS', 'France');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('c7d1e436-4030-44f4-b83b-eafa840cc085', 24, 56, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('f77229fe-d2a9-4cd0-80d8-64f54eed4236', 24, 57, 1);
-- Completed generating test data for France

-- Generating Test data for Gambia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('GM', 'Gambia', 'Gambia', 'GMD', 2009, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4e1503a5-45a0-421c-8c92-c0636fe849e8', 2, null, 25, 'Gambia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('089727fe-55c5-45f5-a7f9-729f9df801da', 4, 58, 25, 'Gambia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d2046347-1ccf-437b-b35e-afe782ff0e49', 'ACTIVE', 'gambia@iaeste.gm', 'NS.Gambia@iaeste.org', 'b41c3736fb8da1b76d60311ebb0998553f6bce5947885412a53c005ffb99974d', 'b52966a2-58e1-44f3-99d7-77d785d298f7', 'NS', 'Gambia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a27656cd-48a7-4912-8226-470536b72913', 25, 58, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2d441a71-29c9-4dc2-8071-2c212c56a38b', 25, 59, 1);
-- Completed generating test data for Gambia

-- Generating Test data for Germany
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('DE', 'Germany', 'Germany', 'EUR', 1950, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('8771e306-0e5e-4eb8-bad1-7f7837b97762', 2, null, 26, 'Germany');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9d10455b-834a-4570-89ca-c7ce4c68eb3a', 4, 60, 26, 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ad009b4b-f2e9-4664-ac3e-ed59a44f1c17', 'ACTIVE', 'germany@iaeste.de', 'NS.Germany@iaeste.org', 'c82b046711db77b448ba36b87abaaebb9af37ac595d1a636b861d577eb731b2e', 'a92a6147-3b70-463b-80a3-fe51e82e7961', 'NS', 'Germany');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ac38b617-4f6c-4a3b-ab0f-7d6756908449', 26, 60, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a9870253-53cc-447f-9e16-25188fefd8c1', 26, 61, 1);
-- Completed generating test data for Germany

-- Generating Test data for Ghana
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('GH', 'Ghana', 'Ghana', 'GHS', 1970, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('28735bcd-ac28-4ad7-86da-34e342a225c3', 2, null, 27, 'Ghana');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('78c15605-f41d-4c0f-9694-140b65af9e61', 4, 62, 27, 'Ghana');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('333d33a1-550b-4e8a-968f-2b8899c56efd', 'ACTIVE', 'ghana@iaeste.gh', 'NS.Ghana@iaeste.org', '09bff7ed7b9646661f3d4048e47e3c04f0f490abecb7220b2601b3cd9fc9c66f', 'e9c7f9bd-cf1b-422c-912e-a3cd24b56a54', 'NS', 'Ghana');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e1aa64dd-cc8b-472f-9bf1-d9a118650114', 27, 62, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0470aa87-4ae5-440f-bbdf-dfb5ec39a0e5', 27, 63, 1);
-- Completed generating test data for Ghana

-- Generating Test data for Greece
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('GR', 'Greece', 'Greece', 'EUR', 1958, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f4455e0c-5b8e-4dfb-ad3b-ffc224f3824b', 2, null, 28, 'Greece');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('afc2175c-740f-4d8f-9118-70a4b7ead7c0', 4, 64, 28, 'Greece');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a4def29c-532a-44a2-a7e4-d800a120bee2', 'ACTIVE', 'greece@iaeste.gr', 'NS.Greece@iaeste.org', '6a1e8151ea498e47c314ce7dfff00edf80a30272302a6dda02d3a773bdd3fe88', 'b8ee85ab-4684-4b92-ad26-893a4679b9b5', 'NS', 'Greece');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1e69c4df-9d07-49cf-b53c-085e744940f7', 28, 64, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('42680a58-04aa-4c5c-b9ba-4ccb50497212', 28, 65, 1);
-- Completed generating test data for Greece

-- Generating Test data for Hong Kong
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('HK', 'HongKong', 'HongKong', 'HKD', 1997, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('33864a9b-5032-475a-8bc5-3c43336a409e', 2, null, 29, 'HongKong');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('341ac3a6-ffde-42ec-83b1-a04227ec194b', 4, 66, 29, 'HongKong');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b9e5967d-bbeb-49f5-8d04-72efc0376066', 'ACTIVE', 'hongkong@iaeste.hk', 'NS.HongKong@iaeste.org', 'ea80f3333f7ca379b3d570a59505a0367fbba61b0357a51209a87d888f7b9e78', '9360c771-605c-45ea-a889-ab4f17fe0024', 'NS', 'HongKong');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('753f942e-9e13-4f41-a94d-6b2f1d5cd5f6', 29, 66, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b5e4457a-ca44-401f-a60d-13e95604e655', 29, 67, 1);
-- Completed generating test data for Hong Kong

-- Generating Test data for Hungary
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('HU', 'Hungary', 'Hungary', 'HUF', 1983, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0e12c5c0-9ecc-4d35-90ef-e2d6a4cdcc6a', 2, null, 30, 'Hungary');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4f6591a4-c5fe-4885-ab9d-46cbc44bc969', 4, 68, 30, 'Hungary');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('bacd33e3-7deb-46bf-9ba6-ad1cf6ae86c4', 'ACTIVE', 'hungary@iaeste.hu', 'NS.Hungary@iaeste.org', '1f1d47cbe856900bd2f3b749eed5c10521eafaebbecf27dff6b13da0753baf8d', '89a9764f-3a76-4c74-be83-b143fb14e84f', 'NS', 'Hungary');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1b83ba51-8cd7-4eb1-8b78-3abbe4d6e4c0', 30, 68, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b1e3ab12-3706-4b6f-b876-e55ebf2ec999', 30, 69, 1);
-- Completed generating test data for Hungary

-- Generating Test data for Iceland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IC', 'Iceland', 'Iceland', 'ISK', 1951, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d4352cea-a7ba-4254-82cf-f73e91a8e965', 2, null, 31, 'Iceland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('872ead93-530b-4bf2-854f-f539077dbd1a', 4, 70, 31, 'Iceland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('cd885d8f-c26e-4ded-9d08-a42e1ebf5053', 'ACTIVE', 'iceland@iaeste.ic', 'NS.Iceland@iaeste.org', 'e6d6b73381bc31ee3e4930637b525cebdeacb6917ed3f823bfd3528ff244af47', '07af1b1f-ccb6-4364-ab92-5d2a6379ccaa', 'NS', 'Iceland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3c02d1b0-4f8b-42d7-a605-a295db27711e', 31, 70, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a27eca01-4e4b-41b2-8bdb-f8736d095ca9', 31, 71, 1);
-- Completed generating test data for Iceland

-- Generating Test data for India
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IN', 'India', 'India', 'INR', 2001, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b9f73e60-b07a-488b-bf5c-ef932d4d0658', 2, null, 32, 'India');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('35c5df32-4543-4813-ae8e-631fd46b5b99', 4, 72, 32, 'India');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1cc567ea-db71-4221-81e5-e1abcb644613', 'ACTIVE', 'india@iaeste.in', 'NS.India@iaeste.org', '2e1704de3a65dfb8e74dcf2c8283b5c20342bef93661eb2979d56866c0ac5c31', 'b154dc70-7c81-445f-8801-3c174c9d8eb2', 'NS', 'India');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3fba082d-968a-4bf2-b25d-3127cf42e36b', 32, 72, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('73f0669b-31f7-4c8e-bc0e-b79a9bb1c617', 32, 73, 1);
-- Completed generating test data for India.KU

-- Generating Test data for Iran
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IR', 'Iran', 'Iran', 'IRR', 2002, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('12ff37b6-5ac9-4c96-8949-5dc858fd4870', 2, null, 33, 'Iran');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e5463cb8-f6ad-49d5-83cd-2d836f70c57f', 4, 74, 33, 'Iran');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('dcae209d-8470-47fc-8d00-6e291cf40f4a', 'ACTIVE', 'iran@iaeste.ir', 'NS.Iran@iaeste.org', 'b58887933ef3d6fdff6ff65b0542e7b8e5c9807216132962f28dc0f13be0ffeb', 'c85c72f2-2959-4339-b5f2-5cb9635becfe', 'NS', 'Iran');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e0db2796-4129-4c4a-91bb-69821d9f24c4', 33, 74, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('878365e1-100f-41aa-bea3-3df25ae85458', 33, 75, 1);
-- Completed generating test data for Iran

-- Generating Test data for Ireland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IE', 'Ireland', 'Ireland', 'EUR', 1962, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e9372753-1931-4342-bb8a-7807b64db7f0', 2, null, 34, 'Ireland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4a209296-fabe-463b-b8c0-16dbcb7341b6', 4, 76, 34, 'Ireland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('6d24dd03-ff9c-429c-bbdb-df2b7134ce64', 'ACTIVE', 'ireland@iaeste.ie', 'NS.Ireland@iaeste.org', '9d034478f72da96b36a2182aa84ed979dbc2dc497cefba42a891b722eae7a77c', 'a36a579c-948b-4fe4-80ec-73c11add8436', 'NS', 'Ireland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('138d5838-fbeb-4d8c-9495-22ecb597c3c5', 34, 76, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1466f618-1bd8-4157-89fe-f51316a88a4e', 34, 77, 1);
-- Completed generating test data for Ireland

-- Generating Test data for Israel
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IL', 'Israel', 'Israel', 'ILS', 1951, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e5b44bdb-170d-4fa6-aa84-6dc06f00fc6a', 2, null, 35, 'Israel');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9c564ca0-6eda-47cf-be3d-3fa6d7918142', 4, 78, 35, 'Israel');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fa1e4f28-4ca5-4766-93d4-32da276315b1', 'ACTIVE', 'israel@iaeste.il', 'NS.Israel@iaeste.org', '641bbba6337a8077764a7b5459b9a8197c437b942e7004dc1282e036cafee6d6', '822dfbc4-c368-4c08-b62d-963055bed411', 'NS', 'Israel');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('592209e9-6aa1-492f-a0e1-2f014f85d0d6', 35, 78, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8798e068-1c5c-4882-a64d-427241da291d', 35, 79, 1);
-- Completed generating test data for Israel

-- Generating Test data for Italy
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IT', 'Italy', 'Italy', 'EUR', 2011, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d188373c-5692-4849-8193-51c14303245d', 2, null, 36, 'Italy');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f75c041f-e9b4-4d45-b0d7-578e29e6c54c', 4, 80, 36, 'Italy');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a8d326e8-a003-4af6-b9d5-0f8bd0689348', 'ACTIVE', 'italy@iaeste.it', 'NS.Italy@iaeste.org', '90b282761d093866fb1811813795f10d25b7ff0fd50e15aca17f648008a449b4', '328f6966-349b-43b5-b42e-d750e7a3ea64', 'NS', 'Italy');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('beac3e94-4571-47a6-9a47-5f772ce109ea', 36, 80, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('7b339bfb-4fb5-48ec-ac3b-48a5ef1cd0d9', 36, 81, 1);
-- Completed generating test data for Italy

-- Generating Test data for Jamaica
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('JM', 'Jamaica', 'Jamaica', 'JMD', 2006, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('cf447972-b50b-4d33-9010-cd9f0cad8f6b', 2, null, 37, 'Jamaica');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4d94ad57-0d19-4428-a9cc-7452a2d129eb', 4, 82, 37, 'Jamaica');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('bc6f54a5-71e6-40c7-90d9-173563372be1', 'ACTIVE', 'jamaica@iaeste.jm', 'NS.Jamaica@iaeste.org', '95558d32912dedb5fc9c461bcc0cc63c25e86393e861539c94d544b45becd4ab', '34de0914-ebb8-41ac-bb6e-685f64b9e707', 'NS', 'Jamaica');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('114c8053-b267-4530-9479-2889647bd6a7', 37, 82, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2279d388-cdea-454c-9479-69a1dac0233f', 37, 83, 1);
-- Completed generating test data for Jamaica

-- Generating Test data for Japan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('JP', 'Japan', 'Japan', 'JPY', 1964, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0c40dce4-093e-4581-98f0-b8695e5a7c82', 2, null, 38, 'Japan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('6ef0a009-b279-4f20-87c8-a218488bca7f', 4, 84, 38, 'Japan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('01826b4c-480c-4034-940c-d6e1a02b663f', 'ACTIVE', 'japan@iaeste.jp', 'NS.Japan@iaeste.org', 'ed896fdbf4998dbc8da4e5197f50977eed0c57ed85b8328f4dc842e244f54c06', '4bfa3c15-15aa-4900-a45d-c42fe49a8fe4', 'NS', 'Japan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8e5908b1-b5f6-4970-9480-62839fbc67ff', 38, 84, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('30a34d90-d8fb-4bb6-81e3-f1606e62276a', 38, 85, 1);
-- Completed generating test data for Japan

-- Generating Test data for Jordan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('JO', 'Jordan', 'Jordan', 'JOD', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a8ba86f9-8129-4466-b9e8-6e9dd3b21a22', 2, null, 39, 'Jordan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c24cdcf3-eeee-4bac-9107-b0570fe96fa0', 4, 86, 39, 'Jordan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('514dc268-d922-47d1-81f0-3773022dbe64', 'ACTIVE', 'jordan@iaeste.jo', 'NS.Jordan@iaeste.org', 'bffc551a9c954b2be2e69f0d26ff1af727228edfb14e1e9f5d0a69df0411d9d6', 'd94f7396-e325-43d6-82f3-8926a5f377c9', 'NS', 'Jordan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bf29a482-6249-4617-907e-98bf842521f6', 39, 86, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8c3076ff-91bb-4782-8b84-e3d106d75640', 39, 87, 1);
-- Completed generating test data for Jordan

-- Generating Test data for Kazakhstan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('KZ', 'Kazakhstan', 'Kazakhstan', 'KZT', 1995, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f1556862-958e-4740-8ae6-50fe35476e66', 2, null, 40, 'Kazakhstan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('99174b65-d9fa-42dd-a01c-4c37b832a916', 4, 88, 40, 'Kazakhstan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('517c4054-5369-43bd-aa69-c73a6872015a', 'ACTIVE', 'kazakhstan@iaeste.kz', 'NS.Kazakhstan@iaeste.org', '3ca1712762fa818f4ab97f080517c8e93efaebf95f8e105f195c3ba568c50018', '82883c26-9c3d-4517-bd20-98edcf1f3902', 'NS', 'Kazakhstan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8ae7beea-2bb2-4cd1-ba3a-ff46db307f98', 40, 88, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('68544604-a928-44d5-8d1a-d97d5f6e93fb', 40, 89, 1);
-- Completed generating test data for Kazakhstan

-- Generating Test data for Kenya
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('KE', 'Kenya', 'Kenya', 'KES', 2004, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('505d8241-4f71-461d-b9cd-db4c90f5b844', 2, null, 41, 'Kenya');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('5f489c47-18f4-441f-a355-add05547876a', 4, 90, 41, 'Kenya');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2960c9ae-b420-4ac2-972f-ea6b8e7dc63e', 'ACTIVE', 'kenya@iaeste.ke', 'NS.Kenya@iaeste.org', '96f5fd82bd967cabaa784d3c010cb434741a25e101a9dde7cda8b443c0d62766', '880589fe-7e6c-4f2d-addc-3f2cabca032a', 'NS', 'Kenya');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('886a0bbd-ec70-4e77-bc80-94a9048b4d28', 41, 90, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ef958fd5-f38d-4573-8a63-e65cc5685367', 41, 91, 1);
-- Completed generating test data for Kenya

-- Generating Test data for Korea
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('KR', 'Korea', 'Korea', 'KRW', 2007, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('08676d20-4308-42df-ba67-f27927182d72', 2, null, 42, 'Korea');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('df4cdec0-b8e9-4bc3-9b8d-87f6672a9cf8', 4, 92, 42, 'Korea');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ad71b795-4d28-4ae6-80d3-c8123306957e', 'ACTIVE', 'korea@iaeste.kr', 'NS.Korea@iaeste.org', 'ed2cfdfab92934cde8c9b356001da8757282ee003ee50e44394999249b27882d', '14ffacee-84c7-46f5-9170-2c90ce92cfc0', 'NS', 'Korea');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('608a8956-3eaa-4f6b-be4c-2f0873e4133f', 42, 92, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0368c14b-290c-404a-8fd7-25a616c35dc7', 42, 93, 1);
-- Completed generating test data for Korea

-- Generating Test data for Latvia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LV', 'Latvia', 'Latvia', 'EUR', 2002, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a657b0ce-b120-4bcf-824c-ac9739ebfb37', 2, null, 43, 'Latvia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('377ab884-b042-4ffd-9041-5455bc79baf4', 4, 94, 43, 'Latvia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ee85be0f-3c3e-4fb8-987e-3f0acb8e2360', 'ACTIVE', 'latvia@iaeste.lv', 'NS.Latvia@iaeste.org', 'ce9810e082c81627fbd06f3613ef2fa6df5a06c7f919086a3ae4cb073ae95409', '7cab1053-8f23-4a53-92f3-cf2e1f1b1631', 'NS', 'Latvia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('76be913b-e0b8-46d7-8fcf-4df8615267cd', 43, 94, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('978b76a6-acdc-4fc4-af11-d7c16bd83b21', 43, 95, 1);
-- Completed generating test data for Latvia

-- Generating Test data for Lebanon
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LB', 'Lebanon', 'Lebanon', 'LBP', 1966, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('40fdc473-da7a-4a14-a60b-f40915b6936b', 2, null, 44, 'Lebanon');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('baee5c49-a95d-4811-90c3-a3d7f7069b61', 4, 96, 44, 'Lebanon');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1e3db167-3799-4cc8-b8ca-8f0f40adb92c', 'ACTIVE', 'lebanon@iaeste.lb', 'NS.Lebanon@iaeste.org', 'b37463ac24e26bfce110fe68b3720df4b90c4db636c99f825b17d6838ac9cf45', 'ff710fbc-a0bc-44eb-9ceb-c0656a278fbd', 'NS', 'Lebanon');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bd8280e7-4af7-4b98-abb5-45af93fe05f8', 44, 96, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('dc582e0f-488f-4eb4-a773-0c73e90beaf7', 44, 97, 1);
-- Completed generating test data for Lebanon

-- Generating Test data for Liberia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LR', 'Liberia', 'Liberia', 'LRD', 2012, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('594cf553-196a-4b02-a50e-c35c6152a5ac', 2, null, 45, 'Liberia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c8aa8dd7-fad1-48ba-af26-afe1ad51ce3e', 4, 98, 45, 'Liberia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d94b3f5a-2896-495b-8320-0b31cbc00bc6', 'ACTIVE', 'liberia@iaeste.lr', 'NS.Liberia@iaeste.org', '8873c97b3d00763d6387649766fda2b97fc6d2ca8db481bf2bb93d89381cfc98', '1562e389-01eb-4852-85b7-561189d205e8', 'NS', 'Liberia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('93136252-0e8d-4de1-8070-96ca7f2d7e7a', 45, 98, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4851d565-a90d-4e91-a9d6-74a12aabbb3e', 45, 99, 1);
-- Completed generating test data for Liberia

-- Generating Test data for Lithuania
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LT', 'Lithuania', 'Lithuania', 'LTL', 1990, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9af8fe53-19c6-495c-a8c3-3b1c6dec2406', 2, null, 46, 'Lithuania');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('8e25de4a-da72-42c7-a035-814f85424138', 4, 100, 46, 'Lithuania');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2afaa563-87db-4f51-bf10-637a7aefa6f4', 'ACTIVE', 'lithuania@iaeste.lt', 'NS.Lithuania@iaeste.org', '1e059c814dc0dd4c6f48e526a8918c440f0d528d0220a3202f67a16c2c4545b5', '282f8b6e-884a-4497-84b4-d8bcfec2df1b', 'NS', 'Lithuania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8d3a5a02-b58a-4f02-80d5-7b67b01cab77', 46, 100, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3444c2f4-eb72-4d5f-9ca5-0c51081d8551', 46, 101, 1);
-- Completed generating test data for Lithuania

-- Generating Test data for Macao
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MO', 'Macao', 'Macao', 'MOP', 2004, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('64c1e3e4-3f67-4890-adff-8b62e9e45448', 2, null, 47, 'Macao');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('04abfe93-4720-4b24-b1e6-432489dc0819', 4, 102, 47, 'Macao');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('e8204669-0819-4815-9f4c-634ee9ca2a64', 'ACTIVE', 'macao@iaeste.mo', 'NS.Macao@iaeste.org', '7ebf078ce872b40017d2d64c7d3bf1def1e4113161aaab631e0da0ae5ca87fd7', '19541820-5ac7-4465-b473-33cec1566f62', 'NS', 'Macao');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a5d37dd0-b119-406f-98d9-298460ed3757', 47, 102, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1985ec8b-1a1f-4884-af05-440aee8b1d9b', 47, 103, 1);
-- Completed generating test data for Macao

-- Generating Test data for Macedonia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MK', 'Macedonia', 'Macedonia', 'MKD', 1994, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('6ce74341-8f4f-4b55-8ff6-d50542217f9a', 2, null, 48, 'Macedonia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ec479694-21ed-4c24-a8a8-ef8b0eb07bd6', 4, 104, 48, 'Macedonia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('dbe34c3a-609f-4e5e-a9e1-0de7e609c03b', 'ACTIVE', 'macedonia@iaeste.mk', 'NS.Macedonia@iaeste.org', '2f81a5eac1a88122aa3369c2a5bfdea76b7786b73e38c5b6686d6acf514ef93e', '68f5dcd2-8091-4b1d-85b1-6f623e888d21', 'NS', 'Macedonia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ad5c7c64-2ac6-4a13-9262-72077ca9ce72', 48, 104, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('81576fbf-b879-4ea5-83a6-e246490f7634', 48, 105, 1);
-- Completed generating test data for Macedonia

-- Generating Test data for Malaysia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MY', 'Malaysia', 'Malaysia', 'MYR', 2008, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('7eae41cc-03d0-48e1-ab01-2b9ad48d7f2f', 2, null, 49, 'Malaysia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1494f627-3fba-4b3a-8cf0-dde608d5f60c', 4, 106, 49, 'Malaysia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c250be3c-757e-47b9-9dec-3a1e71718a9e', 'ACTIVE', 'malaysia@iaeste.my', 'NS.Malaysia@iaeste.org', 'f5dd82a2a9a089b3697ddb2738478a8ead9bd71915f1cfe94423c4c4cd57235e', '93056aef-dcb3-49a2-ab17-7a23ea8c6322', 'NS', 'Malaysia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('146c0f26-2e8c-474d-ab8f-c8087aab0ce9', 49, 106, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e25adcb2-4f0d-45a5-9855-27a7937b4cbe', 49, 107, 1);
-- Completed generating test data for Malaysia

-- Generating Test data for Malta
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MT', 'Malta', 'Malta', 'EUR', 1984, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f362e01c-aa38-4e37-b221-45ddb8bdf968', 2, null, 50, 'Malta');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c75de18f-c9f0-41ed-bee3-3fe2f1826deb', 4, 108, 50, 'Malta');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fca39c22-6af4-4884-80cc-f7ff4c6286d2', 'ACTIVE', 'malta@iaeste.mt', 'NS.Malta@iaeste.org', '707b1a3774a86ebf608d8af67c48848bed52b070ee34da06ec4a0e616c0b2e30', '210c29ac-37ff-46e2-8e17-9fafddf05133', 'NS', 'Malta');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('eff7bec0-f3ae-450b-961d-a1fd8c1d9e1e', 50, 108, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6de6f4f4-e2b2-4be8-b583-76564e9c4935', 50, 109, 1);
-- Completed generating test data for Malta

-- Generating Test data for Mexico
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MX', 'Mexico', 'Mexico', 'MXN', 1985, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1e7336f2-8513-4de2-9f3b-6b73a7da5fcb', 2, null, 51, 'Mexico');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b18a734e-44fa-4a0b-8cff-3dc331d6893b', 4, 110, 51, 'Mexico');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a7200300-fd84-47db-b819-f8476dc26078', 'ACTIVE', 'mexico@iaeste.mx', 'NS.Mexico@iaeste.org', 'e5bc0f65a55013106a8deffee465c6113670c8080f6ef326a61665eaa403fe2e', '399a2840-a927-49c3-99db-deee5a1fecab', 'NS', 'Mexico');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3f911b1f-efae-4f9c-8faa-cf3bdecd8fd2', 51, 110, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cd72378c-f65e-4774-b758-6f2fafbc3ec0', 51, 111, 1);
-- Completed generating test data for Mexico

-- Generating Test data for Mongolia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MN', 'Mongolia', 'Mongolia', 'MNT', 2001, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f68a2678-38fe-4d69-89f9-07e01f537ef0', 2, null, 52, 'Mongolia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0ac91424-ca7e-4be4-87d7-2f35ca665cde', 4, 112, 52, 'Mongolia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('474963ee-9da3-4016-a6c6-a4b434e92afb', 'ACTIVE', 'mongolia@iaeste.mn', 'NS.Mongolia@iaeste.org', 'eaddbd8f071b49e027496a9fe147bcabc32c371b567029ab8e995e1c17073563', '566d4a4e-22de-4d61-b53a-9471bdb25494', 'NS', 'Mongolia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('d2fe4834-ad28-47cc-81b0-e8c98ef75859', 52, 112, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('11c4f6ed-282a-44a4-8292-70d9ef2d78d0', 52, 113, 1);
-- Completed generating test data for Mongolia

-- Generating Test data for Montenegro
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('ME', 'Montenegro', 'Montenegro', 'EUR', 2007, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9f6adbcf-e81a-44a7-85ad-812da9796e69', 2, null, 53, 'Montenegro');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('3390a51e-311a-4c62-a61f-00fc4820e666', 4, 114, 53, 'Montenegro');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d615c92a-7381-4162-a7cd-21c3b786600a', 'ACTIVE', 'montenegro@iaeste.me', 'NS.Montenegro@iaeste.org', '93155d2850dab4ccccbd986a75bf7cc2a7ec48ef66625a869d4d0fecadab4bbb', 'be7d4db8-cd46-47a2-9f65-abfa11208aa1', 'NS', 'Montenegro');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a094b0d2-38b1-4129-8ea8-4253e5d6288c', 53, 114, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('17d6ca3f-f759-4a65-8c10-0bef9a0ea16b', 53, 115, 1);
-- Completed generating test data for Montenegro

-- Generating Test data for Netherlands
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('NL', 'Netherlands', 'Netherlands', 'EUR', 2011, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('161e692f-7a38-4fe5-b8c4-c58c5573b17b', 2, null, 54, 'Netherlands');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ef8762cc-3378-4dcf-b686-0baa60a4525f', 4, 116, 54, 'Netherlands');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('6b8dc44d-5779-4be2-95b1-deb1eaa9fc55', 'ACTIVE', 'netherlands@iaeste.nl', 'NS.Netherlands@iaeste.org', 'a03f927032fd788a4d2cfcac2adf69d17becf23e1d52658670baaccc9708d712', '4d53b94a-32ab-4ce7-8763-70317292d0ee', 'NS', 'Netherlands');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ca3773df-da87-45f4-ba6e-c56a3527bd2d', 54, 116, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('55060933-bd23-4f25-8a0b-43d0db229137', 54, 117, 1);
-- Completed generating test data for Netherlands

-- Generating Test data for Nigeria
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('NG', 'Nigeria', 'Nigeria', 'NGN', 2007, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1a2b1987-e955-493c-8632-9a2fdb5d44b8', 2, null, 55, 'Nigeria');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('24fd88db-8527-4c3c-9c2c-8e46a63df310', 4, 118, 55, 'Nigeria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('14ffb15a-e870-408f-8c4b-973222bffb21', 'ACTIVE', 'nigeria@iaeste.ng', 'NS.Nigeria@iaeste.org', '8f1338536d20863b8abd16415f585b9005720c45f7853140918a6dd1739aad93', '0ff96f09-5fbd-487a-a127-8d611d36c700', 'NS', 'Nigeria');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('25f6de8c-a6c6-4931-a076-e61efdccd1eb', 55, 118, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('35795182-d5d3-45ac-b1ea-95ccb7e87a93', 55, 119, 1);
-- Completed generating test data for Nigeria

-- Generating Test data for Norway
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('NO', 'Norway', 'Norway', 'NOK', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d975e033-7fc0-4980-b1e1-9ae97eb43554', 2, null, 56, 'Norway');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a9bbc993-885a-4c48-b3be-50a89d691f59', 4, 120, 56, 'Norway');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('346c01c6-97bb-4435-9339-d16ccc9ecbac', 'ACTIVE', 'norway@iaeste.no', 'NS.Norway@iaeste.org', 'ab0e565f340780059b801963b05853dc79b3127750bc4b8e461c98a255fc1206', 'd31f478c-3f83-41e0-b58f-9c948f785e5f', 'NS', 'Norway');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bce61325-914f-49ea-b3bf-da405958bcca', 56, 120, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('660837b4-1533-4476-8839-6b3fed73797b', 56, 121, 1);
-- Completed generating test data for Norway

-- Generating Test data for Oman
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('OM', 'Oman', 'Oman', 'OMR', 2001, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c9c2c6e8-6fbd-43ae-9bd5-cdb9c9df2068', 2, null, 57, 'Oman');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c22dd9c4-07fe-4e0a-bc5c-7120fc1c4e4d', 4, 122, 57, 'Oman');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('7151b87c-c535-46c8-86af-8dba8b8d2bb9', 'ACTIVE', 'oman@iaeste.om', 'NS.Oman@iaeste.org', 'ef7f42cb4b760572dd3d99e45e56bee50627ba52419df74922953a4b1d668592', '7d18552b-824c-4844-b956-79b246c711cf', 'NS', 'Oman');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('995ab456-c475-46c3-8810-2f2804abc538', 57, 122, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('686ac121-f6cc-4ef3-82d6-0e5a3cd669ee', 57, 123, 1);
-- Completed generating test data for Oman

-- Generating Test data for Pakistan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PK', 'Pakistan', 'Pakistan', 'PKR', 1990, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('49e7dff5-e152-4da9-bc7f-e07344473eba', 2, null, 58, 'Pakistan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('97027062-a067-4146-b5ed-858ed9d03d7d', 4, 124, 58, 'Pakistan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c632d801-31b2-4c99-a702-3968d685acf5', 'ACTIVE', 'pakistan@iaeste.pk', 'NS.Pakistan@iaeste.org', 'e7a718edf720e6bdda8df3b2a0c6000d1ae9f6199f36ab1d490bc5685a2c73ec', '4459879c-3db4-468a-963e-344d2d4e6d60', 'NS', 'Pakistan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fffc7496-f439-44e6-8493-2880434ce018', 58, 124, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('de87c2c5-5b93-4167-929f-e1823deabe1d', 58, 125, 1);
-- Completed generating test data for Pakistan

-- Generating Test data for Panama
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PA', 'Panama', 'Panama', 'PAB', 2004, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e8922884-8d42-4541-9f08-340dd1a6b0d1', 2, null, 59, 'Panama');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('abd8b394-1fe3-4ccf-8e15-64bf4a36d8b1', 4, 126, 59, 'Panama');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('881cd5db-5939-4576-bc87-cfdaea961d52', 'ACTIVE', 'panama@iaeste.pa', 'NS.Panama@iaeste.org', 'e8f2ec56cce2879cf5fabe4c9cc5a35ad9ed9ff933f83a58cb4c5eeb36e93571', 'e821f8de-ece0-4b86-b9b3-3e71d44e38ff', 'NS', 'Panama');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('aa938141-5197-477f-91f4-f99dc4f189e3', 59, 126, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0227b327-cf00-4267-9eaf-fce43a56e8b9', 59, 127, 1);
-- Completed generating test data for Panama

-- Generating Test data for Peru
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PE', 'Peru', 'Peru', 'PEN', 2001, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f6269393-9df7-44ad-8ab3-6d8e8cf20a22', 2, null, 60, 'Peru');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f77f5093-31fa-40f7-b85f-9a540a8f5433', 4, 128, 60, 'Peru');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('7fa69686-7950-4bfd-ac42-e3d4d0f326af', 'ACTIVE', 'peru@iaeste.pe', 'NS.Peru@iaeste.org', '608e055b74efde45b9c0cf1bea42d8c80937542448ae08e6a43cf3e53d2af37d', '49594e12-7061-4775-b950-3c40f882b8ec', 'NS', 'Peru');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8aa4a44e-4731-4395-8b37-c1b93195d13d', 60, 128, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0056cbf4-b4b5-4831-8c37-12f29b8084b2', 60, 129, 1);
-- Completed generating test data for Peru

-- Generating Test data for Philippines
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PH', 'Philippines', 'Philippines', 'PHP', 2007, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a2d98692-74d2-44de-8c38-557c67721374', 2, null, 61, 'Philippines');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a230e78f-4b74-463d-b6ad-8020166c8b6a', 4, 130, 61, 'Philippines');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('9918168f-61c5-46a4-9fa2-8fcc3785feba', 'ACTIVE', 'philippines@iaeste.ph', 'NS.Philippines@iaeste.org', '12522eac3fac5685a61d059ae47ae383b86cb1c0c90ab7cc7c6651f6c4057454', 'ec772d94-4650-4447-abd7-22fbca2bc976', 'NS', 'Philippines');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e9689b85-2248-4e4b-b9f6-d2d8255f9cee', 61, 130, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0efbce9b-2d2e-4d0a-94ba-7806fd20449b', 61, 131, 1);
-- Completed generating test data for Philippines

-- Generating Test data for Poland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PL', 'Poland', 'Poland', 'PLN', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('70e293c2-7142-4db2-91a7-cc0bf8fe5f65', 2, null, 62, 'Poland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('dcdf3f10-3a51-4970-933e-93b01a70b0b1', 4, 132, 62, 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('41843b29-a581-4dd9-9978-875c79257395', 'ACTIVE', 'poland@iaeste.pl', 'NS.Poland@iaeste.org', 'a3dea00dcb48d3c725e5b38e5beaddae5ffda82a9616679f7e8fe01333898010', 'bb5fad04-e742-4747-a108-bc6a4cb9deae', 'NS', 'Poland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8531065d-d2b8-44d4-b635-f3e5f8e6eaa1', 62, 132, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('df642030-be60-4402-be85-9c4dad60bfed', 62, 133, 1);
-- Completed generating test data for Poland

-- Generating Test data for Portugal
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PT', 'Portugal', 'Portugal', 'EUR', 1954, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('03fc1e57-0913-4c24-b250-5be5b64ddfad', 2, null, 63, 'Portugal');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('25d9165f-1863-4327-81f2-8d12fcf97b6e', 4, 134, 63, 'Portugal');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d8180ac7-aceb-45e6-82fa-e6c4129f2e8e', 'ACTIVE', 'portugal@iaeste.pt', 'NS.Portugal@iaeste.org', '74b2c6eea87a9c181676df2a853fabb60e374bda2a5404763f83f73ffc3e5e32', '2313b46b-c874-4ce3-92b7-2583df0ac6af', 'NS', 'Portugal');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a57a90a4-4063-44ab-84eb-f3f002e5ce70', 63, 134, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9f0aae57-b95e-46c1-8c59-d4a5f36d84fe', 63, 135, 1);
-- Completed generating test data for Portugal

-- Generating Test data for Qatar
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('QA', 'Qatar', 'Qatar', 'QAR', 2011, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('36bcc1ad-cf1a-44dc-98ee-320b2c59f4f9', 2, null, 64, 'Qatar');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4c2a0116-14ac-4d22-8d5c-c3f3e332e4ce', 4, 136, 64, 'Qatar');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('5b04bf8f-1ef8-4c38-872f-6c5f0c7ba64e', 'ACTIVE', 'qatar@iaeste.qa', 'NS.Qatar@iaeste.org', 'f39c2ac9260a232a80050482fe20260b5376f712494a9778917ae957dfd94a7e', '0894ff66-ccaf-4c18-ac17-42998b90e49a', 'NS', 'Qatar');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a4f614c3-072f-4a21-9ad2-df1bfa47c304', 64, 136, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('35521cc4-f95c-4279-b146-85f07e8278e0', 64, 137, 1);
-- Completed generating test data for Qatar

-- Generating Test data for Romania
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('RO', 'Romania', 'Romania', 'RON', 1998, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c6248151-1b55-420d-a3cf-2d5b94b533e2', 2, null, 65, 'Romania');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('fca658a0-db35-4037-b2f6-69bce461128a', 4, 138, 65, 'Romania');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('881bb216-8d46-4af7-90d0-b54ed980dceb', 'ACTIVE', 'romania@iaeste.ro', 'NS.Romania@iaeste.org', '0e03e8b1bfc0e0148a8f0119b22f2cbf8148f2f9b8bcbdd2d404628263f9273c', 'a6c7d11f-8fac-484a-87ca-ca0f1fe8d849', 'NS', 'Romania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('251d118b-aa39-42e1-9379-23dbd4680f2e', 65, 138, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('32befa26-faef-4774-96f6-adf8804895af', 65, 139, 1);
-- Completed generating test data for Romania

-- Generating Test data for Russia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('RU', 'Russia', 'Russia', 'RUB', 1991, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('af16cc9f-0f91-4e0d-964d-62eb7b6e6977', 2, null, 66, 'Russia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ea0b915f-a84e-4f74-85ba-80b5977963fc', 4, 140, 66, 'Russia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('244435dd-66ba-4445-ae6a-d62df350d4c5', 'ACTIVE', 'russia@iaeste.ru', 'NS.Russia@iaeste.org', '165b3e9c85736ab83df0ba533916f08a93a269f775c8df97dc67037323450aa6', '3e47afeb-b97f-4fb5-8b1d-2ddd7a0778ec', 'NS', 'Russia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0336f44a-751a-47cf-9c82-9600cd5ad602', 66, 140, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('5878dba7-cdd5-48c2-b3c1-b2669c2157bb', 66, 141, 1);
-- Completed generating test data for Russia

-- Generating Test data for Serbia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('RS', 'Serbia', 'Serbia', 'RSD', 1952, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('35404eb5-c46d-4edc-96d1-0f1a6f76fc21', 2, null, 67, 'Serbia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('09fc08e7-1541-4835-a81e-ddc5df88f9e6', 4, 142, 67, 'Serbia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('68ce5f12-b762-4e76-be0d-ac94d7680c1f', 'ACTIVE', 'serbia@iaeste.rs', 'NS.Serbia@iaeste.org', '8f3b2c13a05cc2a68c501006ba8ac8257351eff57e238f93a3fbd4bca5ee1a73', '3f9f89eb-cb9e-4cb0-88cb-4165fea6405e', 'NS', 'Serbia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('75de9633-70cb-46e8-a3a5-f9d453b13ba8', 67, 142, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0a092328-6b45-4b91-8875-c57c8100c984', 67, 143, 1);
-- Completed generating test data for Serbia

-- Generating Test data for Slovakia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SK', 'Slovakia', 'Slovakia', 'EUR', 1965, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('18767a92-94ba-4282-ab02-dc3287fd206e', 2, null, 68, 'Slovakia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b5905239-db02-4be4-81ef-a2390aafb07a', 4, 144, 68, 'Slovakia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('8b124814-c152-4bf1-adda-dc593703e587', 'ACTIVE', 'slovakia@iaeste.sk', 'NS.Slovakia@iaeste.org', 'f3429d7d1946d02388ff03d74e48acd15d0b3b5cf7529b04c7a859bf336b100c', '6e471a68-6fc6-4251-b741-ab177bea090c', 'NS', 'Slovakia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('81dfd137-fa49-47fc-99e6-7d93cfd2f800', 68, 144, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3840803a-47c2-47ab-aa51-a34c57379825', 68, 145, 1);
-- Completed generating test data for Slovakia

-- Generating Test data for Slovenia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SI', 'Slovenia', 'Slovenia', 'EUR', 1993, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ff5ec00d-5aad-40bf-a733-7cf3244ede4b', 2, null, 69, 'Slovenia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('dd61acdf-7a01-4123-8003-aef82310e772', 4, 146, 69, 'Slovenia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c85401fe-f6b9-493a-af4f-a553c01811b4', 'ACTIVE', 'slovenia@iaeste.si', 'NS.Slovenia@iaeste.org', 'cd84514ac6e6897fd21b98bd668f9214ddbbd68a09188ec9db8c4d7df8a787c5', '8dd48695-1ab2-4b22-a985-c5fd43c6f382', 'NS', 'Slovenia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b85ddba8-db9c-423b-b472-a1f3cade2530', 69, 146, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fe530d96-978f-4dc8-bcd7-3349564b1e84', 69, 147, 1);
-- Completed generating test data for Slovenia

-- Generating Test data for Spain
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('ES', 'Spain', 'Spain', 'EUR', 1951, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2aa253ae-c412-4041-8212-65978a14132a', 2, null, 70, 'Spain');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('052c7719-33e2-4825-baf1-39bf7d6457c2', 4, 148, 70, 'Spain');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b0abe1c5-ea87-47b2-8943-f1f6a50900f6', 'ACTIVE', 'spain@iaeste.es', 'NS.Spain@iaeste.org', '2732c2019ac5cd457ada6c41b777c40a8caec8abda37c0a7f8548c72a1afd7ad', 'dece4668-1310-4029-8051-c8f09c48b9bc', 'NS', 'Spain');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('016aabd1-53f2-4084-aa7d-faf13cb32fde', 70, 148, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4bf951eb-70ac-419b-9b5b-5d2281890747', 70, 149, 1);
-- Completed generating test data for Spain

-- Generating Test data for Sri Lanka
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LK', 'SriLanka', 'SriLanka', 'LKR', 2000, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2e6551b5-c832-43a0-bb47-88af2e82fc79', 2, null, 71, 'SriLanka');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('7ccf6069-6ada-4045-91c0-2ccdde794dc6', 4, 150, 71, 'SriLanka');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ba82ed83-f1b7-4db9-8e9b-c86dcc99fc79', 'ACTIVE', 'srilanka@iaeste.lk', 'NS.SriLanka@iaeste.org', '48629abeb746e7b20b2d0de7ef7450372e27f1f49fb7b930d5511c92b9878ded', '87f1b13f-7f1b-4931-94ad-cc451660618a', 'NS', 'SriLanka');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cefaefbd-3f21-42cf-81be-b0c721e24eab', 71, 150, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cf0d33b7-eddc-411f-a364-7e4f39b4bf78', 71, 151, 1);
-- Completed generating test data for Sri Lanka

-- Generating Test data for Sweden
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SE', 'Sweden', 'Sweden', 'SEK', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('894c8eed-27dd-45d0-b00a-d6da51bdc8aa', 2, null, 72, 'Sweden');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('df577354-cd95-4ab7-bce7-1756ecbb4b2e', 4, 152, 72, 'Sweden');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('0a56a744-3bc9-4ae6-8f80-cccd806d765a', 'ACTIVE', 'sweden@iaeste.se', 'NS.Sweden@iaeste.org', '596c4781a27a66ddec199b47ebefa5877051db2f9bde0b883833386ee2b3dff5', '11aa6d7a-29fb-40ee-a916-b0ca68a6d20d', 'NS', 'Sweden');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('27c963c3-bcb1-4835-a9ec-a4f7ff628adf', 72, 152, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('5597d1df-2b01-43e4-9b94-a5458614a70a', 72, 153, 1);
-- Completed generating test data for Sweden

-- Generating Test data for Switzerland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CH', 'Switzerland', 'Switzerland', 'CHF', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ddc46e01-c264-4e3c-9fb1-9914c0a3b0fe', 2, null, 73, 'Switzerland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('523cb4df-71d3-416d-80e9-192d338fede8', 4, 154, 73, 'Switzerland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d1d31d3a-b9c0-423f-a985-fb6ecff33a1d', 'ACTIVE', 'switzerland@iaeste.ch', 'NS.Switzerland@iaeste.org', 'f8e90471075b5a29d9b77f075a09429e40309214f339e226e23b198c93307403', 'dbefc0b4-7caa-4fb5-8664-649c1f7cc1aa', 'NS', 'Switzerland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('364b1581-1546-47fb-9f3c-aa38e639a556', 73, 154, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b977b640-4695-4347-a603-38ed587a3e3d', 73, 155, 1);
-- Completed generating test data for Switzerland

-- Generating Test data for Syria
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SY', 'Syria', 'Syria', 'SYP', 1965, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c4990972-901c-44d2-be8e-042cead84491', 2, null, 74, 'Syria');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ddc44f18-401d-46a9-b60c-c42fa6db1958', 4, 156, 74, 'Syria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fe3be799-24e6-4c9d-9274-f751b4e35e6f', 'ACTIVE', 'syria@iaeste.sy', 'NS.Syria@iaeste.org', '0578d8d94ba901cda68f2f98cda08e24ecbdafa39e08900036dc62ca4ca9b725', 'bc415feb-f9da-4042-952c-e66a01d02e78', 'NS', 'Syria');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('c2867937-3f32-41c9-ae7c-b00b36efe9e8', 74, 156, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('58beccf9-1319-4690-9f70-80375f7331a1', 74, 157, 1);
-- Completed generating test data for Syria

-- Generating Test data for Tajikistan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TJ', 'Tajikistan', 'Tajikistan', 'TJS', 1992, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('12551323-0ed7-44b0-8386-59f5e60553f2', 2, null, 75, 'Tajikistan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('5209399e-07a4-451e-92f6-6869ee3e201d', 4, 158, 75, 'Tajikistan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('6a8b7248-1320-4b85-b312-b2fa5dcd9a97', 'ACTIVE', 'tajikistan@iaeste.tj', 'NS.Tajikistan@iaeste.org', 'f9cf85dc5db9524f0b182139bdb6c1902ee71630248063ac295b08f55a544a4c', '9075d770-584f-490d-b97c-46af49057656', 'NS', 'Tajikistan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e6ae4e5b-d765-48d9-8c83-acedbf2657d5', 75, 158, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2d01e29a-d634-4b15-acb5-1a924a480a7b', 75, 159, 1);
-- Completed generating test data for Tajikistan

-- Generating Test data for Tanzania
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TZ', 'Tanzania', 'Tanzania', 'TZS', 2007, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('bd0e94e2-fc66-400e-996f-06de6eae8292', 2, null, 76, 'Tanzania');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('22012cef-863f-45a2-adc1-168779ab587e', 4, 160, 76, 'Tanzania');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('57f41014-b617-42ef-80b5-15f22e96687b', 'ACTIVE', 'tanzania@iaeste.tz', 'NS.Tanzania@iaeste.org', '5e2babb3d2d4207a9bac9270acdcc6a4749765ae28dea90607cc6d526e83bc70', '94bbf205-4e8f-4f58-9219-a68bcf8cfc06', 'NS', 'Tanzania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('798d5da3-0053-41a6-ba44-792d292190cc', 76, 160, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e1a99569-5c97-4a85-9713-0c94f16d4a59', 76, 161, 1);
-- Completed generating test data for Tanzania

-- Generating Test data for Thailand
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TH', 'Thailand', 'Thailand', 'THB', 1978, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f8f550a5-a3ba-4d7f-9263-1183098f7831', 2, null, 77, 'Thailand');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f0d692ac-b2f3-4c01-813b-e7636f5ca969', 4, 162, 77, 'Thailand');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c4524146-c8af-44cf-9440-05b779971293', 'ACTIVE', 'thailand@iaeste.th', 'NS.Thailand@iaeste.org', '0717672022c4ba2b71ceda58b72553c7c190c8c3e6a00590fd575817a7cdb437', 'a21560a9-4a35-4eee-8486-41fcc93c0644', 'NS', 'Thailand');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a1b75209-3675-469c-af16-896f7c462df9', 77, 162, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('c252ee82-08ce-419f-bca4-878489fb3dab', 77, 163, 1);
-- Completed generating test data for Thailand

-- Generating Test data for Tunisia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TN', 'Tunisia', 'Tunisia', 'TND', 1959, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('59f04a7e-2e80-492b-b785-2f041580fd01', 2, null, 78, 'Tunisia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('24e523a7-aed2-4f13-ae6d-c1f1c01e9776', 4, 164, 78, 'Tunisia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('8d7b54e0-24bd-4237-873f-e16d9eb0e63d', 'ACTIVE', 'tunisia@iaeste.tn', 'NS.Tunisia@iaeste.org', '391f37642131ee282235f668431102cd29c6572153326acefedfc6bc4deb58c9', '02a50a50-2b57-43c7-9045-7b7e50c182b2', 'NS', 'Tunisia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9f7303c1-87ac-4027-9f22-a8f27e165bfc', 78, 164, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0a1ed654-d457-4821-b102-17b15f5f9ec5', 78, 165, 1);
-- Completed generating test data for Tunisia

-- Generating Test data for Turkey
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TR', 'Turkey', 'Turkey', 'TRY', 1955, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('bec6ba71-282c-4681-a46b-afbdbaf8d863', 2, null, 79, 'Turkey');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('42732c68-9d2d-48e6-b06d-d8520f349df7', 4, 166, 79, 'Turkey');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fe292103-aa15-476a-a7ea-71c5544228fd', 'ACTIVE', 'turkey@iaeste.tr', 'NS.Turkey@iaeste.org', '98dde175b5d785d9fee56fdd1903e5d6e593bc87739a8c4e066cef9d22485894', 'ccc7c826-0fc4-45e2-aa90-52ce695b45d4', 'NS', 'Turkey');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e7275732-f667-47de-ae0e-579d8604cde9', 79, 166, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('16931979-2eb0-4e2f-98c5-1a87b63f2d12', 79, 167, 1);
-- Completed generating test data for Turkey

-- Generating Test data for Ukraine
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('UA', 'Ukraine', 'Ukraine', 'UAH', 1994, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('eea96d05-6cd0-4bb7-8bed-3c4e7dadfed2', 2, null, 80, 'Ukraine');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('3dd9a7a8-ad8c-47e4-b54f-c5a2759cfe84', 4, 168, 80, 'Ukraine');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('295234e8-aab9-41e8-a4aa-388a1b2d2047', 'ACTIVE', 'ukraine@iaeste.ua', 'NS.Ukraine@iaeste.org', '08c3cba753cbd41d1f59e9fa4de3ab180265a692efaa453c7ad347c9e266d32f', '7a1d3edf-7dbc-4f49-8a52-9915c8c89eb5', 'NS', 'Ukraine');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('76455bca-8c52-44d9-9920-87799c812fb3', 80, 168, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('96019d82-148b-4af5-85c7-102649609981', 80, 169, 1);
-- Completed generating test data for Ukraine

-- Generating Test data for UnitedArabEmirates
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AE', 'UnitedArabEmirates', 'UnitedArabEmirates', 'AED', 2000, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c218e924-f123-4144-b871-8bd1097659c4', 2, null, 81, 'UnitedArabEmirates');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a4c71aee-09c0-401b-a547-d8ed4491e8e2', 4, 170, 81, 'UnitedArabEmirates');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('db54b05d-fa69-4859-ae7b-0ed2b4cb30aa', 'ACTIVE', 'unitedarabemirates@iaeste.ae', 'NS.UnitedArabEmirates@iaeste.org', '1e3b968b3e8c66d5c40e4dc145b2784e7ddab4d2e26c9b057c7360f2ca492ead', 'e294e665-3899-419f-9934-c60a514eb76d', 'NS', 'UnitedArabEmirates');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a6dc77fe-4e9a-4b77-82a5-9a7a3e6e0073', 81, 170, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('20644acb-d39b-4838-b9a1-cb6da097ab11', 81, 171, 1);
-- Completed generating test data for UnitedArabEmirates

-- Generating Test data for UnitedKingdom
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('UK', 'UnitedKingdom', 'UnitedKingdom', 'GBP', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('389e6fbb-9e69-4987-aafe-2d9497798ee7', 2, null, 82, 'UnitedKingdom');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9bcf5332-cd41-44f0-b902-e853c291a436', 4, 172, 82, 'UnitedKingdom');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('26f31463-e2a9-462f-8e0b-98733f064946', 'ACTIVE', 'unitedkingdom@iaeste.uk', 'NS.UnitedKingdom@iaeste.org', '23f7af99bee7858f5e72c348a9cc7108fecd9b5b4aad0a6d2d4d87df1d9b8fbf', '32682260-e130-405e-900c-8f73d8b7c212', 'NS', 'UnitedKingdom');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6d68494c-2201-4930-a51b-17eb73b67998', 82, 172, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3c614a4a-8514-49a0-b73f-4c55d8c66fe6', 82, 173, 1);
-- Completed generating test data for UnitedKingdom

-- Generating Test data for USA
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('US', 'USA', 'USA', 'USD', 1950, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0b403d38-e454-45f3-b2b4-876c4e220832', 2, null, 83, 'USA');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('eed907ec-bfa3-46c7-aadf-4bc313e93e80', 4, 174, 83, 'USA');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a047c1a0-861d-491a-9def-b34022dcdaab', 'ACTIVE', 'usa@iaeste.us', 'NS.USA@iaeste.org', '06d6920d9c5a3b20943428cf10ab6915838578503784662ccd83c0e0663c4844', 'dbe8953b-a125-4f32-81f2-16458408dbc0', 'NS', 'USA');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4f31d4f6-7303-4c4a-8aa1-7ab1f30d6740', 83, 174, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('364c5697-77aa-4622-b1db-97141f7ed49d', 83, 175, 1);
-- Completed generating test data for USA

-- Generating Test data for Uzbekistan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('UZ', 'Uzbekistan', 'Uzbekistan', 'UZS', 1997, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('07e36800-de19-4c50-b1c4-d3e5c8e33ec7', 2, null, 84, 'Uzbekistan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1b4a28d9-8ef3-42dc-86e1-b5043c3f1b10', 4, 176, 84, 'Uzbekistan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2ac40e27-bad5-4ce2-aa33-c05a3d409c11', 'ACTIVE', 'uzbekistan@iaeste.uz', 'NS.Uzbekistan@iaeste.org', '7f8939d26f8084425221a81f2e1788eb385d297f7e4df6f4cc4624591d2e6884', '27769261-f5d1-4954-923d-e94feb981bc4', 'NS', 'Uzbekistan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a1e66ead-de39-48ff-b4bf-e0385e47ae66', 84, 176, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4e9111b7-942e-4b0e-b208-adae68e76b5e', 84, 177, 1);
-- Completed generating test data for Uzbekistan

-- Generating Test data for Vietnam
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('VN', 'Vietnam', 'Vietnam', 'VND', 2006, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2fe23724-157e-4017-8946-0a3a2bef3fa8', 2, null, 85, 'Vietnam');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('67fc0148-99c9-4991-bf0c-ca9239732e88', 4, 178, 85, 'Vietnam');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('18e5e4b3-1801-40bc-b847-c70ccb5408bf', 'ACTIVE', 'vietnam@iaeste.vn', 'NS.Vietnam@iaeste.org', '2f7786583e4389eee91b6993559edbe649a5e354a9317a6c5ba8137f058e9f90', 'c5ed7382-db6a-4211-bd4c-b1ee96160c7b', 'NS', 'Vietnam');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6d2a8c46-3a5b-4576-9b3d-11afaecc7d86', 85, 178, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('30119c5f-133d-4d64-b928-bb4e3fb8816a', 85, 179, 1);
-- Completed generating test data for Vietnam

-- Generating Test data for WestBank
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('WB', 'WestBank', 'WestBank', 'ILS', 2009, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('063a150c-0ef6-4e9c-9b5a-f3d3d3e4e5c6', 2, null, 86, 'WestBank');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('16d990f9-8b82-4ca4-bb4c-a8cdb275fe4f', 4, 180, 86, 'WestBank');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('9c7b359b-b849-4b2c-a22d-1cf92d04a8a7', 'ACTIVE', 'westbank@iaeste.wb', 'NS.WestBank@iaeste.org', 'ba7a355fcbbe47847dcc096599db95f44314e92fa48346bc7359b989c544ced9', 'fea929f2-3507-402a-b1da-afd280a7ae25', 'NS', 'WestBank');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('d4085ac6-557d-494a-b202-ef50cdf977e1', 86, 180, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b95026ab-a2b6-41c5-9b70-4edea2a887f8', 86, 181, 1);
-- Completed generating test data for WestBank

-- Austria LC subgroup
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2688c1ca-2113-4a4b-aef1-27da26dca479', 5, 16, 4, 'Austria LC1');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2f42c597-059a-42c3-b9f8-d8735f7cd7c3', 4, 182, 1);

-- For our testing, we need to suspend a single Country -> First country, Albania
update groups set status = 'SUSPENDED' where id = 10;
-- Four our testing, we need to suspend a single Account, First available, Argentina
update users set status = 'SUSPENDED' where id = 2;

-- Australia is set to be Administrator
insert into user_to_group (external_id, user_id, group_id, role_id) values ('440c0460-9f7f-41d7-8986-94ede5e35ea0',  3, 3, 1);
-- UnitedKingdom is set as SID Coordinator
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3f83b074-0771-40e2-8337-910d2c75bbf8', 82, 4, 1);
-- Denmark is set as IDT Coordinator
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9ab8d850-b096-4b88-b56c-3870128c618a', 19, 5, 1);
-- Germany is set as Alumni Coordinator, for our testing we're suspending this group
insert into user_to_group (external_id, user_id, group_id, role_id) values ('48b86ad0-a8c9-4f79-840b-2d930c76ffd2', 26, 6, 1);
update groups set status = 'SUSPENDED' where id = 6;

-- For the Mailing List to work, we need the two virtual mailing lists, and to
-- update the existing Groups & UserGroup relations to have the correct flags.
insert into mailing_lists (id, list_address, group_id, subject_prefix, list_type, replyto_style, status) values (1, 'ncs@iaeste.net', 2, 'NCS', 'PRIVATE_LIST', 'REPLY_TO_SENDER', 'ACTIVE');
insert into mailing_lists (id, list_address, group_id, subject_prefix, list_type, replyto_style, status) values (2, 'announce@iaeste.net', 2, 'ANNOUNCE', 'PRIVATE_LIST', 'NO_REPLY', 'ACTIVE');
insert into aliases (id, external_id, group_id, alias_address, deprecated, expires) values (1, '30f0efd4-63d0-4f6e-9a97-e7621951b1dc', 3, 'president', 'active', null);
update user_to_group set on_public_list = true, on_private_list = true, write_to_private_list = true;
update groups set public_list = true where grouptype_id in (0, 1, 3, 4, 5, 6);
update groups set private_list = true where grouptype_id in (0, 2, 3, 5, 6);

-- Non Member Country, for our Committee Testing
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AA', 'Aardvark', 'Aardvark', 'EUR', null, 'LISTED');
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AB', 'Absinthe', 'Absinthe', 'EUR', null, 'LISTED');
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AC', 'Accolade', 'Accolade', 'EUR', null, 'LISTED');

-- Add new Table called FileData - which has a foreign key into the Files table.
-- This way, we can read the File table without knowledge of data and later read
-- out the data when requested.
-- Files & Folders
-- Create Folder Structure for Board, 2 NC, 2 LC & SID/IDT
-- Board ID: 3
-- NC Tunisia ID: 165
-- SID ID: 4
-- Create File for same
-- Root Folder eid = afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0
-- Make sure that we have at least 3 Folder depth, with last being public, middle or first being private
-- Folder Structure for our tests:
--  Root                                     EId: "afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0"
--    - Board Minutes PUBLIC                 EId: "6adc3bac-c85c-4dfa-b9d8-3d9b848314af"
--       - AC PUBLIC                         EId: "c9bde21a-011e-42e4-8985-b1da95c0fbdf"
--          - 2014 PUBLIC                    EId: "af0a0c34-7037-4153-a707-65a1ed0b380b"
--       - Finances PROTECTED                EId: "b54a005d-d9cd-4456-9263-7ab3833ab303"
--          - 2014 PUBLIC                    EId: "4126e3c4-2e79-4ee1-a73f-a3ab5f9e52a5"
--          - 2015 PROTECTED                 EId: "9dd06a36-4e02-4a7d-9a46-da78648eaaae"
--    - SID 2014 PUBLIC                      EId: "d8d6d7eb-754a-4e3b-8d4d-7657339096d1"
--       - Administration PUBLIC             EId: "01749318-9f20-4369-bd05-555c18e643ad"
--       - IWUG PUBLIC                       EId: "888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2"
--    - SID 2015 PROTECTED                   EId: "60ba5c47-3dce-41a1-94f9-e0dafe2f717a"
--       - Administration PUBLIC             EId: "a4fa7715-3a10-470f-8538-e8ee13666819"
--       - IWUG PUBLIC                       EId: "f50bed53-7295-4b91-8746-78136df0a189"
--    - NC Tunisia Travel Documents PUBLIC   EId: "94a6d74f-c510-4302-83fd-cd79ea8a4c9a"
--       - 2015 PUBLIC                       EId: "b0ed8121-f359-4bc5-8ce8-bc5c4a7de536"
--          - EU PUBLIC                      EId: "ed5e0487-0605-428d-b40c-78e88df85d8d"
--          - Other PUBLIC                   EId: "0328281b-cf80-4c7a-8ad7-04721eaa45aa"
--       - 2014 PROTECTED                    EId: "df7a45f5-2aed-4529-a527-488ad538e03a"
--          - EU PUBLIC                      EId: "30a460be-641e-4cb9-a336-5eab00c19c3a"
--          - Other PUBLIC                   EId: "bb7953a8-8b7a-43a2-84fe-dca74291abd3"
--
-- For all of the above, we need a sample file also. This will have data stored
-- in a new table as mentioned above. As the database is configured in production
-- to use data replication, it is a more safe and reliable mechanism to use this,
-- than to have to set up backup routines which is not synchronized with the database.
-- Note; for this to work correctly, we need to apply the tree in the correct levels,
--       otherwise we cannot retrieve the Id's from non-committed folders!

  -- Board
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('6adc3bac-c85c-4dfa-b9d8-3d9b848314af', (select id from folders where external_id = 'afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0'), 3, 'Minutes', 'PUBLIC');
  -- SID
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('d8d6d7eb-754a-4e3b-8d4d-7657339096d1', (select id from folders where external_id = 'afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0'), 4, '2014', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('60ba5c47-3dce-41a1-94f9-e0dafe2f717a', (select id from folders where external_id = 'afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0'), 165, '2015', 'PROTECTED');
  -- NC Tunisia
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('94a6d74f-c510-4302-83fd-cd79ea8a4c9a', (select id from folders where external_id = 'afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0'), 165, 'Travel Documents', 'PUBLIC');

insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  -- Board
  ('c9bde21a-011e-42e4-8985-b1da95c0fbdf', (select id from folders where external_id = '6adc3bac-c85c-4dfa-b9d8-3d9b848314af'), 3, 'AC', 'PUBLIC');
  insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('b54a005d-d9cd-4456-9263-7ab3833ab303', (select id from folders where external_id = '6adc3bac-c85c-4dfa-b9d8-3d9b848314af'), 3, 'Finances', 'PROTECTED');
  -- SID
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('01749318-9f20-4369-bd05-555c18e643ad', (select id from folders where external_id = 'd8d6d7eb-754a-4e3b-8d4d-7657339096d1'), 4, 'Administration', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2', (select id from folders where external_id = 'd8d6d7eb-754a-4e3b-8d4d-7657339096d1'), 4, 'IWUG', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('a4fa7715-3a10-470f-8538-e8ee13666819', (select id from folders where external_id = '60ba5c47-3dce-41a1-94f9-e0dafe2f717a'), 4, 'Administration', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('f50bed53-7295-4b91-8746-78136df0a189', (select id from folders where external_id = '60ba5c47-3dce-41a1-94f9-e0dafe2f717a'), 4, 'IWUG', 'PUBLIC');
  -- NC Tunisia
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('b0ed8121-f359-4bc5-8ce8-bc5c4a7de536', (select id from folders where external_id = '94a6d74f-c510-4302-83fd-cd79ea8a4c9a'), 165, '2015', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('df7a45f5-2aed-4529-a527-488ad538e03a', (select id from folders where external_id = '94a6d74f-c510-4302-83fd-cd79ea8a4c9a'), 165, '2014', 'PROTECTED');

insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  -- Board
  ('af0a0c34-7037-4153-a707-65a1ed0b380b', (select id from folders where external_id = 'c9bde21a-011e-42e4-8985-b1da95c0fbdf'), 3, '2014', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('4126e3c4-2e79-4ee1-a73f-a3ab5f9e52a5', (select id from folders where external_id = 'b54a005d-d9cd-4456-9263-7ab3833ab303'), 3, '2014', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('9dd06a36-4e02-4a7d-9a46-da78648eaaae', (select id from folders where external_id = 'b54a005d-d9cd-4456-9263-7ab3833ab303'), 3, '2015', 'PROTECTED');
  -- NC Tunisia
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('ed5e0487-0605-428d-b40c-78e88df85d8d', (select id from folders where external_id = 'b0ed8121-f359-4bc5-8ce8-bc5c4a7de536'), 165, 'EU', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('0328281b-cf80-4c7a-8ad7-04721eaa45aa', (select id from folders where external_id = 'b0ed8121-f359-4bc5-8ce8-bc5c4a7de536'), 165, 'Other', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('30a460be-641e-4cb9-a336-5eab00c19c3a', (select id from folders where external_id = 'df7a45f5-2aed-4529-a527-488ad538e03a'), 165, 'EU', 'PUBLIC');
insert into folders (external_id, parent_id, group_id, foldername, privacy) values
  ('bb7953a8-8b7a-43a2-84fe-dca74291abd3', (select id from folders where external_id = 'df7a45f5-2aed-4529-a527-488ad538e03a'), 165, 'Other', 'PUBLIC');

-- Files & File Data; UserId is set to 1, as it is only a marker for the creator of the file.
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('c79898dc-590c-44a7-8fc0-b5c4e83cb092',   3, 1, 'PUBLIC',    (select id from folders where external_id = '6adc3bac-c85c-4dfa-b9d8-3d9b848314af'), 'bla01.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('78fe35da-7fe1-44a2-b155-8630af1625c6',   3, 1, 'PROTECTED', (select id from folders where external_id = '6adc3bac-c85c-4dfa-b9d8-3d9b848314af'), 'bla02.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('81c2ca20-c46c-49fd-9b87-f40650e76cc1',   3, 1, 'PUBLIC',    (select id from folders where external_id = 'c9bde21a-011e-42e4-8985-b1da95c0fbdf'), 'bla03.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('4f5082ea-0210-4333-aa09-35caad27eb07',   3, 1, 'PROTECTED', (select id from folders where external_id = 'c9bde21a-011e-42e4-8985-b1da95c0fbdf'), 'bla04.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('a4339ac1-9544-4db3-94d5-61fa9510308e',   3, 1, 'PUBLIC',    (select id from folders where external_id = 'af0a0c34-7037-4153-a707-65a1ed0b380b'), 'bla05.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('b77ac424-2091-488d-8f89-d696d7aab65b',   3, 1, 'PROTECTED', (select id from folders where external_id = 'af0a0c34-7037-4153-a707-65a1ed0b380b'), 'bla06.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('b809da57-5dbe-4528-93a6-7c78b30a2838',   3, 1, 'PUBLIC',    (select id from folders where external_id = 'b54a005d-d9cd-4456-9263-7ab3833ab303'), 'bla07.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('cd0eeb3d-550d-4e39-a860-d6ced061fa68',   3, 1, 'PROTECTED', (select id from folders where external_id = 'b54a005d-d9cd-4456-9263-7ab3833ab303'), 'bla08.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('523db27d-30e1-4c3b-8c12-85546e12cf28',   3, 1, 'PUBLIC',    (select id from folders where external_id = '4126e3c4-2e79-4ee1-a73f-a3ab5f9e52a5'), 'bla09.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('30dafa8c-630f-49ca-9cac-d77ab3de4caf',   3, 1, 'PROTECTED', (select id from folders where external_id = '4126e3c4-2e79-4ee1-a73f-a3ab5f9e52a5'), 'bla10.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('e4efbbbd-37b0-4251-9dce-bc764b94747a',   3, 1, 'PUBLIC',    (select id from folders where external_id = '9dd06a36-4e02-4a7d-9a46-da78648eaaae'), 'bla11.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('1c6cc336-128f-459e-9eea-d39a768ac58d',   3, 1, 'PROTECTED', (select id from folders where external_id = '9dd06a36-4e02-4a7d-9a46-da78648eaaae'), 'bla12.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('a8870b01-d07d-4325-a6c3-887903fe56c0',   4, 1, 'PUBLIC',    (select id from folders where external_id = 'd8d6d7eb-754a-4e3b-8d4d-7657339096d1'), 'bla13.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('8e338f4b-24c6-4d5a-ba56-ff229fcbb27a',   4, 1, 'PROTECTED', (select id from folders where external_id = 'd8d6d7eb-754a-4e3b-8d4d-7657339096d1'), 'bla14.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('3266da15-bb21-4f01-a02f-6258de1f5f59',   4, 1, 'PUBLIC',    (select id from folders where external_id = '01749318-9f20-4369-bd05-555c18e643ad'), 'bla15.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('017855a9-70c6-4c27-9b73-ef8eaf721dc4',   4, 1, 'PROTECTED', (select id from folders where external_id = '01749318-9f20-4369-bd05-555c18e643ad'), 'bla16.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('824f2080-5ecf-458f-80aa-b8cdc52b15ec',   4, 1, 'PUBLIC',    (select id from folders where external_id = '888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2'), 'bla17.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('0aa391b9-8bfa-4022-afac-ccf266afa6f4',   4, 1, 'PROTECTED', (select id from folders where external_id = '888f6c83-fcae-4e4a-9f8c-4fb02c2ec8e2'), 'bla18.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('91455b31-b962-4847-bff1-edc5a2a4cd86',   4, 1, 'PUBLIC',    (select id from folders where external_id = '60ba5c47-3dce-41a1-94f9-e0dafe2f717a'), 'bla19.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('8b1aa34d-eb60-47f1-b7ea-05ee9336ec44',   4, 1, 'PROTECTED', (select id from folders where external_id = '60ba5c47-3dce-41a1-94f9-e0dafe2f717a'), 'bla20.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('9c7dcf0c-57fd-4e09-bfdb-fe3c494dbb6a',   4, 1, 'PUBLIC',    (select id from folders where external_id = 'a4fa7715-3a10-470f-8538-e8ee13666819'), 'bla21.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('ad93425d-8453-405a-9201-5ab7bde6856c',   4, 1, 'PROTECTED', (select id from folders where external_id = 'a4fa7715-3a10-470f-8538-e8ee13666819'), 'bla22.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('744ac803-8903-4b5c-b402-dbd5866439d0',   4, 1, 'PUBLIC',    (select id from folders where external_id = 'f50bed53-7295-4b91-8746-78136df0a189'), 'bla23.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('ec67f5b0-03e6-421b-ae28-c1e0fe389fb1',   4, 1, 'PROTECTED', (select id from folders where external_id = 'f50bed53-7295-4b91-8746-78136df0a189'), 'bla24.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('ec64c7ea-7cf2-452b-bf45-9bb5e860f511', 165, 1, 'PUBLIC',    (select id from folders where external_id = '94a6d74f-c510-4302-83fd-cd79ea8a4c9a'), 'bla25.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('3d2ce586-5149-46df-8b1a-38be81b0774c', 165, 1, 'PROTECTED', (select id from folders where external_id = '94a6d74f-c510-4302-83fd-cd79ea8a4c9a'), 'bla26.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('f35ee28a-351d-4f97-8b7c-591a6a1a4a10', 165, 1, 'PUBLIC',    (select id from folders where external_id = 'b0ed8121-f359-4bc5-8ce8-bc5c4a7de536'), 'bla27.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('6f4c52a1-d29a-4a5c-adaf-3e0fd18354ef', 165, 1, 'PROTECTED', (select id from folders where external_id = 'b0ed8121-f359-4bc5-8ce8-bc5c4a7de536'), 'bla28.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('b85b2efc-bc77-4f07-b79c-81f8cfaf4e4c', 165, 1, 'PUBLIC',    (select id from folders where external_id = 'ed5e0487-0605-428d-b40c-78e88df85d8d'), 'bla29.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('86b7a8be-910c-4874-8e00-ed44d09e8eb9', 165, 1, 'PROTECTED', (select id from folders where external_id = 'ed5e0487-0605-428d-b40c-78e88df85d8d'), 'bla30.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('76c8b70d-65bf-4fbb-9223-2b841cffdc68', 165, 1, 'PUBLIC',    (select id from folders where external_id = '0328281b-cf80-4c7a-8ad7-04721eaa45aa'), 'bla31.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('cc5eff9b-6efe-4c4d-8908-ca60a4c2f018', 165, 1, 'PROTECTED', (select id from folders where external_id = '0328281b-cf80-4c7a-8ad7-04721eaa45aa'), 'bla32.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('6b1710a7-a663-41b6-843c-401545758fbf', 165, 1, 'PUBLIC',    (select id from folders where external_id = 'df7a45f5-2aed-4529-a527-488ad538e03a'), 'bla33.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('28aef385-1f20-4f3e-a063-43a5a3921004', 165, 1, 'PROTECTED', (select id from folders where external_id = 'df7a45f5-2aed-4529-a527-488ad538e03a'), 'bla34.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('064472ab-9969-4c76-bb52-a7cf5634836e', 165, 1, 'PUBLIC',    (select id from folders where external_id = '30a460be-641e-4cb9-a336-5eab00c19c3a'), 'bla35.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('300e67f3-20a4-4e90-849f-400fb66235fb', 165, 1, 'PROTECTED', (select id from folders where external_id = '30a460be-641e-4cb9-a336-5eab00c19c3a'), 'bla36.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('1138e2fa-2dc3-4ad2-ae4c-e505f466170e', 165, 1, 'PUBLIC',    (select id from folders where external_id = 'bb7953a8-8b7a-43a2-84fe-dca74291abd3'), 'bla37.txt');
insert into files (external_id, group_id, user_id, privacy, folder_id, filename) values
  ('db830305-bd0e-4708-a1d1-0a6d94eb01aa', 165, 1, 'PROTECTED', (select id from folders where external_id = 'bb7953a8-8b7a-43a2-84fe-dca74291abd3'), 'bla38.txt');
