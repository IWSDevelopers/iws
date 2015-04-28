-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.7 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the next DB version, and the IWS
-- version it is related to.
-- Commented out, as the current updates will not change the data model, only
-- correct some problems discovered with the Views & Data Content
--insert into versions (db_version, iws_version) values (5, '1.1.7');


-- =============================================================================
-- Corrections for Trac Task #837
-- =============================================================================

-- Cleanup of duplicate Swiss Employers, we cannot clear the char(11) before this is resolved.
update offers set employer_id = 12968 where employer_id = 8472;
update offers set employer_id = 12983 where employer_id = 12958;
update offers set employer_id = 9030 where employer_id = 3877;
--select address_id from employers where id in (8472, 13135, 12958, 3877);
delete from employers where id in (8472, 13135, 12958, 3877);
delete from addresses where id in (10704, 16543, 16191, 6109);

-- Corrections to Swiss Offers & Employers. For some reason they're filled with
--  char(11) or VT (Vertical Tab). These cause problems for WebServices.
update employers set
    name = regexp_replace(name, '[' || chr(11) || ']', E'\n', 'g')
where name like '%' || chr(11) || '%';
update employers set
    website = regexp_replace(website, '[' || chr(11) || ']', E'\n', 'g')
where website like '%' || chr(11) || '%';
update offers set
    work_description = regexp_replace(work_description, '[' || chr(7) || ']', '', 'g')
where work_description ilike '%' || chr(7) || '%';
update offers set
    work_description = regexp_replace(work_description, '[' || chr(11) || ']', E'\n', 'g')
where work_description like '%' || chr(11) || '%';
update offers set
    specializations = regexp_replace(specializations, '[' || chr(11) || ']', E'\n', 'g')
where specializations like '%' || chr(11) || '%';
update offers set
    other_requirements = regexp_replace(other_requirements, '[' || chr(7) || ']', '', 'g')
where other_requirements ilike '%' || chr(7) || '%';
update offers set
    other_requirements = regexp_replace(other_requirements, '[' || chr(11) || ']', E'\n', 'g')
where other_requirements like '%' || chr(11) || '%';


-- Correction to the Permissions, by mistake Students can also process folders.
-- It was initially added, since a Student Group could also have a folder - but
-- as Students only work with attachments, there simply is no need for this
-- permission.
-- Permission: 310 - Process Folder
--   -> GroupTypes: All except Student
delete from permission_to_grouptype where grouptype_id = 7 and permission_id = 310;

-- Change all current "Guest" to "Member", with all mail settings false
update user_to_group set role_id = 3, on_public_list = false, on_private_list = false, write_to_private_list = false where role_id = 4;
-- Remove all "Guest" Permissions
delete from permission_to_role where role_id = 4;

-- Update all Students to have no mail privileges
update user_to_group set on_private_list = false, on_public_list = false, write_to_private_list = false where role_id = 5;


-- =============================================================================
-- There was a mistake in the Views, so the GroupType that is being read in as
-- the String representation of the GroupType Enum, was actually just the Id!
-- So, the Views have been extended with correct reading of the value.
-- =============================================================================
drop view employer_view;
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
    t.grouptype                as group_grouptype,
    g.group_name               as group_groupname,
    g.list_name                as group_list_name,
    g.private_list             as group_private_list,
    g.public_list              as group_public_list,
    g.status                   as group_status,
    g.monitoring_level         as group_monitoring_level,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.pobox                    as address_pobox,
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
    groups g,
    grouptypes t
  where e.group_id = g.id
    and e.address_id = a.id
    and a.country_id = c.id
    and g.grouptype_id = t.id;


