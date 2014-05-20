-- ============================================================================
-- Preparing to create test data for users.
-- ============================================================================

-- First reset the existing tables & sequences, regardlessly!
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
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('673f38f2-324d-42d9-bef8-651412d1515b', 'ACTIVE', 'albania@iaeste.al', 'NS.Albania@iaeste.org', '341815eb7b014738f374457808ea493878ad5e1025551a874129078cf205cada', '4ad035ea-ae3a-48dd-913c-15dbd1c4f1e9', 'NS', 'Albania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e34f0917-9ee4-4ec0-a416-0d6a694dcf82', 1, 10, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fb3296de-250f-43ee-a352-76a95f4fbb7a', 1, 11, 1);
-- Completed generating test data for Albania

-- Generating Test data for Argentina
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AR', 'Argentina', 'Argentina', 'ARS', 1961, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('902cf65a-4220-4428-8167-d6695c9b84d9', 2, null, 2, 'Argentina');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('125041b8-d3f5-48ba-84e6-c3cc0e83a86a', 4, 12, 2, 'Argentina');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b9abcede-36fa-4b8f-ba2f-82394f7dd879', 'ACTIVE', 'argentina@iaeste.ar', 'NS.Argentina@iaeste.org', '47ef62d88393740beb716cf97244e2efe034418595e6326c962f0caf2ca67060', '1849aec7-4e29-4174-981d-3f9f6f2163ee', 'NS', 'Argentina');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('54e1a3b1-1854-4455-8e37-12418b51235d', 2, 12, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e180cd2e-9c52-4b23-801f-b21023a59663', 2, 13, 1);
-- Completed generating test data for Argentina

-- Generating Test data for Australia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AU', 'Australia', 'Australia', 'AUD', 1996, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1f6fc75f-f8af-433c-bf9e-bfecb6970239', 2, null, 3, 'Australia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('dc2eff97-c6ef-4915-b2c8-9bde039c62ca', 4, 14, 3, 'Australia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('68b4a77c-f36e-44f3-8de6-b8d5dcb50970', 'ACTIVE', 'australia@iaeste.au', 'NS.Australia@iaeste.org', 'fd6489a76fad2ac1f268342b51658e49b3c7a211ed51ffab18e29afa7d14c8cd', '6aee18c7-66fc-40e0-a377-2155595aa93d', 'NS', 'Australia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('835f79bc-0f73-48d7-b23d-b57c2d74fa2d', 3, 14, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('467f693c-1033-4104-8a3b-4179e322a0bc', 3, 15, 1);
-- Completed generating test data for Australia

-- Generating Test data for Austria
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AT', 'Austria', 'Austria', 'EUR', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('da799505-cbf4-4bb8-bbcc-7e7705a7892f', 2, null, 4, 'Austria');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9f2c4db6-38c9-4a2f-bdaf-141bd1eb4c13', 4, 16, 4, 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('5f2058ef-d284-4670-80a0-f3baad28a6c6', 'ACTIVE', 'austria@iaeste.at', 'NS.Austria@iaeste.org', 'b3d6fb7263c6c2c36ccf38fd6834cd3ce39da3f2f3e78e6f38817ecaa6202d8e', '05291b0f-02a3-4161-9442-978abec74985', 'NS', 'Austria');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a1a3184e-0ca7-4446-b7ef-aff14f3403aa', 4, 16, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e1fe2021-798f-427b-8363-35c53af4ca1b', 4, 17, 1);
-- Completed generating test data for Austria

-- Generating Test data for Azerbaijan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AZ', 'Azerbaijan', 'Azerbaijan', 'AZN', 2010, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c64483d8-2f73-48bc-8030-676e28e83323', 2, null, 4, 'Azerbaijan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('df404581-d167-4c93-8d3f-9c71e71a8e92', 4, 18, 4, 'Azerbaijan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('13fb0d94-73cd-4293-b1f8-85ec476dc89f', 'ACTIVE', 'azerbaijan@iaeste.az', 'NS.Azerbaijan@iaeste.org', '705be284c42a04e0ce8d6af32e94bbfe74ac24d51ba87e645ae48a83043905ef', 'e5568b4e-dd80-4951-9efc-fbeaa91c621b', 'NS', 'Azerbaijan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('05fc1b8a-7f6b-4b0f-84e8-792bbbfacacc', 5, 18, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6a5894aa-5f91-433c-8524-8382d2f104ed', 5, 19, 1);
-- Completed generating test data for Azerbaijan

-- Generating Test data for Bangladesh.Aviation
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BD', 'Bangladesh.Aviation', 'Bangladesh.Aviation', 'BDT', 2013, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('44c9edd1-86d1-495a-876b-95b8c6c322c8', 2, null, 6, 'Bangladesh.Aviation');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f2de0d7a-e502-49ea-afe1-308884d5f22f', 4, 20, 6, 'Bangladesh.Aviation');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('3948ae8d-9bfd-4613-802b-c8996843b1f4', 'ACTIVE', 'bangladesh.aviation@iaeste.bd', 'NS.Bangladesh.Aviation@iaeste.org', '265e6af9bc2015ff32712ea02d1ad2368a5eb7c7c823b66f5bf8470b45c91fa1', '92d23e5f-a4d9-46db-95d3-74afed2e37ea', 'NS', 'Bangladesh.Aviation');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('91554e70-1004-470a-9f13-98fb6ba89725', 6, 20, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('94a23a9e-630d-4828-8e95-7981bdf11db0', 6, 21, 1);
-- Completed generating test data for Bangladesh.Aviation

-- Generating Test data for Belarus
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BY', 'Belarus', 'Belarus', 'BYR', 2000, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('fea5b4ff-58a5-4d9b-a981-7f9520d9f488', 2, null, 7, 'Belarus');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('da122a82-b195-4746-9652-5d9323f4a1f6', 4, 22, 7, 'Belarus');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('65bd883b-bed9-4bc6-9b46-bc3b9fc931fd', 'ACTIVE', 'belarus@iaeste.by', 'NS.Belarus@iaeste.org', '6f0ab171bc482a5efda032d65b6edec886f13c7cc69d3b1f4acaf8557c7df8ab', '366ad5b0-7ae3-43e1-b984-68fa3e7dc05c', 'NS', 'Belarus');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cbeb51df-c8df-43d8-b272-f557408366da', 7, 22, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('08434b07-2d09-40ce-8283-c1480f00eb69', 7, 23, 1);
-- Completed generating test data for Belarus

-- Generating Test data for Belgium
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BE', 'Belgium', 'Belgium', 'EUR', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e375c184-dd9b-422d-ad26-7e03a3ba7dca', 2, null, 8, 'Belgium');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('02b5ddc0-541f-47eb-ae77-6d3b9a33f2a9', 4, 24, 8, 'Belgium');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('85b43aa5-8d99-48da-b9dd-ce9932bb2be1', 'ACTIVE', 'belgium@iaeste.be', 'NS.Belgium@iaeste.org', '725a027eecfb9a519b1fe2a0938e1e038f881035ce1d8e933251060895bc8070', 'f7ddb970-d93d-4ccb-a5a8-7307dcd89920', 'NS', 'Belgium');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('586ad291-74c0-4f52-9031-cf653e526e17', 8, 24, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('329cefbe-9f6a-4afa-81d8-a55f6318079d', 8, 25, 1);
-- Completed generating test data for Belgium

-- Generating Test data for Bolivia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BO', 'Bolivia', 'Bolivia', 'BOB', 2013, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a38b52a0-d05b-4bbb-a794-1ac5a17a04c1', 2, null, 9, 'Bolivia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('cac42b77-f69a-4e0e-966f-588a4960d231', 4, 26, 9, 'Bolivia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b146fe4c-dc3d-4626-a32b-29f34bfe6d1c', 'ACTIVE', 'bolivia@iaeste.bo', 'NS.Bolivia@iaeste.org', 'a0f50e6b818e0adbc3b5567aa1793f7b122514d4245f9cbae2eb6edaca649d93', 'f43760d9-72b9-4129-a464-a5a3a4b04027', 'NS', 'Bolivia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ca4faa6f-1b7a-4395-81a6-4f83df41a8a3', 9, 26, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('932ad701-055c-4a8c-aa92-8eee430617db', 9, 27, 1);
-- Completed generating test data for Bolivia

-- Generating Test data for BosniaandHerzegovina
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BA', 'BosniaandHerzegovina', 'BosniaandHerzegovina', 'BAM', 2000, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b11dea53-4350-4353-8008-27052e276917', 2, null, 10, 'BosniaandHerzegovina');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9da0c7b2-f3fa-40c8-89e9-126bb677a514', 4, 28, 10, 'BosniaandHerzegovina');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d780dbb3-d43e-49b2-b4e3-d9ca204615d3', 'ACTIVE', 'bosniaandherzegovina@iaeste.ba', 'NS.BosniaandHerzegovina@iaeste.org', 'de7d8b281c82bba6d6c9f7651fe7f8dad9d8899eedfd985bf9907ef1c8d61415', '53ed11f3-f688-4142-9386-2ad359fd0832', 'NS', 'BosniaandHerzegovina');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('97cb85b2-4c53-4beb-83ab-963568771e3b', 10, 28, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('65c1a98b-07f5-47fd-9627-c547b8bbda5c', 10, 29, 1);
-- Completed generating test data for BosniaandHerzegovina

-- Generating Test data for Brazil
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('BR', 'Brazil', 'Brazil', 'BRL', 1982, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1dd2f402-7fbc-4ca2-9e46-036edd95b914', 2, null, 11, 'Brazil');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('54523d72-a048-4e7b-8a49-d615377fc0f7', 4, 30, 11, 'Brazil');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('138142b1-ad04-4ea2-8a6e-36ab03506bc6', 'ACTIVE', 'brazil@iaeste.br', 'NS.Brazil@iaeste.org', '692823bebc0cea9139e26083c0c6b5da0532db9f745be85aeece2b64c2664597', '04dd7015-986b-4c46-95d9-346ae9f4e817', 'NS', 'Brazil');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('17a6b95e-2c51-4f7e-8f06-4219497b618a', 11, 30, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e0e644c1-de37-467d-8f0e-1f360668ea77', 11, 31, 1);
-- Manually added, we need to have someone who's working with International Groups
insert into user_to_group (external_id, user_id, group_id, role_id) values ('dd6635da-2483-433c-9663-8b7ab55fba82', 11,  3, 1);
-- Completed generating test data for Brazil

-- Generating Test data for Canada
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CA', 'Canada', 'Canada', 'CAD', 1953, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9fcebe71-d3fa-426e-b5ed-97d0cd4d4582', 2, null, 12, 'Canada');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('be543ebb-5052-4748-9542-4908f5769d25', 4, 32, 12, 'Canada');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('153ec144-8e7d-4138-81d1-cdb4e771245b', 'ACTIVE', 'canada@iaeste.ca', 'NS.Canada@iaeste.org', 'ee8261f4ab87347614a2d500d582b0536e082801f73584b594bd5e82f154263f', 'd891bcfa-9c04-4fe7-85de-87bdfa82044a', 'NS', 'Canada');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('06a4e199-cb44-41e4-8a9f-b53995f7940f', 12, 32, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1402c121-c438-4894-acc2-7ffe9ad3ba4e', 12, 33, 1);
-- Completed generating test data for Canada

-- Generating Test data for Chile
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CL', 'Chile', 'Chile', 'CLP', 2013, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('47d78e60-4370-4533-b936-152fc29698c5', 2, null, 13, 'Chile');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d28f856d-8b8c-4288-a193-a7dd4a2490a9', 4, 34, 13, 'Chile');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('133704f2-52ca-4550-b6e2-1a841a0ac0b6', 'ACTIVE', 'chile@iaeste.cl', 'NS.Chile@iaeste.org', 'a890c6bd0b7b0ee7b7b2c33c67922a2f4ae37aca54a676f3c6463d9b1836f6bf', 'ac6dacc3-e6bf-4ff0-8fdb-f17dd69b4f3f', 'NS', 'Chile');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9b9ef55e-529d-460b-af3c-1b564b570ecd', 13, 34, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e8b1b80c-f26d-4e0d-af46-87b0dad8dfcb', 13, 35, 1);
-- Completed generating test data for Chile

