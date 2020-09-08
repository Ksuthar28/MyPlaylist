package com.example.myplaylist.helpers;

import com.example.myplaylist.model.CourseDetailsModel;
import com.example.myplaylist.model.CourseModel;
import com.example.myplaylist.model.CoursePlaylistModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kailash Suthar on 04/9/2020.
 */
public interface ApiInterface {
    /**
     * Youtube search data api
     */
    @GET("search?part=snippet&type=playlist")
    Call<CourseModel> searchRequest(
            @Query("q") String query,
            @Query("key") String access_token,
            @Query("maxResults") int maxResult
    );

    /**
     * Youtube playlist data api
     */
    @GET("playlistItems?part=snippet&maxResults=3")
    Call<CoursePlaylistModel> playlistRequest(
            @Query("playlistId") String playlistId,
            @Query("key") String access_token
    );

    /**
     * Youtube videos data api
     */
    @GET("videos?part=contentDetails&part=snippet&maxResults=3")
    Call<CourseDetailsModel> videoRequest(
            @Query("id") String video_id,
            @Query("key") String access_token
    );

}
