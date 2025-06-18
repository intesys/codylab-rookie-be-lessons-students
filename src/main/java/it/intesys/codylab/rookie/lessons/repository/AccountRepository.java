package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository extends RookieRepository<Account> implements RowMapper<Account> {
    @Autowired
    JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(AccountRepository.class);
    @Override
    public Account save(Account account){
        logger.info("Creating account alias{}", account.getAlias());
        if(account.getId() == null){
            Long id = jdbcTemplate.queryForObject("select nextval('account_id_generator')", Long.class);
            account.setId(id);
            Instant dateCreated = account.getDateCreated();
            Instant dateModified = account.getDateModified();
            jdbcTemplate.update("""
                    insert into account(id, alias, name, surname, email, date_created, date_modified)
                    values(?, ?, ?, ?, ?, ?, ?)""",
                    account.getId(), account.getAlias(), account.getName(), account.getSurname(),
                    account.getEmail(), Timestamp.from(dateCreated), Timestamp.from(dateModified));

            logger.info("Account created with id{}", account.getId());
            return account;
        } else {
            Instant dateModified = account.getDateModified();
            int count = jdbcTemplate.update("""
                            update account set
                            alias = ?,
                            name = ?,
                            surname = ?,
                            email = ?,
                            date_modified = ?
                            where id = ?
                            """,
                    account.getAlias(), account.getName(), account.getSurname(),
                    account.getEmail(), Timestamp.from(dateModified), account.getId());

            if(count==0){
                IdNotFound(account.getId());
            }

            logger.info("Account updated with id{}", account.getId());
            return getAccount(account.getId());
        }
    }

    public Optional<Account> findById(Long id) {
        try {
            Account account = getAccount(id);
            return Optional.ofNullable(account);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Account getAccount(Long id) {
        return jdbcTemplate.queryForObject("select * from account where id = ?", this, id);
    }

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setName(rs.getString("name"));
        account.setSurname(rs.getString("surname"));
        account.setAlias(rs.getString("alias"));
        account.setEmail(rs.getString("email"));
        account.setDateCreated(rs.getTimestamp("date_created").toInstant());
        account.setDateModified(rs.getTimestamp("date_modified").toInstant());
        return account;
    }

    public void deleteById(Long id){
        int count = jdbcTemplate.update("delete from account where id = ?", id);
        if (count == 0){
            IdNotFound(id);
        }
    }

    private static void IdNotFound(Long id) {
        throw new RuntimeException("Account with id " + id + " not found");
    }

    public List<Account> findbyChatId(Long chatId) {
        return null;
    }

    public List<Account> findAll(int page, int size, String sort, String filter) {
        StringBuilder buf = new StringBuilder("select * from account ");
        List<String> parameters = new ArrayList<>();
        if(filter != null && filter.isBlank()){
            buf.append("""
                    where alias like '%pippo%'
                    or surname like '%pippo%'
                    or email like '%pippo%'
                    """);
            filter= "%" + filter
                    .replace('*', '%' )
                    .replace('?', '_') + "%";
            for(int i = 0; i<3; i++){
                parameters.add(filter);
            }

        };
        pageQuery(page, size, sort, buf);

        String query = buf.toString();
        return jdbcTemplate.query(query, this, null);
    }
}