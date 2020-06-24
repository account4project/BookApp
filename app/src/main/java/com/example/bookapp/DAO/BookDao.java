package com.example.bookapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookapp.DB_Entities.Book;

import java.util.List;

/** Data Access Object pentru entitatea Book - contine operatiile ce pot fi aplicate asupra bazei de date
 *  Room nu permite rularea operatiilor pe thread-ul principal deoarece poate duce la incetinirea sau blocarea UI-ului
 *  Operatiile care folosesc LiveData sunt rulate asincron pe un alt thread decat cel principal al UI-ului
 *  Operatiile care nu folosesc LiveData trebuiesc rulate explicit intr-un alt thread
 **/
@Dao
public interface BookDao {
    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAll();

    @Query("SELECT * FROM books ORDER BY num_of_sales DESC LIMIT 5")
    LiveData<List<Book>> getBestSellers();

    @Query("SELECT * FROM books WHERE bought <> 0")
    LiveData<List<Book>> getMyBooks();

    @Query("DELETE FROM books")
    void deleteAll();

    @Insert
    void addBook(Book book);

    @Update
    void updateBook(Book book);
}
