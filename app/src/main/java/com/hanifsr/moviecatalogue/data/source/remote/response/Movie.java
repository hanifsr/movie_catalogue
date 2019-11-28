package com.hanifsr.moviecatalogue.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie implements Parcelable {

	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("poster_path")
	@Expose
	private String posterPath;

	@SerializedName(value = "title", alternate = "name")
	@Expose
	private String title;

	@SerializedName(value = "release_date", alternate = "first_air_date")
	@Expose
	private String dateRelease;

	@SerializedName("vote_average")
	@Expose
	private String userScore;

	@SerializedName("overview")
	@Expose
	private String overview;

	@SerializedName("genre_ids")
	@Expose
	private ArrayList<Integer> genreIds;

	@SerializedName("backdrop_path")
	@Expose
	private String backdropPath;

	@SerializedName("genres")
	@Expose
	private ArrayList<Genre> genresDetail;

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

	public ArrayList<Integer> getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(ArrayList<Integer> genreIds) {
		this.genreIds = genreIds;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public ArrayList<Genre> getGenresDetail() {
		return genresDetail;
	}

	public void setGenresDetail(ArrayList<Genre> genresDetail) {
		this.genresDetail = genresDetail;
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
		dest.writeInt(this.id);
		dest.writeString(this.posterPath);
		dest.writeString(this.title);
		dest.writeString(this.genresHelper);
		dest.writeString(this.dateRelease);
		dest.writeString(this.userScore);
		dest.writeString(this.overview);
	}

	private Movie(Parcel in) {
		this.id = in.readInt();
		this.posterPath = in.readString();
		this.title = in.readString();
		this.genresHelper = in.readString();
		this.dateRelease = in.readString();
		this.userScore = in.readString();
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