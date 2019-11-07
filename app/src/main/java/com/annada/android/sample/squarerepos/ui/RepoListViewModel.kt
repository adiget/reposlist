package com.annada.android.sample.squarerepos.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.annada.android.sample.squarerepos.R
import com.annada.android.sample.squarerepos.base.BaseViewModel
import com.annada.android.sample.squarerepos.db.daos.RepoDao
import com.annada.android.sample.squarerepos.db.entities.Repo
import com.annada.android.sample.squarerepos.service.RepoApi
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import javax.inject.Inject

class RepoListViewModel(private val repoDao: RepoDao) : BaseViewModel() {
    @Inject
    lateinit var repoApi: RepoApi
    val repoListAdapter: RepoListAdapter = RepoListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadData() }

    private lateinit var subscription: Disposable

    private val viewModelJob = Job()
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        when (error) {
            is Exception -> onRetrieveRepoListError()
        }
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + errorHandler)
    private val bgDispatcher = Dispatchers.IO

    init {
        loadData()
    }

    private fun loadData() {
        onRetrieveRepoListStart()

        uiScope.launch {
            var repoList = getDbRepos()

            if (repoList?.isNotEmpty() == false) {
                repoList = repoApi.getReposCortn()

                insertAll(repos = repoList)
            }

            repoList?.let { onRetrieveRepoListSuccess(it) }

            onRetrieveRepoListFinish()
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()

        viewModelJob.cancel()
    }

    private fun onRetrieveRepoListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveRepoListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRepoListSuccess(repoList: List<Repo>) {
        repoListAdapter.updateRepoList(repoList)
    }

    private fun onRetrieveRepoListError() {
        errorMessage.value = R.string.repo_error
    }

    private suspend fun getDbRepos(): List<Repo>? {
        return withContext(bgDispatcher) {
            repoDao.allLiveData
        }
    }

    private suspend fun insertAll(repos: List<Repo>) {
        return withContext(bgDispatcher) {
            repoDao.insertAll(*repos.toTypedArray())
        }
    }
}