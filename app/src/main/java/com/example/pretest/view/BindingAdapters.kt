package com.example.pretest.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide
        .with(view.context)
        .load(imageUrl)
        .apply(RequestOptions.circleCropTransform()) // round image
        .into(view)
}