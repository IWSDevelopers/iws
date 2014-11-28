-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.2 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the second DB version, and the IWS
-- version it is related to.
insert into versions (db_version, iws_version) values (3, '1.1.2');

-- =============================================================================
-- Group Corrections to allow monitoring & Better Mailing list control
-- =============================================================================
alter table groups add column monitoring_level varchar(10) default 'NONE';
alter table groups add column private_list boolean;
alter table groups add column public_list boolean;
update groups set monitoring_level = 'NONE';
-- 0 = Admin; 1 = Private; 2 = Member; 3 = International; 4 = National; 5 = Local; 6 = WorkGroup; 7 = Student
update groups set private_list = false where grouptype_id in (   1,       4,       7);
update groups set private_list = true  where grouptype_id in (0,    2, 3,    5, 6);
update groups set public_list  = false where grouptype_id in (   1, 2,             7);
update groups set public_list  = true  where grouptype_id in (0,       3, 4, 5, 6);
alter table groups add constraint group_monitoring_level check (monitoring_level is not null);
alter table groups add constraint group_private_list     check (private_list is not null);
alter table groups add constraint group_public_list      check (public_list is not null);

-- =============================================================================
-- Making the Board the default Administrator Group
-- =============================================================================
update groups set grouptype_id = 0 where id = 3;

-- =============================================================================
-- Adding OfferType & ExchangeType to Offer, see Trac Task #930
-- =============================================================================
alter table offers add column offer_type varchar(10);
alter table offers add column exchange_type varchar(10);
update offers set offer_type = 'OPEN', exchange_type = 'COBE';
update offers set offer_type = 'LIMITED' where ref_no like '%-L';
update offers set offer_type = 'RESERVED' where ref_no like '%-R';
alter table offers add constraint offer_notnull_offer_type    check (offer_type is not null);
alter table offers add constraint offer_notnull_exchange_type check (exchange_type is not null);

--select ref_no from offers where ref_no like '%-C' or ref_no like '%-L' or ref_no like '%-R' order by ref_no asc;
-- First the simple updates, will fix around 400
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'AT-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'AU-20%-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'BD-20%-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'BR-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'CA-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'CA-20%-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'ES-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'HK-20%-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'IE-20%-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'IN-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'IN-20%-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'JO-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'NO-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'PS-20%-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'SI-20%-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'TH-20%-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'UK-20%-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no like 'UK-20%-L';
-- Updating individual offers causing problems
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000006-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000014-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000033-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000036-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000057-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000008-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000061-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000064-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000070-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000088-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000095-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000097-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000100-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000109-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000115-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000116-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000122-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000124-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000125-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000126-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000128-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000129-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000130-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000134-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000135-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000136-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000137-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000138-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000139-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000141-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000142-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000143-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000144-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000150-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000151-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000152-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000153-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000154-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000161-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000163-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000165-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000166-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000210-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000229-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-000241-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2014-XXXXXX-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000200-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000201-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000202-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000203-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000207-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000208-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000210-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000211-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000212-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000213-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000214-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000215-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000216-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000217-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000218-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000219-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000220-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000221-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000224-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000245-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000250-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000251-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CH-2015-000252-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'CZ-2014-000228-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'ES-2015-003200-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'ES-2015-005100-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'ES-2015-005101-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'ES-2015-007400-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'ES-2015-007401-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'IE-2014-0000CP-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'IE-2014-0000T1-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'IE-2014-000TAK-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'IE-2014-000ZT1-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'IE-2014-000ZT3-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'IE-2014-VMW005-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'IE-2015-CP0001-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'JP-2014-000002-R';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'JP-2014-00060A-L';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-BA1402-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-BA1404-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-BA1406-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-BA1416-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-KE1405-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-KE1410-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-KE1414-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-KE1416-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-ZA1403-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-ZA1405-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-ZA1406-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-ZA1407-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-ZA1410-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-ZA1411-C';
update offers set ref_no = substring(ref_no from 1 for 14) where ref_no = 'SK-2014-ZA1414-C';
-- Leaving the following offers as problems:
-- select offer_ref_no as refno, offer_exchange_year as year, country_name as country from offer_view where offer_ref_no in ('CH-2014-000043-C', 'CH-2014-000133-R', 'CH-2014-000145-C', 'CH-2015-000230-L', 'CH-2015-000238-C', 'ES-2015-001100-L', 'IE-2014-000CP2-C', 'SK-2014-ZA1418-C');
--       refno       | year |   country
-- ------------------+------+-------------
--  ES-2015-001100-L | 2015 | Spain
--  SK-2014-ZA1418-C | 2014 | Slovakia
--  IE-2014-000CP2-C | 2014 | Ireland
--  CH-2014-000133-R | 2014 | Switzerland
--  CH-2015-000230-L | 2015 | Switzerland
--  CH-2014-000145-C | 2014 | Switzerland
--  CH-2014-000043-C | 2014 | Switzerland
--  CH-2015-000238-C | 2015 | Switzerland
-- (8 rows)

