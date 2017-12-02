package com.upcomingmovies.model;

/**
 * Created by Shuhab abs-pc-2f-28 on 1/12/17.
 */

public class UpcomingMovies {
    private String title;
    private String posterImage;
    private String releaseDate;
    private Boolean isAdult;
    private String movieId;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setIsAdult(Boolean isAdult) {
        this.isAdult = isAdult;
    }

    public Boolean getIsAdult() {
        return isAdult;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }
}
