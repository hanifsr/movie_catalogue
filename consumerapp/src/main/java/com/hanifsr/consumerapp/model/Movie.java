package com.hanifsr.consumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

	private int id;
	private String posterPath;
	private String title;
	private String dateRelease;
	private String userScore;
	private String overview;
	private String backdropPath;
	private String genresHelper;

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

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getGenresHelper() {
		return genresHelper;
	}

	public void setGenresHelper(String genresHelper) {
		this.genresHelper = genresHelper;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(posterPath);
		dest.writeString(title);
		dest.writeString(dateRelease);
		dest.writeString(userScore);
		dest.writeString(overview);
		dest.writeString(backdropPath);
		dest.writeString(genresHelper);
	}

	protected Movie(Parcel in) {
		id = in.readInt();
		posterPath = in.readString();
		title = in.readString();
		dateRelease = in.readString();
		userScore = in.readString();
		overview = in.readString();
		backdropPath = in.readString();
		genresHelper = in.readString();
	}

	public static final Creator<Movie> CREATOR = new Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel in) {
			return new Movie(in);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
}
