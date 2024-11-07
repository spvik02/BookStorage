package edu.spv.books.service.impl;

import edu.spv.books.annotation.CacheAlong;
import edu.spv.books.entity.Book;
import edu.spv.books.repository.BookRepository;
import edu.spv.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Lazy
    @Autowired
    private BookService bookService;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @CacheAlong
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("book with id " + id + " wasn't found"));
    }

    @Override
    public Book updatePartiallyById(Long id, Book book) {
        Book bookForUpdate = bookService.findById(id);

        if (Objects.nonNull(book.getTitle())) {
            bookForUpdate.setTitle(book.getTitle());
        }
        if (Objects.nonNull(book.getAuthor())) {
            bookForUpdate.setAuthor(book.getAuthor());
        }
        if (Objects.nonNull(book.getDescription())) {
            bookForUpdate.setDescription(book.getDescription());
        }

        Book bookUpdated = bookRepository.save(bookForUpdate);
        return bookUpdated;
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
