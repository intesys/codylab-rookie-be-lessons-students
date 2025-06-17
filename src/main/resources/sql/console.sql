   /*     id:
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
   create table account (
   id bigint primary key,
   alias varchar(128),   
   name varchar(128),
   surname varchar(128),
   email varchar(128),
   date_create timestamp,
   date_modified timestamp 
   );
   
   create sequence account_id_generator;
   
   
   drop table account;
   
   insert into account (id, alias, name, surname, email, date_create, date_modified)
   	values (1, 'thomas', 'Thomas', 'Forestieri' , 'tforestieri07@gmail.com', current_timestamp ,current_timestamp);
   
   
   INSERT INTO public.account
(id, alias, "name", surname, email, date_create, date_modified)
VALUES(1, '', '', '', '', '', '');


select * from account;
--where 

select nextval ('account_id_generator') 







   	