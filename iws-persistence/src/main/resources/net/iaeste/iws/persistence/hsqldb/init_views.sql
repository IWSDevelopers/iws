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
    g.groupname          as groupname,
    g.group_description  as group_description,
    g.country_id         as country,
    t.grouptype          as grouptype,
    u2g.custom_title     as rolename,
    p.permission         as permission
  from
    users u,
    groups g,
    grouptypes t,
    roles r,
    permissions p,
    user_to_group u2g,
    permission_to_role p2r,
    permission_to_grouptype p2t
  where t.id = g.grouptype_id
    and u.id = u2g.user_id
    and g.id = u2g.group_id
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
