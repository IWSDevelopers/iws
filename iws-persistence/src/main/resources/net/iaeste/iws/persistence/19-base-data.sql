-- =============================================================================
-- Default Data for the IWS
-- =============================================================================

-- =============================================================================
-- Initial Version information, required to verify against
-- =============================================================================
insert into versions (db_version, iws_version) values (1, '1.0.4');

-- =============================================================================
-- Standard GroupTypes from net.iaeste.iws.api.enums.GroupType
-- =============================================================================
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (0, 'ADMINISTRATION', 'The Super type, designated to all functionality needed by the system emergencyContacts, to correct data at runtime without touching the underlying system.<br /> By default, only 2 groups exists with this group type: <ul> <li><b>Private</b><br /> Certain functionality in the system is determined private initially, and thus is assigned to this group, as the only information that matters when attempting to access it, is the user. </li> <li><b>Admin</b><br /> The main admin group, for functionality in the system, which only members with this access level may use. The primary goal for this, is to correct data or reconstruct reported bugs, if they are hard to track by more conventional matters. However, all usage of this functionality may only be made with the consent of the user involved, as it may otherwise be a criminal offense since data protection laws can be violated. </li> </ul> Note; users can only be member of 1 Administration Group!', 'ALL', true, true, 'PUBLIC');
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (1, 'PRIVATE', 'All User Accounts have a Private Group assigned. And the User set as Owner. It is required, so data can only belong to a Group', 'NONE', false, true, 'PROTECTED');
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (2, 'MEMBER', 'All members are assigned to this type, which gives the rights to the basic functionality in the system.<br /> Each member country have a designated Members group, where all their members are added. However, as some members may not be a member of a specific country, to avoid conflicts between their work and their national organization - another group exists called "Global", for all other members. Mostly this consists of the General Secretary, Ombudsman, IDT members, etc.<br /> Note; users can only be member of 1 Members Group!', 'NONE', true, false, 'PROTECTED');
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (3, 'INTERNATIONAL', 'Any Group, which purpose is not National.', 'ALL', true, true, 'PUBLIC');
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (4, 'NATIONAL', 'All Countries have both a Members group, where all the people who are a part of the Organization in that country are listed. However, for the staff, certain other functionality is required. The National Group will make up for that.<br />  The type of functionality will consists of access to certain sections of the IntraWeb, and only some of the members of the Staff group will be allowed to join the NC''s Mailinglist.<br /> Note; users can only be member of 1 National Group!', 'MEMBERS', false, true, 'PUBLIC');
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (5, 'LOCAL', 'Local Groups are for Local Committees around the Country. Local Groups will have a National Group as parent Group.', 'MEMBERS', true, true, 'PROTECTED');
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (6, 'WORKGROUP', 'For Groups, where you need only to have a common mailinglist as well as some other means of sharing information, the Workgroups will serve this purpose well.<br /> Workgroups can be assigned as a sub-group to any of the other groups.', 'INHERITED', true, true, 'PROTECTED');
insert into grouptypes (id, grouptype, description, who_may_join, private_list, public_list, folder_type) values (7, 'STUDENT', 'Students Group, for allowing externals access to limited parts of the IntraWeb, currently unassigned.', 'MEMBERS', false, false, 'NONE');

-- =============================================================================
-- Standard Roles
-- =============================================================================
insert into roles (id, external_id, role, description) values (1, '4a63d04d-f63f-4608-93b6-b85065a9cfb1', 'Owner', 'Each group may have 1 owner, that has access to all the data and permissions.');
insert into roles (id, external_id, role, description) values (2, 'a3e8a831-2ae7-4751-9668-dfd0f25274dc', 'Moderator', 'Moderators have access to all data and permissions, with the exception of being allowed to change the owner.');
insert into roles (id, external_id, role, description) values (3, '21bff3b2-ca3b-48ac-b7ea-28c1f0c06f2c', 'Member', 'Standard role for users, granted access to all data, and most common permissions that are related to administration of the Group.');
--insert into roles (id, external_id, role, description) values (4, '15ac2e49-7142-43f1-b66a-adc87e628b02', 'Guest', 'Guests are only allowed to view or observe, they may not perform any operations, nor will be on the mailinglists.');
insert into roles (id, external_id, role, description) values (5, '29ee5196-0db1-401a-b4d3-f81518a62b9c', 'Student', 'Students are granted permission to apply for open offers.');

