package com.example.myplaylist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplaylist.adapter.CourseHomeAdapter
import com.example.myplaylist.adapter.CourseRecentAdapter
import com.example.myplaylist.database.PlaylistRepository
import com.example.myplaylist.helpers.CustomClickListener
import com.example.myplaylist.helpers.FormatData
import com.example.myplaylist.helpers.HelperMethod
import com.example.myplaylist.model.MyCourseModel
import com.example.myplaylist.ui.PlaylistActivity
import com.example.myplaylist.ui.SearchCourseActivity
import com.example.myplaylist.ui.VideoActivity
import com.example.myplaylist.viewmodel.CourseViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.progress_bar.*
/**
 * Created by Kailash Suthar on 04/9/2020.
 */

class HomeActivity : AppCompatActivity(), CustomClickListener {

    val TAG = javaClass.simpleName
    private lateinit var homeAdapter: CourseHomeAdapter
    private lateinit var recentAdapter: CourseRecentAdapter
    private lateinit var coursesList: ArrayList<MyCourseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
    }

    /**
     * Initialize view and handle on click
     */
    private fun initViews() {

        initRecentCourses()
        initStaticCourses()

        ivSearch.setOnClickListener {
            startActivity(
                Intent(
                    baseContext,
                    SearchCourseActivity::class.java
                )
            )
        }

    }

    override fun onResume() {
        super.onResume()
        //load recent videos from data base
        loadRecentCourse()
    }

    private fun initRecentCourses() {
        rvRecentCourse.setHasFixedSize(true);
        rvRecentCourse.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        rvStaticCourse.itemAnimator = DefaultItemAnimator()
        recentAdapter = CourseRecentAdapter()
        rvRecentCourse.adapter = recentAdapter
    }

    private fun initStaticCourses() {
        rvStaticCourse.setHasFixedSize(true);
        coursesList = ArrayList()
        rvStaticCourse.layoutManager = GridLayoutManager(baseContext, 2)
        rvStaticCourse.itemAnimator = DefaultItemAnimator()
        homeAdapter = CourseHomeAdapter()
        rvStaticCourse.adapter = homeAdapter

        val courses = arrayOf("english courses", "programming courses", "yoga courses")
        progressBar.visibility = View.VISIBLE
        for (course in courses) {
            loadStaticCourse(course)
        }
    }

    /**
     * Load recent courses from database repository
     */
    private fun loadRecentCourse() {
        val repository = PlaylistRepository(baseContext)
        repository.getRecentVideos()?.observe(this, Observer { response ->
            //Log.e(TAG, "observe loadRecentCourse()= ${response.size}")
            if (response != null) {
                recentAdapter.addData(this, response as ArrayList<MyCourseModel>)
                recentCourseView.visibility =
                    if (response.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
    }

    /**
     * Load courses from youtube data apis
     */
    private fun loadStaticCourse(query: String) {
        if (HelperMethod.networkConnected(baseContext)) {
            CourseViewModel().getCourse(query, getString(R.string.access_token), 2)
                .observe(this,
                    { response ->
                        //Log.e(TAG, "observe onChanged()=$response")
                        //progressBar.visibility = View.GONE
                        if (response != null) {
                            coursesList.addAll(FormatData.getCourses(response.items!!))
                            if (query == "yoga courses") {
                                progressBar.visibility = View.GONE
                                homeAdapter.addData(this, coursesList)
                            }
                        } else {
                            progressBar.visibility = View.GONE
                            homeAdapter.addData(this, coursesList)
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
     * Handle on click on card
     */
    override fun itemSelected(pos: Int, item: MyCourseModel, playVideo: Boolean) {
        if (playVideo) {
            startActivity(
                Intent(baseContext, VideoActivity::class.java)
                    .putExtra("video", item)
                    .putExtra("nextVideo", ArrayList<MyCourseModel>())
            )
        } else {
            startActivity(
                Intent(baseContext, PlaylistActivity::class.java)
                    .putExtra("playlist", item)
            )
        }
    }
}

    