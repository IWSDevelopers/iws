-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.14 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the next DB version, and the IWS
-- version it is related to.
-- Commented out, as the current updates will not change the data model, only
-- correct some problems discovered with the Views & Data Content
insert into versions (db_version, iws_version) values (6, '1.1.14');

-- =============================================================================
-- Changes required for Groups, as part of Trac ticket 1044.
-- =============================================================================
alter table groups add external_parent_id  varchar(36);
alter table groups add private_list_reply  varchar(20) default 'REPLY_TO_LIST';
alter table groups add public_list_reply   varchar(20) default 'REPLY_TO_SENDER';

update groups set private_list_reply = 'REPLY_TO_LIST';
update groups set public_list_reply = 'REPLY_TO_SENDER';

alter table groups add constraint group_notnull_private_reply check (private_list_reply is not null);
alter table groups add constraint group_notnull_public_reply  check (public_list_reply is not null);

-- Error in the Jamaica Table
update groups set parent_id = 3358 where id = 3359;
-- Now we just have to update the Groups table, and ser the external parent Id.
update groups g set external_parent_id = (select p.external_id from groups p where p.id = g.parent_id) where parent_id is not null;
