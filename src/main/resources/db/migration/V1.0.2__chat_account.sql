create table chat_account (
    chat_id bigint not null references chat(id),
    account_id bigint not null references account(id)
)