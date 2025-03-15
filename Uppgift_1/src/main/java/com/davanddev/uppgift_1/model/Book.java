package com.davanddev.uppgift_1.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a book entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    @NotNull
    @Size(min = 2, max = 100)
    private String author;

    @Min(1000)
    @Max(9999)
    private int publishedYear;
}