-- =============================================================================
-- Permissions from net.iaeste.iws.api.enums.Permission
-- =============================================================================
-- System Control: 1xx
insert into permissions (id, permission, restricted) values (100, 'FETCH_COUNTRIES', false);
insert into permissions (id, permission, restricted) values (101, 'PROCESS_COUNTRY', true);
insert into permissions (id, permission, restricted) values (110, 'FETCH_COMMITTEES', false);
insert into permissions (id, permission, restricted) values (111, 'PROCESS_COMMITTEE', true);
insert into permissions (id, permission, restricted) values (120, 'FETCH_INTERNATIONAL_GROUPS', false);
insert into permissions (id, permission, restricted) values (121, 'PROCESS_INTERNATIONAL_GROUP', true);
insert into permissions (id, permission, restricted) values (150, 'FETCH_SURVEY_OF_COUNTRIES', false);
insert into permissions (id, permission, restricted) values (151, 'PROCESS_SURVEY_OF_COUNTRIES', false);
-- Administration: 2xx
insert into permissions (id, permission, restricted) values (200, 'CONTROL_USER_ACCOUNT', false);
insert into permissions (id, permission, restricted) values (201, 'FETCH_USER', false);
insert into permissions (id, permission, restricted) values (202, 'CHANGE_ACCOUNT_NAME', true);
insert into permissions (id, permission, restricted) values (205, 'PROCESS_ROLE', false);
insert into permissions (id, permission, restricted) values (210, 'PROCESS_GROUP', false);
insert into permissions (id, permission, restricted) values (211, 'CHANGE_GROUP_OWNER', false);
insert into permissions (id, permission, restricted) values (212, 'DELETE_GROUP', false);
insert into permissions (id, permission, restricted) values (213, 'PROCESS_USER_GROUP_ASSIGNMENT', false);
insert into permissions (id, permission, restricted) values (220, 'FETCH_EMERGENCY_LIST', false);
insert into permissions (id, permission, restricted) values (230, 'CREATE_STUDENT_ACCOUNT', false);
-- Storage 3xx
insert into permissions (id, permission, restricted) values (300, 'PROCESS_FILE', false);
insert into permissions (id, permission, restricted) values (301, 'FETCH_FILE', false);
insert into permissions (id, permission, restricted) values (310, 'PROCESS_FOLDER', false);
insert into permissions (id, permission, restricted) values (311, 'FETCH_FOLDER', false);

-- Exchange: 4xx
insert into permissions (id, permission, restricted) values (400, 'FETCH_OFFER_STATISTICS', false);
insert into permissions (id, permission, restricted) values (410, 'PROCESS_EMPLOYER', false);
insert into permissions (id, permission, restricted) values (411, 'FETCH_EMPLOYERS', false);
insert into permissions (id, permission, restricted) values (412, 'PROCESS_OFFER', false);
insert into permissions (id, permission, restricted) values (413, 'FETCH_OFFERS', false);

insert into permissions (id, permission, restricted) values (414, 'FETCH_GROUPS_FOR_SHARING', false);
insert into permissions (id, permission, restricted) values (420, 'PROCESS_OFFER_TEMPLATES', false);
insert into permissions (id, permission, restricted) values (421, 'FETCH_OFFER_TEMPLATES', false);

insert into permissions (id, permission, restricted) values (422, 'PROCESS_PUBLISH_OFFER', false);
insert into permissions (id, permission, restricted) values (423, 'FETCH_PUBLISH_GROUPS', false);

insert into permissions (id, permission, restricted) values (424, 'APPLY_FOR_OPEN_OFFER', false);
insert into permissions (id, permission, restricted) values (450, 'PROCESS_STUDENT', false);
insert into permissions (id, permission, restricted) values (451, 'FETCH_STUDENTS', false);
insert into permissions (id, permission, restricted) values (452, 'PROCESS_STUDENT_APPLICATION', false);
insert into permissions (id, permission, restricted) values (453, 'FETCH_STUDENT_APPLICATION', false);

-- =============================================================================
-- Linking the Permissions to both Roles & GroupTypes, so when we do perform a
-- lookup of a user who wishes to perform a given action against some data - all
-- we have to do, is compare the overlap of these two sets of data (Permissions
-- for GroupTypes, and Permissions for Roles), then we'll have the answer
-- =============================================================================

