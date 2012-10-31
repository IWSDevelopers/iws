-- =============================================================================
-- Default Data for the IWS
-- =============================================================================

--  -- Standard GroupTypes
insert into grouptypes (id, grouptype, description) values (0, 'Administration', 'The Super type, designated to all functionality needed by the system administrators, to correct data at runtime without touching the underlying system.<br /> By default, only 2 groups exists with this group type: <ul> <li><b>Private</b><br /> Certain functionality in the system is determined private initially, and thus is assigned to this group, as the only information that matters when attempting to access it, is the user. </li> <li><b>Admin</b><br /> The main admin group, for functionality in the system, which only members with this access level may use. The primary goal for this, is to correct data or reconstruct reported bugs, if they are hard to track by more conventional matters. However, all usage of this functionality may only be made with the consent of the user involved, as it may otherwise be a criminal offense since data protection laws can be violated. </li> </ul> Note; users can only be member of 1 Administration Group!');
insert into grouptypes (id, grouptype, description) values (1, 'Members', ' All members are assigned to this type, which gives the rights to the basic functionality in the system.<br /> Each member country have a designated Members group, where all their members are added. However, as some members may not be a member of a specific country, to avoid conflicts between their work and their national organization - another group exists called "Global", for all other members. Mostly this consists of the General Secretary, Ombudsman, IDT members, etc.<br /> Note; users can only be member of 1 Members Group!');
insert into grouptypes (id, grouptype, description) values (2, 'International', 'A number of Groups exists, which are truly "International", meaning that members of these groups may come from anywhere and there are no restrictions to this.<br /> International Groups serve very specific needs, which means that only the International Groups can post information in the system of general nature, i.e. accessible to all.<br /> The list of pre-defined International Groups include the following: <ul> <li><b>GS</b><br /> General Secretary, and assigned assistents.</li> <li><b>Board</b><br /> Members of the IAESTE A.s.b.l. Board. </li><li><b>SID</b><br /> Members who participate in the annual Seminar on IAESTE Development. </li> <li><b>IDT</b><br /> Members of the IAESTE Internet Development Team. </li> <li><b>Alumni</b><br /> The members of the IAESTE Alumni organization, formerly known as FoIN, The Friends of IAESTE Network. </li> <li><b>Jump</b><br /> Participants in the annual Jump event, a training forum for members who wishes to participate in International IAESTE work. </li> <li><b>Ombudsmand</b><br /> The IAESTE Ombudsmand.</li></ul>');
insert into grouptypes (id, grouptype, description) values (3, 'Regional', 'Regional Groups are for a collection of Countries creating a region of the world. This can be anything like the Americas, Asia, Nordic Countries, Central European Countries, EU, etc. These types of groups have some similarities with International Groups, with some important difference. Members can only come from the region itself, and Regional Groups cannot share information for all to review.');
insert into grouptypes (id, grouptype, description) values (4, 'National', 'All Countries have both a Members group, where all the people who are a part of the Organization in that country are listed. However, for the staff, certain other functionality is required. The National Group will make up for that.<br />  The type of functionality will consists of access to certain sections of the IntraWeb, and only some of the members of the Staff group will be allowed to join the NC''s Mailinglist.<br /> Note; users can only be member of 1 National or SAR Group!');
insert into grouptypes (id, grouptype, description) values (5, 'SAR', 'SAR''s or Self Administrated Region, is areas where there are discussions as to the regions self reliance. An example would be Denmark, where theregions Greenland and Faroe Islands are both SAR''s, with independent governments, but still relying on the primary country''s blessing incertain areas.<br /> Note; users can only be member of 1 National or SAR Group!');
insert into grouptypes (id, grouptype, description) values (6, 'Local', 'Local Groups are for Local Committees around the Country. Local Groups will have a National Group as parent Group.');
insert into grouptypes (id, grouptype, description) values (7, 'Workgroup', 'For Groups, where you need only to have a common mailinglist as well as some other means of sharing information, the Workgroups will serve this purpose well.<br /> Workgroups can be assigned as a sub-group to any of the other groups.');
insert into grouptypes (id, grouptype, description) values (8, 'Alumni', 'Alumni Group. Currently unassigned.');
insert into grouptypes (id, grouptype, description) values (9, 'Students', 'Students Group, for allowing externals access to limited parts of the IntraWeb, currently unassigned.');

-- Standard Roles
insert into roles (id, role, description) values (0, 'Administrator', 'Super User, for all system related operations. Required for the initial setup.');
insert into roles (id, role, description) values (1, 'Owner', 'Each group may have 1 owner, that has access to all the data and permissions.');
insert into roles (id, role, description) values (2, 'Moderator', 'Moderators have access to all data and permissions, with the exception of being allowed to change the owner.');
insert into roles (id, role, description) values (3, 'Member', 'Standard role for users, granted access to all data, and most common permissions that are related to administration of the Group.');
insert into roles (id, role, description) values (4, 'Guest', 'Guests are only allowed to view or observe, they may not perform any operations, nor will be on the mailinglists.');

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
insert into users (id, external_id, username, password, firstname, lastname) values (1, '1', 'Michl', 'ff6668c9c0541301b18b3da3be4f719151eb0f873f3b74dbb036ee00434cee0f', '', '');
insert into users (id, external_id, username, password, firstname, lastname) values (2, '2', 'Matej', '', '', '');
insert into users (id, external_id, username, password, firstname, lastname) values (3, '3', 'Vanja', 'e96e02d8e47f2a7c03be5117b3ed175c52aa30fb22028cf9c96f261563577605', '', '');
insert into users (id, external_id, username, password, firstname, lastname) values (4, '4', 'Marko', 'fc261a5e3e3c85a419825aad1ced0df53b9a3fa69bd439d1610eb99f8de6bcd6', '', '');
insert into users (id, external_id, username, password, firstname, lastname) values (5, '5', 'Kim',   'ff6668c9c0541301b18b3da3be4f719151eb0f873f3b74dbb036ee00434cee0f', '', '');
insert into users (id, external_id, username, password, firstname, lastname) values (6, '6', 'Daniel','26205d66c217a123c72d1e2bccd0536a12c39d65b085c224c4bbd0c75fcb3634', '', '');
insert into users (id, external_id, username, password, firstname, lastname) values (7, '7', 'Michal', '', '', '');
insert into users (id, external_id, username, password, firstname, lastname) values (8, '8', 'Pavel', 'ff6668c9c0541301b18b3da3be4f719151eb0f873f3b74dbb036ee00434cee0f', '', '');

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
insert into countries (country_id, country_name) values ('at', 'Austria');
insert into countries (country_id, country_name) values ('hr', 'Croatia');
insert into countries (country_id, country_name) values ('dk', 'Denmark');
insert into countries (country_id, country_name) values ('de', 'Germany');
insert into countries (country_id, country_name) values ('pl', 'Poland');
