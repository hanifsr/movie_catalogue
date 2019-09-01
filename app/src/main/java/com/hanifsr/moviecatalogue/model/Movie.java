package com.hanifsr.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

	private int poster;
	private String title, genres, dateRelease, rating, runtime, overview;

	public int getPoster() {
		return poster;
	}

	public void setPoster(int poster) {
		this.poster = poster;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getDateRelease() {
		return dateRelease;
	}

	public void setDateRelease(String dateRelease) {
		this.dateRelease = dateRelease;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.poster);
		dest.writeString(this.title);
		dest.writeString(this.genres);
		dest.writeString(this.dateRelease);
		dest.writeString(this.rating);
		dest.writeString(this.runtime);
		dest.writeString(this.overview);
	}

	public Movie() {
	}

	protected Movie(Parcel in) {
		this.poster = in.readInt();
		this.title = in.readString();
		this.genres = in.readString();
		this.dateRelease = in.readString();
		this.rating = in.readString();
		this.runtime = in.readString();
		this.overview = in.readString();
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