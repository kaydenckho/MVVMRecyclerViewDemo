package com.example.myapplication.ui.homePage.activity.mainActivity

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.ui.homePage.fragment.HomePageFragment
import com.example.myapplication.ui.homePage.adapter.ViewPagerAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import kotlinx.coroutines.launch

private const val NUM_PAGES = 5

@RequiresApi(Build.VERSION_CODES.O)
class HomePage : AppCompatActivity() {
    companion object{
        lateinit var viewPager: ViewPager2
    }
    private val pipBuilder by lazy { PictureInPictureParams.Builder() }
    private val context = this
    private var currentPage = 0
    private val viewModel by lazy {
        ViewModelProvider(this)[HomePageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val pipButton : ImageButton = findViewById(R.id.pipBtn)
        pipButton.setOnClickListener {
            pipMode()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomePageFragment.newInstance())
                .commitNow()
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getImageAsync()
                    viewModel.liveDataList.observe(context) { updatedList ->
                        // list to recycler view
                        if (!updatedList.isNullOrEmpty()) {
                            viewPager = findViewById(R.id.fragment1)
                            val pagerAdapter = ViewPagerAdapter(context, viewPager)
                            pagerAdapter.setNumPage(NUM_PAGES)
                            pagerAdapter.setList(updatedList)
                            viewPager.offscreenPageLimit = 5
                            viewPager.adapter = pagerAdapter
                            viewPager.currentItem = currentPage
                        }
                    }
                    Fresco.initialize(
                        context,
                        ImagePipelineConfig.newBuilder(context)
                            .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                            .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                            .experiment().setNativeCodeDisabled(true)
                            .build()
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        pipMode()
    }

    override fun onStop() {
        super.onStop()
        currentPage = viewPager.currentItem
    }

    private fun pipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager
                .defaultDisplay
                .getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            val aspectRatio = Rational(width, height)
            pipBuilder.setAspectRatio(aspectRatio).build()
            enterPictureInPictureMode(pipBuilder.build())
        }
    }

}