package com.davanddev.uppgift_1.service;

import com.davanddev.uppgift_1.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service class for managing books using in-memory storage.
 */
@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public BookService() {
        books.add(new Book(1L, "Neuromancer", "William Gibson", 1984));
        books.add(new Book(2L, "Count Zero", "William Gibson", 1986));
        books.add(new Book(3L, "Mona Lisa Overdrive", "William Gibson", 1988));
        books.add(new Book(4L, "Snow Crash", "Neal Stephenson", 1992));
        books.add(new Book(5L, "Virtual Light", "William Gibson", 1993));
        books.add(new Book(6L, "Islands in the Net", "Bruce Sterling", 1988));
        books.add(new Book(7L, "Schismatrix", "Bruce Sterling", 1985));
        books.add(new Book(8L, "Hardwired", "Walter Jon Williams", 1989));
        books.add(new Book(9L, "When Gravity Fails", "George Alec Effinger", 1986));
        books.add(new Book(10L, "Altered Carbon", "Richard K. Morgan", 2002));
        books.add(new Book(11L, "Idoru", "William Gibson", 1996));
        books.add(new Book(12L, "Cyberpunk", "Bruce Bethke", 1983));
        books.add(new Book(13L, "Trouble and Her Friends", "Melissa Scott", 1994));
        books.add(new Book(14L, "Synners", "Pat Cadigan", 1991));
        books.add(new Book(15L, "Halting State", "Charles Stross", 2007));
        books.add(new Book(16L, "Accelerando", "Charles Stross", 2005));
        books.add(new Book(17L, "Software", "Rudy Rucker", 1982));
        books.add(new Book(18L, "Wetware", "Rudy Rucker", 1988));
        books.add(new Book(19L, "Freeware", "Rudy Rucker", 1997));
        books.add(new Book(20L, "Shockwave Rider", "John Brunner", 1975));

        idGenerator.set(20);
    }

    /**
     * Returns all books.
     *
     * @return a list of all books.
     */
    public List<Book> getAllBooks() {
        return books;
    }

    /**
     * Retrieves a book by its id.
     *
     * @param id the id of the book.
     * @return an Optional containing the book if found, or empty if not.
     */
    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    /**
     * Creates a new book by assigning a unique id and storing it in memory.
     *
     * @param book the book to create.
     * @return the created book with its generated id.
     */
    public Book createBook(Book book) {
        book.setId(idGenerator.incrementAndGet());
        books.add(book);
        return book;
    }
}
