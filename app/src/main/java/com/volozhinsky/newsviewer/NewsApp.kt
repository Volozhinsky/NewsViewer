package com.volozhinsky.newsviewer

import android.app.Application
import com.volozhinsky.HasDependencies
import com.volozhinsky.newsviewer.di.ApplicationComponent
import com.volozhinsky.newsviewer.di.DaggerApplicationComponent

class NewsApp: Application(), HasDependencies{

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override val dependencies: Any
        get() = appComponent
}