package com.example.bookapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.bookapp.Activities.MainActivity;
import com.example.bookapp.DAO.BookDao;
import com.example.bookapp.DB.AppDB;
import com.example.bookapp.DB_Entities.Book;

/** Scheduled worker care adauga o noua carte si lanseaza o notificare */
public class AddNewBookWorker extends Worker {
    public AddNewBookWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    /** Metoda care indica ce are de facut Worker-ul - ruleaza intr-un thread separat deci putem folosi operatii asupra bazei de date */
    @NonNull
    @Override
    public Result doWork() {
        AppDB instance = AppDB.getInstance(this.getApplicationContext());
        BookDao bookDao = instance.bookDao();

        bookDao.addBook(new Book(
                "Added by worker",
                "Added by worker",
                "Added by worker",
                0.0f,
                0.0f,
                0,
                false
        ));

        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent, 0);

        /** Construim notificarea pentru adaugarea unei noi carti */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), "BOOK_APP_CHANNEL_1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New book available!")
                .setContentText("We have just added a new book to the database.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getApplicationContext());

        /** Lansam notificarea */
        notificationManager.notify(1, builder.build());
        return Result.success();
    }
}
