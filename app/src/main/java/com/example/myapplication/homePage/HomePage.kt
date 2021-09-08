package com.example.myapplication.homePage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.homePage.adapter.ViewPagerAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import kotlinx.coroutines.launch

private const val NUM_PAGES = 5

class HomePage : AppCompatActivity() {
    companion object{
        lateinit var viewPager: ViewPager2
    }

    private val context = this
    private var currentPage = 0
    private val viewModel by lazy {
        ViewModelProvider(this).get(HomePageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getImageAsync()
                viewModel.liveDataList.observe(context, { updatedList ->
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
                })
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

    override fun onStop() {
        super.onStop()
        currentPage = viewPager.currentItem
    }

}