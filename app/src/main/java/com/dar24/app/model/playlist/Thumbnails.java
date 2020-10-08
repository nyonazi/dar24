package com.dar24.app.model.playlist;

import com.google.gson.annotations.SerializedName;

public class Thumbnails{

	@SerializedName("default")
	private JsonMemberDefault jsonMemberDefault;

	@SerializedName("high")
	private High high;

	@SerializedName("medium")
	private Medium medium;

	@SerializedName("standard")
	private Standard standard;

	@SerializedName("maxres")
	private Maxres maxres;

	public void setJsonMemberDefault(JsonMemberDefault jsonMemberDefault){
		this.jsonMemberDefault = jsonMemberDefault;
	}

	public JsonMemberDefault getJsonMemberDefault(){
		return jsonMemberDefault;
	}

	public void setHigh(High high){
		this.high = high;
	}

	public High getHigh(){
		return high;
	}

	public void setMedium(Medium medium){
		this.medium = medium;
	}

	public Medium getMedium(){
		return medium;
	}

	public void setStandard(Standard standard){
		this.standard = standard;
	}

	public Standard getStandard(){
		return standard;
	}

	public void setMaxres(Maxres maxres){
		this.maxres = maxres;
	}

	public Maxres getMaxres(){
		return maxres;
	}

	@Override
 	public String toString(){
		return 
			"Thumbnails{" + 
			"default = '" + jsonMemberDefault + '\'' + 
			",high = '" + high + '\'' + 
			",medium = '" + medium + '\'' + 
			",standard = '" + standard + '\'' + 
			",maxres = '" + maxres + '\'' + 
			"}";
		}
}