package com.volozhinsky.newsviewer.ui

import android.animation.ObjectAnimator
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
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volozhinsky.newsviewer.R
import com.volozhinsky.newsviewer.databinding.FragmentNewsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    private val newsListViewModel by viewModels<NewsListViewModel>()
    private var recyclerAdapter: NewsListAdapter? = null

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