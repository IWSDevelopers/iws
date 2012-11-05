-- =============================================================================
-- Default Data for the IWS
-- =============================================================================


-- =============================================================================
-- Standard GroupTypes from net.iaeste.iws.api.enums.GroupType
-- =============================================================================
insert into grouptypes (id, grouptype, description) values (0, 'Administration', 'The Super type, designated to all functionality needed by the system administrators, to correct data at runtime without touching the underlying system.<br /> By default, only 2 groups exists with this group type: <ul> <li><b>Private</b><br /> Certain functionality in the system is determined private initially, and thus is assigned to this group, as the only information that matters when attempting to access it, is the user. </li> <li><b>Admin</b><br /> The main admin group, for functionality in the system, which only members with this access level may use. The primary goal for this, is to correct data or reconstruct reported bugs, if they are hard to track by more conventional matters. However, all usage of this functionality may only be made with the consent of the user involved, as it may otherwise be a criminal offense since data protection laws can be violated. </li> </ul> Note; users can only be member of 1 Administration Group!');
insert into grouptypes (id, grouptype, description) values (1, 'Private', 'All User Accounts have a Private Group assigned. And the User set as Owner. It is required, so data can only belong to a Group');
insert into grouptypes (id, grouptype, description) values (2, 'Members', ' All members are assigned to this type, which gives the rights to the basic functionality in the system.<br /> Each member country have a designated Members group, where all their members are added. However, as some members may not be a member of a specific country, to avoid conflicts between their work and their national organization - another group exists called "Global", for all other members. Mostly this consists of the General Secretary, Ombudsman, IDT members, etc.<br /> Note; users can only be member of 1 Members Group!');
insert into grouptypes (id, grouptype, description) values (3, 'International', 'A number of Groups exists, which are truly "International", meaning that members of these groups may come from anywhere and there are no restrictions to this.<br /> International Groups serve very specific needs, which means that only the International Groups can post information in the system of general nature, i.e. accessible to all.<br /> The list of pre-defined International Groups include the following: <ul> <li><b>GS</b><br /> General Secretary, and assigned assistents.</li> <li><b>Board</b><br /> Members of the IAESTE A.s.b.l. Board. </li><li><b>SID</b><br /> Members who participate in the annual Seminar on IAESTE Development. </li> <li><b>IDT</b><br /> Members of the IAESTE Internet Development Team. </li> <li><b>Alumni</b><br /> The members of the IAESTE Alumni organization, formerly known as FoIN, The Friends of IAESTE Network. </li> <li><b>Jump</b><br /> Participants in the annual Jump event, a training forum for members who wishes to participate in International IAESTE work. </li> <li><b>Ombudsmand</b><br /> The IAESTE Ombudsmand.</li></ul>');
insert into grouptypes (id, grouptype, description) values (4, 'Regional', 'Regional Groups are for a collection of Countries creating a region of the world. This can be anything like the Americas, Asia, Nordic Countries, Central European Countries, EU, etc. These types of groups have some similarities with International Groups, with some important difference. Members can only come from the region itself, and Regional Groups cannot share information for all to review.');
insert into grouptypes (id, grouptype, description) values (5, 'National', 'All Countries have both a Members group, where all the people who are a part of the Organization in that country are listed. However, for the staff, certain other functionality is required. The National Group will make up for that.<br />  The type of functionality will consists of access to certain sections of the IntraWeb, and only some of the members of the Staff group will be allowed to join the NC''s Mailinglist.<br /> Note; users can only be member of 1 National or SAR Group!');
insert into grouptypes (id, grouptype, description) values (6, 'SAR', 'SAR''s or Self Administrated Region, is areas where there are discussions as to the regions self reliance. An example would be Denmark, where theregions Greenland and Faroe Islands are both SAR''s, with independent governments, but still relying on the primary country''s blessing incertain areas.<br /> Note; users can only be member of 1 National or SAR Group!');
insert into grouptypes (id, grouptype, description) values (7, 'Local', 'Local Groups are for Local Committees around the Country. Local Groups will have a National Group as parent Group.');
insert into grouptypes (id, grouptype, description) values (8, 'Workgroup', 'For Groups, where you need only to have a common mailinglist as well as some other means of sharing information, the Workgroups will serve this purpose well.<br /> Workgroups can be assigned as a sub-group to any of the other groups.');
insert into grouptypes (id, grouptype, description) values (9, 'Students', 'Students Group, for allowing externals access to limited parts of the IntraWeb, currently unassigned.');

