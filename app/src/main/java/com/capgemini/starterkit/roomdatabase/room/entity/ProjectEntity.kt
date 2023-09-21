package com.capgemini.starterkit.roomdatabase.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TableName = "Project"

@Entity(tableName = TableName)
data class ProjectEntity(
    @PrimaryKey val projectId: Long = 0,
    val projectName: String
)
