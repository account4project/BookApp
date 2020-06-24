package com.example.bookapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/** Clasa factory pentru clase de tipul BookViewModel - creeaza clase de tipul BookViewModel*/
public class BookFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private final Application application;

    public BookFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookViewModel(application);
    }
}