-- =============================================================================
-- Standard Roles
-- =============================================================================
insert into roles (id, role, description) values (1, 'Owner', 'Each group may have 1 owner, that has access to all the data and permissions.');
insert into roles (id, role, description) values (2, 'Moderator', 'Moderators have access to all data and permissions, with the exception of being allowed to change the owner.');
insert into roles (id, role, description) values (3, 'Member', 'Standard role for users, granted access to all data, and most common permissions that are related to administration of the Group.');
insert into roles (id, role, description) values (4, 'Guest', 'Guests are only allowed to view or observe, they may not perform any operations, nor will be on the mailinglists.');

-- =============================================================================
-- Permissions from net.iaeste.iws.api.enums.Permission
-- =============================================================================
insert into permissions (id, permission) values (101, 'CREATE_USER');
insert into permissions (id, permission) values (102, 'PROCESS_USER');
insert into permissions (id, permission) values (103, 'FETCH_USERS');
insert into permissions (id, permission) values (111, 'PROCESS_SUB_GROUPS');
insert into permissions (id, permission) values (112, 'FETCH_GROUPS');
insert into permissions (id, permission) values (113, 'FETCH_GROUP_MEMBERS');
insert into permissions (id, permission) values (114, 'CHANGE_GROUP_OWNER');
insert into permissions (id, permission) values (115, 'ADD_USER_GROUP_ASSOCIATION');
insert into permissions (id, permission) values (116, 'ALTER_USER_GROUP_ASSOCIATION');
--insert into permissions (id, permission) values (121, 'CREATE_ROLE');
--insert into permissions (id, permission) values (122, 'PROCESS_ROLE');
--insert into permissions (id, permission) values (123, 'FETCH_ROLES');
--insert into permissions (id, permission) values ( 7, 'FETCH_COUNTRIES');
insert into permissions (id, permission) values ( 8, 'PROCESS_COUNTRIES');
insert into permissions (id, permission) values ( 9, 'PROCESS_USER_GROUP_ASSIGNMENT');
insert into permissions (id, permission) values (201, 'PROCESS_OFFERS');
insert into permissions (id, permission) values (202, 'LOOKUP_OFFERS');
--insert into permissions (id, permission) values (12, 'PROCESS_OFFER_TEMPLATES');
--insert into permissions (id, permission) values (13, 'LOOKUP_OFFER_TEMPLATES');
--insert into permissions (id, permission) values (14, 'PROCESS_OFFER_PUBLISH_GROUPS');
--insert into permissions (id, permission) values (15, 'LOOKUP_OFFER_PUBLISH_GROUPS');
--insert into permissions (id, permission) values (16, 'LOOKUP_FACULTIES');
--insert into permissions (id, permission) values (17, 'PROCESS_FACULTIES');
--insert into permissions (id, permission) values (18, 'PROCESS_STUDENTS');
--insert into permissions (id, permission) values (19, 'LOOKUP_STUDENTS');


-- =============================================================================
-- Linking the Permissions to both Roles & GroupTypes, so when we do perform a
-- lookup of a user who wishes to perform a given action against some data - all
-- we have to do, is compare the overlap of these two sets of data (Permissions
-- for GroupTypes, and Permissions for Roles), then we'll have the answer
-- =============================================================================

-- Permission: 101 - Create User
--   -> GroupTypes: 0 Administration
--                  2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 101);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 101);
insert into permission_to_role (role_id, permission_id) values (1, 101);
insert into permission_to_role (role_id, permission_id) values (2, 101);

-- Permission 102 - Process User
--   -> GroupTypes: 0 Administration
--                  2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 102);
insert into permission_to_role (role_id, permission_id) values (1, 102);
insert into permission_to_role (role_id, permission_id) values (2, 102);

-- Permission: 103 - Fetch Users
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 103);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 103);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 103);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 103);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 103);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 103);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 103);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 103);
insert into permission_to_role (role_id, permission_id) values (1, 103);
insert into permission_to_role (role_id, permission_id) values (2, 103);
insert into permission_to_role (role_id, permission_id) values (3, 103);

-- Permission: 111 - Process Sub Group
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 111);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 111);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 111);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 111);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 111);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 111);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 111);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 111);
insert into permission_to_role (role_id, permission_id) values (1, 111);
insert into permission_to_role (role_id, permission_id) values (2, 111);

-- Permission: 112 - Fetch Groups
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 112);
insert into permission_to_role (role_id, permission_id) values (1, 112);
insert into permission_to_role (role_id, permission_id) values (2, 112);
insert into permission_to_role (role_id, permission_id) values (3, 112);

-- Permission: 113 - Fetch Group Members
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 113);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 113);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 113);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 113);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 113);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 113);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 113);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 113);
insert into permission_to_role (role_id, permission_id) values (1, 113);
insert into permission_to_role (role_id, permission_id) values (2, 113);
insert into permission_to_role (role_id, permission_id) values (3, 113);

