package com.example.jpa_entries.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa_entries.Entities.Books;
import com.example.jpa_entries.Entities.Category;
import com.example.jpa_entries.Entities.Publisher;
import com.example.jpa_entries.Exceptions.ResourceNotFoundException;
import com.example.jpa_entries.Repositories.BookRepository;
import com.example.jpa_entries.Repositories.CategoryRepository;
import com.example.jpa_entries.Repositories.PublisherRepository;
import com.example.jpa_entries.Services.BookService;
import com.example.jpa_entries.dto.BookDTO;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    public BooksController(BookRepository bookRepository, PublisherRepository publisherRepository,
            BookService bookService, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.bookService = bookService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Books> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/dto")
    public List<BookDTO> getAllBooksDTO() {
        return bookService.getAllBooksDTO();
    }

    @PostMapping
    public Books create(@RequestBody Map<String, Object> request) {

        // Extract basic info
        String title = (String) request.get("title");
        String author = (String) request.get("author");
        Long publisherId = Long.valueOf(request.get("publisher_id").toString());

        // Find Publisher
        Publisher pub = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        // Create Book
        Books book = new Books();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(pub);

        // Handle categories
        List<Category> categories = new ArrayList<>();

        Category category = new Category("fuck you");
        categoryRepository.save(category);
        categories.add(category);

        book.setCategories(categories);

        // Save book
        return bookRepository.save(book);
    }

    @GetMapping("/{id}")
    public Books getById(@PathVariable Long id) {
        return bookService.getBook(id); // used service layer
    }

    // In BooksController
    @PutMapping("/{id}")
    public Books updateBook(@PathVariable Long id, @RequestBody Books updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());

                    return bookRepository.save(book); // save() updates because id is same
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    // In BooksController
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/publisher/{publisherId}")
    public List<Books> getBooksByPublisher(@PathVariable Long publisherId) {
        return bookRepository.findByPublisherId(publisherId);
    }

    @GetMapping("/search/title/{title}")
    public List<Books> getByTitle(@PathVariable String title) {
        return bookRepository.findByTitle(title);
    }

    @GetMapping("/search/author/{author}")
    public List<Books> getByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }

    @GetMapping("/paged")
    public Page<Books> getPagedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pageable);
    }

    // Example: GET /books/search?keyword=java
    @GetMapping("/search")
    public List<Books> searchBooks(@RequestParam String keyword) {
        return bookRepository.searchByTitle(keyword);
    }
}
