package com.volozhinsky.newsviewer.di

import android.content.Context
import com.volozhinsky.newsviewer.ui.MainActivity
import com.volozhinsky.newsviewer.ui.NewsListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class,
    DataBaseModule::class,
    DataModule::class,
    RepositoryDataModule::class,
    ViewModelModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
    fun inject(fragment: NewsListFragment)
}