--  =============================================================================
-- GroupType Corrections for mailing lists, required for our mail views, the
-- information is not mapped with our ORM, as these settings are fixed.
-- =============================================================================
alter table grouptypes add column private_list boolean;
alter table grouptypes add column public_list boolean;
update grouptypes set private_list = true,  public_list = true  where id = 0;
update grouptypes set private_list = false, public_list = true  where id = 1;
update grouptypes set private_list = true,  public_list = false where id = 2;
update grouptypes set private_list = true,  public_list = true  where id = 3;
update grouptypes set private_list = false, public_list = true  where id = 4;
update grouptypes set private_list = true,  public_list = true  where id = 5;
update grouptypes set private_list = true,  public_list = true  where id = 6;
update grouptypes set private_list = false, public_list = false where id = 7;


-- =============================================================================
-- Corrections for trac Tickets:
-- =============================================================================


-- =============================================================================
-- To ensure that the Permissions are all correct
-- =============================================================================
delete from permission_to_grouptype;
delete from permission_to_role;
delete from permissions;

-- System Control: 1xx
insert into permissions (id, permission) values (100, 'FETCH_COUNTRIES');
insert into permissions (id, permission) values (101, 'PROCESS_COUNTRY');
insert into permissions (id, permission) values (110, 'FETCH_COMMITTEES');
insert into permissions (id, permission) values (111, 'PROCESS_COMMITTEE');
insert into permissions (id, permission) values (120, 'FETCH_INTERNATIONAL_GROUPS');
insert into permissions (id, permission) values (121, 'PROCESS_INTERNATIONAL_GROUP');
insert into permissions (id, permission) values (150, 'FETCH_SURVEY_OF_COUNTRIES');
insert into permissions (id, permission) values (151, 'PROCESS_SURVEY_OF_COUNTRIES');
-- Administration: 2xx
insert into permissions (id, permission) values (200, 'CONTROL_USER_ACCOUNT');
insert into permissions (id, permission) values (201, 'FETCH_USER');
insert into permissions (id, permission) values (202, 'CHANGE_ACCOUNT_NAME');
insert into permissions (id, permission) values (210, 'PROCESS_GROUP');
insert into permissions (id, permission) values (211, 'CHANGE_GROUP_OWNER');
insert into permissions (id, permission) values (212, 'DELETE_GROUP');
insert into permissions (id, permission) values (213, 'PROCESS_USER_GROUP_ASSIGNMENT');
insert into permissions (id, permission) values (220, 'FETCH_EMERGENCY_LIST');
insert into permissions (id, permission) values (230, 'CREATE_STUDENT_ACCOUNT');
-- Storage 3xx
insert into permissions (id, permission) values (300, 'PROCESS_FILE');
insert into permissions (id, permission) values (301, 'FETCH_FILE');
-- Exchange: 4xx
insert into permissions (id, permission) values (400, 'FETCH_OFFER_STATISTICS');
insert into permissions (id, permission) values (410, 'PROCESS_EMPLOYER');
insert into permissions (id, permission) values (411, 'FETCH_EMPLOYERS');
insert into permissions (id, permission) values (412, 'PROCESS_OFFER');
insert into permissions (id, permission) values (413, 'FETCH_OFFERS');

insert into permissions (id, permission) values (414, 'FETCH_GROUPS_FOR_SHARING');
insert into permissions (id, permission) values (420, 'PROCESS_OFFER_TEMPLATES');
insert into permissions (id, permission) values (421, 'FETCH_OFFER_TEMPLATES');

