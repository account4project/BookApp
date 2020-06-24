package com.example.bookapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.bookapp.AddNewBookWorker;
import com.example.bookapp.BookAdapter;
import com.example.bookapp.BookFactory;
import com.example.bookapp.BookViewModel;
import com.example.bookapp.R;

import java.util.concurrent.TimeUnit;

/**
 * DONE:
 *
 * 4 activities
 * RecyclerView and custom Adapter
 * Local DB storage with SQLite and Room
 * Store something in Shared Preferences
 * Auth.
 * Background Task - Periodic Worker
 * Notifications that open a specified activity
 * 3 shape drawables + landscape
 *
 * TODO:
 * Text color doesn't always update properly according to Dark Theme because of how the RecyclerView Adapter handles data
 * SingleBookActivity portrait and landscape display issues
 * Session
 * 3 Server Requests with JSON parse
 * One custom anim.
 * 6 fragments
 **/

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creearea canalului pe care vom transmite notificarea
        createNotificationChannel();

        // Construirea si activarea Periodic Worker-ului - se va activa in fiecare 15 minute - 15 minute este valoarea minima
        WorkRequest addBookWorkRequest = new PeriodicWorkRequest.Builder(AddNewBookWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS).build();
        WorkManager.getInstance(this).enqueue(addBookWorkRequest);

        // Importarea preferintelor pentru aplicatie - in cazul acesta pentru Dark Theme
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        AccountActivity.darkTheme = prefs.getBoolean("DARK_MODE", false);

        RecyclerView recyclerView = findViewById(R.id.allBooksRecyclerView);
        recyclerView.setHasFixedSize(true);

        // Cate elemente vor fi afisate de RecyclerView pe un rand in functie de orientarea telefonului
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        BookViewModel bookViewModel = new ViewModelProvider(this, new BookFactory(this.getApplication())).get(BookViewModel.class);

        BookAdapter adapter = new BookAdapter(this);
        recyclerView.setAdapter(adapter);

        // Observer pentru LiveData, acesta este apelat de fiecare data cand obiectele din LiveData se modifica
        // In cazul acesta actualizeaza obiectele din RecyclerView si anunta Adaptor-ul ca a avut loc o modificare si trebuie sa modifice RecyclerView-ul
        bookViewModel.getAllBooks().observe(this, adapter::setBooks);
    }

    /** Setari pentru Dark Theme */
    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.allBooksRecyclerView);

        if(AccountActivity.darkTheme) {
            recyclerView.setBackgroundColor(Color.rgb(50, 50, 50));
        } else {
            recyclerView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }
    }

    /** Creearea canalului pe care vom transmite notificarea */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BOOK_APP_CHANNEL";
            String description = "BOOK_APP_CHANNEL";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("BOOK_APP_CHANNEL_1", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}