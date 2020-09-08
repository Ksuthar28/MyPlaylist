package com.example.myplaylist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.myplaylist.model.MyCourseModel

/**
 * Created by Kailash Suthar on 06/9/2020.
 */

class PlaylistRepository(context: Context) : ViewModel() {

    private val DB_NAME = "db_playlist"
    private var playlistDatabase: PlaylistDatabase? = null
    /**
     * Initialize database instance
     */
    init {
        playlistDatabase =
            Room.databaseBuilder(context, PlaylistDatabase::class.java, DB_NAME).build()
    }

    //insert courses
    fun insertVideosTask(courses: List<MyCourseModel>) {
        val thread = Thread {
            playlistDatabase?.daoAccess()?.insertAllCourseTask(courses)
        }
        thread.start()
    }

    //get recent played courses
    fun getRecentVideos(): LiveData<List<MyCourseModel>>? {
        return playlistDatabase?.daoAccess()?.fetchRecentVideosTasks()
    }

    //get next play list
    fun getNextVideos(id: Int, playlistId: String): LiveData<List<MyCourseModel>>? {
        return playlistDatabase?.daoAccess()?.fetchNextVideosTasks(id, playlistId)
    }

    //get all videos for playlist
    fun getAllVideos(playlistId: String): LiveData<List<MyCourseModel>>? {
        return playlistDatabase?.daoAccess()?.fetchAllVideoTasks(playlistId)
    }

    //add recent video
    fun updateRecentVideo(video_id: String) {
        val thread = Thread {
            playlistDatabase?.daoAccess()?.addRecentVideoTask(video_id)
        }
        thread.start()
    }
}
  