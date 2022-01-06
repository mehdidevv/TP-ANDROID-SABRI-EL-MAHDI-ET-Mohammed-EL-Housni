package com.mashy.tp1.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mashy.tp1.R;
import com.mashy.tp1.services.classes.Movie;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>  {
    private final List<Movie> mMovies;
    private final Context context;
    private OnNoteListener mOnNoteListener;

    public MovieAdapter(List<Movie> mMovies, Context context,OnNoteListener mOnNoteListener) {
        this.mMovies = mMovies;
        this.context = context;
        this.mOnNoteListener =mOnNoteListener;
    }
    public MovieAdapter(List<Movie> mMovies, Context context) {
        this.mMovies = mMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View MovieView = inflater.inflate(R.layout.movie_view, parent, false);
        return new ViewHolder(MovieView,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        ImageView imageImageView = holder.pictureImageView;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(context).load("https://image.tmdb.org/t/p/original"+movie.getPoster_path()).apply(requestOptions).into(imageImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView pictureImageView;
        OnNoteListener mOnNoteListener;

        public ViewHolder(View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            pictureImageView = (ImageView) itemView.findViewById(R.id.picture);

            mOnNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mOnNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
