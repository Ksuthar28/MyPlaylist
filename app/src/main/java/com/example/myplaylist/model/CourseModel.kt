package com.example.myplaylist.model
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

 class CourseModel {
    var items: ArrayList<CourseItemModel>? = null
}

class CourseItemModel {
    var id: PlaylistId? = null
    var snippet: CourseSnippetModel? = null
}

class PlaylistId {
    var playlistId: String? = null
}