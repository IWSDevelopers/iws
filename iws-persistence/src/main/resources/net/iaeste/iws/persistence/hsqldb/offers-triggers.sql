CREATE TRIGGER offers_frequency_dependency_on_insert BEFORE INSERT ON offers
REFERENCING NEW AS newrow FOR EACH ROW
  WHEN((newrow.lodging_cost IS NOT NULL AND newrow.lodging_cost_frequency IS NULL)
    OR (newrow.living_cost IS NOT NULL AND newrow.living_cost_frequency IS NULL)
    OR (newrow.payment IS NOT NULL AND newrow.payment_frequency IS NULL))
    SIGNAL SQLSTATE '45000';

CREATE TRIGGER offers_frequency_dependency_on_update BEFORE UPDATE ON offers
REFERENCING NEW AS newrow OLD AS oldrow FOR EACH ROW
  WHEN((newrow.lodging_cost IS NOT NULL AND newrow.lodging_cost_frequency IS NULL)
    OR (newrow.living_cost IS NOT NULL AND newrow.living_cost_frequency IS NULL)
    OR (newrow.payment IS NOT NULL AND newrow.payment_frequency IS NULL))
    SIGNAL SQLSTATE '45000';

-- CREATE TRIGGER study_level_at_least_one BEFORE INSERT ON offers
-- REFERENCING NEW AS newrow FOR EACH ROW
--   WHEN ((SELECT COUNT(*) FROM study_levels WHERE offer_id = newrow.id) = 0)
--     SIGNAL SQLSTATE '45000';