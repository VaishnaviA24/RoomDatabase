package com.capgemini.starterkit.roomdatabase.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val PROJ_TABLE_NAME = "Project"

@Entity(tableName = PROJ_TABLE_NAME)
data class ProjectEntity(
    @PrimaryKey val projectId: String = "DEFAULT",
    val projectName: String
)