package com.volozhinsky.newsviewer.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.volozhinsky.newsviewer.R
import com.volozhinsky.newsviewer.ui.models.ArticleUI

class NewsListViewHolder(itemView: View, private val onClicFunc: (String) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(item: ArticleUI) {
        val name = itemView.findViewById<TextView>(R.id.textViewTitle)
        val poster = itemView.findViewById<ImageView>(R.id.imageViewArticle)
        name.text = item.title
        loadPoster(item.urlToImage, poster)
        itemView.setOnClickListener {
            onClicFunc.invoke(item.url)
        }
    }

    private fun loadPoster(url: String, imageView: ImageView) {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}