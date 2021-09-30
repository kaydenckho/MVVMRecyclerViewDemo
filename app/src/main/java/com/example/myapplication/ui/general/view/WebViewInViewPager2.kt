package com.example.myapplication.ui.general.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.ui.homePage.activity.mainActivity.HomePage
import kotlin.math.abs

class WebViewInViewPager2 : WebView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {}

    lateinit var viewPagerRef : ViewPager2

    private var oldX = 0f
    private var oldY = 0f

    // indicate if horizontal scrollbar can't go more to the left
    private var overScrollLeft = false

    // indicate if horizontal scrollbar can't go more to the right
    private var overScrollRight = false

    // indicate if horizontal scrollbar can't go more to the left OR right
    private var isScrollingHorizontal = false

    // custom touch event in webView
    override fun onTouchEvent(event: MotionEvent): Boolean {
        requestDisallowInterceptTouchEvent(true)
        // width of the vertical scrollbar
        val scrollBarWidth = verticalScrollbarWidth

        // width of the view depending of you set in the layout
        val viewWidth = computeHorizontalScrollExtent()

        // width of the webpage depending of the zoom
        val innerWidth = computeHorizontalScrollRange()

        // position of the left side of the horizontal scrollbar
        val scrollBarLeftPos = computeHorizontalScrollOffset()

        // position of the right side of the horizontal scrollbar, the width of scroll is the width of view minus the width of vertical scrollbar
        val scrollBarRightPos = scrollBarLeftPos + viewWidth

        // if left pos of scroll bar is 0 left over scrolling is true
        overScrollLeft = scrollBarLeftPos == 0

        // if right pos of scroll bar is superior to webpage width: right over scrolling is true
        overScrollRight = scrollBarRightPos >= innerWidth

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // if scrollbar is the most left or right
                isScrollingHorizontal = overScrollLeft || overScrollRight
                oldX = event.x
                oldY = event.y
            }
            MotionEvent.ACTION_UP ->             // if scrollbar can't go more to the left OR right
                // this allow to force the user to do another gesture when he reach a side
                if (abs(event.y - oldY) <100 && isScrollingHorizontal) {
                    if ( event.x > oldX && overScrollLeft) {
                        HomePage.viewPager.currentItem--
                    }
                    if (event.x < oldX && overScrollRight) {
                        HomePage.viewPager.currentItem++
                    }
                }
        }
        return super.onTouchEvent(event)
    }
}