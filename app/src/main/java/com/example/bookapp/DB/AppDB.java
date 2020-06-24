package com.example.bookapp.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bookapp.DAO.BookDao;
import com.example.bookapp.DB_Entities.Book;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Clasa ce reprezinta baza de date - scrisa dupa design pattern-ul Singleton pentru a evita existenta unui numar mare de conexiuni catre baza de date */
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract BookDao bookDao();

    private static volatile AppDB INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    /** Folosit pentru aplicarea operatiilor de tipul INSERT sau UPDATE asupra bazei de date deoarece aceastea nu folosesc LiveData */
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /** Callback apelat de fiecare data cand baza de date este deschisa
     *  Goleste si apoi populeaza baza de date cu cateva carti pentru a simula un server unde s-ar afla in realitate baza de date ce contine toate cartile
     **/
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                BookDao dao = INSTANCE.bookDao();

                dao.deleteAll();

                dao.addBook(new Book(
                        "Harry Potter and the Prisoner of Azkaban",
                        "J.K. Rowling",
                        "Harry Potter, Ron and Hermione return to Hogwarts School of Witchcraft and Wizardry for their third year of study," +
                                " where they delve into the mystery surrounding an escaped prisoner who poses a dangerous threat to the young wizard.",
                        5.0f,
                        225.0f,
                        200,
                        true
                        ));

                dao.addBook(new Book(
                        "Rita Hayworth and Shawshank Redemption",
                        "Stephen King",
                        "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                        4.5f,
                        199.99f,
                        150,
                        false
                ));

                dao.addBook(new Book(
                        "The Lord of the Rings The Return of the King",
                        "J.R.R Tolkien",
                        "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
                        4.8f,
                        250.0f,
                        300,
                        false
                ));

                dao.addBook(new Book(
                        "Fight Club",
                        "Chuck Palahniuk",
                        "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.",
                        4.0f,
                        180.0f,
                        150,
                        true
                ));

                dao.addBook(new Book(
                        "Forrest Gump",
                        "Winston Groom",
                        "The presidencies of Kennedy and Johnson, the events of Vietnam," +
                                " Watergate and other historical events unfold through the perspective of an Alabama man with an IQ of 75," +
                                " whose only desire is to be reunited with his childhood sweetheart.",
                        4.6f,
                        219.99f,
                        400,
                        true
                ));

                dao.addBook(new Book(
                        "The Silence of the Lambs",
                        "Thomas Harris",
                        "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer," +
                                " a madman who skins his victims.",
                        4.9f,
                        300.0f,
                        225,
                        false
                ));

                dao.addBook(new Book(
                        "The Green Mile",
                        "Stephen King",
                        "The lives of guards on Death Row are affected by one of their charges: a black man accused of child murder and rape, yet who has a mysterious gift.",
                        4.5f,
                        200.0f,
                        250,
                        true
                ));

                dao.addBook(new Book(
                        "Sense and Sensibility",
                        "Jane Austen",
                        "A famous 19th century novel set in southwest England following the development of the upper-class Dashwood family. " +
                                "Will they be able to find love and survive in a money-driven society, or will they need to give up the luxuries of their life?",
                        4.3f,
                        150.0f,
                        170,
                        true
                ));

                dao.addBook(new Book(
                        "The Hate U Give",
                        "Angie Thomas",
                        "Modern bestseller that focuses on the issues black people face in poor neighbourhoods - gangs, injustice, the danger of day to day life. " +
                                "Story is told from the point of view of 16-year-old Starr Carter who quickly realises the sort of world she lives in.",
                        5.0f,
                        300.0f,
                        500,
                        false
                ));

                dao.addBook(new Book(
                        "Educated",
                        "Tara Westover",
                        "The journey of Tara Westover,  who was raised in a survivalist family, where school and education are considered alienating and brainwashing. " +
                                "Tara narrates her personal life to the most minute details, outgrowing her family and having her life changed radically, " +
                                "the catalyst being getting accepted to Trinity College, Cambridge.",
                        3.5f,
                        165.0f,
                        130,
                        true
                ));
            });
        }
    };

    public static AppDB getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDB.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "book_database").addCallback(roomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }
}
