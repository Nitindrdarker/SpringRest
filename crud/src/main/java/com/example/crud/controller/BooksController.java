package com.example.crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.models.Book;



@RestController
@RequestMapping("/books")
public class BooksController {
    private List<Book> books = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();


    @GetMapping
    public List<Book> getMethodName() {
        return books;
    }

    @PostMapping
    public Book postMethodName(@RequestBody Book book) {
        book.setId(counter.incrementAndGet());
        books.add(book);
        return book;
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }


    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
        }
        return book;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        if (books.stream().noneMatch(b -> b.getId().equals(id))) {
            return "Book not found";
        }
        books.removeIf(b -> b.getId().equals(id));
        return "Book deleted";
    }
}
    



    

