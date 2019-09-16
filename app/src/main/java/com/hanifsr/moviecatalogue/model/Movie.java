package com.hanifsr.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

	private int id;
	private String posterPath, title, genres, dateRelease, userScore, runtime, overview;

	public Movie() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
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

	public String getUserScore() {
		return userScore;
	}

	public void setUserScore(String userScore) {
		this.userScore = userScore;
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
		dest.writeInt(this.id);
		dest.writeString(this.posterPath);
		dest.writeString(this.title);
		dest.writeString(this.genres);
		dest.writeString(this.dateRelease);
		dest.writeString(this.userScore);
		dest.writeString(this.runtime);
		dest.writeString(this.overview);
	}

	protected Movie(Parcel in) {
		this.id = in.readInt();
		this.posterPath = in.readString();
		this.title = in.readString();
		this.genres = in.readString();
		this.dateRelease = in.readString();
		this.userScore = in.readString();
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