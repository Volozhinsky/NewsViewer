package com.volozhinsky.newsviewer.di

import com.volozhinsky.newsviewer.UpdateNewsBaseWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(UpdateNewsBaseWorker::class)
    fun bindHelloWorldWorker(factory: UpdateNewsBaseWorker.Factory): ChildWorkerFactory
}