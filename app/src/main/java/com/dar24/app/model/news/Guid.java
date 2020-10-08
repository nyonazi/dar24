package com.dar24.app.model.news;

import com.google.gson.annotations.SerializedName;

public class Guid{

	@SerializedName("rendered")
	private String rendered;

	public void setRendered(String rendered){
		this.rendered = rendered;
	}

	public String getRendered(){
		return rendered;
	}

	@Override
 	public String toString(){
		return 
			"Guid{" + 
			"rendered = '" + rendered + '\'' + 
			"}";
		}
}