-- =============================================================================
-- This script contain the basic tables for the IWS
-- -----------------------------------------------------------------------------
-- Revision History:
-- 0.01 June 4, 2013 - Kim Jensen <kim@dawn.dk>
--   - Initial revision, based on the hsqldb
-- -----------------------------------------------------------------------------
-- The script uses PostgreSQL 9+ specific commands, it will not work with
-- previous versions of PostgreSQL, nor with any other database system.
--   To install, please do the following:
-- $ psql <DATABASE_NAME> < core.sql
-- -----------------------------------------------------------------------------
-- To view the comments, please invoke the enhanced descriptor command: \d+
-- -----------------------------------------------------------------------------
-- Country Model:
--     - countries
-- Permission Model:
--     - Permissions
--     - GroupTypes
--     - Roles
--     - Permission 2 GroupTypes
-- Access Model:
--     - Users
--     - Groups
--     - User 2 GroupTypes
-- =============================================================================


-- =============================================================================
-- Drop & Create the public schema for clean up. Although the user requires
-- super-user rights perform, we do no care - since the production database
-- will be instantiated via a super-user, and for development, the developer is
-- expected to be superuser.
-- =============================================================================
drop schema if exists public cascade;
create schema public;


-- =============================================================================
-- Countries
-- -----------------------------------------------------------------------------
-- Fields in this table:
--     - country_id, the common 2 letter abbreviation (i.e. DK for Denmark)
--     - country_name, the name of the country (i.e. Denmark)
--     - country_full, the full name of the country (i.e, The Kingdom of Denmark)
--     - country_native, the native name of the country (i.e. Danmark)
--     - nationality, the nationality of the citizens (i.e. Danish)
--     - citizens, the nationality of the citizens (i.e. Danes)
--     - phonecode, the phone code (i.e. +45 for Denmark)
--     - currency, the 3 letter currency (EUR, USD, GBP, etc.)
--     - languages, comma-separated list of official languages
--     - membership_year, the year the country first joined IAESTE
--     - membership_status, see below
--           1 Full Member
--           2 Associative Member
--           3 Cooperating Institution(s)
--           4 Former Member
--           5 Listed Country (countries where trainees have come from)
--           6 Unlisted Countries (countries which have had no contact with IAESTE)
--           7 Regions (parts of a country with an independent country_id)
-- =============================================================================
create sequence country_sequence start with 1 increment by 1;
create table countries (
    id                  integer default nextval('country_sequence'),
    country_id          char(2),
    country_name        varchar(100),
    country_name_full   varchar(100),
    country_name_native varchar(100),
    nationality         varchar(100),
    citizens            varchar(100),
    phonecode           varchar(5),
    currency            char(3),
    languages           varchar(100),
    membership          varchar(25) default 'LISTED',
    member_since        integer default -1,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint country_pk primary key (id),

    /* Unique Constraints */
    constraint country_unique_country_id   unique (country_id),
    constraint country_unique_country_name unique (country_name),

    /* Not Null Constraints */
    constraint country_notnull_id           check (id is not null),
    constraint country_notnull_country_id   check (country_id is not null),
    constraint country_notnull_country_name check (country_name is not null),
    constraint country_notnull_modified     check (modified is not null),
    constraint country_notnull_created      check (created is not null)
);


-- =============================================================================
-- Permissions or IWS Functionality
-- -----------------------------------------------------------------------------
-- All functionality in the IWS is mapped to the permission table. And users
-- must have access to a Permission, to be allowed to perform the associated
-- action.
--   Some Permissions are restricted, meaning that they serve a special purpose,
-- and cannot be granted to customized roles.
-- =============================================================================
create table permissions (
    id                  integer,
    permission          varchar(50),
    restricted          boolean default false,
    description         varchar(2048),

    /* Primary & Foreign Keys */
    constraint permission_pk primary key (id),

    /* Unique Constraints */
    constraint permission_unique_country_id unique (permission),

    /* Not Null Constraints */
    constraint permission_notnull_id         check (id is not null),
    constraint permission_notnull_permission check (permission is not null),
    constraint permission_notnull_restricted check (restricted is not null)
);


