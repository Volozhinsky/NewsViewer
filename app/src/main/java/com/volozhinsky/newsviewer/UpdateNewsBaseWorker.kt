package com.volozhinsky.newsviewer

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.volozhinsky.domain.NewsRepository
import com.volozhinsky.newsviewer.di.ChildWorkerFactory
import javax.inject.Inject
import javax.inject.Provider

class UpdateNewsBaseWorker(context: Context, params: WorkerParameters, newsRepository: NewsRepository): Worker(context, params) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }

    class Factory @Inject constructor(
        private val foo: Provider<NewsRepository>
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return UpdateNewsBaseWorker(
                appContext,
                params,
                foo.get()
            )
        }
    }
}