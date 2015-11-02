-- =============================================================================
-- This script contain the basic tables for the IWS
-- -----------------------------------------------------------------------------
-- Revision History:
-- 1.00 September 7, 2014 - Kim Jensen <kim@dawn.dk>
--   - Various minor upgrades for IWS 1.1
--   - Moved creation to a different script, making this script safer to run
-- 0.01 June 4, 2013 - Kim Jensen <kim@dawn.dk>
--   - Initial revision, based on the hsqldb
-- -----------------------------------------------------------------------------
-- The script uses PostgreSQL 9+ specific commands, it will not work with
-- previous versions of PostgreSQL, nor with any other database system.
--   To install, please do the following:
-- $ psql -f 10-base-tables.sql <DATABASE_NAME>
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
-- File Handling:
--     - Files
-- =============================================================================


-- =============================================================================
-- Database / System Version
-- -----------------------------------------------------------------------------
-- Each feature change will requires some sort of database changes as well,
-- either changes or additions. To ensure that the version is correct for the
-- given system, we need to store a version information in the system. The
-- version of the database is independent of the IWS. But still these two needs
-- to be controlled.
--   This table simply stores the information, so an upgrade tool can be added
-- as part of the standard release or deployment procedure. Meaning, that each
-- time a system is deployed, it checks what the latest database version is, and
-- if the version is lower than what is required, it simply upgrades and sets
-- the version accordingly at the end.
-- =============================================================================
create sequence version_sequence start with 1 increment by 1;
create table versions (
    id           integer default nextval('version_sequence'),
    db_version   integer,
    iws_version  varchar(10),
    created      timestamp default now(),

    /* Primary & Foreign Keys */
    constraint version_pk primary key (id),

    /* Unique Constraints */
    constraint version_unique_db_version   unique (db_version),
    constraint version_unique_iws_version  unique (iws_version),

    /* Not Null Constraints */
    constraint version_notnull_id           check (id is not null),
    constraint version_notnull_db_version   check (db_version is not null),
    constraint version_notnull_iws_version  check (iws_version is not null),
    constraint version_notnull_created      check (created is not null)
);


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
--     - languages, comma-separated publishingGroup of official languages
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
    country_code        char(2),
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
    constraint country_unique_country_code unique (country_code),
    constraint country_unique_country_name unique (country_name),

    /* Not Null Constraints */
    constraint country_notnull_id           check (id is not null),
    constraint country_notnull_country_code check (country_code is not null),
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
-- functionality. All Groups in the IW has to be assigned an overall type.
--   Please note, that certain GroupType, are designed so any given user may
-- only be member of 1 (one), others are open, so users can be part of many.
-- The restricted groups are: Administration, Members, National and Sar - In
-- fact, a user can only be member of either 1 National or 1 SAR.
-- =============================================================================
create table grouptypes (
    id                  integer,
    grouptype           varchar(50),
    description         varchar(2048),
    who_may_join        varchar(10),
    private_list        boolean,
    public_list         boolean,
    folder_type         varchar(10),

    /* Primary & Foreign Keys */
    constraint grouptype_pk primary key (id),

    /* Unique Constraints */
    constraint grouptype_unique_grouptype unique (grouptype),

    /* Not Null Constraints */
    constraint grouptype_notnull_id            check (id is not null),
    constraint grouptype_notnull_grouptype     check (grouptype is not null),
    constraint grouptype_notnull_who_may_join  check (who_may_join is not null),
    constraint grouptype_notnull_public_list   check (public_list is not null),
    constraint grouptype_notnull_private_list  check (private_list is not null),
    constraint grouptype_notnull_folder_type   check (folder_type is not null)
);


