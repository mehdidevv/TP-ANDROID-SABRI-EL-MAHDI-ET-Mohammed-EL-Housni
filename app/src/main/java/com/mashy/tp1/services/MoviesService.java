package com.mashy.tp1.services;


import com.mashy.tp1.services.classes.GenresResponse;
import com.mashy.tp1.services.classes.TMDB_Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface  MoviesService {

    @GET("popular?api_key=68a94f4b6795ba3686c3b737edbec1bc")
    public abstract Call<TMDB_Response> popularMoviesList(@Query("language") String lang, @Query("page") int page);

    @GET("upcoming?api_key=68a94f4b6795ba3686c3b737edbec1bc")
    public abstract Call<TMDB_Response> upcomingMoviesList(@Query("language") String lang,@Query("page") int page);

    @GET("search/movie?api_key=68a94f4b6795ba3686c3b737edbec1bc")
    public abstract Call<TMDB_Response> search(@Query("query") String query);

    @GET("genre/movie/list?api_key=68a94f4b6795ba3686c3b737edbec1bc&language=en-US")
    public abstract Call<GenresResponse> getStringName();


}
