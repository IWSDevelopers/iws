-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.16 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the next DB version, and the IWS
-- version it is related to.
-- Commented out, as the current updates will not change the data model, only
-- correct some problems discovered with the Views & Data Content
insert into versions (db_version, iws_version) values (7, '1.1.16');

-- Changes to the User table.
alter table users add eula_version        integer     default 0;
alter table users add password_changed    timestamp   default now();
update users set eula_version = 0;
update users set password_changed = now();
alter table users add constraint user_notnull_eula_version      check (eula_version is not null);
alter table users add constraint user_notnull_password_changed  check (password_changed is not null);

alter table offers add local_committee_id        integer;
alter table offers add constraint offer_fk_local_committee_id foreign key (local_committee_id) references groups (id);

create sequence alias_sequence start with 1 increment by 1;
create table aliases (
    id                  integer default nextval('alias_sequence'),
    external_id         varchar(36),
    group_id            integer,
    alias_address       varchar(100),
    deprecated          varchar(36) default 'active',
    expires             date,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint alias_pk           primary key (id),
    constraint alias_fk_group_id  foreign key (group_id) references groups (id),

    /* Unique Constraints */
    constraint alias_unique_external_id unique (external_id),
    constraint alias_unique_alias       unique (alias_address, deprecated),

    /* Not Null Constraints */
    constraint alias_notnull_id             check (id is not null),
    constraint alias_notnull_external_id    check (external_id is not null),
    constraint alias_notnull_alias_address  check (alias_address is not null),
    constraint alias_notnull_status         check (deprecated is not null),
    constraint alias_notnull_modified       check (modified is not null),
    constraint alias_notnull_created        check (created is not null)
);

-- Aliases which we already have, from the IW3 -> IWS migration.
insert into aliases (external_id, group_id, alias_address) values
    -- Aliases for the Board
    ('30f0efd4-63d0-4f6e-9a97-e7621951b1dc', 3, 'president@iaeste.org'),
    ('3d61f1ce-532a-4dab-8a05-c1c4916f9931', 3, 'gs@iaeste.org'),
    ('3e4f67ba-78d5-4672-8a3e-0e79d41af5ee', 3, 'general.secretary@iaeste.org'),

    -- Private Alias for Bruce Wicks
    ('3d56ce65-fd65-49f5-974f-193800564a82', 1429, 'bruce.mehlmann-wicks@iaeste.org'),

    -- Aliases for Associate Member: India
    ('7a61558a-73be-4bd7-ae08-28397de6cf92', 155, 'india_ku@iaeste.org'),
    ('96e7841d-87f1-400a-82fa-e95d13803f41', 155, 'india_mit@iaeste.org'),

    -- Aliases for Co-operating Institution: Nepal
    ('c7ea2ea7-2190-4ed9-8872-7fad6f562e22', 425, 'nepal@iaeste.org'),
    ('25ba6612-34a5-4d6a-8173-a4781e3a27ae', 425, 'nepalci@iaeste.org'),

    -- Aliases for Co-operating Institution: Bangladesh, AFM
    ('3c5c3715-f5eb-4b95-a1e6-1da324482434', 2860, 'bangladeshafm@iaeste.org'),
    ('e7f2eb54-bed0-4f17-a50e-4ff7cf436439', 2860, 'bangladesh_afzal_management@iaeste.org'),

    -- Aliases for Co-operating Institution: Bangladesh, CAT
    ('2a7e3e7a-32e1-4b67-94e4-c481744bbbdd', 416, 'college_of_aviation_technology@iaeste.org'),

    -- Aliases for Co-operating Institution: Bolivia, IB
    ('7f44c78f-48b8-468a-861f-5cbc811d4feb', 407, 'boliviaib@iaeste.org'),

    -- Aliases for Co-operating Institution: Vietnam, NU
    ('e00eec36-c128-47b9-8482-4d230d1d24a9', 242, 'vietnamnu@iaeste.org'),

    -- Aliases for Co-operating Institution: Kenya, DKUT
    ('e138b5fc-2d38-4174-a058-e5cf77e24423', 422, 'kenya_dekut@iaeste.org'),

    -- Aliases for Co-operating Institution: Palestine, ANU
    ('22758861-a78c-4273-a4b2-909d8c965dec', 317, 'west_bank_anu@iaeste.org');

