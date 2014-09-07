-- This is the 1.1 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the second DB version, and the IWS
-- version it is related to.
insert into versions (db_version, iws_version) values (2, '1.1.0');

-- =============================================================================
-- Corrections for trac Tickets: #668 & #734
-- =============================================================================
-- Start a transaction, so the process dies if there is an error somewhere
begin;
-- Trac #668 & #734 Moving Work Hours from Employer to Offer
alter table offers add work_days_per_week float;
alter table offers add weekly_hours float;
alter table offers add daily_hours float;
-- Updating existing records based on the Employer Information
update offers set weekly_hours = e.weekly_hours from employers e where e.id = employer_id;
update offers set daily_hours = e.daily_hours from employers e where e.id = employer_id;
-- The new field is not yet populated, so we're using 5 as default
update offers set work_days_per_week = 5;
-- Add new Unique Constraint
alter table offers add constraint offer_notnull_weeklyhours check (weekly_hours is not null);
-- Now to cleanup, we need to drop these views first:
drop view employer_view;
drop view offer_view;
drop view shared_offer_view;
-- Now we can drop the old fields from the Employer Table
alter table employers drop column weekly_hours;
alter table employers drop column daily_hours;
-- Save the changes
commit;

-- Now, we just have to add the views again :-)

-- =============================================================================
-- Corrections for trac Tickets: #890
-- -----------------------------------------------------------------------------
-- Add default value for offer_to_group.has_application
-- =============================================================================
-- Start a transaction, so the process dies if there is an error somewhere
begin;
-- Update existing rows and set default value
update offer_to_group set has_application = false where has_application is null;
alter table offer_to_group alter column has_application set default false;
-- Save the changes
commit;

-- =============================================================================
-- Corrections for trac Tickets: #668 & #734
-- -----------------------------------------------------------------------------
-- Changes to the Session Table, required to fix Trac ticket #846. Where are
-- converting the Deprecation field from a Timestamp to a Varchar, which will
-- make it easier to create a Unique Constraint, and use the default values in
-- Queries.
-- =============================================================================
-- Start a transaction, so the process dies if there is an error somewhere
begin;
--  First, drop current Unique Constraint
alter table sessions drop constraint session_unique_active_user;
-- Add new column which we use to convert the fields:
alter table sessions add new_deprecated varchar(32) default '0';
update sessions set new_deprecated = to_char(deprecated, 'YYYYMMDDHH24MISSMS');
update sessions set new_deprecated = '0' where deprecated is null;
-- Now, we'll drop Views depending on the deprecated field
drop view find_active_sessions;
drop view find_inactive_sessions;
-- Now, move the new column in as our primary deprecated column
alter table sessions drop column deprecated;
alter table sessions rename column new_deprecated to deprecated;
-- Finally, add the Unique Constraint and add an additional Not Null Constraint
alter table sessions add constraint session_unique_active_user unique (user_id, deprecated);
alter table sessions add constraint session_notnull_deprecated check (deprecated is not null);
-- Save the changes
commit;

-- Now, we just have to add the views again :-)

-- =============================================================================
-- Correcting the Permissions, this can be done by truncating the Permission
-- tables and add the data again:
truncate table permission_to_grouptype;
truncate table permission_to_role;
truncate table permissions;
-- =============================================================================

-- =============================================================================
-- Permissions from net.iaeste.iws.api.enums.Permission
-- =============================================================================
-- System Control: 1xx
insert into permissions (id, permission) values (100, 'FETCH_COUNTRIES');
insert into permissions (id, permission) values (101, 'PROCESS_COUNTRY');
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
--                  4 National
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 101);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 101);
insert into permission_to_role (role_id, permission_id) values (1, 101);
insert into permission_to_role (role_id, permission_id) values (2, 101);

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
--                  2 Member (This one should be removed if misused!!!!!)
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 202);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 202);
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
--   -> GroupTypes: 4 National
--   -> Roles:      1 Owner
--                  2 Moderator
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
--                  5 Local
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 422);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 422);
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
-- Views to find currently active/inactive sessions
-- -----------------------------------------------------------------------------
-- These two views are only relevant for administrative work in PostgreSQL.
-- =============================================================================
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
    and s.deprecated is null
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
    and s.deprecated is null
    and s.modified < now() - interval '12 hours'
  order by last_access desc;

