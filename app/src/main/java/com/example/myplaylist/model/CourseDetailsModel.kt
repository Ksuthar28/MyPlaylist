package com.example.myplaylist.model
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class CourseDetailsModel {
    var items: ArrayList<CourseDetailsItemModel>? = null
}

class CourseDetailsItemModel {
    var id: String? = null
    var snippet: CourseSnippetModel? = null
    var contentDetails: ContentDetailsModel? = null
}

class ContentDetailsModel {
    var duration: String? = null
}