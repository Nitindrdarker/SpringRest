package com.example.jpa_entries.Entities;



import jakarta.persistence.*;

@Entity // to create a table with name BOOKS(upper case class name)
public class Books {

    @Id // make the field below primary key 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment the value
    private Long id;

    private String title;
    private String author;

    public Books() {}       // default constructor

    public Books(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
