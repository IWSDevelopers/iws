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
-- the value which was present at the latest available snapshot (2016-02-29):
alter sequence student_application_sequence restart with 52553;
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
-- is not clear is to what. For the IW3 migrator, we used 'O' (Office Work) as
-- the default, and unless a different value is preferred, the same will be used
-- for IWS.
update offers set work_type = 'O' where work_type is null;
-- =============================================================================
