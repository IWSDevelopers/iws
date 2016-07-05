-- Before being able to update, we need to know who to grant ownership of the
-- database views, i.e. the primary IWS user.

-- This is the 1.2.0 release script to update the database to newest state.
-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the next DB version, and the IWS
-- version it is related to.
-- Commented out, as the current updates will not change the data model, only
-- correct some problems discovered with the Views & Data Content
insert into versions (db_version, iws_version) values (9, '1.2.0');


-- =============================================================================
-- Issue #2: Storage
-- =============================================================================
alter table users alter column private_date set default 'PROTECTED';
update grouptypes set folder_type = 'PROTECTED' where folder_type = 'PRIVATE';


-- =============================================================================
-- Issue #4 EULA corrections
-- =============================================================================
-- Considering the importance of Data Protection, it is necessary to frequently
-- update the EULA to reflect changes.
alter table users drop constraint user_notnull_eula_version;
alter table users drop column eula_version;
alter table users add column eula_version varchar(50) default '';
alter table users add constraint user_notnull_eula_version check (eula_version is not null);
update users set eula_version = '';


-- =============================================================================
-- Issue #6: Process Role
-- =============================================================================
insert into permissions (id, permission, restricted) values (205, 'PROCESS_ROLE', false);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 205);
insert into permission_to_role (role_id, permission_id) values (1, 205);
insert into permission_to_role (role_id, permission_id) values (2, 205);
update permissions set restricted = false where id in (101, 111, 121, 202);


-- =============================================================================
-- Issue #11 Sequence Correction for Student Applications
-- =============================================================================
-- The Student Application Entity was incorrectly set to use the Offer Sequence,
-- which means that the changes to trust the DB identity generation will cause
-- a problem for the existing sequence as it was never used.
--   The following correction cannot be applied directly, but must be checked
-- against the latest information from the database, as an alter sequence
-- doesn't allow sub queries.

-- First the query to read the latest value to be used.
select max(id) + 1 as restart_value from student_applications;

-- The input from this, must be taken and used in the following, the default is
-- the value which was present at the latest available snapshot (2016-05-30):
alter sequence student_application_sequence restart with 52607;
-- =============================================================================


-- =============================================================================
-- Issue #18: Resolving Language & Language Level information
-- =============================================================================
-- There is a problem with some of the Language information for Offers in the
-- Database, below is the example of Offers where the information is set for the
-- third but not second, which must be corrected:
-- iws=> select ref_no, language_1, language_1_level, language_1_op, language_2, language_2_level, language_2_op, language_3, language_3_level from offers where language_2 is null and language_3 is not null;
--      ref_no     | language_1 | language_1_level | language_1_op | language_2 | language_2_level | language_2_op | language_3 | language_3_level
-- ----------------+------------+------------------+---------------+------------+------------------+---------------+------------+------------------
--  SI-2010-A00001 | ENGLISH    | E                | A             |            | F                | A             | ENGLISH    | F
--  CZ-2010-A00085 | ENGLISH    | G                | O             |            | F                | A             | ENGLISH    | E
--  CZ-2011-A00027 | ENGLISH    | G                | O             |            | F                | A             | ENGLISH    | E
--  KZ-2009-A00001 | ENGLISH    | E                | A             |            | G                | A             | ENGLISH    | F
--  KZ-2014-A00143 | ENGLISH    | G                | A             |            | F                | A             | ENGLISH    | F
--  TN-2014-A00625 | ENGLISH    | G                | A             |            |                  | A             | ENGLISH    | G
--  DE-2012-A03544 | ENGLISH    | G                | A             |            | G                | O             | ENGLISH    | G
--  DE-2012-A03545 | ENGLISH    | G                | A             |            | G                | O             | ENGLISH    | G
--  SI-2010-A00016 | ENGLISH    | E                | A             |            | F                | A             | ENGLISH    | E
--  CZ-2013-A00283 | ENGLISH    | G                | A             |            |                  | A             | ENGLISH    | G
--  SI-2010-A00015 | ENGLISH    | E                | A             |            | F                | A             | ENGLISH    | E
--  CZ-2010-A00060 | ENGLISH    | G                | O             |            | F                | A             | ENGLISH    | G
--  CZ-2010-A00108 | ENGLISH    | G                | A             |            | F                | A             | ENGLISH    | E
--  CZ-2011-A00097 | ENGLISH    | G                | O             |            | F                | A             | ENGLISH    | G
--  CZ-2011-A00084 | ENGLISH    | G                | O             |            | F                | A             | ENGLISH    | F
--  CZ-2012-A00189 | ENGLISH    | G                | O             |            | F                | A             | ENGLISH    | E
update offers set
  language_1_op = null,
  language_2 = null,
  language_2_level = null,
  language_2_op = null,
  language_3 = null,
  language_3_level = null
where language_2 is null and language_3 is not null;
update offers set language_1_level = 'E' where ref_no in ('CZ-2010-A00085', 'CZ-2011-A00027', 'CZ-2010-A00108', 'CZ-2012-A00189');

-- Now to correct the general errors, according to the database (2016-02-29),
-- we have around 25.000 Offers where the Language 2 & 3 flags have been set
-- but the Language is missing:
-- iws=> select count(id) from offers where language_2_level is not null and language_2 is null;
-- => 24877
-- iws=> select count(id) from offers where language_3_level is not null and language_3 is null;
-- => 25239
update offers set language_1_op = null, language_2_level = null where language_2 is null;
update offers set language_2_op = null, language_3_level = null where language_3 is null;

-- With the above corrections, the data in the database should no longer contain
-- invalid information.
-- =============================================================================


