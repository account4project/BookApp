package com.example.bookapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookapp.DB_Entities.Book;

import java.util.List;

/** ViewModel pentru clasa Book - este folosita in activitati si face conexiunea cu repository-ul clasei Book */
public class BookViewModel extends AndroidViewModel {
    private LiveData<List<Book>> allBooks;
    private LiveData<List<Book>> bestSellers;
    private LiveData<List<Book>> myBooks;
    BookRepository bookRepository;

    public BookViewModel(Application application) {
        super(application);
        bookRepository = new BookRepository(application);

        allBooks = bookRepository.getAllBooks();
        bestSellers = bookRepository.getBestSellers();
        myBooks = bookRepository.getMyBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return this.allBooks;
    }

    public LiveData<List<Book>> getBestSellers() {
        return this.bestSellers;
    }

    public LiveData<List<Book>> getMyBooks() {
        return this.myBooks;
    }

    public void updateBook(Book book) {
        bookRepository.updateBook(book);
    }
}
