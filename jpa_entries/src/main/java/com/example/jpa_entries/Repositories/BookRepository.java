package com.example.jpa_entries.Repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa_entries.Entities.Books;

public interface BookRepository extends JpaRepository<Books, Long> {
        // NEW: Find all books for a given publisher ID
    List<Books> findByPublisherId(Long publisherId);
}