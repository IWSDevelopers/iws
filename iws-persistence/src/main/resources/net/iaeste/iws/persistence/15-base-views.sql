-- =============================================================================
-- Default Views for the Core part of IWS
-- =============================================================================


-- =============================================================================
-- List User Group relations
-- =============================================================================
create view view_user_group as
  select
    u.id                as user_id,
    g.id                as group_id,
    u.firstname         as firstname,
    u.lastname          as lastname,
    u.username          as username,
    u.status            as user_status,
    g.group_name        as groupname,
    gt.grouptype        as grouptype,
    r.role              as role,
    u2g.on_public_list  as public_list,
    u2g.on_private_list as private_list,
    u2g.created         as created
  from
    users u,
    groups g,
    user_to_group u2g,
    roles r,
    grouptypes gt
  where g.id = u2g.group_id
    and u.id = u2g.user_id
    and r.id = u2g.role_id
    and g.grouptype_id = gt.id;


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
    g.group_name         as groupname,
    g.full_name          as group_fullname,
    g.group_description  as group_description,
    c.country_code       as country,
    t.grouptype          as grouptype,
    u2g.custom_title     as title,
    r.role               as role,
    p.permission         as permission
  from
    users u,
    groups g
    left join countries c on c.id = g.country_id,
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
  where g.grouptype_id = 4
    and u2g.role_id = 1;


-- =============================================================================
-- View to publishingGroup file attachments to a document
-- =============================================================================
create view file_attachments as
  select
    a.id                 as attachment_id,
    a.attached_to_record as attachment_record_id,
    a.attached_to_table  as attachment_record_table,
    a.created            as attachment_created,
    f.id                 as file_id,
    f.external_id        as file_external_id,
    f.filename           as file_name,
    f.stored_filename    as file_stored_name,
    f.filesize           as file_size,
    f.mimetype           as file_mimetype,
    f.description        as file_description,
    f.keywords           as file_keywords,
    f.checksum           as file_checksum,
    f.modified           as file_modified,
    f.created            as file_created,
    g.id                 as group_id,
    g.external_id        as group_external_id,
    g.grouptype_id       as group_grouptype,
    g.parent_id          as group_parent_id,
    g.group_name         as group_groupname,
    g.list_name          as group_list_name,
    g.private_list       as group_private_list,
    g.public_list        as group_public_list,
    g.status             as group_status,
    g.monitoring_level   as group_monitoring_level,
    g.modified           as group_modified,
    g.created            as group_created,
    u.id                 as user_id,
    u.external_id        as user_external_id,
    u.username           as user_username,
    u.firstname          as user_firstname,
    u.lastname           as user_lastname,
    u.status             as user_status,
    u.modified           as user_modified,
    u.created            as user_created
  from
    attachments a,
    files f,
    groups g,
    users u
  where f.id = a.attached_file_id
    and g.id = f.group_id
    and u.id = f.user_id;


-- =============================================================================
-- View to See Files for a specific Folder
-- =============================================================================
create view view_folder_files as
  select
    f.id                 as file_id,
    f.external_id        as file_external_id,
    f.privacy            as file_privacy,
    f.folder_id          as file_folder_id,
    f.filename           as file_name,
    f.stored_filename    as file_stored_name,
    f.filesize           as file_size,
    f.mimetype           as file_mimetype,
    f.description        as file_description,
    f.keywords           as file_keywords,
    f.checksum           as file_checksum,
    f.modified           as file_modified,
    f.created            as file_created,
    g.id                 as group_id,
    g.external_id        as group_external_id,
    g.grouptype_id       as group_grouptype,
    g.parent_id          as group_parent_id,
    g.group_name         as group_groupname,
    g.list_name          as group_list_name,
    g.private_list       as group_private_list,
    g.public_list        as group_public_list,
    g.status             as group_status,
    g.monitoring_level   as group_monitoring_level,
    g.modified           as group_modified,
    g.created            as group_created,
    u.id                 as user_id,
    u.external_id        as user_external_id,
    u.username           as user_username,
    u.firstname          as user_firstname,
    u.lastname           as user_lastname,
    u.status             as user_status,
    u.modified           as user_modified,
    u.created            as user_created
  from
    attachments a,
    files f,
    groups g,
    users u
  where f.id = a.attached_file_id
    and g.id = f.group_id
    and u.id = f.user_id;


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

