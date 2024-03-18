package com.example.loginsignupsqlite;

import androidx.annotation.NonNull;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private float rating;

    // Constructor
    public Book(int id, String title, String author, String isbn, float rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.rating = rating;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    @NonNull
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", rating=" + rating +
                '}';
    }
}

