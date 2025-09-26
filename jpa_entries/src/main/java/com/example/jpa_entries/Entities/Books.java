package com.example.jpa_entries.Entities;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity // to create a table with name BOOKS(upper case class name)
public class Books {

    @Id // make the field below primary key 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment the value
    private Long id;

    @ManyToOne
    @JoinColumn(name = "publisher_id") // FK column
    @JsonIgnore
    private Publisher publisher;


    private String title;
    private String author;
    

    public Books() {}       // default constructor

    public Books(String title, String author, Publisher publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    // getter & setter for publisher (you already have @ManyToOne)
    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
}