-- The following view is finding Groups, which is not having an Owner or having
-- too many Owners. Either way it is problematic, since a Group may *only* have
-- a single Owner.
create view problem_groups as
  with gids as (select g.id as ids
                from groups g left join user_to_group u2g on g.id = u2g.group_id
                where u2g.role_id = 1 group by g.id having count(u2g.id) <> 1)
  select
    id,
    parent_id,
    grouptype_id,
    group_name,
    full_name,
    list_name,
    private_list,
    public_list,
    status,
    modified,
    created
  from groups
  where id in (select ids from gids);


-- =============================================================================
-- The Emergency List
-- =============================================================================
create view emergency_list as
  select
    r.role           as role,
    u2g.custom_title as title,
    u.firstname      as firstname,
    u.lastname       as lasname,
    u.alias          as alias,
    u.username       as email,
    p.email          as alternative,
    c.phonecode      as precode,
    p.phone          as phone,
    p.mobile         as mobile
  from user_to_group u2g
    inner join roles r     on r.id = u2g.role_id
    inner join groups g    on g.id = u2g.group_id
    inner join countries c on c.id = g.country_id
    inner join users u     on u.id = u2g.user_id
    left  join persons p   on u.person_id = p.id
  where g.grouptype_id = 4
    and r.id <=2
  order by
    g.group_name asc,
    r.id asc,
    u.firstname asc,
    u.lastname asc;

-- =============================================================================
-- Following Views are commented out, as HyperSQL doesn't support them
-- =============================================================================

-- create view find_inactive_members as
--   select
--     u.id as user_id,
--     u.created::date as created,
--     u.username as username,
--     g.group_name as country
--   from
--     users u
--     left join user_to_group u2g on u.id = u2g.user_id
--     left join groups g on g.id = u2g.group_id
--   where u.status = 'NEW'
--     and g.grouptype_id = 2
--     and u.created < now() - interval '6 months'
--   order by u.created desc;

-- create view find_active_sessions as
--   select
--     s.id          as session_id,
--     s.session_key as session_key,
--     s.user_id     as user_id,
--     u.username    as username,
--     u.firstname   as firstname,
--     u.lastname    as lastname,
--     s.deprecated  as deprecated,
--     s.modified    as last_access,
--     s.created     as session_start
--   from
--     sessions s,
--     users u
--   where u.id = s.user_id
--     and s.deprecated = '0'
--     and s.modified > now() - interval '12 hours'
--   order by last_access desc;

-- create view find_inactive_sessions as
--   select
--     s.id          as session_id,
--     s.session_key as session_key,
--     s.user_id     as user_id,
--     u.username    as username,
--     u.firstname   as firstname,
--     u.lastname    as lastname,
--     s.deprecated  as deprecated,
--     s.modified    as last_access,
--     s.created     as session_start
--   from
--     sessions s,
--     users u
--   where u.id = s.user_id
--     and s.deprecated = '0'
--     and s.modified < now() - interval '12 hours'
--   order by last_access desc;

-- -- =============================================================================
-- -- List User Information
-- -- =============================================================================
-- create view view_users as
--   select
--     u.id                                                                              as id,
--     u.external_id                                                                     as external_id,
--     u.firstname                                                                       as firstname,
--     u.lastname                                                                        as lastname,
--     u.username                                                                        as username,
--     c.nationality                                                                     as nationality,
--     case when u.salt in ('undefined', 'heartbleed') then u.salt else 'defined' end    as password,
--     u.created::date                                                                   as created,
--     case when max(s.created) is null then 'never' else max(s.created)::date::text end as last_login
--   from
--     countries c,
--     persons p,
--     users u
--     left join sessions s on u.id = s.user_id
--   where p.id = u.person_id
--     and c.id = p.nationality
--   group by
--     u.id,
--     u.external_id,
--     u.firstname,
--     u.lastname,
--     u.username,
--     c.nationality,
--     u.salt,
--     u.created;
