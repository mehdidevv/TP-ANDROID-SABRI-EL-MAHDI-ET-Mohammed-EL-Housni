package com.mashy.tp1.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashy.tp1.DetailsMovieApplication;
import com.mashy.tp1.R;
import com.mashy.tp1.adapters.MovieAdapter;
import com.mashy.tp1.services.classes.Movie;
import com.mashy.tp1.services.MoviesService;
import com.mashy.tp1.services.classes.TMDB_Response;
import com.mashy.tp1.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment implements MovieAdapter.OnNoteListener{
    List<Movie> movies=  new ArrayList<Movie>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView rvMovies = root.findViewById(R.id.rvSearch);
        Button search_btn = root.findViewById(R.id.search_btn);
        EditText editText = root.findViewById(R.id.searchInput);

        MovieAdapter.OnNoteListener conn= this;

        search_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (editText.getText().length() > 0){
                 Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                    MoviesService movieService =  retrofit.create(MoviesService.class);
                    DetailsMovieApplication app = (DetailsMovieApplication) getActivity().getApplicationContext();

                    movieService.search(editText.getText().toString()).enqueue(new Callback<TMDB_Response>() {
                        @Override
                        public void onResponse(Call<TMDB_Response> call, Response<TMDB_Response> response) {
                            movies=response.body().getResults();
                            MovieAdapter adapter = new MovieAdapter(movies,getContext(),conn);

                            rvMovies.setAdapter(adapter);

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                            rvMovies.setLayoutManager(mLayoutManager);

                        }

                        @Override
                        public void onFailure(Call<TMDB_Response> call, Throwable t) {
                            t.getCause();            }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Please Enter a text", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    @Override
    public void onNoteClick(int position) {
        DetailsMovieApplication app = (DetailsMovieApplication) getActivity().getApplicationContext();
        app.setDetailsMovie(movies.get(position));
        Intent intent = new Intent(getContext().getApplicationContext(), DetailsActivity.class);
        startActivity(intent);

    }
}

