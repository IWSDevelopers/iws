-- =============================================================================
-- Please add all Exchange related views here
-- =============================================================================
CREATE VIEW employer_information AS
SELECT id, employer_name, group_id, modified
FROM offers o
INNER JOIN (
  SELECT distinct (employer_name) employer_name, max(modified) modified
  FROM offers
  GROUP BY employer_name
) AS t2 ON o.employer_name = t2.employer_name AND o.modified = t2.modified;