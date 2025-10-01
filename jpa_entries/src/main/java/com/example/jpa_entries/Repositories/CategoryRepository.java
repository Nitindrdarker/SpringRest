package com.example.jpa_entries.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jpa_entries.Entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
