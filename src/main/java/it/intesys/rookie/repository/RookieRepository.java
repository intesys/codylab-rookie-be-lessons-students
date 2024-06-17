package it.intesys.rookie.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RookieRepository {
    public static final int BATCH_SIZE = 100;
    protected final JdbcTemplate db;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public RookieRepository(JdbcTemplate db) {
        this.db = db;
    }

    protected <T> List<T> subtract(List<T> from, List<T> what) {
        ArrayList<T> clone = new ArrayList<>(from);
        clone.removeAll(what);
        return clone;
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
