/*        id:
          type: integer
          format: int64
        alias:
          type: string
        name:
          type: string
        surname:
          type: string
        email:
          type: string
        dateCreated:
          type: string
          format: date-time
        dateModified:
          type: string
          format: date-time
*/
create table account(
id bigint primary key,
alias varchar(128),
name varchar(128),
surname varchar(128),
email varchar(128),
date_created timestamp,
date_modified timestamp
);

create sequence account_id_generator;

insert into account(id, alias, name, surname, email, date_created, date_modified)
	values(1, 'PipeC', 'Pippo', 'C', 'pippoC@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

select * from account

select nextval('account_id_generator')