-- =============================================================================
-- The Offer View for Domestic Offers
-- =============================================================================
drop view offer_view;
create view offer_view as
  select
    o.id                       as offer_id,
    g.id                       as group_id,
    u.firstname                as ns_firstname,
    u.lastname                 as ns_lastname,
    o.external_id              as offer_external_id,
    o.ref_no                   as offer_ref_no,
    o.offer_type               as offer_offer_type,
    o.exchange_type            as offer_exchange_type,
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
    g.grouptype_id             as group_grouptype_id,
    t.grouptype                as group_grouptype,
    g.group_name               as group_groupname,
    g.list_name                as group_list_name,
    g.private_list             as group_private_list,
    g.public_list              as group_public_list,
    g.status                   as group_status,
    g.monitoring_level         as group_monitoring_level,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.pobox                    as address_pobox,
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
    grouptypes t,
    employers e,
    addresses a,
    countries c,
    user_to_group u2g,
    users u
  where e.id = o.employer_id
    and g.id = e.group_id
    and t.id = g.grouptype_id
    and a.id = e.address_id
    and c.id = a.country_id
    and u.id = u2g.user_id
    and e.group_id = u2g.group_id
    and u2g.role_id = 1;


-- =============================================================================
-- The Offer View for Shared Offers
-- =============================================================================
drop view shared_offer_view;
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
    o.offer_type               as offer_offer_type,
    o.exchange_type            as offer_exchange_type,
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
    g.grouptype_id             as group_grouptype_id,
    t.grouptype                as group_grouptype,
    g.group_name               as group_groupname,
    g.list_name                as group_list_name,
    g.private_list             as group_private_list,
    g.public_list              as group_public_list,
    g.status                   as group_status,
    g.monitoring_level         as group_monitoring_level,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.pobox                    as address_pobox,
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
    grouptypes t,
    offer_to_group o2g,
    employers e,
    addresses a,
    countries c,
    user_to_group u2g,
    users u
  where o.id = o2g.offer_id
    and e.id = o.employer_id
    and g.id = e.group_id
    and t.id = g.grouptype_id
    and a.id = e.address_id
    and c.id = a.country_id
    and u.id = u2g.user_id
    and e.group_id = u2g.group_id
    and u2g.role_id = 1;


-- =============================================================================
-- The Student View, which embeds Group, User, Roles & Countries
-- =============================================================================
drop view student_view;
create view student_view as
  select
    s.id                       as student_id,
    s.study_level              as student_study_level,
    s.study_fields             as student_study_fields,
    s.specializations          as student_specializations,
    s.available_from           as student_available_from,
    s.available_to             as student_available_to,
    s.language_1               as student_language_1,
    s.language_1_level         as student_language_1_level,
    s.language_2               as student_language_2,
    s.language_2_level         as student_language_2_level,
    s.language_3               as student_language_3,
    s.language_3_level         as student_language_3_level,
    s.modified                 as student_modified,
    s.created                  as student_created,
    u.id                       as user_id,
    u.external_id              as user_external_id,
    u.username                 as user_username,
    u.firstname                as user_firstname,
    u.lastname                 as user_lastname,
    u.status                   as user_status,
    u.modified                 as user_modified,
    u.created                  as user_created,
    g.id                       as group_id,
    g.external_id              as group_external_id,
    g.grouptype_id             as group_grouptype_id,
    t.grouptype                as group_grouptype,
    g.parent_id                as group_parent_id,
    g.group_name               as group_groupname,
    g.list_name                as group_list_name,
    g.private_list             as group_private_list,
    g.public_list              as group_public_list,
    g.status                   as group_status,
    g.monitoring_level         as group_monitoring_level,
    g.modified                 as group_modified,
    g.created                  as group_created,
    r.id                       as role_id,
    r.role                     as role_role,
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
    students s,
    users u,
    user_to_group ug,
    groups g,
    grouptypes t,
    countries c,
    grouptypes gt,
    roles r
  where u.id = s.user_id
    and u.id = ug.user_id
    and g.id = ug.group_id
    and t.id = g.grouptype_id
    and c.id = g.country_id
    and r.id = ug.role_id
    and g.grouptype_id = gt.id
    and gt.grouptype = 'STUDENT';


