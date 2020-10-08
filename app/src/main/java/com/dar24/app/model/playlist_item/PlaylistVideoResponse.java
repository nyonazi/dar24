package com.dar24.app.model.playlist_item;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PlaylistVideoResponse{

	@SerializedName("kind")
	private String kind;

	@SerializedName("nextPageToken")
	private String nextPageToken;

	@SerializedName("pageInfo")
	private PageInfo pageInfo;

	@SerializedName("etag")
	private String etag;

	@SerializedName("items")
	private List<PlaylistVideoItem> items;

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setNextPageToken(String nextPageToken){
		this.nextPageToken = nextPageToken;
	}

	public String getNextPageToken(){
		return nextPageToken;
	}

	public void setPageInfo(PageInfo pageInfo){
		this.pageInfo = pageInfo;
	}

	public PageInfo getPageInfo(){
		return pageInfo;
	}

	public void setEtag(String etag){
		this.etag = etag;
	}

	public String getEtag(){
		return etag;
	}

	public void setItems(List<PlaylistVideoItem> items){
		this.items = items;
	}

	public List<PlaylistVideoItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"PlaylistVideoResponse{" + 
			"kind = '" + kind + '\'' + 
			",nextPageToken = '" + nextPageToken + '\'' + 
			",pageInfo = '" + pageInfo + '\'' + 
			",etag = '" + etag + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}