package com.example.myplaylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myplaylist.model.CourseDetailsModel
import com.example.myplaylist.repo.CourseVideoRepo
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class CourseVideoViewModel() : ViewModel() {

    var courseVideoRepo: CourseVideoRepo? = null
    var mutableLiveData: MutableLiveData<CourseDetailsModel>? = null

    init {
        courseVideoRepo = CourseVideoRepo()
    }

    fun getVideos(query: String, access_token: String): LiveData<CourseDetailsModel> {
        if (mutableLiveData == null) {
            mutableLiveData = courseVideoRepo!!.fetchVideos(query, access_token)
        }
        return mutableLiveData!!
    }


}



