package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Account;
import it.intesys.codylab.rookie.lessons.domain.Chat;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatRepository implements RookieRepository<Chat>, RowMapper<Chat>{

    private static final int BATCH_SIZE = 100;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AccountRepository accountRepository;
    Logger logger = LoggerFactory.getLogger(ChatRepository.class);

    @Override
    public Chat save(Chat chat) {
        logger.info("Creating chat");
        boolean insertion = chat.getId() == null;
        if (insertion) {
            Long id = jdbcTemplate.queryForObject("select nextval ('chat_id_generator') ", Long.class);
            chat.setId(id);
            Instant dateCreated = chat.getDateCreated();
            Instant dateModified = chat.getDateModified();
            jdbcTemplate.update("""
                            insert into chat (id, date_created, date_modified)
                            values (?, ?, ?)""",
                    chat.getId(),
                    Timestamp.from(dateCreated), Timestamp.from(dateModified));

            logger.info("Creating chat with id {}", chat.getId());

        } else {
            Instant dateModified = chat.getDateModified();
            int count = jdbcTemplate.update("""
                            update chat set
                               date_modified = ?
                             where
                               id = ?""",

                    Timestamp.from(dateModified),
                    chat.getId());
            if (count == 0)
                idNotFound(chat.getId());


            logger.info("Updating chat with id {}", chat.getId());
        }

        Chat currentChat = chat;

        List<Account> newMembers = chat.getMembers();
        List <Account> oldMembers = accountRepository.findByChatId(chat.getId());
        List <Account> toInsert = substract(newMembers, oldMembers);
        List <Account> toDelete = substract(oldMembers, newMembers);
        ParameterizedPreparedStatementSetter<Account> setter = (ps, account) -> {
            ps.setLong(1, currentChat.getId());
            ps.setLong(2, account.getId());
        };
        jdbcTemplate.batchUpdate("""
                insert into chat_account (chat_id, account_id)
                values(?, ?)
                """,toInsert, BATCH_SIZE, setter);
        jdbcTemplate.batchUpdate("""
                delete from chat_account
                where chat_id = ?
                and account_id = ?
                """,toDelete, BATCH_SIZE, setter);
        if(!insertion) {
            chat = findById0(chat.getId());
            chat.setMembers(newMembers);
        }
        return chat;
    }

    private <T> List <T> substract (List<T> from, List<T> what){
        List<T> copy = new ArrayList<>(from);
        copy.removeAll(what);
        return copy;

    }


    public Optional<Chat> findById(Long id) {
        try {
            Chat chat = findById0(id);
            return Optional.ofNullable(chat);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private Chat findById0(Long id) {
        Chat chat = jdbcTemplate.queryForObject("select * from chat where id = ?", this, id);
        return chat;
    }

    @Override
    public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Chat chat = new Chat();
        chat.setId (rs.getLong("id"));
        chat.setDateCreated (rs.getTimestamp("date_created").toInstant());
        chat.setDateModified (rs.getTimestamp("date_modified").toInstant());
        return chat;
    }

    public void deleteById(Long id) {
       int count = jdbcTemplate.update("delete from chat where id = ?", id);
       if(count == 0)
           idNotFound(id);
    }

    private static void idNotFound(Long id) {
        throw new RuntimeException("Chat with id " + id + "not found");
    }


}
