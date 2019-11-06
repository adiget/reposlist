package com.annada.android.sample.squarerepos.base

import androidx.lifecycle.ViewModel
import com.annada.android.sample.squarerepos.di.component.DaggerViewModelInjector
import com.annada.android.sample.squarerepos.di.component.ViewModelInjector
import com.annada.android.sample.squarerepos.di.module.NetworkModule
import com.annada.android.sample.squarerepos.ui.RepoListViewModel
import com.annada.android.sample.squarerepos.ui.RepoViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is RepoListViewModel -> injector.inject(this)
            is RepoViewModel -> injector.inject(this)
        }
    }
}