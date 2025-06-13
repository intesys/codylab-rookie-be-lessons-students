package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class AccountRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public void save (Account account) {
        logger.info("Creating account alias {}", account.getAlias());

        if (account.getId () == null) {
            Long id = jdbcTemplate.queryForObject("select nextval ('account_id_generator')", Long.class);
            account.setId(id);
            Instant dateCreated = account.getDateCreated();
            Instant dateModified = account.getDateModified();
            jdbcTemplate.update ("insert into account (id, alias, name, surname, email, date_created, date_modified) " +
                    "values (?, ?, ?, ?, ?, ?, ?)",
                    account.getId (), account.getAlias(), account.getName (), account.getSurname (), account.getEmail (),
                    Timestamp.from(dateCreated), Timestamp.from(dateModified));

            logger.info("Account created with id {}", account.getId ());
        }
     }
}