-- =============================================================================
-- The Employer View, which embeds Address & Group
-- =============================================================================
create view employer_view as
  select
    e.id                       as id,
    e.external_id              as employer_external_id,
    e.name                     as employer_name,
    e.department               as employer_department,
    e.business                 as employer_business,
    e.number_of_employees      as employer_number_of_employees,
    e.website                  as employer_website,
    e.working_place            as employer_working_place,
    e.canteen                  as employer_canteen,
    e.nearest_airport          as employer_nearest_airport,
    e.nearest_public_transport as employer_nearest_public_transport,
    e.modified                 as employer_modified,
    e.created                  as employer_created,
    g.id                       as group_id,
    g.external_id              as group_external_id,
    g.parent_id                as group_parent_id,
    g.grouptype_id             as group_grouptype,
    g.group_name               as group_groupname,
    g.status                   as group_status,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.postal_code              as address_postal_code,
    a.city                     as address_city,
    a.state                    as address_state,
    a.modified                 as address_modified,
    a.created                  as address_created,
    c.country_code             as country_code,
    c.country_name             as country_name,
    c.country_name_full        as country_name_full,
    c.country_name_native      as country_name_native,
    c.nationality              as country_nationality,
    c.citizens                 as country_citizens,
    c.phonecode                as country_phonecode,
    c.currency                 as country_currency,
    c.languages                as country_languages,
    c.membership               as country_membership,
    c.member_since             as country_member_since,
    c.modified                 as country_modified,
    c.created                  as country_created
  from
    employers e,
    addresses a,
    countries c,
    groups g
  where e.group_id = g.id
    and e.address_id = a.id
    and a.country_id = c.id;


-- =============================================================================
-- The Offer View for Domestic Offers
-- =============================================================================
create view offer_view as
  select
    o.id                       as offer_id,
    g.id                       as group_id,
    u.firstname                as ns_firstname,
    u.lastname                 as ns_lastname,
    o.external_id              as offer_external_id,
    o.ref_no                   as offer_ref_no,
    o.old_refno                as offer_old_ref_no,
    o.exchange_year            as offer_exchange_year,
    o.work_description         as offer_work_description,
    o.work_type                as offer_work_type,
    o.weekly_hours             as offer_weekly_hours,
    o.daily_hours              as offer_daily_hours,
    o.work_days_per_week       as offer_work_days_per_week,
    o.study_levels             as offer_study_levels,
    o.study_fields             as offer_study_fields,
    o.specializations          as offer_specializations,
    o.prev_training_req        as offer_prev_training_req,
    o.other_requirements       as offer_other_requirements,
    o.min_weeks                as offer_min_weeks,
    o.max_weeks                as offer_max_weeks,
    o.from_date                as offer_from_date,
    o.to_date                  as offer_to_date,
    o.from_date_2              as offer_from_date_2,
    o.to_date_2                as offer_to_date_2,
    o.unavailable_from         as offer_unavailable_from,
    o.unavailable_to           as offer_unavailable_to,
    o.language_1               as offer_language_1,
    o.language_1_level         as offer_language_1_level,
    o.language_1_op            as offer_language_1_op,
    o.language_2               as offer_language_2,
    o.language_2_level         as offer_language_2_level,
    o.language_2_op            as offer_language_2_op,
    o.language_3               as offer_language_3,
    o.language_3_level         as offer_language_3_level,
    o.payment                  as offer_payment,
    o.payment_frequency        as offer_payment_frequency,
    o.currency                 as offer_currency,
    o.deduction                as offer_deduction,
    o.living_cost              as offer_living_cost,
    o.living_cost_frequency    as offer_living_cost_frequency,
    o.lodging_by               as offer_lodging_by,
    o.lodging_cost             as offer_lodging_cost,
    o.lodging_cost_frequency   as offer_lodging_cost_frequency,
    o.nomination_deadline      as offer_nomination_deadline,
    o.number_of_hard_copies    as offer_number_of_hard_copies,
    o.additional_information   as offer_additional_information,
    o.private_comment          as offer_private_comment,
    o.status                   as offer_status,
    o.modified                 as offer_modified,
    o.created                  as offer_created,
    e.external_id              as employer_external_id,
    e.name                     as employer_name,
    e.department               as employer_department,
    e.business                 as employer_business,
    e.number_of_employees      as employer_number_of_employees,
    e.website                  as employer_website,
    e.working_place            as employer_working_place,
    e.canteen                  as employer_canteen,
    e.nearest_airport          as employer_nearest_airport,
    e.nearest_public_transport as employer_nearest_public_transport,
    e.modified                 as employer_modified,
    e.created                  as employer_created,
    g.external_id              as group_external_id,
    g.parent_id                as group_parent_id,
    g.grouptype_id             as group_grouptype,
    g.group_name               as group_groupname,
    g.status                   as group_status,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.postal_code              as address_postal_code,
    a.city                     as address_city,
    a.state                    as address_state,
    a.modified                 as address_modified,
    a.created                  as address_created,
    c.country_code             as country_code,
    c.country_name             as country_name,
    c.country_name_full        as country_name_full,
    c.country_name_native      as country_name_native,
    c.nationality              as country_nationality,
    c.citizens                 as country_citizens,
    c.phonecode                as country_phonecode,
    c.currency                 as country_currency,
    c.languages                as country_languages,
    c.membership               as country_membership,
    c.member_since             as country_member_since,
    c.modified                 as country_modified,
    c.created                  as country_created
  from
    offers o,
    groups g,
    employers e,
    addresses a,
    countries c,
    user_to_group u2g,
    users u
  where e.id = o.employer_id
    and g.id = e.group_id
    and a.id = e.address_id
    and c.id = a.country_id
    and u.id = u2g.user_id
    and e.group_id = u2g.group_id
    and u2g.role_id = 1;


