package com.volozhinsky.newsviewer.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.volozhinsky.newsviewer.R
import com.volozhinsky.newsviewer.databinding.RvItemNewsListBinding
import com.volozhinsky.newsviewer.ui.models.ArticleUI

class NewsListViewHolder(private val itemBinding: RvItemNewsListBinding, private val onClicFunc: (String) -> Unit) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: ArticleUI) {
        val name = itemBinding.textViewTitle
        val poster = itemBinding.imageViewArticle
        name.text = item.title
        loadPoster(item.urlToImage, poster)
        itemBinding.root.setOnClickListener {
            onClicFunc.invoke(item.url)
        }
    }

    private fun loadPoster(url: String, imageView: ImageView) {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}