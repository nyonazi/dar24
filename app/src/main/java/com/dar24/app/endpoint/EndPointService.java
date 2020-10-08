package com.dar24.app.endpoint;

import com.dar24.app.model.author.AuthorResponse;
import com.dar24.app.model.mailchimp_status.StatusResponse;
import com.dar24.app.model.mailchimp_subscribe.SubscribeRequest;
import com.dar24.app.model.mailchimp_subscribe.SubscribeResponse;
import com.dar24.app.model.news.NewsResponse;
import com.dar24.app.model.playlist.VideoPlaylistResponse;
import com.dar24.app.model.playlist_item.PlaylistVideoResponse;
import com.dar24.app.model.video.VideoResponse;
import com.dar24.app.model.video_detail.VideoDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Simon Mawole
 * @email simonmawole2011@gmail.com
 */

public interface EndPointService {

    @GET(EndPointUrl.API + EndPointUrl.POSTS)
    Call<List<NewsResponse>> getPosts(@Query("categories") Integer categoryId,
                                      @Query("order") String order,
                                      @Query("orderBy") String orderBy,
                                      @Query("per_page") int perPage,
                                      @Query("page") int page);

    @GET(EndPointUrl.API + EndPointUrl.POST)
    Call<NewsResponse> getPost(@Path("postId") int postId);

    @GET(EndPointUrl.API + EndPointUrl.POSTS)
    Call<List<NewsResponse>> getSearchPosts(@Query("search") String search,
                                      @Query("order") String order,
                                      @Query("orderBy") String orderBy,
                                      @Query("per_page") int perPage,
                                      @Query("page") int page);

    @GET(EndPointUrl.API + EndPointUrl.AUTHOR)
    Call<List<AuthorResponse>> getAuthors(@Query("include") String include,
                                          @Query("per_page") int perPage,
                                          @Query("page") int page);

    @GET(EndPointUrl.YOUTUBE_VIDEOS)
    Call<VideoResponse> getYoutubeVideos(@Query("part") String part,
                                         @Query("type") String type,
                                         @Query("key") String apiKey,
                                         @Query("maxResults") int maxResults,
                                         @Query("order") String order,
                                         @Query("channelId") String channelId);

    @GET(EndPointUrl.YOUTUBE_VIDEOS_DETAIL)
    Call<VideoDetailResponse> getYoutubeVideosDetail(@Query("part") String part,
                                                     @Query("key") String apiKey,
                                                     @Query("maxResults") int maxResults,
                                                     @Query("id") String ids);


    @GET(EndPointUrl.YOUTUBE_PLAYLISTS)
    Call<VideoPlaylistResponse> getYoutubePlaylists(@Query("part") String part,
                                                    @Query("key") String apiKey,
                                                    @Query("maxResults") int maxResults,
                                                    @Query("channelId") String channelId);

    @GET(EndPointUrl.YOUTUBE_PLAYLIST_ITEMS)
    Call<PlaylistVideoResponse> getYoutubePlaylistItems(@Query("part") String part,
                                                        @Query("key") String apiKey,
                                                        @Query("maxResults") int maxResults,
                                                        @Query("playlistId") String playlistId);

    @GET(EndPointUrl.MAILCHIMP_CHECK_SUBSCRIPTION)
    Call<StatusResponse> getMailChimpEmailStatus(@Header("authorization") String usernameAndKey,
                                                 @Path("email") String email);

    @POST(EndPointUrl.MAILCHIMP_SUBSCRIBE)
    Call<SubscribeResponse> postMailChimpSubscribe(@Header("authorization") String usernameAndKey,
                                                   @Body SubscribeRequest body);
}

