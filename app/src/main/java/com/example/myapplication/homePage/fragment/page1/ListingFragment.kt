package com.example.myapplication.homePage.fragment.page1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.homePage.adapter.ParentRecyclerViewAdapter
import com.example.myapplication.homePage.model.Data


class ListingFragment(mList: ArrayList<Data>, val viewPager: ViewPager2) : Fragment() {

    val list: ArrayList<Data> = mList
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.parent_recyclerview, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val divider = DividerItemDecoration(context, resources.configuration.orientation)
            addItemDecoration(divider)
            isNestedScrollingEnabled = true // enable parent recyclerView's control on collapsing toolbar
            adapter = ParentRecyclerViewAdapter(list, viewPager)
        }
        return root
    }
}