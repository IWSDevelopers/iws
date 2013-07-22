-- =============================================================================
-- Please add all Exchange related views here
-- =============================================================================

-- =============================================================================
-- The Employer View, which embeds Address & Group
-- =============================================================================
create view employer_view as
  select
    e.id                    as id,
    e.external_id           as external_id,
    e.name                  as name,
    e.department            as department,
    e.business              as business,
    e.number_of_employees   as number_of_employees,
    e.website               as website,
    e.working_place         as working_place,
    e.canteen               as cantee,
    e.nearest_airport       as nearest_airport,
    e.nearest_pub_transport as nearest_pub_transport,
    e.weekly_hours          as weekly_hours,
    e.daily_hours           as daily_hours,
    g.id                    as group_id,
    g.external_id           as group_external_id,
    g.grouptype_id          as grouptype,
    g.groupName             as groupname,
    a.id                    as address_id,
    a.external_id           as address_external_id,
    a.street1               as street1,
    a.street2               as street2,
    a.zip                   as zip,
    a.city                  as city,
    a.region                as region,
    e.modified              as modified,
    e.created               as created
  from
    employers e,
    addresses a,
    groups g
  where e.group_id = g.id
    and e.address_id = a.id;
