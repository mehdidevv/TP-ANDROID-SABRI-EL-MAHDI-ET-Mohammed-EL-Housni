package com.mashy.tp1.ui.popular;

import androidx.lifecycle.ViewModel;

import com.mashy.tp1.services.classes.Movie;

import java.util.ArrayList;

public class PopularViewModel extends ViewModel {

    private ArrayList<Movie> movies;

    public PopularViewModel() {
        movies = new ArrayList<Movie>();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}