package com.volozhinsky

import android.app.Activity

interface HasDependencies {
    val dependencies: Any
}

inline fun <reified T> Activity.findDependencies(): T {
    val application = application

    if (application !is HasDependencies || application.dependencies !is T)
        throw IllegalStateException("Can not find dependencies for $this")

    return application.dependencies as T
}