package edu.spv.books.controller;

import edu.spv.books.entity.Book;
import edu.spv.books.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Creates new book with provided data.
     *
     * @param book object with book data
     * @return ResponseEntity with saved book
     */
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    /**
     * Returns list of books
     *
     * @return ResponseEntity with list of books
     */
    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * Updates book with data passed in request body (title, author and description are available for update).
     * If field is not specified in request body the data is not updated.
     *
     * @param id   id of book that should be updated
     * @param book request body containing book data for update
     * @return ResponseEntity updated book data
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Book> updatePartially(
            @PathVariable Long id,
            @RequestBody Book book
    ) {
        Book bookUpdated = bookService.updatePartiallyById(id, book);
        return new ResponseEntity<>(bookUpdated, HttpStatus.OK);
    }

    /**
     * Deletes book with passed id.
     *
     * @param id id
     * @return ResponseEntity with status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
