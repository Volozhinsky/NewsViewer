package com.volozhinsky.newsviewer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volozhinsky.newsviewer.R
import com.volozhinsky.newsviewer.domain.NewsRepository
import com.volozhinsky.newsviewer.ui.mappers.ArticleUIMapper
import com.volozhinsky.newsviewer.ui.models.ArticleUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val mapper: ArticleUIMapper,

) : ViewModel() {

    private var _newsListLiveData = MutableLiveData<List<ArticleUI>>()
    val newsListLiveData get() = _newsListLiveData
    private var _loadingProgressBarLiveData = MutableLiveData<Boolean>()
    val loadingProgressBarLiveData get() = _loadingProgressBarLiveData
    private val _errorliveData = MutableLiveData<Int>()
    val errorliveData: LiveData<Int> get() = _errorliveData
    private val exceptionHandler = CoroutineExceptionHandler { _, ex ->
        _errorliveData.value = R.string.unknownEx
        _loadingProgressBarLiveData.value = false
    }
    private var _emptyBase = MutableLiveData<Boolean>()
    val emptyBase get() = _emptyBase

    fun getNewsList(keyword: String) {
        viewModelScope.launch(exceptionHandler) {
            _loadingProgressBarLiveData.value = true
            _newsListLiveData.value = repository.getNews(keyword).map { mapper(it) }
            _loadingProgressBarLiveData.value = false
            _emptyBase.value = newsListLiveData.value?.isEmpty()
        }
    }

    fun setUserCountry() {
        repository.setUserCountry("ru")
    }
}