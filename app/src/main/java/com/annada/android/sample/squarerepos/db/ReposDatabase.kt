package com.annada.android.sample.squarerepos.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.annada.android.sample.squarerepos.db.daos.RepoDao
import com.annada.android.sample.squarerepos.db.entities.Repo

@Database(entities = arrayOf(Repo::class), version = 1)
abstract class ReposDatabase : RoomDatabase() {

    abstract fun reposDataDao(): RepoDao

    companion object {
        private var INSTANCE: ReposDatabase? = null

        fun getInstance(context: Context): ReposDatabase? {
            if (INSTANCE == null) {
                synchronized(ReposDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        ReposDatabase::class.java, "repos.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}