package com.example.bookapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.bookapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/** Activitatea pentru afisarea lucrurilor legate de cont */
public class AccountActivity extends AppCompatActivity {
    public static boolean darkTheme = false;
    private GoogleSignInClient signInClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(this, options);

        Switch s = findViewById(R.id.darkModeSwitch);

        if(darkTheme) {
            s.toggle();
        }

        s.setOnClickListener(view -> {
            // Salvarea preferintei pentru Dark Theme in SharedPreferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            if(s.isChecked()) {
                darkTheme = true;
                toggleDarkModeOn();
                prefs.edit().putBoolean("DARK_MODE", true).apply();
            } else {
                darkTheme = false;
                toggleDarkModeOff();
                prefs.edit().putBoolean("DARK_MODE", false).apply();
            }
        });

        findViewById(R.id.signInButton).setOnClickListener(view -> signIn());
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(darkTheme) {
            toggleDarkModeOn();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            updateUI(null);
        }
    }

    private void signIn() {
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    /** Modificarea UI-ului pentru cazurile in care user-ul este sau nu logged in */
    private void updateUI(GoogleSignInAccount account) {
        ImageView i = findViewById(R.id.profilePicture);
        Button b = findViewById(R.id.signInButton);
        TextView t = findViewById(R.id.idTextView);
        TextView t2 = findViewById(R.id.emailTextView);

        if(account != null) {
            i.setVisibility(ImageView.VISIBLE);
            b.setVisibility(Button.INVISIBLE);
            t.setVisibility(TextView.VISIBLE);
            t2.setVisibility(TextView.VISIBLE);

            i.setImageURI(account.getPhotoUrl());
            t.setText(account.getDisplayName());
            t2.setText(account.getEmail());
        } else {
            i.setVisibility(ImageView.INVISIBLE);
            b.setVisibility(Button.VISIBLE);
            t.setVisibility(TextView.INVISIBLE);
            t2.setVisibility(TextView.INVISIBLE);
        }
    }

    private void toggleDarkModeOn() {
        findViewById(R.id.accountConstraintLayout).setBackgroundColor(Color.rgb(50, 50, 50));

        Button b = findViewById(R.id.signInButton);
        b.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corners_rectangle_dark));
        b.setTextColor(Color.rgb(220, 202, 47));

        Switch s = findViewById(R.id.darkModeSwitch);
        s.setTextColor(Color.rgb(220, 202, 47));

        TextView t = findViewById(R.id.idTextView);
        t.setTextColor(Color.rgb(220, 202, 47));

        t = findViewById(R.id.emailTextView);
        t.setTextColor(Color.rgb(220, 202, 47));
    }

    private void toggleDarkModeOff() {
        findViewById(R.id.accountConstraintLayout).setBackgroundColor(Color.argb(0, 0, 0, 0));

        Button b = findViewById(R.id.signInButton);
        b.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corners_rectangle));
        b.setTextColor(Color.rgb(0, 0, 0));

        Switch s = findViewById(R.id.darkModeSwitch);
        s.setTextColor(Color.rgb(0, 0, 0));

        TextView t = findViewById(R.id.idTextView);
        t.setTextColor(Color.rgb(0, 0, 0));

        t = findViewById(R.id.emailTextView);
        t.setTextColor(Color.rgb(0, 0, 0));
    }
}