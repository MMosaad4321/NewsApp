package com.example.newsapp.Utilities

import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.R

fun ImageView.loadImage(url :String?){
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.default_content)
        .into(this)
}
fun Intent.share(url: String?) {
    this.type = "text/plain"
    this.putExtra(Intent.EXTRA_TEXT, url)
}