-- =============================================================================
-- GroupTypes, see net.iaeste.iws.api.enums.GroupType
-- -----------------------------------------------------------------------------
-- GroupTypes or MetaGroups, contains the common information for certain types
-- of Groups, the Common information is the allowed Permissions or
-- functionionality. All Groups in the IW has to be assigned an overall type.
--   Please note, that certain GroupType, are designed so any given user may
-- only be member of 1 (one), others are open, so users can be part of many.
-- The restricted groups are: Administration, Members, National and Sar - In
-- fact, a user can only be member of either 1 National or 1 SAR.
-- =============================================================================
create table grouptypes (
    id                  integer,
    grouptype           varchar(50),
    description         varchar(2048),

    /* Primary & Foreign Keys */
    constraint grouptype_pk primary key (id),

    /* Unique Constraints */
    constraint grouptype_unique_grouptype unique (grouptype),

    /* Not Null Constraints */
    constraint grouptype_notnull_id        check (id is not null),
    constraint grouptype_notnull_grouptype check (grouptype is not null)
);


-- =============================================================================
-- Groups
-- -----------------------------------------------------------------------------
-- All linking between functionality, data and access is going via the Groups
-- which provide the "glue". A Group is simply a collection of people, it
-- consists of a type that links the functionality in. Since Groups are so
-- fundamental for all purposes, the actual Id is never communicated out,
-- instead has an External Id assigned, that is a standard UUID value.
--   The Group hiearchy starts with a Members Group, who all have the
-- Administration Group as their parent. The Administrator Group is purely
-- there for system purposes, and can only perform the operations associated
-- with setting up the system.
--   All users will have one implicit (though physically created) Private
-- Group, which is used to link in data. This way, the Business Logic can be
-- simplified significantly, since no distinctions has to be made when looking
-- up data.
--   All Groups also have 2 Mailinglists, a public and a private. The table
-- doesn't hold both names, only the first part (field: list_name), that is
-- then expanded when creating or updating the mailinglists, so the
-- "@iaeste.org" (public) or "@iaeste.net" (private) is appended. The only
-- groups without a mailinglist, is the Private groups, since users have a
-- public e-mail alias assigned.
--   The Group also has a Status, that determines if this Group may be accessed
-- or not. In the case of the Members GroupType, users who belong to a
-- non-active Members group (you may only belong to 1 Members Group), are not
-- allowed to access the system.
--   All member groups (with the exception of the Global Members group) must
-- have a Country Id assigned.
-- =============================================================================
create sequence group_sequence start with 50 increment by 1;
create table groups (
    id                  integer default nextval('group_sequence'),
    external_id         varchar(36),
    parent_id           integer,
    grouptype_id        integer,
    groupName           varchar(50),
    full_name           varchar(100),
    group_description   varchar(250),
    country_id          integer,
    list_name           varchar(75),
    status              varchar(10) default 'ACTIVE',
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint group_pk              primary key (id),
    constraint group_fk_grouptype_id foreign key (grouptype_id) references grouptypes (id),
    constraint group_fk_country_id   foreign key (country_id)   references countries (id),

    /* Unique Constraints */
    constraint group_unique_external_id unique (external_id),

    /* Not Null Constraints */
    constraint group_notnull_id           check (id is not null),
    constraint group_notnull_external_id  check (external_id is not null),
    constraint group_notnull_grouptype_id check (grouptype_id is not null),
    constraint group_notnull_status       check (status is not null),
    constraint group_notnull_modified     check (modified is not null),
    constraint group_notnull_created      check (created is not null)
);


-- =============================================================================
-- Roles
-- -----------------------------------------------------------------------------
-- A User is asscociated to a Group with a Role, the Role serves as a "hat" for
-- the User in the context of the Group. The role is only needed to hold the
-- collection of permissions, that a User may perform on the data belonging to
-- the Group.
--   By default, 5 roles exists, these roles cannot be altered in any way, and
-- they are there to act as default roles, that will contain the most commonly
-- needed purposes. It is possible to also create customized roles, that will be
-- directed at a specific Group. A customized role can only contain a subset of
-- the Permissions that exists, some permissions are restricted, since they only
-- serve very specific Administrative needs.
--   Note, that the Role itself (the name) is not unique, since it is allowed
-- for Groups to create new Roles, and two Groups should be allowed to have the
-- same name for their Role.
--   Note, Custom Roles are linked to either a specific Country, and will thus
-- be available to all subgroups of that Country. Or it can be linked to a
-- specific Group, meaning that the Custom role will only be available to that
-- Group. If both Country & Group is set, then it can be used by either part.
-- =============================================================================
create sequence role_sequence start with 10 increment by 1;
create table roles (
    id                  integer default nextval('role_sequence'),
    role                varchar(50),
    country_id          integer default null,
    group_id            integer default null,
    description         varchar(2048),
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint role_pk            primary key (id),
    constraint role_fk_group_id   foreign key (group_id)   references groups (id),
    constraint role_fk_country_id foreign key (country_id) references countries (id),

    /* Unique Constraints */
    constraint group_unique_ids unique (role, country_id, group_id),

    /* Not Null Constraints */
    constraint role_notnull_id       check (id is not null),
    constraint role_notnull_role     check (role is not null),
    constraint role_notnull_modified check (modified is not null),
    constraint role_notnull_created  check (created is not null)
);


