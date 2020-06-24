package com.example.bookapp.Activities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.BookAdapter;
import com.example.bookapp.BookFactory;
import com.example.bookapp.BookViewModel;
import com.example.bookapp.R;

/** Activitatea pentru afisarea cartilor best seller */
public class BestSellersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_sellers);

        RecyclerView recyclerView = findViewById(R.id.bestSellersRecyclerView);
        recyclerView.setHasFixedSize(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        BookViewModel bookViewModel = new ViewModelProvider(this, new BookFactory(this.getApplication())).get(BookViewModel.class);

        BookAdapter adapter = new BookAdapter(this);
        recyclerView.setAdapter(adapter);

        bookViewModel.getBestSellers().observe(this, adapter::setBooks);
    }

    /** Setari pentru Dark Theme */
    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.bestSellersRecyclerView);

        if(AccountActivity.darkTheme) {
            recyclerView.setBackgroundColor(Color.rgb(50, 50, 50));
        } else {
            recyclerView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }
    }
}
