package com.example.bookapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bookapp.DAO.BookDao;
import com.example.bookapp.DB.AppDB;
import com.example.bookapp.DB_Entities.Book;

import java.util.List;

/** Repository-ul clasei Book - face legatura intre DAO-ul si ViewModel-ul clasei Book */
public class BookRepository {
    private LiveData<List<Book>> allBooks;
    private LiveData<List<Book>> bestSellers;
    private LiveData<List<Book>> myBooks;
    private BookDao bookDao;

    BookRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        bookDao = db.bookDao();

        allBooks = bookDao.getAll();
        bestSellers = bookDao.getBestSellers();
        myBooks = bookDao.getMyBooks();
    }

    LiveData<List<Book>> getAllBooks() {
        return this.allBooks;
    }

    LiveData<List<Book>> getBestSellers() {
        return this.bestSellers;
    }

    LiveData<List<Book>> getMyBooks() {
        return this.myBooks;
    }

    void updateBook(Book book) {
        AppDB.databaseWriteExecutor.execute(() -> bookDao.updateBook(book));
    }
}

