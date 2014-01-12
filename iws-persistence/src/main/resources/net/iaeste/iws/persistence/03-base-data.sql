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
insert into grouptypes (id, grouptype, description) values (4, 'REGIONAL', 'Regional Groups are for a collection of Countries creating a state of the world. This can be anything like the Americas, Asia, Nordic Countries, Central European Countries, EU, etc. These types of groups have some similarities with International Groups, with some important difference. Members can only come from the state itself, and Regional Groups cannot share information for all to review.');
insert into grouptypes (id, grouptype, description) values (5, 'NATIONAL', 'All Countries have both a Members group, where all the people who are a part of the Organization in that country are listed. However, for the staff, certain other functionality is required. The National Group will make up for that.<br />  The type of functionality will consists of access to certain sections of the IntraWeb, and only some of the members of the Staff group will be allowed to join the NC''s Mailinglist.<br /> Note; users can only be member of 1 National or SAR Group!');
insert into grouptypes (id, grouptype, description) values (6, 'LOCAL', 'Local Groups are for Local Committees around the Country. Local Groups will have a National Group as parent Group.');
insert into grouptypes (id, grouptype, description) values (7, 'WORKGROUP', 'For Groups, where you need only to have a common mailinglist as well as some other means of sharing information, the Workgroups will serve this purpose well.<br /> Workgroups can be assigned as a sub-group to any of the other groups.');
insert into grouptypes (id, grouptype, description) values (8, 'STUDENT', 'Students Group, for allowing externals access to limited parts of the IntraWeb, currently unassigned.');

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
insert into permissions (id, permission) values (102, 'CHANGE_ACCOUNT_NAME');
insert into permissions (id, permission) values (103, 'FETCH_USERS');
insert into permissions (id, permission) values (111, 'PROCESS_GROUP');
insert into permissions (id, permission) values (112, 'DELETE_GROUP');
insert into permissions (id, permission) values (113, 'FETCH_GROUPS');
insert into permissions (id, permission) values (114, 'FETCH_GROUP_MEMBERS');
insert into permissions (id, permission) values (115, 'CHANGE_GROUP_OWNER');
insert into permissions (id, permission) values (116, 'PROCESS_USER_GROUP_ASSIGNMENT');
-- Storage 15x
insert into permissions (id, permission) values (150, 'PROCESS_FILE');
insert into permissions (id, permission) values (151, 'FETCH_FILE');
--insert into permissions (id, permission) values (121, 'CREATE_ROLE');
--insert into permissions (id, permission) values (122, 'PROCESS_ROLE');
--insert into permissions (id, permission) values (123, 'FETCH_ROLES');
-- Exchange: 2xx
insert into permissions (id, permission) values (200, 'FETCH_OFFER_STATISTICS');
insert into permissions (id, permission) values (201, 'PROCESS_EMPLOYER');
insert into permissions (id, permission) values (202, 'FETCH_EMPLOYERS');
insert into permissions (id, permission) values (203, 'PROCESS_OFFER');
insert into permissions (id, permission) values (204, 'FETCH_OFFERS');
insert into permissions (id, permission) values (210, 'PROCESS_STUDENT');
insert into permissions (id, permission) values (211, 'FETCH_STUDENTS');
--insert into permissions (id, permission) values (12, 'PROCESS_OFFER_TEMPLATES');
--insert into permissions (id, permission) values (13, 'LOOKUP_OFFER_TEMPLATES');
--insert into permissions (id, permission) values (14, 'PROCESS_OFFER_PUBLISH_GROUPS');
--insert into permissions (id, permission) values (15, 'LOOKUP_OFFER_PUBLISH_GROUPS');
--insert into permissions (id, permission) values (16, 'LOOKUP_FACULTIES');
--insert into permissions (id, permission) values (17, 'PROCESS_FACULTIES');
--insert into permissions (id, permission) values (19, 'LOOKUP_STUDENTS');
insert into permissions (id, permission) values (220, 'PROCESS_PUBLISH_OFFER');
insert into permissions (id, permission) values (221, 'FETCH_PUBLISH_OFFER');
insert into permissions (id, permission) values (222, 'APPLY_FOR_OPEN_OFFER');
insert into permissions (id, permission) values (223, 'PROCESS_STUDENT_APPLICATION');
insert into permissions (id, permission) values (224, 'FETCH_STUDENT_APPLICATION');

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
--                  6 Local
--                  7 WorkGroup
--                  8 Students
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

-- Permission 101 - Control User Account
--   -> GroupTypes: 0 Administration
--                  2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 101);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 101);
insert into permission_to_role (role_id, permission_id) values (1, 101);
insert into permission_to_role (role_id, permission_id) values (2, 101);

