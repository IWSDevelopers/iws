-- =============================================================================
-- This script will install a sample PostgreSQL database for the IntraWeb
-- Services Project. The script will create the two required databases, add
-- tables, views, data & the users to use for connecting.
--   The script must be run by a user with the permissions to create a new
-- database, and grant permissions. However, it cannot be run as the default
-- PostgreSQL administrator, as it will reassign ownership, and reassigning from
-- the default PostgreSQL administrator is not allowed.
--   To run the script, simply use this command:
-- $ psql -f 01-install.sql postgres
-- =============================================================================

-- To reasign ownership, we need to know who is currently logged in. The command
-- to reassign ownership will not work for user "postgres", as there's hidden
-- system tables
\prompt 'Account name used to create Database with (Cancel with <Esc>):  ' currentuser

-- =============================================================================
-- We start by dropping the IWS Databases & Roles
drop database if exists iws;
drop database if exists mail;
drop role if exists iws_user;
drop role if exists mail_user;

-- Create the users needed for both IWS & Mail. Please note, that the
-- default AppServer Configuration file is referring to these users & passwords.
-- So, if you use a different name and password, please also update this.
create role iws_user with password 'iws';
create role mail_user with password 'mail';

-- Create the IWS database & Mail database
create database iws with owner = iws_user;
create database mail with owner = iws_user;
-- =============================================================================


-- =============================================================================
-- Now, we're ready to create the actual database. Do so so by first connecting
-- to our newly created IWS database. In case the database already exists, the
-- creation will fail - to avoid that, we simply drop/create the public schema.
\connect iws
drop schema if exists public cascade;
create schema public;

-- Now, we can fill the database with tables, views & data
\ir 10-base-tables.sql
\ir ../15-base-views.sql
\ir ../19-base-data.sql
\ir 30-exchange-tables.sql
\ir ../35-exchange-views.sql
\ir ../39-exchange-data.sql

-- Finally, we grant the permissions to use this database to the IWS Mail User.
-- Please note, that the IWS Mail User is only allowed to read from the
-- database, whereas the IWS User is allowed to also fill the content of the
-- tables.
grant connect on database iws to iws_user;
grant select, insert, update, delete, truncate on all tables in schema public to iws_user;

-- If the script fails to
-- Now ensure that the IWS User is owning the database
reassign owned by :currentuser to iws_user;
-- Done. IWS Database is now complete
-- =============================================================================


-- =============================================================================
-- Now, we can repeat the above for the IWS Mail database.
\connect mail
drop schema if exists public cascade;
create schema public;

-- Now, we can fill the database with tables, views & data
\ir 20-mailing-list-tables.sql

-- Finally, we grant the permissions to use this database to the IWS Mail User.
-- Please note, that the IWS Mail User is only allowed to read from the
-- database, whereas the IWS User is allowed to also fill the content of the
-- tables.
grant connect on database mail to iws_user;
grant select, insert, update, delete, truncate on all tables in schema public to iws_user;
grant connect on database mail to mail_user;
grant select on all tables in schema public to mail_user;

-- Now ensure that the IWS User is owning the database
reassign owned by :currentuser to iws_user;
-- Done, database is now created :-D
-- =============================================================================
