package com.dar24.app.model.news;

import com.google.gson.annotations.SerializedName;

public class UrlMeta{

	@SerializedName("origin")
	private int origin;

	@SerializedName("position")
	private int position;

	public void setOrigin(int origin){
		this.origin = origin;
	}

	public int getOrigin(){
		return origin;
	}

	public void setPosition(int position){
		this.position = position;
	}

	public int getPosition(){
		return position;
	}

	@Override
 	public String toString(){
		return 
			"UrlMeta{" + 
			"origin = '" + origin + '\'' + 
			",position = '" + position + '\'' + 
			"}";
		}
}