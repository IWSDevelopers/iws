-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.15 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the next DB version, and the IWS
-- version it is related to.
-- Commented out, as the current updates will not change the data model, only
-- correct some problems discovered with the Views & Data Content
insert into versions (db_version, iws_version) values (7, '1.1.15');


-- Changes to the User table.
alter table users add eula_version        integer     default 0;
alter table users add password_changed    timestamp   default now();
update users set eula_version = 0;
update users set password_changed = now();
alter table users add constraint user_notnull_eula_version      check (eula_version is not null);
alter table users add constraint user_notnull_password_changed  check (password_changed is not null);

create sequence alias_sequence start with 1 increment by 1;
create table aliases (
    id                  integer default nextval('alias_sequence'),
    group_id            integer,
    alias_address       varchar(100),
    expires             date,
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint alias_pk           primary key (id),
    constraint alias_fk_group_id  foreign key (group_id) references groups (id),

    /* Not Null Constraints */
    constraint alias_notnull_id             check (id is not null),
    constraint alias_notnull_alias_address  check (alias_address is not null),
    constraint alias_notnull_modified       check (modified is not null),
    constraint alias_notnull_created        check (created is not null)
);

alter table offers add local_committee_id        integer;
alter table offers add constraint offer_fk_local_committee_id foreign key (local_committee_id) references groups (id);
