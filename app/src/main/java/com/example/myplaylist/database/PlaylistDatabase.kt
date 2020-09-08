package com.example.myplaylist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myplaylist.model.MyCourseModel

/**
 * Created by Kailash Suthar on 06/9/2020.
 */

@Database(entities = [MyCourseModel::class], version = 1, exportSchema = false)
abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun daoAccess(): DaoAccess?
}