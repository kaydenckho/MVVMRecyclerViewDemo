package com.example.myapplication.ui.general.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.myapplication.R

class NavigationBar : CoordinatorLayout.Behavior<View> {
    constructor() {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    private var navigationBar: RelativeLayout? = null
    private var translationY = 0
    private val barColor = Color.rgb(3, 218, 197)
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (navigationBar == null) {
            val rootView = parent.parent.parent as ViewGroup
            navigationBar = rootView.findViewById(R.id.rlNavigationBar)
        }
        return dependency.id == R.id.fragment1
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        //        获取原始距离
        if (translationY == 0) {
            translationY = dependency.y.toInt() - child.height
            Log.d(TAG, "translationY: $translationY")
        }
        //        获取当前距离content列表的距离
        val depY = dependency.y - child.height
        //        除以2用于区分滑动到上半部还是下半部
        val ban = translationY / 2
        var offset = 0f
        var color = barColor

//        处于原始距离的下半部移动  减去10是为了修复移到屏幕外后还有几像素的露白
        if (depY > ban - 10) {
//            求出移动距离占child的比重  因为除的是原始距离所以需要乘以2是才能形成视觉差
            offset = (depY / translationY * child.height - child.height) * 2
            color = Color.TRANSPARENT
        } else {
//            处于原始距离的上半部移动
//            当滑动到上半部时直接使用一半header的高度计算出占child的比重
            offset = depY / ban * child.height
            //            由于是从屏幕外到屏幕内 所以需要从负值到0
            offset = if (offset > 0) -offset else 0F
        }
        //        Log.d(TAG, "depY: " + depY + " ban: " + ban + " offset: " + offset);
        child.setBackgroundColor(color)
        child.translationY = offset
        navigationBar!!.translationY = offset
        return super.onDependentViewChanged(parent, child, dependency)
    }

    /**
     * float depY = dependency.getY() - child.getHeight();
     * float bili = -(depY / translationY);
     * float offset = bili * child.getHeight();
     * Log.d(TAG, "depY: " + depY + "  bili: " + bili + " offset: " + offset);
     * offset = offset > 0 ? 0 : offset;
     */
    companion object {
        private const val TAG = "NavigationBarBehavior"
    }
}