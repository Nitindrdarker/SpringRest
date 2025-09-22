package com.example.jpa_entries.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa_entries.Entities.Books;

public interface BookRepository extends JpaRepository<Books, Long> {
}