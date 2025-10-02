package com.example.jpa_entries.Repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jpa_entries.Entities.Books;
import com.example.jpa_entries.Projections.BookSummary;

public interface BookRepository extends JpaRepository<Books, Long> {
    // NEW: Find all books for a given publisher ID
    List<Books> findByPublisherId(Long publisherId);

    // Derived query: find books by title
    List<Books> findByTitle(String title);

    // Find all books of a specific author
    List<Books> findByAuthor(String author);

    Page<Books> findAll(Pageable pageable);

    // JPQL query to search books whose title contains a keyword (case-insensitive)
    @Query("SELECT b FROM Books b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Books> searchByTitle(@Param("keyword") String keyword);

    @Query("SELECT b.title AS title, b.author AS author, p.name AS publisherName  FROM Books b JOIN b.publisher p")
    List<BookSummary> findAllBookSummaries();

}