package com.dar24.app.model.author;

import com.google.gson.annotations.SerializedName;

public class AvatarUrls{

	@SerializedName("24")
	private String jsonMember24;

	@SerializedName("48")
	private String jsonMember48;

	@SerializedName("96")
	private String jsonMember96;

	public void setJsonMember24(String jsonMember24){
		this.jsonMember24 = jsonMember24;
	}

	public String getJsonMember24(){
		return jsonMember24;
	}

	public void setJsonMember48(String jsonMember48){
		this.jsonMember48 = jsonMember48;
	}

	public String getJsonMember48(){
		return jsonMember48;
	}

	public void setJsonMember96(String jsonMember96){
		this.jsonMember96 = jsonMember96;
	}

	public String getJsonMember96(){
		return jsonMember96;
	}

	@Override
 	public String toString(){
		return 
			"AvatarUrls{" + 
			"24 = '" + jsonMember24 + '\'' + 
			",48 = '" + jsonMember48 + '\'' + 
			",96 = '" + jsonMember96 + '\'' + 
			"}";
		}
}