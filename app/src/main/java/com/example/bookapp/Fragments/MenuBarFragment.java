package com.example.bookapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bookapp.Activities.AccountActivity;
import com.example.bookapp.Activities.BestSellersActivity;
import com.example.bookapp.Activities.MyBooksActivity;
import com.example.bookapp.R;

/** Fragment pentru "Task Bar"-ul din partea de jos a aplicatiei - folosit de mai multe Activitati */
public class MenuBarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_bar, container, false);
    }

    /** OnClickListeners pentru cele 3 butoane */
    @Override
    public void onStart() {
        super.onStart();

        this.getView().findViewById(R.id.buttonBestSellers).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BestSellersActivity.class);
            v.getContext().startActivity(intent);
        });

        this.getView().findViewById(R.id.buttonAccount).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AccountActivity.class);
            v.getContext().startActivity(intent);
        });

        this.getView().findViewById(R.id.buttonMyBooks).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MyBooksActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    /** Setari pentru Dark Theme */
    @Override
    public void onResume() {
        super.onResume();

        if(AccountActivity.darkTheme) {
            Button b = this.getView().findViewById(R.id.buttonBestSellers);
            b.setBackground(ContextCompat.getDrawable(this.getActivity(), R.drawable.bordered_rectangle_dark));
            b.setTextColor(Color.rgb(220, 202, 47));

            b = this.getView().findViewById(R.id.buttonAccount);
            b.setBackground(ContextCompat.getDrawable(this.getActivity(), R.drawable.bordered_rectangle_dark));
            b.setTextColor(Color.rgb(220, 202, 47));

            b = this.getView().findViewById(R.id.buttonMyBooks);
            b.setBackground(ContextCompat.getDrawable(this.getActivity(), R.drawable.bordered_rectangle_dark));
            b.setTextColor(Color.rgb(220, 202, 47));
        }
    }
}
