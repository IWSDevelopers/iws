alter table user_to_group add write_to_private_list boolean default true;
alter table user_to_group add constraint u2g_notnull_write_private_list check (write_to_private_list is not null);
update user_to_group set write_to_private_list = on_private_list;
