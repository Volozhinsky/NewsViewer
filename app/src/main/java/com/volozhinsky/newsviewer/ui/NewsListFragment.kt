package com.volozhinsky.newsviewer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volozhinsky.newsviewer.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private val newsListViewModel by viewModels<NewsListViewModel>()
    private var recyclerAdapter: NewsListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            recyclerAdapter = NewsListAdapter(onClicFunc)
            adapter = recyclerAdapter
            layoutManager =
                LinearLayoutManager(
                    this@NewsListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListViewModel.errorliveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
        }
        newsListViewModel.newsListLiveData.observe(viewLifecycleOwner) {
            recyclerAdapter?.setNewsData(it)
        }
        val progressBar = view.findViewById<ProgressBar>(R.id.newslistProgressBar)
        newsListViewModel.loadingProgressBarLiveData.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
        }
        view.findViewById<EditText>(R.id.editTextKeyword)
            .addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    newsListViewModel.getNewsList(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        newsListViewModel.getNewsList("")
    }

    private val onClicFunc: (String) -> Unit = {
        val intent = Intent()
        intent.data = Uri.parse(it)
        startActivity(intent)
    }
}