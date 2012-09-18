-- Please add the initialization of all Exchange related tables here
-- Note; The HSQLDB is for in-memory testing, hence it makes no sense to have
--  drop the tables and sequences first!

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

    employer_name             varchar(255)  NOT NULL  CHECK(length(employer_name) > 0),
    employer_address          varchar(255),
    employer_address_2        varchar(255),
    employer_business         varchar(255),
    employer_employees_cnt    integer,
    employer_website          varchar(255),
    working_place             varchar(255),
    nearest_airport           varchar(255),
    nearest_pub_transport     varchar(255),

    hours_weekly           REAL,
    hours_daily            REAL,

    study_field_id         INTEGER      NOT NULL REFERENCES study_fields (id) ON DELETE CASCADE,
    faculty_other          TEXT         DEFAULT '',
    specialization         TEXT         DEFAULT '',
    study_completed        INTEGER, --VALUES From enum
    study_required         VARCHAR(1), --is it used? -> delete
    language1              TEXT         DEFAULT '',
    language1_level        INTEGER,
    language1or            BOOLEAN      DEFAULT false,
    language2              TEXT         DEFAULT '',
    language2_level        INTEGER,
    language2or            BOOLEAN      DEFAULT false,
    language3              TEXT         DEFAULT '',
    language3_level        INTEGER,
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