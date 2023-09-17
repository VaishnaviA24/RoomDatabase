package com.capgemini.starterkit.roomdatabase.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_info", indices = [Index(value = ["email","name"], unique = true)])
data class MainInfoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id")
    val id : Int = 0,
    val name: String,
    val email: String,
    val projectId: String
)

@Entity(tableName = "project_details")
data class Project(
    @PrimaryKey val projectId: Long = 0,
    val projectName: String
)