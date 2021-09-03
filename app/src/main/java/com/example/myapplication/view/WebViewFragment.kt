package com.example.myapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapter.TouchyWebView


class WebViewFragment(viewPager2: ViewPager2) : Fragment() {

    private val viewPager2 = viewPager2
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
        val webView: TouchyWebView = root.findViewById(R.id.webView)
        val webView2: TouchyWebView = root.findViewById(R.id.webView2)
        webView.apply {
            loadUrl(url1)
            settings.javaScriptEnabled = true
        }
        webView2.apply {
            loadUrl(url2)
            settings.javaScriptEnabled = true
        }
        return root
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setTouchControl(webView: TouchyWebView) {
        webView.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewPager2.isUserInputEnabled = false
                }
                MotionEvent.ACTION_MOVE -> {
                    viewPager2.isUserInputEnabled = false
                }
                MotionEvent.ACTION_UP -> {
                    viewPager2.isUserInputEnabled = true
                }
                else -> {
                    viewPager2.isUserInputEnabled = true
                }
            }
            v?.onTouchEvent(event) ?: true
        }
    }


}