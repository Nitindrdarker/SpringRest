package com.example.jpa_entries.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa_entries.Entities.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {



}