-- =============================================================================
-- Permission to GroupType Associations
-- -----------------------------------------------------------------------------
-- This table contains the actual mapping of basic Permissions to the individual
-- GroupTypes.
-- The different GroupTypes are there, since they have different purposes or
-- functions. The most fundamental part of the Permission model, is that a
-- permission must be granted to a Group, before a member of that group can
-- perform it. However, to avoid having to map the permissions to each Group,
-- they are instead mapped to the GroupType, which acts as a Template for the
-- Group.
-- =============================================================================
create table permission_to_grouptype (
    permission_id       integer,
    grouptype_id        integer,

    /* Primary & Foreign Keys */
    constraint p2gt_pk               primary key (permission_id, grouptype_id),
    constraint p2gt_fk_permission_id foreign key (permission_id) references permissions (id),
    constraint p2gt_fk_grouptype_id  foreign key (grouptype_id)  references grouptypes (id),

    /* Not Null Constraints */
    constraint p2gt_notnull_permission_id check (permission_id is not null),
    constraint p2gt_notnull_grouptype_id  check (grouptype_id is not null)
);


-- =============================================================================
-- Permission to Role Associations
-- -----------------------------------------------------------------------------
-- The GroupTypes define the all the Permissions that users may invoke, on the
-- data belonging to a specific Group. However, to better control which
-- functionality a user belonging to a Group may invoke, the Role is used to
-- give a different set of Permissions. To determine if a User may invoke a
-- certain Permission on the Group data, the joined part of the two Permission
-- sets is compared, and only if a Permission is granted for both Group & Role,
-- is the User allowed to perform the action.
--   Custom Roles can pick from all available non-restricted Permissions, and be
-- reused throughout the Groups belonging to the same Country.
-- =============================================================================
create sequence permission_to_role_sequence start with 10 increment by 1;
create table permission_to_role (
    id                  integer default nextval('permission_to_role_sequence'),
    permission_id       integer,
    role_id             integer,

    /* Primary & Foreign Keys */
    constraint p2r_pk               primary key (id),
    constraint p2r_fk_permission_id foreign key (permission_id) references permissions (id),
    constraint p2r_fk_role_id       foreign key (role_id)       references roles (id),

    /* Unique Constraints */
    constraint p2r_unique_ids unique (permission_id, role_id),

    /* Not Null Constraints */
    constraint p2r_notnull_id            check (id is not null),
    constraint p2r_notnull_permission_id check (permission_id is not null),
    constraint p2r_notnull_role_id       check (role_id is not null)
);


-- =============================================================================
-- Users
-- -----------------------------------------------------------------------------
-- Everybody who may access the System is assigned a User account, this account
-- (which was called Profile in IW3), holds the system relevant information,
-- i.e. no personal information, with the exception of the users first and last
-- names.
-- =============================================================================
create sequence user_sequence start with 1 increment by 1;
create table users (
    id                  integer default nextval('user_sequence'),
    external_id         varchar(36),
    username            varchar(100),
    alias               varchar(125),
    password            varchar(128),
    salt                varchar(36),
    firstname           varchar(50),
    lastname            varchar(50),
    status              varchar(25) default 'NEW',
    private_data        varchar(10) default 'PRIVATE',
    notifications       varchar(25) default 'immediately',
    temporary_code      varchar(128),
    modified            timestamp   default now(),
    created             timestamp   default now(),

    /* Primary & Foreign Keys */
    constraint user_pk primary key (id),

    /* Unique Constraints */
    constraint user_unique_external_id unique (external_id),
    constraint user_unique_username    unique (username),
    constraint user_unique_alias       unique (alias),
    constraint user_unique_code        unique (temporary_code),

    /* Not Null Constraints */
    constraint user_notnull_id            check (id is not null),
    constraint user_notnull_external_id   check (external_id is not null),
    constraint user_notnull_username      check (username is not null),
    constraint user_notnull_alias         check (alias is not null),
    constraint user_notnull_password      check (password is not null),
    constraint user_notnull_salt          check (salt is not null),
    constraint user_notnull_firstname     check (firstname is not null),
    constraint user_notnull_lastname      check (lastname is not null),
    constraint user_notnull_status        check (status is not null),
    constraint user_notnull_private_data  check (private_data is not null),
    constraint user_notnull_notifications check (notifications is not null),
    constraint user_notnull_modified      check (modified is not null),
    constraint user_notnull_created       check (created is not null)
);