-- Before adding the new mailing list tables, we have to clean up the old
-- garbage.
drop table mailing_list_membership;
drop table mailing_lists;
drop table mailing_aliases;
drop sequence mailing_list_sequence;
drop sequence mailing_alias_sequence;
drop sequence mailing_list_membership_sequence;

-- Now adding the new mailing lists as part of Trac ticket #1082.
create sequence mailing_list_sequence start with 1 increment by 1;
create table mailing_lists (
    id              integer default nextval('mailing_list_sequence'),
    list_address    varchar(100),
    group_id        integer,
    subject_prefix  varchar(50),
    list_type       varchar(25),
    replyto_style   varchar(25) default 'REPLY_TO_SENDER',
    status          varchar(25) default 'ACTIVE',
    modified        timestamp default now(),
    created         timestamp default now(),

    /* Primary & Foreign Keys */
    constraint mailing_list_pk                     primary key (id),
    constraint mailing_list_group_id_fk            foreign key (group_id) references groups (id) on delete cascade,

    /* Unique Constraints */
    constraint mailing_list_unique_address         unique (list_address),

    /* Not Null Constraints */
    constraint mailing_list_notnull_id             check (id is not null),
    constraint mailing_list_notnull_list_address   check (list_address is not null),
    constraint mailing_list_notnull_group_id       check (group_id is not null),
    constraint mailing_list_notnull_list_type      check (list_type is not null),
    constraint mailing_list_notnull_replyto_style  check (replyto_style is not null),
    constraint mailing_list_notnull_modified       check (modified is not null),
    constraint mailing_list_notnull_created        check (created is not null)
);

create sequence user_to_mailing_list_sequence start with 1 increment by 1;
create table user_to_mailing_list (
    id                integer default nextval('user_to_mailing_list_sequence'),
    mailing_list_id   integer,
    user_to_group_id  integer,
    member            varchar(100),
    status            varchar(25) default 'ACTIVE',
    may_write         boolean default true,
    modified          timestamp default now(),
    created           timestamp default now(),

    /* Primary & Foreign Keys */
    constraint user_to_mailing_list_pk                             primary key (id),
    constraint user_to_mailing_list_fk_mailing_list_id             foreign key (mailing_list_id) references mailing_lists (id) on delete cascade,
    constraint user_to_mailing_list_user_group_id                  foreign key (user_to_group_id) references user_to_group (id) on delete cascade,

    /* Unique Constraints */
    constraint user_to_mailing_list_unique_mailing_list_user_id    unique (mailing_list_id, user_to_group_id),

    /* Not Null Constraints */
    constraint user_to_mailing_list_notnull_id                     check (id is not null),
    constraint user_to_mailing_list_notnull_mailing_list_id        check (mailing_list_id is not null),
    constraint user_to_mailing_list_notnull_user_to_group_id       check (user_to_group_id is not null),
    constraint user_to_mailing_list_notnull_list_member            check (member is not null),
    constraint user_to_mailing_list_notnull_modified               check (modified is not null),
    constraint user_to_mailing_list_notnull_created                check (created is not null)
);

insert into mailing_lists (list_address, group_id, subject_prefix, list_type, replyto_style, status) values
    ('ncs@iaeste.net', 3, 'NCS', 'PRIVATE_LIST', 'REPLY_TO_SENDER', 'ACTIVE'),
    ('announce@iaeste.net', 3, 'ANNOUNCE', 'PRIVATE_LIST', 'NO_REPLY', 'ACTIVE');
