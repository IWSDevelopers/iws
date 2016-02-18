-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.17 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the next DB version, and the IWS
-- version it is related to.
-- Commented out, as the current updates will not change the data model, only
-- correct some problems discovered with the Views & Data Content
insert into versions (db_version, iws_version) values (8, '1.1.17');

-- The following Query will update all currently suspended users, once IWS Release 1.1.17 is going live. Release 1.1.17 contains a fix, which will prevent owners from Member, National or International Groups to be suspended.
update users set
  status = 'ACTIVE',
  modified = now()
where id in (
    select distinct u.id
    from users u
      join user_to_group u2g on u2g.user_id = u.id
      join groups g on g.id = u2g.group_id
    where u2g.role_id = 1
      and u.status <> 'ACTIVE'
      and g.grouptype_id in (2,3,4)
      and g.status = 'ACTIVE'
    order by u.id asc);

-- The change to the GroupTypes means that the enum values must be updated!
update grouptypes set who_may_join = upper(who_may_join), folder_type = upper(folder_type);
