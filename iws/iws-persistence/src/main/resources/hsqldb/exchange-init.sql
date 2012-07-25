-- Please add the initialization of all Exchange related tables here
-- Note; The HSQLDB is for in-memory testing, hence it makes no sense to have
--  drop the tables and sequences first!
SET search_path to iws;

DROP SEQUENCE IF EXISTS address_sequence;
DROP SEQUENCE IF EXISTS employer_sequence;
DROP SEQUENCE IF EXISTS study_fields_sequence;
DROP SEQUENCE IF EXISTS offer_sequence;
DROP SEQUENCE IF EXISTS student_sequence;

DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS employers CASCADE;
DROP TABLE IF EXISTS study_field2group CASCADE;
DROP TABLE IF EXISTS study_fields CASCADE;
DROP TABLE IF EXISTS offer2group;
DROP TABLE IF EXISTS offers;
DROP TABLE IF EXISTS addresses;

CREATE SEQUENCE studentfiles_sequence START 1;
-- TODO: student_files table is now the same as in the current version. I will look a bit more at this if I should change something
CREATE TABLE student_files {
  id                INTEGER DEFAULT NextVal('studentfiles_sequence'::TEXT) NOT NULL PRIMARY KEY,
  studentid         INTEGER REFERENCES students(id) ON DELETE SET NULL,
  filetype          CHARACTER VARYING(1) DEFAULT 'f',
  filename          CHARACTER VARYING(100) NOT NULL,
  systemname        CHARACTER VARYING(100) DEFAULT '',
  filesize          INTEGER DEFAULT 0,
  folderid          INTEGER DEFAULT 1,
  mimetypeid        INTEGER DEFAULT 1,
  description       CHARACTER VARYING(250) DEFAULT '',
  keywords          CHARACTER VARYING(250) DEFAULT '',
  checksum          CHARACTER VARYING(32) DEFAULT '',
  modified          TIMESTAMP DEFAULT now(),
  created           TIMESTAMP DEFAULT now()
}

CREATE SEQUENCE student_sequence START 1;
CREATE TABLE students {
  id                INTEGER DEFAULT NextVal('student_sequence'::TEXT) NOT NULL PRIMARY KEY,
  firstname         TEXT NOT NULL,
  lastname          TEXT NOT NULL,
  addressid         INTEGER REFERENCES addresses(id) ON DELETE CASCADE,
  countryid         INTEGER NOT NULL,
  phone             TEXT,
  termaddressid     INTEGER REFERENCES addresses(id) ON DELETE CASCADE,
  termphone         TEXT,
  termcountryid     INTEGER NOT NULL,
  email             TEXT NOT NULL,
  alternativemail   TEXT,
  birthday          DATE,
  birthplace        TEXT,
  nationalityid     INTEGER,
  passportnumber    TEXT,
  passportissued    TEXT,
  passportvalidity  DATE,
  gender            character(1),
  maritalstatus     character(1),
  medicallyfit      character(1),
  university        TEXT,
  facultyid         INTEGER REFERENCES faculties(id) ON DELETE SET NULL, -- CHECK THIS AGAINST THE NEW ABOUT FACULTY
  specialization    TEXT,
  studycompleted    character(1),
  studyrequired     character(1),
  languages1id      INTEGER,
  languages2id      INTEGER,
  languages3id      INTEGER,
  fromdate          DATE,
  todate            DATE,
  requireloding     BOOLEAN,
  trainingreport    BOOLEAN,
  comment           TEXT,
  filepicture       INTEGER REFERENCES student_files(id) ON DELETE CASCADE,
  filecv            INTEGER REFERENCES student_files(id) ON DELETE CASCADE,
  filecover         INTEGER REFERENCES student_files(id) ON DELETE CASCADE,
  fileother         INTEGER REFERENCES student_files(id) ON DELETE CASCADE,
  status            CHARACTER(1),
  modified          TIMESTAMP WITHOUT TIME ZONE,
  modifiedby        INTEGER,
  created           TIMESTAMP WITHOUT TIME ZONE,
  createdby         INTEGER,
  logincode         TEXT,
  completed         BOOLEAN DEFAULT FALSE
}

CREATE SEQUENCE address_sequence START 1;
CREATE TABLE addresses (
    id                 INTEGER      DEFAULT NextVal('address_sequence'::TEXT) NOT NULL PRIMARY KEY,
    street1            TEXT         DEFAULT '',
    street2            TEXT         DEFAULT '',
    zip                TEXT         DEFAULT '',
    city               TEXT         DEFAULT '',
    country_id         INTEGER      NOT NULL REFERENCES countries (id) ON DELETE RESTRICT,
    modified           TIMESTAMP    DEFAULT now(),
    created            TIMESTAMP    DEFAULT now()
);

CREATE SEQUENCE employer_sequence START 1;
CREATE TABLE employers (
    id                     INTEGER      DEFAULT NextVal('employer_sequence'::TEXT) NOT NULL PRIMARY KEY,
    name                   TEXT         DEFAULT '',
    address_id             INTEGER      NOT NULL REFERENCES addresses (id) ON DELETE CASCADE ON UPDATE CASCADE,
    workplace              TEXT         DEFAULT '',
    website                TEXT         DEFAULT '',
    business               TEXT         DEFAULT '',
    responsible_person     TEXT         DEFAULT '',
    airport                TEXT         DEFAULT '',
    transport              TEXT         DEFAULT '',
    employees              TEXT         DEFAULT '',
    modified               TIMESTAMP    DEFAULT now(),
    created                TIMESTAMP    DEFAULT now()
);

