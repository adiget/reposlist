package com.annada.android.sample.squarerepos.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.annada.android.sample.squarerepos.db.ReposDatabase

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepoListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, ReposDatabase::class.java, "repos").build()
            @Suppress("UNCHECKED_CAST")
            return RepoListViewModel(db.reposDataDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}