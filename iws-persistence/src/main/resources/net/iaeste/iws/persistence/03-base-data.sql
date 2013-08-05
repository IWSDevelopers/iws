-- =============================================================================
-- Default Data for the IWS
-- =============================================================================


-- =============================================================================
-- Standard GroupTypes from net.iaeste.iws.api.enums.GroupType
-- =============================================================================
insert into grouptypes (id, grouptype, description) values (0, 'ADMINISTRATION', 'The Super type, designated to all functionality needed by the system administrators, to correct data at runtime without touching the underlying system.<br /> By default, only 2 groups exists with this group type: <ul> <li><b>Private</b><br /> Certain functionality in the system is determined private initially, and thus is assigned to this group, as the only information that matters when attempting to access it, is the user. </li> <li><b>Admin</b><br /> The main admin group, for functionality in the system, which only members with this access level may use. The primary goal for this, is to correct data or reconstruct reported bugs, if they are hard to track by more conventional matters. However, all usage of this functionality may only be made with the consent of the user involved, as it may otherwise be a criminal offense since data protection laws can be violated. </li> </ul> Note; users can only be member of 1 Administration Group!');
insert into grouptypes (id, grouptype, description) values (1, 'PRIVATE', 'All User Accounts have a Private Group assigned. And the User set as Owner. It is required, so data can only belong to a Group');
insert into grouptypes (id, grouptype, description) values (2, 'MEMBER', ' All members are assigned to this type, which gives the rights to the basic functionality in the system.<br /> Each member country have a designated Members group, where all their members are added. However, as some members may not be a member of a specific country, to avoid conflicts between their work and their national organization - another group exists called "Global", for all other members. Mostly this consists of the General Secretary, Ombudsman, IDT members, etc.<br /> Note; users can only be member of 1 Members Group!');
insert into grouptypes (id, grouptype, description) values (3, 'INTERNATIONAL', 'A number of Groups exists, which are truly "International", meaning that members of these groups may come from anywhere and there are no restrictions to this.<br /> International Groups serve very specific needs, which means that only the International Groups can post information in the system of general nature, i.e. accessible to all.<br /> The list of pre-defined International Groups include the following: <ul> <li><b>GS</b><br /> General Secretary, and assigned assistents.</li> <li><b>Board</b><br /> Members of the IAESTE A.s.b.l. Board. </li><li><b>SID</b><br /> Members who participate in the annual Seminar on IAESTE Development. </li> <li><b>IDT</b><br /> Members of the IAESTE Internet Development Team. </li> <li><b>Alumni</b><br /> The members of the IAESTE Alumni organization, formerly known as FoIN, The Friends of IAESTE Network. </li> <li><b>Jump</b><br /> Participants in the annual Jump event, a training forum for members who wishes to participate in International IAESTE work. </li> <li><b>Ombudsmand</b><br /> The IAESTE Ombudsmand.</li></ul>');
insert into grouptypes (id, grouptype, description) values (4, 'REGIONAL', 'Regional Groups are for a collection of Countries creating a region of the world. This can be anything like the Americas, Asia, Nordic Countries, Central European Countries, EU, etc. These types of groups have some similarities with International Groups, with some important difference. Members can only come from the region itself, and Regional Groups cannot share information for all to review.');
insert into grouptypes (id, grouptype, description) values (5, 'NATIONAL', 'All Countries have both a Members group, where all the people who are a part of the Organization in that country are listed. However, for the staff, certain other functionality is required. The National Group will make up for that.<br />  The type of functionality will consists of access to certain sections of the IntraWeb, and only some of the members of the Staff group will be allowed to join the NC''s Mailinglist.<br /> Note; users can only be member of 1 National or SAR Group!');
insert into grouptypes (id, grouptype, description) values (6, 'SAR', 'SAR''s or Self Administrated Region, is areas where there are discussions as to the regions self reliance. An example would be Denmark, where theregions Greenland and Faroe Islands are both SAR''s, with independent governments, but still relying on the primary country''s blessing incertain areas.<br /> Note; users can only be member of 1 National or SAR Group!');
insert into grouptypes (id, grouptype, description) values (7, 'LOCAL', 'Local Groups are for Local Committees around the Country. Local Groups will have a National Group as parent Group.');
insert into grouptypes (id, grouptype, description) values (8, 'WORKGROUP', 'For Groups, where you need only to have a common mailinglist as well as some other means of sharing information, the Workgroups will serve this purpose well.<br /> Workgroups can be assigned as a sub-group to any of the other groups.');
insert into grouptypes (id, grouptype, description) values (9, 'STUDENTS', 'Students Group, for allowing externals access to limited parts of the IntraWeb, currently unassigned.');

