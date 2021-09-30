package com.example.myapplication.homePage.viewPager.page3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.homePage.customViewForViewPager2.WebViewInViewPager2


class WebViewFragment(private val viewPager2: ViewPager2) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        val url1 = args!!.getString("url1", "")
        val url2 = args.getString("url2", "")
        val root = inflater.inflate(R.layout.scroll_webview_fragment, container, false)
        val webViewInViewPager2: WebViewInViewPager2 = root.findViewById(R.id.webView)
        val webViewInViewPager22: WebViewInViewPager2 = root.findViewById(R.id.webView2)
        webViewInViewPager2.apply {
            loadUrl(url1)
            settings.javaScriptEnabled = true
        }
        webViewInViewPager22.apply {
            loadUrl(url2)
            settings.javaScriptEnabled = true
        }
        return root
    }
}