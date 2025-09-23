package com.example.jpa_entries.Entities;





import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One publisher can have many books
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Books> books = new ArrayList<>();

    // Constructors
    public Publisher() {}
    public Publisher(String name) {
        this.name = name;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Books> getBooks() { return books; }
    public void setBooks(List<Books> books) { this.books = books; }
}