-- Permission: 114 - Change Group Owner
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 114);
insert into permission_to_role (role_id, permission_id) values (1, 114);

-- Permission: 115 - add User Group Association
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 115);
insert into permission_to_role (role_id, permission_id) values (1, 115);
insert into permission_to_role (role_id, permission_id) values (2, 115);

-- Permission: 116 - Alter User Group Association
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 116);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 116);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 116);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 116);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 116);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 116);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 116);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 116);
insert into permission_to_role (role_id, permission_id) values (1, 116);
insert into permission_to_role (role_id, permission_id) values (2, 116);

-- Permission: 201 - Process Offer
--   -> GroupTypes: 5 National
--                  6 SAR
--                  7 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 201);
insert into permission_to_role (role_id, permission_id) values (1, 201);
insert into permission_to_role (role_id, permission_id) values (2, 201);

-- Permission: 202 - Lookup Offers
--   -> GroupTypes: 5 National
--                  6 SAR
--                  7 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 202);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 202);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 202);
insert into permission_to_role (role_id, permission_id) values (1, 202);
insert into permission_to_role (role_id, permission_id) values (2, 202);


-- =============================================================================
-- Default Groups
-- =============================================================================
insert into groups (id, grouptype_id, groupname, full_name) values (0, 0, 'SysOp', 'System Operators &amp; Administrators');
insert into groups (id, grouptype_id, groupname, full_name) values (1, 2, 'Global', 'Global Members of IAESTE');
insert into groups (id, grouptype_id, groupname, full_name) values (2, 3, 'Board', 'Board of IAESTE A.s.b.l.');
insert into groups (id, grouptype_id, groupname, full_name) values (3, 3, 'SID', 'Seminar on IAESTE Development');
insert into groups (id, grouptype_id, groupname, full_name) values (4, 3, 'IDT', 'IAESTE Internet Development Team');


-- =============================================================================
-- Test Data
-- =============================================================================
-- Country Data
insert into countries (country_id, country_name) values ('AT', 'Austria');
insert into countries (country_id, country_name) values ('HR', 'Croatia');
insert into countries (country_id, country_name) values ('DK', 'Denmark');
insert into countries (country_id, country_name) values ('DE', 'Germany');
insert into countries (country_id, country_name) values ('PL', 'Poland');

-- Couple of Member Groups
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (10, 2, null, 1, 'Austria');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (11, 5,   10, 1, 'Austria');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (12, 2, null, 2, 'Croatia');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (13, 5,   12, 2, 'Croatia');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (14, 2, null, 3, 'Denmark');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (15, 5,   14, 3, 'Denmark');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (16, 2, null, 4, 'Germany');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (17, 5,   16, 4, 'Germany');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (18, 2, null, 5, 'Poland');
insert into Groups (id, grouptype_id, parent_id, country_id, groupname) values (19, 5,   18, 5, 'Poland');

-- Couple of Users, password is the same as the username
insert into users (external_id, username, password, firstname, lastname) values ('13452874-0c0f-4caf-8101-a8e9b41d6e69', 'austria', '7112733729f24775a6e82d0a6ad7c8106643ad438fef97e33e069f23a2167266', 'NS', 'Austria');
insert into users (external_id, username, password, firstname, lastname) values ('23d4c552-dce4-4941-9254-9b7b87712c0e', 'croatia', 'f17f6b658c4b6feb54250a4266ee642d39b6ca2e2fbc7714a6dc36ae6e6b7c9a', 'NS', 'Croatia');
insert into users (external_id, username, password, firstname, lastname) values ('a14bb076-c2f7-419d-bd28-1d453ac3fda5', 'denmark', '5f0bf3ff6b72ac09f881221fc8bd88ba9f24a393c2e544716dbf792978a7f313', 'NS', 'Denmark');
insert into users (external_id, username, password, firstname, lastname) values ('047fde56-f5f7-4811-8a3d-a9b12d207570', 'germany', 'b2c01c8a8a0d9a99f145f099a963021f010dc608a8e992bd1a2aec958b48f32d', 'NS', 'Germany');
insert into users (external_id, username, password, firstname, lastname) values ('4bb8499a-f0dd-44d8-929e-0a7dbd218ed2', 'poland',  '9cbbdd6cbd5335f528ec9f858ee4ddd727186dca99e33f8fc3273d39681d54a8', 'NS', 'Poland');

-- User Group Associations
insert into user_to_group (user_id, group_id, role_id) values ( 1, 10, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 1, 11, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 2, 12, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 2, 13, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 3, 14, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 3, 15, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 4, 16, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 4, 17, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 5, 18, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 5, 19, 1);