-- Permission 102 - Change Account Name
--   -> GroupTypes: 0 Administration
--                  2 Member (This one should be removed if misused!!!!!)
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
insert into permission_to_role (role_id, permission_id) values (1, 103);
insert into permission_to_role (role_id, permission_id) values (2, 103);
insert into permission_to_role (role_id, permission_id) values (3, 103);

-- Permission: 111 - Process Group
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
insert into permission_to_role (role_id, permission_id) values (1, 111);
insert into permission_to_role (role_id, permission_id) values (2, 111);

-- Permission: 112 - Delete Group
--   -> GroupTypes: All except Private, Work Group & Students
--   - Roles:       1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 112);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 112);
insert into permission_to_role (role_id, permission_id) values (1, 112);
insert into permission_to_role (role_id, permission_id) values (2, 112);

-- Permission: 113 - Fetch Groups
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
insert into permission_to_role (role_id, permission_id) values (1, 113);
insert into permission_to_role (role_id, permission_id) values (2, 113);
insert into permission_to_role (role_id, permission_id) values (3, 113);

-- Permission: 114 - Fetch Group Members
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 114);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 114);
insert into permission_to_role (role_id, permission_id) values (1, 114);
insert into permission_to_role (role_id, permission_id) values (2, 114);
insert into permission_to_role (role_id, permission_id) values (3, 114);

-- Permission: 115 - Change Group Owner
--   -> GroupTypes: All except Private
--   -> Roles:      1 Owner
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 115);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 115);
insert into permission_to_role (role_id, permission_id) values (1, 115);

-- Permission: 116 - Process User Group Assignment
--   -> GroupTypes: All except Private
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

-- Permission: 150 - Process File
--   -> GroupTypes: All
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
--                  4 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 150);
insert into permission_to_role (role_id, permission_id) values (1, 150);
insert into permission_to_role (role_id, permission_id) values (2, 150);
insert into permission_to_role (role_id, permission_id) values (3, 150);
insert into permission_to_role (role_id, permission_id) values (4, 150);

-- Permission: 151 - Fetch File
--   -> GroupTypes: All
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
--                  4 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 151);
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 151);
insert into permission_to_role (role_id, permission_id) values (1, 151);
insert into permission_to_role (role_id, permission_id) values (2, 151);
insert into permission_to_role (role_id, permission_id) values (3, 151);
insert into permission_to_role (role_id, permission_id) values (4, 151);

-- Permission: 200 - Fetch Offer Statistics
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 200);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 200);
insert into permission_to_role (role_id, permission_id) values (1, 200);
insert into permission_to_role (role_id, permission_id) values (2, 200);

-- Permission: 201 - Process Employer
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 201);
insert into permission_to_role (role_id, permission_id) values (1, 201);
insert into permission_to_role (role_id, permission_id) values (2, 201);

-- Permission: 202 - Lookup Employers
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 202);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 202);
insert into permission_to_role (role_id, permission_id) values (1, 202);
insert into permission_to_role (role_id, permission_id) values (2, 202);
insert into permission_to_role (role_id, permission_id) values (3, 202);

-- Permission: 203 - Process Offer
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 203);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 203);
insert into permission_to_role (role_id, permission_id) values (1, 203);
insert into permission_to_role (role_id, permission_id) values (2, 203);
insert into permission_to_role (role_id, permission_id) values (3, 203);

-- Permission: 204 - Lookup Offers
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 204);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 204);
insert into permission_to_role (role_id, permission_id) values (1, 204);
insert into permission_to_role (role_id, permission_id) values (2, 204);
insert into permission_to_role (role_id, permission_id) values (3, 204);

-- Permission: 210 - Process Student
--   -> GroupTypes: 5 National
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 210);
insert into permission_to_role (role_id, permission_id) values (1, 210);
insert into permission_to_role (role_id, permission_id) values (2, 210);
insert into permission_to_role (role_id, permission_id) values (3, 210);

-- Permission: 211 - Fetch Students
--   -> GroupTypes: 5 National
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 211);
insert into permission_to_role (role_id, permission_id) values (1, 211);
insert into permission_to_role (role_id, permission_id) values (2, 211);
insert into permission_to_role (role_id, permission_id) values (3, 211);

-- Permission: 220 - Publish Offer
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 220);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 220);
insert into permission_to_role (role_id, permission_id) values (1, 220);
insert into permission_to_role (role_id, permission_id) values (2, 220);
insert into permission_to_role (role_id, permission_id) values (3, 220);

-- Permission: 221 - Lookup Publish Offer
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 221);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 221);
insert into permission_to_role (role_id, permission_id) values (1, 221);
insert into permission_to_role (role_id, permission_id) values (2, 221);
insert into permission_to_role (role_id, permission_id) values (3, 221);

-- Permission: 222 - Apply For Open Offer
--   -> GroupTypes: 8 Student
--   -> Roles:      5 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (8, 222);
insert into permission_to_role (role_id, permission_id) values (5, 222);

