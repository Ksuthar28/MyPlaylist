package com.example.myplaylist.repo

import androidx.lifecycle.MutableLiveData
import com.example.myplaylist.helpers.RetrofitBuilder
import com.example.myplaylist.model.CoursePlaylistModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Kailash Suthar on 06/9/2020.
 */

class CoursePlaylistRepo {

    val TAG = javaClass.simpleName

    /**
     * Repo for fetch playlist videos
     */
    fun fetchCoursePlaylist(
        id: String,
        access_token: String
    ): MutableLiveData<CoursePlaylistModel> {
        val mutableList: MutableLiveData<CoursePlaylistModel> = MutableLiveData()

        RetrofitBuilder.apiService.playlistRequest(id, access_token)
            ?.enqueue(object : Callback<CoursePlaylistModel> {

                override fun onResponse(
                    call: Call<CoursePlaylistModel>,
                    response: Response<CoursePlaylistModel>
                ) {
                    //Log.e(TAG, "onResponse response=$response")
                    if (response.isSuccessful) {
                        if (response.body() != null && response.body()?.items!!.size > 0) {
                            mutableList.value = response.body()!!
                        }
                    } else {
                        mutableList.value = null
                    }
                }

                override fun onFailure(call: Call<CoursePlaylistModel>, t: Throwable) {
                    //Log.e(TAG, "onFailure call=$call")
                }
            })
        return mutableList
    }
}



