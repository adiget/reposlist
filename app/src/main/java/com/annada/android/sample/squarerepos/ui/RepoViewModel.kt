package com.annada.android.sample.squarerepos.ui

import androidx.lifecycle.MutableLiveData
import com.annada.android.sample.squarerepos.base.BaseViewModel
import com.annada.android.sample.squarerepos.db.entities.Repo

class RepoViewModel : BaseViewModel() {
    private val repoName = MutableLiveData<String>()
    private val repoDesc = MutableLiveData<String>()

    fun bind(repo: Repo) {
        repoName.value = repo.name
        repoDesc.value = repo.description
    }

    fun getRepoName(): MutableLiveData<String> {
        return repoName
    }

    fun getRepoDesc(): MutableLiveData<String> {
        return repoDesc
    }
}