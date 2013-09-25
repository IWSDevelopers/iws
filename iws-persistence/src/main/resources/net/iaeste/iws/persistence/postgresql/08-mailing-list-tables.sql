-- =============================================================================
-- Mailing lists
-- -----------------------------------------------------------------------------
-- table is here to simulate remote mailing list database
-- =============================================================================
create sequence mailing_list_sequence start with 1 increment by 1;
CREATE TABLE mailing_lists (
    id            integer default nextval('mailing_list_sequence'),
    external_id   varchar(36),
    private       boolean default true,
    list_address  varchar(100),
    active        boolean,
    created       timestamp default now(),
    modified      timestamp default now(),

    /* Primary & Foreign Keys */
    constraint mailing_lists_pk              primary key (id),

    /* Not Null Constraints */
    constraint mailing_lists_notnull_id                  check (id is not null),
    constraint mailing_lists_notnull_external_id         check (external_id is not null),
    constraint mailing_lists_notnull_private             check (private is not null),
    constraint mailing_lists_notnull_list_address        check (list_address is not null),
    constraint mailing_lists_notnull_list_active         check (active is not null),
    constraint mailing_lists_notnull_created             check (created is not null),
    constraint mailing_lists_notnull_modified            check (modified is not null)
);

-- =============================================================================
-- Mailing lists
-- -----------------------------------------------------------------------------
-- table is here to simulate remote mailing list database
-- =============================================================================
create sequence mailing_list_membership_sequence start with 1 increment by 1;
CREATE TABLE mailing_list_membership (
    id               integer default nextval('mailing_list_membership_sequence'),
    mailing_list_id  integer,
    member           varchar(100),
    created          timestamp default now(),

    /* Primary & Foreign Keys */
    constraint mailing_list_membership_pk                            primary key (id),
    constraint mailing_list_membership_unique_mailing_list_id_member unique (mailing_list_id, member),
    constraint mailing_list_membership_fk_mailing_list_id            foreign key (mailing_list_id) references mailing_lists (id) on delete cascade,

    /* Not Null Constraints */
    constraint mailing_list_membership_notnull_id                  check (id is not null),
    constraint mailing_list_membership_notnull_mailing_list_id     check (mailing_list_id is not null),
    constraint mailing_list_membership_notnull_list_member         check (member is not null),
    constraint mailing_list_membership_notnull_created             check (created is not null)
);