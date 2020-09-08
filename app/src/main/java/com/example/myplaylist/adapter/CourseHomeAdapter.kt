package com.example.myplaylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myplaylist.BR
import com.example.myplaylist.R
import com.example.myplaylist.databinding.RowItemCourseHomeBinding
import com.example.myplaylist.helpers.CustomClickListener
import com.example.myplaylist.model.MyCourseModel

/**
 * Created by Kailash Suthar on 05/9/2020.
 */
class CourseHomeAdapter : RecyclerView.Adapter<CourseHomeAdapter.ViewHolder>() {

    private var courseList: ArrayList<MyCourseModel>
    private lateinit var clickListener: CustomClickListener

    init {
        courseList = ArrayList()
    }

    /**
     * Initialize and set list
     */
    fun addData(listener: CustomClickListener, arrList: ArrayList<MyCourseModel>) {
        clickListener = listener
        this.courseList = arrList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val binding: RowItemCourseHomeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_item_course_home,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: RowItemCourseHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR.model, data)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.bind(courseList[pos])
        //handle on click
        holder.itemView.setOnClickListener {
            clickListener.itemSelected(pos, courseList[pos], false)
        }
    }

}