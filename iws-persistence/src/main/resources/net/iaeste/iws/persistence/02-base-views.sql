-- =============================================================================
-- Default Views for the Core part of IWS
-- =============================================================================


-- =============================================================================
-- List the Permissions for a User
-- =============================================================================
create view user_permissions as
  select
    u.id                 as uid,
    u.external_id        as euid,
    g.id                 as gid,
    g.external_id        as egid,
    t.id                 as tid,
    r.id                 as rid,
    p.id                 as pid,
    u.username           as username,
    g.groupName          as groupName,
    g.group_description  as group_description,
    c.country_code       as country,
    t.grouptype          as grouptype,
    u2g.custom_title     as rolename,
    p.permission         as permission
  from
    users u,
    groups g,
    grouptypes t,
    roles r,
    countries c,
    permissions p,
    user_to_group u2g,
    permission_to_role p2r,
    permission_to_grouptype p2t
  where t.id = g.grouptype_id
    and u.id = u2g.user_id
    and g.id = u2g.group_id
    and c.id = g.country_id
    and r.id = u2g.role_id
    and p.id = p2r.permission_id
    and r.id = p2r.role_id
    and p.id = p2t.permission_id
    and t.id = p2t.grouptype_id;


-- =============================================================================
-- List the Permissions for a Group
-- =============================================================================
create view group_permissions as
  select
    t.id   as tid,
    p.id   as pid,
    t.grouptype  as grouptype,
    p.permission as permission
  from
    grouptypes t,
    permissions p,
    permission_to_grouptype p2t
  where t.id = p2t.grouptype_id
    and p.id = p2t.permission_id;


-- =============================================================================
-- List the Countries with their respective Staff and National Secretary
-- Note; The GroupTypeId 5 is "National Group" or Staff
-- Note; The RoleId 1 is "Owner" or for National Groups, "National Secretary"
-- =============================================================================
create view country_details as
  select
    c.country_code         as country_code,
    c.country_name         as country_name,
    c.country_name_full    as country_name_full,
    c.country_name_native  as country_name_native,
    c.nationality          as nationality,
    c.citizens             as citizens,
    c.phonecode            as phonecode,
    c.currency             as currency,
    c.languages            as languages,
    c.membership           as membership,
    c.member_since         as member_since,
    g.list_name            as list_name,
    u.firstname            as ns_firstname,
    u.lastname             as ns_lastname
  from
    countries c
    left join groups g          on c.id = g.country_id
    left join user_to_group u2g on g.id = u2g.group_id
    left join users u           on u.id = u2g.user_id
  where g.grouptype_id = 5
    and u2g.role_id = 1;
