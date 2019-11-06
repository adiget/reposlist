package com.annada.android.sample.squarerepos.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.annada.android.sample.squarerepos.R
import com.annada.android.sample.squarerepos.base.BaseViewModel
import com.annada.android.sample.squarerepos.db.daos.RepoDao
import com.annada.android.sample.squarerepos.db.entities.Repo
import com.annada.android.sample.squarerepos.service.RepoApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoListViewModel(private val repoDao: RepoDao) : BaseViewModel() {
    @Inject
    lateinit var repoApi: RepoApi
    val repoListAdapter: RepoListAdapter = RepoListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadRepos() }

    private lateinit var subscription: Disposable

    init {
        loadRepos()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadRepos() {
        subscription = Observable.fromCallable { repoDao.all }
            .concatMap { dbRepoList ->
                if (dbRepoList.isEmpty())
                    repoApi.getRepos().concatMap { apiRepoList ->
                        repoDao.insertAll(*apiRepoList.toTypedArray())
                        Observable.just(apiRepoList)
                    }
                else
                    Observable.just(dbRepoList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRepoListStart() }
            .doOnTerminate { onRetrieveRepoListFinish() }
            .subscribe(
                { result -> onRetrieveRepoListSuccess(result) },
                { onRetrieveRepoListError() }
            )
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
}