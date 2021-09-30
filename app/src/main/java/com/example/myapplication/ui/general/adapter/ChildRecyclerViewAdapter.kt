package com.example.myapplication.ui.general.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.general.model.Data
import com.example.myapplication.ui.webView.activity.activity.WebViewActivity
import com.facebook.drawee.view.SimpleDraweeView

class ChildRecyclerViewAdapter(private val mData: ArrayList<Data>) :
    RecyclerView.Adapter<ChildRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context
    val scale_up by lazy { AnimationUtils.loadAnimation(context, R.anim.btn_scale_up) }
    val scale_down by lazy { AnimationUtils.loadAnimation(context, R.anim.btn_scale_down) }

    init {
        mData.sortBy { it.displayOrder }
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val imageIconView: SimpleDraweeView = itemView.findViewById(R.id.icon)
        val nameTextView: TextView = itemView.findViewById(R.id.list_name)
        val ageTextView: TextView = itemView.findViewById(R.id.list_age)
        val btnView: Button = itemView.findViewById(R.id.view_btn)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        this.context = context
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    // Involves populating data into the item through holder
    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        fun gotoWebView(url: String?) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("programUrl", url)
            context.startActivity(intent)
        }
        viewHolder.itemView.setOnClickListener {
            gotoWebView(mData[position].programUrl)
        }
        // Get the data model based on position
        val data: Data = mData[position]

        // Set item views based on your views and data model
        val textView = viewHolder.nameTextView
        textView.setText(data.title)
        val textView2 = viewHolder.ageTextView
        textView2.setText(data.description)
        val imageView = viewHolder.imageIconView
        imageView.setImageURI(data.imageUrl)
        val btnView = viewHolder.btnView
        viewHolder.btnView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                btnView.startAnimation(scale_up)
                btnView.background = context.getDrawable(R.drawable.mybutton_pressed)
            } else if (event.action == MotionEvent.ACTION_UP) {
                btnView.startAnimation(scale_down)
                gotoWebView(mData[position].programUrl)
                btnView.background = context.getDrawable(R.drawable.mybutton)
            } else if (event.action == MotionEvent.ACTION_CANCEL) {
                btnView.startAnimation(scale_down)
                btnView.background = context.getDrawable(R.drawable.mybutton)
            }
            true
        }
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mData.size
    }
}