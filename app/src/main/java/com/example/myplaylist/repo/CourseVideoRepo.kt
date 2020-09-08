package com.example.myplaylist.repo

import androidx.lifecycle.MutableLiveData
import com.example.myplaylist.helpers.RetrofitBuilder
import com.example.myplaylist.model.CourseDetailsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class CourseVideoRepo {

    val TAG = javaClass.simpleName

    /**
     * Repo for fetch videos
     */
    fun fetchVideos(id: String, access_token: String): MutableLiveData<CourseDetailsModel> {
        val mutableList: MutableLiveData<CourseDetailsModel> = MutableLiveData()

        RetrofitBuilder.apiService.videoRequest(id, access_token)
            ?.enqueue(object : Callback<CourseDetailsModel> {

                override fun onResponse(
                    call: Call<CourseDetailsModel>,
                    response: Response<CourseDetailsModel>
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

                override fun onFailure(call: Call<CourseDetailsModel>, t: Throwable) {
                    //Log.e(TAG, "onFailure call=$call")
                }
            })
        return mutableList
    }
}



