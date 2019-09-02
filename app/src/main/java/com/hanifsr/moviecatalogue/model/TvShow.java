package com.hanifsr.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {

	private int tvshowPoster;
	private String tvshowTitle, tvshowGenres, tvshowRating, tvshowRuntime, tvshowOverview;

	public int getTvshowPoster() {
		return tvshowPoster;
	}

	public void setTvshowPoster(int tvshowPoster) {
		this.tvshowPoster = tvshowPoster;
	}

	public String getTvshowTitle() {
		return tvshowTitle;
	}

	public void setTvshowTitle(String tvshowTitle) {
		this.tvshowTitle = tvshowTitle;
	}

	public String getTvshowGenres() {
		return tvshowGenres;
	}

	public void setTvshowGenres(String tvshowGenres) {
		this.tvshowGenres = tvshowGenres;
	}

	public String getTvshowRating() {
		return tvshowRating;
	}

	public void setTvshowRating(String tvshowRating) {
		this.tvshowRating = tvshowRating;
	}

	public String getTvshowRuntime() {
		return tvshowRuntime;
	}

	public void setTvshowRuntime(String tvshowRuntime) {
		this.tvshowRuntime = tvshowRuntime;
	}

	public String getTvshowOverview() {
		return tvshowOverview;
	}

	public void setTvshowOverview(String tvshowOverview) {
		this.tvshowOverview = tvshowOverview;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(tvshowPoster);
		dest.writeString(tvshowTitle);
		dest.writeString(tvshowGenres);
		dest.writeString(tvshowRating);
		dest.writeString(tvshowRuntime);
		dest.writeString(tvshowOverview);
	}

	public TvShow() {
	}

	protected TvShow(Parcel in) {
		tvshowPoster = in.readInt();
		tvshowTitle = in.readString();
		tvshowGenres = in.readString();
		tvshowRating = in.readString();
		tvshowRuntime = in.readString();
		tvshowOverview = in.readString();
	}

	public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
		@Override
		public TvShow createFromParcel(Parcel in) {
			return new TvShow(in);
		}

		@Override
		public TvShow[] newArray(int size) {
			return new TvShow[size];
		}
	};
}
