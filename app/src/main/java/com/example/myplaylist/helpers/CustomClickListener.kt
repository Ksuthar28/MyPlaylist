package com.example.myplaylist.helpers

import com.example.myplaylist.model.MyCourseModel
/**
 * Created by Kailash Suthar on 08/9/2020.
 */
interface CustomClickListener {
    fun itemSelected(pos: Int, item: MyCourseModel, playVideo: Boolean)
}