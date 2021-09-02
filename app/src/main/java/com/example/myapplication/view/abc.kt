package com.example.myapplication.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.AbcBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

class abc : AppCompatActivity(){
    val from_bottom : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom) }
    val to_bottom : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom) }
    val rotate_close : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close) }
    val rotate_open : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open) }
    val binding by lazy {AbcBinding.inflate(layoutInflater)}
    var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.a.setOnClickListener{
            onAddButtonClicked()
        }
        binding.c.setOnClickListener { Toast.makeText(applicationContext, "c is clicked",Toast.LENGTH_SHORT).show() }
        binding.b.setOnClickListener { Toast.makeText(applicationContext, "b is clicked",Toast.LENGTH_SHORT).show() }
    }

    fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    fun setVisibility(clicked : Boolean){
        if (!clicked){
            binding.b.visibility = View.VISIBLE
            binding.c.visibility = View.VISIBLE
        }
        else{
            binding.b.visibility = View.INVISIBLE
            binding.c.visibility = View.INVISIBLE
        }

    }

    fun setAnimation(clicked : Boolean){
        if (!clicked){
            binding.b.startAnimation(from_bottom)
            binding.c.startAnimation(from_bottom)
            binding.a.startAnimation(rotate_open)
        }
        else{
            binding.b.startAnimation(to_bottom)
            binding.c.startAnimation(to_bottom)
            binding.a.startAnimation(rotate_close)
        }
    }

}