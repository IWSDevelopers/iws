-- This is the backlog script to make all the corrections to the system, which have been requested over the last 6 weeks :-(

-- First, we need to correct data garbage... The on_public_list and on_private_list must both be correctly set
-- Updating all user2group relations where the user is either owner,  moderator or member. Although it can be argued if someone should be on or not - we cannot tell for sure whether it is a mistake or not, that they are not present.
update user_to_group set on_private_list = true where role_id in (1,2, 3) and on_private_list = false;
-- Now, we updating international Groups. By default the public list is only for the owner, with one exception - the Board, where all members receive the mails
update user_to_group set on_public_list = false where role_id <> 1 and group_id in (4,6,7,9,12,3051);
-- Update all Students & Guest accounts to also not be present on the mailing lists.
update user_to_group set on_public_list = false where role_id > 3 and on_public_list = true;
update user_to_group set on_private_list = false where role_id > 3 and on_private_list = true;
commit;

-- Board decision, it must be possible to control who can write to a private mailing list. Hence, a new flag is added which by default is set to true for all
alter table user_to_group add write_to_private_list boolean default true;
alter table user_to_group add constraint u2g_notnull_write_private_list check (write_to_private_list is not null);
update user_to_group set write_to_private_list = on_private_list;
commit;

-- Ensure that all owners also are on the private list
update user_to_group set on_private_list = true where role_id = 1 and on_private_list = false;
commit;

-- 2014-06-18 by Bernard Bayens; That Dan Ewerts mail address should be updated
update users set username = 'dewert@culturalvistas.org' where id = 638;
commit;

-- 2014-06-02 by Seif: Hey guys, could we remove those two addresses from IAETSE Macedonia mailing lists: bojana_1603@hotmail.com and big_klaines@hotmail.com
-- bojana_1603@hotmail.com have id 1260
-- big_klaines@hotmail.com have id 980
-- The users are only present on the Macedonian member list
update user_to_group set on_private_list = false, on_public_list = false, modified = now() where id in (1260, 980);
commit;

-- 2014-06-03 by Jonny Milliken : Would you be able to create a new mailing list for the following people
--                                in accordance with the email chain below?
--                                  o UserId=2226; Jonny Milliken
--                                  o UserId=456;  Magdalena Kuźniar <magdalena.kuzniar@iaeste.pl>;
--                                  o UserId=2255; O'Kane, Lorna (British Council) <Lorna.O'Kane@britishcouncil.org>;
--                                  o UserId=60;   Karin Pankau <pankau@daad.de>;
--                                  o UserId=1556; 'Belkhiria Hamza Yazid' <yoshipepperoni@gmail.com>;
--                                  o UserId=2488; 'Sabine Lenz' <sabine.lenz@office.iaeste.ch>
--                                  o UserId=556;  Vanja Paulik vanja.paulik@iaeste.org
insert into groups (external_id, parent_id, grouptype_id, group_name, group_description, full_name, list_name, status) values ('278d4e1f-8845-4c3e-ab58-5268a515e94b', 1, 3, 'IWUG', 'IntraWeb Usability Group', 'IAESTE IntraWeb Usability Group', 'iwug', 'ACTIVE');
insert into user_to_group (external_id, user_id, group_id, role_id, on_public_list, on_private_list, write_to_private_list) values ('1d273202-b2bc-4885-af29-0063f7f4acdd', 2226, (select id from groups where external_id = '278d4e1f-8845-4c3e-ab58-5268a515e94b'), 1, true, true, true);
insert into user_to_group (external_id, user_id, group_id, role_id, on_public_list, on_private_list, write_to_private_list) values ('3503ac1f-1169-4c07-be78-23590216fcb5', 456, (select id from groups where external_id = '278d4e1f-8845-4c3e-ab58-5268a515e94b'), 3, true, true, true);
insert into user_to_group (external_id, user_id, group_id, role_id, on_public_list, on_private_list, write_to_private_list) values ('6e7ac36e-3686-4caf-8da0-7e689179b38a', 2255, (select id from groups where external_id = '278d4e1f-8845-4c3e-ab58-5268a515e94b'), 3, true, true, true);
insert into user_to_group (external_id, user_id, group_id, role_id, on_public_list, on_private_list, write_to_private_list) values ('a1237eb0-4509-4929-9acf-73848aa49534', 60, (select id from groups where external_id = '278d4e1f-8845-4c3e-ab58-5268a515e94b'), 3, true, true, true);
insert into user_to_group (external_id, user_id, group_id, role_id, on_public_list, on_private_list, write_to_private_list) values ('5d792be5-2564-43e5-9cd8-19e703b6a427', 1556, (select id from groups where external_id = '278d4e1f-8845-4c3e-ab58-5268a515e94b'), 3, true, true, true);
insert into user_to_group (external_id, user_id, group_id, role_id, on_public_list, on_private_list, write_to_private_list) values ('629a5bdd-95fe-4815-9b76-b3cde6392b50', 2488, (select id from groups where external_id = '278d4e1f-8845-4c3e-ab58-5268a515e94b'), 3, true, true, true);
insert into user_to_group (external_id, user_id, group_id, role_id, on_public_list, on_private_list, write_to_private_list) values ('ed59d9b6-4576-4f71-b440-dbaf11a0bd3a', 556, (select id from groups where external_id = '278d4e1f-8845-4c3e-ab58-5268a515e94b'), 3, true, true, true);
commit;

-- Mail from Olga on 2014-05-19
-- problematic address 	            Person 	        Correct address 	                Action
-- dewert@aipt.org 	                Dan Ewert 	    dewert@culturalvistas.org 	      replace problematic address with correct one
--   Comment, this above mentioned address doesn't exists in IW, Dan Ewert is registered with the correct address!
-- lorna.okane@britishcouncil.org 	Lorna O'Kane 	  Lorna.O'Kane@britishcouncil.org   replace problematic address with correct one
update users set username = 'Lorna.O''Kane@britishcouncil.org', alias = 'lorna.o''kane@iaeste.org', modified = now() where id = 2255;
-- beatrice.chu@polyu.edu.hk 	      Beatrice Chu 	  not applicable 	                  remove this  address from ncs mailing list (Beatrice doesn't work for IAESTE and university anymore)
update users set status = 'SUSPENDED', modified = now() where id = 1659;
-- felber@iaeste.at 	              Martin Felber 	not applicable 	                  remove this address from ncs mailing list (Mario is not anymore SID coordinator, and not active member of iAESTE Austria anymore)
update users set status = 'SUSPENDED', modified = now() where id = 253;
commit;

-- Board discussion on 2014-05-16
-- So for cooperating institutions I would suggest:
-- Country_FirstLettersofInstitutionName@iaeste.org,
-- Albania_ICESTE
-- Estonia_TUT
-- Bangladesh_CAT
-- Kenya_DeKut    --> Kenya_DKUT
update groups set group_name = 'Kenya DKUT', list_name = 'Kenya_DKUT', modified = now() where id = 421;
update groups set group_name = 'Kenya DKUT Staff', list_name = 'Kenya_DKUT', modified = now() where id = 422;
-- (the flu name is Bangladesh AF Management, so the name should be rather
--   Bangladesh_Afzal_Management --> Bangladesh_AFM@iaeste.org
update groups set group_name = 'Bangladesh AFM', list_name = 'Bangladesh_AFM', modified = now() where id = 2859;
update groups set group_name = 'Bangladesh AFM Staff', list_name = 'Bangladesh_AFM', modified = now() where id = 2860;
-- Bangladesh     | College_of_Aviation_Technology --->Bangladesh_CAT@iaeste.org
update groups set group_name = 'Bangladesh CAT', full_name = 'Bangladesh College of Aviation Technology Members', group_description = 'Bangladesh College of Aviation Technology', list_name = 'Bangladesh_CAT', modified = now() where id = 415;
update groups set group_name = 'Bangladesh CAT Staff', full_name = 'Bangladesh College of Aviation Technology Staff', group_description = 'Bangladesh College of Aviation Technology Staff', list_name = 'Bangladesh_CAT', modified = now() where id = 416;
-- Nepal CI---> Nepal_CI@iaeste.org
update groups set group_name = 'Nepal CI', full_name = 'Nepal CI Members', group_description = 'Nepal CI Members', list_name = 'Nepal_CI', modified = now() where id = 424;
update groups set group_name = 'Nepal CI Staff', full_name = 'Nepal CI Staff', group_description = 'Nepal CI Staff', list_name = 'Nepal_CI', modified = now() where id = 424;
commit;
