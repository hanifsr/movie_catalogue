package com.hanifsr.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

	private int moviePoster;
	private String movieTitle, movieGenres, movieDateRelease, movieRating, movieRuntime, movieOverview;

	public int getMoviePoster() {
		return moviePoster;
	}

	public void setMoviePoster(int moviePoster) {
		this.moviePoster = moviePoster;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getMovieGenres() {
		return movieGenres;
	}

	public void setMovieGenres(String movieGenres) {
		this.movieGenres = movieGenres;
	}

	public String getMovieDateRelease() {
		return movieDateRelease;
	}

	public void setMovieDateRelease(String movieDateRelease) {
		this.movieDateRelease = movieDateRelease;
	}

	public String getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}

	public String getMovieRuntime() {
		return movieRuntime;
	}

	public void setMovieRuntime(String movieRuntime) {
		this.movieRuntime = movieRuntime;
	}

	public String getMovieOverview() {
		return movieOverview;
	}

	public void setMovieOverview(String movieOverview) {
		this.movieOverview = movieOverview;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.moviePoster);
		dest.writeString(this.movieTitle);
		dest.writeString(this.movieGenres);
		dest.writeString(this.movieDateRelease);
		dest.writeString(this.movieRating);
		dest.writeString(this.movieRuntime);
		dest.writeString(this.movieOverview);
	}

	public Movie() {
	}

	protected Movie(Parcel in) {
		this.moviePoster = in.readInt();
		this.movieTitle = in.readString();
		this.movieGenres = in.readString();
		this.movieDateRelease = in.readString();
		this.movieRating = in.readString();
		this.movieRuntime = in.readString();
		this.movieOverview = in.readString();
	}

	public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
}