-- ============================================================================
-- IntraWeb as a Service - Core Table definition for PostgreSQL
-- ----------------------------------------------------------------------------
-- Revision History:
-- 0.01 March 4, 2012 - Kim Jensen <kim@dawn.dk>
--   - Initial revision, based on the old database
-- ----------------------------------------------------------------------------
-- The script uses PostgreSQL 9+ specific commands, it will not work with
-- previous versions of PostgreSQL, nor with any other database system.
--   To install, please do the following:
-- $ psql <DATABASE_NAME> < core.sql
-- ----------------------------------------------------------------------------
-- To view the comments, please invoke the enhanced descriptor command: \d+
-- ----------------------------------------------------------------------------
-- The following tables are defined in this script:
--  o countries
--  o users
--  o sessions
--  o groups
--  o group_types
--  o functions
--  o roles
--  o function2role
--  o function2group_type
--  o user2group
-- ============================================================================

-- So we don't get the annoying warnings about lictly created indexes.
SET client_min_messages='warning';

-- Build a Schema and use this per default
-- DROP SCHEMA IF EXISTS iws CASCADE;
CREATE SCHEMA IF NOT EXISTS iws;
SET search_path to iws;

-- ============================================================================
-- Cleanup, ensuring that existing tables are removed
-- ============================================================================
DROP TABLE IF EXISTS function2group_type CASCADE;
DROP TABLE IF EXISTS function2role CASCADE;
DROP TABLE IF EXISTS user2group CASCADE;
DROP TABLE IF EXISTS functions CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS group_types CASCADE;
DROP TABLE IF EXISTS sessions CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS countries CASCADE;
DROP SEQUENCE IF EXISTS users_sequence;
DROP SEQUENCE IF EXISTS countries_sequence;
DROP SEQUENCE IF EXISTS session_sequence;
DROP SEQUENCE IF EXISTS roles_sequence;
DROP SEQUENCE IF EXISTS groups_sequence;


-- ============================================================================
-- The required sequences, all tables with dynamically assigned data has one
-- ============================================================================
CREATE SEQUENCE roles_sequence START 10;
CREATE SEQUENCE countries_sequence START 1;
CREATE SEQUENCE users_sequence START 1;
CREATE SEQUENCE session_sequence START 1;
CREATE SEQUENCE groups_sequence START 1;


-- ============================================================================
-- The Countries Table - All users must belong to an active country
-- ============================================================================
CREATE TABLE countries (
    id                  INTEGER DEFAULT NextVal('countries_sequence') NOT NULL PRIMARY KEY,
    country_code        VARCHAR(2)   NOT NULL CHECK (length(country_code) = 2),
    country_name        VARCHAR(100) NOT NULL CHECK (length(country_code) > 1),
    country_fullname    VARCHAR(100) DEFAULT '',
    country_native      VARCHAR(100) DEFAULT '',
    nationality         VARCHAR(100) NOT NULL,
    citizens            VARCHAR(100) DEFAULT '',
    phone_code          VARCHAR(5)   DEFAULT '',
    currency            VARCHAR(3)   DEFAULT '',
    æanguages           VARCHAR(100) DEFAULT '',
    membership          INTEGER      DEFAULT 5,
    membership_since    INTEGER      DEFAULT -1,
    modified            TIMESTAMP    DEFAULT now(),
    created             TIMESTAMP    DEFAULT now()
);
INSERT INTO countries (country_code, country_name, nationality) VALUES ('$$','','');


-- ============================================================================
-- The Users Table - List of all users in the system
-- ============================================================================
CREATE TABLE users (
    id                  INTEGER DEFAULT NextVal('users_sequence') NOT NULL PRIMARY KEY,
    modified            TIMESTAMP    DEFAULT now(),
    created             TIMESTAMP    DEFAULT now()
);


-- ============================================================================
-- The Access Table - All remote request requires an active session
-- ============================================================================
CREATE TABLE sessions (
    id                  INTEGER      DEFAULT NextVal('session_sequence') NOT NULL PRIMARY KEY,
    session_key         VARCHAR(512) NOT NULL CONSTRAINT unique_session_key UNIQUE,
    session_data        TEXT         DEFAULT '',
    session_status      INTEGER      DEFAULT 0,
    user_id             INTEGER      NOT NULL REFERENCES users (id),
    ipv4                VARCHAR(15)  DEFAULT '',
    modified            TIMESTAMP    DEFAULT now() NOT NULL,
    created             TIMESTAMP    DEFAULT now() NOT NULL
);
COMMENT ON TABLE  sessions                IS 'Contains the unique key for each user session.';
COMMENT ON COLUMN sessions.session_key    IS 'The Cryptographical (MD5 or SHA) checksum value for this unique session.';
COMMENT ON COLUMN sessions.session_data   IS 'Session data for remote systems without persistency.';
COMMENT ON COLUMN sessions.session_status IS 'Status value, either active, 1, or inactive 0.';


