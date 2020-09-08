package com.example.myplaylist.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplaylist.R
import com.example.myplaylist.adapter.CourseVideoAdapter
import com.example.myplaylist.database.PlaylistRepository
import com.example.myplaylist.helpers.CustomClickListener
import com.example.myplaylist.helpers.HelperMethod.Companion.expandText
import com.example.myplaylist.model.MyCourseModel
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video.*
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

open class VideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener,
    CustomClickListener {

    val TAG = javaClass.simpleName
    private lateinit var videoAdapter: CourseVideoAdapter
    private lateinit var videoList: ArrayList<MyCourseModel>
    private lateinit var playlist: MyCourseModel
    private var player: YouTubePlayer? = null
    private val RECOVERY_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        initView()
    }

    /**
     * Initialize view and handle on click
     */
    private fun initView() {
        playlist = intent.getSerializableExtra("video") as MyCourseModel
        setVideoDetails()
        tvDescription.expandText()
        ytPlayer.initialize(getString(R.string.access_token), this);

        initPlaylistVideo()
    }

    /**
     * Set video details
     */
    private fun setVideoDetails() {
        tvTitle.text = playlist.title!!
        tvDescription.text = playlist.description!!
    }

    /**
     * Show next videos
     */
    @Suppress("UNCHECKED_CAST")
    private fun initPlaylistVideo() {
        rvVideo.setHasFixedSize(true);
        videoList = intent.getSerializableExtra("nextVideo") as ArrayList<MyCourseModel>
        rvVideo.layoutManager = LinearLayoutManager(baseContext)
        rvVideo.itemAnimator = DefaultItemAnimator()
        videoAdapter = CourseVideoAdapter()
        rvVideo.adapter = videoAdapter
        videoAdapter.addData(this, videoList)
        scrollView.smoothScrollTo(0, 0)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (!p2) {
            player = p1
            p1!!.loadVideo(playlist.videoId);
            setLocalVideo()
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

        if (p1!!.isUserRecoverableError) {
            p1.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            val error = String.format(getString(R.string.player_error), p1.toString())
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(getString(R.string.access_token), this)
        }
    }

    private fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return ytPlayer
    }

    /**
     * Handle on click and play next videos
     */
    override fun itemSelected(pos: Int, item: MyCourseModel, playVideo: Boolean) {
        if (player != null) {
            playlist = item
            player!!.loadVideo(playlist.videoId)
            player!!.play()
            setLocalVideo()
            val list = ArrayList<MyCourseModel>()
            for (i: Int in pos + 1 until videoList.size) {
                list.add(videoList[i])
            }
            videoList = list
            videoAdapter.addData(this, videoList)
            setVideoDetails()
        }
    }

    /**
     * Save recent video local database
     */
    private fun setLocalVideo() {
        val repository = PlaylistRepository(baseContext)
        repository.updateRecentVideo(playlist.videoId!!)
        //Log.e("Saved ", "in recent videos")
    }

}

