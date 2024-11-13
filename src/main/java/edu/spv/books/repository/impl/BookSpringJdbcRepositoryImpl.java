package edu.spv.books.repository.impl;

import edu.spv.books.entity.Book;
import edu.spv.books.repository.BaseBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
@RequiredArgsConstructor
public class BookSpringJdbcRepositoryImpl implements BaseBookRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_SQL = """
            INSERT INTO book (title, author, description) 
            VALUES (?, ?, ?);
            """;
    private final String SELECT_ALL_SQL = "SELECT id, title, author, description FROM book;";
    private final String SELECT_WHERE_ID_SQL = "SELECT id, title, author, description FROM book WHERE id = ?;";
    private final String DELETE_WHERE_ID_SQL = "DELETE FROM book WHERE id = ? ;";
    private static final String UPDATE_WHERE_ID_SQL = "UPDATE book SET title = ?, author = ?, description = ? where id = ?";

    @Override
    public Book save(Book book) {
        Book savedBook;
        if (Objects.nonNull(book.getId()) && findById(Objects.requireNonNull(book.getId())).isPresent()) {
            savedBook = update(book);
        } else {
            savedBook = insert(book);
        }

        return savedBook;
    }

    private Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection1 -> {
                    PreparedStatement ps = connection1.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, book.getTitle());
                    ps.setString(2, book.getAuthor());
                    ps.setString(3, book.getDescription());
                    return ps;
                },
                keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(id).get();
    }

    private Book update(Book book) {
        jdbcTemplate.update(
                connection1 -> {
                    PreparedStatement ps = connection1.prepareStatement(UPDATE_WHERE_ID_SQL, new String[]{"id"});
                    ps.setString(1, book.getTitle());
                    ps.setString(2, book.getAuthor());
                    ps.setString(3, book.getDescription());
                    ps.setLong(4, book.getId());
                    return ps;
                });
        return findById(book.getId()).get();
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new BookMapper());
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book;
        try {
            book = jdbcTemplate.queryForObject(
                    SELECT_WHERE_ID_SQL,
                    new BookMapper(),
                    id);
        } catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_WHERE_ID_SQL, id);
    }

    private static final class BookMapper implements RowMapper<Book> {
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            return book;
        }
    }
}
