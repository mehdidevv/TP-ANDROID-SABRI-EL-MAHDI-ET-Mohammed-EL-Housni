package com.mashy.tp1.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.mashy.tp1.DetailsMovieApplication;
import com.mashy.tp1.R;
import com.mashy.tp1.services.classes.Genre;
import com.mashy.tp1.services.classes.GenresResponse;
import com.mashy.tp1.services.classes.Movie;
import com.mashy.tp1.services.MoviesService;
import com.mashy.tp1.sqlLite.MyDatabaseHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    Movie movie;
    MyDatabaseHelper db = new MyDatabaseHelper(this);
    Drawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView picture = (ImageView) findViewById(R.id.picture);
        TextView description = (TextView) findViewById(R.id.descritption);
        TextView rDate = (TextView) findViewById(R.id.releaseDate);
        TextView genres = (TextView) findViewById(R.id.genres);
        description.setMovementMethod(new ScrollingMovementMethod());

        DetailsMovieApplication app = (DetailsMovieApplication) getApplicationContext();
        movie = app.getDetailsMovie();

        getSupportActionBar().setTitle(movie.getTitle());
        description.setText(movie.getOverview());
        rDate.setText(movie.getRelease_date());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesService movieService =  retrofit.create(MoviesService.class);
        if (movie.getGenre_ids()!= null) {
            for (int id : movie.getGenre_ids()) {
                movieService.getStringName().enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                        genres.setText(genres.getText() + "- " + getGenreName(response.body().getGenres(),id) + "\n");
                    }

                    @Override
                    public void onFailure(Call<GenresResponse> call, Throwable t) {
                        t.getCause();
                    }
                });
            }
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop());
        Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/original"+movie.getBackdrop_path()).apply(requestOptions).into(picture);


    }

    private String getGenreName(List<Genre> list, int id) {
        for (Genre g : list){
            if(g.getId()==id) return g.getName();
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (!db.movieExists(movie)){
            if (id == R.id.addFav) {
                db.addMovie(movie);
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
            }
        }
        else {
            db.deleteMovie(movie);
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(R.color.white ), PorterDuff.Mode.SRC_ATOP);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.appbar,menu);
        drawable = menu.getItem(0).getIcon();
        if(drawable != null) {
            drawable.mutate();
            if (db.movieExists(movie))
                drawable.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
            else
                drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        }
        return super.onCreateOptionsMenu(menu);
    }
}