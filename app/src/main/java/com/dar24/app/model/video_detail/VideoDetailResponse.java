package com.dar24.app.model.video_detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VideoDetailResponse{

	@SerializedName("kind")
	private String kind;

	@SerializedName("pageInfo")
	private PageInfo pageInfo;

	@SerializedName("etag")
	private String etag;

	@SerializedName("items")
	private List<VideoDetailItem> items;

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
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

	public void setItems(List<VideoDetailItem> items){
		this.items = items;
	}

	public List<VideoDetailItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"VideoDetailResponse{" + 
			"kind = '" + kind + '\'' + 
			",pageInfo = '" + pageInfo + '\'' + 
			",etag = '" + etag + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}