package com.example.myplaylist.helpers

import com.example.myplaylist.model.CourseDetailsItemModel
import com.example.myplaylist.model.CourseItemModel
import com.example.myplaylist.model.CoursePlaylistItemModel
import com.example.myplaylist.model.MyCourseModel

/**
 * Created by Kailash Suthar on 06/9/2020.
 */

class FormatData {
    companion object {
        fun getCourses(list: ArrayList<CourseItemModel>): ArrayList<MyCourseModel> {
            val model: ArrayList<MyCourseModel> = ArrayList()
            for (course: CourseItemModel in list) {
                val item = MyCourseModel()
                item.playlistId = course.id?.playlistId
                item.title = course.snippet?.title
                item.description = course.snippet?.description
                item.image = course.snippet?.thumbnails?.medium?.url
                item.videoId = course.snippet?.resourceId?.videoId
                item.duration = ""
                model.add(item)
            }
            return model
        }

        fun getPlaylist(list: ArrayList<CoursePlaylistItemModel>): ArrayList<MyCourseModel> {
            val model: ArrayList<MyCourseModel> = ArrayList()
            for (course: CoursePlaylistItemModel in list) {
                val item = MyCourseModel()
                //Log.e("PlaylistId ", course.id?.playlistId!!)
                item.playlistId = ""
                item.title = course.snippet?.title
                item.description = course.snippet?.description
                item.image = course.snippet?.thumbnails?.medium?.url
                item.videoId = course.snippet?.resourceId?.videoId
                item.duration = ""
                model.add(item)
            }
            return model
        }

        fun getVideoIds(list: ArrayList<MyCourseModel>): String {
            val ids = StringBuilder()
            for (course: MyCourseModel in list) {
                if (ids.isEmpty()) ids.append(course.videoId)
                else {
                    ids.append(",")
                    ids.append(course.videoId)
                }
            }
            //Log.e("videoId ", ids.toString())
            return ids.toString()
        }

        fun getVideos(
            list: ArrayList<CourseDetailsItemModel>,
            playlistId: String
        ): ArrayList<MyCourseModel> {
            val model: ArrayList<MyCourseModel> = ArrayList()
            for (course: CourseDetailsItemModel in list) {
                val item = MyCourseModel()
                //item.id = 0
                item.playlistId = playlistId
                item.title = course.snippet?.title
                item.description = course.snippet?.description
                item.image = course.snippet?.thumbnails?.medium?.url
                item.videoId = course.id
                item.duration = HelperMethod.getDuration(course.contentDetails?.duration!!)
                model.add(item)
            }
            return model
        }
    }
}
