-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.10 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the next DB version, and the IWS
-- version it is related to.
-- Commented out, as the current updates will not change the data model, only
-- correct some problems discovered with the Views & Data Content
insert into versions (db_version, iws_version) values (5, '1.1.10');

alter table persons add column understood_privacy boolean default false;
alter table persons add column accept_newsletters boolean default true;
alter table users add column temporary_expire timestamp;
alter table folders add column privacy varchar(10) default 'PROTECTED';
alter table only files alter column privacy set default 'PROTECTED';

update persons set understood_privacy = false, accept_newsletters = true;
update files set privacy = 'PROTECTED';
update folders set privacy = 'PROTECTED';

-- New table for the IWS, which contain file data - to revert a design
-- mistake forced by a formed administrator.
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
