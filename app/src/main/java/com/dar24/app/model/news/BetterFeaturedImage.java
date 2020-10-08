package com.dar24.app.model.news;

import com.google.gson.annotations.SerializedName;

public class BetterFeaturedImage{

	@SerializedName("post")
	private int post;

	@SerializedName("alt_text")
	private String altText;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("caption")
	private String caption;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("media_details")
	private MediaDetails mediaDetails;

	@SerializedName("source_url")
	private String sourceUrl;

	public void setPost(int post){
		this.post = post;
	}

	public int getPost(){
		return post;
	}

	public void setAltText(String altText){
		this.altText = altText;
	}

	public String getAltText(){
		return altText;
	}

	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}

	public String getMediaType(){
		return mediaType;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setMediaDetails(MediaDetails mediaDetails){
		this.mediaDetails = mediaDetails;
	}

	public MediaDetails getMediaDetails(){
		return mediaDetails;
	}

	public void setSourceUrl(String sourceUrl){
		this.sourceUrl = sourceUrl;
	}

	public String getSourceUrl(){
		return sourceUrl;
	}

	@Override
 	public String toString(){
		return 
			"BetterFeaturedImage{" + 
			"post = '" + post + '\'' + 
			",alt_text = '" + altText + '\'' + 
			",media_type = '" + mediaType + '\'' + 
			",caption = '" + caption + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",media_details = '" + mediaDetails + '\'' + 
			",source_url = '" + sourceUrl + '\'' + 
			"}";
		}
}