-- =============================================================================
-- The Offer View for Shared Offers
-- =============================================================================
create view shared_offer_view as
  select
    o.id                       as offer_id,
    g.id                       as group_id,
    o2g.id                     as shared_id,
    o2g.external_id            as shared_external_id,
    o2g.group_id               as shared_group_id,
    o2g.status                 as shared_status,
    o2g.comment                as shared_comment,
    o2g.hidden                 as shared_hidden,
    o2g.modified               as shared_modified,
    o2g.created                as shared_created,
    u.firstname                as ns_firstname,
    u.lastname                 as ns_lastname,
    o.external_id              as offer_external_id,
    o.ref_no                   as offer_ref_no,
    o.old_refno                as offer_old_ref_no,
    o.exchange_year            as offer_exchange_year,
    o.work_description         as offer_work_description,
    o.work_type                as offer_work_type,
    o.weekly_hours             as offer_weekly_hours,
    o.daily_hours              as offer_daily_hours,
    o.work_days_per_week       as offer_work_days_per_week,
    o.study_levels             as offer_study_levels,
    o.study_fields             as offer_study_fields,
    o.specializations          as offer_specializations,
    o.prev_training_req        as offer_prev_training_req,
    o.other_requirements       as offer_other_requirements,
    o.min_weeks                as offer_min_weeks,
    o.max_weeks                as offer_max_weeks,
    o.from_date                as offer_from_date,
    o.to_date                  as offer_to_date,
    o.from_date_2              as offer_from_date_2,
    o.to_date_2                as offer_to_date_2,
    o.unavailable_from         as offer_unavailable_from,
    o.unavailable_to           as offer_unavailable_to,
    o.language_1               as offer_language_1,
    o.language_1_level         as offer_language_1_level,
    o.language_1_op            as offer_language_1_op,
    o.language_2               as offer_language_2,
    o.language_2_level         as offer_language_2_level,
    o.language_2_op            as offer_language_2_op,
    o.language_3               as offer_language_3,
    o.language_3_level         as offer_language_3_level,
    o.payment                  as offer_payment,
    o.payment_frequency        as offer_payment_frequency,
    o.currency                 as offer_currency,
    o.deduction                as offer_deduction,
    o.living_cost              as offer_living_cost,
    o.living_cost_frequency    as offer_living_cost_frequency,
    o.lodging_by               as offer_lodging_by,
    o.lodging_cost             as offer_lodging_cost,
    o.lodging_cost_frequency   as offer_lodging_cost_frequency,
    o.nomination_deadline      as offer_nomination_deadline,
    o.number_of_hard_copies    as offer_number_of_hard_copies,
    o.additional_information   as offer_additional_information,
    o.private_comment          as offer_private_comment,
    o.status                   as offer_status,
    o.modified                 as offer_modified,
    o.created                  as offer_created,
    e.external_id              as employer_external_id,
    e.name                     as employer_name,
    e.department               as employer_department,
    e.business                 as employer_business,
    e.number_of_employees      as employer_number_of_employees,
    e.website                  as employer_website,
    e.working_place            as employer_working_place,
    e.canteen                  as employer_canteen,
    e.nearest_airport          as employer_nearest_airport,
    e.nearest_public_transport as employer_nearest_public_transport,
    e.modified                 as employer_modified,
    e.created                  as employer_created,
    g.external_id              as group_external_id,
    g.parent_id                as group_parent_id,
    g.grouptype_id             as group_grouptype,
    g.group_name               as group_groupname,
    g.status                   as group_status,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.postal_code              as address_postal_code,
    a.city                     as address_city,
    a.state                    as address_state,
    a.modified                 as address_modified,
    a.created                  as address_created,
    c.country_code             as country_code,
    c.country_name             as country_name,
    c.country_name_full        as country_name_full,
    c.country_name_native      as country_name_native,
    c.nationality              as country_nationality,
    c.citizens                 as country_citizens,
    c.phonecode                as country_phonecode,
    c.currency                 as country_currency,
    c.languages                as country_languages,
    c.membership               as country_membership,
    c.member_since             as country_member_since,
    c.modified                 as country_modified,
    c.created                  as country_created
  from
    offers o,
    groups g,
    offer_to_group o2g,
    employers e,
    addresses a,
    countries c,
    user_to_group u2g,
    users u
  where o.id = o2g.offer_id
    and e.id = o.employer_id
    and g.id = e.group_id
    and a.id = e.address_id
    and c.id = a.country_id
    and u.id = u2g.user_id
    and e.group_id = u2g.group_id
    and u2g.role_id = 1;

-- Done :-)
