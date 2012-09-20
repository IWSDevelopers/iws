DROP SEQUENCE IF EXISTS country_sequence CASCADE;
DROP TABLE IF EXISTS countries CASCADE;
DROP SEQUENCE IF EXISTS address_sequence CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP SEQUENCE IF EXISTS user_sequence CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP INDEX IF EXISTS user_id CASCADE;
DROP SEQUENCE IF EXISTS session_sequence CASCADE;
DROP TABLE IF EXISTS sessions CASCADE;
DROP SEQUENCE IF EXISTS grouptype_sequence CASCADE;
DROP TABLE IF EXISTS grouptypes CASCADE;
DROP INDEX IF EXISTS grouptype_id CASCADE;
DROP SEQUENCE IF EXISTS group_sequence CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP SEQUENCE IF EXISTS role_sequence CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS permission_to_grouptype CASCADE;
DROP TABLE IF EXISTS permission_to_role CASCADE;
DROP TABLE IF EXISTS role_to_group CASCADE;
DROP TABLE IF EXISTS user_to_group CASCADE;
DROP SEQUENCE IF EXISTS history_sequence CASCADE;
DROP TABLE IF EXISTS history CASCADE;

CREATE SEQUENCE country_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE countries (
  id                    integer DEFAULT nextval('country_sequence') PRIMARY KEY,
  country_name          varchar(100) unique NOT NULL
);

CREATE SEQUENCE address_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE addresses (
  id                  INTEGER DEFAULT NEXTVAL('address_sequence') PRIMARY KEY,
  street1             VARCHAR(100) DEFAULT '',
  street2             VARCHAR(100) DEFAULT '',
  zip                 VARCHAR(100) DEFAULT '',
  city                VARCHAR(100) DEFAULT '',
  region              VARCHAR(100) DEFAULT '',
  country_id          INTEGER NOT NULL,
  modified            TIMESTAMP DEFAULT NOW(),
  created             TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE SET NULL
);

CREATE SEQUENCE user_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE users (
  id                  INTEGER DEFAULT NEXTVAL('user_sequence') PRIMARY KEY,
  username            VARCHAR(50),
  password            VARCHAR(128),
  created             TIMESTAMP DEFAULT NOW()
);
CREATE INDEX user_id ON users (id);

CREATE SEQUENCE session_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE sessions (
  id              INTEGER DEFAULT NEXTVAL('session_sequence') PRIMARY KEY,
  session_key     VARCHAR(128),
  user_id         INTEGER,
  created         TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE SEQUENCE grouptype_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE grouptypes (
  id              INTEGER DEFAULT NEXTVAL('grouptype_sequence') PRIMARY KEY,
  grouptype       VARCHAR(50)
);
--CREATE INDEX grouptype_id ON grouptypes(id);

CREATE SEQUENCE group_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE groups (
  id              INTEGER DEFAULT NEXTVAL('group_sequence') PRIMARY KEY,
  grouptype_id    INTEGER,
  groupname       VARCHAR(50),
  FOREIGN KEY (grouptype_id) REFERENCES grouptypes(id)
);

CREATE TABLE permissions (
  id              INTEGER PRIMARY KEY,
  permission      VARCHAR(50)
);

CREATE SEQUENCE role_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE roles (
  id              INTEGER DEFAULT NEXTVAL('role_sequence') PRIMARY KEY,
  role            VARCHAR(50)
);

CREATE TABLE permission_to_grouptype (
  permission_id   INTEGER,
  grouptype_id    INTEGER,
  FOREIGN KEY (permission_id) REFERENCES permissions(id),
  FOREIGN KEY (grouptype_id) REFERENCES grouptypes(id),
  UNIQUE (permission_id, grouptype_id)
);

CREATE TABLE permission_to_role (
  permission_id   INTEGER,
  role_id         INTEGER,
  FOREIGN KEY (permission_id) REFERENCES permissions(id),
  FOREIGN KEY (role_id) REFERENCES roles(id),
  UNIQUE (permission_id,role_id)
);

CREATE TABLE role_to_group (
  role_id         INTEGER,
  group_id        INTEGER,
  FOREIGN KEY (role_id) REFERENCES roles(id),
  FOREIGN KEY (group_id) REFERENCES groups(id),
  PRIMARY KEY (role_id, group_id)
);

CREATE TABLE user_to_group (
  user_id         INTEGER,
  group_id        INTEGER,
  role_id         INTEGER,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (group_id) REFERENCES groups(id),
  FOREIGN KEY (role_id) REFERENCES roles(id),
  PRIMARY KEY (user_id, group_id)
);

CREATE SEQUENCE history_sequence INCREMENT BY 1 START WITH 1;
CREATE TABLE history (
  id              INTEGER DEFAULT NEXTVAL('history_sequence') PRIMARY KEY,
  user_id         INTEGER NOT NULL,
  group_id        INTEGER NOT NULL,
  tablename       varchar(50) NOT NULL,
  record_id       INTEGER NOT NULL,
  fields          BYTEA,
  changed         TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (group_id) REFERENCES groups(id)
);