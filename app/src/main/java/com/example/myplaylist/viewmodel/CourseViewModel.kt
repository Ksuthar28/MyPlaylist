package com.example.myplaylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myplaylist.model.CourseModel
import com.example.myplaylist.repo.CourseRepo
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class CourseViewModel() : ViewModel() {

    var courseRepo: CourseRepo? = null
    var mutableLiveData: MutableLiveData<CourseModel>? = null

    init {
        courseRepo = CourseRepo()
    }

    fun getCourse(query: String, access_token: String, maxResult: Int): LiveData<CourseModel> {
        if (mutableLiveData == null) {
            mutableLiveData = courseRepo!!.fetchCourse(query, access_token, maxResult)
        }
        return mutableLiveData!!
    }


}