-- =============================================================================
-- User Sessions
-- -----------------------------------------------------------------------------
-- All actions against the IWS, must be made with an active Session. The Session
-- is closely linked to a person, and only a single active session can exists at
-- the time.
--   The SessionKey is generated with a cryptographical checksum, that contains
-- both some unique entropy, and some user information. The created timestamp is
-- set when the user is logging in, and the modified is set everytime the user
-- makes a request.
-- =============================================================================
create sequence session_sequence start with 1 increment by 1;
create table sessions (
    id                  integer default nextval('session_sequence'),
    session_key         varchar(128),
    user_id             integer,
    active              boolean default true,
    session_data        bytea,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint session_pk         primary key (id),
    constraint session_fk_user_id foreign key (user_id) references users (id),

    /* Unique Constraints */
    constraint session_unique_session_key unique (session_key),

    /* Not Null Constraints */
    constraint session_notnull_id          check (id is not null),
    constraint session_notnull_session_key check (session_key is not null),
    constraint session_notnull_user_id     check (user_id is not null),
    constraint session_notnull_active      check (active is not null),
    constraint session_notnull_modified    check (modified is not null),
    constraint session_notnull_created     check (created is not null)
);


-- =============================================================================
-- User to Group Associations
-- -----------------------------------------------------------------------------
-- Although this is just a "relation" table - certain additional information is
-- crammed into it, since a users association with a group also include
-- information about how the user may access data, and how the system should
-- deal with information sent to the mailinglists of the Group.
--   Further, a user can remain on the list, but with the status  "Suspended",
-- meaning that the user cannot access anything, but the status can be restored
-- together with all other settings, if so desired.
-- =============================================================================
create sequence user_to_group_sequence start with 1 increment by 1;
create table user_to_group (
    id                  integer default nextval('user_to_group_sequence'),
    user_id             integer,
    group_id            integer,
    role_id             integer,
    custom_title        varchar(50),
    on_public_list      boolean default false,
    on_private_list     boolean default true,
    status              boolean default true,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint u2g_pk          primary key (id),
    constraint u2g_fk_user_id  foreign key (user_id)  references users (id),
    constraint u2g_fk_group_id foreign key (group_id) references groups (id),
    constraint u2g_fk_role_id  foreign key (role_id)  references roles (id),

    /* Unique Constraints */
    constraint u2g_unique_session_key unique (user_id, group_id),

    /* Not Null Constraints */
    constraint u2g_notnull_id              check (id is not null),
    constraint u2g_notnull_user_idd        check (user_id is not null),
    constraint u2g_notnull_group_id        check (group_id is not null),
    constraint u2g_notnull_role_id         check (role_id is not null),
    constraint u2g_notnull_on_public_list  check (on_public_list is not null),
    constraint u2g_notnull_on_private_list check (on_private_list is not null),
    constraint u2g_notnull_status          check (status is not null),
    constraint u2g_notnull_modified        check (modified is not null),
    constraint u2g_notnull_created         check (created is not null)
);


-- =============================================================================
-- Monitoring History
-- -----------------------------------------------------------------------------
-- Monitoring of changes is stored in this table. The changes include the user,
-- the name of the table + the Id of the table and the actual changes (fields),
-- that have been changed. Please note, that not all fields may be present here,
-- if at all - this is depending on the setup of the monitoring for the Entity
-- and the Group.
--   The Group is in the table, so changes can be deleted if the Group is being
-- deleted. Or if the Group desires to have all data removed due to data
-- protection / privacy reasons.
-- =============================================================================
create sequence history_sequence start with 1 increment by 1;
create table history (
    id                  integer default nextval('history_sequence'),
    user_id             integer,
    group_id            integer,
    tablename           varchar(50),
    record_id           integer,
    fields              bytea,
    changed             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint history_pk          primary key (id),
    constraint history_fk_user_id  foreign key (user_id)  references users (id),
    constraint history_fk_group_id foreign key (group_id) references groups (id),

    /* Not Null Constraints */
    constraint history_notnull_id        check (id is not null),
    constraint history_notnull_user_id   check (user_id is not null),
    constraint history_notnull_group_id  check (group_id is not null),
    constraint history_notnull_tablename check (tablename is not null),
    constraint history_notnull_record_id check (record_id is not null),
    constraint history_notnull_changed   check (changed is not null)
);


