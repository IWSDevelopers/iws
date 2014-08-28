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
