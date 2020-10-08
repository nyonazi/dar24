package com.dar24.app.model.news;

import com.google.gson.annotations.SerializedName;

public class AuthorItem{

	@SerializedName("href")
	private String href;

	@SerializedName("embeddable")
	private boolean embeddable;

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	public void setEmbeddable(boolean embeddable){
		this.embeddable = embeddable;
	}

	public boolean isEmbeddable(){
		return embeddable;
	}

	@Override
 	public String toString(){
		return 
			"AuthorItem{" + 
			"href = '" + href + '\'' + 
			",embeddable = '" + embeddable + '\'' + 
			"}";
		}
}