package com.volozhinsky.newsviewer.ui.utills

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object UtillsDataBinding {

    @JvmStatic
    @BindingAdapter("goneUnless")
    fun View.setvisible(flag: Boolean){
        this.isVisible = flag
    }
}