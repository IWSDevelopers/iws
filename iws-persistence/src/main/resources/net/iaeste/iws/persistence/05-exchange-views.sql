-- =============================================================================
-- Please add all Exchange related views here
-- =============================================================================

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
    e.weekly_hours             as employer_weekly_hours,
    e.daily_hours              as employer_daily_hours,
    e.modified                 as employer_modified,
    e.created                  as employer_created,
    g.id                       as group_id,
    g.external_id              as group_external_id,
    g.parent_id                as group_parent_id,
    g.grouptype_id             as group_grouptype,
    g.groupName                as group_groupname,
    g.status                   as group_status,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.external_id              as address_external_id,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.zip                      as address_zip,
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
-- The Offer View, which embeds ???
-- =============================================================================
create view offer_view as
  select
    o.id                       as offer_id,
    g.id                       as group_id,
    u.firstname                as ns_firstname,
    u.lastname                 as ns_lastname,
    o.external_id              as offer_external_id,
    o.ref_no                   as offer_ref_no,
    o.work_description         as offer_work_description,
    o.work_type                as offer_work_type,
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
    e.weekly_hours             as employer_weekly_hours,
    e.daily_hours              as employer_daily_hours,
    e.modified                 as employer_modified,
    e.created                  as employer_created,
    g.external_id              as group_external_id,
    g.parent_id                as group_parent_id,
    g.grouptype_id             as group_grouptype,
    g.groupName                as group_groupname,
    g.status                   as group_status,
    g.modified                 as group_modified,
    g.created                  as group_created,
    a.external_id              as address_external_id,
    a.street1                  as address_street1,
    a.street2                  as address_street2,
    a.zip                      as address_zip,
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
    and g.id = o.group_id
    and a.id = e.address_id
    and c.id = a.country_id
    and u.id = u2g.user_id
    and o.group_id = u2g.group_id
    and u2g.role_id = 1;


-- =============================================================================
-- The Student View, which embeds Group, User, Roles & Countries
-- =============================================================================
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
    g.grouptype_id             as group_grouptype,
    g.parent_id                as group_parent_id,
    g.groupName                as group_groupname,
    g.status                   as group_status,
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
    countries c,
    grouptypes gt,
    roles r
  where u.id = s.user_id
    and u.id = ug.user_id
    and g.id = ug.group_id
    and c.id = g.country_id
    and r.id = ug.role_id
    and g.grouptype_id = gt.id
    and gt.grouptype = 'STUDENT';
