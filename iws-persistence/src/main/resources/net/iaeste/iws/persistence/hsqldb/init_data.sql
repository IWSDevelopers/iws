-- Permissions from net.iaeste.iws.api.enums.Permission
insert into permissions (id, permission) values ( 1, 'AUTHENTICATE');
insert into permissions (id, permission) values ( 2, 'AUTHORIZE');
insert into permissions (id, permission) values ( 3, 'PROCESS_USERS');
insert into permissions (id, permission) values ( 4, 'FETCH_USERS');
insert into permissions (id, permission) values ( 5, 'PROCESS_GROUPS');
insert into permissions (id, permission) values ( 6, 'FETCH_GROUPS');
insert into permissions (id, permission) values ( 7, 'FETCH_COUNTRIES');
insert into permissions (id, permission) values ( 8, 'PROCESS_COUNTRIES');
insert into permissions (id, permission) values ( 9, 'PROCESS_USER_GROUP_ASSIGNMENT');
insert into permissions (id, permission) values (10, 'PROCESS_OFFERS');
insert into permissions (id, permission) values (11, 'LOOKUP_OFFERS');
insert into permissions (id, permission) values (12, 'PROCESS_OFFER_TEMPLATES');
insert into permissions (id, permission) values (13, 'LOOKUP_OFFER_TEMPLATES');
insert into permissions (id, permission) values (14, 'PROCESS_OFFER_PUBLISH_GROUPS');
insert into permissions (id, permission) values (15, 'LOOKUP_OFFER_PUBLISH_GROUPS');
insert into permissions (id, permission) values (16, 'LOOKUP_FACULTIES');
insert into permissions (id, permission) values (17, 'PROCESS_FACULTIES');
insert into permissions (id, permission) values (18, 'PROCESS_STUDENTS');
insert into permissions (id, permission) values (19, 'LOOKUP_STUDENTS');

-- GroupTypes from net.iaeste.iws.api.enums.GroupType
insert into grouptypes (id, grouptype) values (0, 'Administration');
insert into grouptypes (id, grouptype) values (1, 'Members');
insert into grouptypes (id, grouptype) values (2, 'International');
insert into grouptypes (id, grouptype) values (3, 'Regional');
insert into grouptypes (id, grouptype) values (4, 'National');
insert into grouptypes (id, grouptype) values (5, 'SAR');
insert into grouptypes (id, grouptype) values (6, 'Local');
insert into grouptypes (id, grouptype) values (7, 'Workgroup');
insert into grouptypes (id, grouptype) values (8, 'Alumni');
insert into grouptypes (id, grouptype) values (9, 'Students');

-- The Default roles should be generic, meaning that they are not associated to
-- any specific functionality or title or group. The link between a user and a
-- group is made with a role - a user can only have 1 role in a group, it is
-- not possible to have additional roles. Customization at this level is
-- achieved by giving the user a custom title.
--   Any additonally created roles will be "custom", meaning that they serve a
-- different purpose, that can be linked towards specific needs.
insert into roles (id, role) values (0, 'Administrator');
insert into roles (id, role) values (1, 'Owner');
insert into roles (id, role) values (2, 'Moderator');
insert into roles (id, role) values (3, 'Member');
insert into roles (id, role) values (4, 'Guest');



---- Group
-- 1, 'Country'
insert into groups (id, grouptype_id, groupname) values ( 1, 1, 'Austria');
insert into groups (id, grouptype_id, groupname) values ( 2, 1, 'Croatia');
insert into groups (id, grouptype_id, groupname) values ( 3, 1, 'Germany');
insert into groups (id, grouptype_id, groupname) values ( 4, 1, 'Poland');

