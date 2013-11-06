-- =============================================================================
-- Please add the initialization of all Exchange related tables here
-- =============================================================================


-- =============================================================================
-- Employers
-- -----------------------------------------------------------------------------
-- This table contain the information about an employer. The main idea is that
-- an employer is fairly static, so the name combined with the GroupId must be
-- unique.
--   All static employer information is listed here, and removed from the actual
-- Offer table, to make for a cleaner and more normalized design.
-- =============================================================================
create sequence employer_sequence start with 1 increment by 1;
create table employers (
    id                        integer default nextval('employer_sequence'),
    external_id               varchar(36),
    group_id                  integer,
    name                      varchar(255),
    department                varchar(255),
    business                  varchar(255),
    working_place             varchar(255),
    address_id                integer,
    number_of_employees       integer,
    website                   varchar(255),
    canteen                   boolean,
    nearest_airport           varchar(255),
    nearest_public_transport  varchar(255),
    weekly_hours              decimal(5,3),
    daily_hours               decimal(5,3),
    modified                  timestamp default now(),
    created                   timestamp default now(),

    /* Primary & Foreign Keys */
    constraint employer_pk            primary key (id),
    constraint employer_fk_group_id   foreign key (group_id) references groups (id),
    constraint employer_fk_address_id foreign key (address_id) references addresses (id),

    /* Unique Constraints */
    constraint employer_unique_external_id unique (external_id),
    -- Note, it would be good to have a unique constraint for the employer. The
    -- sooner it is added, the sooner we'll avoid problems
    constraint employer_unique_fields      unique (group_id, name, department, working_place),

    /* Not Null Constraints */
    constraint employer_notnull_id          check (id is not null),
    constraint employer_notnull_external_id check (external_id is not null),
    constraint employer_notnull_name        check (name is not null),
    constraint employer_notnull_weeklyhours check (weekly_hours is not null),
    constraint employer_notnull_modified    check (modified is not null),
    constraint employer_notnull_created     check (created is not null)
);


-- =============================================================================
-- Offers
-- -----------------------------------------------------------------------------
-- Description of Table
-- =============================================================================
create sequence offer_sequence start with 100 increment by 1;
create table offers (
    id                        integer default nextval('offer_sequence'),
    external_id               varchar(36),
    group_id                  integer,
    ref_no                    varchar(16),
    -- General Work Description
    employer_id               integer,
    work_description          varchar(1000),
    work_type                 char(1),
    study_levels              varchar(25),
    study_fields              varchar(1000),
    specializations           varchar(1000),
    prev_training_req         boolean,
    other_requirements        varchar(4000),
    -- Period for the Offer
    min_weeks                 integer,
    max_weeks                 integer,
    from_date                 date,
    to_date                   date,
    from_date_2               date,
    to_date_2                 date,
    unavailable_from          date,
    unavailable_to            date,
    -- Language restrictions
    language_1                varchar(255),
    language_1_level          varchar(1),
    language_1_op             varchar(1),
    language_2                varchar(255),
    language_2_level          varchar(1),
    language_2_op             varchar(1),
    language_3                varchar(255),
    language_3_level          varchar(1),
    -- Payment & Cost information
    payment                   decimal(12,2),
    payment_frequency         varchar(10),
    -- Currency is general information, which belongs in the Employer Object
    currency                  varchar(3),
    -- Deduction is general information, which belongs in the Employer Object
    deduction                 varchar(20),
    living_cost               decimal(12,2),
    living_cost_frequency     varchar(10),
    lodging_by                varchar(255),
    lodging_cost              decimal(12,2),
    lodging_cost_frequency    varchar(10),
    -- Other things
    nomination_deadline       date,
    number_of_hard_copies     integer,
    additional_information    varchar(1000),
    status                    varchar(10),
    modified                  timestamp default now(),
    created                   timestamp default now(),

    /* Primary & Foreign Keys */
    constraint offer_pk             primary key (id),
    constraint offer_fk_group_id    foreign key (group_id) references groups (id),
    constraint offer_fk_employer_id foreign key (employer_id) references employers (id),

    /* Unique Constraints */
    constraint offer_unique_external_id unique (external_id),
    constraint offer_unique_ref_no      unique (ref_no),

    /* Not Null Constraints */
    constraint offer_notnull_id               check (id is not null),
    constraint offer_notnull_ref_no           check (ref_no is not null),
    constraint offer_notnull_external_id      check (external_id is not null),
    constraint offer_notnull_group_id         check (group_id is not null),
    constraint offer_notnull_from_date        check (from_date is not null),
    constraint offer_notnull_to_date          check (to_date is not null),
    constraint offer_notnull_language_1       check (language_1 is not null),
    constraint offer_notnull_language_1_level check (language_1_level is not null),
    constraint offer_notnull_min_weeks        check (min_weeks is not null),
    constraint offer_notnull_max_weeks        check (max_weeks is not null),
    constraint offer_notnull_work_description check (work_description is not null),
    constraint offer_notnull_study_levels     check (study_levels is not null),
    constraint offer_notnull_study_fields     check (study_fields is not null),
    constraint offer_notnull_modified         check (modified is not null),
    constraint offer_notnull_created          check (created is not null)
);


