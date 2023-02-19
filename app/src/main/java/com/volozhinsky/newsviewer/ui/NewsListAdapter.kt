package com.volozhinsky.newsviewer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volozhinsky.newsviewer.R
import com.volozhinsky.newsviewer.ui.models.ArticleUI

class NewsListAdapter(private val onClicFunc: (String) -> Unit) :
    RecyclerView.Adapter<NewsListViewHolder>() {

    private var newsListResponse: MutableList<ArticleUI> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_news_list, parent, false)
        return NewsListViewHolder(view, onClicFunc)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind(newsListResponse[position])
    }

    override fun getItemCount(): Int = newsListResponse.size

    fun setNewsData(list: List<ArticleUI>) {
        this.newsListResponse.clear()
        this.newsListResponse.addAll(list)
        notifyDataSetChanged()
    }
}