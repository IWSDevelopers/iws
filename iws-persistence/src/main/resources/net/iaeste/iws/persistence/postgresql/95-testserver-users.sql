-- ============================================================================
-- Preparing to create test data for users.
-- ============================================================================

-- First reset the existing tables & sequences, regardlessly!
delete from user_to_group;
delete from users;
delete from groups where id>= 10;
delete from countries;
alter sequence country_sequence restart with 1;
alter sequence group_sequence restart with 10;
alter sequence user_sequence restart with 1;

-- Generating Test data for Columbia
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('CO', 'Columbia', 'Columbia', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('dff98f89-8b1a-4975-bdeb-3f05c4a59f6b', 2, null, 1, 'Columbia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('5548e401-d55c-47d7-91cb-5bc06be83859', 5, 10, 1, 'Columbia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2ce0e206-f5a6-4295-ba86-4bbf3c731836', 'ACTIVE', 'columbia@iaeste.co', 'NS.Columbia@iaeste.org', 'e9d8557be0a22f4db4aecbb3d026b8882a0ba3af43d8de9be5f25b15a2131003', 'e3b2e289-1387-496f-ae60-096d86866521', 'NS', 'Columbia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('75213350-d711-4633-bb76-abdfccc61a25', 1, 10, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('e2c4cf76-1ab0-4e36-8ed2-1c01faf79f06', 1, 11, 1);
-- Completed generating test data for Columbia

-- Generating Test data for Switzerland
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('CH', 'Switzerland', 'Switzerland', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('048b9cb9-da6e-4d39-8e82-0cb8e934c0e2', 2, null, 2, 'Switzerland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('dd200ae8-56f6-4763-8391-2624e53c7a02', 5, 12, 2, 'Switzerland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('01a75a13-6ee7-4fc3-8281-a6322cecd66c', 'ACTIVE', 'switzerland@iaeste.ch', 'NS.Switzerland@iaeste.org', '8201e7219cb2e13224b497dc757e7ce86c1977001c129a5347ad743b09e772ca', 'a4825030-741f-4518-88e5-a0da2197b276', 'NS', 'Switzerland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('60afe82e-85fe-499e-8013-bacadb138620', 2, 12, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('b0a202c8-9944-4370-9441-562c3028d390', 2, 13, 1);
-- Completed generating test data for Switzerland

-- Generating Test data for Finland
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('FI', 'Finland', 'Finland', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('5ffe5e48-dcc4-44ca-b14a-032a8637cde6', 2, null, 3, 'Finland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('061119ee-90e3-4efb-9b5a-c6fd313f9dd6', 5, 14, 3, 'Finland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('5f5be707-b841-4e7a-aaca-210586b40342', 'ACTIVE', 'finland@iaeste.dk', 'NS.Finland@iaeste.org', 'ab3b64540508ae406a1e2069b7dbdfe863a617c6333505ba8c918d00d62f8f72', '74ac1785-41bf-43ac-9e86-887f67f2a700', 'NS', 'Finland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('7027c7d9-3bae-4d3c-bbb7-510fa87ae1a3', 3, 14, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8e4135ab-d3d6-4f23-9822-4e4274d216bd', 3, 15, 1);
-- Completed generating test data for Finland

-- Generating Test data for Germany
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('DE', 'Germany', 'Germany', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('9e14938d-428e-474e-aa82-141885a6d765', 2, null, 4, 'Germany');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('c61294ea-e74e-4d4a-ba4d-0cacc59e5108', 5, 16, 4, 'Germany');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('1a3b02a5-4e82-4c59-ba76-7f3f37cbde87', 'ACTIVE', 'germany@iaeste.de', 'NS.Germany@iaeste.org', 'ad4b778222dbc935f9b3eb698cb37979d127951c9cb365bdef1874e3f449cd32', '615b7f54-e53e-4372-a573-d3fbaf7d3a32', 'NS', 'Germany');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('4f80fac6-1a27-47e5-ac10-ab1b69fd4ef7', 4, 16, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('79811eaa-1abf-40b2-8b95-f6490e90fa72', 4, 17, 1);
-- Completed generating test data for Germany

-- Generating Test data for Austria
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('AT', 'Austria', 'Austria', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('b0461f21-3796-4b04-95ad-bb964450d623', 2, null, 5, 'Austria');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('d534909f-0971-4b02-8af2-2525ffef3d06', 5, 18, 5, 'Austria');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('36b793f7-fdf3-4472-805f-750b61d5ba29', 'ACTIVE', 'austria@iaeste.at', 'NS.Austria@iaeste.org', '163a1952890988a5b7900ba70031902d26c2a21d689624528558cb21624594a8', '55e78bb9-26aa-48e3-b95d-bb67ebec4063', 'NS', 'Austria');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('00a09264-fd21-4c58-8f7a-a874a88b479b', 5, 18, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('2d5a0b05-0bc9-46d5-b491-25c98ee50926', 5, 19, 1);
-- Completed generating test data for Austria

-- Generating Test data for Croatia
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('HR', 'Croatia', 'Croatia', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('79ff53d0-d139-4ca5-b298-1392737b05e7', 2, null, 6, 'Croatia');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('046ff5fb-ab89-4be6-a1c9-8fc49989d40a', 5, 20, 6, 'Croatia');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2956594e-9545-493b-a2ec-d128ae786c12', 'ACTIVE', 'croatia@iaeste.hr', 'NS.Croatia@iaeste.org', '7cadc26c40566d9793c120d79fdd1a52f41988faa50fcfc029cfe843ec805d63', '42bf00d8-3405-44bd-ac0f-e01a9b7cfb75', 'NS', 'Croatia');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('a258ddca-5911-44bf-9c46-27a5449dce6e', 6, 20, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('70ab8008-390e-4781-9a4d-0b993b1a3340', 6, 21, 1);
-- Completed generating test data for Croatia

-- Generating Test data for Jordan
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('JO', 'Jordan', 'Jordan', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('71e51ab9-2f07-4c74-b91d-bb969242d8e0', 2, null, 7, 'Jordan');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('72475289-64bd-4da5-8eea-d06ab70bcf0e', 5, 22, 7, 'Jordan');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('2cc3f631-15d4-4b05-967c-d71b7e74cbd2', 'ACTIVE', 'jordan@iaeste.jo', 'NS.Jordan@iaeste.org', '53ed0dd289df49121e12e0f56e8ef3cb6f7465005b8422aff6c798ced8aa156c', '033ba563-8de2-4484-bf81-b4641f362a7a', 'NS', 'Jordan');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('780d7844-6f5c-4b53-a7b6-3e268377a5a3', 7, 22, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('1d38ea7f-8649-4f3b-bd19-0959120c98fd', 7, 23, 1);
-- Completed generating test data for Jordan

-- Generating Test data for Poland
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('PL', 'Poland', 'Poland', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('ccc948ae-4b20-4056-867e-9a5ce29feb03', 2, null, 8, 'Poland');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('3e91e3a7-6228-4052-b10d-1a423074869d', 5, 24, 8, 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('8f1f634d-bb8b-4cc2-ac93-6dd2dd542775', 'ACTIVE', 'poland@iaeste.pl', 'NS.Poland@iaeste.org', '3aa50b2e8cc96e915377126b72b7efd322c768895fe84268e6b6d61ab213ee6c', '78cc69d9-af50-4a66-b50b-2bf4f12e2688', 'NS', 'Poland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8797fc39-d6b5-4e50-90ca-c78afe7549fd', 8, 24, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8d65b0b7-670c-4548-83c9-61b8b32387d4', 8, 25, 1);
-- Completed generating test data for Poland

-- Generating Test data for Norway
insert into countries (country_code, country_name, country_name_full, member_since, membership) values ('NO', 'Norway', 'Norway', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('ea30e709-2db0-4e77-9fc8-f989584c4091', 2, null, 9, 'Norway');
insert into Groups (external_id, grouptype_id, parent_id, country_id, groupName) values ('02fa8d7e-f836-44f0-8d8a-50cbd4273770', 5, 26, 9, 'Norway');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('110ee032-296d-4f54-9192-8031b8cbd7b9', 'ACTIVE', 'norway@iaeste.de', 'NS.Norway@iaeste.org', '977267481a2eb39fa7abc3776121e91c3deb28055bb4ef41117cf45f62874647', '256231c8-0a1c-45b0-aaeb-e0e411ca59ba', 'NS', 'Norway');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('885400a1-a256-4bec-b10c-566b9b635492', 9, 26, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values ('92d301ff-67a4-45b2-ad2b-2ef2d8fb6f25', 9, 27, 1);
-- Completed generating test data for Norway
