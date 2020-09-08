package com.example.myplaylist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myplaylist.model.MyCourseModel

/**
 * Created by Kailash Suthar on 06/9/2020.
 */

@Dao
interface DaoAccess {

    /**
     * Insert all courses
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCourseTask(courses: List<MyCourseModel>)

    /**
     * Get all videos from one playlist
     */
    @Query("SELECT * FROM " + MyCourseModel.TABLE_PLAYLIST + " WHERE " + MyCourseModel.PLAYLIST_ID + " =:playlistId")
    fun fetchAllVideoTasks(playlistId: String): LiveData<List<MyCourseModel>>

    /**
     * Get recent played videos list
     */
    @Query("SELECT * FROM " + MyCourseModel.TABLE_PLAYLIST + " WHERE " + MyCourseModel.RECENT_VIDEO + " =1 ORDER BY " + MyCourseModel.ID + " DESC")
    fun fetchRecentVideosTasks(): LiveData<List<MyCourseModel>>

    /**
     * Get videos to play next
     */
    @Query("SELECT * FROM " + MyCourseModel.TABLE_PLAYLIST + " WHERE " + MyCourseModel.ID + " > :id AND " + MyCourseModel.PLAYLIST_ID + " =:playlistId")
    fun fetchNextVideosTasks(id: Int, playlistId: String): LiveData<List<MyCourseModel>>

    /**
     * Add video in recent list
     */
    @Query("UPDATE " + MyCourseModel.TABLE_PLAYLIST + " SET " + MyCourseModel.RECENT_VIDEO + " = 1 WHERE " + MyCourseModel.VIDEO_ID + " = :video_id")
    fun addRecentVideoTask(video_id: String)

}