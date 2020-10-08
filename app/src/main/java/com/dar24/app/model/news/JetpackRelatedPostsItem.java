package com.dar24.app.model.news;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JetpackRelatedPostsItem{

	@SerializedName("date")
	private String date;

	@SerializedName("img")
	private Img img;

	@SerializedName("classes")
	private List<Object> classes;

	@SerializedName("format")
	private boolean format;

	@SerializedName("rel")
	private String rel;

	@SerializedName("context")
	private String context;

	@SerializedName("id")
	private int id;

	@SerializedName("url_meta")
	private UrlMeta urlMeta;

	@SerializedName("title")
	private String title;

	@SerializedName("excerpt")
	private String excerpt;

	@SerializedName("url")
	private String url;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setImg(Img img){
		this.img = img;
	}

	public Img getImg(){
		return img;
	}

	public void setClasses(List<Object> classes){
		this.classes = classes;
	}

	public List<Object> getClasses(){
		return classes;
	}

	public void setFormat(boolean format){
		this.format = format;
	}

	public boolean isFormat(){
		return format;
	}

	public void setRel(String rel){
		this.rel = rel;
	}

	public String getRel(){
		return rel;
	}

	public void setContext(String context){
		this.context = context;
	}

	public String getContext(){
		return context;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUrlMeta(UrlMeta urlMeta){
		this.urlMeta = urlMeta;
	}

	public UrlMeta getUrlMeta(){
		return urlMeta;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setExcerpt(String excerpt){
		this.excerpt = excerpt;
	}

	public String getExcerpt(){
		return excerpt;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"JetpackRelatedPostsItem{" + 
			"date = '" + date + '\'' + 
			",img = '" + img + '\'' + 
			",classes = '" + classes + '\'' + 
			",format = '" + format + '\'' + 
			",rel = '" + rel + '\'' + 
			",context = '" + context + '\'' + 
			",id = '" + id + '\'' + 
			",url_meta = '" + urlMeta + '\'' + 
			",title = '" + title + '\'' + 
			",excerpt = '" + excerpt + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}