package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.HORIZONTAL
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapter.ChildRecyclerViewAdapter
import com.example.myapplication.adapter.ParentRecyclerViewAdapter
import com.example.myapplication.model.Data


class ScrollViewFragment(mList : ArrayList<Data>, val viewPager: ViewPager2) : Fragment(){

    val list : ArrayList<Data> = mList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.parent_scrollview, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.Child_RV)
        val recyclerView2: RecyclerView = root.findViewById(R.id.Child_RV2)
        val recyclerView3: RecyclerView = root.findViewById(R.id.Child_RV3)
        val recyclerView4: RecyclerView = root.findViewById(R.id.Child_RV4)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ChildRecyclerViewAdapter(list)
            controlHorizontalScrollingInViewPager2(this, viewPager)

        }
        recyclerView2.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ChildRecyclerViewAdapter(list)
            controlHorizontalScrollingInViewPager2(this, viewPager)
        }
        recyclerView3.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ChildRecyclerViewAdapter(list)
            controlHorizontalScrollingInViewPager2(this, viewPager)
        }
        recyclerView4.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ChildRecyclerViewAdapter(list)
            controlHorizontalScrollingInViewPager2(this, viewPager)
        }
        return root
    }

    fun controlHorizontalScrollingInViewPager2(recyclerView: RecyclerView, viewPager2: ViewPager2) {
        val onTouchListener: OnItemTouchListener = object : OnItemTouchListener {
            override fun onInterceptTouchEvent(
                rv: RecyclerView,
                e: MotionEvent
            ): Boolean {
                println(e.action.toString())
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        viewPager2.isUserInputEnabled = false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        viewPager2.isUserInputEnabled = !rv.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)
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