package com.dar24.app.endpoint;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class EndPointUrl {

    public static final String BASE = "http://dar24.com/";
    public static final String API = "wp-json/wp/v2/";
    public static final String POSTS = "posts?status=publish";
    public static final String POST = "posts/{postId}";
    public static final String MEDIA = "media?";
    public static final String AUTHOR = "users?";
    public static final String YOUTUBE_API = "https://www.googleapis.com/youtube/v3/";
    public static final String YOUTUBE_PLAYLISTS = "playlists";
    public static final String YOUTUBE_PLAYLIST_ITEMS = "playlistItems";
    public static final String YOUTUBE_VIDEOS = "search";
    public static final String YOUTUBE_VIDEOS_DETAIL = "videos";
    public static final String MAILCHIMP_API = "https://us18.api.mailchimp.com/";
    public static final String MAILCHIMP_SUBSCRIBE = "3.0/lists/2969c0967d/members/";
    public static final String MAILCHIMP_CHECK_SUBSCRIPTION = "3.0/lists/2969c0967d/members/{email}";

}