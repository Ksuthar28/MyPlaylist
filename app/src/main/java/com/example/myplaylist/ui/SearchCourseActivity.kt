package com.example.myplaylist.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myplaylist.R
import com.example.myplaylist.adapter.CourseHomeAdapter
import com.example.myplaylist.helpers.CustomClickListener
import com.example.myplaylist.helpers.FormatData
import com.example.myplaylist.helpers.HelperMethod
import com.example.myplaylist.model.MyCourseModel
import com.example.myplaylist.viewmodel.CourseViewModel
import kotlinx.android.synthetic.main.activity_search_course.*
import kotlinx.android.synthetic.main.progress_bar.*
/**
 * Created by Kailash Suthar on 05/9/2020.
 */

class SearchCourseActivity : AppCompatActivity(), CustomClickListener {

    val TAG = javaClass.simpleName
    private lateinit var searchAdapter: CourseHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_course)

        setUpToolbar()
    }

    /**
     * setup toolbar
     */
    private fun setUpToolbar() {
        try {
            val actionBar: ActionBar? = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            initSearchCourse()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Initialize views
     */
    private fun initSearchCourse() {
        rvSearchCourse.setHasFixedSize(true);
        rvSearchCourse.layoutManager = GridLayoutManager(baseContext, 2)
        rvSearchCourse.itemAnimator = DefaultItemAnimator()
        searchAdapter = CourseHomeAdapter()
        rvSearchCourse.adapter = searchAdapter

    }

    /**
     * Search course from live repo
     */
    private fun searchCourse(query: String) {
        if (HelperMethod.networkConnected(baseContext)) {
            progressBar.visibility = View.VISIBLE
            CourseViewModel().getCourse(query, getString(R.string.access_token), 4)
                .observe(this,
                    { response ->
                        //Log.e(TAG, "observe onChanged()=$response")
                        progressBar.visibility = View.GONE
                        if (response != null) {
                            searchAdapter.addData(this, FormatData.getCourses(response.items!!))
                            //searchAdapter.notifyDataSetChanged()
                        } else {
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
     * Search course from repo
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_search_view, menu)
        val mSearch: MenuItem = menu.findItem(R.id.action_search)
        mSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        val searchView = mSearch.actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_TEXT
        searchView.onActionViewExpanded()
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Log.e("search ", query);
                searchCourse(query)
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) searchAdapter.addData(this@SearchCourseActivity, ArrayList())
                return false
            }
        })
        return true
    }

    /**
     * Handle on click
     */
    override fun itemSelected(pos: Int, item: MyCourseModel, playVideo: Boolean) {
        startActivity(
            Intent(baseContext, PlaylistActivity::class.java)
                .putExtra("playlist", item)
        )
        finish()
    }

    /**
     * Handle home button on click
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}