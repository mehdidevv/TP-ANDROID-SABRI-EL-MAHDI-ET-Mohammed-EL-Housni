package com.mashy.tp1.services.classes;

import java.util.List;

public class TMDB_Response {
    private String page;
    private List<Movie> results;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

}
