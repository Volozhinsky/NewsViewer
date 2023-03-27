package com.volozhinsky.newsviewer

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.volozhinsky.HasDependencies
import com.volozhinsky.newsviewer.di.ApplicationComponent
import com.volozhinsky.newsviewer.di.DaggerApplicationComponent
import javax.inject.Inject

class NewsApp: Application(), HasDependencies{

    @Inject
    lateinit var workerFactory: WorkerFactory

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override val dependencies: Any
        get() = appComponent


    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
    }
}