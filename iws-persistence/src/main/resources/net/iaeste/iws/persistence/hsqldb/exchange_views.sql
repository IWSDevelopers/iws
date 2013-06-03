-- =============================================================================
-- Please add all Exchange related views here
-- =============================================================================

-- The following view is error prone, and must be replaced. As the employers is
-- a vital part of the setup, the best approach would be to create the
-- appropriate table(s), and replace the view with something better.
--   If the view is attempted to be used in PostgreSQL, it will result in an
-- error!
CREATE VIEW employer_information AS
SELECT id, employer_name, group_id, modified
FROM offers o
INNER JOIN (
  SELECT distinct (employer_name) employer_name, max(modified) modified
  FROM offers
  GROUP BY employer_name
) AS t2 ON o.employer_name = t2.employer_name AND o.modified = t2.modified;
