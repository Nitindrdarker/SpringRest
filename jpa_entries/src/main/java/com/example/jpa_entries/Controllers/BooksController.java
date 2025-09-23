package com.example.jpa_entries.Controllers;


import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa_entries.Entities.Books;
import com.example.jpa_entries.Entities.Publisher;
import com.example.jpa_entries.Repositories.BookRepository;
import com.example.jpa_entries.Repositories.PublisherRepository;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    public BooksController(BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public List<Books> getAll() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Books create(@RequestBody Map<String, Object> request) {
         
    String title = (String) request.get("title");
    String author = (String) request.get("author");
    Long publisherId = Long.valueOf(request.get("publisher_id").toString());

    Publisher pub = publisherRepository.findById(publisherId)
                     .orElseThrow(() -> new RuntimeException("Publisher not found"));

    Books book = new Books(title, author, pub);
    

    return bookRepository.save(book);
    }

    @GetMapping("/{id}")
    public Books getById(@PathVariable Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Books update(@PathVariable Long id, @RequestBody Books data) {
        return bookRepository.findById(id).map(b -> {
            b.setTitle(data.getTitle());
            b.setAuthor(data.getAuthor());
            return bookRepository.save(b);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "Book deleted";
    }


    @GetMapping("/publisher/{publisherId}")
    public List<Books> getBooksByPublisher(@PathVariable Long publisherId) {
        return bookRepository.findByPublisherId(publisherId);
    }
}
