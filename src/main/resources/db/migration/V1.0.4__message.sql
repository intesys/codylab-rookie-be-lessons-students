 create table message (
 id bigint primary key,
 date_created timestamp not null,
 date_modified timestamp not null,
 sender_id bigint not null references account (id),
 chat_id bigint not null references chat(id),
 text text not null
 );

 create sequence message_id_generator;