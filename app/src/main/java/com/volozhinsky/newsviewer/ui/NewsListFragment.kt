package com.volozhinsky.newsviewer.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.volozhinsky.newsviewer.NewsApp
import com.volozhinsky.newsviewer.R
import com.volozhinsky.newsviewer.databinding.FragmentNewsListBinding
import com.volozhinsky.newsviewer.di.ViewModelFactory
import javax.inject.Inject


class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var factory: ViewModelFactory
    private val newsListViewModel: NewsListViewModel by viewModels { factory }
    private var recyclerAdapter: NewsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
       (requireContext().applicationContext as NewsApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsListViewModel.setUserCountry()

        val viewRoot = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_news_list, container, false)
        _binding = DataBindingUtil.bind(viewRoot)
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = newsListViewModel
        initViews()
        initLiveData()
        newsListViewModel.getNewsList("")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initLiveData() {
        newsListViewModel.errorliveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
        }
        newsListViewModel.newsListLiveData.observe(viewLifecycleOwner) {
            recyclerAdapter?.setNewsData(it)
        }
        newsListViewModel.loadingProgressBarLiveData.observe(viewLifecycleOwner) {
            if (it) {
                ObjectAnimator.ofFloat(binding.newslistProgressBar, View.SCALE_X, 0f, 1f).start();
                ObjectAnimator.ofFloat(binding.newslistProgressBar, View.SCALE_Y, 0f, 1f).start();
            }
        }
    }

    private fun initViews() {
        binding.recyclerView.apply {
            recyclerAdapter = NewsListAdapter(onClicFunc)
            adapter = recyclerAdapter
            layoutManager =
                LinearLayoutManager(
                    this@NewsListFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
        binding.editTextKeyword
            .doOnTextChanged { p0, _1, _2, _3 ->
                newsListViewModel.getNewsList(p0.toString())
            }
    }

    private val onClicFunc: (String) -> Unit = {
        val intent = Intent()
        intent.data = Uri.parse(it)
        startActivity(intent)
    }
}