package com.dar24.app.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * @author Simon Mawole
 * @email simonmawole2011@gmail.com
 */
@Parcel
public class News {

    String title;
    String category;
    String summary;
    String description;
    String thumbnail;
    String publishedAt;
    String author;
    String link;
    List<NewsRelated> newsRelatedList;

    public News(){
        //Empty constructor
    }

    public News(String title, String category, String summary, String description,
                String thumbnail, String publishedAt, String author, String link,
                List<NewsRelated> newsRelatedList) {
        this.title = title;
        this.category = category;
        this.summary = summary;
        this.description = description;
        this.thumbnail = thumbnail;
        this.publishedAt = publishedAt;
        this.author = author;
        this.link = link;
        this.newsRelatedList = newsRelatedList;
    }

    public List<NewsRelated> getNewsRelatedList() {
        return newsRelatedList;
    }

    public void setNewsRelatedList(List<NewsRelated> newsRelatedList) {
        this.newsRelatedList = newsRelatedList;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
