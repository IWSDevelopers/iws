-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.2 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the second DB version, and the IWS
-- version it is related to.
insert into versions (db_version, iws_version) values (3, '1.1.2');

-- =============================================================================
-- Group Corrections to allow monitoring
-- =============================================================================
alter table groups add column monitoring_level varchar(10) default 'NONE';
update groups set monitoring_level = 'NONE';
alter table groups add constraint group_monitoring_level check (monitoring_level is not null);

-- =============================================================================
-- Making the Board the default Administrator Group
-- =============================================================================
update groups set grouptype_id = 0 where id = 3;

-- =============================================================================
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