-- Permission: 223 - Process Student Application
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 223);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 223);
insert into permission_to_role (role_id, permission_id) values (1, 223);
insert into permission_to_role (role_id, permission_id) values (2, 223);
insert into permission_to_role (role_id, permission_id) values (3, 223);

-- Permission: 224 - Fetch Student Application
--   -> GroupTypes: 5 National
--                  6 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 224);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 224);
insert into permission_to_role (role_id, permission_id) values (1, 224);
insert into permission_to_role (role_id, permission_id) values (2, 224);
insert into permission_to_role (role_id, permission_id) values (3, 224);

-- =============================================================================
-- Default Groups
-- =============================================================================
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (0, '0bbe7a76-3864-4f8c-934f-2b47907c1daa', 0, 0, 0, 'SysOp', 'System Operators & Administrators', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (1, '9eb79837-82a1-45eb-8f55-fd89a8592290', 8, 2, 1, 'Global', 'Global Members of IAESTE', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (2, '1db947ff-7db3-47be-a79c-421f3b3199ed', 1, 3, 1, 'GS', 'IAESTE General Secretary', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (3, '80962576-3e38-4858-be0d-57252e7316b1', 2, 3, 1, 'Board', 'Board of IAESTE A.s.b.l.', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (4, 'd8325230-a4b5-4063-b949-3233693c980d', 3, 3, 1, 'SID', 'Seminar on IAESTE Development', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (5, '2e351535-1609-4867-b713-2f8d6a2aab3f', 4, 3, 1, 'IDT', 'IAESTE Internet Development Team', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (6, 'ab111bed-d5e8-4e34-877d-1d2bc56fdbf2', 5, 3, 1, 'Alumni', 'Friends of IAESTE Network | IAESTE Alumni',  '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (7, '6885d3f1-ac37-4a2b-9ae0-3cd9b8c7c917', 6, 3, 1, 'JUMP', 'IAESTE Jump Seminar',  '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (8, '16654461-b6e0-46cd-983e-d528e6db081a', 7, 3, 1, 'Ombudsman', 'IAESTE Ombudsman', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (9, '5b852a84-c557-460e-8c9d-ef098ae26e43', 9, 4, 1, 'CEC', 'CEC', '2003-10-01 00:00:00');

-- -- =============================================================================
-- -- Default Folders for file handling
-- -- =============================================================================
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES ( 1, 'afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0', null, 'FOLDER', 'PUBLIC',  1, 'Root');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES ( 2, '0a0260bd-244d-4a87-a53e-b613dbbaf265', null, 'FOLDER', 'PUBLIC',  1, 'Library');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES ( 3, '59d02455-1537-49cb-9bf8-77a3aa755d18', null, 'FOLDER', 'PRIVATE', 1, 'Private');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES ( 4, '770ccaff-e739-43f2-b812-f30be84442bf', null, 'FOLDER', 'PRIVATE', 1, 'Committees');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (10, '9af925e7-4d3c-4762-992e-148200d7397f',    0, 'FOLDER', 'PRIVATE', 1, 'SysOp');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (11, '8018e16e-b5ac-40ed-bdcf-9b2b6122631f', null, 'FOLDER', 'PRIVATE', 1, 'GS');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (12, '09521a54-f5b2-4316-8fd0-793f23e6994e',    1, 'FOLDER', 'PRIVATE', 1, 'Board');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (13, 'c1722a2a-57ce-4f72-bd49-2880cdbd6f13',    3, 'FOLDER', 'PRIVATE', 1, 'SID');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (14, 'c23e2aba-05cb-4b09-8a34-e0191f6aebfe',    4, 'FOLDER', 'PRIVATE', 1, 'IDT');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (15, 'f3bc213f-be2b-4154-bdfa-1a022539d9c7', null, 'FOLDER', 'PRIVATE', 1, 'Alumni');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (16, 'f1145b61-2ec1-4a59-b442-57f76e096d57', null, 'FOLDER', 'PRIVATE', 1, 'Jump');
-- insert into files (id, external_id, group_id, filetype, availability, folder_id, filename) VALUES (17, '44b71637-789d-452e-8d9a-1b7ff848343d', null, 'FOLDER', 'PRIVATE', 1, 'Global');

-- =============================================================================
-- Default Setup for notification consumers
-- =============================================================================
insert into notification_consumers (group_id, name, class_name, active) values (0, 'System administration consumer', 'net.iaeste.iws.ejb.notifications.consumers.NotificationSystemAdministration', true);
insert into notification_consumers (group_id, name, class_name, active) values (0, 'Email message consumer', 'net.iaeste.iws.ejb.notifications.consumers.NotificationEmailSender', true);
