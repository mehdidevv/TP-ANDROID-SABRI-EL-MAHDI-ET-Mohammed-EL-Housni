package com.mashy.tp1.ui.upcoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashy.tp1.DetailsMovieApplication;
import com.mashy.tp1.services.classes.Movie;
import com.mashy.tp1.adapters.MovieAdapter;
import com.mashy.tp1.R;
import com.mashy.tp1.services.classes.TMDB_Response;
import com.mashy.tp1.services.MoviesService;
import com.mashy.tp1.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingFragment extends Fragment implements MovieAdapter.OnNoteListener{

    List<Movie> movies=  new ArrayList<Movie>();;
    int count = 0;
    int pageId = 1;
    MovieAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);
        RecyclerView rvMovies = root.findViewById(R.id.rvUpcomingMovies);
        NestedScrollView nestedSV = root.findViewById(R.id.idNestedSV);

        MovieAdapter.OnNoteListener context= this;

        getData(rvMovies,context);
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    count++;
                    if (count < 20) {
                        pageId++;
                        updateRecycleView();
                    }
                }
            }
        });
        return root;
}
    private void updateRecycleView() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesService movieService =  retrofit.create(MoviesService.class);
        DetailsMovieApplication app = (DetailsMovieApplication) getActivity().getApplicationContext();

        movieService.popularMoviesList(app.getLang(),pageId).enqueue(new Callback<TMDB_Response>() {
            @Override
            public void onResponse(Call<TMDB_Response> call, Response<TMDB_Response> response) {

                for (Movie m : response.body().getResults()){
                    movies.add(m);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<TMDB_Response> call, Throwable t) {
                t.getCause();            }
        });
    }
    private void getData(RecyclerView rvMovies,MovieAdapter.OnNoteListener context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesService movieService =  retrofit.create(MoviesService.class);
        DetailsMovieApplication app = (DetailsMovieApplication) getActivity().getApplicationContext();
        movieService.upcomingMoviesList(app.getLang(),pageId).enqueue(new Callback<TMDB_Response>() {
            @Override
            public void onResponse(Call<TMDB_Response> call, Response<TMDB_Response> response) {
                movies=response.body().getResults();
                adapter = new MovieAdapter(movies,getContext(),context);

                rvMovies.setAdapter(adapter);

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                rvMovies.setLayoutManager(mLayoutManager);            }

            @Override
            public void onFailure(Call<TMDB_Response> call, Throwable t) {
                t.getCause();            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        DetailsMovieApplication app = (DetailsMovieApplication) getActivity().getApplicationContext();
        app.setDetailsMovie(movies.get(position));
        Intent intent = new Intent(getContext().getApplicationContext(), DetailsActivity.class);
        startActivity(intent);
    }
}