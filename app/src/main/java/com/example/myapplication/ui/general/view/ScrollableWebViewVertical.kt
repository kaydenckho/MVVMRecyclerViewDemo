package com.example.myapplication.ui.general.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

class ScrollableWebViewVertical @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (clampedY) {
            requestDisallowInterceptTouchEvent(false)
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }
}