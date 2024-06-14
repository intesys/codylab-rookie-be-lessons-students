package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class AccountRepository {
    private final JdbcTemplate db;

    public AccountRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Account save(Account account) {
        if (account.getId() == null) {
            Long id = db.queryForObject("select nextval('account_sequence')", Long.class);
            account.setId(id);
            db.update("Insert into account (id, date_created, date_modified, alias, name, surname, email)" +
                    "values(?, ?, ?, ?, ?, ?, ?)", account.getId(), Timestamp.from(account.getDateCreated()), Timestamp.from(account.getDateModified()), account.getAlias(), account.getName(), account.getSurname(), account.getEmail());
            return account;
        } else {
            int updateCount = db.update("Update into account set date_modified = ?, alias = ?, name = ?, surname = ?, email = ? where id = ?", Timestamp.from(account.getDateModified()), account.getAlias(), account.getName(), account.getSurname(), account.getEmail(), account.getId());
            if (updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
            }
            return getAccount(account.getId());
        }
    }

    public Optional<Account> findById(Long id) {
        try {
            Account account = getAccount(id);
            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private Account getAccount(Long id) {
        Account account = db.queryForObject("select * from account where id = ?", this::map, id);
        return account;
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

    public void delete(Long id) {
        int updateCount = db.update("delete from account where id = ?", id);
        if (updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
        }
    }

    public Page<Account> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from account ");
        List<Object> parameters = new ArrayList<>();
        if (filter != null && !filter.isBlank()){
            queryBuffer.append("where alias like ? or name like ? or surname like ? or email like ?");
            String like = "%" + filter + "%";
            for (int i = 0; i < 4; i++) parameters.add(like);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Account> accounts = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(accounts, pageable, 0);
    }

    protected String pagingQuery(StringBuilder query, Pageable pageable) {
        String orderSep = "";
        Sort sort = pageable.getSort();
        if (!sort.isEmpty()) {
            query.append(" order by ");
            for (Sort.Order order: sort) {
                query.append(orderSep)
                        .append(order.getProperty())
                        .append(' ')
                        .append(order.getDirection().isDescending() ? "desc" : "")
                        .append(' ');
                orderSep = ", ";
            }
        }

        query.append("limit ")
                .append(pageable.getPageSize())
                .append(' ')
                .append("offset ")
                .append(pageable.getOffset());

        return query.toString();
    }
}