-- =============================================================================
-- Standard Roles
-- =============================================================================
insert into roles (id, external_id, role, description) values (1, '4a63d04d-f63f-4608-93b6-b85065a9cfb1', 'Owner', 'Each group may have 1 owner, that has access to all the data and permissions.');
insert into roles (id, external_id, role, description) values (2, 'a3e8a831-2ae7-4751-9668-dfd0f25274dc', 'Moderator', 'Moderators have access to all data and permissions, with the exception of being allowed to change the owner.');
insert into roles (id, external_id, role, description) values (3, '21bff3b2-ca3b-48ac-b7ea-28c1f0c06f2c', 'Member', 'Standard role for users, granted access to all data, and most common permissions that are related to administration of the Group.');
insert into roles (id, external_id, role, description) values (4, '15ac2e49-7142-43f1-b66a-adc87e628b02', 'Guest', 'Guests are only allowed to view or observe, they may not perform any operations, nor will be on the mailinglists.');
insert into roles (id, external_id, role, description) values (5, '29ee5196-0db1-401a-b4d3-f81518a62b9c', 'Student', 'Students are granted permission to apply for open offers.');

-- =============================================================================
-- Permissions from net.iaeste.iws.api.enums.Permission
-- =============================================================================
-- System Control: 0xx
insert into permissions (id, permission) values ( 7, 'FETCH_COUNTRIES');
insert into permissions (id, permission) values ( 8, 'PROCESS_COUNTRY');
-- Administration: 1xx
insert into permissions (id, permission) values (101, 'CONTROL_USER_ACCOUNT');
insert into permissions (id, permission) values (102, 'FETCH_USERS');
insert into permissions (id, permission) values (111, 'PROCESS_SUB_GROUPS');
insert into permissions (id, permission) values (112, 'FETCH_GROUPS');
insert into permissions (id, permission) values (113, 'FETCH_GROUP_MEMBERS');
insert into permissions (id, permission) values (114, 'CHANGE_GROUP_OWNER');
insert into permissions (id, permission) values (115, 'PROCESS_USER_GROUP_ASSIGNMENT');
--insert into permissions (id, permission) values (121, 'CREATE_ROLE');
--insert into permissions (id, permission) values (122, 'PROCESS_ROLE');
--insert into permissions (id, permission) values (123, 'FETCH_ROLES');
-- Exchange: 2xx
insert into permissions (id, permission) values (201, 'PROCESS_EMPLOYER');
insert into permissions (id, permission) values (202, 'FETCH_EMPLOYERS');
insert into permissions (id, permission) values (203, 'PROCESS_OFFER');
insert into permissions (id, permission) values (204, 'FETCH_OFFERS');
--insert into permissions (id, permission) values (12, 'PROCESS_OFFER_TEMPLATES');
--insert into permissions (id, permission) values (13, 'LOOKUP_OFFER_TEMPLATES');
--insert into permissions (id, permission) values (14, 'PROCESS_OFFER_PUBLISH_GROUPS');
--insert into permissions (id, permission) values (15, 'LOOKUP_OFFER_PUBLISH_GROUPS');
--insert into permissions (id, permission) values (16, 'LOOKUP_FACULTIES');
--insert into permissions (id, permission) values (17, 'PROCESS_FACULTIES');
--insert into permissions (id, permission) values (18, 'PROCESS_STUDENTS');
--insert into permissions (id, permission) values (19, 'LOOKUP_STUDENTS');
insert into permissions (id, permission) values (220, 'PROCESS_PUBLISH_OFFER');
insert into permissions (id, permission) values (221, 'FETCH_PUBLISH_OFFER');
insert into permissions (id, permission) values (222, 'APPLY_FOR_OPEN_OFFER');

-- =============================================================================
-- Linking the Permissions to both Roles & GroupTypes, so when we do perform a
-- lookup of a user who wishes to perform a given action against some data - all
-- we have to do, is compare the overlap of these two sets of data (Permissions
-- for GroupTypes, and Permissions for Roles), then we'll have the answer
-- =============================================================================

-- Permission 7 - Lookup Countries
--   -> GroupTypes: 0 Administration
--                  2 Member
--                  3 International
--                  4 Regional
--                  5 National
--                  6 SAR
--                  7 LOCAL
--                  8 WorkGroup
--                  9 Students
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
--                  4 Guest
--                  5 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (9, 7);
insert into permission_to_role (role_id, permission_id) values (1, 7);
insert into permission_to_role (role_id, permission_id) values (2, 7);
insert into permission_to_role (role_id, permission_id) values (3, 7);
insert into permission_to_role (role_id, permission_id) values (4, 7);
insert into permission_to_role (role_id, permission_id) values (5, 7);

