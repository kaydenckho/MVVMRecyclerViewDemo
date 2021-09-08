package com.example.myapplication.homePage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.homePage.HomePage
import com.example.myapplication.homePage.model.Data

enum class RowType{
    CAN_SCROLL,
    CANNOT_SCROLL
}

class ParentRecyclerViewAdapter(val mData: ArrayList<Data>, val viewPager: ViewPager2) :
    RecyclerView.Adapter<ParentRecyclerViewAdapter.ViewHolder>() {
    val scrollableList = ArrayList<RowType>()
    lateinit var context: Context

    init {
        mData.sortBy { it.displayOrder }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.Child_RV)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentRecyclerViewAdapter.ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.child_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ParentRecyclerViewAdapter.ViewHolder, position: Int) {
        for (i in 0..mData.size) { if (i%2==0) scrollableList.add(RowType.CAN_SCROLL) else scrollableList.add(RowType.CANNOT_SCROLL)}
        val layoutMan = when (scrollableList[position]){
            RowType.CAN_SCROLL -> LinearLayoutManager(context, HORIZONTAL, false)
            RowType.CANNOT_SCROLL ->  object : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
        }
        viewHolder.childRecyclerView.apply {
            layoutManager = layoutMan
            setHasFixedSize(true)
            adapter = ChildRecyclerViewAdapter(mData)
            val snapHelper: SnapHelper = PagerSnapHelper()
            onFlingListener = null
            snapHelper.attachToRecyclerView(this)
            controlHorizontalScrollingInViewPager2(this, viewPager)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun controlHorizontalScrollingInViewPager2(recyclerView: RecyclerView, viewPager2: ViewPager2) {
        val onTouchListener: OnItemTouchListener = object : OnItemTouchListener {
            override fun onInterceptTouchEvent(
                rv: RecyclerView,
                e: MotionEvent
            ): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        viewPager2.isUserInputEnabled = false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        viewPager2.isUserInputEnabled = !(rv.canScrollHorizontally(1) or rv.canScrollHorizontally(-1))
                    }
                    MotionEvent.ACTION_UP -> {
                        viewPager2.isUserInputEnabled = true
                    }
                    else -> {
                        viewPager2.isUserInputEnabled = true
                    }
                }
                return false
            }
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        }
        recyclerView.addOnItemTouchListener(onTouchListener)
    }


}