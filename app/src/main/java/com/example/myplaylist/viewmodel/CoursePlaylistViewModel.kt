package com.example.myplaylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myplaylist.model.CoursePlaylistModel
import com.example.myplaylist.repo.CoursePlaylistRepo
/**
 * Created by Kailash Suthar on 06/9/2020.
 */

class CoursePlaylistViewModel() : ViewModel() {

    var coursePlaylistRepo: CoursePlaylistRepo? = null
    var mutableLiveData: MutableLiveData<CoursePlaylistModel>? = null

    init {
        coursePlaylistRepo = CoursePlaylistRepo()
    }

    fun getPlaylist(query: String, access_token: String): LiveData<CoursePlaylistModel> {
        if (mutableLiveData == null) {
            mutableLiveData = coursePlaylistRepo!!.fetchCoursePlaylist(query, access_token)
        }
        return mutableLiveData!!
    }


}



