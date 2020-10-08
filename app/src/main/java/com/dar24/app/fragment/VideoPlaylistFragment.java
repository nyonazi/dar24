package com.dar24.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dar24.app.R;
import com.dar24.app.adapter.VideoPlaylistAdapter;
import com.dar24.app.endpoint.EndPointService;
import com.dar24.app.endpoint.EndPointUrl;
import com.dar24.app.model.VideoPlaylist;
import com.dar24.app.model.playlist.PlaylistItem;
import com.dar24.app.model.playlist.VideoPlaylistResponse;
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
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class VideoPlaylistFragment extends Fragment {

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

    private Call<VideoPlaylistResponse> callVideoPlaylist;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private VideoPlaylistAdapter videoPlaylistAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_playlist, container, false);
        ButterKnife.bind(this, rootView);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        videoPlaylistAdapter = new VideoPlaylistAdapter(getActivity());
        recyclerView.setAdapter(videoPlaylistAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPlaylists();
            }
        });

        fetchPlaylists();

        return rootView;
    }

    private void fetchPlaylists(){
        if(Helpers.isConnected(getActivity())){
            swipeRefreshLayout.setRefreshing(true);
            EndPointService service = mRetrofit.create(EndPointService.class);
            callVideoPlaylist = service.getYoutubePlaylists("snippet,contentDetails",
                    Developer.YOUTUBE_API_KEY, 50, Developer.YOUTUBE_CHANNEL_ID);
            callVideoPlaylist.enqueue(new Callback<VideoPlaylistResponse>() {
                @Override
                public void onResponse(Call<VideoPlaylistResponse> call, Response<VideoPlaylistResponse> response) {
                    if(!call.isCanceled()) {
                        if (response.isSuccessful()) {
                            VideoPlaylistResponse playlistResponse = response.body();
                            if (playlistResponse != null) {
                                List<PlaylistItem> items = playlistResponse.getItems();
                                List<VideoPlaylist> videoPlaylists = new ArrayList<>();
                                for (int i = 0; i < items.size(); i++) {
                                    PlaylistItem playlistItem = items.get(i);
                                    videoPlaylists.add(new VideoPlaylist(
                                            playlistItem.getSnippet().getTitle(),
                                            String.valueOf(playlistItem.getContentDetails().getItemCount()),
                                            playlistItem.getSnippet().getThumbnails().getMedium().getUrl(),
                                            playlistItem.getId()
                                    ));
                                }
                                videoPlaylistAdapter.addItems(videoPlaylists);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(getActivity(), R.string.no_internet_connection,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<VideoPlaylistResponse> call, Throwable t) {
                    if(!call.isCanceled()) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), R.string.no_internet_connection,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
