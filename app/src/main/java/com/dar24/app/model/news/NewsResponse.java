package com.dar24.app.model.news;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NewsResponse {

	@SerializedName("date")
	private String date;

	@SerializedName("template")
	private String template;

	@SerializedName("_links")
	private Links links;

	@SerializedName("link")
	private String link;

	@SerializedName("jetpack-related-posts")
	private List<JetpackRelatedPostsItem> jetpackRelatedPosts;

	@SerializedName("type")
	private String type;

	@SerializedName("title")
	private Title title;

	@SerializedName("content")
	private Content content;

	@SerializedName("featured_media")
	private int featuredMedia;

	@SerializedName("modified")
	private String modified;

	@SerializedName("id")
	private int id;

	@SerializedName("categories")
	private List<Integer> categories;

	@SerializedName("date_gmt")
	private String dateGmt;

	@SerializedName("slug")
	private String slug;

	@SerializedName("modified_gmt")
	private String modifiedGmt;

	@SerializedName("author")
	private int author;

	@SerializedName("format")
	private String format;

	@SerializedName("comment_status")
	private String commentStatus;

	@SerializedName("tags")
	private List<Object> tags;

	@SerializedName("ping_status")
	private String pingStatus;

//	@SerializedName("meta")
//	private List<Object> meta;

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("sticky")
	private boolean sticky;

	@SerializedName("guid")
	private Guid guid;

	@SerializedName("better_featured_image")
	private BetterFeaturedImage betterFeaturedImage;

	@SerializedName("excerpt")
	private Excerpt excerpt;

	@SerializedName("status")
	private String status;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setTemplate(String template){
		this.template = template;
	}

	public String getTemplate(){
		return template;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setJetpackRelatedPosts(List<JetpackRelatedPostsItem> jetpackRelatedPosts){
		this.jetpackRelatedPosts = jetpackRelatedPosts;
	}

	public List<JetpackRelatedPostsItem> getJetpackRelatedPosts(){
		return jetpackRelatedPosts;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTitle(Title title){
		this.title = title;
	}

	public Title getTitle(){
		return title;
	}

	public void setContent(Content content){
		this.content = content;
	}

	public Content getContent(){
		return content;
	}

	public void setFeaturedMedia(int featuredMedia){
		this.featuredMedia = featuredMedia;
	}

	public int getFeaturedMedia(){
		return featuredMedia;
	}

	public void setModified(String modified){
		this.modified = modified;
	}

	public String getModified(){
		return modified;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCategories(List<Integer> categories){
		this.categories = categories;
	}

	public List<Integer> getCategories(){
		return categories;
	}

	public void setDateGmt(String dateGmt){
		this.dateGmt = dateGmt;
	}

	public String getDateGmt(){
		return dateGmt;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setModifiedGmt(String modifiedGmt){
		this.modifiedGmt = modifiedGmt;
	}

	public String getModifiedGmt(){
		return modifiedGmt;
	}

	public void setAuthor(int author){
		this.author = author;
	}

	public int getAuthor(){
		return author;
	}

	public void setFormat(String format){
		this.format = format;
	}

	public String getFormat(){
		return format;
	}

	public void setCommentStatus(String commentStatus){
		this.commentStatus = commentStatus;
	}

	public String getCommentStatus(){
		return commentStatus;
	}

	public void setTags(List<Object> tags){
		this.tags = tags;
	}

	public List<Object> getTags(){
		return tags;
	}

	public void setPingStatus(String pingStatus){
		this.pingStatus = pingStatus;
	}

	public String getPingStatus(){
		return pingStatus;
	}

//	public void setMeta(List<Object> meta){
//		this.meta = meta;
//	}
//
//	public List<Object> getMeta(){
//		return meta;
//	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}


	public void setSticky(boolean sticky){
		this.sticky = sticky;
	}

	public boolean isSticky(){
		return sticky;
	}

	public void setGuid(Guid guid){
		this.guid = guid;
	}

	public Guid getGuid(){
		return guid;
	}

	public void setBetterFeaturedImage(BetterFeaturedImage betterFeaturedImage){
		this.betterFeaturedImage = betterFeaturedImage;
	}

	public BetterFeaturedImage getBetterFeaturedImage(){
		return betterFeaturedImage;
	}

	public void setExcerpt(Excerpt excerpt){
		this.excerpt = excerpt;
	}

	public Excerpt getExcerpt(){
		return excerpt;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"NewsResponse{" +
			"date = '" + date + '\'' + 
			",template = '" + template + '\'' + 
			",_links = '" + links + '\'' + 
			",link = '" + link + '\'' + 
			",jetpack-related-posts = '" + jetpackRelatedPosts + '\'' + 
			",type = '" + type + '\'' + 
			",title = '" + title + '\'' + 
			",content = '" + content + '\'' + 
			",featured_media = '" + featuredMedia + '\'' + 
			",modified = '" + modified + '\'' + 
			",id = '" + id + '\'' + 
			",categories = '" + categories + '\'' + 
			",date_gmt = '" + dateGmt + '\'' + 
			",slug = '" + slug + '\'' + 
			",modified_gmt = '" + modifiedGmt + '\'' + 
			",author = '" + author + '\'' + 
			",format = '" + format + '\'' + 
			",comment_status = '" + commentStatus + '\'' + 
			",tags = '" + tags + '\'' + 
			",ping_status = '" + pingStatus + '\'' + 
			",meta = '" + meta + '\'' +
			",sticky = '" + sticky + '\'' + 
			",guid = '" + guid + '\'' + 
			",better_featured_image = '" + betterFeaturedImage + '\'' + 
			",excerpt = '" + excerpt + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}