-- =============================================================================
-- Addresses
-- -----------------------------------------------------------------------------
-- All addresses that exists in the IWS, must be mapped into this Object. Which
-- contain the most common information available.
--   Although some of the fields combined could make up a unique address, we're
-- ignoring this, since it will make f.x. a divorce harder to control later on.
-- =============================================================================
create sequence address_sequence start with 1 increment by 1;
create table addresses (
    id                  integer default nextval('address_sequence'),
    street1             varchar(100) default '',
    street2             varchar(100) default '',
    zip                 varchar(100) default '',
    city                varchar(100) default '',
    region              varchar(100) default '',
    pobox               varchar(100) default '',
    country_id          integer,
    modified            timestamp    default now(),
    created             timestamp    default now(),

    /* Primary & Foreign Keys */
    constraint address_pk              primary key (id),
    constraint address_fk_countries_id foreign key (country_id) references countries (id),

    /* Not Null Constraints */
    constraint address_notnull_id         check (id is not null),
    constraint address_notnull_country_id check (country_id is not null),
    constraint address_notnull_modified   check (modified is not null),
    constraint address_notnull_created    check (created is not null)
);


-- =============================================================================
-- Persons
-- -----------------------------------------------------------------------------
-- This Table contain all personal information about a person. The discussion
-- regarding data protection and the necessity and relevance of each data field
-- has prevented this Table and the associated Objects from being completed.
--   The table should mostly contain the information from the old Users &
-- Profiles tables in IW3, but before being completed, the topics raised above
-- must be addressed.
-- =============================================================================
create sequence person_sequence start with 1 increment by 1;
create table persons (
    id               integer default nextval('person_sequence'),
    address          integer,
    email            varchar(100),
    phone            varchar(25),
    mobile           varchar(25),
    fax              varchar(25),
    modified         timestamp default now(),
    created          timestamp default now(),

    /* Primary & Foreign Keys */
    constraint persons_pk         primary key (id),
    constraint persons_fk_address foreign key (address) references addresses (id),

    /* Not Null Constraints */
    constraint persons_notnull_id       check (id is not null),
    constraint persons_notnull_modified check (modified is not null),
    constraint persons_notnull_created  check (created is not null)
);


-- =============================================================================
-- User notifications setting
-- -----------------------------------------------------------------------------
-- The notification should additionally list the group that the notification is
-- sent to. Further, the frequency is a user setting, so it should be read from
-- the user table and linked in
-- =============================================================================
create sequence user_notification_sequence start with 1 increment by 1;
create table user_notifications (
    id               integer default nextval('user_notification_sequence'),
    user_id          integer not null,
    subject          varchar(100),
    frequency        varchar(100),
    changed          timestamp default now(),

    /* Primary & Foreign Keys */
    constraint user_notifications_pk         primary key (id),
    constraint user_notifications_fk_user_id foreign key (user_id) references users (id),

    /* Not Null Constraints */
    constraint user_notifications_notnull_id      check (id is not null),
    constraint user_notifications_notnull_user_id check (user_id is not null),
    constraint user_notifications_notnull_changed check (changed is not null)
);


-- =============================================================================
-- Notification messages
-- -----------------------------------------------------------------------------
--
-- =============================================================================
create sequence notification_message_sequence start with 1 increment by 1;
create table notification_messages (
    id                 integer default nextval('notification_message_sequence'),
    user_id            integer,
    title              varchar(100),
    message            varchar(100),
    notification_type  varchar(100),
    status             varchar(100),
    process_after      timestamp default now(),
    changed            timestamp default now(),

    /* Primary & Foreign Keys */
    constraint notitication_messages_pk         primary key (id),
    constraint notitication_messages_fk_user_id foreign key (user_id) references users (id),

    /* Not Null Constraints */
    constraint notitication_messages_notnull_id                check (id is not null),
    constraint notitication_messages_notnull_user_id           check (user_id is not null),
    constraint notitication_messages_notnull_notification_type check (notification_type is not null),
    constraint notitication_messages_notnull_process_after     check (process_after is not null),
    constraint notitication_messages_notnull_changed           check (changed is not null)
);