-- ============================================================================
-- The Roles Table - All group accessed is controlled via roles
-- ============================================================================
CREATE TABLE roles (
    id                  INTEGER      DEFAULT NextVal('roles_sequence') NOT NULL PRIMARY KEY,
    modified            TIMESTAMP    DEFAULT now() NOT NULL,
    created             TIMESTAMP    DEFAULT now() NOT NULL
);


-- ============================================================================
-- The Functions Table - All accesss is associated with a function
-- ============================================================================
CREATE TABLE functions (
    id                  INTEGER      NOT NULL PRIMARY KEY,
    function_name       VARCHAR(50)  NOT NULL,
    modified            TIMESTAMP    DEFAULT now() NOT NULL,
    created             TIMESTAMP    DEFAULT now() NOT NULL
);


-- ============================================================================
-- The GroupTypes Table - All groups have a super type
-- ============================================================================
CREATE TABLE group_types (
    id                  INTEGER      NOT NULL PRIMARY KEY,
    group_type          VARCHAR(25)  NOT NULL,
    modified            TIMESTAMP    DEFAULT now() NOT NULL,
    created             TIMESTAMP    DEFAULT now() NOT NULL
);
INSERT INTO group_types (id, group_type) VALUES ( 0, 'Administration');
INSERT INTO group_types (id, group_type) VALUES ( 1, 'Members');
INSERT INTO group_types (id, group_type) VALUES ( 2, 'International');
INSERT INTO group_types (id, group_type) VALUES ( 3, 'Regional');
INSERT INTO group_types (id, group_type) VALUES ( 4, 'National');
INSERT INTO group_types (id, group_type) VALUES ( 5, 'SAR');
INSERT INTO group_types (id, group_type) VALUES ( 6, 'Local');
INSERT INTO group_types (id, group_type) VALUES ( 7, 'Workgroup');
INSERT INTO group_types (id, group_type) VALUES ( 8, 'IAESTE Alumni');
INSERT INTO group_types (id, group_type) VALUES ( 9, 'Students');
INSERT INTO group_types (id, group_type) VALUES (10, 'Guests');


-- ============================================================================
-- The Groups Table - All rights go via groups
-- ============================================================================
CREATE TABLE groups (
    id                  INTEGER      DEFAULT NextVal('groups_sequence') NOT NULL PRIMARY KEY,
    group_type_fk       INTEGER      NOT NULL REFERENCES group_types (id) ON DELETE CASCADE,
    modified            TIMESTAMP    DEFAULT now() NOT NULL,
    created             TIMESTAMP    DEFAULT now() NOT NULL
);


-- ============================================================================
-- Function to Role Relation Table - Tells which functions a role may perform
-- ============================================================================
CREATE TABLE function2role (
    function_id_fk      INTEGER NOT NULL REFERENCES functions (id) ON DELETE CASCADE,
    role_id_fk          INTEGER NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    modified            TIMESTAMP    DEFAULT now(),
    created             TIMESTAMP    DEFAULT now(),
    PRIMARY KEY (function_id_fk, role_id_fk)
);


-- ============================================================================
-- Function to GroupTypes Relation Table - Overall functionality for groups
-- ============================================================================
CREATE TABLE function2group_type (
    function_id_fk      INTEGER NOT NULL REFERENCES functions (id) ON DELETE CASCADE,
    group_type_id_fk    INTEGER NOT NULL REFERENCES group_types (id) ON DELETE CASCADE,
    modified            TIMESTAMP    DEFAULT now(),
    created             TIMESTAMP    DEFAULT now(),
    PRIMARY KEY (function_id_fk, group_type_id_fk)
);


-- ============================================================================
-- User to Group relation table - Links users to a group with a role
-- ============================================================================
CREATE TABLE user2group (
    user_id_fk          INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    group_id_fk         INTEGER NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
    roles_id_fk         INTEGER NOT NULL REFERENCES roles (id),
    modified            TIMESTAMP    DEFAULT now(),
    created             TIMESTAMP    DEFAULT now(),
    PRIMARY KEY (user_id_fk, group_id_fk)
);
