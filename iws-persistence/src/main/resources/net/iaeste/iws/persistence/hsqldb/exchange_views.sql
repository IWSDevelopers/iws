-- =============================================================================
-- Please add all Exchange related views here
-- =============================================================================
CREATE VIEW employer_information AS
SELECT id, employer_name, changed_on
FROM offers o
INNER JOIN (
  SELECT distinct (employer_name) employer_name, max(changed_on) changed_on
  FROM offers
  GROUP BY employer_name
) AS t2 ON o.employer_name = t2.employer_name AND o.changed_on = t2.changed_ona;