-- Permission 100 - Fetch Countries
--   -> GroupTypes: 0 Administration
--                  2 Member
--                  3 International
--                  4 National
--                  5 Local
--                  6 WorkGroup
--                  7 Students
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
--                  5 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 100);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 100);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 100);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 100);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 100);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 100);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 100);
insert into permission_to_role (role_id, permission_id) values (1, 100);
insert into permission_to_role (role_id, permission_id) values (2, 100);
insert into permission_to_role (role_id, permission_id) values (3, 100);
insert into permission_to_role (role_id, permission_id) values (5, 100);

-- Permission 101 - Process Country
--   -> GroupTypes: 0 Administration
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 101);
insert into permission_to_role (role_id, permission_id) values (1, 101);
insert into permission_to_role (role_id, permission_id) values (2, 101);

-- Permission 110 - Fetch Committees
--   -> GroupTypes: 0 Administration
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 110);
insert into permission_to_role (role_id, permission_id) values (1, 110);
insert into permission_to_role (role_id, permission_id) values (2, 110);
insert into permission_to_role (role_id, permission_id) values (3, 110);

-- Permission 111 - Process Committee
--   -> GroupTypes: 0 Administration
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 111);
insert into permission_to_role (role_id, permission_id) values (1, 111);
insert into permission_to_role (role_id, permission_id) values (2, 111);

-- Permission 120 - Fetch International Groups
--   -> GroupTypes: 0 Administration
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 120);
insert into permission_to_role (role_id, permission_id) values (1, 120);
insert into permission_to_role (role_id, permission_id) values (2, 120);
insert into permission_to_role (role_id, permission_id) values (3, 120);

-- Permission 121 - Process International Group
--   -> GroupTypes: 0 Administration
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 121);
insert into permission_to_role (role_id, permission_id) values (1, 121);
insert into permission_to_role (role_id, permission_id) values (2, 121);

-- Permission 150 - Fetch Survey of Countries
--   -> GroupTypes: 0 Administration
--                  3 International
--                  4 National
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 150);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 150);
insert into permission_to_role (role_id, permission_id) values (1, 150);
insert into permission_to_role (role_id, permission_id) values (2, 150);

-- Permission 151 - Process Survey of Countries
--   -> GroupTypes: 4 National
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 151);
insert into permission_to_role (role_id, permission_id) values (1, 151);
insert into permission_to_role (role_id, permission_id) values (2, 151);

-- Permission 200 - Control User Account
--   -> GroupTypes: 2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 200);
insert into permission_to_role (role_id, permission_id) values (1, 200);
insert into permission_to_role (role_id, permission_id) values (2, 200);

-- Permission: 201 - Fetch Users
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 201);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 201);
insert into permission_to_role (role_id, permission_id) values (1, 201);
insert into permission_to_role (role_id, permission_id) values (2, 201);
insert into permission_to_role (role_id, permission_id) values (3, 201);

-- Permission 202 - Change Account Name
--   -> GroupTypes: 0 Administration
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 202);
insert into permission_to_role (role_id, permission_id) values (1, 202);
insert into permission_to_role (role_id, permission_id) values (2, 202);

-- Permission 205 - Process Role
--   -> GroupTypes: 2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 205);
insert into permission_to_role (role_id, permission_id) values (1, 205);
insert into permission_to_role (role_id, permission_id) values (2, 205);

-- Permission: 210 - Process Group
--   -> GroupTypes: All except Private & Student
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 210);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 210);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 210);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 210);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 210);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 210);
insert into permission_to_role (role_id, permission_id) values (1, 210);
insert into permission_to_role (role_id, permission_id) values (2, 210);

-- Permission: 211 - Change Group Owner
--   -> GroupTypes: All except Private
--   -> Roles:      1 Owner
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 211);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 211);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 211);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 211);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 211);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 211);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 211);
insert into permission_to_role (role_id, permission_id) values (1, 211);

-- Permission: 212 - Delete Group
--   -> GroupTypes: All except Private, Work Group & Students
--   - Roles:       1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 212);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 212);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 212);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 212);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 212);
insert into permission_to_role (role_id, permission_id) values (1, 212);
insert into permission_to_role (role_id, permission_id) values (2, 212);

-- Permission: 213 - Process User Group Assignment
--   -> GroupTypes: All except Private
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 213);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 213);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 213);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 213);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 213);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 213);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 213);
insert into permission_to_role (role_id, permission_id) values (1, 213);
insert into permission_to_role (role_id, permission_id) values (2, 213);

