alter table account
    alter alias set not null,
    alter name set not null,
    alter surname set not null,
    alter email set not null,
    alter date_created set not null,
    alter date_modified set not null
 ;

 alter table chat
   alter date_created set not null,
   alter date_modified set not null
 ;