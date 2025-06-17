create table account (
   id bigint primary key,
   alias varchar(128),
   name varchar(128),
   surname varchar(128),
   email varchar(128),
   date_create timestamp,
   date_modified timestamp
   )

   create sequence account_id_generator;