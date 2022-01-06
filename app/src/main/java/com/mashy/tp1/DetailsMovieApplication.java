package com.mashy.tp1;

import android.app.Application;

import com.mashy.tp1.services.classes.Movie;

public class DetailsMovieApplication extends Application {
    private Movie detailsMovie;
    private String lang;

    @Override
    public void onCreate() {
        super.onCreate();
        this.detailsMovie=null;
        this.lang="en-US";
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Movie getDetailsMovie() {
        return detailsMovie;
    }

    public void setDetailsMovie(Movie detailsMovie) {
        this.detailsMovie = detailsMovie;
    }
}
