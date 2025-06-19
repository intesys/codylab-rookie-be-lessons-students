package it.intesys.codylab.rookie.lessons.repository;

import it.intesys.codylab.rookie.lessons.domain.Message;
import it.intesys.codylab.rookie.lessons.exception.NotFound;
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
public class MessageRepository extends RookieRepository<Message> implements RowMapper<Message> {
    Logger logger = LoggerFactory.getLogger(MessageRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ChatRepository chatRepository;

    @Override
    public Message save(Message message) {
        logger.info("Creating message sender {}", message.getSender());

        if (message.getId () == null) {
            Long id = jdbcTemplate.queryForObject("select nextval ('message_id_generator')", Long.class);
            message.setId(id);
            Instant dateCreated = message.getDateCreated();
            Instant dateModified = message.getDateModified();
            jdbcTemplate.update ("""
                    insert into message (id, date_created, date_modified, sender_id, chat_id, text)
                    values (?, ?, ?, ?, ?, ?)
                """,
                message.getId (),
                Timestamp.from(dateCreated), Timestamp.from(dateModified),
                message.getSender().getId(), message.getChat().getId(), message.getText());

            logger.info("Message created with id {}", message.getId ());
            return message;
        } else {
            Instant dateModified = message.getDateModified();
            int count = jdbcTemplate.update ("""
                    update message set
                        sender_id = ?,
                        chat_id = ?,
                        text = ?,
                        date_modified = ?
                    where
                        id = ?
                """,
                message.getSender().getId(),
                message.getChat().getId(),
                message.getText(),
                Timestamp.from(dateModified),
                message.getId ());

            if (count == 0)
                idNotFound(message.getId());
            
            logger.info("Updated message with id {}", message.getId ());
            return findById0(message.getId());
        }
     }

    public Optional<Message> findById(Long id) {
        try {
            Message message = findById0(id);
            return Optional.ofNullable(message);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Message findById0(Long id) {
        return jdbcTemplate.queryForObject("select * from message where id = ?", this, id);
    }


    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();
        message.setId(rs.getLong("id"));
        message.setDateCreated(rs.getTimestamp("date_created").toInstant());
        message.setDateModified(rs.getTimestamp("date_modified").toInstant());
        message.setSender(accountRepository.findById(rs.getLong("sender_id")).orElseThrow());
        message.setChat(chatRepository.findById(rs.getLong("chat_id")).orElseThrow());
        message.setText(rs.getString("text"));
        return message;
    }

    public void deleteById(Long id) {
        int count = jdbcTemplate.update("delete from message where id = ?", id);
        if (count == 0)
            idNotFound(id);
    }

    private static void idNotFound(Long id) {
        throw new NotFound(id, Message.class);
    }

    public List<Message> findByChatId(Long chatId) {
        return jdbcTemplate.query("""
            select * from message
            where chat_id = ?
            """, this, chatId);
    }

    /*
     * sort: alias oppure alias,desc
     */
    public List<Message> findAll(int page, int size, String sort, String text, Long chatId) {
        StringBuilder buf = new StringBuilder("select  * from message ");
        List<String> parameters = new ArrayList<>();
        String whereOrAnd = "where";
        if (text != null && !text.isBlank()) {
            buf.append(whereOrAnd).append(" text like ? ");
            whereOrAnd = "and";
            text = "%" + text
                    .replace('*', '%')
                    .replace('?', '_')
                    .replace(' ', '%')
                    + "%";
            parameters.add(text);
        };

        if (chatId != null) {
            buf.append(whereOrAnd).append(" chat_id = ? ");
            whereOrAnd = "and";
        }

        pageQuery(page, size, sort, buf);

        String query = buf.toString();
        return jdbcTemplate.query (query, this, parameters.toArray());
    }
}
