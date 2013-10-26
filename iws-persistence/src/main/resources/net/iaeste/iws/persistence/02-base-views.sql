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
    r.id                 as rid,
    r.external_id        as erid,
    t.id                 as tid,
    u2g.external_id      as eugid,
    p.id                 as pid,
    u.username           as username,
    g.groupName          as groupName,
    g.group_description  as group_description,
    c.country_code       as country,
    t.grouptype          as grouptype,
    u2g.custom_title     as title,
    r.role               as role,
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
    c.id                   as country_id,
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
    c.created              as country_created,
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

-- =============================================================================
-- List the Notification job tasks with their Notfication type
-- and Notifiable object
-- Note; only unprocessed job tasks are included in the view
-- =============================================================================
create view notification_job_task_details as
  select
    njt.id                 as id,
    njt.attempts           as attempts,
    nj.notification_type   as notification_type,
    nj.object              as object,
    njt.consumer_id        as consumer_id
  from
    notification_job_tasks njt
    left join notification_jobs nj  on njt.job_id = nj.id
  where njt.processed = false;
