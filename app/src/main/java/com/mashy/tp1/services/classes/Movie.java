package com.mashy.tp1.services.classes;

public class Movie {

    private int id;
    private String title;
    private String overview;
    private String release_date;
    private String poster_path;
    private String backdrop_path;
    private int[]  genre_ids;

    public Movie(int id, String title, String overview, String release_date, String poster_path, String backdrop_path, int[] genre_ids) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
    }

    public Movie(int id, String title, String overview, String release_date, String poster_path, String backdrop_path) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public Movie(int id, String title, String overview, String poster_path) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Movie(int id, String title, String overview, String poster_path, int[] genre_ids) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.genre_ids = genre_ids;
    }
}
