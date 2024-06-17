package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Account;
import it.intesys.rookie.domain.Status;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
            db.update("insert into account (id, date_created, date_modified, alias, name, surname, email, status) values (?, ?, ?, ?, ?, ?, ?, ?)",
                    account.getId(), Timestamp.from(account.getDateCreated()), Timestamp.from(account.getDateModified()), account.getAlias(), account.getName(), account.getSurname(), account.getEmail(), account.getStatus().ordinal());
            return account;
        } else {
            int updateCount = db.update("update account set date_modified = ?, alias = ?, name = ?, surname = ?, email = ?, status = ? where id = ?",
                    Timestamp.from(account.getDateModified()), account.getAlias(), account.getName(), account.getSurname(), account.getEmail(), account.getStatus().ordinal(), account.getId());
            if(updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
            }
            return findOriginalAccountById(account.getId());
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

    private Account findOriginalAccountById(Long id) {
        return db.queryForObject("select * from account where id = ?", this::map, id);
    }

    public void deleteAccount(Long id){
        int updateCount = db.update("delete from account where id = ?", id);

        if(updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
        } else System.out.println("DELETE SUCCESS\nUtente con id = " + id);
    }

    private Account map(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setDateCreated(Optional.ofNullable(resultSet.getTimestamp("date_created")).map(Timestamp::toInstant).orElse(null));
        account.setDateModified(Optional.ofNullable(resultSet.getTimestamp("date_modified")).map(Timestamp::toInstant).orElse(null));
        account.setAlias(resultSet.getString("alias"));
        account.setName(resultSet.getString("name"));
        account.setSurname(resultSet.getString("surname"));
        account.setEmail(resultSet.getString("email"));
        account.setStatus(Status.values()[resultSet.getInt("status")]);
        return account;
    }

    public Page<Account> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from account ");
        List<Object> paramenters =  new ArrayList<>();
        if(filter != null && filter.isBlank()){
            queryBuffer.append("where name like ? or surname like ? or email like ? or alias like ?");
            for(int i = 0; i < 4; i++){
                paramenters.add("%" + filter + "%");
            }
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Account> accounts = db.query(query, this::map, paramenters.toArray());
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
