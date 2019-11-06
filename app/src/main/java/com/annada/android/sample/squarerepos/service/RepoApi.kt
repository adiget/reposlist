package com.annada.android.sample.squarerepos.service

import com.annada.android.sample.squarerepos.db.entities.Repo
import io.reactivex.Observable
import retrofit2.http.GET

interface RepoApi {
    @GET("repos")
    fun getRepos(): Observable<List<Repo>>
}