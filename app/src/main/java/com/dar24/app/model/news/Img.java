package com.dar24.app.model.news;

import com.google.gson.annotations.SerializedName;

public class Img{

	@SerializedName("src")
	private String src;

	@SerializedName("width")
	private int width;

	@SerializedName("height")
	private int height;

	public void setSrc(String src){
		this.src = src;
	}

	public String getSrc(){
		return src;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"Img{" + 
			"src = '" + src + '\'' + 
			",width = '" + width + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}