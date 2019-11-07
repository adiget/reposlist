package com.annada.android.sample.squarerepos.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.annada.android.sample.squarerepos.db.entities.Repo

@Dao
interface RepoDao {

    @get:Query("SELECT * from repo")
    val all: List<Repo>

    @get:Query("SELECT * from repo")
    val allLiveData: List<Repo>?

    @Insert(onConflict = REPLACE)
    fun insert(repo: Repo)

    @Insert
    fun insertAll(vararg repo: Repo)

    @Query("DELETE from repo")
    fun deleteAll()
}