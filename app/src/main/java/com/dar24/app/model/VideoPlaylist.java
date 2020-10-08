package com.dar24.app.model;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class VideoPlaylist {

    String title;
    String videoCount;
    String thumbnail;
    String playlistPath;

    public VideoPlaylist() {
        //Empty constructor
    }

    public VideoPlaylist(String title, String videoCount, String thumbnail, String playlistPath) {
        this.title = title;
        this.videoCount = videoCount;
        this.thumbnail = thumbnail;
        this.playlistPath = playlistPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPlaylistPath() {
        return playlistPath;
    }

    public void setPlaylistPath(String playlistPath) {
        this.playlistPath = playlistPath;
    }
}