-- =============================================================================
-- The Application View, which embeds Offer, Student, User, Roles & Countries
-- =============================================================================
drop view application_view;
create view application_view as
  select
    sa.id                        as application_id,
    sa.external_id               as application_external_id,
    sa.status                    as application_status,
    sa.email                     as application_email,
    sa.phone_number              as application_phone_number,
    sa.date_of_birth             as application_date_of_birth,
    sa.university                as application_university,
    sa.place_of_birth            as application_place_of_birth,
    sa.gender                    as application_gender,
    sa.completed_years_of_study  as application_completed_years_of_study,
    sa.total_years_of_study      as application_total_years_of_study,
    sa.lodging_by_iaeste         as application_lodging_by_iaeste,
    sa.language_1                as application_language_1,
    sa.language_1_level          as application_language_1_level,
    sa.language_2                as application_language_2,
    sa.language_2_level          as application_language_2_level,
    sa.language_3                as application_language_3,
    sa.language_3_level          as application_language_3_level,
    sa.internship_start          as application_internship_start,
    sa.internship_end            as application_internship_end,
    sa.study_fields              as application_study_fields,
    sa.specializations           as application_specializations,
    sa.passport_number           as application_passport_number,
    sa.passport_place_of_issue   as application_passport_place_of_issue,
    sa.passport_valid_until      as application_passport_valid_until,
    sa.reject_by_employer_reason as application_reject_by_employer_reason,
    sa.reject_description        as application_reject_description,
    sa.reject_internal_comment   as application_reject_internal_comment,
    sa.nominated_at              as application_nominated_at,
    sa.modified                  as application_modified,
    sa.created                   as application_created,
    a_h.street1                  as home_address_street1,
    a_h.street2                  as home_address_street2,
    a_h.pobox                    as home_address_pobox,
    a_h.postal_code              as home_address_postal_code,
    a_h.city                     as home_address_city,
    a_h.state                    as home_address_state,
    a_h.country_id               as home_address_country_id,
    a_h.modified                 as home_address_modified,
    a_h.created                  as home_address_created,
    c_h.id                       as home_country_id,
    c_h.country_code             as home_country_code,
    c_h.country_name             as home_country_name,
    c_h.nationality              as home_country_nationality,
    c_h.phonecode                as home_country_phonecode,
    c_h.currency                 as home_country_currency,
    c_h.membership               as home_country_membership,
    c_h.member_since             as home_country_member_since,
    c_h.modified                 as home_country_modified,
    c_h.created                  as home_country_created,
    a_t.street1                  as term_address_street1,
    a_t.street2                  as term_address_street2,
    a_t.pobox                    as term_address_pobox,
    a_t.postal_code              as term_address_postal_code,
    a_t.city                     as term_address_city,
    a_t.state                    as term_address_state,
    a_t.modified                 as term_address_modified,
    a_t.created                  as term_address_created,
    c_t.id                       as term_country_id,
    c_t.country_code             as term_country_code,
    c_t.country_name             as term_country_name,
    c_t.nationality              as term_country_nationality,
    c_t.phonecode                as term_country_phonecode,
    c_t.currency                 as term_country_currency,
    c_t.membership               as term_country_membership,
    c_t.member_since             as term_country_member_since,
    c_t.modified                 as term_country_modified,
    c_t.created                  as term_country_created,
    c.country_code               as country_code,
    c.country_name               as country_name,
    c.nationality                as country_nationality,
    c.phonecode                  as country_phonecode,
    c.currency                   as country_currency,
    c.membership                 as country_membership,
    c.member_since               as country_member_since,
    c.modified                   as country_modified,
    c.created                    as country_created,
    s.id                         as student_id,
    s.study_level                as student_study_level,
    s.study_fields               as student_study_fields,
    s.specializations            as student_specializations,
    s.available_from             as student_available_from,
    s.available_to               as student_available_to,
    s.language_1                 as student_language_1,
    s.language_1_level           as student_language_1_level,
    s.language_2                 as student_language_2,
    s.language_2_level           as student_language_2_level,
    s.language_3                 as student_language_3,
    s.language_3_level           as student_language_3_level,
    s.modified                   as student_modified,
    s.created                    as student_created,
    u.id                         as user_id,
    u.external_id                as user_external_id,
    u.username                   as user_username,
    u.firstname                  as user_firstname,
    u.lastname                   as user_lastname,
    u.status                     as user_status,
    u.modified                   as user_modified,
    u.created                    as user_created,
    o.external_id                as offer_external_id,
    o.ref_no                     as offer_ref_no,
    o.status                     as offer_status,
    e.group_id                   as offer_group_id,
    og.group_id                  as application_group_id
  from
    student_applications sa
    left join addresses a_h on sa.home_address_id = a_h.id
    left join countries c_h on a_h.country_id = c_h.id
    left join addresses a_t on sa.address_during_terms_id = a_t.id
    left join countries c_t on a_t.country_id = c_t.id
    left join countries c   on sa.nationality = c.id,
    students s,
    users u,
    offer_to_group og,
    offers o,
    employers e
  where sa.student_id = s.id
    and s.user_id = u.id
    and sa.offer_group_id = og.id
    and e.id = o.employer_id
    and og.offer_id = o.id;