-- Generating Test data for China
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CN', 'China', 'China', 'CNY', 2000, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('98f3aed9-4246-4bfe-bf34-a88c173c7370', 2, null, 14, 'China');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('92219b23-3bc2-4593-928c-92aee1d8dc44', 4, 36, 14, 'China');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('e5dfd97e-36f4-47bb-9472-3137c84f6e7e', 'ACTIVE', 'china@iaeste.cn', 'NS.China@iaeste.org', 'bb8b937138154726ea464401b25d347dd673af3e173555e58903c99e9411b99f', '95e4af83-fbcc-4c06-aaf3-82f07098f52b', 'NS', 'China');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b988c4c5-df5a-411c-b869-193ee043a788', 14, 36, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('29be4afe-a489-4df3-a58e-5c4e8619af63', 14, 37, 1);
-- Completed generating test data for China

-- Generating Test data for Colombia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CO', 'Colombia', 'Colombia', 'COP', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e4012e2b-b444-4ead-9223-4efabbeaac68', 2, null, 15, 'Colombia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('34823d36-9b1d-4058-88f2-deaeb95f11ab', 4, 38, 15, 'Colombia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2119acdb-e595-444a-81e7-3cbbe6d867dd', 'ACTIVE', 'colombia@iaeste.co', 'NS.Colombia@iaeste.org', '3791e172b4de4e935da7dda50528d679e1a2ffc44d8d1e6bb1d435d0e102a12c', 'f39bd566-d279-4efd-992f-30426f8a9f85', 'NS', 'Colombia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('f2e5369b-9d0f-4132-ae04-ed0876071636', 15, 38, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('138b754c-9bbe-4e8c-9257-4e7e6ceef5b5', 15, 39, 1);
-- Completed generating test data for Colombia

-- Generating Test data for Croatia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('HR', 'Croatia', 'Croatia', 'HRK', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('aa681bc9-0707-4e3c-9cd1-d33c4af13f00', 2, null, 16, 'Croatia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a47fd465-bb39-4c8c-8a9e-d51671790567', 4, 40, 16, 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('e51853c3-cf6f-4875-b646-4eb9c9a8d0c9', 'ACTIVE', 'croatia@iaeste.hr', 'NS.Croatia@iaeste.org', '9c3171c3f854418597de0de37b75fb66fde3492764ab4654c5f423b91331caa7', '851f2bbd-4716-45dd-a398-69fb0e6ef843', 'NS', 'Croatia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('abebaecf-3870-4ade-b6d2-5ce84033fc36', 16, 40, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b618d07b-20c4-4382-9e58-a4106a227211', 16, 41, 1);
-- Completed generating test data for Croatia

-- Generating Test data for Cyprus
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CY', 'Cyprus', 'Cyprus', 'EUR', 1980, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('56dd0070-e1f3-403e-bf58-87f4b6b5b53e', 2, null, 17, 'Cyprus');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ff8b0c56-dfdc-43cf-9fb9-a000cbd14c56', 4, 42, 17, 'Cyprus');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d58b783a-c4b4-4ee3-b93c-0178dd626a37', 'ACTIVE', 'cyprus@iaeste.cy', 'NS.Cyprus@iaeste.org', '66a4340101ebc8e9eec1c236758919b9c6d2b619b138e462550791ba38005a1b', 'd557fd15-f438-4d88-9877-f039e794fe27', 'NS', 'Cyprus');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('7cd6485d-e255-4ecc-be11-f64633d222e8', 17, 42, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('87e8f405-b646-4522-8cb5-34fa5a65e2d2', 17, 43, 1);
-- Completed generating test data for Cyprus

-- Generating Test data for CzechRepublic
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CZ', 'CzechRepublic', 'CzechRepublic', 'CZK', 1965, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('04beae66-f474-4942-8daf-e22c3b2a9bd8', 2, null, 18, 'CzechRepublic');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('78206b95-d772-41fb-89fb-6e9993b05f80', 4, 44, 18, 'CzechRepublic');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fb1b3517-a893-45c1-a7da-906c31e2efe8', 'ACTIVE', 'czechrepublic@iaeste.cz', 'NS.CzechRepublic@iaeste.org', '8eb12706043842ce48b5b055edee35c2b2c14e9377bb56a5710f5145230a76aa', 'b3da679b-4131-4830-a041-f3536b36ff0f', 'NS', 'CzechRepublic');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ac79fe79-c87f-4900-b624-a0f24588c887', 18, 44, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9deaa63e-b729-4969-b068-ee2abfc80356', 18, 45, 1);
-- Completed generating test data for CzechRepublic

