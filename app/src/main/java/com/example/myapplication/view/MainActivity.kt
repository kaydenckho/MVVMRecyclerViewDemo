package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapter.ViewPagerAdapter
import com.example.myapplication.viewModel.ViewModelMainActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import kotlinx.coroutines.launch

private const val NUM_PAGES = 5

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private val viewModel by lazy {
        ViewModelProvider(this).get(ViewModelMainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getImageAsync()
            }
        }
        viewModel.liveDataList.observe(this, { updatedList ->
            // list to recycler view
            if (!updatedList.isNullOrEmpty()) {
                viewPager = findViewById(R.id.fragment1)
                val pagerAdapter = ViewPagerAdapter(this, viewPager)
                pagerAdapter.setNumPage(NUM_PAGES)
                pagerAdapter.setList(updatedList)
                viewPager.offscreenPageLimit = 5
                viewPager.adapter = pagerAdapter
            }
        })
        Fresco.initialize(
            this,
            ImagePipelineConfig.newBuilder(this)
                .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                .experiment().setNativeCodeDisabled(true)
                .build()
        )
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
}