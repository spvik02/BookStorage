package edu.spv.books.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    T save(T entity);

    List<T> findAll();

    Optional<T> findById(ID id);

    void deleteById(ID id);
}
