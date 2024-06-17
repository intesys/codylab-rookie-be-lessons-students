alter table chat_member drop constraint chat_member_chat_id_fkey;

alter table chat_member add constraint chat_member_chat_id_fkey FOREIGN KEY (chat_id) references chat (id) on delete cascade;