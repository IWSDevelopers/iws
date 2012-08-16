insert into permissions (id, permission) values (1, 'Cast Spells');
insert into permissions (id, permission) values (2, 'Bow & Arrow');
insert into permissions (id, permission) values (3, 'Axe');
insert into permissions (id, permission) values (4, 'Swords');
insert into permissions (id, permission) values (5, 'Daggers');
insert into permissions (id, permission) values (6, 'Horse Riding');
insert into permissions (id, permission) values (7, 'Spears');

insert into grouptypes (id, grouptype) values (1, 'Half Size');
insert into grouptypes (id, grouptype) values (2, 'Full Size');
insert into grouptypes (id, grouptype) values (3, 'Wizardry');
insert into grouptypes (id, grouptype) values (4, 'Fellowship');

insert into roles (id, role) values (1, 'Traveller');
insert into roles (id, role) values (2, 'Horseman');
insert into roles (id, role) values (3, 'Wizard');
insert into roles (id, role) values (4, 'Dwarfs');
insert into roles (id, role) values (5, 'Elves');
insert into roles (id, role) values (6, 'Hobbits');

insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 3);
insert into permission_to_grouptype (grouptype_id, permission_id) values (1, 5);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 1);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 2);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 4);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 6);
insert into permission_to_grouptype (grouptype_id, permission_id) values (2, 7);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 1);
insert into permission_to_grouptype (grouptype_id, permission_id) values (3, 6);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 1);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 2);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 3);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 4);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 5);
insert into permission_to_grouptype (grouptype_id, permission_id) values (4, 6);

-- Second, the permissions to each role
insert into permission_to_role (role_id, permission_id) values (1, 2);
insert into permission_to_role (role_id, permission_id) values (1, 4);
insert into permission_to_role (role_id, permission_id) values (1, 6);
insert into permission_to_role (role_id, permission_id) values (2, 6);
insert into permission_to_role (role_id, permission_id) values (2, 7);
insert into permission_to_role (role_id, permission_id) values (3, 1);
insert into permission_to_role (role_id, permission_id) values (3, 4);
insert into permission_to_role (role_id, permission_id) values (3, 6);
insert into permission_to_role (role_id, permission_id) values (4, 3);
insert into permission_to_role (role_id, permission_id) values (5, 2);
insert into permission_to_role (role_id, permission_id) values (5, 4);
insert into permission_to_role (role_id, permission_id) values (5, 6);
insert into permission_to_role (role_id, permission_id) values (6, 5);

insert into groups (id, grouptype_id, groupname) values (1, 1, 'Dwarfs');
insert into groups (id, grouptype_id, groupname) values (2, 1, 'Hobbits');
insert into groups (id, grouptype_id, groupname) values (3, 2, 'Elves');
insert into groups (id, grouptype_id, groupname) values (4, 2, 'Men');
insert into groups (id, grouptype_id, groupname) values (5, 3, 'Wizards');
insert into groups (id, grouptype_id, groupname) values (6, 4, 'Fellowship');

insert into users (username, password) values ('Frodo', 'ff6668c9c0541301b18b3da3be4f719151eb0f873f3b74dbb036ee00434cee0f');
insert into users (username, password) values ('Sam', 'e96e02d8e47f2a7c03be5117b3ed175c52aa30fb22028cf9c96f261563577605');
insert into users (username, password) values ('Merry', 'fc261a5e3e3c85a419825aad1ced0df53b9a3fa69bd439d1610eb99f8de6bcd6');
insert into users (username, password) values ('Pippin', '26205d66c217a123c72d1e2bccd0536a12c39d65b085c224c4bbd0c75fcb3634');
insert into users (username, password) values ('Gandalf', '');
insert into users (username, password) values ('Aragorn', '');
insert into users (username, password) values ('Boromir', '');
insert into users (username, password) values ('Gimli', '');
insert into users (username, password) values ('Legolas', '');
insert into users (username, password) values ('Elrond', '');
insert into users (username, password) values ('Saruman', '');
insert into users (username, password) values ('Eomer', '');

insert into user_to_group (user_id, group_id, role_id) values ( 8, 1, 4);
insert into user_to_group (user_id, group_id, role_id) values ( 1, 2, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 2, 2, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 3, 2, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 4, 2, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 9, 3, 1);
insert into user_to_group (user_id, group_id, role_id) values (10, 3, 3);
insert into user_to_group (user_id, group_id, role_id) values ( 5, 4, 3);
insert into user_to_group (user_id, group_id, role_id) values ( 6, 4, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 7, 4, 1);
insert into user_to_group (user_id, group_id, role_id) values (11, 4, 3);
insert into user_to_group (user_id, group_id, role_id) values (12, 4, 2);
insert into user_to_group (user_id, group_id, role_id) values ( 5, 5, 3);
insert into user_to_group (user_id, group_id, role_id) values (11, 5, 3);
insert into user_to_group (user_id, group_id, role_id) values ( 1, 6, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 2, 6, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 3, 6, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 4, 6, 6);
insert into user_to_group (user_id, group_id, role_id) values ( 5, 6, 3);
insert into user_to_group (user_id, group_id, role_id) values ( 6, 6, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 7, 6, 1);
insert into user_to_group (user_id, group_id, role_id) values ( 8, 6, 4);
insert into user_to_group (user_id, group_id, role_id) values ( 9, 6, 5);

INSERT INTO countries (country_code, country_name, nationality) VALUES ('$$','$$','');
