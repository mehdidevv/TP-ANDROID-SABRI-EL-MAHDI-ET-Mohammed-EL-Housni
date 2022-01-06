package com.mashy.tp1.services.classes;

import java.util.List;

public class GenresResponse {
    private List<Genre> genres;

    public GenresResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
