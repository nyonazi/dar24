package com.dar24.app.model.playlist_item;

import com.google.gson.annotations.SerializedName;

public class ContentDetails{

	@SerializedName("videoPublishedAt")
	private String videoPublishedAt;

	@SerializedName("videoId")
	private String videoId;

	public void setVideoPublishedAt(String videoPublishedAt){
		this.videoPublishedAt = videoPublishedAt;
	}

	public String getVideoPublishedAt(){
		return videoPublishedAt;
	}

	public void setVideoId(String videoId){
		this.videoId = videoId;
	}

	public String getVideoId(){
		return videoId;
	}

	@Override
 	public String toString(){
		return 
			"ContentDetails{" + 
			"videoPublishedAt = '" + videoPublishedAt + '\'' + 
			",videoId = '" + videoId + '\'' + 
			"}";
		}
}