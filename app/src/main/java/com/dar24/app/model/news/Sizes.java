package com.dar24.app.model.news;

import com.google.gson.annotations.SerializedName;

public class Sizes{

	@SerializedName("thumbnail")
	private Thumbnail thumbnail;

	@SerializedName("buzzmag_featured")
	private BuzzmagFeatured buzzmagFeatured;

	@SerializedName("buzzmag_small")
	private BuzzmagSmall buzzmagSmall;

	@SerializedName("medium")
	private Medium medium;

	@SerializedName("medium_large")
	private MediumLarge mediumLarge;

	@SerializedName("buzzmag_medium")
	private BuzzmagMedium buzzmagMedium;

	public void setThumbnail(Thumbnail thumbnail){
		this.thumbnail = thumbnail;
	}

	public Thumbnail getThumbnail(){
		return thumbnail;
	}

	public void setBuzzmagFeatured(BuzzmagFeatured buzzmagFeatured){
		this.buzzmagFeatured = buzzmagFeatured;
	}

	public BuzzmagFeatured getBuzzmagFeatured(){
		return buzzmagFeatured;
	}

	public void setBuzzmagSmall(BuzzmagSmall buzzmagSmall){
		this.buzzmagSmall = buzzmagSmall;
	}

	public BuzzmagSmall getBuzzmagSmall(){
		return buzzmagSmall;
	}

	public void setMedium(Medium medium){
		this.medium = medium;
	}

	public Medium getMedium(){
		return medium;
	}

	public void setMediumLarge(MediumLarge mediumLarge){
		this.mediumLarge = mediumLarge;
	}

	public MediumLarge getMediumLarge(){
		return mediumLarge;
	}

	public void setBuzzmagMedium(BuzzmagMedium buzzmagMedium){
		this.buzzmagMedium = buzzmagMedium;
	}

	public BuzzmagMedium getBuzzmagMedium(){
		return buzzmagMedium;
	}

	@Override
 	public String toString(){
		return 
			"Sizes{" + 
			"thumbnail = '" + thumbnail + '\'' + 
			",buzzmag_featured = '" + buzzmagFeatured + '\'' + 
			",buzzmag_small = '" + buzzmagSmall + '\'' + 
			",medium = '" + medium + '\'' + 
			",medium_large = '" + mediumLarge + '\'' + 
			",buzzmag_medium = '" + buzzmagMedium + '\'' + 
			"}";
		}
}