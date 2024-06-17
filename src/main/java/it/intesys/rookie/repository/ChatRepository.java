package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Account;
import it.intesys.rookie.domain.Chat;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatRepository extends RookieRepository {
    private final AccountRepository accountRepository;
    public ChatRepository(JdbcTemplate db, AccountRepository accountRepository){
        super(db);
        this.accountRepository = accountRepository;
    }

    public Chat save(Chat chat) {
        if(chat.getId() == null){
            Long id = db.queryForObject("select nextval('chat_sequence') ", Long.class);
            chat.setId(id);
            db.update("insert into chat (id, date_created, date_modified) " +
                    "values (?, ?, ?)", chat.getId(), Timestamp.from(chat.getDateCreated()),
                    Timestamp.from(chat.getDateModified()));

            db.batchUpdate("insert into chat_member (chat_id, account_id) values (?, ?)", chat.getMembers(), 5, (ps, account) -> {
                ps.setLong(1, chat.getId());
                ps.setLong(2, account.getId());
            });

            return chat;
        } else {
            int updateCount = db.update("update chat set date_modified = ? where id = ?", Timestamp.from(chat.getDateModified()), chat.getId());
            if(updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
            }

            List<Account> members = chat.getMembers();
            List<Account> currentMembers = findChatById(chat.getId()).getMembers();

            List<Account> insertions = subtract(members, currentMembers);
            db.batchUpdate("insert into chat_member (chat_id, account_id) values (?, ?)", insertions, BATCH_SIZE, (ps, account) -> {
                ps.setLong(1, chat.getId());
                ps.setLong(2, account.getId());
            });

            List<Account> deletions = subtract (currentMembers, members);
            db.batchUpdate("delete from chat_member where chat_id = ? and account_id = ?", deletions, BATCH_SIZE, (ps, account) -> {
                ps.setLong(1, chat.getId());
                ps.setLong(2, account.getId());
            });


            return findChatById(chat.getId());
        }
    }


    public Optional<Chat> findById(Long id) {
        try{
            Chat chat = findChatById(id);
            return Optional.ofNullable(chat);
        } catch (EmptyResultDataAccessException e){
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    private Chat findChatById(Long id) {
        Chat chat = db.queryForObject("select * from chat where id = ?", this::map, id);
        if (chat != null) {
            List<Account> accounts = accountRepository.findByChatId(id);
            chat.setMembers(accounts);
        }
        return chat;
    }

    public void deleteChat(Long id){
        int updateCount = db.update("delete from chat where id = ?", id);

        if(updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
        } else {
            logger.debug("DELETE SUCCESS\nUtente con id = " + id);
        }
    }

    private Chat map(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setId(resultSet.getLong("id"));
        chat.setDateCreated(Optional.ofNullable(resultSet.getTimestamp("date_created")).map(Timestamp::toInstant).orElse(null));
        chat.setDateModified(Optional.ofNullable(resultSet.getTimestamp("date_modified")).map(Timestamp::toInstant).orElse(null));

        return chat;
    }

    public Page<Chat> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from chat ");
        List<Object> parameters = new ArrayList<>();
        if (filter != null && !filter.isBlank()) {
            queryBuffer.append("where name like ? or surname like ? or email like ? or alias like ?");
            String like = "%" + filter + "%";
            for (int i = 0; i < 4; i++) parameters.add(like);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Chat> chats = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(chats, pageable, 0);
    }


}
