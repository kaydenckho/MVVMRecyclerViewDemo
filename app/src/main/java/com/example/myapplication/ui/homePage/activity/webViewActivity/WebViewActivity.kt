package com.example.myapplication.ui.homePage.activity.webViewActivity

import android.app.ActivityManager
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.WebviewFragmentBinding

@RequiresApi(Build.VERSION_CODES.O)
class WebViewActivity : AppCompatActivity() {

    val binding by lazy { WebviewFragmentBinding.inflate(layoutInflater) }
    private val pipBuilder by lazy { PictureInPictureParams.Builder() }
    private val webView by lazy { binding.webView }
    private val pipBtn by lazy { binding.pipBtn }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.extras!!.getString("programUrl", "https://www.ulifestyle.com.hk/")

        setContentView(binding.root)
        webView.apply {
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
        pipBtn.setOnClickListener {
            pipMode()
        }
    }

    private fun pipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
            val aspectRatio = Rational(webView.width, webView.height)
            pipBuilder.setAspectRatio(aspectRatio).build()
            enterPictureInPictureMode(pipBuilder.build())
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        pipMode()
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode){ binding.pipBtn.visibility = View.GONE }
        else{ binding.pipBtn.visibility = View.VISIBLE }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navToLauncherTask()
    }

    private fun navToLauncherTask() {
        val activityManager =
            (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
        val appTasks = activityManager.appTasks
        for (task in appTasks) {
            val baseIntent = task.taskInfo.baseIntent
            val categories = baseIntent.categories
            if (categories != null && categories.contains(Intent.CATEGORY_LAUNCHER)) {
                task.moveToFront()
                return
            }
        }
    }
}