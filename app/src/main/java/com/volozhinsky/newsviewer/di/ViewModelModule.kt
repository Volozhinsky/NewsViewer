package com.volozhinsky.newsviewer.di

import androidx.lifecycle.ViewModel
import com.volozhinsky.newsviewer.ui.NewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    fun bindNewsListViewModel(viewModel: NewsListViewModel): ViewModel
}