insert into permissions (id, permission) values (422, 'PROCESS_PUBLISH_OFFER');
insert into permissions (id, permission) values (423, 'FETCH_PUBLISH_GROUPS');

insert into permissions (id, permission) values (424, 'APPLY_FOR_OPEN_OFFER');
insert into permissions (id, permission) values (450, 'PROCESS_STUDENT');
insert into permissions (id, permission) values (451, 'FETCH_STUDENTS');
insert into permissions (id, permission) values (452, 'PROCESS_STUDENT_APPLICATION');
insert into permissions (id, permission) values (453, 'FETCH_STUDENT_APPLICATION');

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
--                  4 Guest
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
insert into permission_to_role (role_id, permission_id) values (4, 100);
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
--   -> GroupTypes: 0 Administration
--                  2 Member
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 200);
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

-- Permission: 400 - Fetch Offer Statistics
--   -> GroupTypes: 4 National
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
--                  4 Guest
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 400);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 400);
insert into permission_to_role (role_id, permission_id) values (1, 400);
insert into permission_to_role (role_id, permission_id) values (2, 400);
insert into permission_to_role (role_id, permission_id) values (3, 400);
insert into permission_to_role (role_id, permission_id) values (4, 400);

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
-- Complete update with replacing the views
-- =============================================================================
drop view application_view;
drop view country_details;
drop view domestic_offer_statistics;
drop view employer_view;
drop view file_attachments;
drop view find_active_sessions;
drop view find_inactive_sessions;
drop view find_not_activated_accounts;
drop view find_shared_to_groups;
drop view foreign_offer_statistics;
drop view group_permissions;
drop view notification_job_task_details;
drop view offer_view;
drop view shared_offer_view;
drop view student_view;
drop view user_permissions;
drop view view_user_group;
drop view view_users;

\ir ../15-base-views.sql
\ir ../35-exchange-views.sql

-- =============================================================================
-- Following Views are added here as HyperSQL doesn't support them
-- =============================================================================

create view find_inactive_members as
  select
    u.id as user_id,
    u.created::date as created,
    u.username as username,
    g.group_name as country
  from
    users u
    left join user_to_group u2g on u.id = u2g.user_id
    left join groups g on g.id = u2g.group_id
  where u.status = 'NEW'
    and g.grouptype_id = 2
    and u.created < now() - interval '6 months'
  order by u.created desc;

create view find_active_sessions as
  select
    s.id          as session_id,
    s.session_key as session_key,
    s.user_id     as user_id,
    u.username    as username,
    u.firstname   as firstname,
    u.lastname    as lastname,
    s.deprecated  as deprecated,
    s.modified    as last_access,
    s.created     as session_start
  from
    sessions s,
    users u
  where u.id = s.user_id
    and s.deprecated = '0'
    and s.modified > now() - interval '12 hours'
  order by last_access desc;

create view find_inactive_sessions as
  select
    s.id          as session_id,
    s.session_key as session_key,
    s.user_id     as user_id,
    u.username    as username,
    u.firstname   as firstname,
    u.lastname    as lastname,
    s.deprecated  as deprecated,
    s.modified    as last_access,
    s.created     as session_start
  from
    sessions s,
    users u
  where u.id = s.user_id
    and s.deprecated = '0'
    and s.modified < now() - interval '12 hours'
  order by last_access desc;

-- =============================================================================
-- List User Information
-- =============================================================================
create view view_users as
  select
    u.id                                                                              as id,
    u.external_id                                                                     as external_id,
    u.firstname                                                                       as firstname,
    u.lastname                                                                        as lastname,
    u.username                                                                        as username,
    c.nationality                                                                     as nationality,
    case when u.salt in ('undefined', 'heartbleed') then u.salt else 'defined' end    as password,
    u.created::date                                                                   as created,
    case when max(s.created) is null then 'never' else max(s.created)::date::text end as last_login
  from
    countries c,
    persons p,
    users u
    left join sessions s on u.id = s.user_id
  where p.id = u.person_id
    and c.id = p.nationality
  group by
    u.id,
    u.external_id,
    u.firstname,
    u.lastname,
    u.username,
    c.nationality,
    u.salt,
    u.created;
