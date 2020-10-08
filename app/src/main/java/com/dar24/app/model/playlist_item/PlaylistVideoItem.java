package com.dar24.app.model.playlist_item;

import com.google.gson.annotations.SerializedName;

public class PlaylistVideoItem{

	@SerializedName("kind")
	private String kind;

	@SerializedName("etag")
	private String etag;

	@SerializedName("id")
	private String id;

	@SerializedName("contentDetails")
	private ContentDetails contentDetails;

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setEtag(String etag){
		this.etag = etag;
	}

	public String getEtag(){
		return etag;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setContentDetails(ContentDetails contentDetails){
		this.contentDetails = contentDetails;
	}

	public ContentDetails getContentDetails(){
		return contentDetails;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"kind = '" + kind + '\'' + 
			",etag = '" + etag + '\'' + 
			",id = '" + id + '\'' + 
			",contentDetails = '" + contentDetails + '\'' + 
			"}";
		}
}