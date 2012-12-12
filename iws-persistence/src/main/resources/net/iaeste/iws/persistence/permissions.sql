-- First, let's drop any existing tables & views, to avoid errors
drop view if exists group_permissions;
drop view if exists user_permissions;
drop table if exists role_to_group;
drop table if exists user_to_group;
drop table if exists permission_to_role;
drop table if exists permission_to_group_type;
drop table if exists users;
drop table if exists roles;
drop table if exists groups;
drop table if exists group_types;
drop table if exists permissions;

-- Standard tables for users, groups, roles & permissions
create table users (
  id               INTEGER PRIMARY KEY,
  user_name        VARCHAR(50)
);

create table group_types (
  id               INTEGER PRIMARY KEY,
  group_type       VARCHAR(50)
);

create table groups (
  id               INTEGER PRIMARY KEY,
  group_name       VARCHAR(50),
  group_type_id_fk INTEGER REFERENCES group_types (id)
);

drop table if exists permissions;
create table permissions (
  id               INTEGER PRIMARY KEY,
  permission       VARCHAR(50)
);

create table roles (
  id               INTEGER PRIMARY KEY,
  role             VARCHAR(50)
);

-- Relation tables for the permission model
create table permission_to_group_type (
  permission_id_fk INTEGER REFERENCES permissions (id),
  group_type_id_fk INTEGER REFERENCES roles (id),
  UNIQUE (permission_id_fk, group_type_id_fk)
);

create table permission_to_role (
  permission_id_fk INTEGER REFERENCES permissions (id),
  role_id_fk       INTEGER REFERENCES roles (id),
  UNIQUE (permission_id_fk, role_id_fk)
);

create table role_to_group (
  role_id_fk       INTEGER REFERENCES roles (id),
  group_id_fk      INTEGER REFERENCES groups (id)
);

create table user_to_group (
  user_id_fk       INTEGER REFERENCES users (id),
  group_id_fk      INTEGER REFERENCES groups (id),
  role_id_fk       INTEGER REFERENCES roles (id),
  PRIMARY KEY (user_id_fk, group_id_fk)
);

-- Views, to simplify the lookups
create view user_permissions as
  select
    u.id         as uid,
    g.id         as gid,
    t.id         as tid,
    r.id         as rid,
    p.id         as pid,
    u.user_name  as username,
    g.group_name as groupName,
    t.group_type as grouptype,
    r.role       as rolename,
    p.permission as permission
  from
    users u,
    groups g,
    group_types t,
    roles r,
    permissions p,
    user_to_group u2g,
    permission_to_role p2r,
    permission_to_group_type p2t
  where t.id = g.group_type_id_fk
    and u.id = u2g.user_id_fk
    and g.id = u2g.group_id_fk
    and r.id = u2g.role_id_fk
    and p.id = p2r.permission_id_fk
    and r.id = p2r.role_id_fk
    and p.id = p2t.permission_id_fk
    and t.id = p2t.group_type_id_fk
;

create view group_permissions as
  select
    t.id   as tid,
    p.id   as pid,
    t.group_type as grouptype,
    p.permission as permission
  from
    group_types t,
    permissions p,
    permission_to_group_type p2t
  where t.id = p2t.group_type_id_fk
    and p.id = p2t.permission_id_fk
;

-- Let's illustrate this setup, by using a construct from the Lord of the Rings

-- First, the weapons, that is permitted to be used
insert into permissions (id, permission) values (1, 'Cast Spells');
insert into permissions (id, permission) values (2, 'Bow & Arrow');
insert into permissions (id, permission) values (3, 'Axe');
insert into permissions (id, permission) values (4, 'Swords');
insert into permissions (id, permission) values (5, 'Daggers');
insert into permissions (id, permission) values (6, 'Horse Riding');
insert into permissions (id, permission) values (7, 'Spears');
-- Second, the different GroupTypes, that we have
insert into group_types (id, group_type) values (1, 'Half Size');
insert into group_types (id, group_type) values (2, 'Full Size');
insert into group_types (id, group_type) values (3, 'Wizardry');
insert into group_types (id, group_type) values (4, 'Fellowship');
-- Third, the different Roles in the system
insert into roles (id, role) values (1, 'Traveller');
insert into roles (id, role) values (2, 'Horseman');
insert into roles (id, role) values (3, 'Wizard');
insert into roles (id, role) values (4, 'Dwarfs');
insert into roles (id, role) values (5, 'Elves');
insert into roles (id, role) values (6, 'Hobbits');

