package com.example.jpa_entries.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String author;

    public BookDTO(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    
}