-- 2, 'LC'
-- Assume Austria has no LCs
insert into groups (id, grouptype_id, groupname) values ( 5, 2, 'LC Zagreb, Croatia');
insert into groups (id, grouptype_id, groupname) values ( 6, 2, 'LC Osijek, Croatia');
insert into groups (id, grouptype_id, groupname) values ( 7, 2, 'LC Bonn, Germany');
-- insert into groups (id, grouptype_id, groupname) values ( 8, 2, 'LC Munich, Germany');
-- insert into groups (id, grouptype_id, groupname) values ( 9, 2, 'LC Hamburg, Germany');
-- insert into groups (id, grouptype_id, groupname) values (10, 2, 'LC Wrozlaw, Poland');
-- insert into groups (id, grouptype_id, groupname) values (11, 2, 'LC Warsaw, Poland');
-- insert into groups (id, grouptype_id, groupname) values (12, 2, 'LC Krakow, Poland');

-- 3. 'Asbl'
insert into groups (id, grouptype_id, groupname) values (13, 3, 'IAESTE A.s.b.l');

-- 4. 'Asbl Departments'
insert into groups (id, grouptype_id, groupname) values (14, 4, 'IDT');
insert into groups (id, grouptype_id, groupname) values (15, 4, 'SID');


---- Permission to GroupType
-- 1, 'Country'
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1,  3);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1,  4);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1,  5);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1,  6);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1,  7);
-- --insert into permission_to_grouptype (grouptype_id, permission_id) values (1,  8);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1,  9);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 10);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 11);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 12);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 13);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 14);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 15);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 16);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 17);

-- 2. 'LC'
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 10);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 11);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 12);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 13);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 16);
-- insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 17);

-- 3, 'Asbl'
insert into permission_to_grouptype (grouptype_id, permission_id) values (3,  7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3,  8);


---- permissions to each role
-- 1, 'National Secretary' - all rights
-- insert into permission_to_role (role_id, permission_id) values (1,  3);
-- insert into permission_to_role (role_id, permission_id) values (1,  4);
-- insert into permission_to_role (role_id, permission_id) values (1,  5);
-- insert into permission_to_role (role_id, permission_id) values (1,  6);
-- insert into permission_to_role (role_id, permission_id) values (1,  7);
-- insert into permission_to_role (role_id, permission_id) values (1,  9);
-- insert into permission_to_role (role_id, permission_id) values (1, 10);
-- insert into permission_to_role (role_id, permission_id) values (1, 11);
-- insert into permission_to_role (role_id, permission_id) values (1, 12);
-- insert into permission_to_role (role_id, permission_id) values (1, 13);
-- insert into permission_to_role (role_id, permission_id) values (1, 14);
-- insert into permission_to_role (role_id, permission_id) values (1, 15);
-- insert into permission_to_role (role_id, permission_id) values (1, 16);
-- insert into permission_to_role (role_id, permission_id) values (1, 17);

-- 3, 'NC Exchange'
insert into permission_to_role (role_id, permission_id) values (3,  7);
insert into permission_to_role (role_id, permission_id) values (3, 10);
insert into permission_to_role (role_id, permission_id) values (3, 11);
insert into permission_to_role (role_id, permission_id) values (3, 12);
insert into permission_to_role (role_id, permission_id) values (3, 13);
insert into permission_to_role (role_id, permission_id) values (3, 14);
insert into permission_to_role (role_id, permission_id) values (3, 15);
insert into permission_to_role (role_id, permission_id) values (3, 16);
insert into permission_to_role (role_id, permission_id) values (3, 17);

-- -- 5, 'LC Exchange Incoming'
-- insert into permission_to_role (role_id, permission_id) values (5, 11);
--
-- -- 6, 'LC Exchange Outgoing'
-- insert into permission_to_role (role_id, permission_id) values (6, 10);
-- insert into permission_to_role (role_id, permission_id) values (6, 11);
-- insert into permission_to_role (role_id, permission_id) values (6, 12);
-- insert into permission_to_role (role_id, permission_id) values (6, 13);
-- insert into permission_to_role (role_id, permission_id) values (6, 16);
-- insert into permission_to_role (role_id, permission_id) values (6, 17);
--
-- -- 8, 'General Secretary'
-- insert into permission_to_role (role_id, permission_id) values (8, 7);
-- insert into permission_to_role (role_id, permission_id) values (8, 8);

