package com.example.jpa_entries.Controllers;

import org.springframework.web.bind.annotation.*;
import com.example.jpa_entries.Entities.Publisher;
import com.example.jpa_entries.Repositories.PublisherRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/publishers")
public class PublisherController {

    
    private PublisherRepository publisherRepository;
    

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    @PostMapping
    public Publisher create(@RequestBody Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @GetMapping("/{id}")
    public Optional<Publisher> getMethodName(@PathVariable Long id) {
        return publisherRepository.findById(id);
    }
    
}