-- Now, that have the primary elements for the permission, let's assign the
-- roles & group_types some permissions
-- First, the permissions for each grouptype
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (1, 3);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (1, 5);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (2, 1);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (2, 2);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (2, 4);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (2, 6);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (2, 7);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (3, 1);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (3, 6);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (4, 1);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (4, 2);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (4, 3);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (4, 4);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (4, 5);
insert into permission_to_group_type (group_type_id_fk, permission_id_fk) values (4, 6);
-- Second, the permissions to each role
insert into permission_to_role (role_id_fk, permission_id_fk) values (1, 2);
insert into permission_to_role (role_id_fk, permission_id_fk) values (1, 4);
insert into permission_to_role (role_id_fk, permission_id_fk) values (1, 6);
insert into permission_to_role (role_id_fk, permission_id_fk) values (2, 6);
insert into permission_to_role (role_id_fk, permission_id_fk) values (2, 7);
insert into permission_to_role (role_id_fk, permission_id_fk) values (3, 1);
insert into permission_to_role (role_id_fk, permission_id_fk) values (3, 4);
insert into permission_to_role (role_id_fk, permission_id_fk) values (3, 6);
insert into permission_to_role (role_id_fk, permission_id_fk) values (4, 3);
insert into permission_to_role (role_id_fk, permission_id_fk) values (5, 2);
insert into permission_to_role (role_id_fk, permission_id_fk) values (5, 4);
insert into permission_to_role (role_id_fk, permission_id_fk) values (5, 6);
insert into permission_to_role (role_id_fk, permission_id_fk) values (6, 5);

-- With this rather simple setup, we can now start creating different groups
insert into groups (id, group_type_id_fk, group_name) values (1, 1, 'Dwarfs');
insert into groups (id, group_type_id_fk, group_name) values (2, 1, 'Hobbits');
insert into groups (id, group_type_id_fk, group_name) values (3, 2, 'Elves');
insert into groups (id, group_type_id_fk, group_name) values (4, 2, 'Men');
insert into groups (id, group_type_id_fk, group_name) values (5, 3, 'Wizards');
insert into groups (id, group_type_id_fk, group_name) values (6, 4, 'Fellowship');
-- Now, let's add some users
insert into users (id, user_name) values ( 1, 'Frodo');
insert into users (id, user_name) values ( 2, 'Sam');
insert into users (id, user_name) values ( 3, 'Merry');
insert into users (id, user_name) values ( 4, 'Pippin');
insert into users (id, user_name) values ( 5, 'Gandalf');
insert into users (id, user_name) values ( 6, 'Aragorn');
insert into users (id, user_name) values ( 7, 'Boromir');
insert into users (id, user_name) values ( 8, 'Gimli');
insert into users (id, user_name) values ( 9, 'Legolas');
insert into users (id, user_name) values (10, 'Elrond');
insert into users (id, user_name) values (11, 'Saruman');
insert into users (id, user_name) values (12, 'Eomer');

-- With the list of Users, Groups & Role sin place, let's put them together :-)
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 8, 1, 4);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 1, 2, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 2, 2, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 3, 2, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 4, 2, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 9, 3, 1);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values (10, 3, 3);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 5, 4, 3);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 6, 4, 1);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 7, 4, 1);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values (11, 4, 3);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values (12, 4, 2);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 5, 5, 3);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values (11, 5, 3);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 1, 6, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 2, 6, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 3, 6, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 4, 6, 6);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 5, 6, 3);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 6, 6, 1);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 7, 6, 1);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 8, 6, 4);
insert into user_to_group (user_id_fk, group_id_fk, role_id_fk) values ( 9, 6, 5);