-- Generating Test data for Denmark
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('DK', 'Denmark', 'Denmark', 'DKK', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('23fdd892-7cc1-49f1-b8e5-dee37f16e74d', 2, null, 19, 'Denmark');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('82c0bf70-80d3-4fbb-9832-1c772ca300c5', 4, 46, 19, 'Denmark');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('3652586a-1a2b-4fa9-9b49-b22646e353bd', 'ACTIVE', 'denmark@iaeste.dk', 'NS.Denmark@iaeste.org', 'f1b21f07cb08e67680d6e382514591547130ad9a2979317a044ad0c78d4eaa3a', '9b8fef9b-749f-4e6a-86ce-4b2ae3fed2f0', 'NS', 'Denmark');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bcdda30b-aa0e-4fdf-93f7-50f7345fb5b2', 19, 46, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('09a511cf-9f76-4078-bb7e-863702cd0694', 19, 47, 1);
-- Completed generating test data for Denmark

-- Generating Test data for Ecuador
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('EC', 'Ecuador', 'Ecuador', 'USD', 1999, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c723609d-4623-459f-bd8c-7486a49b2364', 2, null, 20, 'Ecuador');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('06c51fe0-8d43-4a06-aa96-faaff3cdcce6', 4, 48, 20, 'Ecuador');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('76c10358-1b1b-4402-8855-c3fd03ea579c', 'ACTIVE', 'ecuador@iaeste.ec', 'NS.Ecuador@iaeste.org', 'aac930781bb498c54e2278a6c0c4c04cd8fb3c95d5238e4bc9244bf825d0b55a', '275ffce1-b3f8-4597-887a-ed7856d4304d', 'NS', 'Ecuador');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('f59461f5-775f-499c-a500-8874bc0fb3d5', 20, 48, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bc43d69b-26fe-4326-b32f-0b036b691072', 20, 49, 1);
-- Completed generating test data for Ecuador

-- Generating Test data for Egypt
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('EG', 'Egypt', 'Egypt', 'EGP', 1961, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ec08a712-f75b-40a7-a74d-388230f7ee0c', 2, null, 21, 'Egypt');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a37b639f-b2e9-4100-b447-59ee217ebf12', 4, 50, 21, 'Egypt');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('0d07c322-73ac-46bd-8ecc-ecc3f5d33be4', 'ACTIVE', 'egypt@iaeste.eg', 'NS.Egypt@iaeste.org', 'b238d2cc14f8113c8fe0e3cc960cb852e29ce06a2973e5d891a4576889934e47', '104988fa-d9ed-496a-a989-0526775df927', 'NS', 'Egypt');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1bf6ecc3-882a-4d1e-ae76-903a028ea152', 21, 50, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('5b408d31-f59b-494b-95a6-2e070d2d9c7a', 21, 51, 1);
-- Completed generating test data for Egypt

-- Generating Test data for Estonia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('EE', 'Estonia', 'Estonia', 'EUR', 2010, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('eb7f2270-a777-4e82-bb49-f8d67a487594', 2, null, 22, 'Estonia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2007aabf-b156-42bc-afcc-c2086d6f879f', 4, 52, 22, 'Estonia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('66ffc7ac-0486-4b28-86ba-5b7418df3e77', 'ACTIVE', 'estonia@iaeste.ee', 'NS.Estonia@iaeste.org', 'feef02b13fcbd2faa248751597aa78a9d5d26be48e5146c295ad1d15b24448c5', 'da415e52-29f8-4285-bd70-aad344f794f0', 'NS', 'Estonia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('206c89ea-c432-48c4-b326-c52b7323fd4c', 22, 52, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fbf41d4d-9d9c-438b-a632-52978e67c98f', 22, 53, 1);
-- Completed generating test data for Estonia

-- Generating Test data for Finland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('FI', 'Finland', 'Finland', 'EUR', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('de8546e4-67dd-4329-9aed-0391c4390e5f', 2, null, 23, 'Finland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('544d1a86-9163-4409-aabd-54f1096100e3', 4, 54, 23, 'Finland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2682f117-6322-4f5a-ab77-505d35cd981b', 'ACTIVE', 'finland@iaeste.fi', 'NS.Finland@iaeste.org', 'eaa9c6d6d31a584c7b8ea8aeaccedae5f449365e63ef575456be4b950da5d9c3', 'eb305743-d80b-415e-9358-0dfa55f54fc0', 'NS', 'Finland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0ec4f780-35d3-4b64-83e7-31db6fb48ceb', 23, 54, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('94c27471-a7d1-49b1-bd38-4bbd12feb4c8', 23, 55, 1);
-- Completed generating test data for Finland

-- Generating Test data for France
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('FR', 'France', 'France', 'EUR', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('714c6061-4df8-469f-9998-87f17620322c', 2, null, 24, 'France');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9f26d467-f35f-44ba-8c2e-510ba05d9e11', 4, 56, 24, 'France');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('7dc36f9a-3da1-4652-bd7b-c22b3d80509a', 'ACTIVE', 'france@iaeste.fr', 'NS.France@iaeste.org', '88de2faa16b70d25e8c82a4a4df5f6d5366fcb950a24d64575204e2290c7dd48', '561fd3cb-f440-4676-be55-1bd32de51dac', 'NS', 'France');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('c7d1e436-4030-44f4-b83b-eafa840cc085', 24, 56, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('f77229fe-d2a9-4cd0-80d8-64f54eed4236', 24, 57, 1);
-- Completed generating test data for France

-- Generating Test data for Gambia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('GM', 'Gambia', 'Gambia', 'GMD', 2009, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4e1503a5-45a0-421c-8c92-c0636fe849e8', 2, null, 25, 'Gambia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('089727fe-55c5-45f5-a7f9-729f9df801da', 4, 58, 25, 'Gambia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('71f73bc3-f045-4e04-98c0-0c40d0c360b9', 'ACTIVE', 'gambia@iaeste.gm', 'NS.Gambia@iaeste.org', '7235902196903e8f4efec1721bff01666c55e56f963e2262b9435e36be911235', '43b561a0-76b4-4790-b5f7-9af0b73881d5', 'NS', 'Gambia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a27656cd-48a7-4912-8226-470536b72913', 25, 58, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2d441a71-29c9-4dc2-8071-2c212c56a38b', 25, 59, 1);
-- Completed generating test data for Gambia

-- Generating Test data for Germany
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('DE', 'Germany', 'Germany', 'EUR', 1950, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('8771e306-0e5e-4eb8-bad1-7f7837b97762', 2, null, 26, 'Germany');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9d10455b-834a-4570-89ca-c7ce4c68eb3a', 4, 60, 26, 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c5f036b7-67a5-4ef8-a778-be1e3f51bca5', 'ACTIVE', 'germany@iaeste.de', 'NS.Germany@iaeste.org', '3131ac34e8830ac4efe3603c7b060f211e3b7dd518ea8bdadc72a3f07553e68a', '1daf2a2f-1067-46d6-a4f0-78c0d5dcbe15', 'NS', 'Germany');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ac38b617-4f6c-4a3b-ab0f-7d6756908449', 26, 60, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a9870253-53cc-447f-9e16-25188fefd8c1', 26, 61, 1);
-- Completed generating test data for Germany

-- Generating Test data for Ghana
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('GH', 'Ghana', 'Ghana', 'GHS', 1970, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('28735bcd-ac28-4ad7-86da-34e342a225c3', 2, null, 27, 'Ghana');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('78c15605-f41d-4c0f-9694-140b65af9e61', 4, 62, 27, 'Ghana');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('18fe82e0-0273-4c27-bdbb-235ac321d7dd', 'ACTIVE', 'ghana@iaeste.gh', 'NS.Ghana@iaeste.org', '1c564c728fe8f9d45c71ca06052c5bd14327fcffeebed93d29755146ed8133bd', 'e63935ae-977d-4898-ab80-b7ecb1e5bd2d', 'NS', 'Ghana');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e1aa64dd-cc8b-472f-9bf1-d9a118650114', 27, 62, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0470aa87-4ae5-440f-bbdf-dfb5ec39a0e5', 27, 63, 1);
-- Completed generating test data for Ghana

-- Generating Test data for Greece
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('GR', 'Greece', 'Greece', 'EUR', 1958, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f4455e0c-5b8e-4dfb-ad3b-ffc224f3824b', 2, null, 28, 'Greece');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('afc2175c-740f-4d8f-9118-70a4b7ead7c0', 4, 64, 28, 'Greece');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('de9d255b-c216-42bb-be62-8fa5458cace0', 'ACTIVE', 'greece@iaeste.gr', 'NS.Greece@iaeste.org', '9b6f61f257c6475d338b00461157a1eb0956f19ee5cb9d55af3aa76d153db9da', 'a5411776-bc7b-4ac6-997b-c21f3af505d0', 'NS', 'Greece');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1e69c4df-9d07-49cf-b53c-085e744940f7', 28, 64, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('42680a58-04aa-4c5c-b9ba-4ccb50497212', 28, 65, 1);
-- Completed generating test data for Greece

-- Generating Test data for HongKong
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('HK', 'HongKong', 'HongKong', 'HKD', 1997, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('33864a9b-5032-475a-8bc5-3c43336a409e', 2, null, 29, 'HongKong');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('341ac3a6-ffde-42ec-83b1-a04227ec194b', 4, 66, 29, 'HongKong');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('f0efae8f-cc15-416a-9055-4163274452c5', 'ACTIVE', 'hongkong@iaeste.hk', 'NS.HongKong@iaeste.org', 'f2164c0aaf435561ee7c96d87587e4d933953a1518ff41b68a9ea20b656ee4f1', '7cbcfec9-2d53-4691-b5d6-5c05f498990a', 'NS', 'HongKong');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('753f942e-9e13-4f41-a94d-6b2f1d5cd5f6', 29, 66, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b5e4457a-ca44-401f-a60d-13e95604e655', 29, 67, 1);
-- Completed generating test data for HongKong

-- Generating Test data for Hungary
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('HU', 'Hungary', 'Hungary', 'HUF', 1983, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0e12c5c0-9ecc-4d35-90ef-e2d6a4cdcc6a', 2, null, 30, 'Hungary');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4f6591a4-c5fe-4885-ab9d-46cbc44bc969', 4, 68, 30, 'Hungary');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('cacebdd7-d4a0-437a-a6cb-fa6591ac6ac7', 'ACTIVE', 'hungary@iaeste.hu', 'NS.Hungary@iaeste.org', 'ac3ee95fb11d16d6b248bdf3ca269f01c6a43fe5b214d8d184b62db24a51578c', '4d4c19c0-a53c-4543-aef3-8bee76a118ce', 'NS', 'Hungary');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1b83ba51-8cd7-4eb1-8b78-3abbe4d6e4c0', 30, 68, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b1e3ab12-3706-4b6f-b876-e55ebf2ec999', 30, 69, 1);
-- Completed generating test data for Hungary

-- Generating Test data for Iceland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IC', 'Iceland', 'Iceland', 'ISK', 1951, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d4352cea-a7ba-4254-82cf-f73e91a8e965', 2, null, 31, 'Iceland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('872ead93-530b-4bf2-854f-f539077dbd1a', 4, 70, 31, 'Iceland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('6738aeff-87a5-49ba-aee8-43549efc992f', 'ACTIVE', 'iceland@iaeste.ic', 'NS.Iceland@iaeste.org', 'bfa7f4509f8881bea8d19b2f3786f20ced82401c408354eb2751e8643f86adee', '69d3cb4f-dd68-4309-9aed-61b5ff6ffcdf', 'NS', 'Iceland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3c02d1b0-4f8b-42d7-a605-a295db27711e', 31, 70, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a27eca01-4e4b-41b2-8bdb-f8736d095ca9', 31, 71, 1);
-- Completed generating test data for Iceland

-- Generating Test data for India.KU
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IN', 'India.KU', 'India.KU', 'INR', 2001, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b9f73e60-b07a-488b-bf5c-ef932d4d0658', 2, null, 32, 'India.KU');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('35c5df32-4543-4813-ae8e-631fd46b5b99', 4, 72, 32, 'India.KU');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('65caa708-bbf6-43f2-a9c5-24b933b65e74', 'ACTIVE', 'india.ku@iaeste.in', 'NS.India.KU@iaeste.org', 'b91501df44603e8dfe4d2a8851df847fbb7ed1a4d84cbb8b38a14f45cb92313d', '6afbd6b9-87c1-4c61-8a70-6b651a6fccb2', 'NS', 'India.KU');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3fba082d-968a-4bf2-b25d-3127cf42e36b', 32, 72, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('73f0669b-31f7-4c8e-bc0e-b79a9bb1c617', 32, 73, 1);
-- Completed generating test data for India.KU

-- Generating Test data for Iran
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IR', 'Iran', 'Iran', 'IRR', 2002, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('12ff37b6-5ac9-4c96-8949-5dc858fd4870', 2, null, 33, 'Iran');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e5463cb8-f6ad-49d5-83cd-2d836f70c57f', 4, 74, 33, 'Iran');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('06e0c58f-cda9-4634-923e-8a653c06e9ac', 'ACTIVE', 'iran@iaeste.ir', 'NS.Iran@iaeste.org', '3d71fbd7f3f6b05255dc8db4562cf7d576031ab33876e034427b50e893fd496a', '10a937a4-2459-4a8f-bbe8-8f622a7ac878', 'NS', 'Iran');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e0db2796-4129-4c4a-91bb-69821d9f24c4', 33, 74, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('878365e1-100f-41aa-bea3-3df25ae85458', 33, 75, 1);
-- Completed generating test data for Iran

-- Generating Test data for Ireland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IE', 'Ireland', 'Ireland', 'EUR', 1962, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e9372753-1931-4342-bb8a-7807b64db7f0', 2, null, 34, 'Ireland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4a209296-fabe-463b-b8c0-16dbcb7341b6', 4, 76, 34, 'Ireland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b00e49a8-f6ec-405a-9395-91de4157a9cf', 'ACTIVE', 'ireland@iaeste.ie', 'NS.Ireland@iaeste.org', 'ba7429c06aa19993fee55eaf416bc07c88bc3e4842a10204c22bf0f940761195', 'f9e16c2b-d4f6-4209-9fb1-8690dc14f1aa', 'NS', 'Ireland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('138d5838-fbeb-4d8c-9495-22ecb597c3c5', 34, 76, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1466f618-1bd8-4157-89fe-f51316a88a4e', 34, 77, 1);
-- Completed generating test data for Ireland

-- Generating Test data for Israel
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IL', 'Israel', 'Israel', 'ILS', 1951, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e5b44bdb-170d-4fa6-aa84-6dc06f00fc6a', 2, null, 35, 'Israel');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9c564ca0-6eda-47cf-be3d-3fa6d7918142', 4, 78, 35, 'Israel');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('612511ce-5b4a-4c54-8102-c29932eddb6f', 'ACTIVE', 'israel@iaeste.il', 'NS.Israel@iaeste.org', '7f1132d07c63252fba625967bb90fd9b2f2ef52f0ac61c4afd777283bf1cf3a5', 'c9429f3a-b920-4075-99ca-ab4f1eddb5d0', 'NS', 'Israel');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('592209e9-6aa1-492f-a0e1-2f014f85d0d6', 35, 78, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8798e068-1c5c-4882-a64d-427241da291d', 35, 79, 1);
-- Completed generating test data for Israel

-- Generating Test data for Italy
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('IT', 'Italy', 'Italy', 'EUR', 2011, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d188373c-5692-4849-8193-51c14303245d', 2, null, 36, 'Italy');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f75c041f-e9b4-4d45-b0d7-578e29e6c54c', 4, 80, 36, 'Italy');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('374437c2-19e3-40f0-9a08-d7a1714ec0bc', 'ACTIVE', 'italy@iaeste.it', 'NS.Italy@iaeste.org', '93cce024ea12584b48ada54bc2fe98db46d0567c85f068ee89cd1b50ec89483d', 'dc322e81-3375-41f8-b781-8c08516f755f', 'NS', 'Italy');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('beac3e94-4571-47a6-9a47-5f772ce109ea', 36, 80, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('7b339bfb-4fb5-48ec-ac3b-48a5ef1cd0d9', 36, 81, 1);
-- Completed generating test data for Italy

-- Generating Test data for Jamaica
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('JM', 'Jamaica', 'Jamaica', 'JMD', 2006, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('cf447972-b50b-4d33-9010-cd9f0cad8f6b', 2, null, 37, 'Jamaica');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4d94ad57-0d19-4428-a9cc-7452a2d129eb', 4, 82, 37, 'Jamaica');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('9421f673-b632-407e-a0de-940ee08d7218', 'ACTIVE', 'jamaica@iaeste.jm', 'NS.Jamaica@iaeste.org', '91adcea63015a06ee8e76338e81e50c206189998d8ca7dfdb781fb9be738dc10', '6ef5e542-4091-4504-8ef4-2239927e8228', 'NS', 'Jamaica');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('114c8053-b267-4530-9479-2889647bd6a7', 37, 82, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2279d388-cdea-454c-9479-69a1dac0233f', 37, 83, 1);
-- Completed generating test data for Jamaica

-- Generating Test data for Japan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('JP', 'Japan', 'Japan', 'JPY', 1964, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0c40dce4-093e-4581-98f0-b8695e5a7c82', 2, null, 38, 'Japan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('6ef0a009-b279-4f20-87c8-a218488bca7f', 4, 84, 38, 'Japan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('45bf4152-793c-4a9b-8bc8-ba3d3e0597a1', 'ACTIVE', 'japan@iaeste.jp', 'NS.Japan@iaeste.org', '8882323829e2674589301af63d88b9b97e4e2df78b5f81ca12211d5a7f29df3b', 'b721b87e-52ca-4662-8e62-c82a01b1e623', 'NS', 'Japan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8e5908b1-b5f6-4970-9480-62839fbc67ff', 38, 84, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('30a34d90-d8fb-4bb6-81e3-f1606e62276a', 38, 85, 1);
-- Completed generating test data for Japan

-- Generating Test data for Jordan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('JO', 'Jordan', 'Jordan', 'JOD', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a8ba86f9-8129-4466-b9e8-6e9dd3b21a22', 2, null, 39, 'Jordan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c24cdcf3-eeee-4bac-9107-b0570fe96fa0', 4, 86, 39, 'Jordan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('44ad1624-02fb-47ab-9812-d12003aab234', 'ACTIVE', 'jordan@iaeste.jo', 'NS.Jordan@iaeste.org', '9a6e35f6fa0e7649d6a81e1527d30d92cc9646e68b93fb241ff31612eff5e8f5', '950b2ae5-0528-4e84-8093-9f73204a515b', 'NS', 'Jordan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bf29a482-6249-4617-907e-98bf842521f6', 39, 86, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8c3076ff-91bb-4782-8b84-e3d106d75640', 39, 87, 1);
-- Completed generating test data for Jordan

-- Generating Test data for Kazakhstan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('KZ', 'Kazakhstan', 'Kazakhstan', 'KZT', 1995, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f1556862-958e-4740-8ae6-50fe35476e66', 2, null, 40, 'Kazakhstan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('99174b65-d9fa-42dd-a01c-4c37b832a916', 4, 88, 40, 'Kazakhstan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1fbd5939-1c9c-47f6-bf49-ec34ef7cfb72', 'ACTIVE', 'kazakhstan@iaeste.kz', 'NS.Kazakhstan@iaeste.org', '6176bd189124d30ddc9e3226a7280f293c519b8e7fd219d36f45e1ec58932af3', '7f75183d-33d8-4e7c-bfac-8971f9293194', 'NS', 'Kazakhstan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8ae7beea-2bb2-4cd1-ba3a-ff46db307f98', 40, 88, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('68544604-a928-44d5-8d1a-d97d5f6e93fb', 40, 89, 1);
-- Completed generating test data for Kazakhstan

-- Generating Test data for Kenya
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('KE', 'Kenya', 'Kenya', 'KES', 2004, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('505d8241-4f71-461d-b9cd-db4c90f5b844', 2, null, 41, 'Kenya');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('5f489c47-18f4-441f-a355-add05547876a', 4, 90, 41, 'Kenya');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c2155f7a-4320-4063-a81f-07b852c7f852', 'ACTIVE', 'kenya@iaeste.ke', 'NS.Kenya@iaeste.org', '7266cc5105ea40670dba166972461ee385a89abbb527c95a8cf3361587c15e4b', 'dbb201d9-3ad7-437c-b38f-b5478a55c90a', 'NS', 'Kenya');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('886a0bbd-ec70-4e77-bc80-94a9048b4d28', 41, 90, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ef958fd5-f38d-4573-8a63-e65cc5685367', 41, 91, 1);
-- Completed generating test data for Kenya

-- Generating Test data for Korea
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('KR', 'Korea', 'Korea', 'KRW', 2007, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('08676d20-4308-42df-ba67-f27927182d72', 2, null, 42, 'Korea');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('df4cdec0-b8e9-4bc3-9b8d-87f6672a9cf8', 4, 92, 42, 'Korea');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ee8aa6b6-e77e-4c3a-9605-3e55a6d92a90', 'ACTIVE', 'korea@iaeste.kr', 'NS.Korea@iaeste.org', 'c660b05851abe27d17eac3303bb01ed0e12f0ac52e1c67071aced29359616448', '60246ad5-53c2-45e2-8439-fc90587c1190', 'NS', 'Korea');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('608a8956-3eaa-4f6b-be4c-2f0873e4133f', 42, 92, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0368c14b-290c-404a-8fd7-25a616c35dc7', 42, 93, 1);
-- Completed generating test data for Korea

-- Generating Test data for Latvia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LV', 'Latvia', 'Latvia', 'EUR', 2002, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a657b0ce-b120-4bcf-824c-ac9739ebfb37', 2, null, 43, 'Latvia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('377ab884-b042-4ffd-9041-5455bc79baf4', 4, 94, 43, 'Latvia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1ac0b03e-3c2d-4d72-9610-2593aeac90b4', 'ACTIVE', 'latvia@iaeste.lv', 'NS.Latvia@iaeste.org', '31cd082f37c1ed15b81796e6b505ce3debdda271a1c9a56c35adf7e636bd4a68', '8644e27f-c771-4902-bfc4-ca205feb7f9f', 'NS', 'Latvia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('76be913b-e0b8-46d7-8fcf-4df8615267cd', 43, 94, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('978b76a6-acdc-4fc4-af11-d7c16bd83b21', 43, 95, 1);
-- Completed generating test data for Latvia

-- Generating Test data for Lebanon
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LB', 'Lebanon', 'Lebanon', 'LBP', 1966, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('40fdc473-da7a-4a14-a60b-f40915b6936b', 2, null, 44, 'Lebanon');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('baee5c49-a95d-4811-90c3-a3d7f7069b61', 4, 96, 44, 'Lebanon');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2b509934-0bf3-49ae-901c-59c5f7e0600d', 'ACTIVE', 'lebanon@iaeste.lb', 'NS.Lebanon@iaeste.org', '189531541de1084176b1370ecf38e9dd3c58d9430e9dadc8a6f02c910d55aeca', 'aba8188c-efd6-4e56-bfaa-f95ea225c11a', 'NS', 'Lebanon');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bd8280e7-4af7-4b98-abb5-45af93fe05f8', 44, 96, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('dc582e0f-488f-4eb4-a773-0c73e90beaf7', 44, 97, 1);
-- Completed generating test data for Lebanon

-- Generating Test data for Liberia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LR', 'Liberia', 'Liberia', 'LRD', 2012, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('594cf553-196a-4b02-a50e-c35c6152a5ac', 2, null, 45, 'Liberia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c8aa8dd7-fad1-48ba-af26-afe1ad51ce3e', 4, 98, 45, 'Liberia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('adb381ef-9d11-4991-a58b-dae6895b11fb', 'ACTIVE', 'liberia@iaeste.lr', 'NS.Liberia@iaeste.org', 'e0a3d0a8c270eafa8a130c578fb66c364e9d7409d7e13c6a861e46461865e396', 'c19b58fe-3c61-4c00-9478-88237a587775', 'NS', 'Liberia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('93136252-0e8d-4de1-8070-96ca7f2d7e7a', 45, 98, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4851d565-a90d-4e91-a9d6-74a12aabbb3e', 45, 99, 1);
-- Completed generating test data for Liberia

-- Generating Test data for Lithuania
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LT', 'Lithuania', 'Lithuania', 'LTL', 1990, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9af8fe53-19c6-495c-a8c3-3b1c6dec2406', 2, null, 46, 'Lithuania');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('8e25de4a-da72-42c7-a035-814f85424138', 4, 100, 46, 'Lithuania');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a832c600-cd54-4837-9965-540e43b399e3', 'ACTIVE', 'lithuania@iaeste.lt', 'NS.Lithuania@iaeste.org', '7a3b7faa6f41811df5a6b80f9cc404e3692b50a4d22f6fdccd4eef7eb3abc049', '0992fa98-aea7-4a2a-b493-dc5bbbdfaa40', 'NS', 'Lithuania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8d3a5a02-b58a-4f02-80d5-7b67b01cab77', 46, 100, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3444c2f4-eb72-4d5f-9ca5-0c51081d8551', 46, 101, 1);
-- Completed generating test data for Lithuania

-- Generating Test data for Macao
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MO', 'Macao', 'Macao', 'MOP', 2004, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('64c1e3e4-3f67-4890-adff-8b62e9e45448', 2, null, 47, 'Macao');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('04abfe93-4720-4b24-b1e6-432489dc0819', 4, 102, 47, 'Macao');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('469677c4-1370-4021-a107-aa9ebd639c7f', 'ACTIVE', 'macao@iaeste.mo', 'NS.Macao@iaeste.org', '0d191fbc454dc78d83b102325885e6a2e9de5717a90b448804c3f9af48ea7fc2', '6678d4b2-a841-463c-a997-742fb2a84446', 'NS', 'Macao');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a5d37dd0-b119-406f-98d9-298460ed3757', 47, 102, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1985ec8b-1a1f-4884-af05-440aee8b1d9b', 47, 103, 1);
-- Completed generating test data for Macao

-- Generating Test data for Macedonia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MK', 'Macedonia', 'Macedonia', 'MKD', 1994, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('6ce74341-8f4f-4b55-8ff6-d50542217f9a', 2, null, 48, 'Macedonia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ec479694-21ed-4c24-a8a8-ef8b0eb07bd6', 4, 104, 48, 'Macedonia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('aafc6a8a-cdf2-4873-8b26-869e9e54f992', 'ACTIVE', 'macedonia@iaeste.mk', 'NS.Macedonia@iaeste.org', '3a4337186ba7b55fead9956ca6942bf809d7967550528d19319b17e7eb76cc5b', 'dc86ba87-6a24-4fc0-804c-2e4419f63b7b', 'NS', 'Macedonia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ad5c7c64-2ac6-4a13-9262-72077ca9ce72', 48, 104, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('81576fbf-b879-4ea5-83a6-e246490f7634', 48, 105, 1);
-- Completed generating test data for Macedonia

-- Generating Test data for Malaysia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MY', 'Malaysia', 'Malaysia', 'MYR', 2008, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('7eae41cc-03d0-48e1-ab01-2b9ad48d7f2f', 2, null, 49, 'Malaysia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1494f627-3fba-4b3a-8cf0-dde608d5f60c', 4, 106, 49, 'Malaysia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d14077e8-bfe6-42d7-a088-71325b1e4275', 'ACTIVE', 'malaysia@iaeste.my', 'NS.Malaysia@iaeste.org', '517098b4816113f9a37f50e151e26c65eb756e7e1a5337ebf553abdc6aab142a', '8ba05e1d-db11-4ee5-a60a-2722385ac4ae', 'NS', 'Malaysia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('146c0f26-2e8c-474d-ab8f-c8087aab0ce9', 49, 106, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e25adcb2-4f0d-45a5-9855-27a7937b4cbe', 49, 107, 1);
-- Completed generating test data for Malaysia

-- Generating Test data for Malta
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MT', 'Malta', 'Malta', 'EUR', 1984, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f362e01c-aa38-4e37-b221-45ddb8bdf968', 2, null, 50, 'Malta');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c75de18f-c9f0-41ed-bee3-3fe2f1826deb', 4, 108, 50, 'Malta');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('78c95454-e005-47f9-ae6d-8e60f5213b2f', 'ACTIVE', 'malta@iaeste.mt', 'NS.Malta@iaeste.org', 'cb61e9998047fb73eb488bbda053dd37ac1b3c8a6d1b27806820545ddf94eae9', 'f255c0a4-453b-4a6f-a2b5-e100b7727a2d', 'NS', 'Malta');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('eff7bec0-f3ae-450b-961d-a1fd8c1d9e1e', 50, 108, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6de6f4f4-e2b2-4be8-b583-76564e9c4935', 50, 109, 1);
-- Completed generating test data for Malta

-- Generating Test data for Mexico
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MX', 'Mexico', 'Mexico', 'MXN', 1985, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1e7336f2-8513-4de2-9f3b-6b73a7da5fcb', 2, null, 51, 'Mexico');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b18a734e-44fa-4a0b-8cff-3dc331d6893b', 4, 110, 51, 'Mexico');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('8ec3dfb3-30ff-45dd-83d3-3891f93feb60', 'ACTIVE', 'mexico@iaeste.mx', 'NS.Mexico@iaeste.org', '3064e4567ba02f5f2d7f68f93ef01620a01a00bb69d6d17ea28a31b952a585cc', '8ec249e0-2d3d-410a-b576-5b0616963aa6', 'NS', 'Mexico');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3f911b1f-efae-4f9c-8faa-cf3bdecd8fd2', 51, 110, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cd72378c-f65e-4774-b758-6f2fafbc3ec0', 51, 111, 1);
-- Completed generating test data for Mexico

-- Generating Test data for Mongolia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('MN', 'Mongolia', 'Mongolia', 'MNT', 2001, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f68a2678-38fe-4d69-89f9-07e01f537ef0', 2, null, 52, 'Mongolia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0ac91424-ca7e-4be4-87d7-2f35ca665cde', 4, 112, 52, 'Mongolia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('8d60b75e-cd06-41fc-a4b2-4a8ac7a6d1ee', 'ACTIVE', 'mongolia@iaeste.mn', 'NS.Mongolia@iaeste.org', '5f1f8e2d49b3dc3ba01990ae055fb7636223aab5f4192736990ccaf5e114a905', 'c614e1eb-ee8c-4fdf-9c62-5d2ce7a5d51e', 'NS', 'Mongolia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('d2fe4834-ad28-47cc-81b0-e8c98ef75859', 52, 112, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('11c4f6ed-282a-44a4-8292-70d9ef2d78d0', 52, 113, 1);
-- Completed generating test data for Mongolia

-- Generating Test data for Montenegro
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('ME', 'Montenegro', 'Montenegro', 'EUR', 2007, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9f6adbcf-e81a-44a7-85ad-812da9796e69', 2, null, 53, 'Montenegro');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('3390a51e-311a-4c62-a61f-00fc4820e666', 4, 114, 53, 'Montenegro');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('8bf79497-1f1a-41af-8b6d-afa29ca60b1b', 'ACTIVE', 'montenegro@iaeste.me', 'NS.Montenegro@iaeste.org', '86ab28c35277e95d83f9179f1939e4cbd7096849b9aa1d828cd34785e9f563e1', '7faba7c2-93ec-454c-b748-4ab7c3506ae7', 'NS', 'Montenegro');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a094b0d2-38b1-4129-8ea8-4253e5d6288c', 53, 114, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('17d6ca3f-f759-4a65-8c10-0bef9a0ea16b', 53, 115, 1);
-- Completed generating test data for Montenegro

-- Generating Test data for Netherlands
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('NL', 'Netherlands', 'Netherlands', 'EUR', 2011, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('161e692f-7a38-4fe5-b8c4-c58c5573b17b', 2, null, 54, 'Netherlands');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ef8762cc-3378-4dcf-b686-0baa60a4525f', 4, 116, 54, 'Netherlands');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('e1203e43-e273-46d0-9ae5-170ca998498e', 'ACTIVE', 'netherlands@iaeste.nl', 'NS.Netherlands@iaeste.org', '8db52e5e14fac5a35584d6aeb85bf08ec638d3170d2a63e5757edba8023a21e5', 'd18333b5-d6dd-4fd5-9e7d-fc3b6cb8ebd1', 'NS', 'Netherlands');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('ca3773df-da87-45f4-ba6e-c56a3527bd2d', 54, 116, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('55060933-bd23-4f25-8a0b-43d0db229137', 54, 117, 1);
-- Completed generating test data for Netherlands

-- Generating Test data for Nigeria
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('NG', 'Nigeria', 'Nigeria', 'NGN', 2007, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1a2b1987-e955-493c-8632-9a2fdb5d44b8', 2, null, 55, 'Nigeria');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('24fd88db-8527-4c3c-9c2c-8e46a63df310', 4, 118, 55, 'Nigeria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('a0b4f078-4b09-4f95-af07-336589dccdce', 'ACTIVE', 'nigeria@iaeste.ng', 'NS.Nigeria@iaeste.org', 'd387c52f4393df63321939780a1d0b30bab56eba25efefcf92aecbac69e8b733', '62572e02-3e5e-459d-9a41-4ae244c3e34e', 'NS', 'Nigeria');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('25f6de8c-a6c6-4931-a076-e61efdccd1eb', 55, 118, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('35795182-d5d3-45ac-b1ea-95ccb7e87a93', 55, 119, 1);
-- Completed generating test data for Nigeria

-- Generating Test data for Norway
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('NO', 'Norway', 'Norway', 'NOK', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('d975e033-7fc0-4980-b1e1-9ae97eb43554', 2, null, 56, 'Norway');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a9bbc993-885a-4c48-b3be-50a89d691f59', 4, 120, 56, 'Norway');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fc1d8ab6-a980-456d-918c-818155c6ee1f', 'ACTIVE', 'norway@iaeste.no', 'NS.Norway@iaeste.org', '394a44542ac1d0cc0dbbcd6edafb1e677bdb7ec7b9e89cd9678e17b5259c4a32', 'c7ebb94b-f911-4d3d-b772-0e8e149682a2', 'NS', 'Norway');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('bce61325-914f-49ea-b3bf-da405958bcca', 56, 120, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('660837b4-1533-4476-8839-6b3fed73797b', 56, 121, 1);
-- Completed generating test data for Norway

-- Generating Test data for Oman
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('OM', 'Oman', 'Oman', 'OMR', 2001, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c9c2c6e8-6fbd-43ae-9bd5-cdb9c9df2068', 2, null, 57, 'Oman');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c22dd9c4-07fe-4e0a-bc5c-7120fc1c4e4d', 4, 122, 57, 'Oman');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('663bb1c3-2d8a-48cd-a93e-cdb41808ca54', 'ACTIVE', 'oman@iaeste.om', 'NS.Oman@iaeste.org', 'c0fe55e52f30d8dbb2464beb0312cc01ffe9551c590aab7bf8b13f4edd4b0d2a', '9ffefade-03b5-4e89-89eb-073b7c4add7f', 'NS', 'Oman');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('995ab456-c475-46c3-8810-2f2804abc538', 57, 122, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('686ac121-f6cc-4ef3-82d6-0e5a3cd669ee', 57, 123, 1);
-- Completed generating test data for Oman

-- Generating Test data for Pakistan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PK', 'Pakistan', 'Pakistan', 'PKR', 1990, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('49e7dff5-e152-4da9-bc7f-e07344473eba', 2, null, 58, 'Pakistan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('97027062-a067-4146-b5ed-858ed9d03d7d', 4, 124, 58, 'Pakistan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('602ec7de-977f-4789-bec0-090d30abf11b', 'ACTIVE', 'pakistan@iaeste.pk', 'NS.Pakistan@iaeste.org', '74459d9497d2c031ea5cdb51319a5c4b9554484283c96d6a5afe95fa0d07a66a', 'fc7fa09a-ff57-49bb-8381-f5a154afe482', 'NS', 'Pakistan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fffc7496-f439-44e6-8493-2880434ce018', 58, 124, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('de87c2c5-5b93-4167-929f-e1823deabe1d', 58, 125, 1);
-- Completed generating test data for Pakistan

-- Generating Test data for Panama
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PA', 'Panama', 'Panama', 'PAB', 2004, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('e8922884-8d42-4541-9f08-340dd1a6b0d1', 2, null, 59, 'Panama');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('abd8b394-1fe3-4ccf-8e15-64bf4a36d8b1', 4, 126, 59, 'Panama');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('40fd60b6-2665-4ce3-818b-3fd24a2767e5', 'ACTIVE', 'panama@iaeste.pa', 'NS.Panama@iaeste.org', 'd45cc3a8441fe8a5ef768a0b43231e7ad27e5547acbbf59f7b1eb040eaba5a12', '54c5d077-b93f-4e24-a16c-2e794364e8bb', 'NS', 'Panama');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('aa938141-5197-477f-91f4-f99dc4f189e3', 59, 126, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0227b327-cf00-4267-9eaf-fce43a56e8b9', 59, 127, 1);
-- Completed generating test data for Panama

-- Generating Test data for Peru
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PE', 'Peru', 'Peru', 'PEN', 2001, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f6269393-9df7-44ad-8ab3-6d8e8cf20a22', 2, null, 60, 'Peru');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f77f5093-31fa-40f7-b85f-9a540a8f5433', 4, 128, 60, 'Peru');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('6c0fa61d-b266-4335-bd10-9a2430b21147', 'ACTIVE', 'peru@iaeste.pe', 'NS.Peru@iaeste.org', '5864e556da24e01674c07ae866c529b9da276294cd385515e9fd33530ae0b260', '753ccc40-aa22-43c6-a5a9-15af0b633a98', 'NS', 'Peru');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8aa4a44e-4731-4395-8b37-c1b93195d13d', 60, 128, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0056cbf4-b4b5-4831-8c37-12f29b8084b2', 60, 129, 1);
-- Completed generating test data for Peru

-- Generating Test data for Philippines
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PH', 'Philippines', 'Philippines', 'PHP', 2007, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a2d98692-74d2-44de-8c38-557c67721374', 2, null, 61, 'Philippines');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a230e78f-4b74-463d-b6ad-8020166c8b6a', 4, 130, 61, 'Philippines');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ac7ccf4c-8d0b-421e-88aa-dff089667dcb', 'ACTIVE', 'philippines@iaeste.ph', 'NS.Philippines@iaeste.org', 'a7993d66d270329527b7b0f012530c178b1b111eec06fb608c055a4e3a3f53c7', '968a57b7-335d-43ac-a15e-5bae8b9122a1', 'NS', 'Philippines');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e9689b85-2248-4e4b-b9f6-d2d8255f9cee', 61, 130, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0efbce9b-2d2e-4d0a-94ba-7806fd20449b', 61, 131, 1);
-- Completed generating test data for Philippines

-- Generating Test data for Poland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PL', 'Poland', 'Poland', 'PLN', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('70e293c2-7142-4db2-91a7-cc0bf8fe5f65', 2, null, 62, 'Poland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('dcdf3f10-3a51-4970-933e-93b01a70b0b1', 4, 132, 62, 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c929b28f-6590-4296-a9cc-6c5f7ead8744', 'ACTIVE', 'poland@iaeste.pl', 'NS.Poland@iaeste.org', 'fe1ade81bf65e13fa965f65ae1e012fcaa059621d70391e07f9c011846ec6699', 'c26b14f3-ca89-4007-b103-e810caf30378', 'NS', 'Poland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8531065d-d2b8-44d4-b635-f3e5f8e6eaa1', 62, 132, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('df642030-be60-4402-be85-9c4dad60bfed', 62, 133, 1);
-- Completed generating test data for Poland

-- Generating Test data for Portugal
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PT', 'Portugal', 'Portugal', 'EUR', 1954, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('03fc1e57-0913-4c24-b250-5be5b64ddfad', 2, null, 63, 'Portugal');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('25d9165f-1863-4327-81f2-8d12fcf97b6e', 4, 134, 63, 'Portugal');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('10b48826-d7dc-4a66-9421-d2d5e8ed3027', 'ACTIVE', 'portugal@iaeste.pt', 'NS.Portugal@iaeste.org', 'ca73bb6161bc81fd8187a52797fce46c789482a33b2d203fbd0616e79b4b5811', 'c25d3542-88ca-446c-bb2c-76d00f09bb5a', 'NS', 'Portugal');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a57a90a4-4063-44ab-84eb-f3f002e5ce70', 63, 134, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9f0aae57-b95e-46c1-8c59-d4a5f36d84fe', 63, 135, 1);
-- Completed generating test data for Portugal

-- Generating Test data for Qatar
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('QA', 'Qatar', 'Qatar', 'QAR', 2011, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('36bcc1ad-cf1a-44dc-98ee-320b2c59f4f9', 2, null, 64, 'Qatar');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('4c2a0116-14ac-4d22-8d5c-c3f3e332e4ce', 4, 136, 64, 'Qatar');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('eea82e01-cd71-4f7f-a521-2c587a733fd8', 'ACTIVE', 'qatar@iaeste.qa', 'NS.Qatar@iaeste.org', 'ed20d75fe282b9fa0cf7d0075018d8244dbcfdb5ac6bcf3516a323acd19cfdc3', '184f379e-d9a0-4550-b7d9-e112a78c9799', 'NS', 'Qatar');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a4f614c3-072f-4a21-9ad2-df1bfa47c304', 64, 136, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('35521cc4-f95c-4279-b146-85f07e8278e0', 64, 137, 1);
-- Completed generating test data for Qatar

-- Generating Test data for Romania
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('RO', 'Romania', 'Romania', 'RON', 1998, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c6248151-1b55-420d-a3cf-2d5b94b533e2', 2, null, 65, 'Romania');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('fca658a0-db35-4037-b2f6-69bce461128a', 4, 138, 65, 'Romania');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('78d7260a-aed1-4002-b48f-88cdeae70184', 'ACTIVE', 'romania@iaeste.ro', 'NS.Romania@iaeste.org', '6d8bc3550f8e47cb4ca71d7eb7047701b2a03fee73b31c4978dd38f48471724a', '5926ae49-1181-4cca-bb64-2f523bfecb98', 'NS', 'Romania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('251d118b-aa39-42e1-9379-23dbd4680f2e', 65, 138, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('32befa26-faef-4774-96f6-adf8804895af', 65, 139, 1);
-- Completed generating test data for Romania

-- Generating Test data for Russia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('RU', 'Russia', 'Russia', 'RUB', 1991, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('af16cc9f-0f91-4e0d-964d-62eb7b6e6977', 2, null, 66, 'Russia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ea0b915f-a84e-4f74-85ba-80b5977963fc', 4, 140, 66, 'Russia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('8ce432c6-8032-4bbb-b4a2-c89b2a6719b9', 'ACTIVE', 'russia@iaeste.ru', 'NS.Russia@iaeste.org', 'b59d13e58bd9d6721b55b6850e77332deb9294439dd83676654bbd4f3fb82081', 'ad5a1d4e-8046-4d6a-bf5d-19b7a8e96d2d', 'NS', 'Russia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0336f44a-751a-47cf-9c82-9600cd5ad602', 66, 140, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('5878dba7-cdd5-48c2-b3c1-b2669c2157bb', 66, 141, 1);
-- Completed generating test data for Russia

-- Generating Test data for Serbia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('RS', 'Serbia', 'Serbia', 'RSD', 1952, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('35404eb5-c46d-4edc-96d1-0f1a6f76fc21', 2, null, 67, 'Serbia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('09fc08e7-1541-4835-a81e-ddc5df88f9e6', 4, 142, 67, 'Serbia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1a2f1dd2-059a-4a6b-bfaf-f97d7496d694', 'ACTIVE', 'serbia@iaeste.rs', 'NS.Serbia@iaeste.org', '66b4aac7ad7547ef1407220bc0f596d58f6df31c4766574b833fe3169af1c9fd', '31ed2982-0eda-4f5c-ad18-5cba1fe81ac7', 'NS', 'Serbia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('75de9633-70cb-46e8-a3a5-f9d453b13ba8', 67, 142, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0a092328-6b45-4b91-8875-c57c8100c984', 67, 143, 1);
-- Completed generating test data for Serbia

-- Generating Test data for Slovakia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SK', 'Slovakia', 'Slovakia', 'EUR', 1965, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('18767a92-94ba-4282-ab02-dc3287fd206e', 2, null, 68, 'Slovakia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('b5905239-db02-4be4-81ef-a2390aafb07a', 4, 144, 68, 'Slovakia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ca6d2106-3c1a-4257-a681-dd287514af2f', 'ACTIVE', 'slovakia@iaeste.sk', 'NS.Slovakia@iaeste.org', 'b92dad9afbe0e93088d243e5ef13cb2eca64261015878846415b26bfad5c53e3', 'd4b424eb-fe0d-49dc-b57b-4b3f06420739', 'NS', 'Slovakia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('81dfd137-fa49-47fc-99e6-7d93cfd2f800', 68, 144, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3840803a-47c2-47ab-aa51-a34c57379825', 68, 145, 1);
-- Completed generating test data for Slovakia

-- Generating Test data for Slovenia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SI', 'Slovenia', 'Slovenia', 'EUR', 1993, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ff5ec00d-5aad-40bf-a733-7cf3244ede4b', 2, null, 69, 'Slovenia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('dd61acdf-7a01-4123-8003-aef82310e772', 4, 146, 69, 'Slovenia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('898d3faa-93db-4b8b-a230-a1669320635a', 'ACTIVE', 'slovenia@iaeste.si', 'NS.Slovenia@iaeste.org', 'dbdc52ee0ebb70b944c680c8ce1fccf77714513e76c49c7d107df357816932fc', 'cccb345d-ada6-4b19-8ad7-60be508b2da7', 'NS', 'Slovenia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b85ddba8-db9c-423b-b472-a1f3cade2530', 69, 146, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('fe530d96-978f-4dc8-bcd7-3349564b1e84', 69, 147, 1);
-- Completed generating test data for Slovenia

-- Generating Test data for Spain
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('ES', 'Spain', 'Spain', 'EUR', 1951, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2aa253ae-c412-4041-8212-65978a14132a', 2, null, 70, 'Spain');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('052c7719-33e2-4825-baf1-39bf7d6457c2', 4, 148, 70, 'Spain');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d9273afb-13c6-418c-b328-2dd1a635cf32', 'ACTIVE', 'spain@iaeste.es', 'NS.Spain@iaeste.org', '03b43920477127ffa49bdf242f277e2744046eaf66657996b64689085fff669b', 'c0e0109e-6714-490a-bd26-7505e95c77dc', 'NS', 'Spain');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('016aabd1-53f2-4084-aa7d-faf13cb32fde', 70, 148, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4bf951eb-70ac-419b-9b5b-5d2281890747', 70, 149, 1);
-- Completed generating test data for Spain

-- Generating Test data for SriLanka
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('LK', 'SriLanka', 'SriLanka', 'LKR', 2000, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2e6551b5-c832-43a0-bb47-88af2e82fc79', 2, null, 71, 'SriLanka');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('7ccf6069-6ada-4045-91c0-2ccdde794dc6', 4, 150, 71, 'SriLanka');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d8cc0286-a6e9-4837-93b8-04e48a33ed49', 'ACTIVE', 'srilanka@iaeste.lk', 'NS.SriLanka@iaeste.org', 'ba32f322b3fc77ff8ef8452a5f693393b64e7f485db1d056f6c94b4f3d6c4b1f', 'cc32854f-82ea-443f-b0e4-842086488f3d', 'NS', 'SriLanka');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cefaefbd-3f21-42cf-81be-b0c721e24eab', 71, 150, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('cf0d33b7-eddc-411f-a364-7e4f39b4bf78', 71, 151, 1);
-- Completed generating test data for SriLanka

-- Generating Test data for Sweden
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SE', 'Sweden', 'Sweden', 'SEK', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('894c8eed-27dd-45d0-b00a-d6da51bdc8aa', 2, null, 72, 'Sweden');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('df577354-cd95-4ab7-bce7-1756ecbb4b2e', 4, 152, 72, 'Sweden');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b798605f-1eaa-48cc-9465-8227ef2632b4', 'ACTIVE', 'sweden@iaeste.se', 'NS.Sweden@iaeste.org', 'b58a9394feca148c354fa3ee31f2e84f585d2c59035120855ebcc3260ac94524', '65a326ec-9123-43e4-95f8-62ed1bb5e40e', 'NS', 'Sweden');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('27c963c3-bcb1-4835-a9ec-a4f7ff628adf', 72, 152, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('5597d1df-2b01-43e4-9b94-a5458614a70a', 72, 153, 1);
-- Completed generating test data for Sweden

-- Generating Test data for Switzerland
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('CH', 'Switzerland', 'Switzerland', 'CHF', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ddc46e01-c264-4e3c-9fb1-9914c0a3b0fe', 2, null, 73, 'Switzerland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('523cb4df-71d3-416d-80e9-192d338fede8', 4, 154, 73, 'Switzerland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('ec68455e-c2e7-4a9f-8ba3-36c45610bd20', 'ACTIVE', 'switzerland@iaeste.ch', 'NS.Switzerland@iaeste.org', '471d4b61d5dea4576bdc6f16e5f75cde2bf7432db9d33964ba8b6d500e000e67', '4c2341fa-6e65-4d38-9c60-4dcf9276885f', 'NS', 'Switzerland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('364b1581-1546-47fb-9f3c-aa38e639a556', 73, 154, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b977b640-4695-4347-a603-38ed587a3e3d', 73, 155, 1);
-- Completed generating test data for Switzerland

-- Generating Test data for Syria
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('SY', 'Syria', 'Syria', 'SYP', 1965, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c4990972-901c-44d2-be8e-042cead84491', 2, null, 74, 'Syria');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('ddc44f18-401d-46a9-b60c-c42fa6db1958', 4, 156, 74, 'Syria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('e074489c-90ef-4afe-b324-808810dbf474', 'ACTIVE', 'syria@iaeste.sy', 'NS.Syria@iaeste.org', 'e188a12b25a41fe2723d6a47a704c0cf1f933cb6c27ce564e174326a90f1e140', 'ea64b238-6f90-451c-a898-1727eaac0519', 'NS', 'Syria');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('c2867937-3f32-41c9-ae7c-b00b36efe9e8', 74, 156, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('58beccf9-1319-4690-9f70-80375f7331a1', 74, 157, 1);
-- Completed generating test data for Syria

-- Generating Test data for Tajikistan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TJ', 'Tajikistan', 'Tajikistan', 'TJS', 1992, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('12551323-0ed7-44b0-8386-59f5e60553f2', 2, null, 75, 'Tajikistan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('5209399e-07a4-451e-92f6-6869ee3e201d', 4, 158, 75, 'Tajikistan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('9ca39825-a9c3-4533-b361-6738a0c8e602', 'ACTIVE', 'tajikistan@iaeste.tj', 'NS.Tajikistan@iaeste.org', '8e4c355087f4adce58919ce2b22e8419f1a74ffc31ea09537fee98a50f7a2863', '0f2b147d-7c09-4387-a1bb-9878c3092f98', 'NS', 'Tajikistan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e6ae4e5b-d765-48d9-8c83-acedbf2657d5', 75, 158, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2d01e29a-d634-4b15-acb5-1a924a480a7b', 75, 159, 1);
-- Completed generating test data for Tajikistan

-- Generating Test data for Tanzania
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TZ', 'Tanzania', 'Tanzania', 'TZS', 2007, 'ASSOCIATE_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('bd0e94e2-fc66-400e-996f-06de6eae8292', 2, null, 76, 'Tanzania');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('22012cef-863f-45a2-adc1-168779ab587e', 4, 160, 76, 'Tanzania');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1dc4cefb-28c2-4b7a-9918-f52d5557d1a7', 'ACTIVE', 'tanzania@iaeste.tz', 'NS.Tanzania@iaeste.org', '120adb786b6ecd5e1062cb113718b5b9349bb2c4bfcf2b23617fb5db41cc520e', '88bcbc51-af94-4dc1-b49e-042f78b39f1e', 'NS', 'Tanzania');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('798d5da3-0053-41a6-ba44-792d292190cc', 76, 160, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e1a99569-5c97-4a85-9713-0c94f16d4a59', 76, 161, 1);
-- Completed generating test data for Tanzania

-- Generating Test data for Thailand
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TH', 'Thailand', 'Thailand', 'THB', 1978, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f8f550a5-a3ba-4d7f-9263-1183098f7831', 2, null, 77, 'Thailand');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('f0d692ac-b2f3-4c01-813b-e7636f5ca969', 4, 162, 77, 'Thailand');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('79775883-6259-44ce-ad01-272eaf5e14b9', 'ACTIVE', 'thailand@iaeste.th', 'NS.Thailand@iaeste.org', 'f4bb6a0fefe25d1af156006a5043f8373c55099c956861fd19db38955075776d', 'dda3cdd4-68ce-4edb-9e3f-f84dda5bd833', 'NS', 'Thailand');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a1b75209-3675-469c-af16-896f7c462df9', 77, 162, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('c252ee82-08ce-419f-bca4-878489fb3dab', 77, 163, 1);
-- Completed generating test data for Thailand

-- Generating Test data for Tunisia
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TN', 'Tunisia', 'Tunisia', 'TND', 1959, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('59f04a7e-2e80-492b-b785-2f041580fd01', 2, null, 78, 'Tunisia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('24e523a7-aed2-4f13-ae6d-c1f1c01e9776', 4, 164, 78, 'Tunisia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('d7dc5ce2-2277-4c3b-a2c5-77ddc9bd696f', 'ACTIVE', 'tunisia@iaeste.tn', 'NS.Tunisia@iaeste.org', 'c9d1c3ed832128f8042f8f781a965fb21e55a33449a7b6fea1397ce1f926791e', '856424ef-fbec-44f3-926e-9f5c04a28bf2', 'NS', 'Tunisia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('9f7303c1-87ac-4027-9f22-a8f27e165bfc', 78, 164, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('0a1ed654-d457-4821-b102-17b15f5f9ec5', 78, 165, 1);
-- Completed generating test data for Tunisia

-- Generating Test data for Turkey
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('TR', 'Turkey', 'Turkey', 'TRY', 1955, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('bec6ba71-282c-4681-a46b-afbdbaf8d863', 2, null, 79, 'Turkey');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('42732c68-9d2d-48e6-b06d-d8520f349df7', 4, 166, 79, 'Turkey');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('23323a60-3c9c-4686-8236-f561b173d1bd', 'ACTIVE', 'turkey@iaeste.tr', 'NS.Turkey@iaeste.org', 'a8de1335d5543e130f28a1e9a05fbd1e40402914fba23a7c9771bd596a8c89fd', '96581d0c-e6dd-4426-9eaf-4e01a16ca782', 'NS', 'Turkey');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e7275732-f667-47de-ae0e-579d8604cde9', 79, 166, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('16931979-2eb0-4e2f-98c5-1a87b63f2d12', 79, 167, 1);
-- Completed generating test data for Turkey

-- Generating Test data for Ukraine
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('UA', 'Ukraine', 'Ukraine', 'UAH', 1994, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('eea96d05-6cd0-4bb7-8bed-3c4e7dadfed2', 2, null, 80, 'Ukraine');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('3dd9a7a8-ad8c-47e4-b54f-c5a2759cfe84', 4, 168, 80, 'Ukraine');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('fa57d7a5-e6af-4d3d-beee-48f0492d3748', 'ACTIVE', 'ukraine@iaeste.ua', 'NS.Ukraine@iaeste.org', '20c7014828246a1fecdc614ae2e38b053830848af250ffeec0d6aa458b7d4702', '9d3f9f71-3cf8-4ada-97ed-ebba14291a76', 'NS', 'Ukraine');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('76455bca-8c52-44d9-9920-87799c812fb3', 80, 168, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('96019d82-148b-4af5-85c7-102649609981', 80, 169, 1);
-- Completed generating test data for Ukraine

-- Generating Test data for UnitedArabEmirates
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('AE', 'UnitedArabEmirates', 'UnitedArabEmirates', 'AED', 2000, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('c218e924-f123-4144-b871-8bd1097659c4', 2, null, 81, 'UnitedArabEmirates');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('a4c71aee-09c0-401b-a547-d8ed4491e8e2', 4, 170, 81, 'UnitedArabEmirates');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('bdf76f19-5c33-4195-9f69-7d67fd54659a', 'ACTIVE', 'unitedarabemirates@iaeste.ae', 'NS.UnitedArabEmirates@iaeste.org', '1a8c2de095a0aa4a5ebf37aa640438d9f237a165c7c2a05e5a8330896e17f3ad', 'fb0541f0-685e-45ca-96a4-bc423e53c702', 'NS', 'UnitedArabEmirates');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a6dc77fe-4e9a-4b77-82a5-9a7a3e6e0073', 81, 170, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('20644acb-d39b-4838-b9a1-cb6da097ab11', 81, 171, 1);
-- Completed generating test data for UnitedArabEmirates

-- Generating Test data for UnitedKingdom
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('UK', 'UnitedKingdom', 'UnitedKingdom', 'GBP', 1948, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('389e6fbb-9e69-4987-aafe-2d9497798ee7', 2, null, 82, 'UnitedKingdom');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('9bcf5332-cd41-44f0-b902-e853c291a436', 4, 172, 82, 'UnitedKingdom');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c0a6ed81-fa0d-447e-94a6-51cc1a2b7221', 'ACTIVE', 'unitedkingdom@iaeste.uk', 'NS.UnitedKingdom@iaeste.org', 'd85fe8b67d859335a0ff703764672e0aad8cd89af6f43d4ea7b28584ca17175b', '3074dfd8-dc63-408f-af82-16e70745e17f', 'NS', 'UnitedKingdom');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6d68494c-2201-4930-a51b-17eb73b67998', 82, 172, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('3c614a4a-8514-49a0-b73f-4c55d8c66fe6', 82, 173, 1);
-- Completed generating test data for UnitedKingdom

-- Generating Test data for USA
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('US', 'USA', 'USA', 'USD', 1950, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('0b403d38-e454-45f3-b2b4-876c4e220832', 2, null, 83, 'USA');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('eed907ec-bfa3-46c7-aadf-4bc313e93e80', 4, 174, 83, 'USA');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('e2b0fd0e-0593-47f6-b7c6-9cf14356ca08', 'ACTIVE', 'usa@iaeste.us', 'NS.USA@iaeste.org', 'e1ae6cb4a3dca66e556a16efaf72f0b32d2dab286e693559fe9f8848df2c433e', '1ff542dd-3874-4b99-bb60-70df0a0fc92e', 'NS', 'USA');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4f31d4f6-7303-4c4a-8aa1-7ab1f30d6740', 83, 174, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('364c5697-77aa-4622-b1db-97141f7ed49d', 83, 175, 1);
-- Completed generating test data for USA

-- Generating Test data for Uzbekistan
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('UZ', 'Uzbekistan', 'Uzbekistan', 'UZS', 1997, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('07e36800-de19-4c50-b1c4-d3e5c8e33ec7', 2, null, 84, 'Uzbekistan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('1b4a28d9-8ef3-42dc-86e1-b5043c3f1b10', 4, 176, 84, 'Uzbekistan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('dd4e30ce-572c-49f4-b8ee-0228c4234d40', 'ACTIVE', 'uzbekistan@iaeste.uz', 'NS.Uzbekistan@iaeste.org', '8f8e02f66ae5cc10a441938dfd71493bb75cb8db245d60d4f28de0210e794ebf', '63494826-9b62-49b3-9916-c02a994f7e5f', 'NS', 'Uzbekistan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a1e66ead-de39-48ff-b4bf-e0385e47ae66', 84, 176, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4e9111b7-942e-4b0e-b208-adae68e76b5e', 84, 177, 1);
-- Completed generating test data for Uzbekistan

-- Generating Test data for Vietnam
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('VN', 'Vietnam', 'Vietnam', 'VND', 2006, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2fe23724-157e-4017-8946-0a3a2bef3fa8', 2, null, 85, 'Vietnam');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('67fc0148-99c9-4991-bf0c-ca9239732e88', 4, 178, 85, 'Vietnam');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('b9bc5391-acbc-4268-b0aa-bfd5840f60be', 'ACTIVE', 'vietnam@iaeste.vn', 'NS.Vietnam@iaeste.org', 'abdeb3ed979ab33702c863c93992755716508b1fbb971e1eed23f67b16b1ecce', '1f1e28d9-78e4-4692-878e-07ce2fef7bf2', 'NS', 'Vietnam');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('6d2a8c46-3a5b-4576-9b3d-11afaecc7d86', 85, 178, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('30119c5f-133d-4d64-b928-bb4e3fb8816a', 85, 179, 1);
-- Completed generating test data for Vietnam

-- Generating Test data for WestBank
insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('WB', 'WestBank', 'WestBank', 'ILS', 2009, 'COOPERATING_INSTITUTION');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('063a150c-0ef6-4e9c-9b5a-f3d3d3e4e5c6', 2, null, 86, 'WestBank');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('16d990f9-8b82-4ca4-bb4c-a8cdb275fe4f', 4, 180, 86, 'WestBank');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('83bb4382-a328-44ff-8731-6d62d3ca28dc', 'ACTIVE', 'westbank@iaeste.wb', 'NS.WestBank@iaeste.org', '58adccbf101cb829169a4bb052aebc82114727b6af4c5c0084c469db61894763', '08852954-eebc-48e5-9489-b4c3d1d99467', 'NS', 'WestBank');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('d4085ac6-557d-494a-b202-ef50cdf977e1', 86, 180, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b95026ab-a2b6-41c5-9b70-4edea2a887f8', 86, 181, 1);
-- Completed generating test data for WestBank

-- Austria LC subgroup
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('2688c1ca-2113-4a4b-aef1-27da26dca479', 5, 16, 4, 'Austria LC1');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2f42c597-059a-42c3-b9f8-d8735f7cd7c3', 4, 182, 1);

-- For our testing, we need to suspend a single Country -> First country, Albania
update groups set status = 'SUSPENDED' where id = 10;
-- Four out testing, we need to suspend a single Account, First available, Argentina
update users set status = 'SUSPENDED' where id = 2;
