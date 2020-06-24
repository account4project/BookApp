package com.example.bookapp.DB_Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/** Clasa ce reprezinta entitatea Book din baza de date - membrii sunt publici deci Room se ocupa automat de ei */
@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    @NonNull
    public String author;

    @NonNull
    public String description;

    public float rating;

    public float price;

    @ColumnInfo(name = "num_of_sales")
    public int numOfSales;

    public boolean bought;

    @NonNull
    @ColumnInfo(name = "cover_image_name")
    public String coverImageName;

    public Book(@NonNull String title, @NonNull String author, @NonNull String description, float rating, float price, int numOfSales, boolean bought) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.numOfSales = numOfSales;
        this.bought = bought;
        this.coverImageName = (title + author).toLowerCase().replaceAll("\\s+", "").replaceAll("\\.", "");
    }

    @Ignore
    public Book(final Book book) {
        this.id = book.id;
        this.title = book.title;
        this.author = book.author;
        this.description = book.description;
        this.rating = book.rating;
        this.price = book.price;
        this.numOfSales = book.numOfSales;
        this.bought = book.bought;
        this.coverImageName = book.coverImageName;
    }
}
