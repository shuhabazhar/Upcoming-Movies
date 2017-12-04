package com.upcomingmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.upcomingmovies.R;
import com.upcomingmovies.activity.MovieDetailsActivity;
import com.upcomingmovies.model.UpcomingMovies;
import com.upcomingmovies.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Shuhab abs-pc-2f-28 on 1/12/17.
 */

public class UpcomingMoviesAdapter extends RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMovieViewHolder> {
    Context context;
    ArrayList<UpcomingMovies> upcomingMoviesList;

    public UpcomingMoviesAdapter(Context context, ArrayList<UpcomingMovies> upcomingMoviesList) {
        this.context = context;
        this.upcomingMoviesList = upcomingMoviesList;
    }

    @Override
    public UpcomingMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_upcoming_movies, parent, false);
        return new UpcomingMovieViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(UpcomingMovieViewHolder holder, int position) {
        Picasso.with(context)
                .load(Constant.IMAGE_BASE_URL + upcomingMoviesList.get(position).getPosterImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgPoster);
        holder.txtMovieName.setText(upcomingMoviesList.get(position).getTitle());
        holder.txtReleaseDate.setText(upcomingMoviesList.get(position).getReleaseDate());
        String isAdult;
        if (upcomingMoviesList.get(position).getIsAdult()) {
            isAdult = "(A)";
        } else {
            isAdult = "U/A";
        }
        holder.txtIsAdult.setText(isAdult);

    }

    @Override
    public int getItemCount() {
        return upcomingMoviesList.size();
    }

    public class UpcomingMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPoster;
        TextView txtMovieName, txtReleaseDate, txtIsAdult;
        LinearLayout llMainLayout;

        public UpcomingMovieViewHolder(View itemView) {
            super(itemView);
            imgPoster = (ImageView) itemView.findViewById(R.id.imgPoster);
            txtMovieName = (TextView) itemView.findViewById(R.id.txtMovieName);
            txtReleaseDate = (TextView) itemView.findViewById(R.id.txtReleaseDate);
            txtIsAdult = (TextView) itemView.findViewById(R.id.txtIsAdult);
            llMainLayout = (LinearLayout) itemView.findViewById(R.id.llMainLayout);
            llMainLayout.setOnClickListener(this);
            llMainLayout.setTag(itemView);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llMainLayout:
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(Constant.MOVIE_ID, upcomingMoviesList.get(getAdapterPosition()).getMovieId());
                    intent.putExtra(Constant.MOVIE_NAME, upcomingMoviesList.get(getAdapterPosition()).getTitle());
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
