package com.dar24.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.dar24.app.R;
import com.dar24.app.adapter.VideoLatestAdapter;
import com.dar24.app.endpoint.EndPointService;
import com.dar24.app.endpoint.EndPointUrl;
import com.dar24.app.model.VideoLatest;
import com.dar24.app.model.playlist_item.PlaylistVideoItem;
import com.dar24.app.model.playlist_item.PlaylistVideoResponse;
import com.dar24.app.model.video_detail.VideoDetailItem;
import com.dar24.app.model.video_detail.VideoDetailResponse;
import com.dar24.app.utility.Developer;
import com.dar24.app.utility.Helpers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Simon Mawole
 * @version 1.0.1
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class PlaylistVideoActivity extends BaseAppCompatActivity {

    private final OkHttpClient mClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create();
    final Retrofit mRetrofit = new Retrofit.Builder()
            .client(mClient)
            .baseUrl(EndPointUrl.YOUTUBE_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            //.addConverterFactory(ScalarsConverterFactory.create())
            .build();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private Call<PlaylistVideoResponse> callVideos;
    private Call<VideoDetailResponse> callVideosDetails;
    private VideoLatestAdapter videoLatestAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String playlistId;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_playlist_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        playlistId = getIntent().getStringExtra("playlist");
        title = getIntent().getStringExtra("title");
        if (title == null || title == "") {
            title = "Dar24 videos";
        }
        setTitle(title);
        if (playlistId == null || playlistId == "") {
            Toast.makeText(this, R.string.unknown_playlist, Toast.LENGTH_SHORT).show();
            finish();
        }

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        videoLatestAdapter = new VideoLatestAdapter(this);
        recyclerView.setAdapter(videoLatestAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchVideos();
            }
        });

        fetchVideos();
    }

    private void fetchVideos() {
        if (Helpers.isConnected(PlaylistVideoActivity.this)) {
            swipeRefreshLayout.setRefreshing(true);
            EndPointService service = mRetrofit.create(EndPointService.class);
            callVideos = service.getYoutubePlaylistItems("contentDetails",
                    Developer.YOUTUBE_API_KEY, 50, playlistId);
            callVideos.enqueue(new Callback<PlaylistVideoResponse>() {
                @Override
                public void onResponse(Call<PlaylistVideoResponse> call, Response<PlaylistVideoResponse> response) {
                    if (!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            PlaylistVideoResponse videoResponse = response.body();
                            if (videoResponse != null) {
                                List<String> ids = new ArrayList<>();
                                List<PlaylistVideoItem> items = videoResponse.getItems();
                                for (int i = 0; i < items.size(); i++) {
                                    ids.add(items.get(i).getContentDetails().getVideoId());
                                }
                                //Remove [ and ] from a string
                                fetchVideosDetail(ids.toString().replace("[", "")
                                        .replace("]", "").trim());
                            }
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(PlaylistVideoActivity.this, R.string.something_went_wrong,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PlaylistVideoResponse> call, Throwable t) {
                    if (!call.isCanceled()) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(PlaylistVideoActivity.this, R.string.something_went_wrong,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(PlaylistVideoActivity.this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchVideosDetail(String ids) {
        if (Helpers.isConnected(PlaylistVideoActivity.this)) {
            swipeRefreshLayout.setRefreshing(true);
            EndPointService service = mRetrofit.create(EndPointService.class);
            callVideosDetails = service.getYoutubeVideosDetail(
                    "snippet,id,statistics", Developer.YOUTUBE_API_KEY, 25, ids);
            callVideosDetails.enqueue(new Callback<VideoDetailResponse>() {
                @Override
                public void onResponse(Call<VideoDetailResponse> call, Response<VideoDetailResponse> response) {
                    if (!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            VideoDetailResponse videoDetailResponse = response.body();
                            if (videoDetailResponse != null) {
                                List<VideoDetailItem> list =
                                        videoDetailResponse.getItems();
                                List<VideoLatest> latests = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    latests.add(new VideoLatest(
                                            list.get(i).getSnippet().getTitle(),
                                            list.get(i).getStatistics().getViewCount(),
                                            list.get(i).getSnippet().getThumbnails().getMedium().getUrl(),
                                            list.get(i).getSnippet().getPublishedAt(),
                                            list.get(i).getId()
                                    ));
                                }
                                videoLatestAdapter.addItems(latests);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(PlaylistVideoActivity.this, R.string.something_went_wrong,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<VideoDetailResponse> call, Throwable t) {
                    if (call.isCanceled()) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(PlaylistVideoActivity.this, R.string.something_went_wrong,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callVideos != null) callVideos.cancel();
        if (callVideosDetails != null) callVideosDetails.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (callVideos != null) callVideos.cancel();
        if (callVideosDetails != null) callVideosDetails.cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
