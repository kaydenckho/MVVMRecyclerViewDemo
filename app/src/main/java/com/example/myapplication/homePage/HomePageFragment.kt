package com.example.myapplication.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.homePage.adapter.ParentRecyclerViewAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomePageFragment : Fragment(){

    companion object {
        fun newInstance() = HomePageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.home_page, container, false)
        val floatingActionButton: FloatingActionButton = root.findViewById(R.id.done_fab)
        val collapsing_toolbar_l : CollapsingToolbarLayout = root.findViewById(R.id.collapsing_toolbar_l)
        collapsing_toolbar_l.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        collapsing_toolbar_l.setExpandedTitleColor(resources.getColor(R.color.white))
        floatingActionButton.apply {
            setOnClickListener { Toast.makeText(context, "Floating Action Button", Toast.LENGTH_SHORT).show()  }
        }
        return root
    }
}