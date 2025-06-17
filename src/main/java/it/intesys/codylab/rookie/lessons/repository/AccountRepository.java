package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Repository
public class AccountRepository implements RookieRepository<Account>, RowMapper<Account>{
    @Autowired
    JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);
    @Override
    public Account save(Account account){
        logger.info("Creating account alias {}", account.getAlias());
        if(account.getId() == null){
             Long id = jdbcTemplate.queryForObject("select nextval ('account_id_generator') ", Long.class);
             account.setId(id);
            Instant dateCreated = account.getDateCreated();
            Instant dateModified = account.getDateModified();
            jdbcTemplate.update("""
                    insert into account (id, alias, name, surname, email, date_create, date_modified)
                    values (?, ?, ?, ? , ?, ? ,?)""",
                    id, account.getAlias (), account.getName (), account.getSurname (), account.getEmail(),
                    Timestamp.from(dateCreated), Timestamp.from(dateModified));

            logger.info("Creating account with id {}", account.getId());
            return account;
        } else {
            Instant dateModified = account.getDateModified();
             int count =  jdbcTemplate.update("""
                   update account set
                      alias = ?,
                      name = ?,
                      surname = ?,
                      email = ?,
                      date_modified = ?
                    where
                      id = ?""",

                    account.getAlias (),
                    account.getName (),
                    account.getSurname (),
                    account.getEmail(),
                    Timestamp.from(dateModified),
                    account.getId());
             if (count ==0)
                idNotFound(account.getId());


            logger.info("Updating account with id {}", account.getId());
             return findById0(account.getId());
        }

    }

    public Optional<Account> findById(Long id) {
        try {
            Account account = findById0(id);
            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private Account findById0(Long id) {
        Account account = jdbcTemplate.queryForObject("select * from account where id = ?", this, id);
        return account;
    }

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId (rs.getLong("id"));
        account.setName (rs.getString("name"));
        account.setSurname (rs.getString("Surname"));
        account.setAlias (rs.getString("Alias"));
        account.setEmail (rs.getString("Email"));
        account.setDateCreated (rs.getTimestamp("date_create").toInstant());
        account.setDateModified (rs.getTimestamp("date_modified").toInstant());
        return account;
    }

    public void deleteById(Long id) {
       int count = jdbcTemplate.update("delete from account where id = ?", id);
       if(count == 0)
           idNotFound(id);
    }

    private static void idNotFound(Long id) {
        throw new RuntimeException("Account with id " + id + "not found");
    }

    public List<Account> findByChatId(Long chatId) {
        return jdbcTemplate.query("""
                select * from account
                where id in (
                	select account_id from chat_account
                	where chat_id = ?
                )     
                """, this, chatId);
    }



    }
}
