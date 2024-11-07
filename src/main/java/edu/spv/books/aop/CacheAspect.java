package edu.spv.books.aop;

import edu.spv.books.entity.Book;
import edu.spv.books.entity.Cache;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Aspect for cache
 */
@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {
    private final Cache<Long, Book> cache;

    @Pointcut("@annotation(edu.spv.books.annotation.CacheAlong)")
    private void annotatedMethod() {
    }

    @Pointcut("within(edu.spv.books.service.BookService+)")
    private void bookServicePointCut() {
    }

    /**
     * Advice processing the get by id operation. If book with provided id is in cache it will get it from cache.
     * If book isn't in cache it will proceed initial operation and add data to cache.
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("annotatedMethod() && bookServicePointCut() && execution(* findById(..))")
    public Object cacheGetById(ProceedingJoinPoint joinPoint) throws Throwable {

        Optional<Book> cachedBook;
        Book book;
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];

        cachedBook = cache.get(id);
        if (cachedBook.isEmpty()) {
            book = (Book) joinPoint.proceed(args);
            cache.put(id, book);
        } else {
            book = cachedBook.get();
        }

        return book;
    }
}