-- =============================================================================
-- Groups
-- -----------------------------------------------------------------------------
-- All linking between functionality, data and access is going via the Groups
-- which provide the "glue". A Group is simply a collection of people, it
-- consists of a type that links the functionality in. Since Groups are so
-- fundamental for all purposes, the actual Id is never communicated out,
-- instead has an External Id assigned, that is a standard UUID value.
--   The Group hierarchy starts with a Members Group, who all have the
-- Administration Group as their parent. The Administrator Group is purely
-- there for system purposes, and can only perform the operations associated
-- with setting up the system.
--   All users will have one implicit (though physically created) Private
-- Group, which is used to link in data. This way, the Business Logic can be
-- simplified significantly, since no distinctions has to be made when looking
-- up data.
--   All Groups also have 2 Mailing lists, a public and a private. The table
-- doesn't hold both names, only the first part (field: list_name), that is
-- then expanded when creating or updating the mailing lists, so the
-- "@iaeste.org" (public) or "@iaeste.net" (private) is appended. The only
-- groups without a mailinglist, are the Private groups, since users have a
-- public e-mail alias assigned.
--   The Group also has a Status, that determines if this Group may be accessed
-- or not. In the case of the Members GroupType, users who belong to a
-- non-active Members group (you may only belong to 1 Members Group), are not
-- allowed to access the system.
--   All member groups (with the exception of the Global Members group) must
-- have a Country Id assigned.
-- =============================================================================
create sequence group_sequence start with 10 increment by 1;
create table groups (
    id                  integer default nextval('group_sequence'),
    external_id         varchar(36),
    parent_id           integer,
    external_parent_id  varchar(36),
    grouptype_id        integer,
    group_name          varchar(50),
    full_name           varchar(125),
    group_description   varchar(250),
    country_id          integer,
    list_name           varchar(100),
    private_list        boolean default false,
    public_list         boolean default false,
    status              varchar(10) default 'ACTIVE',
    monitoring_level    varchar(10) default 'NONE',
    private_list_reply  varchar(20) default 'REPLY_TO_LIST',
    public_list_reply   varchar(20) default 'REPLY_TO_SENDER',
    old_iw3_id          integer,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint group_pk              primary key (id),
    constraint group_fk_group_id     foreign key (parent_id)    references groups (id),
    constraint group_fk_grouptype_id foreign key (grouptype_id) references grouptypes (id),
    constraint group_fk_country_id   foreign key (country_id)   references countries (id),

    /* Unique Constraints */
    constraint group_unique_external_id unique (external_id),
    constraint group_unique_old_iw3_id  unique (old_iw3_id),
    -- Required, since we otherwise cannot guarantee that mailing lists are unique
    constraint group_unique_full_name   unique (full_name),
    -- Required, to avoid that multiple identical Groups are being added. Since
    -- the Unique Constraint won't apply for null values, it means that it
    -- should not be a problem for Private groups (See Trac ticket #854).
    --   However, since the constraint may cause clashes with suspended/deleted
    -- Groups, it cannot be applied!
    --constraint group_unique_name_parent unique (group_name, parent_id),

    /* Not Null & Other Constraints */
    constraint group_notnull_id            check (id is not null),
    constraint group_notnull_external_id   check (external_id is not null),
    constraint group_notnull_grouptype_id  check (grouptype_id is not null),
    constraint group_notnull_name          check (group_name is not null),
    constraint group_parent_before_id      check (parent_id <= id),
    constraint group_private_list          check (private_list is not null),
    constraint group_public_list           check (public_list is not null),
    constraint group_notnull_status        check (status is not null),
    constraint group_monitoring_level      check (monitoring_level is not null),
    constraint group_notnull_private_reply check (private_list_reply is not null),
    constraint group_notnull_public_reply  check (public_list_reply is not null),
    constraint group_notnull_modified      check (modified is not null),
    constraint group_notnull_created       check (created is not null)
);


-- =============================================================================
-- Roles
-- -----------------------------------------------------------------------------
-- A User is associated to a Group with a Role, the Role serves as a "hat" for
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
    external_id         varchar(36),
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
    constraint role_unique_external_id unique (external_id),
    constraint role_unique_ids         unique (role, country_id, group_id),

    /* Not Null Constraints */
    constraint role_notnull_id          check (id is not null),
    constraint role_notnull_external_id check (external_id is not null),
    constraint role_notnull_role        check (role is not null),
    constraint role_notnull_modified    check (modified is not null),
    constraint role_notnull_created     check (created is not null)
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
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint p2r_pk               primary key (id),
    constraint p2r_fk_permission_id foreign key (permission_id) references permissions (id),
    constraint p2r_fk_role_id       foreign key (role_id)       references roles (id),

    /* Unique Constraints */
    constraint p2r_unique_ids         unique (permission_id, role_id),

    /* Not Null Constraints */
    constraint p2r_notnull_id            check (id is not null),
    constraint p2r_notnull_permission_id check (permission_id is not null),
    constraint p2r_notnull_role_id       check (role_id is not null),
    constraint p2r_notnull_created       check (created is not null)
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
    postal_code         varchar(12)  default '',
    city                varchar(100) default '',
    state               varchar(100) default '',
    pobox               varchar(100) default '',
    country_id          integer,
    modified            timestamp    default now(),
    created             timestamp    default now(),

    /* Primary & Foreign Keys */
    constraint address_pk              primary key (id),
    constraint address_fk_countries_id foreign key (country_id) references countries (id),

    /* Not Null Constraints */
    constraint address_notnull_id          check (id is not null),
    constraint address_notnull_modified    check (modified is not null),
    constraint address_notnull_created     check (created is not null)
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
    id                 integer default nextval('person_sequence'),
    firstname          varchar(50),
    lastname           varchar(50),
    nationality        integer,
    address_id         integer,
    email              varchar(100),
    phone              varchar(25),
    mobile             varchar(25),
    fax                varchar(25),
    birthday           date,
    gender             varchar(10),
    understood_privacy boolean default false,
    accept_newsletters boolean default true,
    modified           timestamp default now(),
    created            timestamp default now(),

    /* Primary & Foreign Keys */
    constraint persons_pk             primary key (id),
    constraint persons_fk_nationality foreign key (nationality) references countries (id),
    constraint persons_fk_address_id  foreign key (address_id) references addresses (id),

    /* Not Null Constraints */
    constraint person_notnull_id          check (id is not null),
    constraint person_notnull_modified    check (modified is not null),
    constraint person_notnull_created     check (created is not null)
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
    person_id           integer,
    status              varchar(25) default 'NEW',
    user_type           varchar(10) default 'VOLUNTEER',
    private_data        varchar(10) default 'PRIVATE',
    notifications       varchar(25) default 'IMMEDIATELY',
    temporary_code      varchar(128),
    temporary_data      varchar(128),
    temporary_expire    timestamp,
    old_iw3_id          integer,
    modified            timestamp   default now(),
    created             timestamp   default now(),

    /* Primary & Foreign Keys */
    constraint user_pk primary key (id),
    constraint person_fk foreign key (person_id) references persons (id),

    /* Unique Constraints */
    constraint user_unique_external_id unique (external_id),
    constraint user_unique_username    unique (username),
    constraint user_unique_alias       unique (alias),
    constraint user_unique_code        unique (temporary_code),

    /* Not Null Constraints */
    constraint user_notnull_id            check (id is not null),
    constraint user_notnull_external_id   check (external_id is not null),
    constraint user_notnull_username      check (username is not null),
    constraint user_notnull_firstname     check (firstname is not null),
    constraint user_notnull_lastname      check (lastname is not null),
    constraint user_notnull_status        check (status is not null),
    constraint user_notnull_user_type     check (user_type is not null),
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
--   The SessionKey is generated with a cryptographic checksum, that contains
-- both some unique entropy, and some user information. The created timestamp is
-- set when the user is logging in, and the modified is set every time the user
-- makes a request.
-- =============================================================================
create sequence session_sequence start with 1 increment by 1;
create table sessions (
    id                  integer default nextval('session_sequence'),
    session_key         varchar(128),
    user_id             integer,
    deprecated          varchar(32) default '0',
    session_data        bytea,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint session_pk         primary key (id),
    constraint session_fk_user_id foreign key (user_id) references users (id),

    /* Unique Constraints */
    constraint session_unique_session_key unique (session_key),
    constraint session_unique_active_user unique (user_id, deprecated),

    /* Not Null Constraints */
    constraint session_notnull_id          check (id is not null),
    constraint session_notnull_session_key check (session_key is not null),
    constraint session_notnull_user_id     check (user_id is not null),
    constraint session_notnull_deprecated  check (deprecated is not null),
    constraint session_notnull_modified    check (modified is not null),
    constraint session_notnull_created     check (created is not null)
);


-- =============================================================================
-- Session Request Information
-- -----------------------------------------------------------------------------
-- To help track problems better, some information is stored within the database
-- about the requests which has been made as part of a Session. The data stored
-- is only the request, error code and error message.
--   If a request resulted in an unexpected error, the full request Object is
-- also stored. So it is possible to create an anonymized test case to debug and
-- fix/resolve the error.
-- =============================================================================
create sequence request_sequence start with 1 increment by 1;
create table requests (
    id                integer default nextval('request_sequence'),
    session_id        integer,
    request           varchar(100),
    errorcode         integer,
    errormessage      varchar(512),
    request_object    bytea,
    created           timestamp default now(),

    /* Primary & Foreign Keys */
    constraint request_pk         primary key (id),
    constraint request_session_id foreign key (session_id) references sessions (id),

    /* Not Null Constraints */
    constraint request_notnull_id            check (id is not null),
    constraint request_notnull_request       check (request is not null),
    constraint request_notnull_errorcode     check (errorcode is not null),
    constraint request_notnull_errormessage  check (errormessage is not null),
    constraint request_notnull_created       check (created is not null)
);


-- =============================================================================
-- User to Group Associations
-- -----------------------------------------------------------------------------
-- Although this is just a "relation" table - certain additional information is
-- crammed into it, since a users association with a group also include
-- information about how the user may access data, and how the system should
-- deal with information sent to the mailing lists of the Group.
--   Further, a user can remain on the Group, but with the status  "Suspended",
-- meaning that the user cannot access anything, but the status can be restored
-- together with all other settings, if so desired.
-- =============================================================================
create sequence user_to_group_sequence start with 1 increment by 1;
create table user_to_group (
    id                     integer default nextval('user_to_group_sequence'),
    external_id            varchar(36),
    user_id                integer,
    group_id               integer,
    role_id                integer,
    custom_title           varchar(50),
    on_public_list         boolean default false,
    on_private_list        boolean default true,
    write_to_private_list  boolean default true,
    status                 boolean default true,
    modified               timestamp default now(),
    created                timestamp default now(),

    /* Primary & Foreign Keys */
    constraint u2g_pk          primary key (id),
    constraint u2g_fk_user_id  foreign key (user_id)  references users (id) on delete cascade,
    constraint u2g_fk_group_id foreign key (group_id) references groups (id) on delete cascade,
    constraint u2g_fk_role_id  foreign key (role_id)  references roles (id),

    /* Unique Constraints */
    constraint u2g_unique_external_id unique (external_id),
    constraint u2g_unique_session_key unique (user_id, group_id),

    /* Not Null Constraints */
    constraint u2g_notnull_id                 check (id is not null),
    constraint u2g_notnull_external_id        check (external_id is not null),
    constraint u2g_notnull_user_id            check (user_id is not null),
    constraint u2g_notnull_group_id           check (group_id is not null),
    constraint u2g_notnull_role_id            check (role_id is not null),
    constraint u2g_notnull_on_public_list     check (on_public_list is not null),
    constraint u2g_notnull_on_private_list    check (on_private_list is not null),
    constraint u2g_notnull_write_private_list check (write_to_private_list is not null),
    constraint u2g_notnull_status             check (status is not null),
    constraint u2g_notnull_modified           check (modified is not null),
    constraint u2g_notnull_created            check (created is not null)
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
    created             timestamp default now(),

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
    constraint history_notnull_created   check (created is not null)
);


-- =============================================================================
-- Folders
-- -----------------------------------------------------------------------------
-- In IWS, all files that can be accessed via the Archive, will be placed within
-- a Folder Structure. All folders are by default Public, but accessing them it
-- handled by the GroupType, which holds the rules as to whether or not a Folder
-- is accessible by all or only the Group itself.
-- =============================================================================
create sequence folder_sequence start with 10 increment by 1 no cycle;
create table folders (
    id               integer default nextval('folder_sequence'),
    external_id      varchar(36),
    parent_id        integer,
    group_id         integer,
    foldername       varchar(100),
    privacy          varchar(10) default 'PROTECTED',
    old_iw3_file_id  integer,
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
    constraint folder_notnull_parent_id    check (parent_id < id),
    constraint folder_notnull_group_id     check (group_id is not null),
    constraint folder_notnull_filename     check (foldername is not null),
    constraint folder_notnull_privacy      check (privacy is not null),
    constraint folder_notnull_modified     check (modified is not null),
    constraint folder_notnull_created      check (created is not null)
);


-- =============================================================================
-- Files
-- -----------------------------------------------------------------------------
-- File handling in IWS is different than in IW3, in the sense that in IW3, all
-- files were stored with "classic" file system information, so it was possible
-- to browse the file structure. In IWS, the files are considered attachments to
-- other Objects, so only relevant information is stored.
--   Relevant information is file name, size, MIME type and ownership, together
-- with some additional information that may or may nor be relevant depending on
-- the context. The file itself is stored compressed, so the stored size will
-- differ from the provided file size. The checksum is added to ensure that the
-- raw file is valid.
-- =============================================================================
create sequence file_sequence start with 20 increment by 1 no cycle;
create table files (
    id               integer default nextval('file_sequence'),
    external_id      varchar(36),
    privacy          varchar(10) default 'PROTECTED',
    group_id         integer,
    user_id          integer,
    folder_id        integer,
    filename         varchar(100),
    stored_filename  varchar(100),
    filesize         integer     default 0,
    mimetype         varchar(50),
    description      varchar(250),
    keywords         varchar(250),
    checksum         bigint,
    old_iw3_file_id  integer,
    modified         timestamp   default now(),
    created          timestamp   default now(),

    /* Primary & Foreign Keys */
    constraint file_pk             primary key (id),
    constraint file_fk_group_id    foreign key (group_id) references groups (id) on delete restrict on update cascade,
    constraint file_fk_user_id     foreign key (user_id) references users (id),
    constraint file_fk_folder_id   foreign key (folder_id) references folders (id),

    /* Unique Constraints */
    constraint file_unique_external_id unique (external_id),

    /* Not Null Constraints */
    constraint file_notnull_id           check (id is not null),
    constraint file_notnull_external_id  check (external_id is not null),
    constraint file_notnull_privacy      check (privacy is not null),
    constraint file_notnull_group_id     check (group_id is not null),
    constraint file_notnull_user_id      check (user_id is not null),
    constraint file_notnull_filename     check (filename is not null),
    constraint file_notnull_modified     check (modified is not null),
    constraint file_notnull_created      check (created is not null)
);


-- =============================================================================
-- FileData
-- -----------------------------------------------------------------------------
-- Storing of data can be done either in the database or file system. Since the
-- IWS is configured in production to use replication - it is possible to rely
-- on this for backup, as it reduces the number of sources for data to one. This
-- table contain the content of files which doesn't exist in the file system.
--   To ensure that the Entities will not attempt to load it, the link is
-- reversed, so the foreign key is in this table.
-- =============================================================================
create sequence filedata_sequence start with 1 increment by 1 no cycle;
create table filedata (
    id               integer default nextval('filedata_sequence'),
    file_id          integer,
    file_data        bytea,
    created          timestamp   default now(),

    /* Primary & Foreign Keys */
    constraint filedata_pk               primary key (id),
    constraint filedata_fk_file_id       foreign key (file_id) references files (id),

    /* Not Null Constraints */
    constraint filedata_notnull_id       check (id is not null),
    constraint filedata_notnull_created  check (id is not null)
);


-- =============================================================================
-- Attachments
-- -----------------------------------------------------------------------------
-- Files can be attached to many kinds of documents. And as the IWS requires a
-- dynamic listing, we need a relation table to handle it. To avoid that
-- multiple tables exists for each Objects that can contain the Attachments, a
-- single Attachment table is made.
--   Attachments are considered binary, either they are there or they aren't.
-- There is no state for an attachment, hence there is only a Created timestamp,
-- no Modified! Further, as the attachment is dynamic in nature, the Table &
-- Record which the Attachment belongs to are free fields. Mostly just use for
-- searching. Te AttachedToTable field is the name of the Table, and the Record
-- is then the Id within the AttachedToTable, which the Attachment belongs to.
-- =============================================================================
create sequence attachment_sequence start with 1 increment by 1 no cycle;
create table attachments (
    id                  integer default nextval('attachment_sequence'),
    attached_to_table   varchar(50),
    attached_to_record  integer,
    attached_file_id    integer,
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint attachment_pk          primary key (id),
    constraint attachment_fk_file_id  foreign key (attached_file_id) references files (id),

    /* Unique Constraints */
    constraint attachment_unique_record_file unique (attached_file_id, attached_to_record, attached_to_table),

    /* Not Null Constraints */
    constraint attachment_notnull_id       check (id is not null),
    constraint attachment_notnull_table    check (attached_to_table is not null),
    constraint attachment_notnull_record   check (attached_to_record is not null),
    constraint attachment_notnull_file_id  check (attached_file_id is not null),
    constraint attachment_notnull_created  check (created is not null)
);


-- =============================================================================
-- User notifications setting
-- -----------------------------------------------------------------------------
-- The notification should additionally Group the group that the notification is
-- sent to. Further, the frequency is a user setting, so it should be read from
-- the user table and linked in
-- =============================================================================
create sequence user_notification_sequence start with 1 increment by 1;
create table user_notifications (
    id                 integer default nextval('user_notification_sequence'),
    user_id            integer not null,
    notification_type  varchar(100),
    frequency          varchar(100),
    created            timestamp default now(),

    /* Primary & Foreign Keys */
    constraint user_notifications_pk         primary key (id),
    constraint user_notifications_fk_user_id foreign key (user_id) references users (id) on delete cascade,

    /* Not Null Constraints */
    constraint user_notifications_notnull_id      check (id is not null),
    constraint user_notifications_notnull_user_id check (user_id is not null),
    constraint user_notifications_notnull_created check (created is not null)
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
    message            varchar(1000),
    delivery_mode      varchar(100),
    status             varchar(100),
    process_after      timestamp default now(),
    created            timestamp default now(),

    /* Primary & Foreign Keys */
    constraint notitication_messages_pk         primary key (id),
    constraint notitication_messages_fk_user_id foreign key (user_id) references users (id) on delete cascade,

    /* Not Null Constraints */
    constraint notitication_messages_notnull_id                check (id is not null),
    constraint notitication_messages_notnull_user_id           check (user_id is not null),
    constraint notitication_messages_notnull_delivery_mode     check (delivery_mode is not null),
    constraint notitication_messages_notnull_process_after     check (process_after is not null),
    constraint notitication_messages_notnull_created           check (created is not null)
);


-- =============================================================================
-- Notification consumers
-- -----------------------------------------------------------------------------
--
-- =============================================================================
create sequence notification_consumer_sequence start with 10 increment by 1;
create table notification_consumers (
    id                 integer default nextval('notification_consumer_sequence'),
    group_id           integer,
    name               varchar(100),
    class_name         varchar(100),
    active             boolean default false,
    created            timestamp default now(),
    modified           timestamp default now(),

    /* Primary & Foreign Keys */
    constraint notitication_consumers_pk          primary key (id),
    constraint notitication_consumers_fk_group_id foreign key (group_id) references groups (id) on delete cascade,

    /* Unique Constraints */
    constraint notification_consumers_unique_consumer_name unique (group_id, name),

    /* Not Null Constraints */
    constraint notification_consumers_notnull_id                check (id is not null),
    constraint notification_consumers_notnull_group_id          check (group_id is not null),
    constraint notification_consumers_notnull_created           check (created is not null),
    constraint notification_consumers_notnull_modified          check (modified is not null)
);


-- =============================================================================
-- Jobs - notification jobs to be processed by Notification Manager
-- -----------------------------------------------------------------------------
--
-- =============================================================================
create sequence notification_job_sequence start with 1 increment by 1;
create table notification_jobs (
    id                  integer default nextval('notification_job_sequence'),
    notification_type   varchar(100),
    object              bytea,
    notified            boolean default false,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint notification_job_pk          primary key (id),

    /* Not Null Constraints */
    constraint notification_job_notnull_id                  check (id is not null),
    constraint notification_job_notnull_notification_type   check (notification_type is not null),
    constraint notification_job_notnull_object              check (object is not null),
    constraint notification_job_notnull_modified            check (modified is not null),
    constraint notification_job_notnull_created             check (created is not null)
);


-- =============================================================================
-- Notification Job Tasks - task for notification consumers
-- -----------------------------------------------------------------------------
--
-- =============================================================================
create sequence notification_job_task_sequence start with 1 increment by 1;
create table notification_job_tasks (
    id                  integer default nextval('notification_job_task_sequence'),
    job_id              integer,
    consumer_id         integer,
    processed           boolean default false,
    attempts            integer,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint notification_job_task_pk              primary key (id),
    constraint notification_job_task_fk_job_id       foreign key (job_id) references notification_jobs (id),
    constraint notification_job_task_fk_consumer_id  foreign key (consumer_id) references notification_consumers (id) on delete cascade,

    /* Not Null Constraints */
    constraint notification_job_task_notnull_id                  check (id is not null),
    constraint notification_job_task_notnull_modified            check (modified is not null),
    constraint notification_job_task_notnull_created             check (created is not null)
);
