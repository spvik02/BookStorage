package edu.spv.books.service.impl;

import edu.spv.books.entity.Book;
import edu.spv.books.repository.BookRepository;
import edu.spv.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book updatePartiallyById(Long id, Book book) {
        Book bookForUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("book with id " + id + " wasn't found"));

        if (Objects.nonNull(book.getTitle())) {
            bookForUpdate.setTitle(book.getTitle());
        }
        if (Objects.nonNull(book.getAuthor())) {
            bookForUpdate.setAuthor(book.getAuthor());
        }
        if (Objects.nonNull(book.getDescription())) {
            bookForUpdate.setDescription(book.getDescription());
        }

        return bookRepository.save(bookForUpdate);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