---- User
insert into users (id, username, password) values (1, 'Michl', 'ff6668c9c0541301b18b3da3be4f719151eb0f873f3b74dbb036ee00434cee0f');
insert into users (id, username, password) values (2, 'Matej', '');
insert into users (id, username, password) values (3, 'Vanja', 'e96e02d8e47f2a7c03be5117b3ed175c52aa30fb22028cf9c96f261563577605');
insert into users (id, username, password) values (4, 'Marko', 'fc261a5e3e3c85a419825aad1ced0df53b9a3fa69bd439d1610eb99f8de6bcd6');
insert into users (id, username, password) values (5, 'Kim',   '26205d66c217a123c72d1e2bccd0536a12c39d65b085c224c4bbd0c75fcb3634');
insert into users (id, username, password) values (6, 'Daniel','26205d66c217a123c72d1e2bccd0536a12c39d65b085c224c4bbd0c75fcb3634');
insert into users (id, username, password) values (7, 'Michal', '');

---- User to Group
--- 1, 'Austria'
-- Michl: Nase, NC Member           in Austria
-- Matej: NC Member, NC Exchange    in Austria
--        IDT Member                in IDT
insert into user_to_group (user_id, group_id, role_id) values ( 1, 1, 1);
--insert into user_to_group (user_id, group_id, role_id) values ( 1, 1, 2);
--
-- insert into user_to_group (user_id, group_id, role_id) values ( 2, 1, 2);
-- --insert into user_to_group (user_id, group_id, role_id) values ( 2, 1, 3);
-- insert into user_to_group (user_id, group_id, role_id) values ( 2, 13, 10);
--
-- --- 2, 'Croatia'
-- -- Vanja: NaSe, NC Member           in Croatia
-- -- Marko: NC Member                 in LC Osijek      is not member in NC!
-- insert into user_to_group (user_id, group_id, role_id) values ( 3, 2, 1);
-- --insert into user_to_group (user_id, group_id, role_id) values ( 3, 2, 2);
-- -- 5, 'LC Zagreb, Croatia'
-- insert into user_to_group (user_id, group_id, role_id) values ( 4, 5, 4);
--
-- --- 3, 'Germany'
-- -- Kim: NC Member                   in Germany
-- --      LC Member, LC President     in Munich
-- -- Daniel: NC Member                in Germany
-- --      IDT Member, IDT Coordinator in IDT
-- insert into user_to_group (user_id, group_id, role_id) values ( 5, 3, 2);
-- insert into user_to_group (user_id, group_id, role_id) values ( 5, 8, 4);
-- --insert into user_to_group (user_id, group_id, role_id) values ( 5, 8, 7);
--
-- insert into user_to_group (user_id, group_id, role_id) values ( 6,  3, 2);
-- insert into user_to_group (user_id, group_id, role_id) values ( 6, 13, 9);
-- --insert into user_to_group (user_id, group_id, role_id) values ( 6, 13, 10);
--
-- --- 4, 'Poland'
-- -- Michal: NC Member, NC Exchange   in Poland
-- --         LC Member, LC Exch. Inc., LC Exch. Out.  in LC Krakow
-- insert into user_to_group (user_id, group_id, role_id) values ( 7, 4, 2);
-- --insert into user_to_group (user_id, group_id, role_id) values ( 7, 4, 3);
-- -- 12, 'LC Krakow, Poland'
-- insert into user_to_group (user_id, group_id, role_id) values ( 7, 12, 4);
-- --insert into user_to_group (user_id, group_id, role_id) values ( 7, 12, 5);
-- --insert into user_to_group (user_id, group_id, role_id) values ( 7, 12, 6);
--
-- --- 13, 'IDT'
-- -- Daniel:

---- countries
insert into countries (country_name) values ('Austria');
insert into countries (country_name) values ('Croatia');
insert into countries (country_name) values ('Germany');
insert into countries (country_name) values ('Poland');
