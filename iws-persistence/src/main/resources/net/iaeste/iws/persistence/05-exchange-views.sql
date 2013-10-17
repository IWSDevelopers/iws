-- =============================================================================
-- Please add all Exchange related views here
-- =============================================================================

-- =============================================================================
-- The Employer View, which embeds Address & Group
-- =============================================================================
create view employer_view as
  select
    e.id                       as id,
    e.external_id              as external_id,
    e.name                     as name,
    e.department               as department,
    e.business                 as business,
    e.number_of_employees      as number_of_employees,
    e.website                  as website,
    e.working_place            as working_place,
    e.canteen                  as canteen,
    e.nearest_airport          as nearest_airport,
    e.nearest_public_transport as nearest_public_transport,
    e.weekly_hours             as weekly_hours,
    e.daily_hours              as daily_hours,
    g.id                       as group_id,
    g.external_id              as group_external_id,
    g.grouptype_id             as grouptype,
    g.groupName                as groupname,
    a.id                       as address_id,
    a.external_id              as address_external_id,
    a.street1                  as street1,
    a.street2                  as street2,
    a.zip                      as zip,
    a.city                     as city,
    a.state                    as state,
    e.modified                 as modified,
    e.created                  as created
  from
    employers e,
    addresses a,
    groups g
  where e.group_id = g.id
    and e.address_id = a.id;

-- =============================================================================
-- The Employer View, which embeds Address & Group
-- =============================================================================
create view offer_view as
  select
    o.id                       as offer_id,
    o.external_id              as offer_external_id,
    o.ref_no                   as ref_no,
    o.work_description         as work_description,
    o.work_type                as work_type,
    o.study_levels             as study_levels,
    o.study_fields             as study_fields,
    o.specializations          as specializations,
    o.prev_training_req        as prev_training_req,
    o.other_requirements       as other_requirements,
    o.min_weeks                as min_weeks,
    o.max_weeks                as max_weeks,
    o.from_date                as from_date,
    o.to_date                  as to_date,
    o.from_date_2              as from_date_2,
    o.to_date_2                as to_date_2,
    o.unavailable_from         as unavailable_from,
    o.unavailable_to           as unavailable_to,
    o.language_1               as language_1,
    o.language_1_level         as language_1_level,
    o.language_1_op            as language_1_op,
    o.language_2               as language_2,
    o.language_2_level         as language_2_level,
    o.language_2_op            as language_2_op,
    o.language_3               as language_3,
    o.language_3_level         as language_3_level,
    o.payment                  as payment,
    o.payment_frequency        as payment_frequency,
    o.currency                 as currency,
    o.deduction                as deduction,
    o.living_cost              as living_cost,
    o.living_cost_frequency    as living_cost_frequency,
    o.lodging_by               as lodging_by,
    o.lodging_cost             as lodging_cost,
    o.lodging_cost_frequency   as lodging_cost_frequency,
    o.nomination_deadline      as nomination_deadline,
    o.number_of_hard_copies    as number_of_hard_copies,
    o.additional_information   as additional_information,
    o.status                   as status,
    e.id                       as employer_id,
    e.external_id              as employer_external_eid,
    e.name                     as employer_name,
    e.department               as department,
    e.business                 as business,
    e.number_of_employees      as number_of_employees,
    e.website                  as website,
    e.working_place            as working_place,
    e.canteen                  as canteen,
    e.nearest_airport          as nearest_airport,
    e.nearest_public_transport as nearest_public_transport,
    e.weekly_hours             as weekly_hours,
    e.daily_hours              as daily_hours,
    g.id                       as group_id,
    g.external_id              as group_external_id,
    g.grouptype_id             as grouptype,
    g.groupName                as groupname,
    a.id                       as address_id,
    a.external_id              as address_external_id,
    a.street1                  as street1,
    a.street2                  as street2,
    a.zip                      as zip,
    a.city                     as city,
    a.state                    as state,
    c.country_name             as country_name,
    o.modified                 as offer_modified,
    o.created                  as offer_created
  from
    offers o,
    groups g,
    employers e,
    addresses a,
    countries c
  where e.id = o.employer_id
    and g.id = o.group_id
    and a.id = e.address_id
    and c.id = a.country_id;
