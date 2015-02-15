-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.1.7 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the second DB version, and the IWS
-- version it is related to.
insert into versions (db_version, iws_version) values (5, '1.1.7');

-- =============================================================================
-- Corrections for Trac Tasks
-- =============================================================================
