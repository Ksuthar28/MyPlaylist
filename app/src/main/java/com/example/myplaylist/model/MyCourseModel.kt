package com.example.myplaylist.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myplaylist.helpers.HelperMethod.Companion.loadImage
import java.io.Serializable
/**
 * Created by Kailash Suthar on 06/9/2020.
 */

@Entity(
    tableName = MyCourseModel.TABLE_PLAYLIST,
    indices = [Index(value = [MyCourseModel.VIDEO_ID], unique = true)]
)
class MyCourseModel : Serializable {

    companion object {
        const val TABLE_PLAYLIST = "my_playlist"
        const val ID = "id"
        const val PLAYLIST_ID = "playlist_id"
        const val VIDEO_ID = "video_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
        const val DURATION = "duration"
        const val RECENT_VIDEO = "recent_video"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Int = 0

    @ColumnInfo(name = PLAYLIST_ID)
    var playlistId: String? = ""

    @ColumnInfo(name = VIDEO_ID)
    var videoId: String? = ""

    @ColumnInfo(name = TITLE)
    var title: String? = ""

    @ColumnInfo(name = DESCRIPTION)
    var description: String? = ""

    @ColumnInfo(name = IMAGE)
    var image: String? = ""

    @ColumnInfo(name = DURATION)
    var duration: String? = ""

    @ColumnInfo(name = RECENT_VIDEO)
    var recentVideo: String = "0"


}

@BindingAdapter("setImageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    view.loadImage(imageUrl)
}
        