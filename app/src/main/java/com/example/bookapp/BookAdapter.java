package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.Activities.AccountActivity;
import com.example.bookapp.Activities.SingleBookActivity;
import com.example.bookapp.DB_Entities.Book;

import java.util.List;

/** Adaptor custom pentru RecyclerView */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books;
    private final Context context;

    /** Retine View-ul care trebuie aplicat tuturor elementelor din RecyclerView */
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private final View bookView;

        private BookViewHolder(View view) {
            super(view);
            this.bookView = view;
        }
    }

    public BookAdapter(final Context context) {
        this.context = context;
    }

    /** Stocam View-ul pe care il dorim aplicat tuturor elementelor din RecyclerView in BookViewHolder */
    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_element, parent, false);

        return new BookViewHolder(view);
    }

    /** Metoda care face legatura de date intre elementul din RecyclerView de pe pozitia position cu obiectul din lista de carti de pe aceeasi pozitie */
    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        ImageView image = holder.bookView.findViewById(R.id.bookCover);
        final int id = context.getResources().getIdentifier(books.get(position).coverImageName, "drawable", context.getPackageName());
        image.setImageResource(id);

        Button b = holder.bookView.findViewById(R.id.bookTitle);
        b.setText(books.get(position).title);
        b.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleBookActivity.class);
            intent.putExtra("bookTitle", books.get(position).title);
            intent.putExtra("bookAuthor", books.get(position).author);
            intent.putExtra("bookDesc", books.get(position).description);
            intent.putExtra("bookRating", books.get(position).rating);
            intent.putExtra("bookPrice", books.get(position).price);
            intent.putExtra("bookNumOfSales", books.get(position).numOfSales);
            intent.putExtra("bookBought", books.get(position).bought);
            context.startActivity(intent);
        });

        TextView t = holder.bookView.findViewById(R.id.bookRating);
        t.setText("Rating: " + books.get(position).rating + "/5");

        TextView t2 = holder.bookView.findViewById(R.id.bookPrice);
        t2.setText("Price: " + books.get(position).price + " LEI");

        if(AccountActivity.darkTheme) {
            b.setTextColor(Color.rgb(220, 202, 47));
            t.setTextColor(Color.rgb(220, 202, 47));
            t2.setTextColor(Color.rgb(220, 202, 47));
        } else {
            b.setTextColor(Color.rgb(0, 0, 0));
            t.setTextColor(Color.rgb(0, 0, 0));
            t2.setTextColor(Color.rgb(0, 0, 0));
        }
    }

    public void setBooks(List<Book> books){
        this.books = books;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(books != null) {
            return books.size();
        }

        return 0;
    }
}
