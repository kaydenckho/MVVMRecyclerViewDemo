package com.example.myapplication.ui.general.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.ui.general.view.RecyclerViewInViewPager2
import com.example.myapplication.ui.homePage.model.Data

class ParentRecyclerViewAdapter(val mData: ArrayList<Data>, val viewPager: ViewPager2) :
    RecyclerView.Adapter<ParentRecyclerViewAdapter.ViewHolder>() {
    var viewtype : Int = 0

    companion object {
        private const val TYPE_SCROLLABLE = 0
        private const val TYPE_NOT_SCROLLABLE = 1
    }

    lateinit var context: Context

    init {
        mData.sortBy { it.displayOrder }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val childRecyclerView: RecyclerViewInViewPager2 = itemView.findViewById(R.id.Child_RV)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        this.context = parent.context
        viewtype = when (viewType){
            TYPE_SCROLLABLE -> 1
            TYPE_NOT_SCROLLABLE -> 2
            else -> throw IllegalArgumentException("Invalid type of data $viewType")
        }
        val view = LayoutInflater.from(context).inflate(R.layout.child_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.childRecyclerView.apply {
            layoutManager = when (viewtype){
                1 -> LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                2 -> object : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
                    override fun canScrollHorizontally(): Boolean { return false }
                }
                else -> throw IllegalArgumentException("Invalid type of data $viewtype")
            }
            setHasFixedSize(true)
            adapter = ChildRecyclerViewAdapter(mData)
            val snapHelper: SnapHelper = PagerSnapHelper()
            isNestedScrollingEnabled = false // disable child recyclerView's control on collapsing toolbar
            onFlingListener = null
            snapHelper.attachToRecyclerView(this)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position%2){
            0 -> TYPE_SCROLLABLE
            1 -> TYPE_NOT_SCROLLABLE
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }
}