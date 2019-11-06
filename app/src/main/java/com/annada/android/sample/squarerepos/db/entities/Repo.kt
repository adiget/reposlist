package com.annada.android.sample.squarerepos.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Represents a table within the database.
 */
@Entity(tableName = "repo")
data class Repo(@PrimaryKey(autoGenerate = true) var id: Long?,
                     @ColumnInfo(name = "name") var name : String,
                     @ColumnInfo(name = "description") var description: String?){
    constructor():this(null,"","")
}
