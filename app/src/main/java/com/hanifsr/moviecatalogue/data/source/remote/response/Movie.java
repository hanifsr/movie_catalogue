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

	@SerializedName("backdrop_path")
	@Expose
	private String backdropPath;

	@SerializedName(value = "title", alternate = "name")
	@Expose
	private String title;

	@SerializedName("genre_ids")
	@Expose
	private ArrayList<Integer> genreIds;

	@SerializedName("genres")
	@Expose
	private ArrayList<Genre> genresDetail;

	private String genresHelper;

	@SerializedName(value = "release_date", alternate = "first_air_date")
	@Expose
	private String releaseDate;

	@SerializedName("vote_average")
	@Expose
	private String userScore;

	@SerializedName("overview")
	@Expose
	private String overview;

	public Movie() {
	}

	public Movie(int id, String posterPath, String backdropPath, String title, String genresHelper, String releaseDate, String userScore, String overview) {
		this.id = id;
		this.posterPath = posterPath;
		this.backdropPath = backdropPath;
		this.title = title;
		this.genresHelper = genresHelper;
		this.releaseDate = releaseDate;
		this.userScore = userScore;
		this.overview = overview;
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

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Integer> getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(ArrayList<Integer> genreIds) {
		this.genreIds = genreIds;
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

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(posterPath);
		dest.writeString(backdropPath);
		dest.writeString(title);
		dest.writeString(genresHelper);
		dest.writeString(releaseDate);
		dest.writeString(userScore);
		dest.writeString(overview);
	}

	protected Movie(Parcel in) {
		id = in.readInt();
		posterPath = in.readString();
		backdropPath = in.readString();
		title = in.readString();
		genresHelper = in.readString();
		releaseDate = in.readString();
		userScore = in.readString();
		overview = in.readString();
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