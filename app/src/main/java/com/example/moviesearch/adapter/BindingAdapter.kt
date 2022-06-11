package com.example.moviesearch.adapter

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageLoadDefault")
fun bindImageLoadDefault(view: ImageView, url: String?) {
    if (url != null || url != "") {
        view.setImageResource(android.R.color.transparent)
        Glide.with(view).load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions.fitCenterTransform())
            .into(view)
    }
}

@BindingAdapter("loadHtml")
fun loadHtml(view: TextView, content: String?) {
    if(!content.isNullOrEmpty()) {
        view.text = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(content)
        }
    }
}