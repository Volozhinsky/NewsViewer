package com.volozhinsky.newsviewer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volozhinsky.newsviewer.R
import com.volozhinsky.domain.NewsRepository
import com.volozhinsky.newsviewer.ui.mappers.ArticleUIMapper
import com.volozhinsky.newsviewer.ui.models.ArticleUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


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
    private var _trigger = MutableStateFlow("")
    val trigger get() = _trigger
    val results: Flow<String> = trigger.mapLatest { keywords ->
        delay(3000)
        keywords
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ""
    )

    fun startLoading(keyword: String) {
        viewModelScope.launch(exceptionHandler) {
            _loadingProgressBarLiveData.value = true
            repository.getNews(keyword)
            _loadingProgressBarLiveData.value = false
            _emptyBase.value = newsListLiveData.value?.isEmpty()
        }
    }

    fun getNewsList(keyword: String) {
        trigger.value = keyword
    }

    fun startGettingFromDataBase(keyword: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getNewsFromDataBase(keyword).collect() { listArticle ->
                _newsListLiveData.value = listArticle.map { mapper(it) }
            }
        }
    }

    fun startObserveKeywords() {
        viewModelScope.launch {
            results.collect() { keywords ->
                startGettingFromDataBase(keywords)
                startLoading(keywords)
            }
        }
    }

    fun setUserCountry() {
        repository.setUserCountry("ru")
    }
}