-- =============================================================================
-- Issue #19: Resolving the WorkType issue for Offers
-- =============================================================================
-- The WorkType field for Offers was deliberately not enforced for Offers, as it
-- was not correctly handled in IW4. However, it has to be mandatory, as it
-- states what kind of work can be expected. It is enforced for CSV uploads, and
-- the rule must therefore also be present for API uploads. The database has the
-- following types defined (2016-02-29):
--
-- iws=# select count(id), work_type from offers group by work_type;
--  count | work_type
-- -------+-----------
--   1061 |
--   1750 | F
--  23395 | R
--  19072 | O
--
-- When the data was migrated over from IW3, a migrator was written, which is
-- pasted in below:
--
-- /**
--  * IW3 contains several fields where the information about work type is
--  * divided. In IWS, there's a single entry. So the question is how to map it
--  * over, since there can be several types marked for the IW3 records.<br />
--  *   The following query will yield the result from below, using the
--  * Mid-November snapshot of the IW3 database, that Henrik made.
--  * <pre>
--  *   select
--  *     count(offerid) as records,
--  *     worktype,
--  *     worktype_n,
--  *     worktype_p,
--  *     worktype_r,
--  *     worktype_w
--  *   from offers
--  *   group by
--  *     worktype,
--  *     worktype_n,
--  *     worktype_p,
--  *     worktype_r,
--  *     worktype_w;
--  * </pre>
--  * <br />
--  * <pre>
--  * records   worktype   worktype_n   worktype_p   worktype_r   worktype_w
--  *    9311          x     false        true         false        false
--  *       5          x     true         true         true         true
--  *    1260          x     false        true         true         false
--  *       4          x     true         true         false        true
--  *     780          x     false        false        false        false
--  *   12022          x     false        false        true         false
--  *      11          x     true         false        false        true
--  *     581          x     false        true         false        true
--  *       5          x     true         true         true         false
--  *      93          x     false        true         true         true
--  *      10          x     true         true         false        false
--  *     679          x     false        false        false        true
--  *       7          x     true         false        true         false
--  *     335          x     true         false        false        false
--  *     232          x     false        false        true         true
--  * </pre>
--  *
--  * @param oldOffer IW3 Offer
--  * @return Type of Work
--  */
-- private static TypeOfWork convertTypeOfWork(final IW3OffersEntity oldOffer) {
--     final TypeOfWork result;
--
--     if (oldOffer.getWorktypeR()) {
--         result = TypeOfWork.R;
--     } else if (oldOffer.getWorktypeP()) {
--         result = TypeOfWork.O;
--     } else if (oldOffer.getWorktypeW()) {
--         result = TypeOfWork.F;
--     } else {
--         result = TypeOfWork.O;
--     }
--
--     return result;
-- }
--
-- It is clear that the Offers lacking the WorkType must have it set, but what
-- is not clear is to what. For the IW3 Migrator, we used 'O' (Office Work) as
-- the default, and unless a different value is preferred, the same will be used
-- for IWS.
update offers set work_type = 'O' where work_type is null;
-- =============================================================================

-- =============================================================================
-- Various Corrections...
-- =============================================================================
delete from history where group_id = 391;
delete from user_to_group where group_id = 391;
delete from groups where id = 391;
update users set status = 'ACTIVE' where id = 35;
insert into user_to_group (id, external_id, user_id, group_id, role_id) values (35, '1787d15c-26c1-4ef1-8640-8a2dfdcf2063', 35, 463, 1);
update user_to_group set role_id = 2 where id = 10990;
delete from user_to_group where id = 10997;
delete from user_to_group where id = 10993;
insert into user_to_group (id, external_id, user_id, group_id, role_id) values (2288, '0f02f6b1-14f3-42f4-aee1-1b4febd20c4c', 35, 1, 2);
insert into user_to_group (id, external_id, user_id, group_id, role_id) values (5992, 'a867bf8f-2fc5-49eb-a79d-1a02761e74bf', 35, 2, 1);
insert into user_to_group (external_id, user_id, group_id, role_id) values
  ('d92ed883-d0da-49f9-b4c3-3d3688ff6422', 3249, 2, 3),
  ('ab9b92bd-fe02-4127-97b4-12ed621a82dc', 2581, 2, 3),
  ('2d3cec20-3594-4f3c-97ba-7cc1858d15f8', 3181, 2, 3),
  ('be4a4361-e8cf-47a0-8ed6-bf126094212b', 2994, 2, 3),
  ('fceda0d3-cd61-4339-a36f-4dce5f803712', 1598, 2, 3);
insert into aliases (external_id, group_id, alias_address) values ('877d4640-a28a-4e85-8435-56fbabaca61b', 5, 'idt');
alter table user_to_group drop constraint u2g_unique_session_key;
alter table user_to_group add constraint u2g_unique_user_group  unique (user_id, group_id);
drop view view_users;
alter table users add column tmp_salt varchar(36);
update users set tmp_salt = salt;
alter table users drop column salt;
alter table users add column salt varchar(128);
update users set salt = tmp_salt;
alter table users drop column tmp_salt;

-- =============================================================================
-- List User Information
-- =============================================================================
create view view_users as
  select
    u.id                                                                              as id,
    u.external_id                                                                     as external_id,
    u.firstname                                                                       as firstname,
    u.lastname                                                                        as lastname,
    u.username                                                                        as username,
    c.nationality                                                                     as nationality,
    case when u.salt in ('undefined', 'heartbleed') then u.salt else 'defined' end    as password,
    u.created::date                                                                   as created,
    case when max(s.created) is null then 'never' else max(s.created)::date::text end as last_login
  from
    countries c,
    persons p,
    users u
    left join sessions s on u.id = s.user_id
  where p.id = u.person_id
    and c.id = p.nationality
  group by
    u.id,
    u.external_id,
    u.firstname,
    u.lastname,
    u.username,
    c.nationality,
    u.salt,
    u.created;
