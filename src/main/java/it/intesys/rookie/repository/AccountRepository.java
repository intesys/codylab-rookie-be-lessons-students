package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final JdbcTemplate db;

    public AccountRepository(JdbcTemplate db){
        this.db = db;
    }
    public Account save(Account account) {
        Long id = db.queryForObject( "select nextval ('account_sequence')", Long.class);
        account.setId(id);
        db.update ("insert into account (id, date_created, date_modified, alias, surname, email)" +
        "values (?, ?, ?, ?, ?, ?, ?)",account.getId(), Timestamp.from(account.getDataCreated()),Timestamp.from(account.getDataModified()),
                account.getAlias(), account.getName(),account.getSurname(), account.getEmail());
        return account;
    }

    public Optional<Account> findById(Long id) {
        try{
        Account account = db.queryForObject("select * from account where id = ?", this::map, id);
         return Optional.ofNullable(account);
        }  catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Account map(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setDataCreated(Optional.ofNullable(resultSet.getTimestamp("date_created")).map(Timestamp::toInstant).orElse(null));
        account.setDataModified(Optional.ofNullable(resultSet.getTimestamp("date_modified")).map(Timestamp::toInstant).orElse(null));
        account.setAlias(resultSet.getString("alias"));
        account.setName(resultSet.getString("name"));
        account.setSurname(resultSet.getString("surname"));
        account.setEmail(resultSet.getString("email"));
        return account;
    }

    public void delete (Long id) {
        int uodateCount = db.update("deletefrom account where id = ?", id);
    }
}
