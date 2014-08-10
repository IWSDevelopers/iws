-- This is the 1.1 release script to update the database to newest state.

-- The changes makes the system incompatible with earlier versions of the IWS,
-- so let's add the information that this is the second DB version, and the IWS
-- version it is related to.
insert into versions (db_version, iws_version) values (2, '1.1.0');

-- Trac #668 & #734 Moving Work Hours from Employer to Offer
alter table offers add work_days_per_week float;
alter table offers add weekly_hours float;
alter table offers add daily_hours float;
-- Updating existing records based on the Employer Information
update offers set weekly_hours = e.weekly_hours from employers e where e.id = employer_id;
update offers set daily_hours = e.daily_hours from employers e where e.id = employer_id;
-- The new field is not yet populated, so we're using 5 as default
update offers set work_days_per_week = 5;
-- Add new Unique Constraint
alter table offers add constraint offer_notnull_weeklyhours check (weekly_hours is not null);
alter table employers drop column weekly_hours;
alter table employers drop column daily_hours;

-- Now, we just have to drop the former fields :-)
-- First, we need to re-create all views using them
-- Then we can drop the columns
