package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
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
        if(account.getId() == null){
            Long id = db.queryForObject("select nextval('account_sequence') ", Long.class);
            account.setId(id);
            db.update("insert into account (id, date_created, date_modified, alias, name, surname, email) values (?, ?, ?, ?, ?, ?, ?)", account.getId(), Timestamp.from(account.getDateCreated()), Timestamp.from(account.getDateModified()), account.getAlias(), account.getName(), account.getSurname(), account.getEmail());
            return account;
        } else {
            db.update("update account set date_modified = ?, alias = ?, name = ?, surname = ?, email = ?)", Timestamp.from(account.getDateModified()), account.getAlias(), account.getName(), account.getSurname(), account.getEmail());
            return account;
        }
    }

    public Optional<Account> findById(Long id) {
        try{
            Account account = db.queryForObject("select * from account where id = ?", this::map, id);
            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException e){
            System.out.println("FOUND ERROR\nUtente con id = " + id);
            return Optional.empty();
        }
    }

    public Optional<Account> findAccountOriginalById(Long id) {
        try{
            Account account = db.queryForObject("select * from account where id = ?", this::map, id);
            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException e){
            System.out.println("FOUND ERROR\nUtente con id = " + id);
            return Optional.empty();
        }
    }

    public Optional<Account> deleteAccount(Long id){
        try{
            Account account = db.queryForObject("select * from account where id = ?", this::map, id);
            db.update("delete from account where id = ?", id);
            System.out.println("DELETE SUCCESS\nUtente con id = " + id);
            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException e){
            System.out.println("DELETE ERROR\nUtente con id = " + id);
            return Optional.empty();
        }
    }

    private Account map(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setDateCreated(resultSet.getTimestamp("date_created").toInstant());
        account.setDateModified(resultSet.getTimestamp("date_modified").toInstant());
        account.setAlias(resultSet.getString("alias"));
        account.setName(resultSet.getString("name"));
        account.setSurname(resultSet.getString("surname"));
        account.setEmail(resultSet.getString("email"));
        return account;
    }
}
