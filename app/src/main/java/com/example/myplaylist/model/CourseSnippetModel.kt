package com.example.myplaylist.model
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class CourseSnippetModel {
    var channelId: String? = null
    var title: String? = null
    var description: String? = null
    var thumbnails: ThumbnailModel? = null
    var resourceId: VideoId? = null
}

class ThumbnailModel {
    var medium: ThumbnailMedium? = null
}

class ThumbnailMedium {
    var url: String? = null
}

class VideoId {
    var videoId: String? = null
}

/*
@BindingAdapter("setImageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl).apply(RequestOptions().fitCenter())
        //.placeholder(ContextCompat.getDrawable(view.context, R.drawable.placeholder))
        .error(ContextCompat.getDrawable(view.context, R.drawable.placeholder))
        .into(view)
}
*/


