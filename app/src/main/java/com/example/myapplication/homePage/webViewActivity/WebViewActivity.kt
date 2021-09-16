package com.example.myapplication.homePage.webViewActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.WebviewFragmentBinding

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.extras!!.getString("programUrl", "https://www.ulifestyle.com.hk/")
        val binding by lazy { WebviewFragmentBinding.inflate(layoutInflater) }
        setContentView(binding.root)
        binding.webView.apply {
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }
}