-- Permission 8 - Process Country
--   -> GroupTypes: 0 Administration
--                  5 National
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 8);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 8);
insert into permission_to_role (role_id, permission_id) values (1, 8);
insert into permission_to_role (role_id, permission_id) values (2, 8);

-- Permission 102 - Control User Account
--   -> GroupTypes: 0 Administration
--                  2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 101);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 101);
insert into permission_to_role (role_id, permission_id) values (1, 101);
insert into permission_to_role (role_id, permission_id) values (2, 101);

-- Permission: 103 - Fetch Users
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 102);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 102);
insert into permission_to_role (role_id, permission_id) values (1, 102);
insert into permission_to_role (role_id, permission_id) values (2, 102);
insert into permission_to_role (role_id, permission_id) values (3, 102);

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

-- Permission: 115 - Process User Group Association
--   -> GroupTypes: All except Private & Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (9, 115);
insert into permission_to_role (role_id, permission_id) values (1, 115);
insert into permission_to_role (role_id, permission_id) values (2, 115);

-- Permission: 201 - Process Employer
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

-- Permission: 202 - Lookup Employers
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

-- Permission: 203 - Process Offer
--   -> GroupTypes: 5 National
--                  6 SAR
--                  7 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 203);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 203);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 203);
insert into permission_to_role (role_id, permission_id) values (1, 203);
insert into permission_to_role (role_id, permission_id) values (2, 203);

-- Permission: 204 - Lookup Offers
--   -> GroupTypes: 5 National
--                  6 SAR
--                  7 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 204);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 204);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 204);
insert into permission_to_role (role_id, permission_id) values (1, 204);
insert into permission_to_role (role_id, permission_id) values (2, 204);

-- Permission: 220 - Publish Offer
--   -> GroupTypes: 5 National
--                  6 SAR
--                  7 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 220);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 220);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 220);
insert into permission_to_role (role_id, permission_id) values (1, 220);
insert into permission_to_role (role_id, permission_id) values (2, 220);

-- Permission: 221 - Lookup Publish Offer
--   -> GroupTypes: 5 National
--                  6 SAR
--                  7 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 221);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 221);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 221);
insert into permission_to_role (role_id, permission_id) values (1, 221);
insert into permission_to_role (role_id, permission_id) values (2, 221);

-- Permission: 222 - Apply For Open Offer
--   -> GroupTypes: 9 Student
--   -> Roles:      5 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (9, 222);
insert into permission_to_role (role_id, permission_id) values (5, 222);

-- =============================================================================
-- Default Groups
-- =============================================================================
insert into groups (id, external_id, grouptype_id, groupName, full_name) values (0, '0bbe7a76-3864-4f8c-934f-2b47907c1daa', 0, 'SysOp', 'System Operators &amp; Administrators');
insert into groups (id, external_id, grouptype_id, groupName, full_name) values (1, '9eb79837-82a1-45eb-8f55-fd89a8592290', 2, 'Global', 'Global Members of IAESTE');
insert into groups (id, external_id, grouptype_id, groupName, full_name) values (2, '80962576-3e38-4858-be0d-57252e7316b1', 3, 'Board', 'Board of IAESTE A.s.b.l.');
insert into groups (id, external_id, grouptype_id, groupName, full_name) values (3, 'd8325230-a4b5-4063-b949-3233693c980d', 3, 'SID', 'Seminar on IAESTE Development');
insert into groups (id, external_id, grouptype_id, groupName, full_name) values (4, '2e351535-1609-4867-b713-2f8d6a2aab3f', 3, 'IDT', 'IAESTE Internet Development Team');


-- =============================================================================
-- Test Data
-- =============================================================================
-- Country Data
insert into countries (country_code, country_name, country_name_full, member_since) values ('AT', 'Austria', 'Austria', 1960);
insert into countries (country_code, country_name, country_name_full, member_since) values ('HR', 'Croatia', 'Croatia', 1990);
insert into countries (country_code, country_name, country_name_full, member_since) values ('DK', 'Denmark', 'Denmark', 1948);
insert into countries (country_code, country_name, country_name_full, member_since) values ('DE', 'Germany', 'Germany', 1970);
insert into countries (country_code, country_name, country_name_full, member_since) values ('PL', 'Poland', 'Poland', 1990);
insert into countries (country_code, country_name, country_name_full, member_since) values ('HU', 'Hungary', 'Hungary', 1990);

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
