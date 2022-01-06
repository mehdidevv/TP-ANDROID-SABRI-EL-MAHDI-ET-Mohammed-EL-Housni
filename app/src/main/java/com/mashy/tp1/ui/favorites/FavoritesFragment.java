package com.mashy.tp1.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashy.tp1.DetailsMovieApplication;
import com.mashy.tp1.R;
import com.mashy.tp1.adapters.MovieAdapter;
import com.mashy.tp1.services.classes.Movie;
import com.mashy.tp1.sqlLite.MyDatabaseHelper;
import com.mashy.tp1.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements MovieAdapter.OnNoteListener {

    private FavoritesViewModel notificationsViewModel;
    List<Movie> movies=  new ArrayList<Movie>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        RecyclerView rvMovies = root.findViewById(R.id.rvFavoritesMovies);
        MyDatabaseHelper db = new MyDatabaseHelper(getContext());
        movies= db.getAllMovies();

        MovieAdapter adapter = new MovieAdapter(movies,getContext(),this);
        rvMovies.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMovies.setLayoutManager(mLayoutManager);
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