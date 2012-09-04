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