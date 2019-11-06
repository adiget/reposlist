package com.annada.android.sample.squarerepos.di.component

import com.annada.android.sample.squarerepos.di.module.NetworkModule
import com.annada.android.sample.squarerepos.ui.RepoListViewModel
import com.annada.android.sample.squarerepos.ui.RepoViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(repoListViewModel: RepoListViewModel)

    /**
     * Injects required dependencies into the specified RepoViewModel.
     * @param repoViewModel RepoViewModel in which to inject the dependencies
     */
    fun inject(repoViewModel: RepoViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}