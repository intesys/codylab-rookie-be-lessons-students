CREATE TABLE chat (
	id bigint NOT NULL primary key,
	date_created timestamp NULL,
	date_modified timestamp NULL
);

create table chat_member (
    chat_id bigint not null references chat (id),
    account_id bigint not null references account (id),
    CONSTRAINT chat_members_pkey PRIMARY KEY (chat_id, account_id)
);

CREATE SEQUENCE chat_sequence;