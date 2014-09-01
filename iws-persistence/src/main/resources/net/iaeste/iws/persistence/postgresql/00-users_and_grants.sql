-- Following must be run as user 'postgres'
-- NOTE: Please replace the passwords before running!

create user iws_user with password 'iws';
create user mail_user with password 'mail';

-- Now, connect to the IWS database, and grant permissions, the normally used
-- users should only have the permissions that is needed, nothing more!
\connect iws
grant connect on database iws to iws_user;
grant select, insert, update, delete, truncate on all tables in schema public to iws_user;
grant usage on all sequences in schema public to iws_user;

\connect mail
grant connect on database mail to iws_user;
grant connect on database mail to mail_user;
grant select, insert, update, delete, truncate on all tables in schema public to iws_user;
grant usage on all sequences in schema public to iws_user;
grant select on all tables in schema public to mail_user;
