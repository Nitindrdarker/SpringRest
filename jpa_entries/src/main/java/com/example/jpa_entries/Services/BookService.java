package com.example.jpa_entries.Services;

import com.example.jpa_entries.Entities.Books;
import com.example.jpa_entries.Repositories.BookRepository;
import com.example.jpa_entries.dto.BookDTO;
import com.example.jpa_entries.Exceptions.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    @Autowired
    ModelMapper modelMapper;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Books getBook(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }


    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id))
            throw new ResourceNotFoundException("Book not found with id " + id);
        bookRepository.deleteById(id);
    }

        public List<BookDTO> getAllBooksDTO() {
        // return bookRepository.findAll()
        //         .stream()
        //         .map(b -> new BookDTO(b.getId(), b.getTitle(), b.getAuthor()))
        //         .toList();

        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class)) // automatic mapping
                .toList();
    }

    public BookDTO convertToDTO(Books book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
