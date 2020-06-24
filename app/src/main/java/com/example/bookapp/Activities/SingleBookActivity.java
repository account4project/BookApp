package com.example.bookapp.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookapp.BookFactory;
import com.example.bookapp.BookViewModel;
import com.example.bookapp.DB_Entities.Book;
import com.example.bookapp.R;

/** Activitatea pentru afisarea individuala a unei carti */
public class SingleBookActivity extends AppCompatActivity {
    private Book book;
    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book);

        Bundle extras = getIntent().getExtras();

        book = new Book(extras.getString("bookTitle"),
                extras.getString("bookAuthor"),
                extras.getString("bookDesc"),
                extras.getFloat("bookRating"),
                extras.getFloat("bookPrice"),
                extras.getInt("bookNumOfSales"),
                extras.getBoolean("bookBought"));

        bookViewModel = new ViewModelProvider(this, new BookFactory(this.getApplication())).get(BookViewModel.class);
    }

    /** UI setup */
    @Override
    protected void onStart() {
        super.onStart();

        ImageView image = findViewById(R.id.bookCover);
        final int id = getResources().getIdentifier(book.coverImageName, "drawable", getPackageName());
        image.setImageResource(id);

        Button b = findViewById(R.id.buyButton);

        if(book.bought) {
            b.setText("Read");
        }

        b.setOnClickListener(view -> {
            if(book.bought) {

            } else {
                book.bought = true;
                bookViewModel.updateBook(book);
            }
        });

        TextView t = findViewById(R.id.bookDescription);
        t.setText(book.description);

        t = findViewById(R.id.bookInfo);
        String text = book.title + "\nBy " + book.author + "\nPrice: " + book.price + "LEI\nTimes sold: " + book.numOfSales;
        t.setText(text);
    }

    /** Setari pentru Dark Theme */
    @Override
    protected void onResume() {
        super.onResume();

        Button b = findViewById(R.id.buyButton);
        TextView t = findViewById(R.id.bookDescription);
        TextView t2 = findViewById(R.id.bookInfo);

        if(AccountActivity.darkTheme) {
            findViewById(R.id.singleBookConstraintLayout).setBackgroundColor(Color.rgb(50, 50, 50));
            b.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corners_rectangle_dark));
            b.setTextColor(Color.rgb(220, 202, 47));
            t.setTextColor(Color.rgb(220, 202, 47));
            t2.setTextColor(Color.rgb(220, 202, 47));
        } else {
            findViewById(R.id.singleBookConstraintLayout).setBackgroundColor(Color.argb(0, 0, 0, 0));
            b.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corners_rectangle));
            b.setTextColor(Color.rgb(0, 0, 0));
            t.setTextColor(Color.rgb(0, 0, 0));
            t2.setTextColor(Color.rgb(0, 0, 0));
        }
    }
}
