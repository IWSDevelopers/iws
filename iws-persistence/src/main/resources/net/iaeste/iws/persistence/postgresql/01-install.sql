-- =============================================================================
-- This script will setup the IWS Database for PostgreSQL. It will wipe any
-- existing database and roles and create everything from scratch.
--   The script must be run by a user with the permissions to create a new
-- database, and grant permissions. To run the script, simply use this command:
-- $ psql -f 01-install.sql postgres
-- =============================================================================


-- -----------------------------------------------------------------------------
-- Part ONE: Creating Database & Roles
-- -----------------------------------------------------------------------------
-- To ensure that we have a clean IWS database setup, we start by re-creating
-- the Database & Roles required:
drop database if exists iws;
drop role if exists iws_user;
drop role if exists mail_user;

-- Create the users needed for both IWS & Mail. Please note, that the
-- default AppServer Configuration file is referring to these users & passwords.
-- So, if you use a different name and password, please also update this.
create role iws_user with login password 'iws';
create role mail_user with login password 'mail';

-- Create the IWS database for the IWS User
create database iws with owner = iws_user;
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
-- Part TWO: Creating Tables
-- -----------------------------------------------------------------------------
-- Now, we're ready to create the actual database. Do so so by first connecting
-- to our newly created IWS database.
\connect iws iws_user

-- Now, we can fill the database with tables, views & data
\ir 10-base-tables.sql
\ir ../15-base-views.sql
\ir ../19-base-data.sql
\ir 30-exchange-tables.sql
\ir ../35-exchange-views.sql
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
-- Part THREE: Grant access to others
-- -----------------------------------------------------------------------------
-- Finally, we grant the permissions to use this database to the IWS Mail User.
-- Please note, that the IWS Mail User is only allowed to read from certain
-- tables in the database.
grant connect on database iws to mail_user;
grant select on table mailing_lists to mail_user;
grant select on table user_to_mailing_list to mail_user;
grant select on table list_members to mail_user;
-- -----------------------------------------------------------------------------


-- =============================================================================
-- Done, database is now created :-D
-- =============================================================================