-- =============================================================================
-- View to Find Groups, which Offers are shared too
-- =============================================================================
drop view find_shared_to_groups;
create view find_shared_to_groups as
  select
    o2g.id                 as id,
    o2g.status             as status,
    p.parent_id            as offer_owner,
    o.id                   as offer_id,
    o.external_id          as offer_external_id,
    o.ref_no               as offer_ref_no,
    o.offer_type           as offer_offer_type,
    o.exchange_type        as offer_exchange_type,
    o.exchange_year        as exchange_year,
    o.nomination_deadline  as offer_nomination_deadline,
    g.id                   as group_id,
    g.external_id          as group_external_id,
    g.grouptype_id         as group_grouptype_id,
    t.grouptype            as group_grouptype,
    g.parent_id            as group_parent_id,
    g.group_name           as group_groupname,
    g.list_name            as group_list_name,
    g.private_list         as group_private_list,
    g.public_list          as group_public_list,
    g.status               as group_status,
    g.monitoring_level     as group_monitoring_level,
    g.modified             as group_modified,
    g.created              as group_created,
    c.country_code         as country_code,
    c.country_name         as country_name,
    c.country_name_full    as country_name_full,
    c.country_name_native  as country_name_native,
    c.nationality          as country_nationality,
    c.citizens             as country_citizens,
    c.phonecode            as country_phonecode,
    c.currency             as country_currency,
    c.languages            as country_languages,
    c.membership           as country_membership,
    c.member_since         as country_member_since,
    c.modified             as country_modified,
    c.created              as country_created
  from
    offer_to_group o2g,
    offers o,
    employers e,
    groups p,
    groups g,
    grouptypes t,
    countries c
  where o.id = o2g.offer_id
    and e.id = o.employer_id
    and p.id = e.group_id
    and g.id = o2g.group_id
    and t.id = g.grouptype_id
    and c.id = g.country_id;


-- =============================================================================
-- Statistics View for Foreign Offers
-- =============================================================================
drop view foreign_offer_statistics;
create view foreign_offer_statistics as
  select
    count(o2g.id)   as records,
    o2g.status      as status,
    o2g.group_id    as group_id,
    o.exchange_year as exchange_year
  from
    offers o,
    offer_to_group o2g
  where o.id = o2g.offer_id
  group by
    o2g.status,
    o2g.group_id,
    o.exchange_year;


-- =============================================================================
-- Statistics View for Domestic Offers
-- =============================================================================
drop view domestic_offer_statistics;
create view domestic_offer_statistics as
  select
    count (o.id)    as records,
    o.status        as status,
    e.group_id      as group_id,
    o.exchange_year as exchange_year
  from
    offers o,
    employers e
  where e.id = o.employer_id
  group by
    o.status,
    e.group_id,
    o.exchange_year;
