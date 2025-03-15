package com.davanddev.uppgift_1.controller;

import com.davanddev.uppgift_1.model.Book;
import com.davanddev.uppgift_1.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing book resources.
 */
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Retrieves all books.
     *
     * @return a list of all books.
     */
    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of all books")
    })
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Retrieves a book by its id.
     * If the book is not found, returns a 404 Not Found with an error message.
     *
     * @param id the id of the book.
     * @return a ResponseEntity containing the book or an error message.
     */
    @Operation(summary = "Get book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        var optionalBook = bookService.getBookById(id);

        if (optionalBook.isPresent()) {
            return ResponseEntity.ok(optionalBook.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Book not found with id: " + id);
        }
    }

    /**
     * Creates a new book.
     * Validates the incoming data and returns the created book with a generated id.
     *
     * @param book the book to create.
     * @return a ResponseEntity containing the created book with HTTP status 201.
     */
    @Operation(summary = "Create book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of book"),
            @ApiResponse(responseCode = "400", description = "Invalid book data")
    })
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.created(URI.create("/books/" + createdBook.getId()))
                .body(createdBook);
    }
}