CREATE SEQUENCE study_fields_sequence START 1;
CREATE TABLE study_fields (
    id                     INTEGER      DEFAULT NextVal('study_fields_sequence'::TEXT) NOT NULL PRIMARY KEY,
    modified               TIMESTAMP    DEFAULT now(),
    created                TIMESTAMP    DEFAULT now()
);

CREATE TABLE study_field2group (
    study_field_id     INTEGER NOT NULL REFERENCES study_fields (id) ON DELETE CASCADE,
    group_id           INTEGER NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
    support            VARCHAR(10) DEFAULT 'yes',
    modified           TIMESTAMP DEFAULT now(),
    created            TIMESTAMP DEFAULT now()
);

CREATE SEQUENCE offer_sequence  START 1;
CREATE TABLE offers (
    id                     INTEGER      DEFAULT NextVal('offer_sequence'::TEXT) NOT NULL PRIMARY KEY,
    group_id               INTEGER      NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
    system_ref_no          VARCHAR(10),
    local_ref_no           TEXT         NOT NULL,

    exchange_year          INTEGER, --diferences  between years?
    offer_year             INTEGER, --diferences  between years?
    is_archive             INTEGER      DEFAULT 0,
    deadline               DATE,
    expire                 DATE,

    employer_id            INTEGER      NOT NULL REFERENCES employers (id) ON DELETE CASCADE,
    hours_weekly           REAL,
    hours_daily            REAL,

    study_field_id         INTEGER      NOT NULL REFERENCES study_fields (id) ON DELETE CASCADE,
    faculty_other          TEXT         DEFAULT '',
    specialization         TEXT         DEFAULT '',
    study_completed        INTEGER, --VALUES From enum
    study_required         VARCHAR(1), --is it used? -> delete
    -- To be done --
    language1              TEXT         DEFAULT '',
    language1_level        INTEGER,
    language1or            BOOLEAN      DEFAULT false,
    language2              TEXT         DEFAULT '',
    language2_level        INTEGER,
    language2or            BOOLEAN      DEFAULT false,
    language3              TEXT         DEFAULT '',
    language3_level        INTEGER,
    ----------------
    other_requirements     TEXT,
    training_required      TEXT         DEFAULT '',
    gender                 VARCHAR(1), --enum

    work_kind              TEXT,
    work_type              INTEGER, --enum
    weeksmin               INTEGER      NOT NULL,
    weeksmax               INTEGER      NOT NULL,
    from_date              DATE,
    to_date                DATE,
    from_date2             DATE,
    to_date2               DATE,
    holidays_from          DATE,
    holidays_to            DATE,
    payment                INTEGER,
    payment_frequency      INTEGER, --enum
    deduction              TEXT         DEFAULT '',

    lodging                TEXT         DEFAULT '',
    lodging_cost           INTEGER,
    lodging_cost_frequency INTEGER, --enum
    living_cost            INTEGER,
    living_cost_frequency  INTEGER, --enum
    canteen                BOOLEAN      DEFAULT false,

    nomination_e           BOOLEAN      DEFAULT false, --to enum?
    nomination_h           BOOLEAN      DEFAULT false, --to enum?
    nomination_and_or      BOOLEAN      DEFAULT false, --to enum?
    no_hard_copies         INTEGER      NOT null,

    require_student        BOOLEAN      DEFAULT false,
    comment                TEXT,

    answered               TIMESTAMP,
    status                 INTEGER, --enum
    published              TIMESTAMP,
    published_by           INTEGER, --i'm not sure now whether this is reference to users TABLE, need have a look at the code afternoon
    modified               TIMESTAMP,
    modified_by            INTEGER, --i'm NOT sure now whether this IS reference To users TABLE, need have a look AT the Code afternoon
    created                TIMESTAMP,
    created_by             INTEGER, --i'm not sure now whether this is reference to users TABLE, need have a look at the code afternoon
    country_id             INTEGER NOT NULL REFERENCES countries (id) ON DELETE RESTRICT,
    student_id             INTEGER REFERENCES students (id) ON DELETE SET NULL
);

CREATE TABLE offer2group (
    offer_id           INTEGER    REFERENCES offers (id) ON DELETE CASCADE,
    group_id           INTEGER    REFERENCES groups (id) ON DELETE CASCADE,
    student_id         INTEGER    REFERENCES students (id) ON DELETE SET NULL, --TODO: duplicity - offers.student_id
    status             VARCHAR(1) DEFAULT 'n',
    visible            BOOLEAN    DEFAULT true,
    comment            TEXT       DEFAULT '',
    exchanged          BOOLEAN    DEFAULT false,
    answered_by        INTEGER    REFERENCES users (id), --ON DELETE ???,
    answered           TIMESTAMP,
    modified           TIMESTAMP,
    created_by         INTEGER    REFERENCES users (id), --ON DELETE ???
    created            TIMESTAMP,
    is_archived        BOOLEAN    DEFAULT false NOT NULL
);