package com.dar24.app.model;

import org.parceler.Parcel;

/**
 * @author Simon Mawole
 * @email simonmawole2011@gmail.com
 */

@Parcel
public class NewsRelated {

    int id;
    String title;
    String summary;
    String thumbnail;
    String publishedAt;
    String link;

    public NewsRelated(){
        //Empty constructor
    }

    public NewsRelated(int id, String title, String summary, String thumbnail,
                       String publishedAt, String link) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.publishedAt = publishedAt;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
