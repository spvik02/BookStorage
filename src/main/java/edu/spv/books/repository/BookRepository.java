package edu.spv.books.repository;

import edu.spv.books.entity.Book;
import org.springframework.data.repository.ListCrudRepository;

public interface BookRepository extends ListCrudRepository<Book, Long> {

}
