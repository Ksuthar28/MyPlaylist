package com.example.myplaylist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplaylist.R
import com.example.myplaylist.adapter.CourseVideoAdapter
import com.example.myplaylist.database.PlaylistRepository
import com.example.myplaylist.helpers.CustomClickListener
import com.example.myplaylist.helpers.FormatData
import com.example.myplaylist.helpers.HelperMethod
import com.example.myplaylist.helpers.HelperMethod.Companion.loadImage
import com.example.myplaylist.model.MyCourseModel
import com.example.myplaylist.viewmodel.CoursePlaylistViewModel
import com.example.myplaylist.viewmodel.CourseVideoViewModel
import kotlinx.android.synthetic.main.activity_playlist.*
import kotlinx.android.synthetic.main.progress_bar.*
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class PlaylistActivity : AppCompatActivity(), CustomClickListener {

    val TAG = javaClass.simpleName
    private lateinit var videoAdapter: CourseVideoAdapter
    private lateinit var videoList: ArrayList<MyCourseModel>
    private lateinit var playlist: MyCourseModel
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        initView()
    }

    /**
     * Initialize views
     */
    private fun initView() {
        playlist = intent.getSerializableExtra("playlist") as MyCourseModel
        setPlayListDetails()
        initPlaylistCourse()
    }

    private fun initPlaylistCourse() {
        rvPlaylist.setHasFixedSize(true);
        videoList = ArrayList();
        rvPlaylist.layoutManager = LinearLayoutManager(baseContext)
        rvPlaylist.itemAnimator = DefaultItemAnimator()
        videoAdapter = CourseVideoAdapter()
        rvPlaylist.adapter = videoAdapter

        loadLocalVideos()
    }

    /**
     * Set playlist course details
     */
    private fun setPlayListDetails() {
        tvTitle.text = playlist.title!!
        tvDescription.text = playlist.description!!
        ivThumbnail.loadImage(playlist.image)
    }

    /**
     * Load playlist videos from database repository
     */
    private fun loadLocalVideos() {
        progressBar.visibility = View.VISIBLE
        val repository = PlaylistRepository(baseContext)
        repository.getAllVideos(playlist.playlistId!!)?.observe(this, Observer { response ->
            //Log.e(TAG, "observe loadLocalVideos()= ${response.size}")
            if (response != null && response.isNotEmpty()) {
                progressBar.visibility = View.GONE
                videoList = response as ArrayList<MyCourseModel>
                videoAdapter.addData(this, videoList)
            } else loadPlaylistLive()
        })
    }

    /**
     * Load playlist videos from repo
     */
    private fun loadPlaylistLive() {
        if (HelperMethod.networkConnected(baseContext)) {
            CoursePlaylistViewModel().getPlaylist(
                playlist.playlistId!!,
                getString(R.string.access_token)
            )
                .observe(this,
                    { response ->
                        //Log.e(TAG, "observe loadPlaylistLive()= ${response.items?.size}")
                        if (response != null) {
                            loadVideos(FormatData.getVideoIds(FormatData.getPlaylist(response.items!!)))
                        } else {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                getString(R.string.server_unreachable),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    })

        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Get videos details from repo
     */
    private fun loadVideos(id: String) {
        progressBar.visibility = View.VISIBLE
        CourseVideoViewModel().getVideos(
            id,
            getString(R.string.access_token)
        )
            .observe(this,
                { response ->
                    //Log.e(TAG, "observe loadVideos()= ${response.items?.size}")
                    progressBar.visibility = View.GONE
                    if (response != null) {
                        videoList = FormatData.getVideos(response.items!!, playlist.playlistId!!)
                        videoAdapter.addData(this, videoList)
                        saveVideos()
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.server_unreachable),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
    }

    /**
     * Save playlist videos in local database
     */
    private fun saveVideos() {
        val repository = PlaylistRepository(baseContext)
        repository.insertVideosTask(videoList)
        id = videoList[0].id
        //Log.e("Saved ", "in database")
    }

    /**
     * Handle on click
     */
    override fun itemSelected(pos: Int, item: MyCourseModel, playVideo: Boolean) {
        val list = ArrayList<MyCourseModel>()
        for (i: Int in pos + 1 until videoList.size) {
            list.add(videoList[i])
        }
        startActivity(
            Intent(baseContext, VideoActivity::class.java)
                .putExtra("video", videoList[pos])
                .putExtra("nextVideo", list)
        )
        //progressBar.visibility = View.VISIBLE
        //loadNextVideos(item, pos)
    }

    /**
     * Load next videos from local repository
     */
    private fun loadNextVideos(video: MyCourseModel, pos: Int) {
        val repository = PlaylistRepository(baseContext)
        repository.getNextVideos(id + 1, video.playlistId!!)
            ?.observe(this, Observer { response ->
                //Log.e(TAG, "observe loadNextVideos()= ${response.size}")
                var nextVideos = ArrayList<MyCourseModel>();
                if (response != null && response.isNotEmpty())
                    nextVideos = response as ArrayList<MyCourseModel>
                progressBar.visibility = View.GONE
                startActivity(
                    Intent(baseContext, VideoActivity::class.java)
                        .putExtra("video", videoList[pos])
                        .putExtra("nextVideo", nextVideos)
                )
            })
    }
}