-- =============================================================================
-- Offer to Group
-- -----------------------------------------------------------------------------
-- Description of Table
-- =============================================================================
create sequence offer_to_group_sequence start with 50 increment by 1;
create table offer_to_group (
    id                 integer default nextval('offer_to_group_sequence'),
    external_id        varchar(36),
    offer_id           integer,
    group_id           integer,
    comment            varchar(100)  default '',
    modified           timestamp default now(),
    modified_by        integer,
    created            timestamp default now(),
    created_by         integer,

    /* Primary & Foreign Keys */
    constraint offer_to_group_pk              primary key (id),
    constraint offer_to_group_fk_offer_id     foreign key (offer_id) references offers (id) ON DELETE CASCADE,
    constraint offer_to_group_fk_group_id     foreign key (group_id) references groups (id) ON DELETE CASCADE,
    constraint offer_to_group_fk_modified_by  foreign key (modified_by) references users (id) ON DELETE SET NULL,
    constraint offer_to_group_fk_created_by   foreign key (created_by) references users (id) ON DELETE SET NULL,

    /* Unique Constraints */
    constraint offer_to_group_unique_external_id unique (external_id),

    /* Not Null Constraints */
    constraint offer_to_group_notnull_id          check (id is not null),
    constraint offer_to_group_notnull_external_id check (external_id is not null),
    constraint offer_to_group_notnull_offer_id    check (offer_id is not null),
    constraint offer_to_group_notnull_group_id    check (group_id is not null),
    constraint offer_to_group_notnull_modified    check (modified is not null),
    constraint offer_to_group_notnull_created     check (created is not null)
);


-- =============================================================================
-- Students
-- -----------------------------------------------------------------------------
-- This table contains the additional information for students, which is neither
-- present in the User, nor Person records. The information here, is relevant
-- for students, for applying and searching.
--   A Student is linked one-to-one to the User table, meaning that if a user
-- wishes to apply for an Offer, then the user must be part of the country's
-- Student Group, and have a Student Object associated.
-- =============================================================================
create sequence student_sequence start with 1 increment by 1;
create table students (
    id                  integer default nextval('student_sequence'),
    user_id             integer,
    study_level         varchar(25),
    study_fields        varchar(1000),
    specializations     varchar(1000),
    available_from      date,
    available_to        date,
    language_1          varchar(255),
    language_1_level    varchar(1),
    language_2          varchar(255),
    language_2_level    varchar(1),
    language_3          varchar(255),
    language_3_level    varchar(1),
    modified            timestamp default now(),
    created             timestamp default now(),

    /* Primary & Foreign Keys */
    constraint student_pk          primary key (id),
    constraint student_fk_user_id foreign key (user_id) references users (id),

    /* Not Null Constraints */
    constraint student_notnull_id           check (id is not null),
    constraint student_notnull_user_id      check (user_id is not null),
    constraint student_notnull_modified     check (modified is not null),
    constraint student_notnull_created      check (created is not null)
);


-- =============================================================================
-- Student Applications
-- -----------------------------------------------------------------------------
-- Description of Table
-- =============================================================================
create sequence student_application_sequence start with 1 increment by 1;
create table student_applications (
    id                        integer default nextval('student_application_sequence'),
    external_id               varchar(36),
    offer_id                  integer,
    student_id                integer,
    status                    varchar(25),
    modified                  timestamp default now(),
    created                   timestamp default now(),

    /* Primary & Foreign Keys */
    constraint student_application_pk             primary key (id),
    constraint student_application_fk_offer_id    foreign key (offer_id) references offers (id) ON DELETE CASCADE,
    constraint student_application_fk_student_id  foreign key (student_id) references users (id),

    /* Unique Constraints */
    constraint student_application_unique_external_id unique (external_id),

    /* Not Null Constraints */
    constraint student_application_notnull_id               check (id is not null),
    constraint student_application_notnull_external_id      check (external_id is not null),
    constraint student_application_notnull_offer_id         check (offer_id is not null),
    constraint student_application_notnull_student_id       check (student_id is not null),
    constraint student_application_notnull_modified         check (modified is not null),
    constraint student_application_notnull_created          check (created is not null)
);
