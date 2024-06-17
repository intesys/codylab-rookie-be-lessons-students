package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Chat;
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
public class ChatRepository {
    private final JdbcTemplate db;

    public ChatRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Chat save(Chat chat) {
        if(chat.getId() == null) {
            Long id = db.queryForObject("select nextval('account_sequence')", Long.class);
            chat.setId(id);
            db.update("insert into chat (id, date_created, date_modified, name, surname, email) " +
                            "values (?,?,?,?,?,?)", chat.getId(), Timestamp.from(chat.getDateCreated()), Timestamp.from(chat.getDateModified()),
                    chat.getName(), chat.getSurname(), chat.getEmail());
            return chat;
        } else{
            db.update("update chat set date_modified = ?, name = ?, surname = ?, email = ? " +
                    "where id = ?", Timestamp.from(chat.getDateModified()), chat.getName(),
                    chat.getSurname(), chat.getEmail(), chat.getId());
            return findOriginalAccountById(chat.getId());
        }
    }

    public Optional<Chat> findById(Long id) {
        try {
            Chat chat = db.queryForObject("select * from chat where id = ?", this::map, id);
            return Optional.ofNullable(chat);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    private Chat findOriginalAccountById(Long id){
        return db.queryForObject("select * from chat where id = ?", this::map, id);
    }

    private Chat map(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setId(resultSet.getLong("id"));
        chat.setDateCreated(Optional.ofNullable(resultSet.getTimestamp("date_created")).map(Timestamp::toInstant).orElse(null));
        chat.setDateModified(resultSet.getTimestamp("date_Modified").toInstant());
        chat.setName(resultSet.getString("name"));
        chat.setSurname(resultSet.getString("surname"));
        chat.setEmail(resultSet.getString("email"));
        return chat;
    }

    public void delete(Long id){
       int updateCount  = db.update("delete from chat where id= ?", id);
       if (updateCount != 1)
           throw new IllegalStateException(String.format("update count %d, expected 1", id));
    }

    public Page<Chat> findAll(String filter, Pageable pageable){
        StringBuilder queryBuffer = new StringBuilder("select * from chat");

        List<Object> parameters = new ArrayList<>();
        if(filter != null && !filter.isBlank()){
            queryBuffer.append("where name like ? or surname like ? or email like ?");
            String like = "%" + filter + "%";
            for(int i= 0; i< 4; i++){
                parameters.add(like);
            }
        }
            String query = pagingQuery(queryBuffer, pageable);
        List<Chat> accounts = db.query(queryBuffer.toString(), this::map, parameters.toArray());
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
