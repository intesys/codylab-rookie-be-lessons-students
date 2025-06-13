package it.intesys.codylab.roockie.lessons.repository;

import it.intesys.codylab.roockie.lessons.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

public class AccountRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;



    public void save (Account account){
    if(account.getId() == null){
         Long id = jdbcTemplate.queryForObject("select nextval ('account_id_generator') ", Long.class);
         account.setId(id);
        jdbcTemplate.update("insert into account (id, alias, name, surname, email, date_create, date_modified)" +
                " values (1, 'thomas', 'Thomas', 'Forestieri' , 'tforestieri07@gmail.com', current_timestamp ,current_timestamp)",
                account.getId (), account.getAlias (), account.getName (), account.getSurname (), account.getEmail());




        }

    }

}
