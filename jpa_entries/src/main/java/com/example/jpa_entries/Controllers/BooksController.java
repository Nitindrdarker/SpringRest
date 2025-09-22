package com.example.jpa_entries.Controllers;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa_entries.Entities.Books;
import com.example.jpa_entries.Repositories.BookRepository;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    public BooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Books> getAll() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Books create(@RequestBody Books book) {
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
}
