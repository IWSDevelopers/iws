-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.2 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the second DB version, and the IWS
-- version it is related to.
insert into versions (db_version, iws_version) values (4, '1.1.6');

-- =============================================================================
-- Corrections for Trac Tasks
-- =============================================================================
create sequence folder_sequence start with 10 increment by 1 no cycle;
create table folders (
    id               integer default nextval('file_sequence'),
    external_id      varchar(36),
    parent_id        integer,
    group_id         integer,
    foldername       varchar(100),
    modified         timestamp   default now(),
    created          timestamp   default now(),

    /* Primary & Foreign Keys */
    constraint folder_pk             primary key (id),
    constraint folder_fk_group_id    foreign key (group_id) references groups (id) on delete restrict on update cascade,

    /* Unique Constraints */
    constraint folder_unique_external_id unique (external_id),

    /* Not Null Constraints */
    constraint folder_notnull_id           check (id is not null),
    constraint folder_notnull_external_id  check (external_id is not null),
    constraint folder_notnull_parent_id    check (parent_id = 1 or parent_id < id),
    constraint folder_notnull_group_id     check (group_id is not null),
    constraint folder_notnull_filename     check (foldername is not null),
    constraint folder_notnull_modified     check (modified is not null),
    constraint folder_notnull_created      check (created is not null)
);

alter table files add column privacy varchar(10) default 'PRIVATE';
alter table files add column folder_id integer;
alter table files add constraint file_fk_folder_id foreign key (folder_id) references folders (id);
alter table files add constraint file_notnull_privacy check (privacy is not null);

insert into permissions (id, permission) values (310, 'PROCESS_FOLDER');
insert into permissions (id, permission) values (311, 'PROCESS_FOLDER');

-- Permission: 310 - Process Folder
--   -> GroupTypes: All
--   -> Roles:      1 Owner
--                  2 Moderator
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 310);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 310);
insert into permission_to_role (role_id, permission_id) values (1, 310);
insert into permission_to_role (role_id, permission_id) values (2, 310);

-- Permission: 311 - Fetch Folder
--   -> GroupTypes: All
--   -> Roles:      1 Owner
--                  2 Moderator
--                  3 Member
insert into permission_to_grouptype (grouptype_id, permission_id) values (0, 311);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 311);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 311);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 311);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 311);
insert into permission_to_grouptype (grouptype_id, permission_id) values (5, 311);
insert into permission_to_grouptype (grouptype_id, permission_id) values (6, 311);
insert into permission_to_grouptype (grouptype_id, permission_id) values (7, 311);
insert into permission_to_role (role_id, permission_id) values (1, 311);
insert into permission_to_role (role_id, permission_id) values (2, 311);
insert into permission_to_role (role_id, permission_id) values (3, 311);
