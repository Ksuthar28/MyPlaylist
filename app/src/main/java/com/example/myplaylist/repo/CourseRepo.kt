package com.example.myplaylist.repo

import androidx.lifecycle.MutableLiveData
import com.example.myplaylist.helpers.RetrofitBuilder
import com.example.myplaylist.model.CourseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class CourseRepo {

    val TAG = javaClass.simpleName

    /**
     * Repo for search courses
     */
    fun fetchCourse(
        query: String,
        access_token: String,
        maxResult: Int
    ): MutableLiveData<CourseModel> {
        val mutableList: MutableLiveData<CourseModel> = MutableLiveData()

        RetrofitBuilder.apiService.searchRequest(query, access_token, maxResult)
            ?.enqueue(object : Callback<CourseModel> {

                override fun onResponse(
                    call: Call<CourseModel>,
                    response: Response<CourseModel>
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

                override fun onFailure(call: Call<CourseModel>, t: Throwable) {
                    //Log.e(TAG, "onFailure call=$call")
                }
            })
        return mutableList
    }
}