-- Permission: 220 - Fetch NC's List
--   -> GroupTypes: 0 Administration
--                  4 National
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 220);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 220);
insert into permission_to_role (role_id, permission_id) values (1, 220);
insert into permission_to_role (role_id, permission_id) values (2, 220);

-- Permission 230 - Create Student Account
--   -> GroupTypes: 4 National
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 230);
insert into permission_to_role (role_id, permission_id) values (1, 230);
insert into permission_to_role (role_id, permission_id) values (2, 230);
insert into permission_to_role (role_id, permission_id) values (3, 230);

-- Permission: 300 - Process File
--   -> GroupTypes: All
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
--                  5 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 300);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 300);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 300);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 300);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 300);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 300);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 300);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 300);
insert into permission_to_role (role_id, permission_id) values (1, 300);
insert into permission_to_role (role_id, permission_id) values (2, 300);
insert into permission_to_role (role_id, permission_id) values (3, 300);
insert into permission_to_role (role_id, permission_id) values (5, 300);

-- Permission: 301 - Fetch File
--   -> GroupTypes: All
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
--                  5 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 301);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 301);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 301);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 301);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 301);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 301);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 301);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 301);
insert into permission_to_role (role_id, permission_id) values (1, 301);
insert into permission_to_role (role_id, permission_id) values (2, 301);
insert into permission_to_role (role_id, permission_id) values (3, 301);
insert into permission_to_role (role_id, permission_id) values (5, 301);

-- Permission: 310 - Process Folder
--   -> GroupTypes: All except Student
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 310);
-- TODO Remove Student from this listing!
--insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 310);
insert into permission_to_role (role_id, permission_id) values (1, 310);
insert into permission_to_role (role_id, permission_id) values (2, 310);

-- Permission: 311 - Fetch Folder
--   -> GroupTypes: 2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 311);
insert into permission_to_role (role_id, permission_id) values (1, 311);
insert into permission_to_role (role_id, permission_id) values (2, 311);
insert into permission_to_role (role_id, permission_id) values (3, 311);

-- Permission: 400 - Fetch Offer Statistics
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 400);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 400);
insert into permission_to_role (role_id, permission_id) values (1, 400);
insert into permission_to_role (role_id, permission_id) values (2, 400);
insert into permission_to_role (role_id, permission_id) values (3, 400);

-- Permission: 410 - Process Employer
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 410);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 410);
insert into permission_to_role (role_id, permission_id) values (1, 410);
insert into permission_to_role (role_id, permission_id) values (2, 410);
insert into permission_to_role (role_id, permission_id) values (3, 410);

-- Permission: 411 - Fetch Employers
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 411);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 411);
insert into permission_to_role (role_id, permission_id) values (1, 411);
insert into permission_to_role (role_id, permission_id) values (2, 411);
insert into permission_to_role (role_id, permission_id) values (3, 411);

-- Permission: 412 - Process Offer
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 412);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 412);
insert into permission_to_role (role_id, permission_id) values (1, 412);
insert into permission_to_role (role_id, permission_id) values (2, 412);
insert into permission_to_role (role_id, permission_id) values (3, 412);

-- Permission: 413 - Fetch Offers
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 413);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 413);
insert into permission_to_role (role_id, permission_id) values (1, 413);
insert into permission_to_role (role_id, permission_id) values (2, 413);
insert into permission_to_role (role_id, permission_id) values (3, 413);

-- Permission: 414 - Fetch Groups for Sharing
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 414);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 414);
insert into permission_to_role (role_id, permission_id) values (1, 414);
insert into permission_to_role (role_id, permission_id) values (2, 414);
insert into permission_to_role (role_id, permission_id) values (3, 414);

-- Permission: 420 - Process Offer Templates
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 420);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 420);
insert into permission_to_role (role_id, permission_id) values (1, 420);
insert into permission_to_role (role_id, permission_id) values (2, 420);
insert into permission_to_role (role_id, permission_id) values (3, 420);

-- Permission: 421 - Fetch Offer Templates
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 421);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 421);
insert into permission_to_role (role_id, permission_id) values (1, 421);
insert into permission_to_role (role_id, permission_id) values (2, 421);
insert into permission_to_role (role_id, permission_id) values (3, 421);

