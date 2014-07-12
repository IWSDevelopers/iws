-- Accounts where the status has been suspended for 2+ years. Query finds 87
select id, modified::date, created::date, username from users where status = 'SUSPENDED' and modified < now() - interval '2 years' order by modified desc;

-- Finding users who have no membership of a Members group. Query finds 127
select
  u.id,
  u.username,
  u.firstname,
  u.lastname,
  c.country_name,
  u.status,
  u.created::date,
  u.modified::date
from
  users u,
  persons p,
  countries c
where u.id = p.id
  and c.id = p.nationality
  and u.status <> 'DELETED'
  and u.id not in (select user_id from view_user_group where grouptype = 'MEMBER');

select
  u.id,
  u.username,
  u.firstname,
  u.lastname,
  c.country_name,
  u.status,
  u.created::date,
  u.modified::date
from
  users u,
  persons p,
  countries c
where u.id = p.id
  and c.id = p.nationality
  and u.status = 'ACTIVE'
  and u.id not in (select user_id from view_user_group where grouptype = 'MEMBER');

-- Accounts that has been in status NEW for 6+ months. Query finds over 200
create view find_inactive_members as
  select
    u.id as user_id,
    u.created::date as created,
    u.username as username,
    g.group_name as country
  from
    users u
    left join user_to_group u2g on u.id = u2g.user_id
    left join groups g on g.id = u2g.group_id
  where u.status = 'NEW'
    and g.grouptype_id = 2
    and u.created < now() - interval '6 months'
  order by u.created desc;

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
-- List User Information
-- =============================================================================
create view view_users as
  select
    u.id                                                                              as id,
    u.external_id                                                                     as external_id,
    u.firstname                                                                       as firstname,
    u.lastname                                                                        as lastname,
    u.username                                                                        as username,
    c.nationality                                                                     as nationality,
    case when u.salt in ('undefined', 'heartbleed') then u.salt else 'defined' end    as password,
    u.created::date                                                                   as created,
    case when max(s.created) is null then 'never' else max(s.created)::date::text end as last_login
  from
    countries c,
    persons p,
    users u
    left join sessions s on u.id = s.user_id
  where p.id = u.person_id
    and c.id = p.nationality
  group by
    u.id,
    u.external_id,
    u.firstname,
    u.lastname,
    u.username,
    c.nationality,
    u.salt,
    u.created;
