package edu.spv.books.service;

import edu.spv.books.entity.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    List<Book> findAll();

    Book findById(Long id);

    Book updatePartiallyById(Long id, Book book);

    void deleteById(long id);
}
