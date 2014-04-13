-- ============================================================================
-- Preparing to create test data for users and groups.
-- ============================================================================

create extension if not exists "uuid-ossp";

-- uncomment queries below if you want to use this script without running 90-initial-test-data.sql (or you wish to discard data from script 90)
-- /*
delete from user_to_group;
delete from sessions;
delete from history;
delete from students;
delete from users;
delete from groups where id>= 10;
delete from countries;
alter sequence country_sequence restart with 62;
alter sequence group_sequence restart with 132;
alter sequence user_sequence restart with 62;

insert into countries (country_code, country_name, country_name_full, currency, member_since, membership) values ('PL', 'Poland', 'Poland', 'PLN', 1960, 'FULL_MEMBER');
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name) values ('70e293c2-7142-4db2-91a7-cc0bf8fe5f65', 2, null, 62, 'Poland'), ('dcdf3f10-3a51-4970-933e-93b01a70b0b1', 4, 132, 62, 'Poland');
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values ('c929b28f-6590-4296-a9cc-6c5f7ead8744', 'ACTIVE', 'poland@iaeste.pl', 'NS.Poland@iaeste.org', 'fe1ade81bf65e13fa965f65ae1e012fcaa059621d70391e07f9c011846ec6699', 'c26b14f3-ca89-4007-b103-e810caf30378', 'NS', 'Poland');
insert into user_to_group (external_id, user_id, group_id, role_id) values ('8531065d-d2b8-44d4-b635-f3e5f8e6eaa1', 62, 132, 1), ('df642030-be60-4402-be85-9c4dad60bfed', 62, 133, 1);
-- */

-- starting sequence where they finished in script 90
alter sequence country_sequence restart with 88;
alter sequence group_sequence restart with 182;
alter sequence user_sequence restart with 88;

-- User1, NS (Owner Staff & Member Groups)
-- User2, Staff member (Moderator Staff & Member Groups)
-- User3, LC (Owner LC Group, Moderator Member Group)
-- User4, LC (Member LC, & Member Group)
-- User5, Student (Member Student & Member of Member Group)
-- Additionally, add a Workgroup under the Staff, Member group & LC. Where user 1-4 are present in all three.

insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name, full_name) values
  (uuid_generate_v4(), 5, 132, 62, 'PolandKrakow', 'KN KL IAESTE AGH Kraków'), -- id=182

  (uuid_generate_v4(), 6, 132, 62, 'PolandFR', 'Poland National Fund Raising'),
  (uuid_generate_v4(), 6, 133, 62, 'PolandExchange', 'Poland Exchange Coordinators'),
  (uuid_generate_v4(), 6, 182, 62, 'PolandKrakowIT', 'AGH Krakow IT'),

  (uuid_generate_v4(), 7, 182, 62, 'PolandKrakowStudents', 'Students AGH Krakow');

-- Polish Test users, the NS is defined in the initial-test-data script
insert into users (external_id, status, username, alias, password, salt, firstname, lastname) values
  (uuid_generate_v4(), 'ACTIVE', 'moderator@iaeste.pl', 'moderator.poland@iaeste.org', '', '', 'moderator', 'Poland'), -- id=88
  (uuid_generate_v4(), 'ACTIVE', 'owner@krakow.iaeste.pl', 'owner.krakow@iaeste.org', '', '', 'owner', 'Krakow'),
  (uuid_generate_v4(), 'ACTIVE', 'member@krakow.iaeste.pl', 'member.krakow@iaeste.org', '', '', 'member', 'Krakow'),
  (uuid_generate_v4(), 'ACTIVE', 'student@krakow.iaeste.pl', 'student.krakow@iaeste.org', '', '', 'member', 'Krakow');

update users set password = 'fe1ade81bf65e13fa965f65ae1e012fcaa059621d70391e07f9c011846ec6699', salt = 'c26b14f3-ca89-4007-b103-e810caf30378' where id >= 88;

insert into students (user_id) values (90);

insert into user_to_group (external_id, user_id, group_id, role_id) values
-- NS
  (uuid_generate_v4(), 62, 183, 3),
  (uuid_generate_v4(), 62, 184, 1),
  (uuid_generate_v4(), 62, 185, 3), -- can NS be a member of LC workgroup if he is not a member of LC?
-- staff member
  (uuid_generate_v4(), 88, 132, 2),
  (uuid_generate_v4(), 88, 133, 2),

  (uuid_generate_v4(), 88, 183, 1),
  (uuid_generate_v4(), 88, 184, 3),
  (uuid_generate_v4(), 88, 185, 3), -- can National Staff Member be a member of LC workgroup if he is not a member of LC?
-- LC Owner
  (uuid_generate_v4(), 89, 182, 1),
  (uuid_generate_v4(), 89, 132, 2),

  (uuid_generate_v4(), 89, 183, 3),
  (uuid_generate_v4(), 89, 184, 1),
  (uuid_generate_v4(), 89, 185, 3),
-- LC Member
  (uuid_generate_v4(), 90, 132, 3),
  (uuid_generate_v4(), 90, 182, 3),

  (uuid_generate_v4(), 90, 183, 3),
  (uuid_generate_v4(), 90, 184, 3),
  (uuid_generate_v4(), 90, 185, 1),
-- Student
  (uuid_generate_v4(), 91, 132, 5),
  (uuid_generate_v4(), 91, 186, 5), -- should it be role student or member in student group?
  (uuid_generate_v4(), 91, 182, 5); -- can student be a student in LC?

-- create private groups for all users
insert into Groups (external_id, grouptype_id, parent_id, country_id, group_name, full_name)
  select uuid_generate_v4(), 1, null, 62, username, username
  from users
  where id >= 88 or id = 62;

insert into user_to_group (external_id, user_id, group_id, role_id)
  select uuid_generate_v4(), u.id, g.id, 1
  from users as u
  join groups as g on u.username = g.group_name and grouptype_id = 1
  where u.id >= 88 or u.id = 62;

-- select * from view_user_group order by username, groupname;
