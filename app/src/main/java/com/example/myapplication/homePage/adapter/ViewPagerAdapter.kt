package com.example.myapplication.homePage.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.homePage.model.Data
import com.example.myapplication.homePage.fragment.page1.ListingFragment
import com.example.myapplication.homePage.fragment.page2.ScrollViewFragment
import com.example.myapplication.homePage.fragment.page3.WebViewFragment

class ViewPagerAdapter(fa: FragmentActivity, val viewPager: ViewPager2) : FragmentStateAdapter(fa) {

    var mList: ArrayList<Data> = ArrayList()
    var numPages: Int = 0

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return ScrollViewFragment(mList, viewPager)
            2 -> {
                val args = Bundle()
                val fragment = WebViewFragment(viewPager)
                args.putString("url1", "https://www.ulifestyle.com.hk")
                args.putString("url2", "https://umagazine.com.hk")
                fragment.arguments = args
                return fragment
            }
        }
        return ListingFragment(mList, viewPager)
    }

    override fun getItemCount(): Int {
        return numPages
    }

    fun setList(list: ArrayList<Data>) {
        mList = list
    }

    fun setNumPage(numPage: Int) {
        numPages = numPage
    }
}