-- Permission: 422 - Process Publish Offer
--   -> GroupTypes: 4 National
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 422);
insert into permission_to_role (role_id, permission_id) values (1, 422);
insert into permission_to_role (role_id, permission_id) values (2, 422);
insert into permission_to_role (role_id, permission_id) values (3, 422);

-- Permission: 423 - Fetch Publish Offer
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 423);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 423);
insert into permission_to_role (role_id, permission_id) values (1, 423);
insert into permission_to_role (role_id, permission_id) values (2, 423);
insert into permission_to_role (role_id, permission_id) values (3, 423);

-- Permission: 424 - Apply For Open Offer
--   -> GroupTypes: 7 Student
--   -> Roles:      5 Student
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 424);
insert into permission_to_role (role_id, permission_id) values (5, 424);

-- Permission: 450 - Process Student
--   -> GroupTypes: 4 National
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 450);
insert into permission_to_role (role_id, permission_id) values (1, 450);
insert into permission_to_role (role_id, permission_id) values (2, 450);
insert into permission_to_role (role_id, permission_id) values (3, 450);

-- Permission: 451 - Fetch Students
--   -> GroupTypes: 4 National
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 451);
insert into permission_to_role (role_id, permission_id) values (1, 451);
insert into permission_to_role (role_id, permission_id) values (2, 451);
insert into permission_to_role (role_id, permission_id) values (3, 451);

-- Permission: 452 - Process Student Application
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 452);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 452);
insert into permission_to_role (role_id, permission_id) values (1, 452);
insert into permission_to_role (role_id, permission_id) values (2, 452);
insert into permission_to_role (role_id, permission_id) values (3, 452);

-- Permission: 453 - Fetch Student Application
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 453);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 453);
insert into permission_to_role (role_id, permission_id) values (1, 453);
insert into permission_to_role (role_id, permission_id) values (2, 453);
insert into permission_to_role (role_id, permission_id) values (3, 453);

-- =============================================================================
-- Default Groups
-- =============================================================================
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (0, '0bbe7a76-3864-4f8c-934f-2b47907c1daa', 0, 0, 0, 'SysOp', 'System Operators & Administrators', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (1, '9eb79837-82a1-45eb-8f55-fd89a8592290', 8, 2, 1, 'Global', 'Global Members of IAESTE', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (2, '1db947ff-7db3-47be-a79c-421f3b3199ed', 1, 3, 1, 'GS', 'IAESTE General Secretary', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (3, '80962576-3e38-4858-be0d-57252e7316b1', 2, 0, 1, 'Board', 'Board of IAESTE A.s.b.l.', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (4, 'd8325230-a4b5-4063-b949-3233693c980d', 3, 3, 1, 'SID', 'Seminar on IAESTE Development', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (5, '2e351535-1609-4867-b713-2f8d6a2aab3f', 4, 3, 1, 'IDT', 'IAESTE Internet Development Team', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (6, 'ab111bed-d5e8-4e34-877d-1d2bc56fdbf2', 5, 3, 1, 'Alumni', 'Friends of IAESTE Network | IAESTE Alumni',  '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (7, '6885d3f1-ac37-4a2b-9ae0-3cd9b8c7c917', 6, 3, 1, 'JUMP', 'IAESTE Jump Seminar',  '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (8, '16654461-b6e0-46cd-983e-d528e6db081a', 7, 3, 1, 'Ombudsman', 'IAESTE Ombudsman', '2003-10-01 00:00:00');
insert into groups (id, external_id, old_iw3_id, grouptype_id, parent_id, group_name, full_name, created) VALUES (9, '5b852a84-c557-460e-8c9d-ef098ae26e43', 9, 3, 1, 'CEC', 'CEC', '2003-10-01 00:00:00');

-- -- =============================================================================
-- -- Default Folders for file handling
-- -- =============================================================================
insert into folders (id, external_id, group_id, parent_id, foldername, privacy, old_iw3_file_id) VALUES ( 1, 'afec3bc0-296b-4bf2-8a9e-c2d7b74e93a0', 3, 0, 'Root', 'PUBLIC', 2);

-- =============================================================================
-- Default Setup for notification consumers
-- =============================================================================
insert into notification_consumers (group_id, name, class_name, active) values (0, 'System administration consumer', 'net.iaeste.iws.ejb.notifications.consumers.NotificationSystemAdministration', true);
insert into notification_consumers (group_id, name, class_name, active) values (0, 'Email message consumer', 'net.iaeste.iws.ejb.notifications.consumers.NotificationEmailSender', true);
