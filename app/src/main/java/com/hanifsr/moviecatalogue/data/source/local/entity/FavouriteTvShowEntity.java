package com.hanifsr.moviecatalogue.data.source.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_tv_show")
public class FavouriteTvShowEntity {

	@PrimaryKey
	@ColumnInfo
	private int id;

	@ColumnInfo
	private String posterPath;

	@ColumnInfo
	private String backdropPath;

	@ColumnInfo
	private String title;

	@ColumnInfo
	private String genre;

	@ColumnInfo
	private String firstAirDate;

	@ColumnInfo
	private String userScore;

	@ColumnInfo
	private String overview;

	public FavouriteTvShowEntity(int id, String posterPath, String backdropPath, String title, String genre, String firstAirDate, String userScore, String overview) {
		this.id = id;
		this.posterPath = posterPath;
		this.backdropPath = backdropPath;
		this.title = title;
		this.genre = genre;
		this.firstAirDate = firstAirDate;
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getFirstAirDate() {
		return firstAirDate;
	}

	public void setFirstAirDate(String firstAirDate) {
		this.firstAirDate = firstAirDate;
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
}
